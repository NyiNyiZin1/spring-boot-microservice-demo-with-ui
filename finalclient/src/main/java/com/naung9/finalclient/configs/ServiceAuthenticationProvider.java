package com.naung9.finalclient.configs;

import com.naung9.finalclient.models.User;
import com.naung9.finalclient.servicecontrollers.AuthenticationServiceController;
import feign.FeignException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;


public class ServiceAuthenticationProvider implements AuthenticationProvider { // Customized Authentication Provider Method For Getting Authenticated User From Service
    final AuthenticationServiceController authController;

    public ServiceAuthenticationProvider(AuthenticationServiceController authController){
        this.authController= authController;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user;
        try{
            // Getting User From Service
            user = authController.login(authentication.getName(), authentication.getCredentials().toString());
        }catch (FeignException.FeignClientException e){
            e.printStackTrace();
            throw e;
        }
        return new UsernamePasswordAuthenticationToken(user,authentication.getCredentials(), new ArrayList<SimpleGrantedAuthority>(){{
            user.getRoles().forEach(role->add(new SimpleGrantedAuthority(role.getName()))); // Converting Roles To Granted Authorities
        }});
    }

    @Override
    public boolean supports(Class<?> aClass) {
        // Check If Authentication Token is The Same As UsernamePasswordAuthenticationToken
        return aClass.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
