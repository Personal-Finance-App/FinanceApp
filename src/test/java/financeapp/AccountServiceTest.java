package financeapp;

import financeapp.accounts.models.Account;
import financeapp.accounts.models.variousAccount.CreditAccount;
import financeapp.accounts.models.variousAccount.DebitAccount;
import financeapp.accounts.models.variousAccount.SavingAccount;
import financeapp.accounts.repositories.AccountRepo;
import financeapp.accounts.services.AccountService;
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
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class AccountServiceTest extends AbstractTest{

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepo accountRepo;

    @Autowired
    private UserService userService;

    @TestConfiguration
    class AccountServiceTestContextConfiguration {

        @Bean
        public AccountService accountService() {
            return new AccountService(accountRepo, userService);
        }
    }

    private CustomUser user;

    @Before
    public void setUp() throws Exception {
        user = new CustomUser("bla", "bla", "bla", "bla", "bla");
        userService.saveUser(user);
    }

    @Test
    public void CreateAccounts_repositoryCalls() {
        var accounts = new LinkedList<Account>() {{
            add(new DebitAccount("1", "debit", user, "test"));
            add(new CreditAccount("2", "credit", user, "test"));
            add(new SavingAccount("3", "saving", user, "test"));
        }};
        accountService.CreateAccountFromPayload(accounts, user);
    }

    @Test
    public void DeleteAccounts_repositoryCalls() {
        var accToDel = new DebitAccount("1", "debit", user, "test");
        var accounts = new LinkedList<Account>() {{
            add(accToDel);
            add(new CreditAccount("2", "credit", user, "test"));
            add(new SavingAccount("3", "saving", user, "test"));
        }};

        accountService.deleteAccount(accToDel.getId());
        Mockito.verify(accountRepo).deleteById(Mockito.any());
        var userByEmail = userService.findUserByEmail(user.getEmail());
        Assert.assertEquals(user.getId(), userByEmail.getId());

    }
}