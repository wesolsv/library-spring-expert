package io.github.wesolsv.libraryapi.security;

import io.github.wesolsv.libraryapi.model.Usuario;
import io.github.wesolsv.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UsuarioService service;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = service.obterPorLogin(login);

        if(usuario == null){
            throw new UsernameNotFoundException("O usuário não foi encontrado");
        }

        return User.builder()
                .username((usuario.getLogin()))
                .password((usuario.getSenha()))
                .roles(usuario.getRoles().toArray(new String[usuario.getRoles().size()]))
                .build();
    }
}
