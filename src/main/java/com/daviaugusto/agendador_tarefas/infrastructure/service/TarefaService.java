package com.daviaugusto.agendador_tarefas.infrastructure.service;

import com.daviaugusto.agendador_tarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.daviaugusto.agendador_tarefas.infrastructure.dtos.TarefaDTO;
import com.daviaugusto.agendador_tarefas.infrastructure.entity.Tarefa;
import com.daviaugusto.agendador_tarefas.infrastructure.enums.StatusTarefaEnum;
import com.daviaugusto.agendador_tarefas.infrastructure.mapper.TarefaConverter;
import com.daviaugusto.agendador_tarefas.infrastructure.mapper.UpdateTarefa;
import com.daviaugusto.agendador_tarefas.infrastructure.repositories.TarefaRepository;
import com.daviaugusto.agendador_tarefas.infrastructure.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private TarefaConverter tarefaConverter;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UpdateTarefa updateTarefa;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public TarefaDTO gravarTarefa(TarefaDTO tarefaDTO, String token) {
        tarefaDTO.setDataCriacao(LocalDateTime.now());
        tarefaDTO.setStatusTarefaEnum(StatusTarefaEnum.PEDENTE);
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        tarefaDTO.setEmailUsuario(email);
        Tarefa tarefa = tarefaRepository.save(tarefaConverter.paraTarefaEntity(tarefaDTO));
        return tarefaConverter.paraTarefaDTO(tarefa);
    }

    public List<TarefaDTO> buscarTarefaPeriodo(LocalDateTime min, LocalDateTime max) {
        return tarefaConverter.paraListaTarefaDTO((tarefaRepository.findByDataEventoBetween(min, max)));
    }

    public List<TarefaDTO> buscarTarefasPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        return tarefaConverter.paraListaTarefaDTO(tarefaRepository.findByEmailUsuario(email));
    }

    public void deletarTarefa(String id) {
        try {
            tarefaRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ID não encontrado" + e.getCause());
        }
    }

    public TarefaDTO atualizarStatus(String id, StatusTarefaEnum status) {
        Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID não encontrado"));
        tarefa.setStatusTarefaEnum(status);
        return tarefaConverter.paraTarefaDTO(tarefaRepository.save(tarefa));
    }


    public TarefaDTO atualizarTarefa(String id, TarefaDTO dto) {
        try {
            Tarefa tarefa = tarefaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));
            updateTarefa.updateTarefa(dto, tarefa);
            return tarefaConverter.paraTarefaDTO(tarefaRepository.save(tarefa));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("ID não encontrado" + e.getCause());
        }
    }

}
