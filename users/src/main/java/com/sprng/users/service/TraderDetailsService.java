package com.sprng.users.service;



import com.sprng.library.entity.LoginData;
import com.sprng.library.entity.Role;
import com.sprng.users.repository.LoginDataRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TraderDetailsService implements UserDetailsService {
    private LoginDataRepository loginDataRepository;

    public TraderDetailsService(LoginDataRepository loginDataRepository) {
        this.loginDataRepository = loginDataRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        LoginData loginData = loginDataRepository.findByUserName(username).orElseThrow(() ->
//                new UsernameNotFoundException(String.format("Пользователь с именем %s не зарегистрирован", username)));
        LoginData loginData = null;
        try {
            loginData = (LoginData) loginDataRepository.findByUserName(username).orElseThrow(() ->
                    new UsernameNotFoundException(String.format("Пользователь с именем %s не зарегистрирован", username)));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        if(loginData.getRole().getRoleType() != Role.RoleType.ROLE_TRADER) throw  new UsernameNotFoundException("Авторизация только для трейдеров. Не верный тип роли");
        GrantedAuthority authority = new SimpleGrantedAuthority(loginData.getRole().getRoleType().name());
        return new User(username, loginData.getPassword(), Set.of(authority));
    }
}
