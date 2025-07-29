package com.forohub.api.domain.autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DatosRegistroAutor(
        @NotBlank(message = "El login es obligatorio")
        String login,

        @NotBlank(message = "La clave es obligatoria")
        @Size(min = 6, message = "La clave debe tener al menos 6 caracteres")
        String clave
) {
}
