package com.sprng.library.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_role")
public class Role {
    @Id
    @GeneratedValue
    int id;
    @Enumerated(EnumType.STRING)
    RoleType roleType;


    public enum RoleType {
        ROLE_ADMIN,
        ROLE_CUSTOMER,
        ROLE_TRADER,
        ROLE_CLIENT,
        ROLE_READONLY_ADMIN,
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
}
