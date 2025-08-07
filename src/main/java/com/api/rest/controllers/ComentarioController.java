package com.api.rest.controllers;

import com.api.rest.dto.taskDto.ComentarioDTO;
import com.api.rest.service.ComentarioService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/tasks/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @PostMapping("/{taskId}/add")
    public ResponseEntity<?> adicionarComentarioComMidia(
            @PathVariable Long taskId,
            @RequestParam("usuarioId") Long usuarioId,
            @RequestParam("texto") String texto,
            @RequestPart(value = "midia", required = false) List<MultipartFile> midia
    ) {
        try {
            comentarioService.salvarComentario(taskId, usuarioId, texto, midia);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar comentário");
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<List<ComentarioDTO>> listarComentariosPorTask(@PathVariable Long taskId) {
        try {
            List<ComentarioDTO> comentarios = comentarioService.listarComentariosPorTask(taskId);
            return ResponseEntity.ok(comentarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            // Caminho onde os arquivos estão armazenados
            Path filePath = Paths.get("uploads").resolve(filename).normalize();

            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // Define o tipo MIME (opcional, pode usar application/octet-stream para forçar download genérico)
            String contentType = "application/octet-stream";

            // Cabeçalho Content-Disposition para forçar download com nome do arquivo correto
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
