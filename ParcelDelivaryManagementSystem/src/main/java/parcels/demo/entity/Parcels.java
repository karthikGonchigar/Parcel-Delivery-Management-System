package parcels.demo.entity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Parcels {

    @Id
    private int id;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, max = 30, message = "Name should be between 3 and 30 characters")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{8,}$",
            message = "Password must be strong (Min 8 chars, 1 uppercase, 1 lowercase, 1 digit, 1 special char)")
    private String password;

    @NotBlank(message = "Mobile cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobile;

    @NotNull(message = "Pickup date & time is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime pick;

    @NotBlank(message = "Source cannot be empty")
    private String source;

    @NotBlank(message = "Destination cannot be empty")
    private String destination;

    @Min(value = 1, message = "Weight must be greater than 0")
    private int weight;

    @Min(value = 1, message = "Height must be greater than 0")
    private int height;

    @Min(value = 1, message = "Width must be greater than 0")
    private int width;


    public Parcels() {}

    public Parcels(int id, String name, String email, String password, String mobile, LocalDateTime pick, String source,
                   String destination, int weight, int height, int width) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.pick = pick;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.height = height;
        this.width = width;
    }

    @Override
    public String toString() {
        return "Parcels [id=" + id + 
               ", name=" + name + 
               ", email=" + email + 
               ", password=" + password + 
               ", mobile=" + mobile + 
               ", pick=" + pick + 
               ", source=" + source + 
               ", destination=" + destination + 
               ", weight=" + weight + 
               ", height=" + height + 
               ", width=" + width + "]";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public LocalDateTime getPick() { return pick; }
    public void setPick(LocalDateTime pick) { this.pick = pick; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }
}
