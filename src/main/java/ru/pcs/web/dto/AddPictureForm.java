package ru.pcs.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pcs.web.models.Picture;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddPictureForm {

    @NotBlank(message = "Поле 'Название' не может быть пустым")
    private String name;

    private String descr;
}
