package com.daviaugusto.agendador_tarefas.infrastructure.security;
import com.daviaugusto.agendador_tarefas.infrastructure.client.UsuarioClient;
import com.daviaugusto.agendador_tarefas.infrastructure.dtos.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl{

    @Autowired
    public UsuarioClient usuarioClient;



    public UserDetails carregaDadosUsuario(String email, String token){
        UsuarioDTO usuarioDTO = usuarioClient.buscarUsuarioPorEmail(email, token);

        return User
                .withUsername(usuarioDTO.getEmail()) // Define o nome de usuário como o e-mail
                .password(usuarioDTO.getSenha()) // Define a senha do usuário
                .build(); // Constrói o objeto UserDetails
    }
}
