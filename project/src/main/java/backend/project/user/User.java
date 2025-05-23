package backend.project.user;

import backend.project.entity.Debt;
import backend.project.entity.Expense;
import backend.project.entity.ExpenseCategory;
import backend.project.entity.Goal;
import backend.project.entity.Income;
import backend.project.entity.IncomeCategory;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
@Entity
@Table(name="users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 50)
  private String username;

//  @Column(nullable = false, unique = true, length = 100)
//  private String email;

  @Column(name = "password", nullable = false, length = 255)
  private String password;

  // Відносини
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<IncomeCategory> incomeCategories = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<ExpenseCategory> expenseCategories = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Income> incomes = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Expense> expenses = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Goal> goals = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Debt> debts = new HashSet<>();
}
