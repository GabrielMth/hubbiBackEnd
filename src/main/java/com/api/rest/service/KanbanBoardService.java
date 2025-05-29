package com.api.rest.service;


import com.api.rest.model.KanbanBoard;
import com.api.rest.repository.KanbanBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KanbanBoardService {

    @Autowired
    private KanbanBoardRepository kanbanBoardRepository;


    public KanbanBoard salvar (KanbanBoard kanbanBoard) {
        return kanbanBoardRepository.save(kanbanBoard);
    }

    public void deletarPorId(Long id) {
        kanbanBoardRepository.deleteById(id);
    }

    public Optional<KanbanBoard> buscarPorId(Long id) {
        return kanbanBoardRepository.findById(id);
    }
}