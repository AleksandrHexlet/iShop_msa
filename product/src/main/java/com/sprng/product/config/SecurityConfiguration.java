package com.sprng.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Flux;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }


    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {

        ReactiveJwtAuthenticationConverter jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new ConverterSplitString());
//        Products.Write", "Products.Read", "Users.Create"
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchangeSpec -> exchangeSpec
                        .pathMatchers(HttpMethod.GET).hasAuthority("SCOPE_Products.Read")
                        .pathMatchers(HttpMethod.POST).hasRole("TRADER")
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)))
//                        .jwt(Customizer.withDefaults()))
                .build();
    }

    public static class ConverterSplitString implements Converter<Jwt, Flux<GrantedAuthority>> {

        @Override
        public Flux<GrantedAuthority> convert(Jwt source) {
            List<String> scopes = (ArrayList<String>) source.getClaims().get("scope");
            if ((scopes == null || scopes.isEmpty())) {
                return Flux.empty();
            }
            GrantedAuthority[] authorities = scopes.stream()
                    .map(string -> {
                        if (!string.startsWith("ROLE")) return new SimpleGrantedAuthority("SCOPE_" + string);
                        return new SimpleGrantedAuthority(string);
                    })
                    .toArray(SimpleGrantedAuthority[]::new);
            return Flux.fromArray(authorities);
        }
    }
}