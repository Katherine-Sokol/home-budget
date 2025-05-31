package backend.project.repository;

import backend.project.entity.ExpenseCategory;
import backend.project.entity.IncomeCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {
  List<ExpenseCategory> findAllByUserId(Long userId);

  @Query("SELECT c FROM ExpenseCategory c WHERE c.user.id = :userId OR c.user IS NULL")
  List<ExpenseCategory> findAllByUserIdOrDefault(@Param("userId") Long userId);

  Optional<ExpenseCategory> findByNameAndUserId(String name, Long userId);
}