package ru.pcs.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pcs.web.models.Genre;

public interface GenreRepository extends JpaRepository<Genre,Long> {
}
