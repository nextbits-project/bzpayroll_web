package com.bzpayroll;

import com.bzpayroll.common.utility.BzPayRollConstants;
import com.bzpayroll.login.entity.BcaUser;
import com.bzpayroll.login.forms.LoginForm;
import com.bzpayroll.login.forms.MultiUserForm;
import com.bzpayroll.login.service.LoginService;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    ObjectFactory<HttpSession> httpSessionFactory;
    @Autowired
    private LoginService loginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        System.out.println("onAuthenticationSuccess");
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        authorities.forEach(authority -> {
            if(authority.getAuthority().equalsIgnoreCase(BzPayRollConstants.USERROLEADMIN)){
                try {
                    List<BcaUser> users = loginService.findByUserName(authentication.getPrincipal().toString());
                    BcaUser user = users.get(0);
                    HttpSession session = httpSessionFactory.getObject();
                    LoginForm loginObj = new LoginForm();
                    loginObj.setUserName(authentication.getPrincipal().toString());
                    loginObj.setUserPassword(user.getPassword());
                    MultiUserForm multiUserObj = loginService.checkUserLogin(loginObj, authority.getAuthority());

                    String companyId = "1";
                    session.setAttribute("CID", companyId);
                    session.setAttribute("password", authentication.getCredentials());

                    session.setAttribute("Email_Address", multiUserObj.getUserName());
                    session.setAttribute("LoginID", multiUserObj.getUserId());
                    session.setAttribute("userRole", authority.getAuthority());
                    session.setAttribute("Role", authority.getAuthority());
                    session.setAttribute("CID", String.valueOf(multiUserObj.getCompanyID()));
                    session.setAttribute("user", multiUserObj.getCompanyName());
                    session.setAttribute("userID", multiUserObj.getUserId());
                    session.setAttribute("membershipLevel", multiUserObj.getMembershipLevel());
                    session.setAttribute("username", multiUserObj.getFirstName() + " " + multiUserObj.getLastName());

                    redirectStrategy.sendRedirect(httpServletRequest,httpServletResponse,"/dashboard?tabid=Dashboard");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } if(authority.getAuthority().equalsIgnoreCase(BzPayRollConstants.USERROLESUPERADMIN)){
                try {
                    redirectStrategy.sendRedirect(httpServletRequest,httpServletResponse,"/dashboard?tabid=Dashboard");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{

            }

        });

    }
}
