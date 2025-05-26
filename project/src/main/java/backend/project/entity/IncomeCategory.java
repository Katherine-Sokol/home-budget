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
@Table(name = "income_categories",
    uniqueConstraints = @UniqueConstraint(columnNames = {"name", "user_id"}))
public class IncomeCategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String name;

  // Зв'язок з користувачем (user_id), nullable = true (для загальних категорій)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  // Зворотній зв'язок - категорія має багато доходів
  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Income> incomes = new HashSet<>();

  public IncomeCategory(String name, User user) {
    this.name = name;
    this.user = user;
  }
}
