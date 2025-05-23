package backend.project.user;

import backend.project.exception.UsernameAlreadyExistsException;
import java.util.Optional;
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

  public User registerUser(UserRequestDTO requestDTO) {
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
    return (UserDetails) user.get();
  }
}