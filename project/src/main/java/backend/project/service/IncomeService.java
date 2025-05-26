package backend.project.service;

import backend.project.dto.IncomeDto;
import backend.project.entity.Income;
import backend.project.mapper.IncomeMapper;
import backend.project.repository.IncomeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class IncomeService {

  private final IncomeRepository incomeRepository;
  private final IncomeMapper incomeMapper;

  // 1. Get all incomes by user ID
  public List<IncomeDto> getIncomesByUserId(Long userId) {
    return incomeRepository.findAllByUserId(userId)
        .stream()
        .map(incomeMapper::toDto)
        .collect(Collectors.toList());
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