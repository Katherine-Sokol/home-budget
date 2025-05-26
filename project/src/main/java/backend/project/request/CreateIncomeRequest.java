package backend.project.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIncomeRequest {
  @NotNull(message = "Category ID is required")
  private Long categoryId;
  @NotNull(message = "Amount is required")
  @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
  private BigDecimal amount;
  @Size(max = 500, message = "Description must be less than 500 characters")
  private String description;
  @NotNull(message = "Income date is required") // todo create own check of date field
  private LocalDate incomeDate;
}