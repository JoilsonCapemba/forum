package br.com.alura.forum.infra.exceptions;

import br.com.alura.forum.infra.exceptions.dto.FormErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class HendlerValidationsErrorException {

    @Autowired
    MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FormErrorDTO> handlerErrorExceptions(MethodArgumentNotValidException exception){
        List<FormErrorDTO> formErrorDTOS = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(error ->{
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            formErrorDTOS.add(new FormErrorDTO(error.getField(), message));
        });
        return formErrorDTOS;
    }
}
