package com.auth.auth_service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(UUID id) {
        return roleRepository.findById(id);
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public void deleteRole(UUID id) {
        roleRepository.deleteById(id);
    }

    public Role updateRole(UUID id, Role newRoleData) {
        return roleRepository.findById(id)
                .map(role -> {
                    role.setName(newRoleData.getName());
                    role.setDescription(newRoleData.getDescription());
                    role.setPermissions(newRoleData.getPermissions());
                    return roleRepository.save(role);
                }).orElseThrow(() -> new RuntimeException("Role not found"));
    }
}
