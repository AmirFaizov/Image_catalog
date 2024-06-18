package ru.pcs.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pcs.web.dto.AccountDto;
import ru.pcs.web.dto.AddPictureForm;
import ru.pcs.web.models.Account;
import ru.pcs.web.models.Genre;
import ru.pcs.web.models.Picture;
import ru.pcs.web.repositories.AccountsRepository;
import ru.pcs.web.repositories.PicturesRepository;
import ru.pcs.web.services.AccountsService;
import ru.pcs.web.services.FilesService;
import ru.pcs.web.services.GenreService;
import ru.pcs.web.services.PicturesService;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping
public class PicturesController {


    @Autowired
    private final PicturesService picturesService;

    @Autowired
    private final AccountsService accountsService;

    @Autowired
    private final GenreService genreService;

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "8") int size,
                         @RequestParam(defaultValue = "id") String sortBy,
                         Model model,
                         @RequestParam("search") String searchString
                         ) {
        Page<Picture> pictures = picturesService.search(page, size, sortBy, searchString);
        model.addAttribute("url", "/search?"+searchString);
        model.addAttribute("urlForPagination", "/search");
        model.addAttribute("pictures", pictures);
        model.addAttribute("searchString", searchString);
        return "search";
    }

    @GetMapping("/genre")
    public String getPicturesByGenre(@RequestParam Long genreId, Model model) {
        List<Genre> allGenres = genreService.findAll();
        Genre genre = genreService.findById(genreId).orElseThrow();
        List<Picture> pictures = genre.getPictures();
        model.addAttribute("pictures", pictures);
        model.addAttribute("genres", allGenres);
        return "sortedByGenre";
    }


    @PostMapping("/picture/{id}/visibility")
    public String changeVisibility(@PathVariable("id") Long id,
                                   @RequestParam("visibility") String visibility,
                                   Principal principal) {
        Picture picture = picturesService.findById(id);
        if (picture == null) {
            return "redirect:/images";
        }
        Account account = accountsService.findByEmail(principal.getName());
        if (!picture.getAccount().equals(account)) {
            return "redirect:/images";
        }
        Picture.Visibility visibilityEnum = Picture.Visibility.valueOf(visibility);
        picturesService.update(picture,visibilityEnum);
        return "redirect:/images";
    }

    @PostMapping("/picture/{id}/delete")
    public String delete(@PathVariable("id") Long id){
        picturesService.delete(id);
        return "redirect:/images";
    }

    @GetMapping(("/images"))
    public String getAllAccounts(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "6") int size,
                                 @RequestParam(defaultValue = "id") String sortBy,
                                 Principal principal) {
        String account = principal.getName();
        List<Genre> genres = genreService.findAll();
        Page<Picture> pictures = picturesService.findAllContent(page, size, sortBy);
        model.addAttribute("account", account);
        model.addAttribute("pictures", pictures);
        model.addAttribute("genres", genres);
        model.addAttribute("url", "/images");
        return "ad";
    }

    @GetMapping(("/changed"))
    public String modifiedImages(Model model,
                                 Principal principal) {
        String account = principal.getName();
        List<Picture> pictures = picturesService.findAllContent();
        List<Picture> filteredPictures = pictures.stream()
                .filter(picture -> picture.getImageInfo().getDescription())
                .collect(Collectors.toList());
        model.addAttribute("account", account);
        model.addAttribute("pictures", filteredPictures);
        model.addAttribute("url", "/changed");
        return "modifiedImages";
    }

    @GetMapping("/my-images")
    public String myImages(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "100") int size,
                           @RequestParam(defaultValue = "id") String sortBy,
                           Model model, Principal principal) {
        String username = principal.getName();
        Page<Picture> pictures = picturesService.getPicturesByUser(page, size, sortBy, username);
        model.addAttribute("pictures", pictures);
        return "myImages";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParams(MissingServletRequestParameterException ex, Model model) {
        return "redirect:/images";
    }
}
