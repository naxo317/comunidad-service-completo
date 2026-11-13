package com.cine_teatro.comunidad.controller;
import com.cine_teatro.comunidad.dto.*;import com.cine_teatro.comunidad.model.*;import com.cine_teatro.comunidad.service.ComunidadService;import com.cine_teatro.comunidad.exception.ApiException;
import jakarta.validation.Valid;import org.springframework.web.bind.annotation.*;import org.springframework.http.*;import java.util.*;
@RestController @RequestMapping("/api/v1/comunidad") public class ComunidadController {
private final ComunidadService service;public ComunidadController(ComunidadService service){this.service=service;}
private String user(String id){if(id==null||id.isBlank())throw new ApiException(HttpStatus.UNAUTHORIZED,"Falta identidad del gateway (X-User-Id)");return id;}
private void admin(String role){if(!"ADMIN".equals(role))throw new ApiException(HttpStatus.FORBIDDEN,"Se requiere ADMIN");}
@GetMapping("/publicaciones") public List<Publicacion> listar(){return service.listar();}
@GetMapping("/publicaciones/{id}") public Publicacion obtener(@PathVariable Long id){return service.obtener(id);}
@PostMapping("/publicaciones") @ResponseStatus(HttpStatus.CREATED) public Publicacion crear(@Valid @RequestBody PublicacionRequest r,@RequestHeader(value="X-User-Id",required=false) String id){return service.crear(r,user(id));}
@DeleteMapping("/publicaciones/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void eliminar(@PathVariable Long id,@RequestHeader(value="X-User-Id",required=false) String user,@RequestHeader(value="X-User-Role",required=false) String role){service.eliminar(id,user(user),"ADMIN".equals(role));}
@GetMapping("/publicaciones/{id}/comentarios") public List<Comentario> comentarios(@PathVariable Long id){return service.comentarios(id);}
@PostMapping("/publicaciones/{id}/comentarios") @ResponseStatus(HttpStatus.CREATED) public Comentario comentar(@PathVariable Long id,@Valid @RequestBody ComentarioRequest r,@RequestHeader(value="X-User-Id",required=false) String idUsuario){return service.comentar(id,r,user(idUsuario));}
@PostMapping("/comentarios/{id}/respuestas") @ResponseStatus(HttpStatus.CREATED) public Comentario responder(@PathVariable Long id,@Valid @RequestBody ComentarioRequest r,@RequestHeader(value="X-User-Id",required=false) String usuario){return service.responder(id,r,user(usuario));}
@PostMapping("/publicaciones/{id}/likes") @ResponseStatus(HttpStatus.CREATED) public void like(@PathVariable Long id,@RequestHeader(value="X-User-Id",required=false) String usuario){service.like(id,user(usuario));}
@DeleteMapping("/publicaciones/{id}/likes") @ResponseStatus(HttpStatus.NO_CONTENT) public void unlike(@PathVariable Long id,@RequestHeader(value="X-User-Id",required=false) String usuario){service.quitarLike(id,user(usuario));}
@PostMapping("/publicaciones/{id}/republicaciones") @ResponseStatus(HttpStatus.CREATED) public void republicar(@PathVariable Long id,@RequestHeader(value="X-User-Id",required=false) String usuario){service.republicar(id,user(usuario));}
@DeleteMapping("/publicaciones/{id}/republicaciones") @ResponseStatus(HttpStatus.NO_CONTENT) public void quitarRepublicacion(@PathVariable Long id,@RequestHeader(value="X-User-Id",required=false) String usuario){service.quitarRepublicacion(id,user(usuario));}
@PostMapping("/reportes") @ResponseStatus(HttpStatus.CREATED) public Reporte reporte(@Valid @RequestBody ReporteRequest r,@RequestHeader(value="X-User-Id",required=false) String usuario){return service.reportar(r,user(usuario));}
@PatchMapping("/publicaciones/{id}/estado") public Publicacion estadoPublicacion(@PathVariable Long id,@Valid @RequestBody EstadoRequest r,@RequestHeader(value="X-User-Role",required=false) String role){admin(role);return service.estadoPublicacion(id,r);}
@PatchMapping("/comentarios/{id}/estado") public Comentario estadoComentario(@PathVariable Long id,@Valid @RequestBody EstadoRequest r,@RequestHeader(value="X-User-Role",required=false) String role){admin(role);return service.estadoComentario(id,r);}
@GetMapping("/publicaciones/{id}/contadores") public Map<String,Long> contadores(@PathVariable Long id){return service.contadores(id);}
}
