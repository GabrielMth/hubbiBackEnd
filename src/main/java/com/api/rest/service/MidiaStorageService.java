package com.api.rest.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class MidiaStorageService {

    private final Path pastaUploads = Paths.get("uploads");

    public String salvar(MultipartFile file) throws IOException {
        if (!Files.exists(pastaUploads)) {
            Files.createDirectories(pastaUploads);
        }

        String nomeArquivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path destino = pastaUploads.resolve(nomeArquivo);

        Files.copy(file.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/" + nomeArquivo;
    }
}
