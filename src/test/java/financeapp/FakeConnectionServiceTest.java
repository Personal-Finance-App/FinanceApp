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

public class FakeConnectionServiceTest extends AbstractTest{
    @Autowired
    private FakeConnectionService fakeConnectionService;

    @Autowired
    private UserService userService;

    @SpyBean
    private AccountService accountService;

    @Autowired
    private RandomUtility randomUtility;

    @Autowired
    private TransactionService transactionService;

    @TestConfiguration
    class FakeConnectionServiceContextConfiguration {

        @Bean
        public FakeConnectionService fakeConnectionService() {
            return new FakeConnectionService(randomUtility, accountService, userService, transactionService);
        }
    }

    @Test
    public void CreateFakeAccount(){
        CustomUser user = new CustomUser("vasya@gmail.com", "12345");
        userService.saveUser(user);
        Account account = fakeConnectionService.CreateAccount("credit", user.getEmail());
        Mockito.verify(accountService).CreateAccountFromPayload(Mockito.any(), Mockito.any());
    }

//    @Test
//    public void CreateFakeAccounts() {
//        List<Account> accountList = new ArrayList<>();
//        CustomUser user = new CustomUser("vasya@gmail.com", "12345");
//        userService.saveUser(user);
//        List<Account> accounts = fakeConnectionService.CreateAccounts("vasya@gmail.com");
//        CustomUser userFromDB = userService.findUserByEmail("vasya@gmail.com");
//        Assert.assertEquals(userFromDB.getId(), user.getId());
//        for(Account account : accounts){
//            Account account1 = accountService.getById(account.getId());
//            accountList.add(account1);
//
//        }
//        Assert.assertEquals(accountList.size(), 3);
////        Mockito.verify(accountRepo).saveAll(accounts);
//    }
}
