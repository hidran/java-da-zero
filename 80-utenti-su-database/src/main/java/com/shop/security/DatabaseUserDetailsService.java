package com.shop.security;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {
    private final JdbcClient jdbc;
    public DatabaseUserDetailsService(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        return jdbc.sql("select username, password_hash, role, enabled from app_user where username = ?")
                .param(username)
                .query((rs, row) -> User.withUsername(rs.getString("username"))
                        .password(rs.getString("password_hash"))
                        .roles(rs.getString("role"))
                        .disabled(!rs.getBoolean("enabled"))
                        .build())
                .optional()
                .orElseThrow(() -> new UsernameNotFoundException("Unknown user"));
    }
}
