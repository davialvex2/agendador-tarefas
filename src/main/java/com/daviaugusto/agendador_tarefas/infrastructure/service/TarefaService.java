package com.daviaugusto.agendador_tarefas.infrastructure.service;

import com.daviaugusto.agendador_tarefas.infrastructure.dtos.TarefaDTO;
import com.daviaugusto.agendador_tarefas.infrastructure.entity.Tarefa;
import com.daviaugusto.agendador_tarefas.infrastructure.enums.StatusTarefaEnum;
import com.daviaugusto.agendador_tarefas.infrastructure.mapper.TarefaConverter;
import com.daviaugusto.agendador_tarefas.infrastructure.repositories.TarefaRepository;
import com.daviaugusto.agendador_tarefas.infrastructure.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private TarefaConverter tarefaConverter;

    @Autowired
    private JwtUtil jwtUtil;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public TarefaDTO gravarTarefa(TarefaDTO tarefaDTO, String token){
        tarefaDTO.setDataCriacao(LocalDateTime.now());
        tarefaDTO.setStatusTarefaEnum(StatusTarefaEnum.PEDENTE);
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        tarefaDTO.setEmailUsuario(email);
        Tarefa tarefa = tarefaRepository.save(tarefaConverter.paraTarefaEntity(tarefaDTO));
        return tarefaConverter.paraTarefaDTO(tarefa);
    }

    public List<TarefaDTO> buscarTarefaPeriodo(LocalDateTime min, LocalDateTime max){
    return tarefaConverter.paraListaTarefaDTO((tarefaRepository.findByDataEventoBetween(min, max)));
    }

    public List<TarefaDTO> buscarTarefasPorEmail(String token){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        return tarefaConverter.paraListaTarefaDTO(tarefaRepository.findByEmailUsuario(email));
    }

    public void deletarTarefa(String id){
        tarefaRepository.deleteById(id);
    }



    public void atualizarTarefa(Tarefa tarefaAntigo, Tarefa tarefaNovo){
        tarefaAntigo.setNomeTarefa(tarefaNovo.getNomeTarefa() != null ? tarefaNovo.getNomeTarefa() : tarefaAntigo.getNomeTarefa());
        tarefaAntigo.setDescricao(tarefaNovo.getDescricao() != null ? tarefaNovo.getDescricao() : tarefaAntigo.getDescricao());
        tarefaAntigo.setDataCriacao(tarefaAntigo.getDataCriacao());
        tarefaAntigo.setId(tarefaAntigo.getId());
        tarefaAntigo.setStatusTarefaEnum(tarefaAntigo.getStatusTarefaEnum());
        tarefaAntigo.setEmailUsuario(tarefaAntigo.getEmailUsuario());
        tarefaAntigo.setDataEvento(tarefaNovo.getDataEvento() != null ? tarefaNovo.getDataEvento() : tarefaAntigo.getDataEvento());
        tarefaAntigo.setDataAlteracao(LocalDateTime.now());
    }

}
