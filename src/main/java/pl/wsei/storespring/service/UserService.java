package pl.wsei.storespring.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
        if (userRepository.existsByLogin(command.login())) {
            throw new IllegalArgumentException("Login: " + command.login() + " already exists");
        }

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
        user.setName(command.getName());
        user.setSurname(command.getSurname());
        user.setEmail(command.getEmail());
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

    public static class UpdateUserCommand {

        @NotEmpty private String name;

        @Size(min = 2) private String surname;

        @Email private String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public record CreateUserCommand(
        @NotEmpty String name,
        @Size(min = 2) String surname,
        @Email String email,
        @Pattern(regexp = "^[a-zA-Z0-9_.-]*$") String login
    ) {}

}
