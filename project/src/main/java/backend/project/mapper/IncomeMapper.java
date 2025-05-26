package backend.project.mapper;

import backend.project.dto.IncomeDto;
import backend.project.entity.Income;
import backend.project.entity.IncomeCategory;
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

  public Income toEntity(IncomeDto dto, User user, IncomeCategory category) {
    return new Income(
        user,
        category,
        dto.getAmount(),
        dto.getDescription(),
        dto.getIncomeDate()
    );
  }
}