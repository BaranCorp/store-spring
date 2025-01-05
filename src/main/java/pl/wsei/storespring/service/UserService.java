package pl.wsei.storespring.service;

import org.springframework.stereotype.Service;
import pl.wsei.storespring.exception.ResourceNotFoundException;
import pl.wsei.storespring.model.User;
import pl.wsei.storespring.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(CreateUserCommand command) {
        User user = new User(
                null,
                command.name(),
                command.surname(),
                command.email(),
                command.login()
        );

        return userRepository.save(user);
    }

    public User update(Long id, UpdateUserCommand command) {
        User user = getById(id);
        user.setName(command.name());
        user.setSurname(command.surname());
        user.setEmail(command.email());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));
    }

    public record UpdateUserCommand(
        String name,
        String surname,
        String email
    ) {}

    public record CreateUserCommand(
        String name,
        String surname,
        String email,
        String login
    ) {}

}
