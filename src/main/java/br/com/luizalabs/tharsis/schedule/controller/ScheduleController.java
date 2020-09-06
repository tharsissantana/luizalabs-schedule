package br.com.luizalabs.tharsis.schedule.controller;

import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.luizalabs.tharsis.schedule.dto.ScheduleDTO;
import br.com.luizalabs.tharsis.schedule.model.Schedule;
import br.com.luizalabs.tharsis.schedule.model.StatusSchedule;
import br.com.luizalabs.tharsis.schedule.service.ScheduleService;
import io.swagger.annotations.ApiOperation;

@RestController
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation(value = "Cria um novo agendamento", response = Schedule.class, notes = "Esta operação cadastra um novo agendamento para envio da mensagem")
    @PostMapping(value = "/schedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Schedule create(@Valid @RequestBody ScheduleDTO scheduleDTO) {
        return scheduleService.create(scheduleDTO);
    }

    @ApiOperation(value = "Busca os agendamentos", response = Page.class, notes = "Esta operação lista os agendamentos criados podendo filtrar pelo status")
    @GetMapping(value = "/schedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Schedule> findSchedules(@RequestParam String destinatario, @RequestParam(required = false) StatusSchedule status,
            @RequestParam(required = false, defaultValue = "50") Integer limit, @RequestParam(required = false, defaultValue = "0") Integer page) {
        return scheduleService.findSchedules(destinatario, status, limit, page);
    }

    @ApiOperation(value = "Remove um agendamento", response = Schedule.class, notes = "Esta operação remove o agendamento pelo id informado")
    @DeleteMapping(value = "/schedule/{id}")
    public Map<String, Boolean> delete(@PathVariable UUID id) {
        return scheduleService.delete(id);
    }
}
