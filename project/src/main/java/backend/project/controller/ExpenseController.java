package backend.project.controller;
import backend.project.dto.ExpenseDto;
import backend.project.service.ExpenseService;
import backend.project.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

  private final ExpenseService expenseService;

  @GetMapping
  public List<ExpenseDto> getByUserId(@AuthenticationPrincipal User userDetails) {
    return expenseService.getByUserId(userDetails.getId());
  }

  @GetMapping("/by-category/{categoryId}")
  public List<ExpenseDto> getByUserIdAndCategory(@AuthenticationPrincipal User userDetails, @PathVariable Long categoryId) {
    return expenseService.getByUserIdAndCategoryId(userDetails.getId(), categoryId);
  }

  @GetMapping("/between-dates")
  public List<ExpenseDto> getByDateRange(
      @AuthenticationPrincipal User userDetails,
      @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
      @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
    return expenseService.getByUserIdAndDateRange(userDetails.getId(), start, end);
  }

  @GetMapping("/by-amount")
  public List<ExpenseDto> getByExactAmount(@AuthenticationPrincipal User userDetails, @RequestParam BigDecimal amount) {
    return expenseService.getByUserIdAndAmount(userDetails.getId(), amount);
  }

  @GetMapping("/by-amount-range")
  public List<ExpenseDto> getByAmountRange(@AuthenticationPrincipal User userDetails,
                                           @RequestParam BigDecimal from,
                                           @RequestParam BigDecimal to) {
    return expenseService.getByUserIdAndAmountBetween(userDetails.getId(), from, to);
  }
}