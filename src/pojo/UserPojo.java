package pojo;

import java.util.UUID;

public class UserPojo {
    private String email;
    private String password;
    private UUID uuid;

    public UserPojo(String email, String password) {
        this.email = email;
        this.password = password;
        this.uuid = UUID.randomUUID();
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}