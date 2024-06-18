package ru.pcs.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import ru.pcs.web.models.ImageInfo;

import java.util.Optional;

@Repository
public interface ImageInfoRepository extends JpaRepository<ImageInfo, Long> {

    ImageInfo findByName(String fileName);
}
