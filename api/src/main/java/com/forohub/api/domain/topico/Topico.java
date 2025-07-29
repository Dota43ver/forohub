package com.forohub.api.domain.topico;

import com.forohub.api.domain.autor.Autor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaDeCreacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private Curso curso;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Topico(DatosRegistroTopico datos, Autor autor) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechaDeCreacion = LocalDateTime.now(); // Se establece la fecha actual
        this.status = Status.ABIERTO; // Estado inicial por defecto
        this.autor = autor;
        this.curso = datos.curso();
    }

    public void actualizarDatos(DatosActualizarTopico datos) {
        if (datos.titulo() != null && !datos.titulo().isBlank()) {
            this.titulo = datos.titulo();
        }

        if (datos.mensaje() != null && !datos.mensaje().isBlank()) {
            this.mensaje = datos.mensaje();
        }
        if (datos.status() != null) {
            this.status = datos.status();
        }
    }
}
