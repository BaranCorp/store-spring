package pl.wsei.storespring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.wsei.storespring.dto.UserDTO;
import pl.wsei.storespring.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserService.CreateUserCommand command) {
        return ResponseEntity
                .status(201)
                .body(new UserDTO(userService.create(command)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
        @PathVariable Long id,
        @RequestBody UserService.UpdateUserCommand command
    ) {
        return ResponseEntity.ok(new UserDTO(userService.update(id, command)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(new UserDTO(userService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll().stream().map(UserDTO::new).toList());
    }

    @DeleteMapping
    public ResponseEntity<UserDTO> deleteUser(@RequestParam Long id) {
        return ResponseEntity.status(204).build();
    }

}
