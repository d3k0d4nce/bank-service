package ru.kishko.bankservice.user.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;
import ru.kishko.bankservice.account.entities.Account;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", unique = true, length = 20, nullable = false)
    private String login;

    @Column(name = "email", unique = true, length = 50)
    private String email;

    @Column(name = "phone", unique = true, length = 11)
    private String phone;

    @Column(name = "firstName", length = 50, nullable = false)
    private String firstName;

    @Column(name = "middleName", length = 50, nullable = false)
    private String middleName;

    @Column(name = "lastName", length = 50, nullable = false)
    private String lastName;

    @Column(name = "birthDate", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @Column(name = "password", length = 120, nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId", referencedColumnName = "id")
    private Account account;

    public void checkContactInfo() {
        if (phone == null && email == null) {
            throw new IllegalArgumentException("Должен быть указан хотя бы один контактный способ (телефон или email)");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
