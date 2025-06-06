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

import com.proyecto.demos.Model.enums.Permisos;

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
        .authenticationProvider(authenticationProvider) 
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(authConfig->{
                // 🔓 1. Endpoints públicos (login, error)
                authConfig.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll();
                authConfig.requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll();
                authConfig.requestMatchers("/error").permitAll();

                // 🔐 2. Reglas de Acceso (más específicas a más generales, por rol o permiso)
                // Reglas para RECEPCIONISTA: Tienen prioridad sobre la regla general del ADMINISTRADOR si coinciden
                // DASHBOARD
                authConfig.requestMatchers(HttpMethod.GET, "/api/dashboard/**").hasRole("RECEPCIONISTA");

                // Atenciones: CRUD completo para Recepcionista
                authConfig.requestMatchers(HttpMethod.GET, "/api/atenciones/**").hasRole("RECEPCIONISTA");
                authConfig.requestMatchers(HttpMethod.POST, "/api/atenciones/**").hasRole("RECEPCIONISTA");
                authConfig.requestMatchers(HttpMethod.PUT, "/api/atenciones/**").hasRole("RECEPCIONISTA");
                authConfig.requestMatchers(HttpMethod.DELETE, "/api/atenciones/**").hasRole("RECEPCIONISTA"); // RECEPCIONISTA ahora puede DELETE

                // Servicios: CRUD completo para Recepcionista
                authConfig.requestMatchers(HttpMethod.GET, "/api/servicios/**").hasRole("RECEPCIONISTA");
                authConfig.requestMatchers(HttpMethod.POST, "/api/servicios/**").hasRole("RECEPCIONISTA");
                authConfig.requestMatchers(HttpMethod.PUT, "/api/servicios/**").hasRole("RECEPCIONISTA");
                authConfig.requestMatchers(HttpMethod.DELETE, "/api/servicios/**").hasRole("RECEPCIONISTA"); // RECEPCIONISTA ahora puede DELETE

                // Estaciones: CRUD completo para Recepcionista
                authConfig.requestMatchers(HttpMethod.GET, "/api/estaciones/**").hasRole("RECEPCIONISTA");
                authConfig.requestMatchers(HttpMethod.POST, "/api/estaciones/**").hasRole("RECEPCIONISTA");
                authConfig.requestMatchers(HttpMethod.PUT, "/api/estaciones/**").hasRole("RECEPCIONISTA");
                authConfig.requestMatchers(HttpMethod.DELETE, "/api/estaciones/**").hasRole("RECEPCIONISTA"); // RECEPCIONISTA ahora puede DELETE

                // Empleados: Recepcionista solo puede LEER
                authConfig.requestMatchers(HttpMethod.GET, "/api/empleados/**").hasRole("RECEPCIONISTA");

                // Administradores (usuarios del sistema): Recepcionista solo puede LEER
                // Aquí usamos hasAuthority porque es un permiso específico para leer usuarios del sistema.
                // Podrías usar hasRole("RECEPCIONISTA") si el RECEPCIONISTA es el único rol aparte del ADMIN que puede leerlos.
                authConfig.requestMatchers(HttpMethod.GET, "/api/administradores/**").hasAuthority(Permisos.ADMINISTRADOR_READ.name());


                // Regla general para ADMINISTRADOR (Dueño): Si no coincide con las reglas anteriores,
                // solo el ADMINISTRADOR puede acceder a cualquier otra ruta en /api/.
                // Esto incluye DELETE de empleados, y toda la gestión de administradores (excepto READ para RECEPCIONISTA),
                // reportes y configuración.
                authConfig.requestMatchers("/api/**").hasRole("ADMINISTRADOR");
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
