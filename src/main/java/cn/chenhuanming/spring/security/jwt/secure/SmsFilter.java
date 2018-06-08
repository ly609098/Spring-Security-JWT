package cn.chenhuanming.spring.security.jwt.secure;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SmsFilter extends AbstractAuthenticationProcessingFilter {
    public String mobile = "mobile";
    public String code = "code";
    private String mobileParameter = "mobile";
    private String codeParameter = "code";
    private boolean postOnly = true;

    public SmsFilter() {
        super(new AntPathRequestMatcher("/auth/sms", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String mobile = this.obtainMobile(request);
            String code = this.obtainCode(request);
            if (mobile == null) {
                mobile = "";
            }

            if (code == null) {
                code = "";
            }

            mobile = mobile.trim();
            SmsToken authRequest = new SmsToken(mobile, code);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected String obtainCode(HttpServletRequest request) {
        return request.getParameter(this.codeParameter);
    }

    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(this.mobileParameter);
    }

    protected void setDetails(HttpServletRequest request, SmsToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "Username parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }

    public void setCodeParameter(String codeParameter) {
        Assert.hasText(codeParameter, "Password parameter must not be empty or null");
        this.codeParameter = codeParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return this.mobileParameter;
    }

    public final String getCodeParameter() {
        return this.codeParameter;
    }
}