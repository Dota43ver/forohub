package com.forohub.api.domain.topico;
import jakarta.validation.constraints.NotBlank;

public record DatosActualizarTopico(String titulo,
                                    String mensaje,
                                    Status status) {
}
