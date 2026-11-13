package com.cine_teatro.comunidad.repository;
import com.cine_teatro.comunidad.model.*;import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
public interface ReaccionRepository extends JpaRepository<Reaccion,Long> {Optional<Reaccion> findByIdUsuarioAndIdPublicacionAndTipo(String idUsuario,Long idPublicacion,TipoReaccion tipo); long countByIdPublicacion(Long idPublicacion);}
