package br.com.luizalabs.tharsis.schedule.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Schedule {

    @ApiModelProperty(value = "Código do agendamento")
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ApiModelProperty(value = "Data/Hora para envio")
    @Getter
    @Setter
    @Column(columnDefinition = "DATE")
    private Date data;

    @ApiModelProperty(value = "Destinatário (e-mail ou telefone) da mensagem")
    @Getter
    @Setter
    private String destinatario;

    @ApiModelProperty(value = "Tipo de envio da mensagem")
    @Getter
    @Setter
    private TypeMessage type;

    @ApiModelProperty(value = "Mensagem a ser enviada")
    @Getter
    @Setter
    private String mensagem;

    @ApiModelProperty(value = "Situação do agendamento da mensagem")
    @Getter
    @Setter
    private StatusSchedule status;
}
