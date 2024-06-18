package ru.pcs.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import ru.pcs.web.dto.AddPictureForm;
import ru.pcs.web.dto.SignUpForm;
import ru.pcs.web.models.Account;
import ru.pcs.web.models.Genre;
import ru.pcs.web.models.Picture;
import ru.pcs.web.repositories.AccountsRepository;
import ru.pcs.web.services.AccountsService;
import ru.pcs.web.services.FilesService;
import ru.pcs.web.services.GenreService;
import ru.pcs.web.services.PicturesService;

import javax.validation.Valid;
import java.io.File;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/add")
public class AddFileController {

    @Autowired
    private final PicturesService picturesService;


    @Autowired
    private final AccountsService accountsService;

    @Autowired
    private final FilesService filesService;

    private final GenreService genreService;

    @GetMapping
    public String getRootPage(Model model) {
        List<Genre> genres = genreService.findAll();
        model.addAttribute("genres",genres);
        return "download";
    }

    @PostMapping("/file")
    public String add(@RequestParam("file") MultipartFile multipartFile,
                      @RequestParam("genreIds") List<Long> genreId,
                      @Valid AddPictureForm form,
                      BindingResult bindingResult,
                      Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Genre> genres = genreService.findAll();
        if (multipartFile.isEmpty()) {
            model.addAttribute("error", "Файл не выбран");
            model.addAttribute("genres",genres);
            return "download";
        }
        if(!filesService.formatСheck(multipartFile)){
            model.addAttribute("error", "Выбранный файл не является изображением");
            model.addAttribute("genres",genres);
            return "download";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Поле name не может быть пустым");
            model.addAttribute("genres", genres);
            return "download";
        }
        Optional<Picture> pictureOptional = picturesService.findByNameOptional(form.getName());
        if (pictureOptional.isPresent()){
            model.addAttribute("error", "Изображение с таким названием уже существует");
            model.addAttribute("genres", genres);
            return "download";
        }

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Account account = accountsService.findByEmail(username);
            picturesService.save(form, account);
        }

        String name = form.getName();
        Picture picture = picturesService.findByName(name);
        genreService.addToGenre(picture,genreId);
        filesService.upload(multipartFile, picture);
        return "redirect:/images";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParams(Model model) {
        model.addAttribute("error", "Поле genres не может быть пустым");
        model.addAttribute("genres", genreService.findAll());
        return "download";
    }
}

