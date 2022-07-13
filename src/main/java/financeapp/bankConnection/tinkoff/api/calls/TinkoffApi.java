package financeapp.bankConnection.tinkoff.api.calls;

import financeapp.bankConnection.tinkoff.api.responseEntitys.PinConfirm.SetUpPinAnswer;
import financeapp.bankConnection.tinkoff.api.responseEntitys.Session;
import financeapp.bankConnection.tinkoff.api.responseEntitys.SmsRequest;
import financeapp.bankConnection.tinkoff.api.responseEntitys.WarmUpCache;
import financeapp.bankConnection.tinkoff.api.responseEntitys.accountsList.AccountsAnswer;
import financeapp.bankConnection.tinkoff.api.responseEntitys.afterConfirm.ConfirmSmsAnswer;
import financeapp.bankConnection.tinkoff.api.responseEntitys.pingConfirm.PingAnswer;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Интерфейс, позволяющий отправлять запросы на сервер тинькова со стандартными значениями
 * (они имитируют запросы с телефона)
 * Значения указаны на фабрике
 * Использование: TinkoffApiFactory.getService().__название_метода__.execute()
 *
 * @see TinkoffApiFactory
 */
public interface TinkoffApi {


    @POST("/v1/auth/session")
    @FormUrlEncoded
    Call<Session> getSession(@Field("deviceId") String deviceId, @Field("oldDeviceId") String oldDeviceId);

    /**
     * Запрос, отправляя который, пользователь получает смс-код
     *
     * @param sessionid идентификатор сессии
     * @param phone     номер телефона (в формате +7**********)
     * @param deviceId  - deviceID = oldDevicdeId
     * @return initialOperationTicket
     * @see SmsRequest
     */
    @POST("/v1/auth/by/phone")
    @FormUrlEncoded
    Call<SmsRequest> requestSms(
            @Query("sessionid") String sessionid,
            @Field("phone") String phone,
            @Field("deviceId") String deviceId,
            @Field("oldDeviceId") String oldDevicdeId);


    /**
     * @param sessionid идентификатор сессии
     * @param deviceId  информация об устройстве
     * @return StatusCode должен быть OK
     * @see WarmUpCache
     */

    @POST("/v1/warmup_cache")
    @FormUrlEncoded
    Call<WarmUpCache> warmUpCache(@Query("sessionid") String sessionid,
                                  @Field("deviceId") String deviceId,
                                  @Field("oldDeviceId") String oldDeviceId);


    /**
     * @param sessionid              идентификатор сессии
     * @param initialOperationTicket какой-то идентификатор, который получаем из функции requestSms
     * @param initialOperation       изначальная операция например auth/by/phone
     * @param confirmationData       строчка с смс-кодом
     * @param deviceId               информация об устройстве
     * @return ...
     * @see ConfirmSmsAnswer
     */

    @POST("/v1/confirm")
    @FormUrlEncoded
    Call<ConfirmSmsAnswer> confirmSms(@Query("sessionid") String sessionid,
                                      @Field("deviceId") String deviceId,
                                      @Field("oldDeviceId") String oldDeviceId,
                                      @Field("initialOperationTicket") String initialOperationTicket,
                                      @Field("initialOperation") String initialOperation,
                                      @Field("confirmationData") String confirmationData);

    /**
     * Как я понял, нужно отправлять пароль от онлайн банка, для того, чтобы выполнять расширенный набор вызовов функций
     * Но может получать список счетов и транзакций этого не требует
     *
     * @param sessionid идентификатор сессии
     * @param deviceId  - информация об устройстве <br>
     * @param password  - пароль от онлайн банка <br>
     * @return TODO: понять что вернет
     */
    @POST("/v1/auth/by/password")
    @FormUrlEncoded
    Call<ConfirmSmsAnswer> confirmPassword(@Query("sessionid") String sessionid,
                                           @Field("deviceId") String deviceId,
                                           @Field("oldDeviceId") String oldDeviceId,
                                           @Field("password") String password);

    /**
     * @param sessionid       идентификатор сессии
     * @param deviceId        информация об устройстве
     * @param pinHash         пин-код который, наверное, будет храниться у пользователя
     * @param authTypeSetDate время установки пин кода
     * @return TODO: понять что вернет
     */
    @POST("/v1/auth/pin/set")
    @FormUrlEncoded
    Call<SetUpPinAnswer> setUpPin(@Query("sessionid") String sessionid,
                                  @Field("deviceId") String deviceId,
                                  @Field("oldDeviceId") String oldDeviceId,
                                  @Field("pinHash") String pinHash,
                                  @Field("auth_type_set_date") String authTypeSetDate);


    /**
     * @param sessionid    идентификатор сессии
     * @param deviceId     информация об устройстве
     * @param oldSessionId идентификатор старой сессий
     * @param pinHash      пин код
     * @param setDate      дата установки пин кода
     * @param authType     всегда значение равно pin
     * @return TODO: новые какие-то данные (?)
     */

    @POST("/v1/auth/by/pin")
    @FormUrlEncoded
    Call<ConfirmSmsAnswer> loginByPinCode(@Query("sessionid") String sessionid,
                                          @Field("deviceId") String deviceId,
                                          @Field("oldDeviceId") String oldDeviceId,
                                          @Field("oldSessionId") String oldSessionId,
                                          @Field("pinHash") String pinHash,
                                          @Field("auth_type_set_date") String setDate,
                                          @Field("auth_type") String authType);


    /**
     * Получить список всех доступных счетов
     *
     * @param deviceId  информация об устройстве
     * @param sessionid идентификатор сессии
     * @return список счетов
     */
    @POST("/v1/accounts_flat")
    @FormUrlEncoded
    Call<AccountsAnswer> accountsList(@Query("sessionid") String sessionid,
                                      @Field("deviceId") String deviceId,
                                      @Query("oldDeviceId") String oldsDeviceId);

    /**
     * Получить список транзакций для счета, начиная с даты
     *
     * @param deviceId  информация об устройстве
     * @param sessionid идентификатор сессии
     * @param accountId идентификатор счета, получаемый из accountsList
     * @param start     дата начала
     * @return список транзакций
     */
    @POST("/v1/operations")
    @FormUrlEncoded
    Call<?> transactionList(@Query("deviceId") String deviceId,
                            @Query("sessionid") String sessionid,
                            @Query("account") String accountId,
                            @Query("start") String start);


    /**
     * Функция, которая может вернуть состояние сессии
     *
     * @param sessionid   идентификатор сессии-
     * @param deviceid    информация об устройстве
     * @param oldDeviceId информация об устройстве
     * @return "accessLevel": "ANONYMOUS" / "TODO" / "TODO"
     */
    @POST("/v1/ping")
    @FormUrlEncoded
    Call<PingAnswer> pingSession(@Query("sessionid") String sessionid,
                                 @Field("deviceId") String deviceid,
                                 @Field("oldDeviceId") String oldDeviceId);


}

