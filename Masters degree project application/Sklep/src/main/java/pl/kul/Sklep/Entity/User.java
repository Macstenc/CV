package pl.kul.Sklep.Entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, message = "Username must be at least 3 characters long")
    @Indexed(unique = true)
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Indexed(unique = true)
    private String email;

    private String firstName;

    private String lastName;

    private String role;

    private String phoneNumber;
    private List<Address> shippingAddresses;
}
