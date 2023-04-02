package com.project.ddbb.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // 요청에 의한 보안검사 시작
                .anyRequest().authenticated(); //어떤 요청에도 보안검사를 한다.

        http.formLogin() // From 로그인 처리
                .loginPage("/member/login") // 사용자 정의 로그인 페이지
                .defaultSuccessUrl("/") // 로그인 성공 후 이동페이지
                .failureUrl("/login?error=true") // 로그인 실패 후 이동페이지
                .usernameParameter("userId") // 아이디 파라미터명
                .passwordParameter("password") // 비밀번호 파라미터명
                .loginProcessingUrl("/member/login") // 로그인 Form Action Url
//                .successHandler(authenticationSuccess) // 로그인 성공 후 핸들러
//                .failureHandler(authenticationFailure) // 로그인 실패 후 핸들러
                .permitAll() // .loginPage() 의 경로는 인증 없이 접근 가능
        ;

        http.logout() // 로그아웃 처리
                .logoutUrl("/member/logout") // 로그아웃 처리 URL(기본이 post)
                .logoutSuccessUrl("/") // 로그아웃 성공 URL
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID") // 로그아웃 성공 시 제거할 쿠키명
//                .addLogoutHandler(logoutExecute) // 로그아웃 핸들러
//                .logoutSuccessHandler(logoutSuccess) // 로그아웃 성공 후 핸들러
        ;

        http.exceptionHandling() // Exception 처리
//                .authenticationEntryPoint(authenticationEntryException) // 인증 예외
//                .accessDeniedHandler(accessDeniedHandlerException) // 인가 예외
        ;

        return http.build();
    }

    @Bean
    // 암호화 처리
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//                .antMatchers("/", "/img/**", "/lib/**");
        // configure Web security...

    }

}