package com.alex.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

/**
 * Class User to represent a user.
 * I could use org.springframework.validation.Validator to validate the field 
 * but I like the annotations being set here.
 * By using @Entity, it will automatically create the table in the database (hibernate).
 */
@Entity
public class User {

    /**
     * User id.
     */
    private @GeneratedValue @Id Long id;

      /**
      * Make sure the password is strong to protect from brute force.
      * The password is not saved in the database.
      */
    @Transient
    @Size(min=6, max=30)
    @Pattern.List({
          @Pattern(regexp = "(?=.*[0-9]).+", message = "Password must contain one digit."),
          @Pattern(regexp = "(?=.*[a-z]).+", message = "Password must contain one lowercase letter."),
          @Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must contain one uppercase letter."),
          @Pattern(regexp = "(?=.*[!@#$%^&*+=?-_()/\"\\.,<>~`;:]).+", message ="Password must contain one special character."),
          @Pattern(regexp = "(?=\\S+$).+", message = "Password must not contain whitespace.")
    })
    private String password;
    
    @NotNull
    @Pattern(regexp = "(?=\\S+$).+", message = "Username must not contain whitespace.")
    @Size(min=4, max=30)
    @Column(unique=true)
    private String username;
    
    /**
     * The hashed password is saved in the database, for exemple:
     * $2a$10$Wwyx8DPNhNsJ/.rXlU6U8OzZfSKtMc6OJDM9wClU3G2kA//M3JsjC
     */
    @Column(nullable=false)
    private String hashedPassword;
    
    /**
     * Default constructor.
     */
    public User() { }
    
    /**
     * Constructor.
     * @param username - the username.
     * @param password - the password.
     */
    public User(String username, String hashedPassword) {
        this.username = username;
        this.hashedPassword = hashedPassword;
    }
    
    /**** setter & getter ****/
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }
}
