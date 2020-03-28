package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.assembler.UsuarioAssembler;
import com.algaworks.algafood.api.assembler.UsuarioInputDesassembler;
import com.algaworks.algafood.api.assembler.UsuarioNameEmailInputDesassembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.api.model.input.UsuarioNameEmailInput;
import com.algaworks.algafood.api.model.input.UsuarioPasswordInput;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.exception.UsuarioPasswordInvalidoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CadastroUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioAssembler usuarioAssembler;
    private final UsuarioInputDesassembler usuarioInputDesassembler;
    private final UsuarioNameEmailInputDesassembler usuarioNameInputDesassembler;
    private final GrupoModelAssembler grupoModelAssembler;
    private final CadastroGrupoService grupoService;
    private static final String USUARIO_EM_USO = "Usuario encontra-se em uso %d";
    private static final String USUARIO_SENHA_INCORRETA = "senha atual incorreta para o usuario %d";

    @Transactional
    public UsuarioModel save(Usuario usuario){
        usuarioRepository.detach(usuario);// se nao ele atualiza o cara e vai vir 2 registros no findbyemail

        if(!usuarioRepository.findByEmail(usuario.getEmail()).filter(m -> !m.equals(usuario)).isEmpty()){
                throw new NegocioException(String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
        }

        usuarioRepository.save(usuario);
        usuarioRepository.flush();
        return usuarioAssembler.toModel(usuario);
    }

    @Transactional
    public void adicionarGrupo(Long usuarioId, Long grupoId){
        var usuario = getUsuario(usuarioId);
        var grupo = grupoService.getGrupo(grupoId);
        usuario.adicionarGrupo(grupo);
    }

    @Transactional
    public void removerGrupo(Long usuarioId, Long grupoId){
        var usuario = getUsuario(usuarioId);
        var grupo = grupoService.getGrupo(grupoId);
        usuario.removerGrupo(grupo);
    }

    public List<GrupoModel> getGrupos(Long usuarioId){
        return getUsuario(usuarioId).getGrupos().stream().map(g -> grupoModelAssembler.toModel(g)).collect(Collectors.toList());
    }

    public GrupoModel getGrupo(Long usuarioId, Long grupoId){
        return grupoModelAssembler.toModel(getUsuario(usuarioId).getGrupos().stream().filter(g -> g.getId().equals(grupoId)).findFirst().orElse(new Grupo()));
    }

    @Transactional
    public void updatePassword(UsuarioPasswordInput input, Long id){
        var usuario = getUsuario(id);
        if(!usuario.getSenha().equals(input.getSenhaAtual())){
            throw new UsuarioPasswordInvalidoException(String.format(USUARIO_SENHA_INCORRETA, id));
        }

        usuario.setSenha(input.getNovaSenha());
        usuarioRepository.flush();
    }

    public UsuarioModel update(Usuario usuario, Long id){
        Usuario entity = getUsuario(id);
        BeanUtils.copyProperties(usuario, entity, "id", "dataCadastro", "grupos", "senha");
        save(entity);
        return usuarioAssembler.toModel(entity);
    }

    @Transactional
    public void deleteById(Long id){
        try{
            usuarioRepository.deleteById(id);
            usuarioRepository.flush();
        }catch (EmptyResultDataAccessException e){
            throw new UsuarioNaoEncontradoException(id);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(USUARIO_EM_USO, id));
        }
    }

    public UsuarioModel findById(Long id){
        return usuarioAssembler.toModel(getUsuario(id));
    }

    public List<UsuarioModel> findAll(){
        return usuarioAssembler.toModelList(usuarioRepository.findAll());
    }

    public Usuario getUsuario(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

}
