package backend.project.service;

import backend.project.dto.IncomeDto;
import backend.project.entity.Income;
import backend.project.entity.IncomeCategory;
import backend.project.exception.CategoryNotFoundException;
import backend.project.exception.EntityNotBelongToUserException;
import static backend.project.exception.GlobalExceptionHandler.ERROR_ACCESS_DENIED_MESSAGE;
import static backend.project.exception.GlobalExceptionHandler.ERROR_CATEGORY_NOT_FOUND_MESSAGE;
import static backend.project.exception.GlobalExceptionHandler.ERROR_INCOME_NOT_FOUND_MESSAGE;
import backend.project.exception.IncomeNotFoundException;
import backend.project.mapper.IncomeMapper;
import backend.project.repository.IncomeCategoryRepository;
import backend.project.repository.IncomeRepository;
import backend.project.request.CreateIncomeRequest;
import backend.project.user.User;
import backend.project.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeService {

  private final IncomeRepository incomeRepository;
  private final IncomeCategoryRepository incomeCategoryRepository;
  private final IncomeMapper incomeMapper;
  private final UserRepository userRepository;

  // 1. Get all incomes by user ID
  public List<IncomeDto> getIncomesByUserId(Long userId) {
    return incomeRepository.findAllByUserId(userId)
        .stream()
        .map(incomeMapper::toDto)
        .collect(Collectors.toList());
  }

  @Transactional
  public IncomeDto create(Long userId, CreateIncomeRequest request) {
    IncomeCategory category;
    category = incomeCategoryRepository.findById(request.getCategoryId())
        .orElseThrow(() -> new CategoryNotFoundException(String.format(ERROR_CATEGORY_NOT_FOUND_MESSAGE, request.getCategoryId())));

    // it cannot be null, spring security would not guaranty access
    // if no user with such id exists
    User user = userRepository.findById(userId).get();

    Income income = incomeMapper.toEntity(request, user, category);
    Income saved = incomeRepository.save(income);
    return incomeMapper.toDto(saved);
  }

  @Transactional
  public void deleteById(Long id, Long userId) {
    Income income = incomeRepository.findById(id)
        .orElseThrow(() -> new IncomeNotFoundException(String.format(ERROR_INCOME_NOT_FOUND_MESSAGE, id)));
    if (!income.getUser().getId().equals(userId)) {
      throw new EntityNotBelongToUserException(ERROR_ACCESS_DENIED_MESSAGE);
    }
    incomeRepository.deleteById(id);
  }


  // 2. Get all income by user ID and category ID
  public List<IncomeDto> getAllIncomesByCategory(Long userId, Long categoryId) {
    return incomeRepository.findAllByUserIdAndCategoryId(userId, categoryId)
        .stream()
        .map(incomeMapper::toDto)
        .collect(Collectors.toList());
  }

  // 3. Get all incomes by user ID and date range
  public List<IncomeDto> getIncomesByUserIdBetweenDates(Long userId, LocalDate start, LocalDate end) {
    return incomeRepository.findAllByUserIdAndIncomeDateBetween(userId, start, end)
        .stream()
        .map(incomeMapper::toDto)
        .collect(Collectors.toList());
  }

  // 4. Get all incomes by user ID and exact amount
  public List<IncomeDto> getIncomesByUserIdAndAmount(Long userId, BigDecimal amount) {
    return incomeRepository.findAllByUserIdAndAmount(userId, amount)
        .stream()
        .map(incomeMapper::toDto)
        .collect(Collectors.toList());
  }

  // 5. Get all incomes by user ID and amount range
  public List<IncomeDto> getIncomesByUserIdAndAmountBetween(Long userId, BigDecimal from, BigDecimal to) {
    return incomeRepository.findAllByUserIdAndAmountBetween(userId, from, to)
        .stream()
        .map(incomeMapper::toDto)
        .collect(Collectors.toList());
  }
}