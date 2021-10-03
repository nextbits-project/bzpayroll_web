package com.bzpayroll;

import com.bzpayroll.login.entity.BcaUser;
import com.bzpayroll.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private LoginService loginService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        System.out.println(authentication.getAuthorities());
        List<BcaUser> userList = loginService.checkUserLogin(authentication.getName(),authentication.getCredentials().toString());
        if(!userList.isEmpty()){
            BcaUser user = userList.get(0);
            String userRole = loginService.checkUserRole(user);
            List<GrantedAuthority> authorities = new ArrayList<>();
            if(userRole != null){
                authorities.add(new SimpleGrantedAuthority(userRole));
                return  new UsernamePasswordAuthenticationToken(user.getLoginId(),user.getPassword(),authorities);
            }else{
                throw new BadCredentialsException("Error");
            }


        }else{
            throw new BadCredentialsException("Error");
        }


     }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
