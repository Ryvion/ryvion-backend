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
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple5;
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
    public static final String BINARY = "60a060405234801561000f575f5ffd5b506040516119ff3803806119ff83398101604081905261002e91610135565b806001600160a01b03811661005d57604051631e4fbdf760e01b81525f60048201526024015b60405180910390fd5b610066816100cb565b50600180556001600160a01b0382166100ae5760405162461bcd60e51b815260206004820152600a602482015269746f6b656e207a65726f60b01b6044820152606401610054565b6001600160a01b0382166080526100c4816100cb565b5050610166565b5f80546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b80516001600160a01b0381168114610130575f5ffd5b919050565b5f5f60408385031215610146575f5ffd5b61014f8361011a565b915061015d6020840161011a565b90509250929050565b60805161183b6101c45f395f81816103d80152818161049d01528181610564015281816106630152818161085b015281816108e9015281816109f301528181610abf01528181610e9f01528181611073015261113f015261183b5ff3fe608060405234801561000f575f5ffd5b5060043610610153575f3560e01c8063715018a6116100bf578063d88490d011610079578063d88490d014610355578063f101e82b1461037d578063f1eb17f614610390578063f2fde38b146103c0578063fc0c546a146103d3578063fe67489d146103fa575f5ffd5b8063715018a61461022d5780638da5cb5b146102355780638f32d59b1461025957806395ccea6714610276578063cfc0cc3414610289578063d574ea3d1461030b575f5ffd5b80633e9f9e49116101105780633e9f9e49146101e4578063539aa77f146101f7578063587f5ed7146101ff57806361b8ce8c146102085780636637e38c146102115780636b0ae15e1461021a575f5ffd5b80630bc65c0f146101575780630c6ddc0d1461018057806311e330b2146101a1578063164e68de146101b65780632e1a7d4d146101c9578063317d9453146101dc575b5f5ffd5b61016a6101653660046115bc565b61040d565b60405161017791906115dc565b60405180910390f35b61019361018e3660046115bc565b610476565b604051908152602001610177565b6101b46101af36600461161e565b61050e565b005b6101b46101c43660046115bc565b6105c3565b6101b46101d736600461161e565b6106da565b6101936108d2565b6101936101f2366004611635565b61095f565b610193601481565b61019360035481565b61019360025481565b61019361271081565b6101b46102283660046116a2565b61098a565b6101b4610c1c565b5f546001600160a01b03165b6040516001600160a01b039091168152602001610177565b5f546001600160a01b031633146040519015158152602001610177565b6101b4610284366004611635565b610c2f565b6102d561029736600461161e565b5f9081526004602052604090208054600182015460028301546003909301546001600160a01b039092169390929160ff808216926101009092041690565b604080516001600160a01b039096168652602086019490945292840191909152151560608301521515608082015260a001610177565b6102d561031936600461161e565b60046020525f908152604090208054600182015460028301546003909301546001600160a01b0390921692909160ff8082169161010090041685565b6101936103633660046115bc565b6001600160a01b03165f9081526005602052604090205490565b6101b461038b3660046116ea565b610cae565b610398610f1a565b604080519586526020860194909452928401919091526060830152608082015260a001610177565b6101b46103ce3660046115bc565b610fc7565b6102417f000000000000000000000000000000000000000000000000000000000000000081565b6101b461040836600461170a565b611001565b6001600160a01b0381165f9081526005602090815260409182902080548351818402810184019094528084526060939283018282801561046a57602002820191905f5260205f20905b815481526020019060010190808311610456575b50505050509050919050565b604051636eb1769f60e11b81526001600160a01b0382811660048301523060248301525f917f00000000000000000000000000000000000000000000000000000000000000009091169063dd62ed3e90604401602060405180830381865afa1580156104e4573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906105089190611757565b92915050565b5f81116105575760405162461bcd60e51b81526020600482015260126024820152710416d6f756e74206d757374206265203e20360741b60448201526064015b60405180910390fd5b61058b6001600160a01b037f000000000000000000000000000000000000000000000000000000000000000016308361129d565b60405181815233907fac1a4dcc7a2e06cc76c0fda51fa77de8c9d0a73d0982a5e55400c9942aa523ce9060200160405180910390a250565b6105cb611324565b6105d3611350565b6001600160a01b0381166106185760405162461bcd60e51b815260206004820152600c60248201526b7a65726f206164647265737360a01b604482015260640161054e565b600354806106525760405162461bcd60e51b81526020600482015260076024820152666e6f206665657360c81b604482015260640161054e565b5f60035561068a6001600160a01b037f000000000000000000000000000000000000000000000000000000000000000016838361137a565b816001600160a01b03167fc0819c13be868895eb93e40eaceb96de976442fa1d404e5c55f14bb65a8c489a826040516106c591815260200190565b60405180910390a2506106d760018055565b50565b6106e2611350565b5f81815260046020526040902080546001600160a01b0316331461073c5760405162461bcd60e51b81526020600482015260116024820152704e6f7420796f757220737472617465677960781b604482015260640161054e565b600381015460ff16156107845760405162461bcd60e51b815260206004820152601060248201526f105b1c9958591e48195e1958dd5d195960821b604482015260640161054e565b6003810154610100900460ff166107d65760405162461bcd60e51b81526020600482015260166024820152755374726174656779206973206e6f742061637469766560501b604482015260640161054e565b5f81600101541161081f5760405162461bcd60e51b81526020600482015260136024820152724e6f7468696e6720746f20776974686472617760681b604482015260640161054e565b6001808201805460038401805461ffff19169093179092555f905581546001600160a01b0319811683556001600160a01b0390811690610882907f000000000000000000000000000000000000000000000000000000000000000016828461137a565b806001600160a01b0316847fe3cf1c9649f092a027e4573cbf79898735b91072d70a0fb5a6fd8224a8f97acd846040516108be91815260200190565b60405180910390a35050506106d760018055565b6040516370a0823160e01b81523060048201525f907f00000000000000000000000000000000000000000000000000000000000000006001600160a01b0316906370a0823190602401602060405180830381865afa158015610936573d5f5f3e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061095a9190611757565b905090565b6005602052815f5260405f208181548110610978575f80fd5b905f5260205f20015f91509150505481565b610992611350565b5f81116109d65760405162461bcd60e51b81526020600482015260126024820152710416d6f756e74206d757374206265203e20360741b604482015260640161054e565b604051636eb1769f60e11b815233600482015230602482015281907f00000000000000000000000000000000000000000000000000000000000000006001600160a01b03169063dd62ed3e90604401602060405180830381865afa158015610a40573d5f5f3e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610a649190611757565b1015610ab25760405162461bcd60e51b815260206004820152601d60248201527f45524332303a20496e73756666696369656e7420616c6c6f77616e6365000000604482015260640161054e565b610ae76001600160a01b037f0000000000000000000000000000000000000000000000000000000000000000163330846113d9565b5f8383604051602001610afb92919061176e565b60408051808303601f19018152828252805160209182012060a084018352338085528285018781528585018381525f60608801818152600160808a0181815260028054855260048a528a85209b518c546001600160a01b0319166001600160a01b03909116178c5595518b83015593518a860155905160039099018054935161ffff1990941699151561ff0019169990991761010093151593909302929092179097558287526005855285872082548154928301825590885296859020019590955593548351878152928301829052909450917f3494d3d67b44d6b0cc310f121b927e4874b8b6cf678b546268042fd5e0d2c96c910160405180910390a360028054905f610c0883611791565b919050555050610c1760018055565b505050565b610c24611324565b610c2d5f611412565b565b610c37611324565b6001600160a01b038216610c855760405162461bcd60e51b8152602060048201526015602482015274496e76616c696420746f6b656e206164647265737360581b604482015260640161054e565b610caa610c995f546001600160a01b031690565b6001600160a01b038416908361137a565b5050565b610cb6611324565b610cbe611350565b5f82815260046020526040902080546001600160a01b0316610d225760405162461bcd60e51b815260206004820152601760248201527f537472617465677920646f6573206e6f74206578697374000000000000000000604482015260640161054e565b600381015460ff1615610d6a5760405162461bcd60e51b815260206004820152601060248201526f105b1c9958591e48195e1958dd5d195960821b604482015260640161054e565b6003810154610100900460ff16610dbc5760405162461bcd60e51b81526020600482015260166024820152755374726174656779206973206e6f742061637469766560501b604482015260640161054e565b5f8211610e0b5760405162461bcd60e51b815260206004820152601860248201527f5969656c6420616d6f756e74206d757374206265203e20300000000000000000604482015260640161054e565b5f612710610e1a6014856117a9565b610e2491906117c0565b90505f610e3182856117df565b90505f818460010154610e4491906117f2565b6003808601805461ffff191660019081179091555f90870181905586546001600160a01b03198116885582549394506001600160a01b03169286929190610e8c9084906117f2565b90915550610ec690506001600160a01b037f000000000000000000000000000000000000000000000000000000000000000016828461137a565b60408051838152602081018690526001600160a01b0383169189917fcd19bb5d0157296d4f7382afb5c2308f7413979d682b76af1d187c8bd78b1c35910160405180910390a35050505050610caa60018055565b6002545f808080808080805b600254811015610fb4575f81815260046020526040902080546001600160a01b031615610fab576001810154610f5c90846117f2565b6003820154909350610100900460ff168015610f7d5750600381015460ff16155b15610f905784610f8c81611791565b9550505b600381015460ff1615610fab5783610fa781611791565b9450505b50600101610f26565b5060035497989297919650945092509050565b610fcf611324565b6001600160a01b038116610ff857604051631e4fbdf760e01b81525f600482015260240161054e565b6106d781611412565b611009611350565b5f821180156110185750818110155b6110565760405162461bcd60e51b815260206004820152600f60248201526e496e76616c696420616d6f756e747360881b604482015260640161054e565b604051636eb1769f60e11b815233600482015230602482015281907f00000000000000000000000000000000000000000000000000000000000000006001600160a01b03169063dd62ed3e90604401602060405180830381865afa1580156110c0573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906110e49190611757565b10156111325760405162461bcd60e51b815260206004820152601d60248201527f45524332303a20496e73756666696369656e7420616c6c6f77616e6365000000604482015260640161054e565b6111676001600160a01b037f0000000000000000000000000000000000000000000000000000000000000000163330856113d9565b5f848460405160200161117b92919061176e565b60408051808303601f19018152828252805160209182012060a084018352338085528285018881528585018381525f60608801818152600160808a0181815260028054855260048a528a85209b518c546001600160a01b0319166001600160a01b03909116178c5595518b83015593518a860155905160039099018054935161ffff1990941699151561ff0019169990991761010093151593909302929092179097558287526005855285872082548154928301825590885296859020019590955593548351888152928301829052909450917f3494d3d67b44d6b0cc310f121b927e4874b8b6cf678b546268042fd5e0d2c96c910160405180910390a360028054905f61128883611791565b91905055505061129760018055565b50505050565b604051636eb1769f60e11b81523060048201526001600160a01b0383811660248301525f919085169063dd62ed3e90604401602060405180830381865afa1580156112ea573d5f5f3e3d5ffd5b505050506040513d601f19601f8201168201806040525081019061130e9190611757565b9050611297848461131f85856117f2565b611461565b5f546001600160a01b03163314610c2d5760405163118cdaa760e01b815233600482015260240161054e565b60026001540361137357604051633ee5aeb560e01b815260040160405180910390fd5b6002600155565b6040516001600160a01b03838116602483015260448201839052610c1791859182169063a9059cbb906064015b604051602081830303815290604052915060e01b6020820180516001600160e01b0383818316178352505050506114ec565b6040516001600160a01b0384811660248301528381166044830152606482018390526112979186918216906323b872dd906084016113a7565b5f80546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b604080516001600160a01b038416602482015260448082018490528251808303909101815260649091019091526020810180516001600160e01b031663095ea7b360e01b1790526114b28482611558565b611297576040516001600160a01b0384811660248301525f60448301526114e691869182169063095ea7b3906064016113a7565b61129784825b5f5f60205f8451602086015f885af18061150b576040513d5f823e3d81fd5b50505f513d9150811561152257806001141561152f565b6001600160a01b0384163b155b1561129757604051635274afe760e01b81526001600160a01b038516600482015260240161054e565b5f5f5f5f60205f8651602088015f8a5af192503d91505f519050828015611597575081156115895780600114611597565b5f866001600160a01b03163b115b9695505050505050565b80356001600160a01b03811681146115b7575f5ffd5b919050565b5f602082840312156115cc575f5ffd5b6115d5826115a1565b9392505050565b602080825282518282018190525f918401906040840190835b818110156116135783518352602093840193909201916001016115f5565b509095945050505050565b5f6020828403121561162e575f5ffd5b5035919050565b5f5f60408385031215611646575f5ffd5b61164f836115a1565b946020939093013593505050565b5f5f83601f84011261166d575f5ffd5b50813567ffffffffffffffff811115611684575f5ffd5b60208301915083602082850101111561169b575f5ffd5b9250929050565b5f5f5f604084860312156116b4575f5ffd5b833567ffffffffffffffff8111156116ca575f5ffd5b6116d68682870161165d565b909790965060209590950135949350505050565b5f5f604083850312156116fb575f5ffd5b50508035926020909101359150565b5f5f5f5f6060858703121561171d575f5ffd5b843567ffffffffffffffff811115611733575f5ffd5b61173f8782880161165d565b90989097506020870135966040013595509350505050565b5f60208284031215611767575f5ffd5b5051919050565b818382375f9101908152919050565b634e487b7160e01b5f52601160045260245ffd5b5f600182016117a2576117a261177d565b5060010190565b80820281158282048414176105085761050861177d565b5f826117da57634e487b7160e01b5f52601260045260245ffd5b500490565b818103818111156105085761050861177d565b808201808211156105085761050861177d56fea2646970667358221220862a32218265271c5aa99e6336d193470385cbff3f8469cbaca2a8881fabcabb64736f6c634300081d0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_BPS_DENOM = "BPS_DENOM";

    public static final String FUNC_PLATFORM_FEE_BPS = "PLATFORM_FEE_BPS";

    public static final String FUNC_ACCUMULATEDFEES = "accumulatedFees";

    public static final String FUNC_CREATESTRATEGY = "createStrategy";

    public static final String FUNC_CREATESTRATEGYWITHPERMIT = "createStrategyWithPermit";

    public static final String FUNC_EMERGENCYWITHDRAW = "emergencyWithdraw";

    public static final String FUNC_EXECUTESTRATEGY = "executeStrategy";

    public static final String FUNC_GETCONTRACTTOKENBALANCE = "getContractTokenBalance";

    public static final String FUNC_GETSTRATEGY = "getStrategy";

    public static final String FUNC_GETSTRATEGYSTATS = "getStrategyStats";

    public static final String FUNC_GETUSERACTIVESTRATEGIES = "getUserActiveStrategies";

    public static final String FUNC_GETUSERALLOWANCE = "getUserAllowance";

    public static final String FUNC_GETUSERSTRATEGYCOUNT = "getUserStrategyCount";

    public static final String FUNC_INCREASEALLOWANCE = "increaseAllowance";

    public static final String FUNC_ISOWNER = "isOwner";

    public static final String FUNC_NEXTID = "nextId";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_STRATEGIES = "strategies";

    public static final String FUNC_TOKEN = "token";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

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

    public static final Event ALLOWANCEINCREASED_EVENT = new Event("AllowanceIncreased", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event FEESWITHDRAWN_EVENT = new Event("FeesWithdrawn", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event PAYMENTEXECUTED_EVENT = new Event("PaymentExecuted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event STRATEGYCREATED_EVENT = new Event("StrategyCreated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event STRATEGYWITHDRAWN_EVENT = new Event("StrategyWithdrawn", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
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

    public static List<AllowanceIncreasedEventResponse> getAllowanceIncreasedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ALLOWANCEINCREASED_EVENT, transactionReceipt);
        ArrayList<AllowanceIncreasedEventResponse> responses = new ArrayList<AllowanceIncreasedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AllowanceIncreasedEventResponse typedResponse = new AllowanceIncreasedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static AllowanceIncreasedEventResponse getAllowanceIncreasedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ALLOWANCEINCREASED_EVENT, log);
        AllowanceIncreasedEventResponse typedResponse = new AllowanceIncreasedEventResponse();
        typedResponse.log = log;
        typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<AllowanceIncreasedEventResponse> allowanceIncreasedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAllowanceIncreasedEventFromLog(log));
    }

    public Flowable<AllowanceIncreasedEventResponse> allowanceIncreasedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ALLOWANCEINCREASED_EVENT));
        return allowanceIncreasedEventFlowable(filter);
    }

    public static List<FeesWithdrawnEventResponse> getFeesWithdrawnEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FEESWITHDRAWN_EVENT, transactionReceipt);
        ArrayList<FeesWithdrawnEventResponse> responses = new ArrayList<FeesWithdrawnEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FeesWithdrawnEventResponse typedResponse = new FeesWithdrawnEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FeesWithdrawnEventResponse getFeesWithdrawnEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FEESWITHDRAWN_EVENT, log);
        FeesWithdrawnEventResponse typedResponse = new FeesWithdrawnEventResponse();
        typedResponse.log = log;
        typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
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

    public static List<PaymentExecutedEventResponse> getPaymentExecutedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PAYMENTEXECUTED_EVENT, transactionReceipt);
        ArrayList<PaymentExecutedEventResponse> responses = new ArrayList<PaymentExecutedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PaymentExecutedEventResponse typedResponse = new PaymentExecutedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.payout = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.fee = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PaymentExecutedEventResponse getPaymentExecutedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PAYMENTEXECUTED_EVENT, log);
        PaymentExecutedEventResponse typedResponse = new PaymentExecutedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.payout = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.fee = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<PaymentExecutedEventResponse> paymentExecutedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPaymentExecutedEventFromLog(log));
    }

    public Flowable<PaymentExecutedEventResponse> paymentExecutedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PAYMENTEXECUTED_EVENT));
        return paymentExecutedEventFlowable(filter);
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
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.recommendationHash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
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
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.recommendationHash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
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

    public static List<StrategyWithdrawnEventResponse> getStrategyWithdrawnEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(STRATEGYWITHDRAWN_EVENT, transactionReceipt);
        ArrayList<StrategyWithdrawnEventResponse> responses = new ArrayList<StrategyWithdrawnEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            StrategyWithdrawnEventResponse typedResponse = new StrategyWithdrawnEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.refundedAmount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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
        typedResponse.refundedAmount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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

    public RemoteFunctionCall<BigInteger> BPS_DENOM() {
        final Function function = new Function(FUNC_BPS_DENOM, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> PLATFORM_FEE_BPS() {
        final Function function = new Function(FUNC_PLATFORM_FEE_BPS, 
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
            BigInteger amount) {
        final Function function = new Function(
                FUNC_CREATESTRATEGY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(recommendationStr), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> createStrategyWithPermit(String recommendationStr,
            BigInteger amount, BigInteger permitAmount) {
        final Function function = new Function(
                FUNC_CREATESTRATEGYWITHPERMIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(recommendationStr), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Uint256(permitAmount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> emergencyWithdraw(String tokenAddress,
            BigInteger amount) {
        final Function function = new Function(
                FUNC_EMERGENCYWITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, tokenAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> executeStrategy(BigInteger id,
            BigInteger yieldAmount) {
        final Function function = new Function(
                FUNC_EXECUTESTRATEGY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id), 
                new org.web3j.abi.datatypes.generated.Uint256(yieldAmount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getContractTokenBalance() {
        final Function function = new Function(FUNC_GETCONTRACTTOKENBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Tuple5<String, BigInteger, byte[], Boolean, Boolean>> getStrategy(
            BigInteger id) {
        final Function function = new Function(FUNC_GETSTRATEGY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple5<String, BigInteger, byte[], Boolean, Boolean>>(function,
                new Callable<Tuple5<String, BigInteger, byte[], Boolean, Boolean>>() {
                    @Override
                    public Tuple5<String, BigInteger, byte[], Boolean, Boolean> call() throws
                            Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, BigInteger, byte[], Boolean, Boolean>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (byte[]) results.get(2).getValue(), 
                                (Boolean) results.get(3).getValue(), 
                                (Boolean) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>> getStrategyStats(
            ) {
        final Function function = new Function(FUNC_GETSTRATEGYSTATS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>(function,
                new Callable<Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> call()
                            throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, BigInteger, BigInteger, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteFunctionCall<List> getUserActiveStrategies(String user) {
        final Function function = new Function(FUNC_GETUSERACTIVESTRATEGIES, 
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

    public RemoteFunctionCall<BigInteger> getUserAllowance(String user) {
        final Function function = new Function(FUNC_GETUSERALLOWANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getUserStrategyCount(String user) {
        final Function function = new Function(FUNC_GETUSERSTRATEGYCOUNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> increaseAllowance(BigInteger amount) {
        final Function function = new Function(
                FUNC_INCREASEALLOWANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isOwner() {
        final Function function = new Function(FUNC_ISOWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
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

    public RemoteFunctionCall<Tuple5<String, BigInteger, byte[], Boolean, Boolean>> strategies(
            BigInteger param0) {
        final Function function = new Function(FUNC_STRATEGIES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple5<String, BigInteger, byte[], Boolean, Boolean>>(function,
                new Callable<Tuple5<String, BigInteger, byte[], Boolean, Boolean>>() {
                    @Override
                    public Tuple5<String, BigInteger, byte[], Boolean, Boolean> call() throws
                            Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, BigInteger, byte[], Boolean, Boolean>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (byte[]) results.get(2).getValue(), 
                                (Boolean) results.get(3).getValue(), 
                                (Boolean) results.get(4).getValue());
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
            ContractGasProvider contractGasProvider, String _token, String _owner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.Address(160, _owner)));
        return deployRemoteCall(YieldEscrow.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<YieldEscrow> deploy(Web3j web3j, TransactionManager transactionManager,
            ContractGasProvider contractGasProvider, String _token, String _owner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.Address(160, _owner)));
        return deployRemoteCall(YieldEscrow.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<YieldEscrow> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, String _token, String _owner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.Address(160, _owner)));
        return deployRemoteCall(YieldEscrow.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<YieldEscrow> deploy(Web3j web3j, TransactionManager transactionManager,
            BigInteger gasPrice, BigInteger gasLimit, String _token, String _owner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _token), 
                new org.web3j.abi.datatypes.Address(160, _owner)));
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

    public static class AllowanceIncreasedEventResponse extends BaseEventResponse {
        public String user;

        public BigInteger amount;
    }

    public static class FeesWithdrawnEventResponse extends BaseEventResponse {
        public String owner;

        public BigInteger amount;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class PaymentExecutedEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String user;

        public BigInteger payout;

        public BigInteger fee;
    }

    public static class StrategyCreatedEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String user;

        public BigInteger amount;

        public byte[] recommendationHash;
    }

    public static class StrategyWithdrawnEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String user;

        public BigInteger refundedAmount;
    }
}
