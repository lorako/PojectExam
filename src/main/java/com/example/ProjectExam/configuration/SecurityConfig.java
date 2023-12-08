package com.example.ProjectExam.configuration;

import com.example.ProjectExam.repositories.UserRepository;
import com.example.ProjectExam.services.Impl.ProjectUserDetailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.example.ProjectExam.models.enums.RoleEnum.Admin;
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final String rememberMeKey;

    public SecurityConfig(@Value("${projectExam.remember.me.key}")
                          String rememberMeKey) {
        this.rememberMeKey = rememberMeKey;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return  httpSecurity.cors(Customizer.withDefaults()).
        authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/login","/register","/login-error", "/api/**", "/thanks", "/index").permitAll()
                                .requestMatchers("/errors").permitAll()
                                .requestMatchers("/allHeroes","/allHeroes/like-hero/**","/contacts", "/weapons", "/artists", "/shoppingBag", "/home").authenticated()
                                .requestMatchers("/allHeroes/add").hasRole("Artist")
                                .requestMatchers("/weapons/add").hasRole("Artist")
                              //  .requestMatchers(HttpMethod.DELETE, "/api/heroes/delete/{id}").permitAll()
                              //  .requestMatchers(HttpMethod.GET, "/api/heroes").permitAll()
                              //  .requestMatchers(HttpMethod.POST, "/api/heroes/add/**").permitAll()
                                .requestMatchers("/admin").hasRole(Admin.name())
                                .anyRequest().authenticated())

                .formLogin(
                        formLogin ->{
                            formLogin.loginPage("/login")
                                    .usernameParameter("username")
                                    .passwordParameter("password")
                                    .defaultSuccessUrl("/home")
                                    .failureForwardUrl("/login-error");
                        })
                .logout(logout->{
                    logout.
                            logoutUrl("/logout")
                            .logoutSuccessUrl("/index")
                            .deleteCookies("JSESSIONID")
                            .invalidateHttpSession(true);

                }).rememberMe(
                        rememberMe->{
                            rememberMe
                                    .key(rememberMeKey)
                                    .rememberMeParameter("rememberme")
                                    .rememberMeCookieName("rememberme");
                        })
                .headers(headers -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                .build();

    }

    private SecurityContextRepository securityContextRepository() {
        return    new HttpSessionSecurityContextRepository();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:63342"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new ProjectUserDetailService(userRepository);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}