package backend.project.service;

import backend.project.dto.IncomeDto;
import backend.project.entity.Income;
import backend.project.entity.IncomeCategory;
import backend.project.exception.CategoryNotFoundException;
import backend.project.exception.EntityNotBelongToUserException;
import static backend.project.exception.GlobalExceptionHandler.ERROR_ACCESS_DENIED_MESSAGE;
import static backend.project.exception.GlobalExceptionHandler.ERROR_CATEGORY_NOT_FOUND_MESSAGE;
import static backend.project.exception.GlobalExceptionHandler.ERROR_INCOME_NOT_FOUND_MESSAGE;
import backend.project.exception.IncomeNotFoundException;
import backend.project.mapper.IncomeMapper;
import backend.project.repository.IncomeCategoryRepository;
import backend.project.repository.IncomeRepository;
import backend.project.request.CreateIncomeRequest;
import backend.project.user.User;
import backend.project.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {

  private final IncomeRepository incomeRepository;
  private final IncomeCategoryRepository incomeCategoryRepository;
  private final IncomeMapper incomeMapper;
  private final UserRepository userRepository;

  // 1. Get all incomes by user ID
  public Page<IncomeDto> getIncomesByUserId(Long userId, Pageable pageable) {
    Page<Income> incomes = incomeRepository.findAllByUserId(userId, pageable);

    return incomes.map(incomeMapper::toDto);
  }

  @Transactional
  public IncomeDto create(Long userId, CreateIncomeRequest request) {
    IncomeCategory category;
    category = incomeCategoryRepository.findById(request.getCategoryId())
        .orElseThrow(() -> new CategoryNotFoundException(String.format(ERROR_CATEGORY_NOT_FOUND_MESSAGE, request.getCategoryId())));

    // it cannot be null, spring security would not guaranty access
    // if no user with such id exists
    User user = userRepository.findById(userId).get();

    Income income = incomeMapper.toEntity(request, user, category);
    Income saved = incomeRepository.save(income);
    return incomeMapper.toDto(saved);
  }

  @Transactional
  public void deleteById(Long id, Long userId) {
    Income income = incomeRepository.findById(id)
        .orElseThrow(() -> new IncomeNotFoundException(String.format(ERROR_INCOME_NOT_FOUND_MESSAGE, id)));
    if (!income.getUser().getId().equals(userId)) {
      throw new EntityNotBelongToUserException(ERROR_ACCESS_DENIED_MESSAGE);
    }
    incomeRepository.deleteById(id);
  }


  // 2. Get all income by user ID and category ID
  public List<IncomeDto> getAllIncomesByCategory(Long userId, Long categoryId, Pageable pageable) {
    Page<Income> incomes = incomeRepository.findAllByUserIdAndCategoryId(userId, categoryId, pageable);
    return incomes.getContent().stream().map(incomeMapper::toDto).toList();
  }

  // 3. Get all incomes by user ID and date range
  public Page<IncomeDto> getIncomesByUserIdBetweenDates(Long userId, LocalDate start, LocalDate end, Pageable pageable) {
    Page<Income> incomes = incomeRepository.findAllByUserIdAndIncomeDateBetween(userId, start, end, pageable);
    return incomes.map(incomeMapper::toDto);
  }

  // 4. Get all incomes by user ID and exact amount
  public List<IncomeDto> getIncomesByUserIdAndAmount(Long userId, BigDecimal amount, Pageable pageable) {
    Page<Income> incomes = incomeRepository.findAllByUserIdAndAmount(userId, amount, pageable);
    return incomes.getContent().stream().map(incomeMapper::toDto).toList();
  }
  // 5. Get all incomes by user ID and amount range
  public List<IncomeDto> getIncomesByUserIdAndAmountBetween(Long userId, BigDecimal from, BigDecimal to, Pageable pageable) {
    Page<Income> incomes = incomeRepository.findAllByUserIdAndAmountBetween(userId, from, to, pageable);
    return incomes.getContent().stream().map(incomeMapper::toDto).toList();
  }
}