package com.proyecto.demos.Security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

//EnabledWebSecurity
@Configuration
public class SecurityConf {
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(csrfconfig->csrfconfig.disable())
        .sessionManagement(sessionMangConfig->sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider) //inyectamos nuestro Dao
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(authConfig->{

        // 🔓 Endpoints públicos (sin autenticación)
        authConfig.requestMatchers(HttpMethod.POST, "/api/*").permitAll();
        authConfig.requestMatchers(HttpMethod.GET, "/api/*").permitAll();
        //authConfig.requestMatchers(HttpMethod.PUT, "/api/*").permitAll();
        //authConfig.requestMatchers(HttpMethod.DELETE, "/api/*").permitAll();
        authConfig.requestMatchers("/error").permitAll();

        // 🔒 Endpoints protegidos por permisos
        authConfig.requestMatchers(HttpMethod.GET, "/api/*/{id}").hasAuthority("READ");
        authConfig.requestMatchers(HttpMethod.POST, "/api/*/{id}").hasAuthority("CREATE");
        authConfig.requestMatchers(HttpMethod.PUT, "/api/*/{id}").hasAuthority("UPDATE");
        authConfig.requestMatchers(HttpMethod.DELETE, "/api/*/{id}").hasAuthority("DELETE");

        // 🔐 Endpoints protegidos por roles
        authConfig.requestMatchers(HttpMethod.GET, "/api/atencion/*").hasRole("ADMINISTRADOR");
        authConfig.requestMatchers(HttpMethod.GET, "api/servicios/*").hasRole("BARBERO");

    // ❌ Cualquier otra petición se bloquea
        authConfig.anyRequest().denyAll();
            });

    return http.build();
    }    

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Permite Angular en localhost
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        configuration.setAllowedHeaders(Arrays.asList("*")); // Permitir todos los headers
        configuration.setAllowCredentials(true); // Permitir credenciales

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
