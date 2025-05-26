package backend.project.service;

import backend.project.dto.ExpenseCategoryDto;
import backend.project.mapper.ExpenseCategoryMapper;
import backend.project.repository.ExpenseCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseCategoryService {

  private final ExpenseCategoryRepository categoryRepository;
  private final ExpenseCategoryMapper categoryMapper;

  @Transactional
  public List<ExpenseCategoryDto> getAll(Long userId) {
    return categoryMapper.toDtoList(categoryRepository.findAllByUserId(userId));
  }
}