package com.forohub.api.domain.autor;

public record DatosRespuestaAutor(
        Long id,
        String login
) {
    public DatosRespuestaAutor(Autor autor) {
        this(autor.getId(), autor.getUsername());
    }
}
