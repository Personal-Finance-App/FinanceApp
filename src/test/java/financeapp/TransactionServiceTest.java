package financeapp;

import financeapp.accounts.models.variousAccount.DebitAccount;
import financeapp.accounts.repositories.AccountRepo;
import financeapp.accounts.services.AccountService;
import financeapp.transaction.TransactionRepo;
import financeapp.transaction.models.AbstractTransaction;
import financeapp.transaction.models.PayTransaction;
import financeapp.transaction.services.TransactionService;
import financeapp.users.CustomUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.LinkedList;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @MockBean
    private TransactionRepo transactionRepo;

    @MockBean
    private AccountRepo accountRepo;


    @TestConfiguration
    class TransactionServiceTestContextConfiguration {
        @Bean
        public TransactionService transactionService() {
            return new TransactionService(transactionRepo, accountRepo);
        }
    }


    @Test
    public void saveTransactions_repositoryCalls() {
        var user = new CustomUser("Test", "bla");
        var account = new DebitAccount("1", "debit", user, "test");
        accountService.CreateAccountFromPayload(new LinkedList<>() {{
            add(account);
        }});
        var transaction = new LinkedList<AbstractTransaction>() {{
            add(new PayTransaction(1000, "ha", LocalDateTime.now(), "bla"));
        }};
        var syncTime = LocalDateTime.now();
        transactionService.saveTransactions(transaction, account, syncTime);
        Mockito.verify(transactionRepo).saveAll(Mockito.any());
    }
}