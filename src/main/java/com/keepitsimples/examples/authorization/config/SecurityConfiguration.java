package com.keepitsimples.examples.authorization.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Created by Paul Rhoades on 18/02/2025.
 */
@Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConfiguration  {


    /*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
            .cors(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/unprotected").permitAll()
                .anyRequest().authenticated())
             .httpBasic();
           // .formLogin((form) -> form.loginPage("/login")
          //  .exceptionHandling().accessDeniedPage("/denied");
           // .authenticationManager(authenticationManager)
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        final String password = passwordEncoder().encode("pw");
        UserDetails user1 = CustomUser
            .forUsername("user")
            .withEncodedPassword(password)
            .withRoles("USER")
            .withAuthorities("EDIT_USER")
            .build();
        UserDetails user2 = CustomUser
            .forUsername("admin")
            .withEncodedPassword(password)
            .withRoles("ADMIN")
            .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
*/
}
