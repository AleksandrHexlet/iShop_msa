package com.sprng.users.service;

import com.sprng.library.entity.ClientApp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

public class ClientDetails implements UserDetails {
    private RegisteredClient registeredClient;
    private ClientApp clientApp;


    public ClientDetails(RegisteredClient registeredClient, ClientApp clientApp) {
        this.registeredClient = registeredClient;
        this.clientApp = clientApp;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(clientApp.getRole().getRoleType().name()));
    }


    public Instant getClientExpireAt() {
        return registeredClient.getClientSecretExpiresAt();
    }

    public String getClientId() {
        return registeredClient.getClientId();
    }

    public String getClientSecret() {
        return clientApp.getClientSecretClean();
    }

    @Override
    public String getPassword() {
        return clientApp.getPassword();
    }

    @Override
    public String getUsername() {
        return clientApp.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
