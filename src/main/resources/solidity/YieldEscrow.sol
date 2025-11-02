// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

import "./contracts/token/ERC20/utils/SafeERC20.sol";
import "./contracts/token/ERC20/IERC20.sol";
import "./contracts/utils/ReentrancyGuard.sol";
import "./contracts/access/Ownable.sol";

/**
 * @title YieldEscrow
 * @dev Enhanced version for Ryvion: AI-powered RWA yield platform on Arc.
 * Improvements: Autonomous AI yield distribution via Chainlink oracle; partial withdraws.
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
    event YieldDistributed(uint256 indexed id, address indexed user, uint256 yieldPayout, uint256 fee, string dataSource); // Added source for transparency
    event StrategyWithdrawn(uint256 indexed id, address indexed user, uint256 totalAmount);
    event PartialWithdrawn(uint256 indexed id, address indexed user, uint256 amount);
    event FeesWithdrawn(address indexed recipient, uint256 amount);
    event FeePercentageUpdated(uint256 oldFee, uint256 newFee);
    event EmergencyWithdraw(address indexed recipient, uint256 amount);

    /**
     * @dev Constructor initializes the contract with USDC token and Chainlink oracle
     * @param _token The address of the USDC token contract
     */
    constructor(address _token) Ownable(msg.sender) {
        token = IERC20(_token);
        nextId = 1;
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
     * @dev Distributes yield (AI agent calls with real data validated off-chain)
     * @param id The strategy ID
     * @param yieldAmount The yield amount (from real data)
     * @param minExpectedYield Min for slippage
     * @param dataSource Proof string (e.g., "FRED Treasury 5.3% 2025-11-01")
     */
    function distributeYield(
        uint256 id,
        uint256 yieldAmount,
        uint256 minExpectedYield,
        string calldata dataSource
    ) external nonReentrant {  // No modifier—trust agent
        Strategy storage strategy = strategies[id];
        require(strategy.isActive, "Strategy not active");
        require(yieldAmount >= minExpectedYield, "Yield below min expected");
        require(yieldAmount > 0, "Yield must be > 0");
        require(bytes(dataSource).length > 0, "Data source required");

        token.safeTransferFrom(msg.sender, address(this), yieldAmount);

        uint256 fee = (yieldAmount * feePercentage) / 100;
        uint256 userYield = yieldAmount - fee;

        strategy.totalYield += userYield;
        strategy.lastYieldAt = block.timestamp;
        accumulatedFees += fee;

        emit YieldDistributed(id, strategy.owner, userYield, fee, dataSource);
    }

    /**
     * @dev Allows a user to withdraw all funds from their strategy (full close)
     * @param id The strategy ID
     */
    function withdraw(uint256 id) external nonReentrant {
        Strategy storage strategy = strategies[id];
        require(strategy.owner == msg.sender, "Not strategy owner");
        require(strategy.isActive, "Strategy not active");

        uint256 totalAmount = strategy.totalDeposited + strategy.totalYield;
        require(totalAmount > 0, "Nothing to withdraw");
        require(token.balanceOf(address(this)) >= totalAmount, "Low contract balance: Insufficient funds");

        strategy.isActive = false;
        strategy.totalDeposited = 0;
        strategy.totalYield = 0;

        token.safeTransfer(msg.sender, totalAmount);
        emit StrategyWithdrawn(id, msg.sender, totalAmount);
    }

    /**
     * @dev Allows a user to partially withdraw from their strategy
     * @param id The strategy ID
     * @param amount The amount to withdraw (from deposited + yield)
     */
    function partialWithdraw(uint256 id, uint256 amount) external nonReentrant {
        Strategy storage strategy = strategies[id];
        require(strategy.owner == msg.sender, "Not strategy owner");
        require(strategy.isActive, "Strategy not active");
        require(amount > 0, "Amount must be > 0");

        uint256 totalBalance = strategy.totalDeposited + strategy.totalYield;
        require(totalBalance >= amount, "Requested > available balance");
        require(token.balanceOf(address(this)) >= amount, "Low contract balance: Insufficient funds");

        // Pro-rata: Withdraw from yield first, then principal
        if (amount <= strategy.totalYield) {
            strategy.totalYield -= amount;
        } else {
            uint256 fromYield = strategy.totalYield;
            uint256 fromDeposit = amount - fromYield;
            strategy.totalYield = 0;
            strategy.totalDeposited -= fromDeposit;
        }

        token.safeTransfer(msg.sender, amount);
        emit PartialWithdrawn(id, msg.sender, amount);
    }

    /**
     * @dev View: Get max safe withdraw amount for a strategy (accounts for contract balance)
     * @param id The strategy ID
     * @return available The max withdrawable amount
     */
    function getAvailableWithdraw(uint256 id) external view returns (uint256 available) {
        Strategy memory strategy = strategies[id];
        if (!strategy.isActive) return 0;
        uint256 strategyBalance = strategy.totalDeposited + strategy.totalYield;
        uint256 contractBalance = token.balanceOf(address(this));
        return strategyBalance > contractBalance ? contractBalance : strategyBalance;
    }

    /**
     * @dev Allows the owner to withdraw accumulated fees
     * @param to The address to send fees to
     */
    function withdrawFees(address to) external onlyOwner nonReentrant {
        require(to != address(0), "Cannot withdraw to zero address");
        uint256 amount = accumulatedFees;
        require(amount > 0, "No fees to withdraw");
        require(token.balanceOf(address(this)) >= amount, "Low contract balance: Insufficient funds");

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
        require(token.balanceOf(address(this)) >= amount, "Low contract balance: Insufficient funds");
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