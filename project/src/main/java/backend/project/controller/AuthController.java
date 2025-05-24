package backend.project.controller;

import backend.project.user.User;
import backend.project.user.UserRequestDTO;
import backend.project.user.UserResponseDTO;
import backend.project.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

  private final UserService userService;
  private final AuthService authService;
  private final AuthenticationManager authenticationManager;

  @PostMapping("/register")
  public UserResponseDTO registerUser(@Valid @RequestBody UserRequestDTO requestDTO) {
//    return authService.signUp(requestDTO);
    UserResponseDTO response = authService.signUp(requestDTO);
    System.out.println("Returning token: " + response.getToken());
    return response;
  }
}
