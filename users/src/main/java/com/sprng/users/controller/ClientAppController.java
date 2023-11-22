package com.sprng.users.controller;

import com.sprng.library.entity.ProductTrader;
import com.sprng.library.exception.IshopResponseException;
import com.sprng.users.ClientRegisterData;
import com.sprng.users.service.RegisteredClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/clientapp")
public class ClientAppController {
    private RegisteredClientService registeredClientService;

    @Autowired
    public ClientAppController(RegisteredClientService registeredClientService) {
        this.registeredClientService = registeredClientService;
    }

    @PostMapping("/registration")
    public ResponseEntity<RegisteredClient> clientAppRegistration(@Valid ClientRegisterData clientRegisterData ) {

        try {
            RegisteredClient registeredClient = registeredClientService.clientRegistration(clientRegisterData);
            return new ResponseEntity<RegisteredClient>(registeredClient, HttpStatus.CREATED);
        } catch (IshopResponseException responseException) {
            System.out.println("clientRegistration error ===  " + responseException.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client not registration");
        }

    }
}
