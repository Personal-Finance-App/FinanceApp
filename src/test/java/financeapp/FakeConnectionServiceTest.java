package financeapp;

import financeapp.accounts.models.Account;
import financeapp.accounts.services.AccountService;
import financeapp.bankConnection.fakeConnection.service.FakeConnectionService;
import financeapp.bankConnection.fakeConnection.tools.RandomUtility;
import financeapp.transaction.services.TransactionService;
import financeapp.users.CustomUser;
import financeapp.users.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class FakeConnectionServiceTest {
    @Autowired
    private FakeConnectionService fakeConnectionService;

    @Autowired
    private UserService userService;

    @SpyBean
    private AccountService accountService;

    @Autowired
    private RandomUtility randomUtility;

    @MockBean
    private TransactionService transactionService;


    @TestConfiguration
    class FakeConnectionServiceContextConfiguration {

        @Bean
        public FakeConnectionService fakeConnectionService() {
            return new FakeConnectionService(randomUtility, accountService, userService, transactionService);
        }
    }

    private CustomUser user;

    @Before
    public void setUp() throws Exception {
        user = new CustomUser("vasya@gmail.com", "12345");
        userService.saveUser(user);
    }

    @Test
    public void CreateFakeAccount() {
        Account account = fakeConnectionService.CreateAccount("credit", user.getEmail());
        ArgumentCaptor<CustomUser> argument = ArgumentCaptor.forClass(CustomUser.class);
        Mockito.verify(accountService).CreateAccountFromPayload(Mockito.any(), argument.capture());
        Assert.assertEquals(user.getEmail(), argument.getValue().getEmail());
    }


    @Test
    public void CreateFakeAccounts() {
        List<Account> accounts = fakeConnectionService.CreateAccounts("vasya@gmail.com");
        Mockito.verify(accountService).CreateAccountFromPayload(Mockito.any(), Mockito.any());
        Mockito.verify(transactionService, Mockito.times(3))
                .saveTransactions(Mockito.any(), Mockito.any(), Mockito.any());

        for (var acc : accounts) {
            Mockito.verify(transactionService).saveTransactions(Mockito.any(), Mockito.eq(acc), Mockito.any());
        }
    }
}
