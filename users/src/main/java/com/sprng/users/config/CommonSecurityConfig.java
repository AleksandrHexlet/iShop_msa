package com.sprng.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class CommonSecurityConfig {
    // перенесём в отдельный класс,
    // чтобы не создавать цикличных зависимостей


}


//http://localhost:9090/oauth2/authorize?client_id=$2a$10$XPItnalALTnwWei0WTnlAulpmP2RatO0REzG9m/QjgwgdZFstfmv.&response_type=code&redirect_uri=http://app.ru&scope=openid%20read