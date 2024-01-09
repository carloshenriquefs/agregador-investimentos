package project.agregadorinvestimentos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.agregadorinvestimentos.dto.CreateUserDto;
import project.agregadorinvestimentos.entity.User;
import project.agregadorinvestimentos.repository.UserRepository;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UUID createUser(CreateUserDto createUserDto) {

        var entity = new User(
                UUID.randomUUID(),
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                Instant.now(),
                null);

        var user = userRepository.save(entity);

        return user.getUserId();
    }
}
