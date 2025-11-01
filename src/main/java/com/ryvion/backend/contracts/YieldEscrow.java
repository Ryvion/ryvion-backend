package com.ryvion.backend.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.CustomError;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tuples.generated.Tuple8;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.14.0.
 */
@SuppressWarnings("rawtypes")
public class YieldEscrow extends Contract {
    public static final String BINARY = "60a0604052600a600455348015610014575f5ffd5b50604051611a55380380611a55833981016040819052610033916100cc565b338061005857604051631e4fbdf760e01b81525f600482015260240160405180910390fd5b6100618161007d565b5060018080556001600160a01b039091166080526002556100f9565b5f80546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b5f602082840312156100dc575f5ffd5b81516001600160a01b03811681146100f2575f5ffd5b9392505050565b60805161190c6101495f395f81816102e3015281816103c7015281816105380152818161061d01528181610a2701528181610c5701528181610d1a015281816110300152611189015261190c5ff3fe608060405234801561000f575f5ffd5b5060043610610132575f3560e01c806379f02fd4116100b4578063bdfec18311610079578063bdfec18314610276578063d574ea3d14610289578063d9e3fd64146102b0578063e2bbb158146102b8578063f2fde38b146102cb578063fc0c546a146102de575f5ffd5b806379f02fd4146102035780638da5cb5b14610223578063903041581461024757806395ccea671461025a578063a001ecdd1461026d575f5ffd5b8063587f5ed7116100fa578063587f5ed7146101b657806361b8ce8c146101bf5780636cad3fb0146101c85780636e5d5a62146101db578063715018a6146101fb575f5ffd5b8063164e68de146101365780632e1a7d4d1461014b57806332a50c7f1461015e5780633e9f9e491461018d578063558e44d3146101ae575b5f5ffd5b61014961014436600461140b565b610305565b005b61014961015936600461142b565b61043e565b61017161016c36600461142b565b610686565b6040516101849796959493929190611476565b60405180910390f35b6101a061019b3660046114be565b6107fa565b604051908152602001610184565b6101a0601481565b6101a060035481565b6101a060025481565b6101496101d636600461142b565b610825565b6101ee6101e936600461142b565b6108b2565b6040516101849190611514565b610149610954565b61021661021136600461140b565b610967565b6040516101849190611526565b5f546001600160a01b03165b6040516001600160a01b039091168152602001610184565b610149610255366004611568565b6109cf565b6101496102683660046114be565b610bda565b6101a060045481565b6101a0610284366004611591565b610d91565b61029c61029736600461142b565b610f3d565b604051610184989796959493929190611613565b6101a0611019565b6101496102c636600461166e565b6110a6565b6101496102d936600461140b565b611207565b61022f7f000000000000000000000000000000000000000000000000000000000000000081565b61030d611241565b61031561126d565b6001600160a01b0381166103705760405162461bcd60e51b815260206004820152601f60248201527f43616e6e6f7420776974686472617720746f207a65726f20616464726573730060448201526064015b60405180910390fd5b600354806103b65760405162461bcd60e51b81526020600482015260136024820152724e6f206665657320746f20776974686472617760681b6044820152606401610367565b5f6003556103ee6001600160a01b037f0000000000000000000000000000000000000000000000000000000000000000168383611297565b816001600160a01b03167fc0819c13be868895eb93e40eaceb96de976442fa1d404e5c55f14bb65a8c489a8260405161042991815260200190565b60405180910390a25061043b60018055565b50565b61044661126d565b5f81815260056020526040902080546001600160a01b031633146104a15760405162461bcd60e51b81526020600482015260126024820152712737ba1039ba3930ba32b3bc9037bbb732b960711b6044820152606401610367565b600381015460ff166104c55760405162461bcd60e51b81526004016103679061168e565b5f816002015482600101546104da91906116cf565b90505f81116105215760405162461bcd60e51b81526020600482015260136024820152724e6f7468696e6720746f20776974686472617760681b6044820152606401610367565b6040516370a0823160e01b815230600482015281907f00000000000000000000000000000000000000000000000000000000000000006001600160a01b0316906370a0823190602401602060405180830381865afa158015610585573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906105a991906116e8565b10156105f75760405162461bcd60e51b815260206004820152601d60248201527f496e73756666696369656e7420636f6e74726163742062616c616e63650000006044820152606401610367565b60038201805460ff191690555f6001830181905560028301556106446001600160a01b037f0000000000000000000000000000000000000000000000000000000000000000163383611297565b604051818152339084907fe3cf1c9649f092a027e4573cbf79898735b91072d70a0fb5a6fd8224a8f97acd9060200160405180910390a3505061043b60018055565b5f81815260056020908152604080832081516101008101835281546001600160a01b03168152600182015493810193909352600281015491830191909152600381015460ff161515606083015260048101805484938493849384938493849384939192909160808401916106f9906116ff565b80601f0160208091040260200160405190810160405280929190818152602001828054610725906116ff565b80156107705780601f1061074757610100808354040283529160200191610770565b820191905f5260205f20905b81548152906001019060200180831161075357829003601f168201915b5050509183525050600582015460209091019060ff16600281111561079757610797611442565b60028111156107a8576107a8611442565b8152600682015460208083019190915260079092015460409182015282519183015190830151606084015160a085015160c086015160e090960151949f939e50919c509a509850919650945092505050565b6006602052815f5260405f208181548110610813575f80fd5b905f5260205f20015f91509150505481565b61082d611241565b601481111561086d5760405162461bcd60e51b815260206004820152600c60248201526b08ccaca40e8dede40d0d2ced60a31b6044820152606401610367565b600480549082905560408051828152602081018490527fb27c12a91635e11c22bffa7bd8e0a8735da52b94aaefd7f249776c7590ba7894910160405180910390a15050565b5f8181526005602052604090206004018054606091906108d1906116ff565b80601f01602080910402602001604051908101604052809291908181526020018280546108fd906116ff565b80156109485780601f1061091f57610100808354040283529160200191610948565b820191905f5260205f20905b81548152906001019060200180831161092b57829003601f168201915b50505050509050919050565b61095c611241565b6109655f6112f6565b565b6001600160a01b0381165f9081526006602090815260409182902080548351818402810184019094528084526060939283018282801561094857602002820191905f5260205f20905b8154815260200190600101908083116109b05750505050509050919050565b6109d7611241565b6109df61126d565b5f838152600560205260409020600381015460ff16610a105760405162461bcd60e51b81526004016103679061168e565b6040516370a0823160e01b815230600482015283907f00000000000000000000000000000000000000000000000000000000000000006001600160a01b0316906370a0823190602401602060405180830381865afa158015610a74573d5f5f3e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610a9891906116e8565b1015610ae65760405162461bcd60e51b815260206004820152601a60248201527f496e73756666696369656e74207969656c642062616c616e63650000000000006044820152606401610367565b81831015610b2a5760405162461bcd60e51b81526020600482015260116024820152700a6d8d2e0e0c2ceca40e8dede40d0d2ced607b1b6044820152606401610367565b5f606460045485610b3b9190611737565b610b45919061174e565b90505f610b52828661176d565b9050808360020154610b6491906116cf565b6002840155426007840155600354610b7d9083906116cf565b600355825460408051838152602081018590526001600160a01b039092169188917fc47ce953cf0e0fd061689cc913d628f9fa71a9156bf3d9bfc23bccd0c3c8fdb1910160405180910390a3505050610bd560018055565b505050565b610be2611241565b610bea61126d565b6001600160a01b038216610c405760405162461bcd60e51b815260206004820152601f60248201527f43616e6e6f7420776974686472617720746f207a65726f2061646472657373006044820152606401610367565b6040516370a0823160e01b815230600482015281907f00000000000000000000000000000000000000000000000000000000000000006001600160a01b0316906370a0823190602401602060405180830381865afa158015610ca4573d5f5f3e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610cc891906116e8565b1015610d0d5760405162461bcd60e51b8152602060048201526014602482015273496e73756666696369656e742062616c616e636560601b6044820152606401610367565b610d416001600160a01b037f0000000000000000000000000000000000000000000000000000000000000000168383611297565b816001600160a01b03167f5fafa99d0643513820be26656b45130b01e1c03062e1266bf36f88cbd3bd969582604051610d7c91815260200190565b60405180910390a2610d8d60018055565b5050565b60025460408051610100810182523381525f6020808301829052828401829052600160608401528351601f8801829004820281018201909452868452909392608083019188908890819084018382808284375f92019190915250505090825250602001846002811115610e0657610e06611442565b81524260208083018290526040928301919091525f84815260058252829020835181546001600160a01b0319166001600160a01b03909116178155908301516001820155908201516002820155606082015160038201805460ff191691151591909117905560808201516004820190610e7f90826117df565b5060a082015160058201805460ff19166001836002811115610ea357610ea3611442565b021790555060c082015160068083019190915560e090920151600790910155335f90815260209182526040812080546001818101835591835292909120909101829055600254610ef2916116cf565b600255604051339082907f7ecac7a9595a304e76f6472457df16afbafe24e5649906a2ff819d543fe176db90610f2d9087908a908a9061189a565b60405180910390a3949350505050565b60056020525f9081526040902080546001820154600283015460038401546004850180546001600160a01b03909516959394929360ff90921692610f80906116ff565b80601f0160208091040260200160405190810160405280929190818152602001828054610fac906116ff565b8015610ff75780601f10610fce57610100808354040283529160200191610ff7565b820191905f5260205f20905b815481529060010190602001808311610fda57829003601f168201915b5050505060058301546006840154600790940154929360ff9091169290915088565b6040516370a0823160e01b81523060048201525f907f00000000000000000000000000000000000000000000000000000000000000006001600160a01b0316906370a0823190602401602060405180830381865afa15801561107d573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906110a191906116e8565b905090565b6110ae61126d565b5f82815260056020526040902080546001600160a01b031633146111095760405162461bcd60e51b81526020600482015260126024820152712737ba1039ba3930ba32b3bc9037bbb732b960711b6044820152606401610367565b600381015460ff1661112d5760405162461bcd60e51b81526004016103679061168e565b5f821161117c5760405162461bcd60e51b815260206004820152601d60248201527f416d6f756e74206d7573742062652067726561746572207468616e20300000006044820152606401610367565b6111b16001600160a01b037f000000000000000000000000000000000000000000000000000000000000000016333085611345565b8181600101546111c191906116cf565b6001820155604051828152339084907f8eb219d25fffc7dbe6ebb8243673d13df598b2afb06334ee4fd35b0029db13209060200160405180910390a350610d8d60018055565b61120f611241565b6001600160a01b03811661123857604051631e4fbdf760e01b81525f6004820152602401610367565b61043b816112f6565b5f546001600160a01b031633146109655760405163118cdaa760e01b8152336004820152602401610367565b60026001540361129057604051633ee5aeb560e01b815260040160405180910390fd5b6002600155565b6040516001600160a01b03838116602483015260448201839052610bd591859182169063a9059cbb906064015b604051602081830303815290604052915060e01b6020820180516001600160e01b038381831617835250505050611384565b5f80546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b6040516001600160a01b03848116602483015283811660448301526064820183905261137e9186918216906323b872dd906084016112c4565b50505050565b5f5f60205f8451602086015f885af1806113a3576040513d5f823e3d81fd5b50505f513d915081156113ba5780600114156113c7565b6001600160a01b0384163b155b1561137e57604051635274afe760e01b81526001600160a01b0385166004820152602401610367565b80356001600160a01b0381168114611406575f5ffd5b919050565b5f6020828403121561141b575f5ffd5b611424826113f0565b9392505050565b5f6020828403121561143b575f5ffd5b5035919050565b634e487b7160e01b5f52602160045260245ffd5b6003811061147257634e487b7160e01b5f52602160045260245ffd5b9052565b6001600160a01b03881681526020810187905260408101869052841515606082015260e081016114a96080830186611456565b60a082019390935260c0015295945050505050565b5f5f604083850312156114cf575f5ffd5b6114d8836113f0565b946020939093013593505050565b5f81518084528060208401602086015e5f602082860101526020601f19601f83011685010191505092915050565b602081525f61142460208301846114e6565b602080825282518282018190525f918401906040840190835b8181101561155d57835183526020938401939092019160010161153f565b509095945050505050565b5f5f5f6060848603121561157a575f5ffd5b505081359360208301359350604090920135919050565b5f5f5f604084860312156115a3575f5ffd5b833567ffffffffffffffff8111156115b9575f5ffd5b8401601f810186136115c9575f5ffd5b803567ffffffffffffffff8111156115df575f5ffd5b8660208284010111156115f0575f5ffd5b60209182019450925084013560038110611608575f5ffd5b809150509250925092565b60018060a01b0389168152876020820152866040820152851515606082015261010060808201525f6116496101008301876114e6565b905061165860a0830186611456565b60c082019390935260e001529695505050505050565b5f5f6040838503121561167f575f5ffd5b50508035926020909101359150565b6020808252601390820152725374726174656779206e6f742061637469766560681b604082015260600190565b634e487b7160e01b5f52601160045260245ffd5b808201808211156116e2576116e26116bb565b92915050565b5f602082840312156116f8575f5ffd5b5051919050565b600181811c9082168061171357607f821691505b60208210810361173157634e487b7160e01b5f52602260045260245ffd5b50919050565b80820281158282048414176116e2576116e26116bb565b5f8261176857634e487b7160e01b5f52601260045260245ffd5b500490565b818103818111156116e2576116e26116bb565b634e487b7160e01b5f52604160045260245ffd5b601f821115610bd557805f5260205f20601f840160051c810160208510156117b95750805b601f840160051c820191505b818110156117d8575f81556001016117c5565b5050505050565b815167ffffffffffffffff8111156117f9576117f9611780565b61180d8161180784546116ff565b84611794565b6020601f82116001811461183f575f83156118285750848201515b5f19600385901b1c1916600184901b1784556117d8565b5f84815260208120601f198516915b8281101561186e578785015182556020948501946001909201910161184e565b508482101561188b57868401515f19600387901b60f8161c191681555b50505050600190811b01905550565b6118a48185611456565b60406020820152816040820152818360608301375f818301606090810191909152601f909201601f191601019291505056fea26469706673582212209c9e5eb1679ac02f5730eeb9f7754eaec745588f8fb82ced251cfb805b5ace4964736f6c634300081d0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_MAX_FEE_PERCENTAGE = "MAX_FEE_PERCENTAGE";

    public static final String FUNC_ACCUMULATEDFEES = "accumulatedFees";

    public static final String FUNC_CREATESTRATEGY = "createStrategy";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_DISTRIBUTEYIELD = "distributeYield";

    public static final String FUNC_EMERGENCYWITHDRAW = "emergencyWithdraw";

    public static final String FUNC_FEEPERCENTAGE = "feePercentage";

    public static final String FUNC_GETSTRATEGYDETAILS = "getStrategyDetails";

    public static final String FUNC_GETSTRATEGYRECOMMENDATION = "getStrategyRecommendation";

    public static final String FUNC_GETTOTALCONTRACTBALANCE = "getTotalContractBalance";

    public static final String FUNC_GETUSERSTRATEGIES = "getUserStrategies";

    public static final String FUNC_NEXTID = "nextId";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_STRATEGIES = "strategies";

    public static final String FUNC_TOKEN = "token";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_UPDATEFEEPERCENTAGE = "updateFeePercentage";

    public static final String FUNC_USERSTRATEGIES = "userStrategies";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final String FUNC_WITHDRAWFEES = "withdrawFees";

    public static final CustomError OWNABLEINVALIDOWNER_ERROR = new CustomError("OwnableInvalidOwner", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final CustomError OWNABLEUNAUTHORIZEDACCOUNT_ERROR = new CustomError("OwnableUnauthorizedAccount", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final CustomError REENTRANCYGUARDREENTRANTCALL_ERROR = new CustomError("ReentrancyGuardReentrantCall", 
            Arrays.<TypeReference<?>>asList());
    ;

    public static final CustomError SAFEERC20FAILEDOPERATION_ERROR = new CustomError("SafeERC20FailedOperation", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event EMERGENCYWITHDRAW_EVENT = new Event("EmergencyWithdraw", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event FEEPERCENTAGEUPDATED_EVENT = new Event("FeePercentageUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event FEESWITHDRAWN_EVENT = new Event("FeesWithdrawn", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event STRATEGYCREATED_EVENT = new Event("StrategyCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint8>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event STRATEGYDEPOSIT_EVENT = new Event("StrategyDeposit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event STRATEGYWITHDRAWN_EVENT = new Event("StrategyWithdrawn", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event YIELDDISTRIBUTED_EVENT = new Event("YieldDistributed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected YieldEscrow(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected YieldEscrow(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected YieldEscrow(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected YieldEscrow(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<EmergencyWithdrawEventResponse> getEmergencyWithdrawEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(EMERGENCYWITHDRAW_EVENT, transactionReceipt);
        ArrayList<EmergencyWithdrawEventResponse> responses = new ArrayList<EmergencyWithdrawEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            EmergencyWithdrawEventResponse typedResponse = new EmergencyWithdrawEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.recipient = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static EmergencyWithdrawEventResponse getEmergencyWithdrawEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(EMERGENCYWITHDRAW_EVENT, log);
        EmergencyWithdrawEventResponse typedResponse = new EmergencyWithdrawEventResponse();
        typedResponse.log = log;
        typedResponse.recipient = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<EmergencyWithdrawEventResponse> emergencyWithdrawEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getEmergencyWithdrawEventFromLog(log));
    }

    public Flowable<EmergencyWithdrawEventResponse> emergencyWithdrawEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EMERGENCYWITHDRAW_EVENT));
        return emergencyWithdrawEventFlowable(filter);
    }

    public static List<FeePercentageUpdatedEventResponse> getFeePercentageUpdatedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FEEPERCENTAGEUPDATED_EVENT, transactionReceipt);
        ArrayList<FeePercentageUpdatedEventResponse> responses = new ArrayList<FeePercentageUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FeePercentageUpdatedEventResponse typedResponse = new FeePercentageUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldFee = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newFee = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FeePercentageUpdatedEventResponse getFeePercentageUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FEEPERCENTAGEUPDATED_EVENT, log);
        FeePercentageUpdatedEventResponse typedResponse = new FeePercentageUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.oldFee = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.newFee = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<FeePercentageUpdatedEventResponse> feePercentageUpdatedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getFeePercentageUpdatedEventFromLog(log));
    }

    public Flowable<FeePercentageUpdatedEventResponse> feePercentageUpdatedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FEEPERCENTAGEUPDATED_EVENT));
        return feePercentageUpdatedEventFlowable(filter);
    }

    public static List<FeesWithdrawnEventResponse> getFeesWithdrawnEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FEESWITHDRAWN_EVENT, transactionReceipt);
        ArrayList<FeesWithdrawnEventResponse> responses = new ArrayList<FeesWithdrawnEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FeesWithdrawnEventResponse typedResponse = new FeesWithdrawnEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.recipient = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FeesWithdrawnEventResponse getFeesWithdrawnEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FEESWITHDRAWN_EVENT, log);
        FeesWithdrawnEventResponse typedResponse = new FeesWithdrawnEventResponse();
        typedResponse.log = log;
        typedResponse.recipient = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<FeesWithdrawnEventResponse> feesWithdrawnEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getFeesWithdrawnEventFromLog(log));
    }

    public Flowable<FeesWithdrawnEventResponse> feesWithdrawnEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FEESWITHDRAWN_EVENT));
        return feesWithdrawnEventFlowable(filter);
    }

    public static List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static OwnershipTransferredEventResponse getOwnershipTransferredEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
        OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
        typedResponse.log = log;
        typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getOwnershipTransferredEventFromLog(log));
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public static List<StrategyCreatedEventResponse> getStrategyCreatedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(STRATEGYCREATED_EVENT, transactionReceipt);
        ArrayList<StrategyCreatedEventResponse> responses = new ArrayList<StrategyCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            StrategyCreatedEventResponse typedResponse = new StrategyCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.riskLevel = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.recommendation = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static StrategyCreatedEventResponse getStrategyCreatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(STRATEGYCREATED_EVENT, log);
        StrategyCreatedEventResponse typedResponse = new StrategyCreatedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.riskLevel = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.recommendation = (String) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<StrategyCreatedEventResponse> strategyCreatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getStrategyCreatedEventFromLog(log));
    }

    public Flowable<StrategyCreatedEventResponse> strategyCreatedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(STRATEGYCREATED_EVENT));
        return strategyCreatedEventFlowable(filter);
    }

    public static List<StrategyDepositEventResponse> getStrategyDepositEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(STRATEGYDEPOSIT_EVENT, transactionReceipt);
        ArrayList<StrategyDepositEventResponse> responses = new ArrayList<StrategyDepositEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            StrategyDepositEventResponse typedResponse = new StrategyDepositEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static StrategyDepositEventResponse getStrategyDepositEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(STRATEGYDEPOSIT_EVENT, log);
        StrategyDepositEventResponse typedResponse = new StrategyDepositEventResponse();
        typedResponse.log = log;
        typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<StrategyDepositEventResponse> strategyDepositEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getStrategyDepositEventFromLog(log));
    }

    public Flowable<StrategyDepositEventResponse> strategyDepositEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(STRATEGYDEPOSIT_EVENT));
        return strategyDepositEventFlowable(filter);
    }

    public static List<StrategyWithdrawnEventResponse> getStrategyWithdrawnEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(STRATEGYWITHDRAWN_EVENT, transactionReceipt);
        ArrayList<StrategyWithdrawnEventResponse> responses = new ArrayList<StrategyWithdrawnEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            StrategyWithdrawnEventResponse typedResponse = new StrategyWithdrawnEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.totalAmount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static StrategyWithdrawnEventResponse getStrategyWithdrawnEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(STRATEGYWITHDRAWN_EVENT, log);
        StrategyWithdrawnEventResponse typedResponse = new StrategyWithdrawnEventResponse();
        typedResponse.log = log;
        typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.totalAmount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<StrategyWithdrawnEventResponse> strategyWithdrawnEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getStrategyWithdrawnEventFromLog(log));
    }

    public Flowable<StrategyWithdrawnEventResponse> strategyWithdrawnEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(STRATEGYWITHDRAWN_EVENT));
        return strategyWithdrawnEventFlowable(filter);
    }

    public static List<YieldDistributedEventResponse> getYieldDistributedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(YIELDDISTRIBUTED_EVENT, transactionReceipt);
        ArrayList<YieldDistributedEventResponse> responses = new ArrayList<YieldDistributedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            YieldDistributedEventResponse typedResponse = new YieldDistributedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.yieldPayout = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.fee = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static YieldDistributedEventResponse getYieldDistributedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(YIELDDISTRIBUTED_EVENT, log);
        YieldDistributedEventResponse typedResponse = new YieldDistributedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.yieldPayout = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.fee = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<YieldDistributedEventResponse> yieldDistributedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getYieldDistributedEventFromLog(log));
    }

    public Flowable<YieldDistributedEventResponse> yieldDistributedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(YIELDDISTRIBUTED_EVENT));
        return yieldDistributedEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> MAX_FEE_PERCENTAGE() {
        final Function function = new Function(FUNC_MAX_FEE_PERCENTAGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> accumulatedFees() {
        final Function function = new Function(FUNC_ACCUMULATEDFEES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> createStrategy(String recommendationStr,
            BigInteger riskLevel) {
        final Function function = new Function(
                FUNC_CREATESTRATEGY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(recommendationStr), 
                new org.web3j.abi.datatypes.generated.Uint8(riskLevel)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> deposit(BigInteger id, BigInteger amount) {
        final Function function = new Function(
                FUNC_DEPOSIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> distributeYield(BigInteger id,
            BigInteger yieldAmount, BigInteger minExpectedYield) {
        final Function function = new Function(
                FUNC_DISTRIBUTEYIELD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id), 
                new org.web3j.abi.datatypes.generated.Uint256(yieldAmount), 
                new org.web3j.abi.datatypes.generated.Uint256(minExpectedYield)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> emergencyWithdraw(String to, BigInteger amount) {
        final Function function = new Function(
                FUNC_EMERGENCYWITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> feePercentage() {
        final Function function = new Function(FUNC_FEEPERCENTAGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple7<String, BigInteger, BigInteger, Boolean, BigInteger, BigInteger, BigInteger>> getStrategyDetails(
            BigInteger id) {
        final Function function = new Function(FUNC_GETSTRATEGYDETAILS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple7<String, BigInteger, BigInteger, Boolean, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple7<String, BigInteger, BigInteger, Boolean, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple7<String, BigInteger, BigInteger, Boolean, BigInteger, BigInteger, BigInteger> call(
                            ) throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<String, BigInteger, BigInteger, Boolean, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (Boolean) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> getStrategyRecommendation(BigInteger id) {
        final Function function = new Function(FUNC_GETSTRATEGYRECOMMENDATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getTotalContractBalance() {
        final Function function = new Function(FUNC_GETTOTALCONTRACTBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> getUserStrategies(String user) {
        final Function function = new Function(FUNC_GETUSERSTRATEGIES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> nextId() {
        final Function function = new Function(FUNC_NEXTID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple8<String, BigInteger, BigInteger, Boolean, String, BigInteger, BigInteger, BigInteger>> strategies(
            BigInteger param0) {
        final Function function = new Function(FUNC_STRATEGIES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint8>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple8<String, BigInteger, BigInteger, Boolean, String, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple8<String, BigInteger, BigInteger, Boolean, String, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple8<String, BigInteger, BigInteger, Boolean, String, BigInteger, BigInteger, BigInteger> call(
                            ) throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple8<String, BigInteger, BigInteger, Boolean, String, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (Boolean) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> token() {
        final Function function = new Function(FUNC_TOKEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateFeePercentage(BigInteger newFeePercentage) {
        final Function function = new Function(
                FUNC_UPDATEFEEPERCENTAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(newFeePercentage)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> userStrategies(String param0, BigInteger param1) {
        final Function function = new Function(FUNC_USERSTRATEGIES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.generated.Uint256(param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(BigInteger id) {
        final Function function = new Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawFees(String to) {
        final Function function = new Function(
                FUNC_WITHDRAWFEES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static YieldEscrow load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new YieldEscrow(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static YieldEscrow load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new YieldEscrow(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static YieldEscrow load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new YieldEscrow(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static YieldEscrow load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new YieldEscrow(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<YieldEscrow> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, String _token) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token)));
        return deployRemoteCall(YieldEscrow.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<YieldEscrow> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider, String _token) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token)));
        return deployRemoteCall(YieldEscrow.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<YieldEscrow> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, String _token) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token)));
        return deployRemoteCall(YieldEscrow.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<YieldEscrow> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit, String _token) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token)));
        return deployRemoteCall(YieldEscrow.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    public static void linkLibraries(List<Contract.LinkReference> references) {
        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class EmergencyWithdrawEventResponse extends BaseEventResponse {
        public String recipient;

        public BigInteger amount;
    }

    public static class FeePercentageUpdatedEventResponse extends BaseEventResponse {
        public BigInteger oldFee;

        public BigInteger newFee;
    }

    public static class FeesWithdrawnEventResponse extends BaseEventResponse {
        public String recipient;

        public BigInteger amount;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class StrategyCreatedEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String user;

        public BigInteger riskLevel;

        public String recommendation;
    }

    public static class StrategyDepositEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String user;

        public BigInteger amount;
    }

    public static class StrategyWithdrawnEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String user;

        public BigInteger totalAmount;
    }

    public static class YieldDistributedEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String user;

        public BigInteger yieldPayout;

        public BigInteger fee;
    }
}
