package configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import util.UserRoleEnum;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] ADMIN_AUTH_WHITELIST = {
            "/cards/table/**",
            "/acquiring-banks/table/**",
            "/merchant-category-codes/table/**",
            "/payment-systems/table/**",
            "/response-codes/table/**",
            "/sales-points/table/**",
            "/terminals/table/**",
            "/transactions/table/**",
            "/transaction-types/table/**",
            "/users/**",
    };
    private static final String[] NO_AUTH_WHITELIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/auth/**",
            "/error/**",
            "/swagger-ui.html"
    };
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       DaoAuthenticationProvider authProvider) throws Exception {
        return http
                .authenticationProvider(authProvider)
                .getSharedObject(AuthenticationManager.class);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   DaoAuthenticationProvider authProvider) throws Exception {
        http
                .authenticationProvider(authProvider)
                .csrf(Customizer.withDefaults()
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(NO_AUTH_WHITELIST).permitAll()
                        .requestMatchers(ADMIN_AUTH_WHITELIST).hasRole(UserRoleEnum.ADMIN.name())
                        .anyRequest().hasAnyRole(UserRoleEnum.USER.name(), UserRoleEnum.ADMIN.name())
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/cards", true)
                        .failureUrl("/auth/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login?logout=true")
                        .invalidateHttpSession(true)
                        .permitAll()
                );

        return http.build();
    }

}