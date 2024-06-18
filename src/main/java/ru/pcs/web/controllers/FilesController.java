package ru.pcs.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pcs.web.dto.AddPictureForm;
import ru.pcs.web.dto.ImageLinkDto;
import ru.pcs.web.models.Account;
import ru.pcs.web.repositories.AccountsRepository;
import ru.pcs.web.services.FilesService;
import ru.pcs.web.services.PicturesService;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
@RequestMapping("/files")
public class FilesController {

    @Autowired
    private final FilesService filesService;


    @GetMapping("/{file-name:.+}")
    @ResponseBody
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response){
        filesService.addImageToResponse(fileName, response);
    }
}
