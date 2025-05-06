package com.proyecto.demos.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//EnabledWebSecurity
@Configuration
public class SecurityConf {
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrfconfig->csrfconfig.disable())
        .sessionManagement(sessionMangConfig->sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider) //inyectamos nuestro Dao
        //registramos nuestro filtro personalizado en el orden adecuado
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(authConfig->{

        // 🔓 Endpoints públicos (sin autenticación)
        authConfig.requestMatchers(HttpMethod.POST, "/api/register").permitAll();
        authConfig.requestMatchers(HttpMethod.POST, "/api/login").permitAll();
        authConfig.requestMatchers("/error").permitAll();

        // 🔒 Endpoints protegidos por permisos
        authConfig.requestMatchers(HttpMethod.GET, "/private/*").hasAuthority("READ");
        authConfig.requestMatchers(HttpMethod.POST, "/home").hasAuthority("CREATE");
        authConfig.requestMatchers(HttpMethod.PUT, "/formulario/**").hasAuthority("UPDATE");

        // 🔐 Endpoints protegidos por roles
        authConfig.requestMatchers(HttpMethod.GET, "/admin").hasRole("ADMIN");
        authConfig.requestMatchers(HttpMethod.GET, "/user").hasRole("USER");

    // ❌ Cualquier otra petición se bloquea
        authConfig.anyRequest().denyAll();
            });

    return http.build();
    }    
}
