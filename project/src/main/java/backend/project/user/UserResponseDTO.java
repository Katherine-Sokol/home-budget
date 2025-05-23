package backend.project.user;

import lombok.Getter;

@Getter
public class UserResponseDTO {

  private Long id;
  private String username;

  public UserResponseDTO(Long id, String username) {
    this.id = id;
    this.username = username;
  }

}