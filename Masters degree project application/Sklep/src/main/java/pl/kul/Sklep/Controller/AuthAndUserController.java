package pl.kul.Sklep.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kul.Sklep.Entity.User;
import pl.kul.Sklep.Service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthAndUserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        try {
            if (user.getId() != null) {
                throw new IllegalArgumentException("User Already Exists");
            }

            userService.registerNewUser(user);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody Map<String, String> request) {
        try {
            String token = userService.authenticateUser(request.get("username"), request.get("password"));
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid username or password"));
        }
    }

    @GetMapping("/users/me")
    public ResponseEntity<User> getCurrentUser(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            User user = userService.getCurrentUser(token);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @GetMapping("/users/check-email")
    public ResponseEntity<Boolean> checkEmailExists(
            @RequestParam String email,
            @RequestHeader(value = "Authorization", required = false) String token) {

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        User currentUser = null;

        if (token != null && !token.isEmpty()) {
            try {
                currentUser = userService.getCurrentUser(token);
            } catch (Exception e) {
                currentUser = null;
            }
        }

        if (currentUser != null && currentUser.getEmail().equals(email)) {
            return ResponseEntity.ok(false);
        }

        boolean exists = userService.checkIfEmailExists(email);
        return ResponseEntity.ok(exists);
    }


    @PutMapping("/users/update")
    public ResponseEntity<String> updateCurrentUser(@Valid @RequestBody User updatedUser, HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            userService.updateUserData(updatedUser);
            return ResponseEntity.ok("User data updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



}
