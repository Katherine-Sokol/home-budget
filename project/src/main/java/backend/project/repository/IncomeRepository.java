package backend.project.repository;

import backend.project.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

  // 1. Get all incomes by user_id
  List<Income> findAllByUserId(Long userId);

  // 2. Get all incomes by user_id and category_id
  List<Income> findAllByUserIdAndCategoryId(Long userId, Long categoryId);

  // 3. Get all incomes by user_id between dates start and end
  List<Income> findAllByUserIdAndIncomeDateBetween(Long userId, LocalDate start, LocalDate end);

  // 4. Get all incomes by user_id and exact amount
  List<Income> findAllByUserIdAndAmount(Long userId, BigDecimal amount);

  // 5. Get all incomes by user_id and amount between start and end
  List<Income> findAllByUserIdAndAmountBetween(Long userId, BigDecimal startAmount, BigDecimal endAmount);
}