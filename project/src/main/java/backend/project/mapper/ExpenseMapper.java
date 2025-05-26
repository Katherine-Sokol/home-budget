package backend.project.mapper;

import backend.project.dto.ExpenseDto;
import backend.project.entity.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

  public ExpenseDto toDto(Expense expense) {
    ExpenseDto dto = new ExpenseDto();
    dto.setId(expense.getId());
    dto.setUserId(expense.getUser().getId());
    if (expense.getCategory() != null) {
      dto.setCategoryId(expense.getCategory().getId());
      dto.setCategoryName(expense.getCategory().getName());
    }
    dto.setAmount(expense.getAmount());
    dto.setDescription(expense.getDescription());
    dto.setExpenseDate(expense.getExpenseDate());
    return dto;
  }
}