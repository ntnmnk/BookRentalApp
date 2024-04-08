package com.bookrental.bookrentalapp.Models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bookrental.bookrentalapp.Constants.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;


@Entity
@Builder
@Data
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
public class User  {

  public User(String username, String email, String password) {
    this.userName = username;
    this.email = email;
    this.password = password;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String userName;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  private boolean active;

  @Column(nullable = false)
  private int activeRentals;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  Set<Role> roles = new HashSet<>();

  @Builder.Default
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Rental> rentals = new ArrayList<>(0);


  public void addRole(Role role) {
    if (role == null) {
      throw new IllegalArgumentException("Role Cannot be null!!!");
    }
    roles.add(role);
    //logger.debug("Role {} added to user {}", role, getUsername());
  }
}
