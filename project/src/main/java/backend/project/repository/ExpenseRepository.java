package backend.project.repository;

import backend.project.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
  Page<Expense> findAllByUserId(Long userId, Pageable pageable);

  Page<Expense> findAllByUserIdAndCategoryId(Long userId, Long categoryId, Pageable pageable);

  Page<Expense> findAllByUserIdAndExpenseDateBetween(Long userId, LocalDate start, LocalDate end, Pageable pageable);

  Page<Expense> findAllByUserIdAndAmount(Long userId, BigDecimal amount, Pageable pageable);

  Page<Expense> findAllByUserIdAndAmountBetween(Long userId, BigDecimal from, BigDecimal to, Pageable pageable);
}