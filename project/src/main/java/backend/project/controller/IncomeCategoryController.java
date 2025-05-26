package backend.project.controller;

import backend.project.dto.IncomeCategoryDto;
import backend.project.service.IncomeCategoryService;
import backend.project.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class IncomeCategoryController {

  private final IncomeCategoryService categoryService;

  @GetMapping
  public List<IncomeCategoryDto> getAllCategories(@AuthenticationPrincipal User userDetails) {
    return categoryService.getAllCategories(userDetails.getId());
  }
}