package com.cine_teatro.comunidad.repository;
import com.cine_teatro.comunidad.model.*;import org.springframework.data.jpa.repository.JpaRepository;import java.util.*;
public interface RepublicacionRepository extends JpaRepository<Republicacion,Long> {Optional<Republicacion> findByIdUsuarioAndIdPublicacionOriginal(String idUsuario,Long idPublicacionOriginal); long countByIdPublicacionOriginal(Long idPublicacionOriginal);}
