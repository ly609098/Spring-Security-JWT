package cn.chenhuanming.spring.security.jwt.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SmsProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SmsToken smsToken = (SmsToken) authentication;


        User userByMobile = userService.findUserByMobile(smsToken.getMobile().toString());
        if ()
        return null;
    }

    @Override
    public boolean supports(Class<?> authenticate) {
        return SmsToken.class.isAssignableFrom(authenticate);
    }
}
