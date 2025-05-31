package backend.project.controller;

import backend.project.dto.ExpenseDto;
import backend.project.request.CreateExpenseRequest;
import backend.project.service.ExpenseService;
import backend.project.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
  private static final String DEFAULT_SORT_FIELD = "expenseDate";
  private static final String DEFAULT_SORT_DIRECTION = "asc";
  private static final String DEFAULT_LIMIT = "10";
  private static final String DEFAULT_PAGE = "0";
  private final ExpenseService expenseService;

  @GetMapping
  public Page<ExpenseDto> getByUserId(@AuthenticationPrincipal User userDetails,
                                      @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortField,
                                      @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDir,
                                      @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                      @RequestParam(defaultValue = DEFAULT_LIMIT) int limit) {
    Sort sort = sortDir.equalsIgnoreCase(DEFAULT_SORT_DIRECTION) ?
        Sort.by(sortField).ascending() :
        Sort.by(sortField).descending();

    Pageable pageable = PageRequest.of(page, limit, sort);

    return expenseService.getByUserId(userDetails.getId(), pageable);
  }

  @PostMapping
  public ResponseEntity<ExpenseDto> create(@AuthenticationPrincipal User userDetails,
                                           @RequestBody @Valid CreateExpenseRequest request) {
    ExpenseDto created = expenseService.create(userDetails.getId(), request);
    return ResponseEntity.ok(created);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@AuthenticationPrincipal User userDetails,
                                     @PathVariable Long id) {
    expenseService.deleteById(id, userDetails.getId());
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/by-category/{categoryId}")
  public List<ExpenseDto> getByUserIdAndCategory(@AuthenticationPrincipal User userDetails,
                                                 @PathVariable Long categoryId,
                                                 @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortField,
                                                 @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDir,
                                                 @RequestParam(defaultValue = DEFAULT_LIMIT) int limit) {
    Sort sort = sortDir.equalsIgnoreCase(DEFAULT_SORT_DIRECTION) ?
        Sort.by(sortField).ascending() :
        Sort.by(sortField).descending();

    Pageable pageable = PageRequest.of(0, limit, sort);
    return expenseService.getByUserIdAndCategoryId(userDetails.getId(), categoryId, pageable);
  }

  @GetMapping("/between-dates")
  public Page<ExpenseDto> getByDateRange(
      @AuthenticationPrincipal User userDetails,
      @RequestParam(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
      @RequestParam(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
      @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortField,
      @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDir,
      @RequestParam(defaultValue = DEFAULT_PAGE) int page,
      @RequestParam(defaultValue = DEFAULT_LIMIT) int limit) {
    Sort sort = sortDir.equalsIgnoreCase(DEFAULT_SORT_DIRECTION) ?
        Sort.by(sortField).ascending() :
        Sort.by(sortField).descending();
    Pageable pageable = PageRequest.of(page, limit, sort);
    return expenseService.getByUserIdAndDateRange(userDetails.getId(), start, end, pageable);
  }

  @GetMapping("/by-amount")
  public List<ExpenseDto> getByExactAmount(@AuthenticationPrincipal User userDetails,
                                           @RequestParam BigDecimal amount,
                                           @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortField,
                                           @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDir,
                                           @RequestParam(defaultValue = DEFAULT_LIMIT) int limit) {
    Sort sort = sortDir.equalsIgnoreCase(DEFAULT_SORT_DIRECTION) ?
        Sort.by(sortField).ascending() :
        Sort.by(sortField).descending();

    Pageable pageable = PageRequest.of(0, limit, sort);
    return expenseService.getByUserIdAndAmount(userDetails.getId(), amount, pageable);
  }

  @GetMapping("/by-amount-range")
  public List<ExpenseDto> getByAmountRange(@AuthenticationPrincipal User userDetails,
                                           @RequestParam BigDecimal from,
                                           @RequestParam BigDecimal to,
                                           @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortField,
                                           @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDir,
                                           @RequestParam(defaultValue = DEFAULT_LIMIT) int limit) {
    Sort sort = sortDir.equalsIgnoreCase(DEFAULT_SORT_DIRECTION) ?
        Sort.by(sortField).ascending() :
        Sort.by(sortField).descending();

    Pageable pageable = PageRequest.of(0, limit, sort);
    return expenseService.getByUserIdAndAmountBetween(userDetails.getId(), from, to, pageable);
  }
}

