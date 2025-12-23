// package com.example.demo.config;

// import com.example.demo.security.JwtFilter;
// import com.example.demo.security.CustomUserDetailsService;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// @Configuration
// public class SecurityConfig {

//     private final CustomUserDetailsService userDetailsService;
//     private final JwtFilter jwtFilter;

//     public SecurityConfig(CustomUserDetailsService userDetailsService,
//                           JwtFilter jwtFilter) {
//         this.userDetailsService = userDetailsService;
//         this.jwtFilter = jwtFilter;
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public DaoAuthenticationProvider authenticationProvider() {
//         DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//         provider.setUserDetailsService(userDetailsService);
//         provider.setPasswordEncoder(passwordEncoder());
//         return provider;
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(
//             AuthenticationConfiguration config) throws Exception {
//         return config.getAuthenticationManager();
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http)
//             throws Exception {

//         return http
//                 .csrf(csrf -> csrf.disable())
//                 .authorizeHttpRequests(auth -> auth
//                         .requestMatchers(
//                                 "/auth/**",
//                                 "/parcels/**",     // ✅ ALLOW PARCEL APIs
//                                 "/rules/**",
//                                 "/claims/**",
//                                 "/evidence/**",
//                                 "/v3/api-docs/**",
//                                 "/swagger-ui/**",
//                                 "/swagger-ui.html"
//                         ).permitAll()
//                         .anyRequest().authenticated()
//                 )
//                 .addFilterBefore(jwtFilter,
//                         UsernamePasswordAuthenticationFilter.class)
//                 .build();
//     //}
//}
package com.example.demo.config;

import com.example.demo.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // ✅ EXPLICITLY ALLOW RULE APIs
                        .requestMatchers(HttpMethod.GET, "/rules/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/rules/**").permitAll()

                        // ✅ OTHER PUBLIC APIs
                        .requestMatchers(
                                "/auth/**",
                                "/parcels/**",
                                "/claims/**",
                                "/evidence/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // ❌ EVERYTHING ELSE NEEDS AUTH
                        .anyRequest().authenticated()
                )
                // JWT filter still present, but rules are bypassed
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
