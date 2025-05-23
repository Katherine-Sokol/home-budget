package backend.project.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
@Table(name = "goal_contributions")
public class GoalContribution {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "goal_id")
  private Goal goal;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Column(name = "contribution_date", nullable = false)
  private LocalDate contributionDate;

  @Column(columnDefinition = "TEXT")
  private String note;

  public GoalContribution() {}

  // Геттери і сеттери
}
