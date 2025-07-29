package com.forohub.api.controller;

import com.forohub.api.domain.autor.Autor;
import com.forohub.api.domain.autor.AutorRepository;
import com.forohub.api.domain.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private AutorRepository autorRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datosRegistro, UriComponentsBuilder uriBuilder) {
        if (topicoRepository.existsByTituloAndMensaje(datosRegistro.titulo(), datosRegistro.mensaje())) {
            throw new IllegalArgumentException("Ya existe un tópico con el mismo título y mensaje.");
        }

        Autor autor = autorRepository.findById(datosRegistro.idAutor())
                .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado con el ID proporcionado."));


        Topico topico = new Topico(datosRegistro, autor);
        topicoRepository.save(topico);
        URI url = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosRespuestaTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosRespuestaTopico>> listarTopicos(@PageableDefault(size = 10, sort = "fechaDeCreacion") Pageable paginacion) {
        Page<Topico> topicosPaginados = topicoRepository.findAll(paginacion);
        Page<DatosRespuestaTopico> respuesta = topicosPaginados.map(DatosRespuestaTopico::new);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> detallarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);

        var datosTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getStatus(),
                topico.getAutor().getLogin(),
                topico.getCurso()
        );

        return ResponseEntity.ok(datosTopico);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@PathVariable Long id, @RequestBody DatosActualizarTopico datos) {
        Topico topico = topicoRepository.getReferenceById(id);

        var autorAutenticado = (Autor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!topico.getAutor().getId().equals(autorAutenticado.getId())) {
            throw new SecurityException("No tiene permisos para modificar este tópico.");
        }

        if (datos.titulo() != null && datos.mensaje() != null) {
            Optional<Topico> topicoExistente = topicoRepository.findByTituloAndMensaje(datos.titulo(), datos.mensaje());
            if (topicoExistente.isPresent() && !topicoExistente.get().getId().equals(id)) {
                throw new IllegalArgumentException("Ya existe un tópico con este título y mensaje.");
            }
        }

        topico.actualizarDatos(datos);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado con el ID: " + id));


        var autorAutenticado = (Autor) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!topico.getAutor().getId().equals(autorAutenticado.getId())) {
            throw new SecurityException("No tiene permisos para eliminar este tópico.");
        }
        topicoRepository.delete(topico);
        return ResponseEntity.noContent().build();
    }
}
