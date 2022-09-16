package financeapp.category;

import financeapp.transaction.services.TransactionService;
import financeapp.users.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@RestController()
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final TransactionService transactionService;
    private final UserService userService;

    @PostMapping(value = "/set")
    @ResponseBody
    public ResponseEntity<?> setCategory(Authentication authentication, @RequestBody SetCategoryData data) {
        var transaction = transactionService.getTransaction(data.getTransactionId());
        var user = userService.findUserByEmail(authentication.getName());
        if (transaction.getAccount().getUser() != user)
            throw new RuntimeException("It's not your!");

        var category = categoryService.getCategory(data.getCategoryId());
        if (category == null)
            throw new RuntimeException("Not valid category id");

        transactionService.setCategory(transaction, category);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Collections.singletonMap("transactionId", transaction.getId()));
    }
}

@Data
class SetCategoryData {
    private Long transactionId;
    private Long categoryId;
}
