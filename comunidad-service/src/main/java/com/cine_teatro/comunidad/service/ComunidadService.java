package com.cine_teatro.comunidad.service;
import com.cine_teatro.comunidad.dto.*;import com.cine_teatro.comunidad.model.*;import com.cine_teatro.comunidad.repository.*;import com.cine_teatro.comunidad.exception.ApiException;
import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import org.springframework.http.HttpStatus;import java.time.Instant;import java.util.*;
@Service @Transactional public class ComunidadService {
private final PublicacionRepository publicaciones;private final ComentarioRepository comentarios;private final ReaccionRepository reacciones;private final RepublicacionRepository republicaciones;private final ReporteRepository reportes;
public ComunidadService(PublicacionRepository p,ComentarioRepository c,ReaccionRepository r,RepublicacionRepository rp,ReporteRepository re){publicaciones=p;comentarios=c;reacciones=r;republicaciones=rp;reportes=re;}
private ApiException error(HttpStatus s,String m){return new ApiException(s,m);}
public Publicacion obtener(Long id){return publicaciones.findById(id).filter(p->p.estado!=Estado.ELIMINADO).orElseThrow(()->error(HttpStatus.NOT_FOUND,"Publicacion no encontrada"));}
public Comentario comentario(Long id){return comentarios.findById(id).filter(c->c.estado!=Estado.ELIMINADO).orElseThrow(()->error(HttpStatus.NOT_FOUND,"Comentario no encontrado"));}
public List<Publicacion> listar(){return publicaciones.findByEstadoOrderByFechaCreacionDesc(Estado.VISIBLE);}
public Publicacion crear(PublicacionRequest r,String usuario){Publicacion p=new Publicacion();p.idUsuario=usuario;p.titulo=r.titulo();p.contenido=r.contenido();p.categoria=r.categoria();p.idContenido=r.idContenido();return publicaciones.save(p);}
public void eliminar(Long id,String usuario,boolean admin){Publicacion p=obtener(id);autor(p.idUsuario,usuario,admin);p.estado=Estado.ELIMINADO;p.fechaActualizacion=Instant.now();}
public List<Comentario> comentarios(Long id){obtener(id);return comentarios.findByIdPublicacionAndEstadoOrderByFechaCreacionAsc(id,Estado.VISIBLE);}
public Comentario comentar(Long id,ComentarioRequest r,String usuario){obtener(id);Comentario c=new Comentario();c.idPublicacion=id;c.idUsuario=usuario;c.texto=r.texto();return comentarios.save(c);}
public Comentario responder(Long id,ComentarioRequest r,String usuario){Comentario padre=comentario(id);int depth=0;Comentario current=padre;while(current.idComentarioPadre!=null){depth++;if(depth>=2)throw error(HttpStatus.BAD_REQUEST,"Maximo 3 niveles de comentarios");current=comentario(current.idComentarioPadre);}Comentario c=comentar(padre.idPublicacion,r,usuario);c.idComentarioPadre=id;return comentarios.save(c);}
public void like(Long id,String usuario){obtener(id);if(reacciones.findByIdUsuarioAndIdPublicacionAndTipo(usuario,id,TipoReaccion.LIKE).isPresent())throw error(HttpStatus.CONFLICT,"Ya existe el like");Reaccion r=new Reaccion();r.idPublicacion=id;r.idUsuario=usuario;reacciones.save(r);}
public void quitarLike(Long id,String usuario){Reaccion r=reacciones.findByIdUsuarioAndIdPublicacionAndTipo(usuario,id,TipoReaccion.LIKE).orElseThrow(()->error(HttpStatus.NOT_FOUND,"Like no encontrado"));reacciones.delete(r);}
public void republicar(Long id,String usuario){obtener(id);if(republicaciones.findByIdUsuarioAndIdPublicacionOriginal(usuario,id).isPresent())throw error(HttpStatus.CONFLICT,"Ya republicaste");Republicacion r=new Republicacion();r.idUsuario=usuario;r.idPublicacionOriginal=id;republicaciones.save(r);}
public void quitarRepublicacion(Long id,String usuario){Republicacion r=republicaciones.findByIdUsuarioAndIdPublicacionOriginal(usuario,id).orElseThrow(()->error(HttpStatus.NOT_FOUND,"Republicacion no encontrada"));republicaciones.delete(r);}
public Reporte reportar(ReporteRequest r,String usuario){if((r.idPublicacion()==null)==(r.idComentario()==null))throw error(HttpStatus.BAD_REQUEST,"Indicar publicacion o comentario, no ambos");if(r.idPublicacion()!=null)obtener(r.idPublicacion());else comentario(r.idComentario());Reporte reporte=new Reporte();reporte.idUsuario=usuario;reporte.idPublicacion=r.idPublicacion();reporte.idComentario=r.idComentario();reporte.motivo=r.motivo();return reportes.save(reporte);}
public Publicacion estadoPublicacion(Long id,EstadoRequest r){Publicacion p=obtener(id);p.estado=r.estado();p.fechaActualizacion=Instant.now();return p;}
public Comentario estadoComentario(Long id,EstadoRequest r){Comentario c=comentario(id);c.estado=r.estado();return c;}
public void autor(String autor,String usuario,boolean admin){if(!admin&&!autor.equals(usuario))throw error(HttpStatus.FORBIDDEN,"Solo el autor o administrador");}
public Map<String,Long> contadores(Long id){obtener(id);return Map.of("likes",reacciones.countByIdPublicacion(id),"comentarios",comentarios.countByIdPublicacionAndEstado(id,Estado.VISIBLE),"republicaciones",republicaciones.countByIdPublicacionOriginal(id));}
}
