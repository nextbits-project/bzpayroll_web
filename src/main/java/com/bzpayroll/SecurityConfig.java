package com.bzpayroll;

import com.bzpayroll.common.utility.BzPayRollConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/dashboard").hasAnyAuthority(BzPayRollConstants.USERROLEADMIN)
            .antMatchers("/Dashboard").hasAnyAuthority(BzPayRollConstants.USERROLEADMIN)
            .antMatchers("/dashboard/**").hasAnyAuthority(BzPayRollConstants.USERROLEADMIN)
            .antMatchers("/Rest/**").permitAll()
            .antMatchers("/**").permitAll()
            .and()
            .formLogin().successHandler(successHandler)
            .loginPage("/login")
            .failureUrl("/login?error=true")
            .usernameParameter("swpm_user_name").passwordParameter("swpm_password")
            .permitAll()
            .and()
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login")
            .invalidateHttpSession(true)
            .permitAll()
            .and()
            .csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("**/login"));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/ConfigurationImages/**")
            .antMatchers("/css/**")
            .antMatchers("/js/**")
            .antMatchers("/inputstyle/**")
            .antMatchers("/images/**")
            .antMatchers("/poppins/**")
            .antMatchers("/tableStyle/**")
            .antMatchers("/fonts.poppins/**")
            .antMatchers("/styles/**")
            .antMatchers("/scripts/**")
            .antMatchers("/dist/**");
    }

}
