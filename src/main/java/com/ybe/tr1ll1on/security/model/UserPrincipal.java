package com.ybe.tr1ll1on.security.model;

import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.security.common.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {
    private final String userId;
    private final String password;
    private final Authority authority;
    private final Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public String getUsername() {
        return userId;
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

    public static UserPrincipal create(User user) {
        return new UserPrincipal(
                String.valueOf(user.getId()),
                user.getPassword(),
                user.getAuthority(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getAuthority().toString())));

    }
}