package com.kdt.landing.global.config;

import com.kdt.landing.domain.user.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig {

    private final DataSource dataSource;

    private final CustomUserDetailsService userDetailsService;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("-------------------  filter Chain  ------------------");

        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)

//                .authorizeHttpRequests((authorizeHttpRequests) ->
//                        authorizeHttpRequests
//                        .requestMatchers("/", "/**","/login","/join","/emailConfrim","/java/project").permitAll()
//                                .requestMatchers("/admin/**").hasAnyRole("ADMIN")
//                                .requestMatchers("/member/mypage").hasAnyRole("USER")
//                                .anyRequest().permitAll());


                .authorizeHttpRequests((authorizeRequests) -> {
            authorizeRequests
                    .requestMatchers(new AntPathRequestMatcher("/apply/**"))
                    .permitAll() // 모두 접근 가능
                    .anyRequest().permitAll();
        });

        http
                .formLogin((formLogin) -> formLogin
                        .loginPage("/login")
                        .failureUrl("/member/loginFail")
                        .defaultSuccessUrl("/")
                );

        http
                .logout((logout) ->
                        logout.logoutUrl("/logout").logoutSuccessUrl("/")
                );
              //  .requestmvcMatchers("/", "/resource/**", "/css/**", "/js/**", "/images/**").permitAll();

        http
                .exceptionHandling((exceptionHandling) ->
                        exceptionHandling.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                );
        http
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)));

       return http.build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        log.info("-------------------- WebSecurity ----------------------");
        return (web) -> web.ignoring().requestMatchers(
                PathRequest.toStaticResources().atCommonLocations());
    }

}