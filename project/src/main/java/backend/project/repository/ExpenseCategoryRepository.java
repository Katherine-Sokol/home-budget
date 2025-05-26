package backend.project.repository;

import backend.project.entity.ExpenseCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {
  List<ExpenseCategory> findAllByUserId(Long userId);

  Optional<ExpenseCategory> findByNameAndUserId(String name, Long userId);
}