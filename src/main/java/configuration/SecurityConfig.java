package configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import service.UserService;
import util.UserRoleEnum;

import static org.springframework.security.config.Customizer.withDefaults;

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
            "/users/**"
    };
    private static final String[] NO_AUTH_WHITELIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/auth/**",
            "/error/**",
            "/swagger-ui.html",
            "/login"
    };
    private final UserService userService;

    @Autowired
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
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
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(NO_AUTH_WHITELIST).permitAll()
                        .requestMatchers(ADMIN_AUTH_WHITELIST).hasRole(UserRoleEnum.ADMIN.name())
                        .anyRequest().hasAnyRole(UserRoleEnum.USER.name(), UserRoleEnum.ADMIN.name())
                )
                .formLogin(withDefaults());

        return http.build();
    }

}