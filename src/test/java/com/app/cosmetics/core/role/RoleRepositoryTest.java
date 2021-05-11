package com.app.cosmetics.core.role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void beforeEach() {
        roleRepository.deleteAll();
    }

    @Test
    void it_should_find_Role_by_type() {
        // Arrange
        Role role = new Role(RoleType.ROLE_ADMIN);
        Role save = roleRepository.save(role);

        // Act
        Role result = roleRepository.findRoleByType(RoleType.ROLE_ADMIN)
                .orElseThrow();

        // Assert
        assertEquals(save.getType(), result.getType());
        assertEquals(save.getId(), result.getId());
    }
}