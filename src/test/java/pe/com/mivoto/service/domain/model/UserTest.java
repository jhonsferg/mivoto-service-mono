package pe.com.mivoto.service.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserBuilderAndGetters() {
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .id(1L)
                .firstName("Jhon")
                .lastName("Doe")
                .password("secret")
                .documentNumber("12345678")
                .email("jhon@example.com")
                .role(pe.com.mivoto.service.domain.enums.UserRole.VOTER)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertEquals(1L, user.getId());
        assertEquals("Jhon", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("secret", user.getPassword());
        assertEquals("12345678", user.getDocumentNumber());
        assertEquals("jhon@example.com", user.getEmail());
        assertEquals(pe.com.mivoto.service.domain.enums.UserRole.VOTER, user.getRole());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    void testUserSetters() {
        User user = new User();
        user.setId(2L);
        user.setFirstName("Jane");

        assertEquals(2L, user.getId());
        assertEquals("Jane", user.getFirstName());
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = User.builder().id(1L).firstName("user").build();
        User user2 = User.builder().id(1L).firstName("user").build();
        User user3 = User.builder().id(2L).firstName("other").build();

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }

    @Test
    void testToString() {
        User user = User.builder().id(1L).firstName("test").build();
        assertNotNull(user.toString());
    }
}
