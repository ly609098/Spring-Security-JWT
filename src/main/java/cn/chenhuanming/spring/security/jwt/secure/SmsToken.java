package cn.chenhuanming.spring.security.jwt.secure;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SmsToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 420L;
    private final Object mobile;
    private Object code;

    public SmsToken(Object mobile, Object code) {
        super((Collection) null);
        this.mobile = mobile;
        this.code = code;
        this.setAuthenticated(false);
    }

    public SmsToken(Object mobile, Object code, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.mobile = mobile;
        this.code = code;
        super.setAuthenticated(true);
    }

    public Object getCode() {
        return this.code;
    }

    public Object getMobile() {
        return this.mobile;
    }

    @Override
    public Object getCredentials() {
        return code;
    }

    @Override
    public Object getPrincipal() {
        return mobile;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.code = null;
    }
}
