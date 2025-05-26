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
@Table(name = "incomes")
public class Income {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Зв'язок з користувачем (user_id)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  // Зв'язок з категорією доходу (category_id)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private IncomeCategory category;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(name = "income_date", nullable = false)
  private LocalDate incomeDate;

  public Income(User user, IncomeCategory category, BigDecimal amount, String description, LocalDate incomeDate) {
    this.user = user;
    this.category = category;
    this.amount = amount;
    this.description = description;
    this.incomeDate = incomeDate;
  }
}
