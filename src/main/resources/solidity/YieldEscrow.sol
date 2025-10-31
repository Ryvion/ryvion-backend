// SPDX-License-Identifier: MIT
pragma solidity ^0.8.19;

import "./contracts/token/ERC20/utils/SafeERC20.sol";
import "./contracts/token/ERC20/IERC20.sol";
import "./contracts/utils/ReentrancyGuard.sol";
import "./contracts/access/Ownable.sol";

contract YieldEscrow is Ownable, ReentrancyGuard {
    using SafeERC20 for IERC20;

    struct Strategy {
        address user;
        uint256 amount;
        bytes32 recommendation;
        bool executed;
        bool isActive;
    }

    uint256 public constant PLATFORM_FEE_BPS = 20;
    uint256 public constant BPS_DENOM = 10000;

    IERC20 public immutable token;
    uint256 public nextId;
    uint256 public accumulatedFees;

    mapping(uint256 => Strategy) public strategies;
    mapping(address => uint256[]) public userStrategies;

    event StrategyCreated(uint256 indexed id, address indexed user, uint256 amount, bytes32 recommendationHash);
    event PaymentExecuted(uint256 indexed id, address indexed user, uint256 payout, uint256 fee);
    event StrategyWithdrawn(uint256 indexed id, address indexed user, uint256 refundedAmount);
    event FeesWithdrawn(address indexed owner, uint256 amount);
    event AllowanceIncreased(address indexed user, uint256 amount);

    constructor(address _token, address _owner) Ownable(_owner) {
        require(_token != address(0), "token zero");
        token = IERC20(_token);
        _transferOwnership(_owner); 
    }

    function createStrategy(string calldata recommendationStr, uint256 amount) external nonReentrant {
        require(amount > 0, "Amount must be > 0");
        require(token.allowance(msg.sender, address(this)) >= amount, "ERC20: Insufficient allowance");
        token.safeTransferFrom(msg.sender, address(this), amount);

        bytes32 recHash = keccak256(abi.encodePacked(recommendationStr));

        strategies[nextId] = Strategy({
            user: msg.sender,
            amount: amount,
            recommendation: recHash,
            executed: false,
            isActive: true
        });

        userStrategies[msg.sender].push(nextId);

        emit StrategyCreated(nextId, msg.sender, amount, recHash);
        nextId++;
    }

    function createStrategyWithPermit(
        string calldata recommendationStr,
        uint256 amount,
        uint256 permitAmount
    ) external nonReentrant {
        require(amount > 0 && permitAmount >= amount, "Invalid amounts");
        require(token.allowance(msg.sender, address(this)) >= permitAmount,
         "ERC20: Insufficient allowance");

        token.safeTransferFrom(msg.sender, address(this), amount);

        bytes32 recHash = keccak256(abi.encodePacked(recommendationStr));

        strategies[nextId] = Strategy({
            user: msg.sender,
            amount: amount,
            recommendation: recHash,
            executed: false,
            isActive: true
        });

        userStrategies[msg.sender].push(nextId);

        emit StrategyCreated(nextId, msg.sender, amount, recHash);
        nextId++;
    }

    function increaseAllowance(uint256 amount) external {
        require(amount > 0, "Amount must be > 0");
        token.safeIncreaseAllowance(address(this), amount);
        emit AllowanceIncreased(msg.sender, amount);
    }

    function getUserAllowance(address user) external view returns (uint256) {
        return token.allowance(user, address(this));
    }

    function getUserActiveStrategies(address user) external view returns (uint256[] memory) {
        return userStrategies[user];
    }

    function getUserStrategyCount(address user) external view returns (uint256) {
        return userStrategies[user].length;
    }

    function withdraw(uint256 id) external nonReentrant {
        Strategy storage s = strategies[id];
        require(s.user == msg.sender, "Not your strategy");
        require(!s.executed, "Already executed");
        require(s.isActive, "Strategy is not active");
        require(s.amount > 0, "Nothing to withdraw");

        uint256 amount = s.amount;

        s.executed = true;
        s.isActive = false;
        s.amount = 0;
        address recipient = s.user;
        s.user = address(0);

        token.safeTransfer(recipient, amount);
        emit StrategyWithdrawn(id, recipient, amount);
    }

    function executeStrategy(uint256 id, uint256 yieldAmount) external onlyOwner nonReentrant {
        Strategy storage s = strategies[id];
        require(s.user != address(0), "Strategy does not exist");
        require(!s.executed, "Already executed");
        require(s.isActive, "Strategy is not active");
        require(yieldAmount > 0, "Yield amount must be > 0");

        uint256 fee = (yieldAmount * PLATFORM_FEE_BPS) / BPS_DENOM;
        uint256 yieldPayout = yieldAmount - fee;
        uint256 totalPayout = s.amount + yieldPayout;

        s.executed = true;
        s.isActive = false;
        s.amount = 0;
        address recipient = s.user;
        s.user = address(0);
        accumulatedFees += fee;

        token.safeTransfer(recipient, totalPayout);
        emit PaymentExecuted(id, recipient, totalPayout, fee);
    }

    function withdrawFees(address to) external onlyOwner nonReentrant {
        require(to != address(0), "zero address");
        uint256 amount = accumulatedFees;
        require(amount > 0, "no fees");

        accumulatedFees = 0;
        token.safeTransfer(to, amount);
        emit FeesWithdrawn(to, amount);
    }

    function getContractTokenBalance() external view returns (uint256) {
        return token.balanceOf(address(this));
    }

    function getStrategy(uint256 id) external view returns (
        address user,
        uint256 amount,
        bytes32 recommendationHash,
        bool executed,
        bool isActive
    ) {
        Strategy storage s = strategies[id];
        return (s.user, s.amount, s.recommendation, s.executed, s.isActive);
    }

    function getStrategyStats() external view returns (
        uint256 totalStrategies,
        uint256 activeStrategies,
        uint256 executedStrategies,
        uint256 totalVolume,
        uint256 totalFees
    ) {
        totalStrategies = nextId;
        
        uint256 activeCount = 0;
        uint256 executedCount = 0;
        uint256 volume = 0;
        
        for (uint256 i = 0; i < nextId; i++) {
            Strategy storage s = strategies[i];
            if (s.user != address(0)) {
                volume += s.amount;
                if (s.isActive && !s.executed) {
                    activeCount++;
                }
                if (s.executed) {
                    executedCount++;
                }
            }
        }
        
        return (totalStrategies, activeCount, executedCount, volume, accumulatedFees);
    }

    //Emergency restoration of tokens (by owner)
    function emergencyWithdraw(address tokenAddress, uint256 amount) external onlyOwner {
        require(tokenAddress != address(0), "Invalid token address");
        IERC20(tokenAddress).safeTransfer(owner(), amount);
    }

    function isOwner() external view returns (bool) {
        return msg.sender == owner();
    }
}