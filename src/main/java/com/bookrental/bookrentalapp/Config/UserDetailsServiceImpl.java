package com.bookrental.bookrentalapp.Config;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookrental.bookrentalapp.Constants.Role;
import com.bookrental.bookrentalapp.Controllers.UserController;
import com.bookrental.bookrentalapp.Models.User;
import com.bookrental.bookrentalapp.Repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    private static final Logger logger = LogManager.getLogger(UserController.class);

     @Autowired
     private  UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        logger.info("Attempting to load user by username: {}", userName);
        
        Optional<User> userOptional = userRepository.findByuserName(userName);
        
        User user = userOptional.orElseThrow(() -> {
            logger.warn("User not found with username: {}", userName);
            return new UsernameNotFoundException("User not found with username: " + userName);
        });

        logger.info("User found with username: {}", userName);

        return org.springframework.security.core.userdetails.User.builder()
        .username(user.getUserName())
        .password(user.getPassword())
        .authorities(user.getRoles().stream().map(Role::name).toArray(String[]::new))
// Assuming roles are stored as strings in the User entity
        .build();

    }

        // private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        // return roles.stream()
        //         .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
        //         .collect(Collectors.toList());
    }
