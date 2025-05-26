package backend.project.mapper;

import backend.project.entity.IncomeCategory;
import backend.project.dto.IncomeCategoryDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IncomeCategoryMapper {

  public IncomeCategoryDto toDto(IncomeCategory category) {
    return new IncomeCategoryDto(category.getId(), category.getName());
  }

  public List<IncomeCategoryDto> toDtoList(List<IncomeCategory> categories) {
    return categories.stream().map(this::toDto).toList();
  }
}