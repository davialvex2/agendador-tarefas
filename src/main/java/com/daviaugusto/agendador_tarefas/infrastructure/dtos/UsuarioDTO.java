package com.daviaugusto.agendador_tarefas.infrastructure.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {


    private String email;
    private String senha;
}