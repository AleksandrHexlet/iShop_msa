package com.sprng.users.controller;

import com.sprng.library.entity.ClientAppOwner;
import com.sprng.library.entity.ClientRegisterData;
import com.sprng.library.exception.IshopResponseException;
import com.sprng.users.service.ClientAppOwnerRegistrationService;
import com.sprng.users.service.ClientAppRegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/clientapp")
public class ClientAppController {
    private ClientAppRegistrationService clientAppRegistrationService;
    private ClientAppOwnerRegistrationService clientAppOwnerRegistrationService;

    @Autowired
    public ClientAppController(ClientAppRegistrationService clientAppRegistrationService,
                               ClientAppOwnerRegistrationService clientAppOwnerRegistrationService) {
        this.clientAppRegistrationService = clientAppRegistrationService;
        this.clientAppOwnerRegistrationService = clientAppOwnerRegistrationService;
    }

    @PostMapping("/registration") // controller поломан из-за того, что убрал clientRegistеr
    public ResponseEntity<ClientRegisterData> clientAppRegistration(@Valid ClientRegisterData clientRegisterData) {
        try {
            ClientRegisterData clientRegistrationData = clientAppRegistrationService.registrationAppClient(clientRegisterData);

            return new ResponseEntity<>(clientRegistrationData, HttpStatus.CREATED);
        } catch (Exception responseException) {
            System.out.println("clientRegistration error ===  " + responseException.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client application not registration");
        }
    }

//    @PostMapping("/registrationAppOwner")
//    public ResponseEntity<ClientAppOwner> clientAppOwnerRegistration
//            (@Valid ClientAppOwner clientAppOwnerData) {
//        try {
//            ClientAppOwner clientAppOwner = clientAppOwnerRegistrationService.clientAppOwnerRegistration(clientAppOwnerData);
//            return new ResponseEntity<ClientAppOwner>(clientAppOwner, HttpStatus.CREATED);
//        } catch (IshopResponseException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                    "ClientAppOwner not registration; " + e.getMessage() );
//        }
//
//    }
//}

    @PostMapping("/registrationAppOwner")
    public ResponseEntity<String> clientAppOwnerRegistration
            (@Valid ClientAppOwner clientAppOwnerData) {
        try {
            if (!clientAppOwnerData.getAppList().isEmpty()) {
                ClientRegisterData clientRegisterData = clientAppRegistrationService.registrationAppClient(clientAppOwnerData.getAppList().get(0));
                clientAppOwnerData.getAppList().set(0, clientRegisterData);
            }
            ClientAppOwner clientAppOwner = clientAppOwnerRegistrationService
                    .clientAppOwnerRegistration(clientAppOwnerData);
            return ResponseEntity.ok("The owner of the client application with this name has been successfully saved");
        } catch (IshopResponseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ClientAppOwner not registration; " + e.getMessage());
        }

    }
}

