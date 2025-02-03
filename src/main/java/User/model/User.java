package User.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Username can not be empty")
    @NotNull(message = "Username can not be null")
    private String username;

    @Column(nullable = false, unique = true)
    @Email(message = "Email should be written in correct way")
    @NotEmpty(message = "Email can not be empty")
    @NotNull(message = "Email can not be null")
    private String email;

    @Column(nullable = false)
    @NotEmpty(message = "Password can not be empty")
    @NotNull(message = "Password can not be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
    @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter")
    @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one number")
    @Pattern(regexp = ".*[!@#$%^&*()_+=<>?/].*", message = "Password must contain at least one special character")
    private String password;
}
