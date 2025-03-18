package com.daviaugusto.agendador_tarefas.infrastructure.mapper;

import ch.qos.logback.core.model.ComponentModel;
import com.daviaugusto.agendador_tarefas.infrastructure.dtos.TarefaDTO;
import com.daviaugusto.agendador_tarefas.infrastructure.entity.Tarefa;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefaConverter {

    @Mapping(source = "id", target = "id")
    Tarefa paraTarefaEntity(TarefaDTO tarefaDTO);

    TarefaDTO paraTarefaDTO(Tarefa terefa);

    List<TarefaDTO> paraListaTarefaDTO(List<Tarefa> tafs);

    List<Tarefa> paraListaTarefa(List<TarefaDTO> dtos);


}
