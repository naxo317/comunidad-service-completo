package com.cine_teatro.comunidad.dto;
import com.cine_teatro.comunidad.model.Categoria;import jakarta.validation.constraints.*;
public record PublicacionRequest(@NotBlank String titulo,@NotBlank String contenido,@NotNull Categoria categoria,Long idContenido) {}
