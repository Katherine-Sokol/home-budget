package backend.project.service;

import backend.project.dto.ExpenseDto;
import backend.project.mapper.ExpenseMapper;
import backend.project.repository.ExpenseRepository;
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
  private final ExpenseMapper expenseMapper;

  @Transactional
  public List<ExpenseDto> getByUserId(Long userId) {
    return expenseRepository.findAllByUserId(userId)
        .stream().map(expenseMapper::toDto).toList();
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