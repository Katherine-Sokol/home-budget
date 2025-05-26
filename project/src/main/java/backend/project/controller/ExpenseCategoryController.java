package backend.project.controller;

import backend.project.dto.ExpenseCategoryDto;
import backend.project.request.ExpenseCategoryRequest;
import backend.project.service.ExpenseCategoryService;
import backend.project.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense-categories")
@RequiredArgsConstructor
public class ExpenseCategoryController {

  private final ExpenseCategoryService expenseCategoryService;

  @GetMapping
  public List<ExpenseCategoryDto> getAllCategories(@AuthenticationPrincipal User userDetails) {
    return expenseCategoryService.getAll(userDetails.getId());
  }

  @PostMapping
  public ExpenseCategoryDto createCategory(@RequestBody @Valid ExpenseCategoryRequest request,
                                           @AuthenticationPrincipal User userDetails) {
    return expenseCategoryService.create(request, userDetails.getId());
  }

  @DeleteMapping("/{id}")
  public void deleteCategory(@PathVariable Long id) {
    expenseCategoryService.deleteById(id);
  }
}