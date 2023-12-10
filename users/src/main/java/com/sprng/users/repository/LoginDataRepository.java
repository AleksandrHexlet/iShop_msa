package com.sprng.users.repository;


import com.sprng.library.entity.LoginData;
import com.sprng.library.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface LoginDataRepository <T extends LoginData> extends JpaRepository<T, Integer> {
    Optional<T> findByUserName(String userName);
    boolean existsByUserName(String userName);
    Optional<T> findByUserNameAndRoleRoleTypeIn(String userName, Collection<Role.RoleType> roleTypes);
    boolean existsByIdAndUserNameAndRoleRoleType(int id,String username, Role.RoleType roleType);

}
