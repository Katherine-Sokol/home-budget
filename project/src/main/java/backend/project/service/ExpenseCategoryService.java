package backend.project.service;

import backend.project.dto.ExpenseCategoryDto;
import backend.project.entity.ExpenseCategory;
import backend.project.exception.CategoryAlreadyExistsException;
import backend.project.exception.CategoryNotFoundException;
import static backend.project.exception.GlobalExceptionHandler.ERROR_CATEGORY_EXIST_MESSAGE;
import static backend.project.exception.GlobalExceptionHandler.ERROR_CATEGORY_NOT_FOUND_MESSAGE;
import backend.project.mapper.ExpenseCategoryMapper;
import backend.project.repository.ExpenseCategoryRepository;
import backend.project.request.ExpenseCategoryRequest;
import backend.project.user.User;
import backend.project.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseCategoryService {

  private final ExpenseCategoryRepository categoryRepository;
  private final ExpenseCategoryMapper categoryMapper;
  private final UserRepository userRepository;

  public List<ExpenseCategoryDto> getAll(Long userId) {
    return categoryMapper.toDtoList(categoryRepository.findAllByUserIdOrDefault(userId));
  }

  @Transactional
  public ExpenseCategoryDto create(ExpenseCategoryRequest dto, Long userId) {
    categoryRepository.findByNameAndUserId(dto.getName(), userId)
        .ifPresent(value -> {
          throw new CategoryAlreadyExistsException(String.format(ERROR_CATEGORY_EXIST_MESSAGE, dto.getName(), userId));
        });

    // it cannot be null, spring security would not guaranty access
    // if no user with such id exists
    User user = userRepository.findById(userId).get();

    ExpenseCategory category = new ExpenseCategory();
    category.setName(dto.getName());
    category.setUser(user);
    return categoryMapper.toDto(categoryRepository.save(category));
  }

  @Transactional
  public void deleteById(Long id) {
    if (!categoryRepository.existsById(id)) {
      throw new CategoryNotFoundException(String.format(ERROR_CATEGORY_NOT_FOUND_MESSAGE, id));
    }
    categoryRepository.deleteById(id);
  }
}