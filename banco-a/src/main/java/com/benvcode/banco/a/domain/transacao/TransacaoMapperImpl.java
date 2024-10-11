package com.benvcode.banco.a.domain.transacao;

import com.benvcode.banco.a.domain.transacao.interfaces.TransacaoMapper;
import com.benvcode.shared.module.doamin.transacao.TransacaoApiContractDto;
import org.springframework.stereotype.Component;

@Component
public class TransacaoMapperImpl implements TransacaoMapper {
    @Override
    public TransacaoDto toTransacaoDto(TransacaoApiContractDto transacaoApiContractDto) {
        return TransacaoMapper.INSTANCE.toTransacaoDto(transacaoApiContractDto);
    }
    @Override
    public Transacao dtoToEntity(TransacaoDto transacaoDto) {
        return TransacaoMapper.INSTANCE.dtoToEntity(transacaoDto);
    }

    @Override
    public TransacaoDto entityToDto(Transacao transacao) {
        return TransacaoMapper.INSTANCE.entityToDto(transacao);
    }
}
