package backend.project.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseDto {
  private Long id;
  private Long userId;
  private Long categoryId;
  private String categoryName;
  private BigDecimal amount;
  private String description;
  private LocalDate expenseDate;
}