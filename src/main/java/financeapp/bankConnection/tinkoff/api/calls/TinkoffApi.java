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
    Call<Session> getSession(@Body RequestBody data);

    /**
     * Запрос, отправляя который, пользователь получает смс-код
     *
     * @param sessionId индентификатор сессии
     * @param data      RequestBody с phone (в формате +7**********) и deviceId
     * @return initialOperationTicket
     * @see SmsRequest
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
     * @see WarmUpCache
     */
    @Headers({"Content-Type: application/json", "user-agent: HUAWEI MAR-LX1M/android: 10/TCSMB/5.8.1"})
    @POST("/v1/warmup_cache")
    Call<WarmUpCache> warmUpCache(@Query("sessionId") String sessionId,
                                  @Body RequestBody deviceId);


    /**
     * @param sessionId индентификатор сессии- индентификатор сессии
     * @param data      RequestBody со след содержанием: <br>
     *                  initialOperationTicket -  какой-то индентификатор, который получаем из функции requestSms <br>
     *                  initialOperation - изначальная операция например auth/by/phone <br>
     *                  confirmationData - строчка с смс-кодом <br>
     *                  deviceId - строчка с смс-кодом <br>
     * @return ...
     * @see ConfirmSmsAnswer
     */
    @Headers({"Content-Type: application/json", "user-agent: HUAWEI MAR-LX1M/android: 10/TCSMB/5.8.1"})
    @POST("/v1/confirm")
    Call<ConfirmSmsAnswer> confirmSms(@Query("sessionId") String sessionId,
                                      @Body RequestBody data);

    /**
     * Как я понял, нужно отправлять пароль от онлайн банка, для того, чтобы выполнять Расширенный набор вызовов функций
     * Но может получать список счетов и транзакций этого не требует
     *
     * @param sessionId индентификатор сессии- индентификатор сессии
     * @param data      RequestBody со след содержанием: <br>
     *                  password  - пароль от онлайн банка <br>
     *                  deviceId  - информация об устройстве <br>
     * @return TODO: понять что вернет
     */
    @Headers({"Content-Type: application/json", "user-agent: HUAWEI MAR-LX1M/android: 10/TCSMB/5.8.1"})
    @POST("/v1/auth/by/password")
    Call<?> confirmPassword(@Body RequestBody data,
                            @Query("sessionId") String sessionId);

    /**
     * @param data     RequestBody со след содержанием: <br>
     *                 sessionId  индентификатор сессии- индентификатор сессии <br>
     *                 pinHash    пин-код который, наверное, будет храниться у пользователя <br>
     * @param deviceId информация об устройстве
     * @return TODO: понять что вернет
     */
    @Headers({"Content-Type: application/json", "user-agent: HUAWEI MAR-LX1M/android: 10/TCSMB/5.8.1"})
    @POST("/v1/auth/pin/set")
    Call<?> setUpPin(@Body RequestBody data,
                     @Query("deviceId") String deviceId);

    /**
     * @param sessionId     индентификатор сессии- индентификатор сессии
     * @param data <br> RequestBody со след содержанием
     * deviceId      - информация об устройстве <br>
     * pinHash       - пин код <br>
     * auth_type     - всегда значение равно pin <br>
     * oldSessionId  - индентификатор сессии <br>
     * @return TODO: новые какие-то данные (?)
     */
    @Headers({"Content-Type: application/json", "user-agent: HUAWEI MAR-LX1M/android: 10/TCSMB/5.8.1"})
    @POST("/v1/auth/by/pin")
    Call<?> loginByPinCode(@Query("sessionId") String sessionId,
                           @Body RequestBody data);


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

