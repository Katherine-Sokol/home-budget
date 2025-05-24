package backend.project.user;

import backend.project.exception.UsernameAlreadyExistsException;
import java.util.Optional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User createUser(UserRequestDTO requestDTO) {
    if (userRepository.findByUsername(requestDTO.getUsername()).isPresent()) {
      throw new UsernameAlreadyExistsException(requestDTO.getUsername());
    }

    User user = new User();
    user.setUsername(requestDTO.getUsername());
    user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

    return userRepository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("There is no such user");
    }
    return user.get();
  }

  public User authenticate(String username, String rawPassword) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("Користувача не знайдено"));

    if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
      throw new RuntimeException("Невірний пароль");
    }

    return user;
  }

  public User getCurrentUser() {
    // Получение имени пользователя из контекста Spring Security
    var username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByUsername(username).get();
  }
}