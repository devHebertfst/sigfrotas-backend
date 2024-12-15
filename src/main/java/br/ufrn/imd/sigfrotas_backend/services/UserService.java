package br.ufrn.imd.sigfrotas_backend.services;

import br.ufrn.imd.sigfrotas_backend.domain.Usuario;
import br.ufrn.imd.sigfrotas_backend.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UsuarioRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Usuario> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<Usuario> findAll() {
        return userRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return Optional.ofNullable(userRepository.findById(id).orElse(null));
    }

    public Usuario save(Usuario user) {
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
