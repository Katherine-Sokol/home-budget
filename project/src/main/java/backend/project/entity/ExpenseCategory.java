package backend.project.entity;

import backend.project.user.User;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "expense_categories",
    uniqueConstraints = @UniqueConstraint(columnNames = {"name", "user_id"}))
public class ExpenseCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user; // nullable для загальних категорій

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Expense> expenses = new HashSet<>();

  public ExpenseCategory(String name, User user) {
    this.name = name;
    this.user = user;
  }
}

