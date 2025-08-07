package com.api.rest.service;


import com.api.rest.dto.taskDto.ComentarioDTO;
import com.api.rest.model.Comentario;
import com.api.rest.model.ComentarioMidia;
import com.api.rest.model.Task;
import com.api.rest.model.Usuario;
import com.api.rest.repository.ComentarioRepository;
import com.api.rest.repository.TaskRepository;
import com.api.rest.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final TaskRepository taskRepository;
    private final UserRepository usuarioRepository;
    private final MidiaStorageService midiaStorageService;

    public ComentarioService(
            ComentarioRepository comentarioRepository,
            TaskRepository taskRepository,
            UserRepository usuarioRepository,
            MidiaStorageService midiaStorageService
    ) {
        this.comentarioRepository = comentarioRepository;
        this.taskRepository = taskRepository;
        this.usuarioRepository = usuarioRepository;
        this.midiaStorageService = midiaStorageService;
    }

    public void salvarComentario(Long taskId, Long usuarioId, String texto, List<MultipartFile> midias) throws IOException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task não encontrada"));

        Usuario autor = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Comentario comentario = new Comentario();
        comentario.setTexto(texto);
        comentario.setTask(task);
        comentario.setAutor(autor);
        comentario.setCriadoEm(Instant.now());

        if (midias != null && !midias.isEmpty()) {
            List<ComentarioMidia> listaMidias = new ArrayList<>();
            for (MultipartFile file : midias) {
                String url = midiaStorageService.salvar(file);
                ComentarioMidia cm = new ComentarioMidia();
                cm.setUrl(url);

                String contentType = file.getContentType();
                if (contentType != null && contentType.startsWith("image")) {
                    cm.setTipo("image");
                } else if (contentType != null && contentType.startsWith("video")) {
                    cm.setTipo("video");
                } else {
                    cm.setTipo("file");
                }

                cm.setComentario(comentario);
                listaMidias.add(cm);
            }
            comentario.setMidias(listaMidias);
        }

        comentarioRepository.save(comentario);
    }


    public List<ComentarioDTO> listarComentariosPorTask(Long taskId) {
        List<Comentario> comentarios = comentarioRepository.findByTaskId(taskId);
        return comentarios.stream()
                .map(ComentarioDTO::fromEntity) // seu DTO já adapta a entidade para JSON
                .collect(Collectors.toList());
    }

}
