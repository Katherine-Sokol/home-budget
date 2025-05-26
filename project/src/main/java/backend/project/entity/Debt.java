package backend.project.entity;

import backend.project.user.User;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "debts")
public class Debt {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "counterparty_name", nullable = false, length = 100)
  private String counterpartyName;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Column(nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  private DebtType type;

  @Column(nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  private DebtStatus status = DebtStatus.UNPAID;

  @Column(name = "issue_date", nullable = false)
  private LocalDate issueDate = LocalDate.now();

  @Column(name = "due_date")
  private LocalDate dueDate;

  @Column(columnDefinition = "TEXT")
  private String note;

  @OneToMany(mappedBy = "debt", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<DebtPayment> payments = new HashSet<>();

  public enum DebtType {
    GIVEN, TAKEN
  }

  public enum DebtStatus {
    UNPAID, PAID
  }
}
