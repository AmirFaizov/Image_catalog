package ru.pcs.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pcs.web.models.Genre;
import ru.pcs.web.models.Picture;
import ru.pcs.web.repositories.GenreRepository;
import ru.pcs.web.repositories.PicturesRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findById(Long genreId) {
        return genreRepository.findById(genreId);
    }

    @Override
    public void addToGenre(Picture picture, List<Long> genreIds){
        List<Genre> genres = genreRepository.findAllById(genreIds);
        picture.setGenres(genres);
    }
    }

