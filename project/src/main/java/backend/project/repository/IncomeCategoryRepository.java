package backend.project.repository;

import backend.project.entity.IncomeCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory, Long> {
  List<IncomeCategory> findAllByUserId(Long userId);

  Optional<IncomeCategory> findByNameAndUserId(String name, Long userId);

}