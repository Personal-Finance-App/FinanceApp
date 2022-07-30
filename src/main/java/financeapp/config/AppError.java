package financeapp.config;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppError {
    private String error;
}
