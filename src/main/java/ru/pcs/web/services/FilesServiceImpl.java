package ru.pcs.web.services;


import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ru.pcs.web.models.Account;
import ru.pcs.web.models.ImageInfo;
import ru.pcs.web.models.Picture;
import ru.pcs.web.repositories.ImageInfoRepository;
import ru.pcs.web.repositories.PicturesRepository;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


@Service
@RequiredArgsConstructor
public class FilesServiceImpl implements FilesService {

    @Autowired
    private final ImageInfoRepository imageInfoRepository;

    @Autowired
    private final PicturesRepository picturesRepository;

    @Value("${images.url}")
    String imageUrl;

    @Value("${files.storage.path}")
    private String filesStoragePath;

    @Value("${duoImages.storage.path}")
    private String duoImagesPath;
    @Value("${goodmorning-sticker.path}")
    private String goodMorningStickerPath;
    @Value("${surprised-sticker.path}")
    private String surprisedStickerPath;
    @Value("${cloud-sticker.path}")
    private String cloudStickerPath;


    @Transactional
    @Override
    public void duo(Picture picture, int x, int y, int angle, String smile, Account account){
        String fileNameWithExtension = picture.getImageInfo().getName();
        String fileName = fileNameWithExtension.substring(0,fileNameWithExtension.lastIndexOf("."));
        ImageInfo existingFile = imageInfoRepository.findByName(fileName+".png");
        while(existingFile != null) {
            fileName = counter(fileName);
            existingFile = imageInfoRepository.findByName(fileName+".png");
        }
        String urlBaseImage = duoImagesPath + File.separator + fileName+".png";
        try {
            BufferedImage baseImage;
            if(!picture.getImageInfo().getDescription()) {
                baseImage = ImageIO.read(new File(filesStoragePath + File.separator + picture.getImageInfo().getName()));
            }
            else{
                baseImage = ImageIO.read(new File(duoImagesPath + File.separator + picture.getImageInfo().getName()));
            }
            BufferedImage overlayImage;
            switch (smile) {
                case "a":
                    overlayImage = ImageIO.read(new File(goodMorningStickerPath));
                    break;
                case "b":
                    overlayImage = ImageIO.read(new File(cloudStickerPath));
                    break;
                default:
                    overlayImage = ImageIO.read(new File(surprisedStickerPath));
                    break;
            }
            BufferedImage combinedImage = new BufferedImage(
                    baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = combinedImage.createGraphics();
            g2d.drawImage(baseImage, 0, 0, null);
            g2d.rotate(Math.toRadians(angle), x+overlayImage.getWidth() / 2, y+overlayImage.getHeight() / 2);
            g2d.drawImage(overlayImage, x, y, null);
            g2d.rotate(Math.toRadians(angle), x-overlayImage.getWidth()/2, y-overlayImage.getHeight()/2);
            g2d.dispose();
            File outputFile = new File(urlBaseImage);
            ImageIO.write(combinedImage, "png", outputFile);
            String newPictureName = counter(picture.getName());
            Optional<Picture> existingPicture = picturesRepository.findByName(newPictureName);
            while (!existingPicture.isEmpty()){
                newPictureName = counter(newPictureName);
                existingPicture = picturesRepository.findByName(newPictureName);
            }
            Picture newPicture = Picture.builder()
                    .name(newPictureName)
                    .descr(picture.getDescr())
                    .visibility(picture.getVisibility())
                    .account(account)
                    .build();
            picturesRepository.save(newPicture);

            ImageInfo imageInfo = ImageInfo.builder()
                    .originalName(picture.getImageInfo().getOriginalName())
                    .size(picture.getImageInfo().getSize())
                    .type(picture.getImageInfo().getType())
                    .height(picture.getImageInfo().getHeight())
                    .width(picture.getImageInfo().getWidth())
                    .name(fileName+".png")
                    .description(true)
                    .build();
            newPicture.setImageInfo(imageInfo);
            imageInfoRepository.save(imageInfo);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Boolean formatСheck(MultipartFile multipartFile){
        try {
            InputStream stream = multipartFile.getInputStream();
            Tika tika = new Tika();
            String mimeType = tika.detect(stream);
            if (!mimeType.startsWith("image/")) {
                return false;
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка при обработке файла");

        }
        return true;
    }
    @Transactional
    @Override
    public void upload(MultipartFile multipartFile,  Picture picture) {
        try {
            String extension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));

            String name = UUID.randomUUID() + extension;

            InputStream inputStream = multipartFile.getInputStream();
            BufferedImage image = ImageIO.read(inputStream);
            Long width = (long) image.getWidth();
            Long height = (long) image.getHeight();


            ImageInfo imageInfo = ImageInfo.builder()
                    .originalName(multipartFile.getOriginalFilename())
                    .size(multipartFile.getSize())
                    .type(multipartFile.getContentType())
                    .height(height)
                    .description(false)
                    .width(width)
                    .name(name)
                    .build();
            picture.setImageInfo(imageInfo);
            Files.copy(multipartFile.getInputStream(), Paths.get(filesStoragePath, imageInfo.getName()));
            imageInfoRepository.save(imageInfo);

        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }


    @Override
    public void addImageToResponse(String fileName, HttpServletResponse response) {
        ImageInfo imageInfo = imageInfoRepository.findByName(fileName);

        response.setContentType(imageInfo.getType());
        response.setHeader("Content-Disposition","filename=\"" + imageInfo.getName() + "\"");
        try {
            if(imageInfo.getDescription()){
                IOUtils.copy(new FileInputStream(duoImagesPath + File.separator + imageInfo.getName()), response.getOutputStream());
                response.flushBuffer();
            }
            else {
                IOUtils.copy(new FileInputStream(filesStoragePath + File.separator + imageInfo.getName()), response.getOutputStream());
                response.flushBuffer();
            }
        } catch(IOException e) {
            throw new IllegalArgumentException(e);
        }
        }

    public String counter(String str) {
        int len = str.length();
        if (Character.isDigit(str.charAt(len-1))) {
            int num = Integer.parseInt(str.substring(len-1)) + 1;
            return str.substring(0, len-1) + num;
        } else {
            return str + "1";
        }
    }
}
