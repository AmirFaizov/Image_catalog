package ru.pcs.web.models;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class ImageInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean description;

    private String type;

    private Long size;
    private String name;
    private String originalName;
    private Long height;
    private Long width;

}
