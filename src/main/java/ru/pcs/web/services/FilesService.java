package ru.pcs.web.services;


import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ru.pcs.web.dto.ImageLinkDto;
import ru.pcs.web.models.Account;
import ru.pcs.web.models.Picture;

import javax.servlet.http.HttpServletResponse;

public interface FilesService {

    public Boolean formatСheck( MultipartFile multipartFile);

    void upload(MultipartFile multipartFile,  Picture picture);

    void addImageToResponse(String fileName, HttpServletResponse response);

    void duo(Picture picture, int x, int y, int angle, String smile, Account account);
    }
