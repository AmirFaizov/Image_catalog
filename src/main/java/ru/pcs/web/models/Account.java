package ru.pcs.web.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString(exclude = "pictures")
public class Account {

    public enum Role{
        USER, ADMIN
    }

    public enum State{
        NOT_CONFIRMED, CONFIRMED, DELETED, BANNED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING )
    private State state;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    private String password;

    @OneToMany(mappedBy = "account",
    cascade = CascadeType.ALL)
    private List<Picture> pictures;

    public boolean isAdmin(){
        return role.equals(Role.ADMIN);
    }
}
