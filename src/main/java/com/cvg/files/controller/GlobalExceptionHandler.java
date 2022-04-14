package com.cvg.files.controller;

import com.cvg.files.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ResponseDto> handleMultipart(MultipartException e){
        return new ResponseEntity<ResponseDto>(new ResponseDto("Asegúrese de subir un archivo válido..."), HttpStatus.BAD_REQUEST);
    }
}
