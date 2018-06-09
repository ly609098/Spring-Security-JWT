package cn.chenhuanming.spring.security.jwt.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Created by chenhuanming on 2017-07-18.
 *
 * @author chenhuanming
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RestController
public class SecureConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired TokenAuthorizationFilter tokenAuthorizationFilter;

  @Autowired SuccessHandler successHandler;

  @Autowired FailHandler failHandler;
  @Autowired UserDetailsService userServiceImpl;

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @GetMapping("/me")
  public Principal me(Principal principal) {
    return principal;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    SmsFilter smsFilter = new SmsFilter();
    smsFilter.setAuthenticationManager(this.authenticationManager());

    smsFilter.setAuthenticationFailureHandler(failHandler);
    smsFilter.setAuthenticationSuccessHandler(successHandler);
    SmsProvider smsProvider = new SmsProvider();

    http.antMatcher("/**")
        .authorizeRequests()
        .antMatchers("/me")
        .authenticated()
        .anyRequest()
        .permitAll()
        .and()
        .formLogin()
        .loginProcessingUrl("/auth/pwd")
        .successHandler(successHandler)
        .failureHandler(failHandler)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(STATELESS)
        .and()
        .addFilterBefore(tokenAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(smsFilter,UsernamePasswordAuthenticationFilter.class).authenticationProvider(smsProvider)
        .csrf()
        .disable();



  }
}
