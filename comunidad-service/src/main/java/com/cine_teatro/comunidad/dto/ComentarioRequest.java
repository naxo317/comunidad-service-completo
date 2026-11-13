package com.cine_teatro.comunidad.dto;import jakarta.validation.constraints.NotBlank;
public record ComentarioRequest(@NotBlank String texto) {}
