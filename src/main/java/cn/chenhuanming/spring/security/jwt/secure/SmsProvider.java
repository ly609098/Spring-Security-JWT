package cn.chenhuanming.spring.security.jwt.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

public class SmsProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SmsToken smsToken = (SmsToken) authentication;


        User user = userService.findUserByMobile(smsToken.getMobile().toString());
        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }
        if (!smsToken.getCode().equals("1234")) {
            throw new InternalAuthenticationServiceException("验证码错误");
        }
        SmsToken authenticationResult = new SmsToken(smsToken.getMobile().toString(), smsToken.getCode(), user.getAuthorities());
        //将未认证的details放入已认证的details中去
        authenticationResult.setDetails(smsToken.getDetails());

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authenticate) {
        return SmsToken.class.isAssignableFrom(authenticate);
    }
}
