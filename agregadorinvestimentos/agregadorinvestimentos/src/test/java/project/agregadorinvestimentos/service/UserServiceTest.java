package project.agregadorinvestimentos.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.agregadorinvestimentos.dto.CreateUserDto;
import project.agregadorinvestimentos.entity.User;
import project.agregadorinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser {

        @Test
        @DisplayName("Should create a user with success")
        void shouldCreatedAUserWithSuccess() {
            // Arrange
            var entity = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "123456",
                    Instant.now(),
                    null
            );

            doReturn(entity).when(userRepository).save(userArgumentCaptor.capture());

            var input = new CreateUserDto(
                    "username",
                    "email@email.com",
                    "123456"
            );

            // Act
            var output = userService.createUser(input);

            // Assert
            assertNotNull(output);

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs() {
            // Arrange
            doThrow(RuntimeException.class).when(userRepository).save(any());
            var input = new CreateUserDto(
                    "username",
                    "email@email.com",
                    "123456"
            );

            // Act & Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }
    }

    @Nested
    class getUserById {

        @Test
        @DisplayName("Should get user by id with success when optional is present")
        void shouldGetUserByIdWithSuccessWhenOptionalIsPresent() {
            //Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );

            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());

            //Act
            var output = userService.getUserById(user.getUserId().toString());

            //Assert
            assertTrue(output.isPresent());
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should get user by id with success when optional is empty")
        void shouldGetUserByIdWithSuccessWhenOptionalIsEmpty() {
            //Arrange
            var userId = UUID.randomUUID();

            doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());

            //Act
            var output = userService.getUserById(userId.toString());

            //Assert
            assertTrue(output.isEmpty());
            assertEquals(userId, uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs() {
            // Arrange
            doThrow(RuntimeException.class).when(userRepository).findById(any());
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );

            // Act & Assert
            assertThrows(RuntimeException.class, () -> userService.getUserById(user.getUserId().toString()));
        }
    }

    @Nested
    class deleteById {

        @Test
        @DisplayName("Should delete user with success when user exists")
        void shouldDeleteUserWithSuccessWhenUserExists() {

            // Arrange
            doReturn(true).when(userRepository).existsById(uuidArgumentCaptor.capture());

            doNothing().when(userRepository).deleteById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();

            // Act
            userService.deleteById(userId.toString());

            // Assert
            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));

            verify(userRepository, times(1)).existsById(idList.get(0));
            verify(userRepository, times(1)).deleteById(idList.get(1));
        }

        @Test
        @DisplayName("Should not delete user when user not exists")
        void shouldNotDeleteUserWhenUserNotExists() {
            // Arrange
            doReturn(false).when(userRepository).existsById(uuidArgumentCaptor.capture());

            var userId = UUID.randomUUID();

            // Act
            userService.deleteById(userId.toString());

            // Assert
            assertEquals(userId, uuidArgumentCaptor.getValue());

            verify(userRepository, times(1)).existsById(uuidArgumentCaptor.getValue());
            verify(userRepository, times(0)).deleteById(any());
        }
    }
}
