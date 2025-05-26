package backend.project.entity;

import backend.project.user.User;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "expenses")
public class Expense {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private ExpenseCategory category;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "expense_date", nullable = false)
  private LocalDate expenseDate;
}
