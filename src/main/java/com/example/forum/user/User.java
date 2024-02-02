package com.example.forum.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class User implements UserDetails {
    private String id;
    private String username;
    private String displayName;
    private String password;
    private String email;
    private Boolean emailVerified;
    private String avatar;
    private List<Role> roles;
    private String image;
    private String bio;
    private String signature;
    private String url;
    private String bearerToken;
    private String isOnline;
    private String registrationIp;
    private String lastIp;
    private String lastSeenAt;
    private String createdAt;
    private String updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles!=null){
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
