package backend.project.exception;

public class EntityNotBelongToUserException extends RuntimeException {
  public EntityNotBelongToUserException(String message) {
    super(message);
  }
}
