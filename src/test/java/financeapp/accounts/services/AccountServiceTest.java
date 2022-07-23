package financeapp.accounts.services;

import financeapp.accounts.models.Account;
import financeapp.accounts.models.variousAccount.CreditAccount;
import financeapp.accounts.models.variousAccount.DebitAccount;
import financeapp.accounts.models.variousAccount.SavingAccount;
import financeapp.accounts.repositories.AccountRepo;
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

import java.util.LinkedList;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepo accountRepo;

    @TestConfiguration
    class AccountServiceTestContextConfiguration {

        @Bean
        public AccountService accountService() {
            return new AccountService(accountRepo);
        }
    }


    @Test
    public void CreateAccounts_repositoryCalls() {
        var user = new CustomUser("bla", "bla");
        var accounts = new LinkedList<Account>() {{
            add(new DebitAccount("1", "debit", user, "test"));
            add(new CreditAccount("2", "credit", user, "test"));
            add(new SavingAccount("3", "saving", user, "test"));
        }};

        accountService.CreateAccountFromPayload(accounts);
        verify(accountRepo).saveAll(Mockito.any());

    }
}