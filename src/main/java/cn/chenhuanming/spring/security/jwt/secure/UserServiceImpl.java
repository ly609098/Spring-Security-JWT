package cn.chenhuanming.spring.security.jwt.secure;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
  @Override
  public User loadUserByUsername(String userName) throws UsernameNotFoundException {
    System.out.println(11111);

    if (!userName.equals("zs")) {

      throw new InternalAuthenticationServiceException("用户没找到");
    }
    User user = new User();
    user.setUsername(null);
    user.setPassword(new BCryptPasswordEncoder().encode("123"));
    return user;
  }

  @Override
  public User findUserByMobile(String mobile) {
    System.out.println(mobile);
    return null;
  }
}
