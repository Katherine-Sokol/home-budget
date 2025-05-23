package backend.project.entity;

import backend.project.user.User;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;


@Data
@Entity
@Table(name = "goals")
public class Goal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(name = "target_amount", nullable = false, precision = 10, scale = 2)
  private BigDecimal targetAmount;

  @Column(name = "current_amount", nullable = false, precision = 10, scale = 2)
  private BigDecimal currentAmount = BigDecimal.ZERO;

  @Column(name = "due_date")
  private LocalDate dueDate;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(nullable = false)
  private Boolean completed = false;

  @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<GoalContribution> contributions = new HashSet<>();

  public Goal() {}

  // Геттери і сеттери
}
