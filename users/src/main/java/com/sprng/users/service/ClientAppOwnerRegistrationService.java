package com.sprng.users.service;


import com.sprng.library.entity.ClientAppOwner;
import com.sprng.library.entity.ClientRegisterData;
import com.sprng.library.entity.Role;
import com.sprng.library.exception.IshopResponseException;
import com.sprng.users.repository.ClientAppOwnerRepository;
import com.sprng.users.repository.ClientAppRepository;
import com.sprng.users.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientAppOwnerRegistrationService {

    private final RoleRepository roleRepository;
    private ClientAppOwnerRepository clientAppOwnerRepository;
    private ClientAppRepository clientAppRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ClientAppOwnerRegistrationService(ClientAppOwnerRepository clientAppOwnerRepository,
                                             ClientAppRepository clientAppRepository,
                                             PasswordEncoder passwordEncoder, RoleRepository roleRepository
    ) {
        this.clientAppOwnerRepository = clientAppOwnerRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.clientAppRepository = clientAppRepository;
    }

    public ClientAppOwner clientAppOwnerRegistration(ClientAppOwner clientAppOwner) throws IshopResponseException {
        if (clientAppOwnerRepository.existsByUserName(clientAppOwner.getUserName()))
            throw new IshopResponseException("the owner of the client application with this name already exists");
        Role role = roleRepository.findByRoleType(Role.RoleType.ROLE_CLIENT_OWNER).orElseGet(() -> {
            Role roleClientOwner = new Role();
            roleClientOwner.setRoleType(Role.RoleType.ROLE_CLIENT_OWNER);
            roleRepository.save(roleClientOwner);
            return roleClientOwner;
        });
        clientAppOwner.setRole(role);
        clientAppOwner.setPassword(passwordEncoder.encode(clientAppOwner.getPassword()));
        clientAppOwnerRepository.save(clientAppOwner);
        return clientAppOwner;
    }
    ;
}

// тут бери два репозитория 1) для сохранения владельца приложения ClientAppOwnerRepository
// 2) для сохранения самого приложения RegisteredClientRepositoryCustom смотри как
// делал в ClientRegisterService
//этот класс с сервисом  создай по новому, эти конструктор, поля... просто для примера



