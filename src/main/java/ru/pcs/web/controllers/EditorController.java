package ru.pcs.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.pcs.web.models.Account;
import ru.pcs.web.models.Picture;
import ru.pcs.web.repositories.PicturesRepository;
import ru.pcs.web.services.AccountsService;
import ru.pcs.web.services.FilesService;
import ru.pcs.web.services.PicturesService;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping()
public class EditorController {

    @Autowired
    private final FilesService filesService;

    private final PicturesService picturesService;

    private final AccountsService accountsService;


    @PostMapping(("/picture/{id}/duo"))
    public String getFile(@PathVariable("id") Long id,
                          @RequestParam ("x") int x,
                          @RequestParam ("y") int y,
                          @RequestParam ("y") int angle,
                          @RequestParam("smile") String smile,
                          Principal principal){
        String accountEmail = principal.getName();
        Account account = accountsService.findByEmail(accountEmail);
        Picture picture = picturesService.findById(id);
        filesService.duo(picture, x, y, angle, smile, account);
        return "redirect:/images";
    }
}
