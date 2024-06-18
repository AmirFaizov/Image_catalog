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
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @ManyToMany(mappedBy = "genres")
    private List<Picture> pictures;

    @Override
    public String toString() {
        return name;
    }

}

