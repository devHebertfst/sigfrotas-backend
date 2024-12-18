package br.ufrn.imd.sigfrotas_backend.services.auth;


import br.ufrn.imd.sigfrotas_backend.domain.Usuario;
import br.ufrn.imd.sigfrotas_backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public Optional<Usuario> findByLogin(String login){
        return usuarioRepository.findByUsername(login);
    }


    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findById(Long id){
        return usuarioRepository.findById(id);
    }

    public void deleteById(Long id){
        usuarioRepository.deleteById(id);
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

}
