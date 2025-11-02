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
    public static final String BINARY = "60a0604052600a600455348015610014575f5ffd5b50604051612026380380612026833981016040819052610033916100cc565b338061005857604051631e4fbdf760e01b81525f600482015260240160405180910390fd5b6100618161007d565b5060018080556001600160a01b039091166080526002556100f9565b5f80546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b5f602082840312156100dc575f5ffd5b81516001600160a01b03811681146100f2575f5ffd5b9392505050565b608051611ec16101655f395f818161031f0152818161046701528181610614015281816106b401528181610800015281816108b501528181610be101528181610cdc01528181611069015281816111690152818161120501528181611517015261164b0152611ec15ff3fe608060405234801561000f575f5ffd5b5060043610610148575f3560e01c8063715018a6116100bf578063bdfec18311610079578063bdfec183146102b2578063d574ea3d146102c5578063d9e3fd64146102ec578063e2bbb158146102f4578063f2fde38b14610307578063fc0c546a1461031a575f5ffd5b8063715018a61461023757806379f02fd41461023f5780637f4bee151461025f5780638da5cb5b1461027257806395ccea6714610296578063a001ecdd146102a9575f5ffd5b8063558e44d311610110578063558e44d3146101d757806356d02710146101df578063587f5ed7146101f257806361b8ce8c146101fb5780636cad3fb0146102045780636e5d5a6214610217575f5ffd5b8063087b9f2d1461014c578063164e68de146101615780632e1a7d4d1461017457806332a50c7f146101875780633e9f9e49146101b6575b5f5ffd5b61015f61015a3660046118fc565b610341565b005b61015f61016f366004611973565b610551565b61015f610182366004611993565b61072b565b61019a610195366004611993565b61091e565b6040516101ad97969594939291906119de565b60405180910390f35b6101c96101c4366004611a26565b610a92565b6040519081526020016101ad565b6101c9601481565b61015f6101ed366004611a4e565b610abd565b6101c960035481565b6101c960025481565b61015f610212366004611993565b610d49565b61022a610225366004611993565b610dd6565b6040516101ad9190611a9c565b61015f610e78565b61025261024d366004611973565b610e8b565b6040516101ad9190611aae565b6101c961026d366004611993565b610ef3565b5f546001600160a01b03165b6040516001600160a01b0390911681526020016101ad565b61015f6102a4366004611a26565b6110ec565b6101c960045481565b6101c96102c0366004611af0565b611278565b6102d86102d3366004611993565b611424565b6040516101ad989796959493929190611b46565b6101c9611500565b61015f610302366004611a4e565b61158d565b61015f610315366004611973565b6116c9565b61027e7f000000000000000000000000000000000000000000000000000000000000000081565b610349611703565b5f858152600560205260409020600381015460ff166103835760405162461bcd60e51b815260040161037a90611ba1565b60405180910390fd5b838510156103d35760405162461bcd60e51b815260206004820152601860248201527f5969656c642062656c6f77206d696e2065787065637465640000000000000000604482015260640161037a565b5f85116104165760405162461bcd60e51b815260206004820152601160248201527005969656c64206d757374206265203e203607c1b604482015260640161037a565b8161045a5760405162461bcd60e51b815260206004820152601460248201527311185d18481cdbdd5c98d9481c995c5d5a5c995960621b604482015260640161037a565b61048f6001600160a01b037f00000000000000000000000000000000000000000000000000000000000000001633308861172d565b5f6064600454876104a09190611be2565b6104aa9190611bff565b90505f6104b78288611c1e565b905080836002015f8282546104cc9190611c31565b9091555050426007840155600380548391905f906104eb908490611c31565b909155505082546040516001600160a01b039091169089907f62844229a68d30c4c87e4e26cb170af9ff6956af6d8aa6d6c1dfcc70a8b3d7259061053690859087908b908b90611c6c565b60405180910390a350505061054a60018055565b5050505050565b61055961179a565b610561611703565b6001600160a01b0381166105b75760405162461bcd60e51b815260206004820152601f60248201527f43616e6e6f7420776974686472617720746f207a65726f206164647265737300604482015260640161037a565b600354806105fd5760405162461bcd60e51b81526020600482015260136024820152724e6f206665657320746f20776974686472617760681b604482015260640161037a565b6040516370a0823160e01b815230600482015281907f00000000000000000000000000000000000000000000000000000000000000006001600160a01b0316906370a0823190602401602060405180830381865afa158015610661573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906106859190611c95565b10156106a35760405162461bcd60e51b815260040161037a90611cac565b5f6003556106db6001600160a01b037f00000000000000000000000000000000000000000000000000000000000000001683836117c6565b816001600160a01b03167fc0819c13be868895eb93e40eaceb96de976442fa1d404e5c55f14bb65a8c489a8260405161071691815260200190565b60405180910390a25061072860018055565b50565b610733611703565b5f81815260056020526040902080546001600160a01b031633146107695760405162461bcd60e51b815260040161037a90611cf4565b600381015460ff1661078d5760405162461bcd60e51b815260040161037a90611ba1565b5f816002015482600101546107a29190611c31565b90505f81116107e95760405162461bcd60e51b81526020600482015260136024820152724e6f7468696e6720746f20776974686472617760681b604482015260640161037a565b6040516370a0823160e01b815230600482015281907f00000000000000000000000000000000000000000000000000000000000000006001600160a01b0316906370a0823190602401602060405180830381865afa15801561084d573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906108719190611c95565b101561088f5760405162461bcd60e51b815260040161037a90611cac565b60038201805460ff191690555f6001830181905560028301556108dc6001600160a01b037f00000000000000000000000000000000000000000000000000000000000000001633836117c6565b604051818152339084907fe3cf1c9649f092a027e4573cbf79898735b91072d70a0fb5a6fd8224a8f97acd9060200160405180910390a3505061072860018055565b5f81815260056020908152604080832081516101008101835281546001600160a01b03168152600182015493810193909352600281015491830191909152600381015460ff1615156060830152600481018054849384938493849384938493849391929091608084019161099190611d20565b80601f01602080910402602001604051908101604052809291908181526020018280546109bd90611d20565b8015610a085780601f106109df57610100808354040283529160200191610a08565b820191905f5260205f20905b8154815290600101906020018083116109eb57829003601f168201915b5050509183525050600582015460209091019060ff166002811115610a2f57610a2f6119aa565b6002811115610a4057610a406119aa565b8152600682015460208083019190915260079092015460409182015282519183015190830151606084015160a085015160c086015160e090960151949f939e50919c509a509850919650945092505050565b6006602052815f5260405f208181548110610aab575f80fd5b905f5260205f20015f91509150505481565b610ac5611703565b5f82815260056020526040902080546001600160a01b03163314610afb5760405162461bcd60e51b815260040161037a90611cf4565b600381015460ff16610b1f5760405162461bcd60e51b815260040161037a90611ba1565b5f8211610b635760405162461bcd60e51b81526020600482015260126024820152710416d6f756e74206d757374206265203e20360741b604482015260640161037a565b5f81600201548260010154610b789190611c31565b905082811015610bca5760405162461bcd60e51b815260206004820152601d60248201527f526571756573746564203e20617661696c61626c652062616c616e6365000000604482015260640161037a565b6040516370a0823160e01b815230600482015283907f00000000000000000000000000000000000000000000000000000000000000006001600160a01b0316906370a0823190602401602060405180830381865afa158015610c2e573d5f5f3e3d5ffd5b505050506040513d601f19601f82011682018060405250810190610c529190611c95565b1015610c705760405162461bcd60e51b815260040161037a90611cac565b81600201548311610c995782826002015f828254610c8e9190611c1e565b90915550610ccf9050565b60028201545f610ca98286611c1e565b90505f846002018190555080846001015f828254610cc79190611c1e565b909155505050505b610d036001600160a01b037f00000000000000000000000000000000000000000000000000000000000000001633856117c6565b604051838152339085907fac320221b136816fb4c7bbdd0fefacb9dd36ba3cefaf1fe8c1c5a64a2fa451c89060200160405180910390a35050610d4560018055565b5050565b610d5161179a565b6014811115610d915760405162461bcd60e51b815260206004820152600c60248201526b08ccaca40e8dede40d0d2ced60a31b604482015260640161037a565b600480549082905560408051828152602081018490527fb27c12a91635e11c22bffa7bd8e0a8735da52b94aaefd7f249776c7590ba7894910160405180910390a15050565b5f818152600560205260409020600401805460609190610df590611d20565b80601f0160208091040260200160405190810160405280929190818152602001828054610e2190611d20565b8015610e6c5780601f10610e4357610100808354040283529160200191610e6c565b820191905f5260205f20905b815481529060010190602001808311610e4f57829003601f168201915b50505050509050919050565b610e8061179a565b610e895f6117fc565b565b6001600160a01b0381165f90815260066020908152604091829020805483518184028101840190945280845260609392830182828015610e6c57602002820191905f5260205f20905b815481526020019060010190808311610ed45750505050509050919050565b5f81815260056020908152604080832081516101008101835281546001600160a01b03168152600182015493810193909352600281015491830191909152600381015460ff1615156060830152600481018054849392916080840191610f5890611d20565b80601f0160208091040260200160405190810160405280929190818152602001828054610f8490611d20565b8015610fcf5780601f10610fa657610100808354040283529160200191610fcf565b820191905f5260205f20905b815481529060010190602001808311610fb257829003601f168201915b5050509183525050600582015460209091019060ff166002811115610ff657610ff66119aa565b6002811115611007576110076119aa565b8152602001600682015481526020016007820154815250509050806060015161103257505f92915050565b5f816040015182602001516110479190611c31565b6040516370a0823160e01b81523060048201529091505f906001600160a01b037f000000000000000000000000000000000000000000000000000000000000000016906370a0823190602401602060405180830381865afa1580156110ae573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906110d29190611c95565b90508082116110e157816110e3565b805b95945050505050565b6110f461179a565b6110fc611703565b6001600160a01b0382166111525760405162461bcd60e51b815260206004820152601f60248201527f43616e6e6f7420776974686472617720746f207a65726f206164647265737300604482015260640161037a565b6040516370a0823160e01b815230600482015281907f00000000000000000000000000000000000000000000000000000000000000006001600160a01b0316906370a0823190602401602060405180830381865afa1580156111b6573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906111da9190611c95565b10156111f85760405162461bcd60e51b815260040161037a90611cac565b61122c6001600160a01b037f00000000000000000000000000000000000000000000000000000000000000001683836117c6565b816001600160a01b03167f5fafa99d0643513820be26656b45130b01e1c03062e1266bf36f88cbd3bd96958260405161126791815260200190565b60405180910390a2610d4560018055565b60025460408051610100810182523381525f6020808301829052828401829052600160608401528351601f8801829004820281018201909452868452909392608083019188908890819084018382808284375f920191909152505050908252506020018460028111156112ed576112ed6119aa565b81524260208083018290526040928301919091525f84815260058252829020835181546001600160a01b0319166001600160a01b03909116178155908301516001820155908201516002820155606082015160038201805460ff1916911515919091179055608082015160048201906113669082611db0565b5060a082015160058201805460ff1916600183600281111561138a5761138a6119aa565b021790555060c082015160068083019190915560e090920151600790910155335f908152602091825260408120805460018181018355918352929091209091018290556002546113d991611c31565b600255604051339082907f7ecac7a9595a304e76f6472457df16afbafe24e5649906a2ff819d543fe176db906114149087908a908a90611e6b565b60405180910390a3949350505050565b60056020525f9081526040902080546001820154600283015460038401546004850180546001600160a01b03909516959394929360ff9092169261146790611d20565b80601f016020809104026020016040519081016040528092919081815260200182805461149390611d20565b80156114de5780601f106114b5576101008083540402835291602001916114de565b820191905f5260205f20905b8154815290600101906020018083116114c157829003601f168201915b5050505060058301546006840154600790940154929360ff9091169290915088565b6040516370a0823160e01b81523060048201525f907f00000000000000000000000000000000000000000000000000000000000000006001600160a01b0316906370a0823190602401602060405180830381865afa158015611564573d5f5f3e3d5ffd5b505050506040513d601f19601f820116820180604052508101906115889190611c95565b905090565b611595611703565b5f82815260056020526040902080546001600160a01b031633146115cb5760405162461bcd60e51b815260040161037a90611cf4565b600381015460ff166115ef5760405162461bcd60e51b815260040161037a90611ba1565b5f821161163e5760405162461bcd60e51b815260206004820152601d60248201527f416d6f756e74206d7573742062652067726561746572207468616e2030000000604482015260640161037a565b6116736001600160a01b037f00000000000000000000000000000000000000000000000000000000000000001633308561172d565b8181600101546116839190611c31565b6001820155604051828152339084907f8eb219d25fffc7dbe6ebb8243673d13df598b2afb06334ee4fd35b0029db13209060200160405180910390a350610d4560018055565b6116d161179a565b6001600160a01b0381166116fa57604051631e4fbdf760e01b81525f600482015260240161037a565b610728816117fc565b60026001540361172657604051633ee5aeb560e01b815260040160405180910390fd5b6002600155565b6040516001600160a01b0384811660248301528381166044830152606482018390526117949186918216906323b872dd906084015b604051602081830303815290604052915060e01b6020820180516001600160e01b03838183161783525050505061184b565b50505050565b5f546001600160a01b03163314610e895760405163118cdaa760e01b815233600482015260240161037a565b6040516001600160a01b038381166024830152604482018390526117f791859182169063a9059cbb90606401611762565b505050565b5f80546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b5f5f60205f8451602086015f885af18061186a576040513d5f823e3d81fd5b50505f513d9150811561188157806001141561188e565b6001600160a01b0384163b155b1561179457604051635274afe760e01b81526001600160a01b038516600482015260240161037a565b5f5f83601f8401126118c7575f5ffd5b50813567ffffffffffffffff8111156118de575f5ffd5b6020830191508360208285010111156118f5575f5ffd5b9250929050565b5f5f5f5f5f60808688031215611910575f5ffd5b853594506020860135935060408601359250606086013567ffffffffffffffff81111561193b575f5ffd5b611947888289016118b7565b969995985093965092949392505050565b80356001600160a01b038116811461196e575f5ffd5b919050565b5f60208284031215611983575f5ffd5b61198c82611958565b9392505050565b5f602082840312156119a3575f5ffd5b5035919050565b634e487b7160e01b5f52602160045260245ffd5b600381106119da57634e487b7160e01b5f52602160045260245ffd5b9052565b6001600160a01b03881681526020810187905260408101869052841515606082015260e08101611a1160808301866119be565b60a082019390935260c0015295945050505050565b5f5f60408385031215611a37575f5ffd5b611a4083611958565b946020939093013593505050565b5f5f60408385031215611a5f575f5ffd5b50508035926020909101359150565b5f81518084528060208401602086015e5f602082860101526020601f19601f83011685010191505092915050565b602081525f61198c6020830184611a6e565b602080825282518282018190525f918401906040840190835b81811015611ae5578351835260209384019390920191600101611ac7565b509095945050505050565b5f5f5f60408486031215611b02575f5ffd5b833567ffffffffffffffff811115611b18575f5ffd5b611b24868287016118b7565b909450925050602084013560038110611b3b575f5ffd5b809150509250925092565b60018060a01b0389168152876020820152866040820152851515606082015261010060808201525f611b7c610100830187611a6e565b9050611b8b60a08301866119be565b60c082019390935260e001529695505050505050565b6020808252601390820152725374726174656779206e6f742061637469766560681b604082015260600190565b634e487b7160e01b5f52601160045260245ffd5b8082028115828204841417611bf957611bf9611bce565b92915050565b5f82611c1957634e487b7160e01b5f52601260045260245ffd5b500490565b81810381811115611bf957611bf9611bce565b80820180821115611bf957611bf9611bce565b81835281816020850137505f828201602090810191909152601f909101601f19169091010190565b848152836020820152606060408201525f611c8b606083018486611c44565b9695505050505050565b5f60208284031215611ca5575f5ffd5b5051919050565b60208082526028908201527f4c6f7720636f6e74726163742062616c616e63653a20496e73756666696369656040820152676e742066756e647360c01b606082015260800190565b6020808252601290820152712737ba1039ba3930ba32b3bc9037bbb732b960711b604082015260600190565b600181811c90821680611d3457607f821691505b602082108103611d5257634e487b7160e01b5f52602260045260245ffd5b50919050565b634e487b7160e01b5f52604160045260245ffd5b601f8211156117f757805f5260205f20601f840160051c81016020851015611d915750805b601f840160051c820191505b8181101561054a575f8155600101611d9d565b815167ffffffffffffffff811115611dca57611dca611d58565b611dde81611dd88454611d20565b84611d6c565b6020601f821160018114611e10575f8315611df95750848201515b5f19600385901b1c1916600184901b17845561054a565b5f84815260208120601f198516915b82811015611e3f5787850151825560209485019460019092019101611e1f565b5084821015611e5c57868401515f19600387901b60f8161c191681555b50505050600190811b01905550565b611e7581856119be565b604060208201525f6110e3604083018486611c4456fea26469706673582212207b3dd24eea6201472e3dc2cb7fb267b23718abb5f16c19d3edfc61ee72e4401164736f6c634300081d0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_MAX_FEE_PERCENTAGE = "MAX_FEE_PERCENTAGE";

    public static final String FUNC_ACCUMULATEDFEES = "accumulatedFees";

    public static final String FUNC_CREATESTRATEGY = "createStrategy";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_DISTRIBUTEYIELD = "distributeYield";

    public static final String FUNC_EMERGENCYWITHDRAW = "emergencyWithdraw";

    public static final String FUNC_FEEPERCENTAGE = "feePercentage";

    public static final String FUNC_GETAVAILABLEWITHDRAW = "getAvailableWithdraw";

    public static final String FUNC_GETSTRATEGYDETAILS = "getStrategyDetails";

    public static final String FUNC_GETSTRATEGYRECOMMENDATION = "getStrategyRecommendation";

    public static final String FUNC_GETTOTALCONTRACTBALANCE = "getTotalContractBalance";

    public static final String FUNC_GETUSERSTRATEGIES = "getUserStrategies";

    public static final String FUNC_NEXTID = "nextId";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PARTIALWITHDRAW = "partialWithdraw";

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

    public static final Event PARTIALWITHDRAWN_EVENT = new Event("PartialWithdrawn", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
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
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
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

    public static List<PartialWithdrawnEventResponse> getPartialWithdrawnEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PARTIALWITHDRAWN_EVENT, transactionReceipt);
        ArrayList<PartialWithdrawnEventResponse> responses = new ArrayList<PartialWithdrawnEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PartialWithdrawnEventResponse typedResponse = new PartialWithdrawnEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PartialWithdrawnEventResponse getPartialWithdrawnEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PARTIALWITHDRAWN_EVENT, log);
        PartialWithdrawnEventResponse typedResponse = new PartialWithdrawnEventResponse();
        typedResponse.log = log;
        typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.user = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<PartialWithdrawnEventResponse> partialWithdrawnEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPartialWithdrawnEventFromLog(log));
    }

    public Flowable<PartialWithdrawnEventResponse> partialWithdrawnEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PARTIALWITHDRAWN_EVENT));
        return partialWithdrawnEventFlowable(filter);
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
            typedResponse.dataSource = (String) eventValues.getNonIndexedValues().get(2).getValue();
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
        typedResponse.dataSource = (String) eventValues.getNonIndexedValues().get(2).getValue();
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
            BigInteger yieldAmount, BigInteger minExpectedYield, String dataSource) {
        final Function function = new Function(
                FUNC_DISTRIBUTEYIELD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id), 
                new org.web3j.abi.datatypes.generated.Uint256(yieldAmount), 
                new org.web3j.abi.datatypes.generated.Uint256(minExpectedYield), 
                new org.web3j.abi.datatypes.Utf8String(dataSource)), 
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

    public RemoteFunctionCall<BigInteger> getAvailableWithdraw(BigInteger id) {
        final Function function = new Function(FUNC_GETAVAILABLEWITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)), 
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

    public RemoteFunctionCall<TransactionReceipt> partialWithdraw(BigInteger id,
            BigInteger amount) {
        final Function function = new Function(
                FUNC_PARTIALWITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public static class PartialWithdrawnEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String user;

        public BigInteger amount;
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

        public String dataSource;
    }
}
