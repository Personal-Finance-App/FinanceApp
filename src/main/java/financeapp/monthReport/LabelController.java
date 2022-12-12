package financeapp.monthReport;

import financeapp.config.AppError;
import financeapp.monthReport.services.LabelService;
import financeapp.transaction.services.TransactionService;
import financeapp.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/label")
@AllArgsConstructor
@Tag(name = "Label")
@SecurityRequirement(name = "javainuseapi")
public class LabelController {
    private final LabelService labelService;
    private final TransactionService transactionService;
    private final UserService userService;


    @Operation(summary = "Установить список лейблов для транзакции")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Лейблы устанавлены"),
		@ApiResponse(responseCode = "403", description = "Доступ запрещен"),

		@ApiResponse(responseCode = "500", description = "Ошибочка ",
			content = { @Content(mediaType = "application/json",
			schema = @Schema(implementation = AppError.class)) }),
	})

    @PostMapping("/set")
    @ResponseBody
    public ResponseEntity<?> setLabelsForTransaction(@RequestBody DataForSet data, Authentication authentication) {
        var labels = data.getLabelsIds()
                .stream().map(labelService::getLabelById).collect(Collectors.toList());

        var transaction = transactionService.getTransaction(data.getTransactionId());
        if (transaction == null)
            throw new RuntimeException("Invalid transaction id");

        var user = userService.findUserByEmail(authentication.getName());
        if (!transaction.getAccount().getUser().equals(user))
            throw new RuntimeException("It is not your transaction");

        transaction.setLabelList(labels);
        transactionService.save(transaction);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(Collections.singletonMap("status", "ok"));
    }
}

@Data
class DataForSet {
    private Long transactionId;
    private List<Long> labelsIds;
}