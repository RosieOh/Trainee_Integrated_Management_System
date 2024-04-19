package com.lms.global.cosntant;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ROLE_ADMIN"),
    MANAGER("ROLE_MANAGER"),
    TEACHER("ROLE_TEACHER"),
    STUDENT("ROLE_STUDENT");

    private final String roleName;


    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRole() {
        return roleName;
    }
}
