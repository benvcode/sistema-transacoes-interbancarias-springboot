package com.benvcode.banco.a.domain.agencia.bancaria;

import com.benvcode.banco.a.domain.conta.Conta;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "agencia_bancaria")
public class AgenciaBancaria implements Serializable {
    @PrePersist
    protected void onCreate() {
        if (this.codigoBanco == null) {
            this.codigoBanco = "BA"; // Valor padrão
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    // JPA não aplica automaticamente valores padrão definido na coluna (columnDefinition) para campos de texto
    // (character varying). Assim, mesmo definido um valor padrão como "BA", o JPA não o usará automaticamente
    // na persistência. Por isso, é necessário inicializar esses valores usando um "callback" (@PrePersist).
    @Column(name = "codigo_banco", nullable = false, columnDefinition = "character varying default 'BA'")
    private String codigoBanco;

    // Relação bidirecional, definindo uma relação de agregação com a entidade "Conta".
    // Agregação (--◇): representa um relacionamento forte, onde o 'filho' (a parte) pode existir
    // independentemente do 'pai' (o todo). Ou seja, as "Contas" podem continuar existindo mesmo
    // se uma agência deixar de existir. Embora uma agência deixar de existir as contas podem ser transferidas
    // para outras agências.
    // "cascade=CascadeType.ALL" indica que todas as operações de persistência (PERSIST, REMOVE, etc)
    // realizadas na entidade pai (agencia_bancaria) serão automaticamente propagadas para as entidades
    // filhas (contaList).
    @OneToMany(mappedBy="agenciaBancaria", cascade=CascadeType.ALL)
    @JsonManagedReference // Gerencia a serialização do lado do pai
    private List<Conta> contaList = new ArrayList<Conta>();
}