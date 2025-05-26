package backend.project.service;

import backend.project.dto.IncomeCategoryDto;
import backend.project.mapper.IncomeCategoryMapper;
import backend.project.repository.IncomeCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeCategoryService {

  private final IncomeCategoryRepository categoryRepository;
  private final IncomeCategoryMapper categoryMapper;

  @Transactional
  public List<IncomeCategoryDto> getAllCategories(Long userId) {
    return categoryMapper.toDtoList(categoryRepository.findAllByUserId(userId));
  }
}