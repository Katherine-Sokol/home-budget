package backend.project.user;

import lombok.Getter;

//@Getter
public class UserResponseDTO {

  private String token;

  public UserResponseDTO(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

}