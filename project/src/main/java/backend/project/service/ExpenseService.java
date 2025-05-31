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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  public Page<ExpenseDto> getByUserId(Long userId, Pageable pageable) {
    Page<Expense> expenses = expenseRepository.findAllByUserId(userId, pageable);
    return expenses.map(expenseMapper::toDto);
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

  public List<ExpenseDto> getByUserIdAndCategoryId(Long userId, Long categoryId, Pageable pageable) {
    Page<Expense> expenses = expenseRepository.findAllByUserIdAndCategoryId(userId, categoryId, pageable);
    return expenses.getContent().stream().map(expenseMapper::toDto).toList();
  }

  public Page<ExpenseDto> getByUserIdAndDateRange(Long userId, LocalDate start, LocalDate end, Pageable pageable) {
    Page<Expense> expenses = expenseRepository.findAllByUserIdAndExpenseDateBetween(userId, start, end, pageable);
    return expenses.map(expenseMapper::toDto);
  }

  public List<ExpenseDto> getByUserIdAndAmount(Long userId, BigDecimal amount, Pageable pageable) {
    Page<Expense> expenses = expenseRepository.findAllByUserIdAndAmount(userId, amount, pageable);
    return expenses.getContent().stream().map(expenseMapper::toDto).toList();
  }

  public List<ExpenseDto> getByUserIdAndAmountBetween(Long userId, BigDecimal from, BigDecimal to, Pageable pageable) {
    Page<Expense> expenses = expenseRepository.findAllByUserIdAndAmountBetween(userId, from, to, pageable);
    return expenses.getContent().stream().map(expenseMapper::toDto).toList();
  }
}