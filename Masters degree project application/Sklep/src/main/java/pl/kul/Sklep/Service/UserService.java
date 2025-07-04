package pl.kul.Sklep.Service;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kul.Sklep.Entity.User;
import pl.kul.Sklep.Repository.UserRepository;
import pl.kul.Sklep.Security.JwtTokenProvider;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public boolean checkIfEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }


    public User registerNewUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("User with this username already exists");
        }

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        validateUser(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
    private void validateUser(User user) {
        if (user.getUsername().isBlank() || user.getPassword().isBlank() || user.getEmail().isBlank()
                ) {
            throw new ValidationException("All fields must be filled");
        }
    }
    public void updateUserData(User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        validateUser(updatedUser);

        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setShippingAddresses(updatedUser.getShippingAddresses());
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());

        userRepository.save(existingUser);
    }

    public String authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        return tokenProvider.generateToken(authentication);
    }


    public User getCurrentUser(String token) {
        String username = tokenProvider.getUsernameFromJwt(token);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }
}
