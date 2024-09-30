package com.auth.auth_service.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String name; // Permission name like 'manage_orders', 'view_inventory'

    @Column(length = 255)
    private String description;

    @JsonIgnore
     @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles= new HashSet<>();
}
