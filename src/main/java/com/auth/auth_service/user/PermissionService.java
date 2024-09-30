package com.auth.auth_service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public Optional<Permission> getPermissionById(UUID id) {
        return permissionRepository.findById(id);
    }

    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    public void deletePermission(UUID id) {
        permissionRepository.deleteById(id);
    }

    public Permission updatePermission(UUID id, Permission newPermissionData) {
        return permissionRepository.findById(id)
                .map(permission -> {
                    permission.setName(newPermissionData.getName());
                    permission.setDescription(newPermissionData.getDescription());
                    return permissionRepository.save(permission);
                }).orElseThrow(() -> new RuntimeException("Permission not found"));
    }
}
