package com.daviaugusto.agendador_tarefas.infrastructure.controller;

import com.daviaugusto.agendador_tarefas.infrastructure.dtos.TarefaDTO;
import com.daviaugusto.agendador_tarefas.infrastructure.enums.StatusTarefaEnum;
import com.daviaugusto.agendador_tarefas.infrastructure.service.TarefaService;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    @Autowired
    public TarefaService tarefaServive;

    @PostMapping
    public ResponseEntity<TarefaDTO> gravarTarefa(@RequestBody TarefaDTO tarefaDTO,
                                                  @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(tarefaServive.gravarTarefa(tarefaDTO, token));
    }

    @GetMapping("/eventos")
    public ResponseEntity<List<TarefaDTO>> buscarPorPeriodo(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataMin,
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataMax){
        return ResponseEntity.ok(tarefaServive.buscarTarefaPeriodo(dataMin, dataMax));
    }

    @GetMapping
    public ResponseEntity<List<TarefaDTO>> buscarPorEmail(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(tarefaServive.buscarTarefasPorEmail(token));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable String id){
        tarefaServive.deletarTarefa(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<TarefaDTO> atualizarStatus(@RequestParam("id") String id, @RequestParam("status")StatusTarefaEnum status){
        return ResponseEntity.ok(tarefaServive.atualizarStatus(id,status));
    }

    @PutMapping
    public ResponseEntity<TarefaDTO> atualizarTarefa(@RequestParam("id") String id, @RequestBody TarefaDTO tarefaDTO){
        return ResponseEntity.ok(tarefaServive.atualizarTarefa(id, tarefaDTO));
    }


}
