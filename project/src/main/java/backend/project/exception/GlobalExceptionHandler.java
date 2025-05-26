package backend.project.exception;

import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

  public static final String ERROR_CATEGORY_EXIST_MESSAGE = "Category with name {%s} already exists for the user id {%d}";
  public static final String ERROR_CATEGORY_NOT_FOUND_MESSAGE = "Category with id {%d} not found";
  public static final String ERROR_EXPENSE_NOT_FOUND_MESSAGE = "Expense with id {%d} not found";
  public static final String ERROR_INCOME_NOT_FOUND_MESSAGE = "Income with id {%d} not found";
  public static final String ERROR_ACCESS_DENIED_MESSAGE = "Access denied: expense does not belong to user";


  // -------------------------- BAD REQUEST --------------------------

  @ExceptionHandler(UsernameAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleUsernameExists(UsernameAlreadyExistsException ex) {
    ErrorResponse response = new ErrorResponse();
    response.setMessage(ex.getMessage());
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setDate(System.currentTimeMillis());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(CategoryAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleCategoryAlreadyExists(CategoryAlreadyExistsException ex) {
    ErrorResponse response = new ErrorResponse();
    response.setMessage(ex.getMessage());
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setDate(System.currentTimeMillis());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(EntityNotBelongToUserException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotBelongToUser(EntityNotBelongToUserException ex) {
    ErrorResponse response = new ErrorResponse();
    response.setMessage(ex.getMessage());
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setDate(System.currentTimeMillis());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
    ErrorResponse response = new ErrorResponse();
    response.setMessage(ex.getMessage());
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setDate(System.currentTimeMillis());
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    ErrorResponse response = new ErrorResponse();
    response.setMessage(errors.toString());
    response.setStatus(HttpStatus.BAD_REQUEST.value());
    response.setDate(System.currentTimeMillis());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  // -------------------------- NOT FOUND --------------------------

  @ExceptionHandler(CategoryNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleCategoryNotFound(CategoryNotFoundException ex) {
    ErrorResponse response = new ErrorResponse();
    response.setMessage(ex.getMessage());
    response.setStatus(HttpStatus.NOT_FOUND.value());
    response.setDate(System.currentTimeMillis());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(IncomeNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleIncomeNotFound(IncomeNotFoundException ex) {
    ErrorResponse response = new ErrorResponse();
    response.setMessage(ex.getMessage());
    response.setStatus(HttpStatus.NOT_FOUND.value());
    response.setDate(System.currentTimeMillis());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler(ExpenseNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleExpenseNotFound(ExpenseNotFoundException ex) {
    ErrorResponse response = new ErrorResponse();
    response.setMessage(ex.getMessage());
    response.setStatus(HttpStatus.NOT_FOUND.value());
    response.setDate(System.currentTimeMillis());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }
}
