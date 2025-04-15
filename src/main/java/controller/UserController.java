package controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/users")
@Tag(name = "users-controller", description = "Управление пользователями")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Получить всех пользователей")
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @Operation(summary = "Получить пользователя по ID")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Создать нового пользователя")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
       User createdUser = userService.save(user);
        return ResponseEntity.status(201).body(createdUser);
    }

    @Operation(summary = "Обновить пользователя по ID")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        user.setId(id);
        Optional<User> updatedUser= Optional.ofNullable(userService.save(user));
        return updatedUser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удалить пользователя по ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Очистить таблицу user (TRUNCATE)")
    @PostMapping("/table/truncate")
    public ResponseEntity<Void> truncateUserTable() {
        userService.truncateTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удалить таблицу user (DROP)")
    @DeleteMapping("/table/drop")
    public ResponseEntity<Void> dropUserTable() {
        userService.dropTable();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Создать таблицу user (CREATE)")
    @PostMapping("/table/create")
    public ResponseEntity<Void> createUserTable() {
        userService.createTable();
        return ResponseEntity.ok().build();
    }

}
