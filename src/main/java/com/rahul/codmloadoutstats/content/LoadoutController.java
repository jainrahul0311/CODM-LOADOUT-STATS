package com.rahul.codmloadoutstats.content;

import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.util.Optional;

@Log4j2
@RestController
public class LoadoutController {

    private final LoadoutService loadoutService;

    public LoadoutController(LoadoutService loadoutService) {
        this.loadoutService = loadoutService;
    }

    @PostMapping("/loadout")
    public ResponseEntity uploadLoadOut(@RequestParam("file") MultipartFile multipartFile) {

        if (multipartFile == null) {
            throw new RuntimeException("File is Null !! Please ensure Multipart have 'file' attribute !!");
        }

        log.info("File Name : " + multipartFile.getOriginalFilename());
        log.info("File Size : " + multipartFile.getSize());
        log.info("File Type : " + multipartFile.getContentType());

        LoadoutDAO loadoutDAO = null;

        try {
            loadoutDAO = new LoadoutDAO(
                    0,
                    multipartFile.getBytes(),
                    multipartFile.getOriginalFilename(),
                    multipartFile.getContentType(),
                    multipartFile.getSize()
            );
        } catch (Exception e) {
            throw new RuntimeException("Unable to Create Loadout Object!!!");
        }

        loadoutService.save(loadoutDAO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(loadoutDAO.getLoadoutId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping(path = "/loadout/{lid}")
    public void getLoadOut(@PathVariable long lid, HttpServletResponse response) {

        Optional<LoadoutDAO> byId = loadoutService.findById(lid);
        if (byId.isEmpty())
            throw new RuntimeException("Unable to find LoadOut with id : " + lid);

        LoadoutDAO loadoutDAO = byId.get();

        response.addHeader("Content-Disposition", "attachment;filename="+loadoutDAO.getFileName());
        response.setContentType(loadoutDAO.getContentType());

        try{
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(loadoutDAO.getBlobData());
            outputStream.flush();
        }catch (Exception e){
            throw new RuntimeException("Failed to Return File");
        }

    }
}
