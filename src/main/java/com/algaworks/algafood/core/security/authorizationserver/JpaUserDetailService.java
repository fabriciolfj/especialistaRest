package com.algaworks.algafood.core.security.authorizationserver;

import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(s)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado."));

        return new AuthUser(usuario, getAuthorities(usuario));
    }

    public Collection<GrantedAuthority>  getAuthorities(Usuario usuario) {
        return usuario.getGrupos().stream()
                .flatMap(grupo -> grupo.getPermissoes().stream()
                .map(permissao -> new SimpleGrantedAuthority(permissao.getNome().toUpperCase())))
                .collect(Collectors.toSet());
    }

    private Grupo test(Usuario usuario) {
        return usuario.getGrupos().stream()
                .map(grupo -> {
                    grupo.setId(0L);
                    return grupo;
                })
                .collect(Collectors.toList()).get(0);
    }
}
