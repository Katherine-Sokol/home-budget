package backend.project.controller;
import backend.project.dto.IncomeDto;
import backend.project.request.CreateIncomeRequest;
import backend.project.service.IncomeService;
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
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/incomes")
@RequiredArgsConstructor
public class IncomeController {
  private static final String DEFAULT_SORT_FIELD = "incomeDate";
  private static final String DEFAULT_SORT_DIRECTION = "asc";
  private static final String DEFAULT_LIMIT = "10";
  private static final String DEFAULT_PAGE = "0";
  private final IncomeService incomeService;

  // 1. Get all incomes
  @GetMapping
  public Page<IncomeDto> getAllIncomes(@AuthenticationPrincipal User userDetails,
                                       @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortField,
                                       @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDir,
                                       @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                       @RequestParam(defaultValue = DEFAULT_LIMIT) int limit)
  {
    Sort sort = sortDir.equalsIgnoreCase(DEFAULT_SORT_DIRECTION) ?
        Sort.by(sortField).ascending() :
        Sort.by(sortField).descending();

    Pageable pageable = PageRequest.of(page, limit, sort);

    return incomeService.getIncomesByUserId(userDetails.getId(), pageable);
  }

  @PostMapping
  public ResponseEntity<IncomeDto> create(@AuthenticationPrincipal User userDetails,
                                          @RequestBody @Valid CreateIncomeRequest request) {
    IncomeDto created = incomeService.create(userDetails.getId(), request);
    return ResponseEntity.ok(created);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@AuthenticationPrincipal User userDetails,
                                     @PathVariable Long id) {
    incomeService.deleteById(id, userDetails.getId());
    return ResponseEntity.noContent().build();
  }

  // 2. Get income amounts by category
  @GetMapping("/by-category/{categoryId}")
  public List<IncomeDto> getAllIncomesByCategory(@PathVariable Long categoryId,
                                                 @AuthenticationPrincipal User userDetails,
                                                 @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortField,
                                                 @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDir,
                                                 @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                 @RequestParam(defaultValue = DEFAULT_LIMIT) int limit)
  {
    Sort sort = sortDir.equalsIgnoreCase(DEFAULT_SORT_DIRECTION) ?
        Sort.by(sortField).ascending() :
        Sort.by(sortField).descending();

    Pageable pageable = PageRequest.of(page, limit, sort);

    return incomeService.getAllIncomesByCategory(userDetails.getId(), categoryId, pageable);
  }

  // 3. Get incomes between two dates
  @GetMapping("/between-dates")
  public Page<IncomeDto> getIncomesBetweenDates(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                                @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                                                @AuthenticationPrincipal User userDetails,
                                                @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortField,
                                                @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDir,
                                                @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                @RequestParam(defaultValue = DEFAULT_LIMIT) int limit)
  {
    Sort sort = sortDir.equalsIgnoreCase(DEFAULT_SORT_DIRECTION) ?
        Sort.by(sortField).ascending() :
        Sort.by(sortField).descending();

    Pageable pageable = PageRequest.of(page, limit, sort);
    return incomeService.getIncomesByUserIdBetweenDates(userDetails.getId(), start, end, pageable);
  }

  // 4. Get incomes by exact amount
  @GetMapping("/by-amount")
  public List<IncomeDto> getIncomesByExactAmount(@RequestParam BigDecimal amount,
                                                 @AuthenticationPrincipal User userDetails,
                                                 @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortField,
                                                 @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDir,
                                                 @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                 @RequestParam(defaultValue = DEFAULT_LIMIT) int limit)
  {
    Sort sort = sortDir.equalsIgnoreCase(DEFAULT_SORT_DIRECTION) ?
        Sort.by(sortField).ascending() :
        Sort.by(sortField).descending();

    Pageable pageable = PageRequest.of(page, limit, sort);
    return incomeService.getIncomesByUserIdAndAmount(userDetails.getId(), amount, pageable);
  }

  // 5. Get incomes in an amount range
  @GetMapping("/by-amount-range")
  public List<IncomeDto> getIncomesByAmountRange(@RequestParam BigDecimal from,
                                                 @RequestParam BigDecimal to,
                                                 @AuthenticationPrincipal User userDetails,
                                                 @RequestParam(defaultValue = DEFAULT_SORT_FIELD) String sortField,
                                                 @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) String sortDir,
                                                 @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                                 @RequestParam(defaultValue = DEFAULT_LIMIT) int limit)
  {
    Sort sort = sortDir.equalsIgnoreCase(DEFAULT_SORT_DIRECTION) ?
        Sort.by(sortField).ascending() :
        Sort.by(sortField).descending();

    Pageable pageable = PageRequest.of(page, limit, sort);
    return incomeService.getIncomesByUserIdAndAmountBetween(userDetails.getId(), from, to, pageable);
  }
}