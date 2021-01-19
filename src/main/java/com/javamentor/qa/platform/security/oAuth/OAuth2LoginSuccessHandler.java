package com.javamentor.qa.platform.security.oAuth;

import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.security.SecurityConfig;
import com.javamentor.qa.platform.security.jwt.JwtUtils;
import com.javamentor.qa.platform.security.util.SecurityHelper;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserService userService;
    private final JwtUtils jwtUtils;
    @Autowired
    OAuth2LoginSuccessHandler(UserService userService, JwtUtils jwtUtils){
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuthUser = (CustomOAuth2User) authentication.getPrincipal();
        Optional userOptional = userService.getUserByEmail(authentication.getName());
        User user = null;
        if(!userOptional.isPresent()){
            user = new User();
            user.setPassword("oauth2user");
            user.setEmail(oAuthUser.getEmail());
            user.setIsEnabled(true);
            Role role = new Role();
            role.setId(151L);
            role.setName("ROLE_USER");
            user.setRole(role);
            userService.persist(user);
        } else {
            user = (User) userOptional.get();
        }
        Cookie cookie = new Cookie("token","Bearer_" + jwtUtils.generateJwtTokenOAuth(authentication));
        cookie.setPath("/");
        response.addCookie(cookie);
        this.handle(request,response);

    }

    protected void handle(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        String targetUrl = "http://localhost:5557/site";
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
}
