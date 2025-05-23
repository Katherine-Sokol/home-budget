package backend.project.controller;

import backend.project.user.User;
import backend.project.user.UserRequestDTO;
import backend.project.user.UserResponseDTO;
import backend.project.user.UserService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class AuthController {

  private final UserService userService;
  private final AuthenticationManager authenticationManager;

  public AuthController(UserService userService, AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping
  public UserResponseDTO registerUser(@Valid @RequestBody UserRequestDTO requestDTO) {
    User savedUser = userService.registerUser(requestDTO);

    // Автоматический логин
    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword());

    Authentication authentication = authenticationManager.authenticate(authToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return new UserResponseDTO(savedUser.getId(), savedUser.getUsername());
  }
}
