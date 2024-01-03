package com.sprng.users.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
//@Profile("dev")
public class ConfigFile {
    @Value("${configfile.name}")
    String name;
    @Value("${configfile.age}")
    int age;

}
