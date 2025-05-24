package backend.project.controller;

import backend.project.jwt.JwtService;
import backend.project.user.User;
import backend.project.user.UserRequestDTO;
import backend.project.user.UserResponseDTO;
import backend.project.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserService userService;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  /**
   * Регистрация пользователя
   *
   * @param request данные пользователя
   * @return токен
   */
  public UserResponseDTO signUp(UserRequestDTO request) {
    User user = userService.createUser(request);
    var jwt = jwtService.generateToken(user);
    return new UserResponseDTO(jwt);
  }

  /**
   * Аутентификация пользователя
   *
   * @param request данные пользователя
   * @return токен
   */
  public UserResponseDTO signIn(UserRequestDTO request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        request.getUsername(),
        request.getPassword()
    ));

    UserDetails user = userService.loadUserByUsername(request.getUsername());

    var jwt = jwtService.generateToken(user);
    return new UserResponseDTO(jwt);
  }
}

