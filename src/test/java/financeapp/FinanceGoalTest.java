package financeapp;

import financeapp.accounts.models.Account;
import financeapp.accounts.models.variousAccount.CreditAccount;
import financeapp.accounts.models.variousAccount.DebitAccount;
import financeapp.accounts.models.variousAccount.SavingAccount;
import financeapp.accounts.repositories.AccountRepo;
import financeapp.accounts.services.AccountService;
import financeapp.category.CategoryService;
import financeapp.financeGoal.FinanceGoal;
import financeapp.financeGoal.FinanceGoalRepository;
import financeapp.financeGoal.FinanceGoalService;
import financeapp.users.CustomUser;
import financeapp.users.UserRepo;
import financeapp.users.UserService;
import org.checkerframework.checker.units.qual.A;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.mockito.Mockito.verify;

public class FinanceGoalTest extends AbstractTest{

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private FinanceGoalService financeGoalService;

    @MockBean
    private FinanceGoalRepository financeGoalRepository;

    @TestConfiguration
    class CategoryServiceTestContextConfiguration {
        @Bean
        public FinanceGoalService financeGoalService() {
            return new FinanceGoalService(financeGoalRepository);
        }
    }

    private SavingAccount savingAccount;
    private CustomUser user;
    private FinanceGoal goal;

    @Before
    public void startUp() {
        user = new CustomUser("bla", "bla");
        savingAccount = new SavingAccount("3", "saving", user, "test");

        Mockito.when(financeGoalRepository.save(Mockito.any())).thenReturn(
                new FinanceGoal(savingAccount, "iPad",
                        LocalDate.now().plusMonths(3), 60000d, user)
        );


        goal = financeGoalService.create(user, savingAccount,
                "iPad", LocalDate.now().plusMonths(3), 60000d);
    }


    @Test
    public void createGoal_checkMonthlyFee() {
        Assert.assertEquals(20000d, goal.MonthlyFee(), 1);
        savingAccount.setBalance(30000d);
        Assert.assertEquals(10000d, goal.MonthlyFee(), 1);
    }

    @Test
    public void setImage() {
        var urlImg = "https://www.ixbt.com/img/r30/00/02/53/11/AppleiPadAirherocolorlineup220308.jpg";
        financeGoalService.setImage(goal, urlImg);
        Assert.assertEquals(urlImg, goal.getImageUrl());

    }


}
