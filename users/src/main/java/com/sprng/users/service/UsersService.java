package com.sprng.users.service;

import com.sprng.library.entity.LoginData;
import com.sprng.library.entity.Role;
import com.sprng.library.exception.IshopResponseException;
import com.sprng.users.repository.LoginDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UsersService {
    private LoginDataRepository<LoginData> loginDataRepository;


    @Autowired
    public UsersService(LoginDataRepository<LoginData> loginDataRepository) {
        this.loginDataRepository = loginDataRepository;
    }


    public boolean existsUserByIdAndUserNameAndRole(int id, String username, String role) {
        try {
            Role.RoleType roleType = Role.RoleType.valueOf( "ROLE_" + role.toUpperCase(Locale.ROOT)); // ROLE_ADMIN
        return loginDataRepository.existsByIdAndUserNameAndRoleRoleType(id,username,roleType);
        } catch (IllegalArgumentException exception){
            return false;
        }
    }
}
