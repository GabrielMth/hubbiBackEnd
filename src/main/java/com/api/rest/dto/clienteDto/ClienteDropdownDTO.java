package com.api.rest.dto.clienteDto;

public class ClienteDropdownDTO {
    private final Long id;
    private final String nome;
    private final String documento;
    private final Long kanbanBoardId;  // novo campo

    public ClienteDropdownDTO(Long id, String nome, String documento, Long kanbanBoardId) {
        this.id = id;
        this.nome = nome;
        this.documento = documento;
        this.kanbanBoardId = kanbanBoardId;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDocumento() { return documento; }
    public Long getKanbanBoardId() { return kanbanBoardId; }  // getter novo
}