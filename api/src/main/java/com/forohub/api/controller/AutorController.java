package com.forohub.api.controller;

import com.forohub.api.domain.autor.Autor;
import com.forohub.api.domain.autor.AutorRepository;
import com.forohub.api.domain.autor.DatosRegistroAutor;
import com.forohub.api.domain.autor.DatosRespuestaAutor;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaAutor> registrarAutor(@RequestBody @Valid DatosRegistroAutor datos, UriComponentsBuilder uriBuilder) {
        if (autorRepository.existsByLogin(datos.login())) {
            throw new IllegalArgumentException("Este login ya está en uso.");
        }

        String claveEncriptada = passwordEncoder.encode(datos.clave());


        Autor autor = new Autor(null, datos.login(), claveEncriptada);
        autorRepository.save(autor);
        URI url = uriBuilder.path("/autores/{id}").buildAndExpand(autor.getId()).toUri();
        return ResponseEntity.created(url).body(new DatosRespuestaAutor(autor));
    }
}
