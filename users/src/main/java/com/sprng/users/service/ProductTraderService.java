package com.sprng.users.service;


import com.sprng.library.entity.ProductTrader;
import com.sprng.library.entity.Role;
import com.sprng.library.exception.IshopResponseException;
import com.sprng.users.repository.ProductTraderRepository;
import com.sprng.users.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProductTraderService {
    private ProductTraderRepository productTraderRepository;
    private RoleRepository roleRepository;

    private PasswordEncoder encoderPassword;

@Autowired
    public ProductTraderService(ProductTraderRepository productTraderRepository,
                                RoleRepository roleRepository,
                                PasswordEncoder loginFormSecurityConfigurationEncoderPassword) {
        this.productTraderRepository = productTraderRepository;
        this.roleRepository = roleRepository;
        this.encoderPassword = loginFormSecurityConfigurationEncoderPassword;
    }



    public ProductTrader traderRegistration(ProductTrader productTrader) throws IshopResponseException {
        if (productTraderRepository.existsByUserName(productTrader.getUserName()))
            throw new IshopResponseException("Такой продавец уже зарегистрирован.");
        String passwordNotSecured = productTrader.getPassword();
        System.out.println(productTrader.getUserName());


        // наполнять нужно того продавца, который пришёл, а не новую logindata
        // productTraderRepository сам сохраняет в обе таблицы
        productTrader.setUserName(productTrader.getUserName().strip()); // не getName, a getUserName
        productTrader.setPassword(encoderPassword.encode(productTrader.getPassword().strip()));

        Role roleTrader = roleRepository.findByRoleType(Role.RoleType.ROLE_TRADER).orElseGet(()->{
            Role traderRole = new Role();
            traderRole.setRoleType(Role.RoleType.ROLE_TRADER);
             Role traderRoleFromDBwithID = roleRepository.save(traderRole);
             return traderRoleFromDBwithID;
        });
        System.out.println("roleTrader === " + roleTrader.getRoleType().name());
        productTrader.setRole(roleTrader);
        productTrader.setDateRegistration(LocalDate.now());
        productTraderRepository.save(productTrader);
        productTrader.setPassword(passwordNotSecured);
        return productTrader;
    }

}
//http://127.0.0.1:9090/oauth2/authorize?response_type=code&client_id=$2a$10$.8ck0l.PVTGuKzrfPPpUBepgPHKWrhR.Un6/JjU/dWuYmfBOC1iTy&redirect_uri=http://localhost:8888/redirect&scope=openid read