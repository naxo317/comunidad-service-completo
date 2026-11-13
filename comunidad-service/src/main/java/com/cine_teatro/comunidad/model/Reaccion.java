package com.cine_teatro.comunidad.model;
import jakarta.persistence.*;import java.time.Instant;
@Entity @Table(name="reaccion") public class Reaccion {@Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long idReaccion; @Column(nullable=false) public String idUsuario; public Long idPublicacion; public Long idComentario; @Enumerated(EnumType.STRING) public TipoReaccion tipo=TipoReaccion.LIKE; public Instant fechaCreacion=Instant.now();}
