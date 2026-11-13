package com.cine_teatro.comunidad.model;
import jakarta.persistence.*;import java.time.Instant;
@Entity @Table(name="republicacion") public class Republicacion {@Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long idRepublicacion; @Column(nullable=false) public String idUsuario; @Column(nullable=false) public Long idPublicacionOriginal; public Instant fechaCreacion=Instant.now();}
