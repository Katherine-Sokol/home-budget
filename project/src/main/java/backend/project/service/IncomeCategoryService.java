package backend.project.service;

import backend.project.dto.IncomeCategoryDto;
import backend.project.entity.IncomeCategory;
import backend.project.exception.CategoryAlreadyExistsException;
import backend.project.exception.CategoryNotFoundException;
import static backend.project.exception.GlobalExceptionHandler.ERROR_CATEGORY_EXIST_MESSAGE;
import static backend.project.exception.GlobalExceptionHandler.ERROR_CATEGORY_NOT_FOUND_MESSAGE;
import backend.project.mapper.IncomeCategoryMapper;
import backend.project.repository.IncomeCategoryRepository;
import backend.project.request.IncomeCategoryRequest;
import backend.project.user.User;
import backend.project.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeCategoryService {

  private final IncomeCategoryRepository categoryRepository;
  private final IncomeCategoryMapper categoryMapper;
  private final UserRepository userRepository;

  @Transactional
  public List<IncomeCategoryDto> getAllCategories(Long userId) {
    return categoryMapper.toDtoList(categoryRepository.findAllByUserId(userId));
  }

  @Transactional
  public IncomeCategoryDto create(IncomeCategoryRequest request, Long userId) {
    categoryRepository.findByNameAndUserId(request.getName(), userId)
        .ifPresent(value -> {
          throw new CategoryAlreadyExistsException(String.format(ERROR_CATEGORY_EXIST_MESSAGE, request.getName(), userId));
        });

    // it cannot be null, spring security would not guaranty access
    // if no user with such id exists
    User user = userRepository.findById(userId).get();

    IncomeCategory category = new IncomeCategory();
    category.setName(request.getName());
    category.setUser(user);
    return categoryMapper.toDto(categoryRepository.save(category));
  }

  @Transactional
  public void deleteById(Long id) {
    if (!categoryRepository.existsById(id)) {
      throw new CategoryNotFoundException(String.format(ERROR_CATEGORY_NOT_FOUND_MESSAGE,id));
    }
    categoryRepository.deleteById(id);
  }
}