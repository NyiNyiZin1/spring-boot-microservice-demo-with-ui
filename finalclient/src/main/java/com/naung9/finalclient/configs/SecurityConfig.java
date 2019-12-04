package com.naung9.finalclient.configs;

import com.naung9.finalclient.servicecontrollers.AuthenticationServiceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    final
    AuthenticationServiceController authenticationServiceController;

    public SecurityConfig(AuthenticationServiceController authenticationServiceController) {
        this.authenticationServiceController = authenticationServiceController;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login","/logout","/register").permitAll().anyRequest().authenticated().and()
                .formLogin().loginPage("/login").usernameParameter("email").passwordParameter("password").loginProcessingUrl("/login")
                .defaultSuccessUrl("/index").failureUrl("/login?error")
                .and().logout().logoutUrl("/logout").deleteCookies("JSESSIONID").clearAuthentication(true).logoutSuccessUrl("/login").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new ServiceAuthenticationProvider(authenticationServiceController));
    }
}