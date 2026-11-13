package com.cine_teatro.comunidad.model;
import jakarta.persistence.*;import java.time.Instant;
@Entity @Table(name="reporte") public class Reporte {@Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long idReporte; @Column(nullable=false) public String idUsuario; public Long idPublicacion; public Long idComentario; @Column(nullable=false,length=2000) public String motivo; @Enumerated(EnumType.STRING) public EstadoReporte estado=EstadoReporte.PENDIENTE; public Instant fechaCreacion=Instant.now();}
