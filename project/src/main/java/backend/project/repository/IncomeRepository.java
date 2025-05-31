package backend.project.repository;

import backend.project.entity.Income;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

  // 1. Get all incomes by user_id
  Page<Income> findAllByUserId(Long userId, Pageable pageable);

  // 2. Get all incomes by user_id and category_id
  Page<Income> findAllByUserIdAndCategoryId(Long userId, Long categoryId, Pageable pageable);

  // 3. Get all incomes by user_id between dates start and end
  Page<Income> findAllByUserIdAndIncomeDateBetween(Long userId, LocalDate start, LocalDate end, Pageable pageable);

  // 4. Get all incomes by user_id and exact amount
  Page<Income> findAllByUserIdAndAmount(Long userId, BigDecimal amount, Pageable pageable);

  // 5. Get all incomes by user_id and amount between start and end
  Page<Income> findAllByUserIdAndAmountBetween(Long userId, BigDecimal startAmount, BigDecimal endAmount, Pageable pageable);
}