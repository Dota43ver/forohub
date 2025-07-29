package com.forohub.api.infra.errores;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class TratadorDeErrores {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DatosErrorValidacion>> tratarError400(MethodArgumentNotValidException ex) {
        var errores = ex.getFieldErrors().stream()
                .map(DatosErrorValidacion::new)
                .toList();
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> tratarErrorDeNegocio(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    private record DatosErrorValidacion(String campo, String mensaje) {
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<String> tratarErrorDePermisos(SecurityException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}
