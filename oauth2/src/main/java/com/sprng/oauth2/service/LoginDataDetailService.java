package com.sprng.oauth2.service;

import com.sprng.library.entity.LoginData;
import com.sprng.oauth2.repository.LoginDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginDataDetailService implements UserDetailsService {
    private LoginDataRepository loginDataRepository;

    @Autowired
    public LoginDataDetailService(LoginDataRepository loginDataRepository) {
        this.loginDataRepository = loginDataRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginData userLoginData = loginDataRepository.findByUserName(username)
                .orElseThrow(()-> new UsernameNotFoundException("user not found"));
        UserDetails userDetails  = User.builder()
                .username(userLoginData.getUserName())
                .password(userLoginData.getPassword())
                .authorities(userLoginData.getRole().getRoleType().name())
                .build();

        return userDetails;
    }
}
