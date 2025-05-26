package backend.project.controller;

import backend.project.dto.IncomeCategoryDto;
import backend.project.request.IncomeCategoryRequest;
import backend.project.service.IncomeCategoryService;
import backend.project.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/income-categories")
@RequiredArgsConstructor
public class IncomeCategoryController {

  private final IncomeCategoryService categoryService;

  @GetMapping
  public List<IncomeCategoryDto> getAllCategories(@AuthenticationPrincipal User userDetails) {
    return categoryService.getAllCategories(userDetails.getId());
  }

  @PostMapping
  public IncomeCategoryDto createCategory(@RequestBody @Valid IncomeCategoryRequest request,
                                          @AuthenticationPrincipal User userDetails) {
    return categoryService.create(request, userDetails.getId());
  }

  @DeleteMapping("/{id}")
  public void deleteCategory(@PathVariable Long id) {
    categoryService.deleteById(id);
  }
}