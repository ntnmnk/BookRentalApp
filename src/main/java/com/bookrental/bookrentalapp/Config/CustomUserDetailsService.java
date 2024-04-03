package com.bookrental.bookrentalapp.Config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bookrental.bookrentalapp.Models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class CustomUserDetailsService implements UserDetails {
    

    private static final long serialVersionUID = 1L;
  
    private Long id;
  
    private String username;
  
    private String email;
  
    @JsonIgnore
    private String password;
     
    private Collection<? extends GrantedAuthority> authorities;

    // public static CustomUserDetailsService build(User user) {
    //     return CustomUserDetailsService
    //             .id(user.getId())
    //             .username(user.getUserName())
    //             .email(user.getEmail())
    //             .password(user.getPassword())
    //             .build();
    // }

  
    public CustomUserDetailsService(User user) {
        this.id = user.getId();
        this.username = user.getUserName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
  
    
  
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return authorities;
    }
  
    public Long getId() {
      return id;
    }
  
    public String getEmail() {
      return email;
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
  
    // @Override
    // public boolean equals(Object o) {
    //   if (this == o)
    //     return true;
    //   if (o == null || getClass() != o.getClass())
    //     return false;
    //   CustomUserDetailsService user = (CustomUserDetailsService) o;
    //   return Objects.equals(id, user.id);
    // }
  }