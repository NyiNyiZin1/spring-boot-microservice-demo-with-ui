package com.nnz.finalclient.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.nnz.finalclient.servicecontrollers.AuthenticationServiceController;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter { // For Configuring Http Security
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
