package ru.pcs.web.services;

import ru.pcs.web.models.Genre;
import ru.pcs.web.models.Picture;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    public void addToGenre(Picture picture, List<Long> genreId);

    public List<Genre> findAll();

    Optional<Genre> findById(Long genreId);
}
