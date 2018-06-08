package cn.chenhuanming.spring.security.jwt.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by chenhuanming on 2017-07-18.
 *
 * @author chenhuanming
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RestController
public class SecureConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    TokenAuthorizationFilter tokenAuthorizationFilter;

    @Autowired
    SuccessHandler successHandler;

    @Autowired
    FailHandler failHandler;

    @GetMapping("/me")
    public Principal me(Principal principal) {
        return principal;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.antMatcher("/**")
                .authorizeRequests().antMatchers("/me").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin().loginProcessingUrl("/login")
                .successHandler(successHandler).failureHandler(failHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .addFilterBefore(tokenAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();*/
        SmsFilter smsFilter =new SmsFilter();
        smsFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsFilter.setAuthenticationFailureHandler(failHandler);
        smsFilter.setAuthenticationSuccessHandler(successHandler);
        SmsProvider smsProvider =new SmsProvider();
        http.csrf().disable().authenticationProvider(smsProvider).addFilterAfter(smsFilter,UsernamePasswordAuthenticationFilter.class);
    }

   /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("test").password("123").authorities(Collections.emptyList());
    }*/
}
