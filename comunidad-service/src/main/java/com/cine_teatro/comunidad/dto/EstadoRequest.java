package com.cine_teatro.comunidad.dto;import com.cine_teatro.comunidad.model.Estado;import jakarta.validation.constraints.NotNull;
public record EstadoRequest(@NotNull Estado estado) {}
