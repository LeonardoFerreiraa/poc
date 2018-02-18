package saballestra.deeping.mapstruct.mapper.util;

import saballestra.deeping.mapstruct.domain.Pessoa;
import saballestra.deeping.mapstruct.domain.dto.PessoaDTO;
import saballestra.deeping.mapstruct.mapper.Mapper10;

/**
 * @author s2it_leferreira
 * @since 2/1/18 11:36 AM
 */
public class PessoaDecorator implements Mapper10 {

    private Mapper10 delegate;

    public PessoaDecorator(Mapper10 delegate) {
        this.delegate = delegate;
    }

    @Override
    public PessoaDTO toFullDTO(Pessoa pessoa) {
        PessoaDTO pessoaDTO = delegate.toFullDTO(pessoa);
        pessoaDTO.setNome(pessoa.getPrimeiroNome() + " " + pessoa.getSobreNome());
        pessoaDTO.setTelefoneOpcional(pessoa.getTelefone2().getDdd() + " " + pessoa.getTelefone2().getNumero());
        return pessoaDTO;
    }
}
