package com.benvcode.banco.a.domain.transacao.interfaces;

import com.benvcode.banco.a.domain.transacao.Transacao;
import com.benvcode.banco.a.domain.transacao.TransacaoDto;
import com.benvcode.shared.module.doamin.transacao.TransacaoApiContractDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransacaoMapper {
    TransacaoMapper INSTANCE = Mappers.getMapper(TransacaoMapper.class);

    // Mapear de "TransacaoApiContractDto" para "TransacaoDto".
    TransacaoDto toTransacaoDto(TransacaoApiContractDto dto);

    Transacao dtoToEntity(TransacaoDto transacaoDto);

    TransacaoDto entityToDto(Transacao transacao);

    // Outros metodos aqui...

}