package financeapp.bankConnection.tinkoff.api.calls;

import financeapp.bankConnection.tinkoff.api.responseEntitys.Session;
import financeapp.bankConnection.tinkoff.api.responseEntitys.SmsRequest;
import financeapp.bankConnection.tinkoff.api.responseEntitys.WarmUpCache;
import financeapp.bankConnection.tinkoff.api.responseEntitys.afterConfirm.ConfirmSmsAnswer;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
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

    @Headers({"Content-Type: application/json", "user-agent: HUAWEI MAR-LX1M/android: 10/TCSMB/5.8.1"})
    @POST("/v1/auth/session")
    Call<Session> getSession(@Query("deviceId") String deviceId);

    /**
     * Запрос, отправляя который, пользователь получает смс-код
     *
     * @param sessionId индентификатор сессии
     * @param data      RequestBody с phone (в формате +7**********) и deviceId
     * @return initialOperationTicket
     */
    @Headers({"Content-Type: application/json", "user-agent: HUAWEI MAR-LX1M/android: 10/TCSMB/5.8.1"})
    @POST("/v1/auth/by/phone")
    Call<SmsRequest> requestSms(
            @Query("sessionId") String sessionId,
            @Body RequestBody data);


    /**
     * @param sessionId индентификатор сессии
     * @param deviceId  RequestBody с deviceId
     * @return StatusCode должен быть OK
     */
    @Headers({"Content-Type: application/json", "user-agent: HUAWEI MAR-LX1M/android: 10/TCSMB/5.8.1"})
    @POST("/v1/warmup_cache")
    Call<WarmUpCache> warmUpCache(@Query("sessionId") String sessionId,
                                  @Body RequestBody deviceId);


    /**
     * @param sessionId индентификатор сессии- индентификатор сессии
     * @param data      RequestBody со след значениями:
     *                  initialOperationTicket -  какой-то индентификатор, который получаем из функции requestSms
     *                  initialOperation - изначальная операция например auth/by/phone
     *                  confirmationData - строчка с смс-кодом
     *                  deviceId - строчка с смс-кодом
     * @return TODO: понять что вернет
     */
    @Headers({"Content-Type: application/json", "user-agent: HUAWEI MAR-LX1M/android: 10/TCSMB/5.8.1"})
    @POST("/v1/confirm")
    Call<ConfirmSmsAnswer> confirmSms(@Query("sessionId") String sessionId,
                                      @Body RequestBody data);

    /**
     * Как я понял, нужно отправлять пароль от онлайн банка, для того, чтобы выполнять Расширенный набор вызовов функций
     * Но может получать список счетов и транзакций этого не требует
     *
     * @param deviceId   информация об устройстве
     * @param sessionId  индентификатор сессии- индентификатор сессии
     * @param password   пароль от онлайн банка
     * @return TODO: понять что вернет
     */
    @Headers({"Content-Type: application/json", "user-agent: HUAWEI MAR-LX1M/android: 10/TCSMB/5.8.1"})
    @POST("/v1/auth/by/password")
    Call<?> confirmPassword(@Query("deviceId") String deviceId,
                            @Query("sessionId") String sessionId,
                            @Query("password") String password);

    /**
     * @param deviceId   информация об устройстве
     * @param sessionId  индентификатор сессии- индентификатор сессии
     * @param pinHash    пин-код который, наверное, будет храниться у пользователя
     * @return TODO: понять что вернет
     */
    @Headers({"Content-Type: application/json", "user-agent: HUAWEI MAR-LX1M/android: 10/TCSMB/5.8.1"})
    @POST("/v1/auth/pin/set")
    Call<?> setUpPing(@Query("deviceId") String deviceId,
                      @Query("sessionId") String sessionId,
                      @Query("pinHash") String pinHash);

    /**
     * @param deviceId      информация об устройстве
     * @param sessionId     индентификатор сессии- индентификатор сессии
     * @param pinHash       пин код
     * @param auth_type     всегда значение равно pin
     * @param oldSessionId  индентификатор сессии
     * @return TODO: новые какие-то данные (?)
     */
    @Headers({"Content-Type: application/json", "user-agent: HUAWEI MAR-LX1M/android: 10/TCSMB/5.8.1"})
    @POST("/v1/auth/by/pin")
    Call<?> loginByPinCode(@Query("deviceId") String deviceId,
                           @Query("sessionId") String sessionId,
                           @Query("pinHash") String pinHash,
                           @Query("auth_type") String auth_type,
                           @Query("oldsessionId") String oldSessionId);


    /**
     * Получить список всех доступных счетов
     *
     * @param deviceId   информация об устройстве
     * @param sessionId  индентификатор сессии- индентификатор сессии
     * @return список счетов
     */
    @Headers({"Content-Type: application/json", "user-agent: HUAWEI MAR-LX1M/android: 10/TCSMB/5.8.1"})
    @POST("/v1/accounts_flat")
    Call<?> accountsList(@Query("deviceId") String deviceId,
                         @Query("sessionId - индентификатор сессии") String sessionId);

    /**
     * Получить список транзакций для счета, начиная с даты
     *
     * @param deviceId   информация об устройстве
     * @param sessionId  индентификатор сессии- индентификатор сессии
     * @param accountId  индентификатор счета, получаемый из accountsList
     * @param start      дата начала
     * @return список транзакций
     */
    @Headers({"Content-Type: application/json", "user-agent: HUAWEI MAR-LX1M/android: 10/TCSMB/5.8.1"})
    @POST("/v1/operations")
    Call<?> transactionList(@Query("deviceId") String deviceId,
                            @Query("sessionId - индентификатор сессии") String sessionId,
                            @Query("account") String accountId,
                            @Query("start") String start);


}

