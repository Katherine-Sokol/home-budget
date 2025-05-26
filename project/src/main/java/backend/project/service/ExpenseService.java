package backend.project.service;

import backend.project.dto.ExpenseDto;
import backend.project.entity.Expense;
import backend.project.entity.ExpenseCategory;
import backend.project.exception.CategoryNotFoundException;
import backend.project.exception.EntityNotBelongToUserException;
import backend.project.exception.ExpenseNotFoundException;
import static backend.project.exception.GlobalExceptionHandler.ERROR_ACCESS_DENIED_MESSAGE;
import static backend.project.exception.GlobalExceptionHandler.ERROR_CATEGORY_NOT_FOUND_MESSAGE;
import static backend.project.exception.GlobalExceptionHandler.ERROR_EXPENSE_NOT_FOUND_MESSAGE;
import backend.project.mapper.ExpenseMapper;
import backend.project.repository.ExpenseCategoryRepository;
import backend.project.repository.ExpenseRepository;
import backend.project.request.CreateExpenseRequest;
import backend.project.user.User;
import backend.project.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {
  private final ExpenseRepository expenseRepository;
  private final ExpenseCategoryRepository categoryRepository;
  private final ExpenseMapper expenseMapper;
  private final UserRepository userRepository;

  @Transactional
  public List<ExpenseDto> getByUserId(Long userId) {
    return expenseRepository.findAllByUserId(userId)
        .stream().map(expenseMapper::toDto).toList();
  }

  @Transactional
  public ExpenseDto create(Long userId, CreateExpenseRequest request) {
    ExpenseCategory category = categoryRepository.findById(request.getCategoryId())
        .orElseThrow(() -> new CategoryNotFoundException(String.format(ERROR_CATEGORY_NOT_FOUND_MESSAGE, request.getCategoryId())));

    // it cannot be null, spring security would not guaranty access
    // if no user with such id exists
    User user = userRepository.findById(userId).get();

    Expense expense = expenseMapper.toEntity(request, user, category);
    Expense saved = expenseRepository.save(expense);
    return expenseMapper.toDto(saved);
  }

  @Transactional
  public void deleteById(Long id, Long userId) {
    Expense expense = expenseRepository.findById(id)
        .orElseThrow(() -> new ExpenseNotFoundException(String.format(ERROR_EXPENSE_NOT_FOUND_MESSAGE, id)));
    if (!expense.getUser().getId().equals(userId)) {
      throw new EntityNotBelongToUserException(ERROR_ACCESS_DENIED_MESSAGE);
    }
    expenseRepository.deleteById(id);
  }

  @Transactional
  public List<ExpenseDto> getByUserIdAndCategoryId(Long userId, Long categoryId) {
    return expenseRepository.findAllByUserIdAndCategoryId(userId, categoryId)
        .stream().map(expenseMapper::toDto).toList();
  }

  @Transactional
  public List<ExpenseDto> getByUserIdAndDateRange(Long userId, LocalDate start, LocalDate end) {
    return expenseRepository.findAllByUserIdAndExpenseDateBetween(userId, start, end)
        .stream().map(expenseMapper::toDto).toList();
  }

  @Transactional
  public List<ExpenseDto> getByUserIdAndAmount(Long userId, BigDecimal amount) {
    return expenseRepository.findAllByUserIdAndAmount(userId, amount)
        .stream().map(expenseMapper::toDto).toList();
  }

  @Transactional
  public List<ExpenseDto> getByUserIdAndAmountBetween(Long userId, BigDecimal from, BigDecimal to) {
    return expenseRepository.findAllByUserIdAndAmountBetween(userId, from, to)
        .stream().map(expenseMapper::toDto).toList();
  }
}