package financeapp;

import financeapp.accounts.models.Account;
import financeapp.accounts.repositories.AccountRepo;
import financeapp.accounts.services.AccountService;
import financeapp.bankConnection.fakeConnection.service.FakeConnectionService;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class FakeConnectionServiceTest {
    @Autowired
    private FakeConnectionService fakeConnectionService;

//    @Autowired
//    private AccountRepo accountRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

//    @SpyBean
//    private TransactionRepo transactionRepo;

//    @SpyBean
//    private LabelService labelService;


//    @TestConfiguration
//    class TransactionServiceTestContextConfiguration {
//
//        @Bean
//        public TransactionService transactionService() {
//            return new TransactionService(transactionRepo, accountRepo, labelService);
//        }
//    }

    @Test
    public void CreateFakeAccount(){
        CustomUser user = new CustomUser("vasya@gmail.com", "12345");
        userService.saveUser(user);
        Account account = fakeConnectionService.CreateAccount("credit", user.getEmail());
//        CustomUser userFromDB = userService.findUserByEmail(user.getEmail());
//        Assert.assertEquals(userFromDB.getId(), user.getId());
        Account accountFromDB = accountService.getById(account.getId());
        Assert.assertEquals(account.getId(), accountFromDB.getId());
//        Assert.assertEquals("Fake", account.getProvider());
//        Mockito.verify(accountRepo).save(account);
    }

    @Test
    public void CreateFakeAccounts() {
        List<Account> accountList = new ArrayList<>();
        CustomUser user = new CustomUser("vasya@gmail.com", "12345");
        userService.saveUser(user);
        List<Account> accounts = fakeConnectionService.CreateAccounts("vasya@gmail.com");
        CustomUser userFromDB = userService.findUserByEmail("vasya@gmail.com");
        Assert.assertEquals(userFromDB.getId(), user.getId());
        for(Account account : accounts){
            Account account1 = accountService.getById(account.getId());
            accountList.add(account1);

        }
        Assert.assertEquals(accountList.size(), 3);
//        Mockito.verify(accountRepo).saveAll(accounts);
    }
}
