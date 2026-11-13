package com.cine_teatro.comunidad.repository;
import com.cine_teatro.comunidad.model.*;import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
public interface PublicacionRepository extends JpaRepository<Publicacion,Long> {List<Publicacion> findByEstadoOrderByFechaCreacionDesc(Estado estado);}
