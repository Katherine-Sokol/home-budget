package backend.project.mapper;

import backend.project.dto.ExpenseCategoryDto;
import backend.project.entity.ExpenseCategory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpenseCategoryMapper {
  public ExpenseCategoryDto toDto(ExpenseCategory category) {
    return new ExpenseCategoryDto(category.getId(), category.getName());
  }

  public List<ExpenseCategoryDto> toDtoList(List<ExpenseCategory> categories) {
    return categories.stream().map(this::toDto).toList();
  }
}