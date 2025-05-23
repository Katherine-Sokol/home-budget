package backend.project.controller;

import backend.project.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {
  private final UserService userService;
  private final PasswordEncoderProvider passwordEncoder;

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userService);
    provider.setPasswordEncoder(passwordEncoder.passwordEncoder());
    return provider;
  }

  @Autowired
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(passwordEncoder.passwordEncoder());
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authBuilder =
        http.getSharedObject(AuthenticationManagerBuilder.class);
    authBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder.passwordEncoder());
    return authBuilder.build();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/register", "/login").permitAll()
            .requestMatchers("/budget").fullyAuthenticated()
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/budget")
            .permitAll()
        )
        .logout(logout -> logout.permitAll())
        .build();
  }
}

//  @Bean
//  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//    AuthenticationManagerBuilder authBuilder =
//        http.getSharedObject(AuthenticationManagerBuilder.class);
//    authBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder.passwordEncoder());
//    return authBuilder.build();
//  }
//
//  @Bean
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    return http
//        .csrf(csrf -> csrf.disable())
//        .authorizeHttpRequests(auth -> auth
//            .requestMatchers("/register", "/login").permitAll()
//            .requestMatchers("/budget").fullyAuthenticated()
//            .anyRequest().authenticated()
//        )
//        .formLogin(form -> form
//            .loginPage("/login")
//            .defaultSuccessUrl("/budget")
//            .permitAll()
//        )
//        .logout(logout -> logout.permitAll())
//        .build();
//  }
//}