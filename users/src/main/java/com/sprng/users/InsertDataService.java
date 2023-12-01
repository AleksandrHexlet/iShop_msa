package com.sprng.users;

import com.sprng.library.entity.AdminIshop;
import com.sprng.library.entity.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

public class InsertDataService {



//        @Bean
//        public void insertRole() {
//            return (args) -> {
//// создаём роли и устанавливаем в БД
//                Role roleAdmin = new Role();
//                roleAdmin.setRoleType(Role.RoleType.ROLE_ADMIN);
//                if(roleRepository.existsByRoleType(Role.RoleType.ROLE_ADMIN));
//                Role roleReadOnlyAdmin = new Role();
//                roleReadOnlyAdmin.setRoleType(Role.RoleType.ROLE_READONLY_ADMIN);
//
//                roleRepository.save(roleAdmin);
//                roleRepository.save(roleReadOnlyAdmin);
//
//                AdminIshop admin = new AdminIshop("Иван Админ");
//                admin.setRole(roleAdmin);
//                admin.setUserName("admin");
//                admin.setPassword("$2a$10$7oCTGflP2kNI3WP41FV2IOFyXVh9beW6e9ywgsew3/rmIOxoEq/LW");
//
//                AdminIshop readOnlyAdmin = new AdminIshop("Ivan ReadOnly");
//                readOnlyAdmin.setRole(roleReadOnlyAdmin);
//                readOnlyAdmin.setUserName("readOnlyAdmin");
//                readOnlyAdmin.setPassword("$2a$10$fnDj5PUIC0rWC1otWLxHbeRXK8Plfh1oHGriPC6QBI5cP99Tb3wTq");
////
//                adminRepository.save(admin);
//                adminRepository.save(readOnlyAdmin);
//
//            };
//        }


//    @Bean
//    public void insertAdmine() {
//        return (args) -> {
            //  создаём админов, проверяем что такая роль уже есть  и если есть тогда я
            //  через  public Optional<Role> findByRoleType(Role.RoleType roleType); беру роль и устанавливаю админу
            //  также вынеси сюда создание клиентских приложений из com.sprng.users.service;ClientAppRegistrationService
//            Role roleAdmin = new Role();
//            roleAdmin.setRoleType(Role.RoleType.ROLE_ADMIN);
//            // так проверяем наличие роли
//            if(roleRepository.existsByRoleType(Role.RoleType.ROLE_ADMIN));
//            Role roleReadOnlyAdmin = new Role();
//            roleReadOnlyAdmin.setRoleType(Role.RoleType.ROLE_READONLY_ADMIN);
//
//            roleRepository.save(roleAdmin);
//            roleRepository.save(roleReadOnlyAdmin);
//
//            AdminIshop admin = new AdminIshop("Иван Админ");
//            admin.setRole(roleAdmin);
//            admin.setUserName("admin");
//            admin.setPassword("$2a$10$7oCTGflP2kNI3WP41FV2IOFyXVh9beW6e9ywgsew3/rmIOxoEq/LW");
//
//            AdminIshop readOnlyAdmin = new AdminIshop("Ivan ReadOnly");
//            readOnlyAdmin.setRole(roleReadOnlyAdmin);
//            readOnlyAdmin.setUserName("readOnlyAdmin");
//            readOnlyAdmin.setPassword("$2a$10$fnDj5PUIC0rWC1otWLxHbeRXK8Plfh1oHGriPC6QBI5cP99Tb3wTq");
////
//            adminRepository.save(admin);
//            adminRepository.save(readOnlyAdmin);

//        };
//    }


//}
}
