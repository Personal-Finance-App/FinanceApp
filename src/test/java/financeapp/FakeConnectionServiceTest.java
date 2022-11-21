package financeapp;

import financeapp.accounts.models.Account;
import financeapp.accounts.repositories.AccountRepo;
import financeapp.accounts.services.AccountService;
import financeapp.bankConnection.fakeConnection.service.FakeConnectionService;
import financeapp.bankConnection.fakeConnection.tools.RandomUtility;
import financeapp.monthReport.services.LabelService;
import financeapp.transaction.TransactionRepo;
import financeapp.transaction.services.TransactionService;
import financeapp.users.CustomUser;
import financeapp.users.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class FakeConnectionServiceTest {
    @SpyBean
    private FakeConnectionService fakeConnectionService;

    @SpyBean
    private AccountRepo accountRepo;

    @SpyBean
    private UserService userService;

    @SpyBean
    private AccountService accountService;

    @SpyBean
    private TransactionService transactionService;

    @SpyBean
    private LabelService labelService;

    @SpyBean
    private RandomUtility randomUtility;


//    @TestConfiguration
//    class AccountServiceTestContextConfiguration {
//
//        @Bean
//        public AccountService accountService() {
//            return new AccountService(accountRepo, userService);
//        }
//    }
//    @TestConfiguration
//    class TransactionServiceTestContextConfiguration {
//
//        @Bean
//        public TransactionService transactionService() {
//            return new TransactionService(transactionRepo, accountRepo, labelService);
//        }
//    }

    @TestConfiguration
    class  FakeConnectionServiceTestContextConfiguration {

        @Bean
        public FakeConnectionService fakeConnectionService(){
            return new FakeConnectionService(randomUtility, accountService, userService, transactionService);
        }
    }
    @Before
    public void setUp(){
        CustomUser user = new CustomUser("vasya@gmail.com", "12345");
        userService.saveUser(user);
    }



    @Test
    public void CreateFakeAccount(){
        CustomUser user = new CustomUser("vasya@gmail.com", "12345");
        userService.saveUser(user);
        Account account = fakeConnectionService.CreateAccount("credit", user.getEmail());
        Mockito.verify(accountService).CreateAccountFromPayload(Mockito.any(), Mockito.any());
    }


    @Test
    public void CreateFakeAccounts() {
        CustomUser user = new CustomUser("vasya@gmail.com", "12345");
        userService.saveUser(user);
        List<Account> account = fakeConnectionService.CreateAccounts(user.getEmail());
        Mockito.verify(accountService).CreateAccountFromPayload(Mockito.any(), Mockito.any());
    }
}
