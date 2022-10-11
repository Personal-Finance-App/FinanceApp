package financeapp.bankConnection.sberbank.api.calls;

import financeapp.bankConnection.sberbank.api.responseEntitys.accountList.AccountListResponse;
import financeapp.bankConnection.sberbank.api.responseEntitys.confirmSMS.ConfirmSmsResponse;
import financeapp.bankConnection.sberbank.api.responseEntitys.login.LoginResponse;
import financeapp.bankConnection.sberbank.api.responseEntitys.postCSALogin.PostCSALoginResponse;
import financeapp.bankConnection.sberbank.api.responseEntitys.requestSms.RequestSmsResponse;

import financeapp.bankConnection.sberbank.api.responseEntitys.setUpPin.SetUpPinResponse;
import financeapp.bankConnection.sberbank.api.responseEntitys.transactionList.TransactionListResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SberbankApi {
    @POST("/CSAMAPI/registerApp.do")
    @FormUrlEncoded
    Call<RequestSmsResponse> requestSMS(@Field("operation") String operation,
                                        @Field("login") String login);

    @POST("/CSAMAPI/registerApp.do")
    @FormUrlEncoded
    Call<ConfirmSmsResponse> confirmSms(@Field("operation") String operation,
                                        @Field("mGUID") String mGUID,
                                        @Field("smsPassword") String SmsCode,
                                        @Field("confirmData") String smsCodeAgain);

    @POST("/CSAMAPI/registerApp.do")
    @FormUrlEncoded
    Call<SetUpPinResponse> setUpPing(@Field("operation") String operation,
                                     @Field("mGUID") String mGUID,
                                     @Field("password") String pinCode);

    @POST("CSAMAPI/login.do")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("operation") String operation,
                              @Field("password") String pinCode,
                              @Field("mGUID") String mGUID);

    @POST("mobile9/postCSALogin.do")
    @FormUrlEncoded
    Call<PostCSALoginResponse> postCSALogin(@Field("token") String token);

    @POST("mobile9/private/products/list.do")
    @FormUrlEncoded
    Call<AccountListResponse> getAccounts(@Field("showProductType") String types);

    @POST("/mobile9/private/payments/list.do")
    @FormUrlEncoded
    Call<TransactionListResponse> getTransaction(@Field("from") String from,
                                                 @Field("to") String to,
                                                 @Field("usedResource") String card,
                                                 @Field("paginationSize") String paginationSize,
                                                 @Field("paginationOffset") String offset,
                                                 @Field("includeUfs") String includeUfs);

}
