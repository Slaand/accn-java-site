package com.slaand.site.model.enumerated;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {

    ADMIN("ADMIN"), USER("USER");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String getAuthority() {
        return this.getRole();
    }

}
