package backend.project.mapper;

import backend.project.dto.ExpenseDto;
import backend.project.entity.Expense;
import backend.project.entity.ExpenseCategory;
import backend.project.request.CreateExpenseRequest;
import backend.project.user.User;
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

  public Expense toEntity(CreateExpenseRequest request, User user, ExpenseCategory category) {
    Expense expense = new Expense();
    expense.setUser(user);
    expense.setCategory(category);
    expense.setAmount(request.getAmount());
    expense.setDescription(request.getDescription());
    expense.setExpenseDate(request.getExpenseDate());
    return expense;
  }
}