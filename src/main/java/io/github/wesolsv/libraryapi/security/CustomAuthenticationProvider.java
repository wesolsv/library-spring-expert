package io.github.wesolsv.libraryapi.security;

import io.github.wesolsv.libraryapi.model.Usuario;
import io.github.wesolsv.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    
    private final UsuarioService usuarioService;
    private final PasswordEncoder encoder;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senhaDigitada = authentication.getCredentials().toString();

        Usuario usuarioEncontrado = usuarioService.obterPorLogin(login);
        if(usuarioEncontrado == null){
            throw getErroUsuarioNaoEncontrado();
        }

        String senhaCriptografada = usuarioEncontrado.getSenha();
        boolean senhasBatem = encoder.matches(senhaDigitada, senhaCriptografada);

        if(senhasBatem){
            return new CustomAuthentication(usuarioEncontrado);
        }
        throw getErroUsuarioNaoEncontrado();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }

    private UsernameNotFoundException getErroUsuarioNaoEncontrado() {
        return new UsernameNotFoundException("Usuário e/ou senha incorretos!");
    }
}
