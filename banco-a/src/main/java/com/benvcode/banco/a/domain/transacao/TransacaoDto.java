package com.benvcode.banco.a.domain.transacao;

import java.io.Serializable;

import com.benvcode.banco.a.domain.conta.Conta;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransacaoDto {
    private Long id;
    private LocalDateTime data;
    private BigDecimal montante;
    private String ibanDestino;
    private Conta conta;
}