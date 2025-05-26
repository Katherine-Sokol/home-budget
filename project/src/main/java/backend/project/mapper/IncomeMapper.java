package backend.project.mapper;

import backend.project.dto.IncomeDto;
import backend.project.entity.Income;
import backend.project.entity.IncomeCategory;
import backend.project.request.CreateIncomeRequest;
import backend.project.user.User;
import org.springframework.stereotype.Component;

@Component
public class IncomeMapper {

  public IncomeDto toDto(Income income) {
    IncomeDto dto = new IncomeDto();
    dto.setId(income.getId());
    dto.setUserId(income.getUser().getId());
    dto.setCategoryId(income.getCategory() != null ? income.getCategory().getId() : null);
    dto.setAmount(income.getAmount());
    dto.setDescription(income.getDescription());
    dto.setIncomeDate(income.getIncomeDate());
    return dto;
  }

  public Income toEntity(CreateIncomeRequest request, User user, IncomeCategory category) {
    Income income = new Income();
    income.setUser(user);
    income.setCategory(category);
    income.setAmount(request.getAmount());
    income.setDescription(request.getDescription());
    income.setIncomeDate(request.getIncomeDate());
    return income;
  }
}