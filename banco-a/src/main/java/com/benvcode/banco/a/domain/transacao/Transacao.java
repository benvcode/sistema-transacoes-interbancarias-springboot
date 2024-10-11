package com.benvcode.banco.a.domain.transacao;

import com.benvcode.banco.a.domain.conta.Conta;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transacao")
public class Transacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @Column(name = "data")
    private LocalDateTime data;

    @Column(name = "montante", nullable = false)
    private BigDecimal montante;

    @Column(name = "iban_destino", nullable = false)
    private String ibanDestino;

    @ManyToOne
    @JoinColumn(name = "id_conta") // Chave estrangeira que referencia a 'conta'
    @JsonBackReference // Evita loop infinito ao serializar JSON
    private Conta conta;
}