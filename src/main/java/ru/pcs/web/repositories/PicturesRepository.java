package ru.pcs.web.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.pcs.web.models.Picture;

import java.util.List;
import java.util.Optional;

@Repository
public interface PicturesRepository extends JpaRepository<Picture, Long> {

    List<Picture> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("select new ru.pcs.web.models.Picture(p.id, p.name, p.descr, p.account, p.imageInfo, p.visibility) from Picture p")
    Page<Picture> findAllWithoutContent(Pageable pageable);

   List<Picture> findByAccountEmail(String email, Pageable pageable);

    @Modifying
    @Query("update Picture p set p.visibility=:visibility where p.id =:id")
    void updateVisibility(@Param("visibility") Picture.Visibility visibility, @Param("id") long id);

   Optional<Picture> findByName(String name);

    void deleteById(Long id);

//  List<Picture> findAllById(Picture picture);
}
