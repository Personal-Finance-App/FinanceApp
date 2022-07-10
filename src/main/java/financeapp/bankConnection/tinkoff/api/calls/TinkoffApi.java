package financeapp.bankConnection.tinkoff.api.calls;

import financeapp.bankConnection.tinkoff.api.responseEntitys.Session;
import financeapp.bankConnection.tinkoff.api.responseEntitys.SmsRequest;
import financeapp.bankConnection.tinkoff.api.responseEntitys.WarmUpCache;
import financeapp.bankConnection.tinkoff.api.responseEntitys.afterConfirm.ConfirmSmsAnswer;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Интерфейс, позволяющий отправлять запросы на сервер тинькова со стандартными значениями
 * (они иммитируют запросы с телефона)
 * Значения указаны в фабрике
 * Использование: TinkoffApiFactory.getService().__название_метода__.execute()
 * @see TinkoffApiFactory
 */
public interface TinkoffApi {


    @POST("/v1/auth/session")
    @FormUrlEncoded
    Call<Session> getSession(@Field("deviceId") String deviceId, @Field("oldDeviceId") String oldDeviceId);

    /**
     * Запрос, отправляя который, пользователь получает смс-код
     *
     * @param sessionid индентификатор сессии
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
     * @param sessionid индентификатор сессии
     * @param deviceId  RequestBody с deviceId
     * @return StatusCode должен быть OK
     * @see WarmUpCache
     */

    @POST("/v1/warmup_cache")
    @FormUrlEncoded
    Call<WarmUpCache> warmUpCache(@Query("sessionid") String sessionid,
                                  @Field("deviceId") String deviceId,
                                  @Field("oldDeviceId") String oldDeviceId);


    /**
     * @param sessionid              индентификатор сессии- индентификатор сессии
     * @param initialOperationTicket какой-то индентификатор, который получаем из функции requestSms
     * @param initialOperation       изначальная операция например auth/by/phone
     * @param confirmationData       строчка с смс-кодом
     * @param deviceId               строчка с смс-кодом
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
     * Как я понял, нужно отправлять пароль от онлайн банка, для того, чтобы выполнять Расширенный набор вызовов функций
     * Но может получать список счетов и транзакций этого не требует
     *
     * @param sessionid индентификатор сессии- индентификатор сессии
     * @param deviceId  - информация об устройстве <br>
     * @param password  - пароль от онлайн банка <br>
     * @return TODO: понять что вернет
     */
    @POST("/v1/auth/by/password")
    @FormUrlEncoded
    Call<?> confirmPassword(@Query("sessionid") String sessionid,
                            @Field("deviceId") String deviceId,
                            @Field("oldDeviceId") String oldDeviceId,
                            @Field("password") String password);

    /**
     * @param sessionid       индентификатор сессии- индентификатор сессии
     * @param deviceId        информация об устройстве
     * @param pinHash         пин-код который, наверное, будет храниться у пользователя
     * @param authTypeSetDate время установки пин кода
     * @return TODO: понять что вернет
     */
    @POST("/v1/auth/pin/set")
    @FormUrlEncoded
    Call<?> setUpPin(@Query("sessionid") String sessionid,
                     @Field("deviceId") String deviceId,
                     @Field("oldDeviceId") String oldDeviceId,
                     @Field("pinHash") String pinHash,
                     @Field("auth_type_set_date") String authTypeSetDate);


    /**
     * @param sessionid    индентификатор сессии
     * @param deviceId     информация об устройстве
     * @param oldSessionId инщдентификатор старой ссессий
     * @param pinHash      пин код
     * @param setDate      дата установки пин кода
     * @param authType     всегда значение равно pin
     * @return TODO: новые какие-то данные (?)
     */

    @POST("/auth/by/pin")
    @FormUrlEncoded
    Call<?> loginByPinCode(@Query("sessionid") String sessionid,
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
     * @param sessionid индентификатор сессии- индентификатор сессии
     * @return список счетов
     */
    @POST("/accounts_flat")
    @FormUrlEncoded
    Call<?> accountsList(@Query("deviceId") String deviceId,
                         @Query("sessionid - индентификатор сессии") String sessionid);

    /**
     * Получить список транзакций для счета, начиная с даты
     *
     * @param deviceId  информация об устройстве
     * @param sessionid индентификатор сессии- индентификатор сессии
     * @param accountId индентификатор счета, получаемый из accountsList
     * @param start     дата начала
     * @return список транзакций
     */
    @POST("/operations")
    @FormUrlEncoded
    Call<?> transactionList(@Query("deviceId") String deviceId,
                            @Query("sessionid - индентификатор сессии") String sessionid,
                            @Query("account") String accountId,
                            @Query("start") String start);


}

