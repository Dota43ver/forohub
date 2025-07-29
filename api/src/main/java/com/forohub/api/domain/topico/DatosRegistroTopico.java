package com.forohub.api.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(@NotBlank(message = "El título es obligatorio")
                                  String titulo,

                                  @NotBlank(message = "El mensaje es obligatorio")
                                  String mensaje,

                                  @NotNull(message = "El ID del autor es obligatorio")
                                  Long idAutor,

                                  @NotNull(message = "El curso es obligatorio")
                                  Curso curso) {
}
