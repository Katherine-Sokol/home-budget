package backend.project.jwt;

import backend.project.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;


public class JwtServiceTest {

  private JwtService jwtService;

  @BeforeEach
  void setUp() {
    jwtService = new JwtService();

    // Прямо подставляем секретный ключ (base64, минимум 256 бит для HS256)
    String testKey = "Rk9YUkFORF9LRVlfRk9SX1RFU1RfU1RFU1RfU1RFU1RfU1RFUw=="; // "FOXRAND_KEY_FOR_TEST_STEST_STEST_STESTS" в base64
    ReflectionTestUtils.setField(jwtService, "jwtSigningKey", testKey);
  }

  @Test
  void testGenerateAndValidateToken() {
    // Создаём фиктивного пользователя
    User user = new User();
    user.setId(1L);
    user.setUsername("testuser");
    user.setPassword("password");

    String token = jwtService.generateToken(user);
    assertNotNull(token);

    String username = jwtService.extractUserName(token);
    assertEquals("testuser", username);

    boolean valid = jwtService.isTokenValid(token, user);
    assertTrue(valid);
  }
}