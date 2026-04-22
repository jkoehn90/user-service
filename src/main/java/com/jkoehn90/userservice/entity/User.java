package com.jkoehn90.userservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data // Lombok: generates getters, setters, toString, equals, hashCode
@Builder // Lombok: enables builder pattern (User.builder().email("...").build())
@NoArgsConstructor // Lombok: generates no-args constructor (required by JPA)
@AllArgsConstructor // Lombok: generates all-args constructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // Will store BCrypt hashed password

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; //Enum Role will store ADMIN or USER in place of 0 or 1 for readability
}
