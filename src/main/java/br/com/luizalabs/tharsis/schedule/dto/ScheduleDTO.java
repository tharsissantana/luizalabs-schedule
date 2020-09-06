package br.com.luizalabs.tharsis.schedule.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.luizalabs.tharsis.schedule.model.TypeMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {

    @ApiModelProperty(value = "Data/Hora para envio")
    @Getter
    @Setter
    @NotNull(message = "Data não informada")
    private Date data;

    @ApiModelProperty(value = "Destinatário (e-mail ou telefone) da mensagem")
    @Getter
    @Setter
    @NotBlank(message = "Destinatário não informado")
    private String destinatario;

    @ApiModelProperty(value = "Tipo de envio da mensagem")
    @Getter
    @Setter
    @NotNull(message = "Tipo de envio não informado")
    private TypeMessage type;

    @ApiModelProperty(value = "Mensagem a ser enviada")
    @Getter
    @Setter
    private String mensagem;

}
