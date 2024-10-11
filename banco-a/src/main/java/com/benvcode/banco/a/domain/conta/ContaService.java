package com.benvcode.banco.a.domain.conta;

import com.benvcode.banco.a.domain.conta.interfaces.ContaRepository;
import com.benvcode.shared.module.exceptions.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ContaService {
    private final ContaRepository contaRepository;

    public Conta save(Conta conta)  {

        return contaRepository.save(conta);
    }

    public Conta getByIban(String ibanConta) {
        Conta conta = contaRepository.findByIban(ibanConta);
        if (conta == null) {
            throw new EntityNotFoundException(
                    String.format("Conta com IBAN '%s' não encontrada", ibanConta)
            );
        }
        return  conta;
    }

    public void updateSaldo(Conta conta, BigDecimal montante){
        conta.setSaldo(conta.getSaldo().add(montante));
        contaRepository.saveAndFlush(conta);
    }


}
