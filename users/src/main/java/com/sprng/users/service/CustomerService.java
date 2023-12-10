package com.sprng.users.service;


import com.sprng.library.entity.Customer;
import com.sprng.library.entity.Role;
import com.sprng.library.exception.IshopResponseException;
import com.sprng.users.repository.CustomerRepository;
import com.sprng.users.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

//    private AuthenticationManager authenticationManager;


    @Autowired
    public CustomerService(CustomerRepository customerRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder
//                           ,AuthenticationManager authenticationManager
                           ) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
//        this.authenticationManager = authenticationManager;
    }



    public void customerRegistration(Customer customer) throws IshopResponseException {
        if (customerRepository.existsByUserName(customer.getUserName()))
            throw new IshopResponseException("Пользователь с таким именем уже зарегистрирован");
        Role role = roleRepository.findByRoleType(Role.RoleType.ROLE_CUSTOMER).orElseGet(()->{
            Role roleCustomer = new Role();
            roleCustomer.setRoleType(Role.RoleType.ROLE_TRADER);
            roleRepository.save(roleCustomer);
            return roleCustomer;
        });
        customer.setRole(role);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);
    }


    public Customer getCustomer(String username) throws IshopResponseException {
        return customerRepository.findByUserName(username)
                .orElseThrow(()-> new IshopResponseException("Customer not found. Unfortunately"));
    }
}
