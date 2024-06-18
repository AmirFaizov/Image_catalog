package ru.pcs.web.models;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Picture {


    public enum Visibility {
        PUBLIC, PRIVATE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Visibility visibility;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ImageInfo imageInfo;

    private String name;

    private String descr;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Account account;

    @ManyToMany
    @JoinTable(name = "picture_genre",
            joinColumns = @JoinColumn(name = "pictures_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;


    public Picture(Long id, String name, String descr, Account account, ImageInfo imageInfo, Visibility visibility) {
        this.id = id;
        this.name = name;
        this.descr = descr;
        this.account = account;
        this.imageInfo = imageInfo;
        this.visibility = visibility;
    }

}
