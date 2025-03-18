package com.daviaugusto.agendador_tarefas.infrastructure.repositories;

import com.daviaugusto.agendador_tarefas.infrastructure.entity.Tarefa;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TarefaRepository extends MongoRepository<Tarefa, String> {

    List<Tarefa> findByDataEventoBetween(LocalDateTime dataMin, LocalDateTime dataMax);

    List<Tarefa> findByEmailUsuario(String email);
}
