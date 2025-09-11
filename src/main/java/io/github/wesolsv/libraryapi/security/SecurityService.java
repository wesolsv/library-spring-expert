package io.github.wesolsv.libraryapi.security;

import io.github.wesolsv.libraryapi.model.Usuario;
import io.github.wesolsv.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioService usuarioService;

    public Usuario obterUsuarioLogado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userLogado = (UserDetails) authentication.getPrincipal();
        return usuarioService.obterPorLogin(userLogado.getUsername());
    }
}
