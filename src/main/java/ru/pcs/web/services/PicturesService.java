package ru.pcs.web.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.pcs.web.dto.AddPictureForm;
import ru.pcs.web.models.Account;
import ru.pcs.web.models.Picture;

import java.util.List;
import java.util.Optional;

public interface PicturesService {

    Page<Picture> findAllContent(int pageNumber, int pageSize, String sortBy);

    List<Picture> findAllContent();

    Page<Picture> search(int pageNumber, int pageSize, String sortBy, String... searchString);

    Picture findByName(String name);

    Optional<Picture> findByNameOptional(String name);

    void save(AddPictureForm form, Account account);

    void save(Picture picture, Account account);

    Picture findById(Long id);

    void delete(Long id);

    void update(Picture picture, Picture.Visibility visibilit);

    Page<Picture> getPicturesByUser(int pageNumber, int pageSize, String sortBy, String email);
}
