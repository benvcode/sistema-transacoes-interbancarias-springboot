package com.benvcode.shared.module.doamin.transacao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Define o contrato de API para transações interbancárias.
 *
 * Este DTO é utilizado para a comunicação entre microserviços, como os "Banco A", "Banco B" e
 * o "Banco Intermediário". O "Banco Intermediário" é responsável por definir este contrato,
 * garantindo que os microserviços sigam o mesmo padrão de nomenclatura dos campos para
 * garantir uma desserialização automática e correta dos dados JSON.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TransacaoApiContractDto implements Serializable {
    // 'Serializable' indica que os objectos desta class podem ser serializados(ou seja, convertidos
    // para outros tipos de formatos como JSON, XML)
    // Usando anotações Jackson para controlar a desserialização
    @JsonProperty("montante")
    private BigDecimal montante;

    @JsonProperty("ibanDestino")
    private String ibanDestino;

    @JsonProperty("codigoBancoDestino")
    private String codigoBancoDestino;
}