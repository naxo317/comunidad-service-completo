package com.cine_teatro.comunidad.repository;
import com.cine_teatro.comunidad.model.*;import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
public interface ComentarioRepository extends JpaRepository<Comentario,Long> {List<Comentario> findByIdPublicacionAndEstadoOrderByFechaCreacionAsc(Long idPublicacion,Estado estado); long countByIdPublicacionAndEstado(Long idPublicacion,Estado estado);}
