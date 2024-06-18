package ru.pcs.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pcs.web.dto.AddPictureForm;
import ru.pcs.web.dto.SignUpForm;
import ru.pcs.web.models.Account;
import ru.pcs.web.models.Genre;
import ru.pcs.web.models.Picture;
import ru.pcs.web.repositories.PicturesRepository;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PicturesServiceImpl implements PicturesService {

    @Autowired
    PicturesRepository picturesRepository;


    @Override
    public Page<Picture> findAllContent(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        return picturesRepository.findAllWithoutContent(pageable);
    }

    @Override
    public List<Picture> findAllContent() {
        return picturesRepository.findAll();
    }


    @Override
    public Page<Picture> search(int pageNumber, int pageSize, String sortBy, String... searchString) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        List<Picture> pictures = picturesRepository.findByNameContainingIgnoreCase(searchString[0], pageable);
        long totalCount = pictures.size();
        return PageableExecutionUtils.getPage(pictures, pageable, () -> totalCount);
    }


    public Picture findByName(String name) {
        Optional<Picture> pictureOptional = picturesRepository.findByName(name);
        if (pictureOptional.isPresent()) {
            Picture picture = pictureOptional.get();
            return picture;
        }
        else {
            throw new NoSuchElementException("Picture with name " + name + " not found");
        }
    }

    public Optional<Picture> findByNameOptional(String name) {
        Optional<Picture> pictureOptional = picturesRepository.findByName(name);
        if (pictureOptional.isPresent()) {
            return pictureOptional;
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public void save(AddPictureForm form, Account account) {
        Picture picture = Picture.builder()
                .name(form.getName())
                .descr(form.getDescr())
                .visibility(Picture.Visibility.PUBLIC)
                .account(account)
                .build();
        picturesRepository.save(picture);
    }

    @Override
    public void save(Picture picture, Account account) {
        picture = Picture.builder()
                .name(picture.getName())
                .descr(picture.getDescr())
                .visibility(Picture.Visibility.PUBLIC)
                .account(account)
                .build();
        picturesRepository.save(picture);
    }

    @Override
    @Transactional
    public void update(Picture picture, Picture.Visibility visibility) {
        picturesRepository.updateVisibility(visibility, picture.getId());
    }

    @Override
    public Picture findById(Long id) {
      Optional<Picture> picturesOptional = picturesRepository.findById(id);
        if (picturesOptional.isPresent()) {
            Picture picture = picturesOptional.get();
            return picture;
        }
        else {
            throw new NoSuchElementException("Picture with id " + id + " not found");
        }
    }

    @Override
    public void delete(Long id) {
        picturesRepository.deleteById(id);
    }

    @Override
    public Page<Picture> getPicturesByUser(int pageNumber, int pageSize, String sortBy, String email) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        List<Picture> pictures = picturesRepository.findByAccountEmail(email, pageable);
        long totalCount = pictures.size();
        return PageableExecutionUtils.getPage(pictures, pageable, () -> totalCount);
    }
}


