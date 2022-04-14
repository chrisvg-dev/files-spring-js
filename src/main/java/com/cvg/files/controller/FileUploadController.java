package com.cvg.files.controller;

import com.cvg.files.dto.FormDto;
import com.cvg.files.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("files")
public class FileUploadController {

    @PostMapping("/upload")
    public ResponseEntity<ResponseDto> uploadFile(@ModelAttribute FormDto form) throws IOException {
        /**
         * AQUI SE VALIDA SI EL USUARIO ENVIÓ UN ARCHIVO EN LA PETICION
         */
        if (form.getFile() == null || form.getFile().isEmpty()) {
            return new ResponseEntity<ResponseDto>(new ResponseDto("Se requiere un archivo..."), HttpStatus.BAD_REQUEST);
        }

        int indexExtension = form.getFile().getOriginalFilename().lastIndexOf(".");
        int lenFileName = form.getFile().getOriginalFilename().length();
        String extension = form.getFile().getOriginalFilename().substring(indexExtension+1, lenFileName);

        if (!extension.equalsIgnoreCase("PDF")) {
            return new ResponseEntity<ResponseDto>(new ResponseDto("No se acepta este formato..."), HttpStatus.BAD_REQUEST);
        }

        StringBuilder builder = new StringBuilder();
        String home = System.getProperty("user.home").toString();
        String uploadDate = LocalDate.now().toString();
        String baseName = "FILE";

        builder.append(home).append(File.separator)
                .append("Documents").append(File.separator)
                .append(uploadDate).append("-")
                .append(baseName).append(".").append(extension);

        byte[] fileBytes = form.getFile().getBytes();
        Path path = Paths.get( builder.toString() );
        Files.write(path,fileBytes);
        return new ResponseEntity<ResponseDto>(new ResponseDto("El archivo "+ form.getDescripcion() +" se subió correctamente..."), HttpStatus.OK);
    }
}
