package com.cine_teatro.comunidad.dto;import jakarta.validation.constraints.NotBlank;
public record ReporteRequest(Long idPublicacion,Long idComentario,@NotBlank String motivo) {}
