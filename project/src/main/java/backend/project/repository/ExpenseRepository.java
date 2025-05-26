package backend.project.repository;

import backend.project.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
  List<Expense> findAllByUserId(Long userId);

  List<Expense> findAllByUserIdAndCategoryId(Long userId, Long categoryId);

  List<Expense> findAllByUserIdAndExpenseDateBetween(Long userId, LocalDate start, LocalDate end);

  List<Expense> findAllByUserIdAndAmount(Long userId, BigDecimal amount);

  List<Expense> findAllByUserIdAndAmountBetween(Long userId, BigDecimal from, BigDecimal to);
}