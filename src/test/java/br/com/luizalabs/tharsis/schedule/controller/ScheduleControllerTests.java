package br.com.luizalabs.tharsis.schedule.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.luizalabs.tharsis.schedule.dto.ScheduleDTO;
import br.com.luizalabs.tharsis.schedule.model.Schedule;
import br.com.luizalabs.tharsis.schedule.model.StatusSchedule;
import br.com.luizalabs.tharsis.schedule.model.TypeMessage;

@SpringBootTest
@AutoConfigureMockMvc
class ScheduleControllerTests {

    private static final String URL_BASE = "http://localhost:8080/schedule";

    @Autowired
    protected MockMvc mockMvc;

    protected String mapToJson(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, TypeReference<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        return new ObjectMapper().readValue(json, clazz);
    }

    @Test
    void createSchedule() throws JsonProcessingException, Exception {
        ScheduleDTO scheduleDTO = new ScheduleDTO(
                new Date(),
                "tharsis.ssantana@gmail.com",
                TypeMessage.EMAIL,
                "Teste de criação do agendamento");

        MvcResult mvcResult = mockMvc.perform(post(URL_BASE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(scheduleDTO)))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        Schedule schedule = mapFromJson(mvcResult.getResponse().getContentAsString(), new TypeReference<Schedule>() {
        });

        assertNotNull(schedule);
        assertNotNull(schedule.getId());
    }

    @Test
    void createInvalidData() throws JsonProcessingException, Exception {
        ScheduleDTO scheduleDTO = new ScheduleDTO(
                null,
                "tharsis.ssantana@gmail.com",
                TypeMessage.EMAIL,
                "Teste de criação do agendamento");

        MvcResult mvcResult = mockMvc.perform(post(URL_BASE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(scheduleDTO)))
                .andReturn();

        assertEquals(400, mvcResult.getResponse().getStatus());

        String content = mvcResult.getResponse().getContentAsString(Charset.forName("UTF-8")).replace("[", "").replace("]", "");

        Map<String, String> result = mapFromJson(content, new TypeReference<Map<String, String>>() {
        });

        assertEquals("data", result.get("field"));
        assertEquals("Data não informada", result.get("message"));
    }

    @Test
    void createInvalidEmail() throws JsonProcessingException, Exception {
        ScheduleDTO scheduleDTO = new ScheduleDTO(
                new Date(),
                "tharsis.ssantana@gmail",
                TypeMessage.EMAIL,
                "Teste de criação do agendamento");

        MvcResult mvcResult = mockMvc.perform(post(URL_BASE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(scheduleDTO))).andReturn();

        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    void getSchedule() throws JsonProcessingException, Exception {
        ScheduleDTO scheduleDTO = new ScheduleDTO(
                new Date(),
                "luizalabs@luizalabs.com.br",
                TypeMessage.EMAIL,
                "Teste de criação do agendamento");

        MvcResult mvcResult = mockMvc.perform(post(URL_BASE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(scheduleDTO)))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        mvcResult = mockMvc.perform(get(URL_BASE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("destinatario", "luizalabs@luizalabs.com.br"))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        String content = mvcResult.getResponse().getContentAsString(Charset.forName("UTF-8"));
        content = content.substring(content.indexOf("[", content.indexOf("]" + 1)));

        List<Schedule> result = mapFromJson(content, new TypeReference<List<Schedule>>() {
        });

        assertEquals(true, !result.isEmpty());

        mvcResult = mockMvc.perform(get(URL_BASE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("destinatario", "luizalabs@luizalabs.com.br")
                .param("status", StatusSchedule.SENT.toString()))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        content = mvcResult.getResponse().getContentAsString(Charset.forName("UTF-8"));
        content = content.substring(content.indexOf("[", content.indexOf("]" + 1)));

        result = mapFromJson(content, new TypeReference<List<Schedule>>() {
        });

        assertEquals(true, result.isEmpty());
    }

    @Test
    void deleteSchedule() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete(URL_BASE + "/32542cb1-5187-41d5-845f-8afee8e0d462")).andReturn();

        assertEquals(404, mvcResult.getResponse().getStatus());

        ScheduleDTO scheduleDTO = new ScheduleDTO(
                new Date(),
                "tharsis.ssantana@gmail.com",
                TypeMessage.EMAIL,
                "Teste de criação do agendamento");

        mvcResult = mockMvc.perform(post(URL_BASE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(scheduleDTO)))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        Schedule schedule = mapFromJson(mvcResult.getResponse().getContentAsString(), new TypeReference<Schedule>() {
        });

        mvcResult = mockMvc.perform(delete(URL_BASE + "/" + schedule.getId())).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
    }
}
