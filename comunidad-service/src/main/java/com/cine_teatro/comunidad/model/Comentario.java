package com.cine_teatro.comunidad.model;
import jakarta.persistence.*;import java.time.Instant;
@Entity @Table(name="comentario") public class Comentario {@Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long idComentario; @Column(nullable=false) public Long idPublicacion; public Long idComentarioPadre; @Column(nullable=false) public String idUsuario; @Column(nullable=false,length=5000) public String texto; public Instant fechaCreacion=Instant.now(); @Enumerated(EnumType.STRING) public Estado estado=Estado.VISIBLE;}
