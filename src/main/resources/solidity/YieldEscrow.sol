// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

import "./contracts/token/ERC20/utils/SafeERC20.sol";
import "./contracts/token/ERC20/IERC20.sol";
import "./contracts/utils/ReentrancyGuard.sol";
import "./contracts/access/Ownable.sol";

/**
 * @title YieldEscrow
 * @dev A smart contract for Ryvion, an AI-powered RWA yield platform on Arc blockchain.
 * This contract manages user deposits, yield distribution, and strategy management.
 */
contract YieldEscrow is Ownable, ReentrancyGuard {
    using SafeERC20 for IERC20;

    IERC20 public immutable token;  // USDC token
    uint256 public nextId;          // Next strategy ID
    uint256 public accumulatedFees; // Platform fees accumulated
    uint256 public feePercentage = 10; // 10% fee on yield (can be adjusted by owner)
    uint256 public constant MAX_FEE_PERCENTAGE = 20; // Maximum fee percentage (20%)

    enum RiskLevel { Low, Medium, High }

    struct Strategy {
        address owner;
        uint256 totalDeposited;
        uint256 totalYield;
        bool isActive;
        string recommendationStr;
        RiskLevel riskLevel;
        uint256 createdAt;
        uint256 lastYieldAt;
    }

    mapping(uint256 => Strategy) public strategies;
    mapping(address => uint256[]) public userStrategies;

    event StrategyCreated(uint256 indexed id, address indexed user, RiskLevel riskLevel, string recommendation);
    event StrategyDeposit(uint256 indexed id, address indexed user, uint256 amount);
    event YieldDistributed(uint256 indexed id, address indexed user, uint256 yieldPayout, uint256 fee);
    event StrategyWithdrawn(uint256 indexed id, address indexed user, uint256 totalAmount);
    event FeesWithdrawn(address indexed recipient, uint256 amount);
    event FeePercentageUpdated(uint256 oldFee, uint256 newFee);
    event EmergencyWithdraw(address indexed recipient, uint256 amount);

    /**
     * @dev Constructor initializes the contract with the USDC token address
     * @param _token The address of the USDC token contract
     */
    constructor(address _token) Ownable(msg.sender) {
        token = IERC20(_token);
        nextId = 1; // Start IDs from 1
    }

    /**
     * @dev Creates a new yield strategy for a user
     * @param recommendationStr AI-generated recommendation string
     * @param riskLevel User's selected risk level
     * @return id The ID of the created strategy
     */
    function createStrategy(
        string calldata recommendationStr,
        RiskLevel riskLevel
    ) external returns (uint256) {
        uint256 id = nextId;
        strategies[id] = Strategy({
            owner: msg.sender,
            totalDeposited: 0,
            totalYield: 0,
            isActive: true,
            recommendationStr: recommendationStr,
            riskLevel: riskLevel,
            createdAt: block.timestamp,
            lastYieldAt: block.timestamp
        });
        userStrategies[msg.sender].push(id);
        nextId = nextId + 1;

        emit StrategyCreated(id, msg.sender, riskLevel, recommendationStr);
        return id;
    }

    /**
     * @dev Allows a user to deposit funds into their strategy
     * @param id The strategy ID
     * @param amount The amount to deposit
     */
    function deposit(uint256 id, uint256 amount) external nonReentrant {
        Strategy storage strategy = strategies[id];
        require(strategy.owner == msg.sender, "Not strategy owner");
        require(strategy.isActive, "Strategy not active");
        require(amount > 0, "Amount must be greater than 0");

        token.safeTransferFrom(msg.sender, address(this), amount);
        strategy.totalDeposited = strategy.totalDeposited + amount;

        emit StrategyDeposit(id, msg.sender, amount);
    }

    /**
     * @dev Distributes yield to a user's strategy (called by the AI agent)
     * @param id The strategy ID
     * @param yieldAmount The amount of yield to distribute
     * @param minExpectedYield Minimum expected yield (slippage protection)
     */
    function distributeYield(
        uint256 id,
        uint256 yieldAmount,
        uint256 minExpectedYield
    ) external onlyOwner nonReentrant {
        Strategy storage strategy = strategies[id];
        require(strategy.isActive, "Strategy not active");
        require(
            token.balanceOf(address(this)) >= yieldAmount,
            "Insufficient yield balance"
        );
        require(yieldAmount >= minExpectedYield, "Slippage too high");

        uint256 fee = (yieldAmount * feePercentage) / 100;
        uint256 userYield = yieldAmount - fee;

        strategy.totalYield = strategy.totalYield + userYield;
        strategy.lastYieldAt = block.timestamp;
        accumulatedFees = accumulatedFees + fee;

        emit YieldDistributed(id, strategy.owner, userYield, fee);
    }

    /**
     * @dev Allows a user to withdraw all funds from their strategy
     * @param id The strategy ID
     */
    function withdraw(uint256 id) external nonReentrant {
        Strategy storage strategy = strategies[id];
        require(strategy.owner == msg.sender, "Not strategy owner");
        require(strategy.isActive, "Strategy not active");

        uint256 totalAmount = strategy.totalDeposited + strategy.totalYield;
        require(totalAmount > 0, "Nothing to withdraw");
        require(token.balanceOf(address(this)) >= totalAmount, "Insufficient contract balance");

        strategy.isActive = false;
        strategy.totalDeposited = 0;
        strategy.totalYield = 0;

        token.safeTransfer(msg.sender, totalAmount);
        emit StrategyWithdrawn(id, msg.sender, totalAmount);
    }

    /**
     * @dev Allows the owner to withdraw accumulated fees
     * @param to The address to send fees to
     */
    function withdrawFees(address to) external onlyOwner nonReentrant {
        require(to != address(0), "Cannot withdraw to zero address");
        uint256 amount = accumulatedFees;
        require(amount > 0, "No fees to withdraw");

        accumulatedFees = 0;
        token.safeTransfer(to, amount);
        emit FeesWithdrawn(to, amount);
    }

    /**
     * @dev Updates the fee percentage (capped at MAX_FEE_PERCENTAGE)
     * @param newFeePercentage The new fee percentage
     */
    function updateFeePercentage(uint256 newFeePercentage) external onlyOwner {
        require(newFeePercentage <= MAX_FEE_PERCENTAGE, "Fee too high");
        uint256 oldFee = feePercentage;
        feePercentage = newFeePercentage;
        emit FeePercentageUpdated(oldFee, newFeePercentage);
    }

    /**
     * @dev Emergency function to withdraw funds in case of critical issues
     * @param to The address to send funds to
     * @param amount The amount to withdraw
     */
    function emergencyWithdraw(
        address to,
        uint256 amount
    ) external onlyOwner nonReentrant {
        require(to != address(0), "Cannot withdraw to zero address");
        require(token.balanceOf(address(this)) >= amount, "Insufficient balance");
        token.safeTransfer(to, amount);
        emit EmergencyWithdraw(to, amount);
    }

    /**
     * @dev Returns all strategies owned by a user
     * @param user The user address
     * @return An array of strategy IDs
     */
    function getUserStrategies(address user) external view returns (uint256[] memory) {
        return userStrategies[user];
    }

    /**
     * @dev Returns detailed information about a strategy
     * @param id The strategy ID
     * @return owner The strategy owner
     * @return totalDeposited Total deposited amount
     * @return totalYield Total yield accumulated
     * @return isActive Whether the strategy is active
     * @return riskLevel The risk level of the strategy
     * @return createdAt When the strategy was created
     * @return lastYieldAt When yield was last distributed
     */
    function getStrategyDetails(uint256 id)
    external
    view
    returns (
        address owner,
        uint256 totalDeposited,
        uint256 totalYield,
        bool isActive,
        RiskLevel riskLevel,
        uint256 createdAt,
        uint256 lastYieldAt
    )
    {
        Strategy memory strategy = strategies[id];
        return (
            strategy.owner,
            strategy.totalDeposited,
            strategy.totalYield,
            strategy.isActive,
            strategy.riskLevel,
            strategy.createdAt,
            strategy.lastYieldAt
        );
    }

    /**
     * @dev Returns the AI recommendation for a strategy
     * @param id The strategy ID
     * @return The AI recommendation string
     */
    function getStrategyRecommendation(uint256 id) external view returns (string memory) {
        return strategies[id].recommendationStr;
    }

    /**
     * @dev Returns the total contract balance
     * @return The total USDC balance held by the contract
     */
    function getTotalContractBalance() external view returns (uint256) {
        return token.balanceOf(address(this));
    }
}