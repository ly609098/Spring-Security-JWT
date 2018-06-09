package cn.chenhuanming.spring.security.jwt.secure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Component
public class FailHandler extends SimpleUrlAuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {
    response.setStatus(500);
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    ObjectMapper objectMapper = new ObjectMapper();
    try (Writer writer = response.getWriter()) {
      Map map = new HashMap();
      map.put("code", "0");
      map.put("message", exception.getMessage());
      map.put("data", null);
      objectMapper.writeValue(writer, map);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
