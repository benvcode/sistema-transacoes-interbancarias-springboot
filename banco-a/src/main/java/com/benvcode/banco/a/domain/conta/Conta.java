package com.benvcode.banco.a.domain.conta;

import com.benvcode.banco.a.domain.agencia.bancaria.AgenciaBancaria;
import com.benvcode.banco.a.domain.cliente.Cliente;
import com.benvcode.banco.a.domain.transacao.Transacao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "conta")
public class Conta implements Serializable {

    @PrePersist
    protected void onCreate() {
        if (this.saldo == null) {
            this.saldo = BigDecimal.valueOf(0.00); // Valor padrão
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conta", nullable = false)
    private TipoContaEnum tipoConta = TipoContaEnum.SINGULAR;

    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "num_conta", nullable = false, unique = true)
    private String numConta;

    @Column(name = "iban", nullable = false, unique = true)
    private String iban;

    @Column(name = "saldo", nullable = false , columnDefinition = "numeric default 0.00")
    private BigDecimal saldo;

    @OneToOne
    @JoinColumn(name = "id_agencia_bancaria") // Chave estrangeira que referencia a 'agencia_bancaria'
    @JsonBackReference // Evita loop infinito ao serializar JSON
    private AgenciaBancaria agenciaBancaria;

    @ManyToOne
    @JoinColumn(name = "id_cliente") // Chave estrangeira que referencia o 'cliente'
    private Cliente cliente;

    // Relação bidirecional, definindo uma relação de composição com a entidade "Transacao".
    // Composição (--◆): representa um relacionamento fraco, onde o 'filho' (a parte)
    // e o 'pai' (o todo) são altamente dependentes uma da outra. Ou seja, as transações
    // dependem diretamente da existência das contas, e se uma conta bancária for encerrada
    // todas as transações associadas já não serão consideradas.
    // "cascade=CascadeType.ALL" indica que todas as operações de persistência (PERSIST, REMOVE, etc)
    // realizadas na entidade pai (conta) serão automaticamente propagadas para as entidades filhas (transacaoList).
    // "orphanRemoval" indica que a remoção automática dos filhos(contaList) no banco de dados
    // se a entidade pai(conta) for removido.
    @OneToMany(mappedBy="conta", cascade=CascadeType.ALL, orphanRemoval=true)
    @JsonManagedReference // Gerencia a serialização do lado do pai
    private List<Transacao> transacaoList = new ArrayList<Transacao>();

    public enum TipoContaEnum {
        SINGULAR, COLECTIVA
    }


}