package br.com.luizalabs.tharsis.schedule.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.luizalabs.tharsis.schedule.dao.ScheduleRepository;
import br.com.luizalabs.tharsis.schedule.dto.ScheduleDTO;
import br.com.luizalabs.tharsis.schedule.exception.InvalidValueException;
import br.com.luizalabs.tharsis.schedule.exception.NotFoundException;
import br.com.luizalabs.tharsis.schedule.model.Schedule;
import br.com.luizalabs.tharsis.schedule.model.StatusSchedule;
import br.com.luizalabs.tharsis.schedule.model.TypeMessage;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule create(ScheduleDTO scheduleDTO) {
        validateEmail(scheduleDTO);

        return scheduleRepository.save(toModel(scheduleDTO));
    }

    private void validateEmail(ScheduleDTO scheduleDTO) {
        if (TypeMessage.EMAIL.equals(scheduleDTO.getType()) && !scheduleDTO.getDestinatario().matches("^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$")) {
            throw new InvalidValueException("E-mail inválido");
        }
    }

    private Schedule toModel(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setData(scheduleDTO.getData());
        schedule.setDestinatario(scheduleDTO.getDestinatario());
        schedule.setType(scheduleDTO.getType());
        schedule.setMensagem(scheduleDTO.getMensagem());
        schedule.setStatus(StatusSchedule.PENDING);

        return schedule;
    }

    public Page<Schedule> findSchedules(String destinatario, StatusSchedule status, Integer limit, Integer page) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("data").descending());
        if (status == null) {
            return scheduleRepository.findAllByDestinatario(destinatario, pageable);
        }

        return scheduleRepository.findAllByDestinatarioStatus(destinatario, status, pageable);
    }

    public Map<String, Boolean> delete(UUID id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new NotFoundException("Schedule not found on :: " + id));

        scheduleRepository.delete(schedule);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
