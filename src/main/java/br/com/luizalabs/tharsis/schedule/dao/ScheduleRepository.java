package br.com.luizalabs.tharsis.schedule.dao;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.luizalabs.tharsis.schedule.model.Schedule;
import br.com.luizalabs.tharsis.schedule.model.StatusSchedule;

public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, UUID> {

    @Query("SELECT v FROM br.com.luizalabs.tharsis.schedule.model.Schedule v WHERE upper(destinatario) = upper(:destinatario) and STATUS = :status")
    Page<Schedule> findAllByDestinatarioStatus(String destinatario, StatusSchedule status, Pageable pageable);

    Page<Schedule> findAllByDestinatario(String destinatario, Pageable pageable);
}
