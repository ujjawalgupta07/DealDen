package com.example.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    /**This creates a third-table in the database
     * Table :: user_roles
     * ----------------------
     *user_id |  role_id
    * --------|--------------
    * 1       | 1
    * 1       | 2
    * 2       | 1
    * 2       | 2
    * 3       | 3
    * This way, you do not have duplicate data in user or roles, and the join table
    * keeps the many-to-many relationship clean and normalized.
    * Why FetchType.EAGER ? --> EAGER tells hibernate to load roles immediately when user is loaded
     You usually want to know a user's roles for authentication as soon as user logs in
     **/
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

     @Column(unique = true, nullable = false)
    private String username;

    private String firstName;

    private String lastName;

    private boolean enabled = true;

    @Column(name = "refresh_token")
    private String refreshToken;

}





