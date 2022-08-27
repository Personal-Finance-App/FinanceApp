package financeapp;

import financeapp.accounts.models.Account;
import financeapp.accounts.repositories.AccountRepo;
import financeapp.accounts.services.AccountService;
import financeapp.bankConnection.fakeConnection.service.FakeConnectionService;
import financeapp.transaction.TransactionRepo;
import financeapp.transaction.services.TransactionService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
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

    @SpyBean
    private AccountRepo accountRepo;

    @SpyBean
    private TransactionRepo transactionRepo;

    @TestConfiguration
    class AccountServiceTestContextConfiguration {

        @Bean
        public AccountService accountService() {
            return new AccountService(accountRepo);
        }
    }
    @TestConfiguration
    class TransactionServiceTestContextConfiguration {

        @Bean
        public TransactionService transactionService() {
            return new TransactionService(transactionRepo, accountRepo);
        }
    }

    @Test
    public void CreateFakeAccount(){
        Account account = fakeConnectionService.CreateAccount("credit", "vasya@gmail.com");
        Assert.assertEquals("Fake", account.getProvider());
        Mockito.verify(accountRepo).save(account);
    }

    @Test
    public void CreateFakeAccounts() {
        List<Account> accounts = fakeConnectionService.CreateAccounts("vasya@gmail.com");
        Assert.assertEquals(accounts.size(), 3);
        Mockito.verify(accountRepo).saveAll(accounts);
    }
}
