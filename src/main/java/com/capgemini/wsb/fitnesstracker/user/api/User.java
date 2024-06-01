package com.capgemini.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.time.LocalDate;

import static org.hibernate.annotations.CascadeType.MERGE;

/**
 * Entity representing a user.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    @Getter
    @Setter
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Getter
    @Setter
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Setter
    @Getter
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Setter
    @Getter
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Constructor for creating a new User.
     *
     * @param firstName the first name of the user
     * @param lastName  the last name of the user
     * @param birthdate the birthdate of the user
     * @param email     the email of the user
     */
    public User(final String firstName, final String lastName, final LocalDate birthdate, final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }
}
