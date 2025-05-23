package backend.project.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
@Table(name = "debt_payments")
public class DebtPayment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "debt_id", nullable = false)
  private Debt debt;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Column(name = "payment_date", nullable = false)
  private LocalDate paymentDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "income_id")
  private Income income;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "expense_id")
  private Expense expense;

  @Column(columnDefinition = "TEXT")
  private String note;

  public DebtPayment() {}

  // Геттери і сеттери
}
