package com.brk.commentor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@ToString(exclude = "password")
@Entity
public class User implements Serializable {
    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @JsonIgnore
    private String password;


    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }
}
