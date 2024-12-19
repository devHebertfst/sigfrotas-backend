package br.ufrn.imd.sigfrotas_backend.services.auth;

import br.ufrn.imd.sigfrotas_backend.domain.Cargo;
import br.ufrn.imd.sigfrotas_backend.domain.Usuario;
import br.ufrn.imd.sigfrotas_backend.dto.UserReqResponseDTO;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UsuarioManagementService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    public Usuario register(Usuario usuarioReq) throws AuthException {
        Optional<Usuario> userOptional = userService.findByLogin(usuarioReq.getUsername());

        if (userOptional.isPresent()) {
            throw new AuthException("Login já existe no sistema.");
        }

        Usuario user = new Usuario();
        user.setUsername(usuarioReq.getUsername());
        user.setRole(usuarioReq.getCargo());
        user.setSenha(passwordEncoder.encode(usuarioReq.getPassword()));

        Usuario usuarioSalvoBD = userService.save(user);

        if (usuarioSalvoBD.getId() > 0) {
            return usuarioSalvoBD;
        } else {
            throw new AuthException("Erro ao salvar usuário");
        }
    }

    public UserReqResponseDTO login(Usuario loginRequest) throws AuthException {
        UserReqResponseDTO userReqResponseDTO = new UserReqResponseDTO();

        try {
            Optional<Usuario> user = userService.findByLogin(loginRequest.getUsername());
            if (user.isPresent()) {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(), loginRequest.getPassword()
                        )
                );
                String token = jwtService.generateToken(user.get());
                String refreshToken = jwtService.refreshToken(new HashMap<>(), user.get());

                userReqResponseDTO.setToken(token);
                userReqResponseDTO.setRefreshToken(refreshToken);
                userReqResponseDTO.setExpirationTime("15 minutos");
                userReqResponseDTO.setMessage("Usuário autenticado com sucesso");
                userReqResponseDTO.setStatusCode(200);
            } else {
                throw new AuthException("Dados não encontrados");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthException("Usuário ou senha inválidos");
        }

        return userReqResponseDTO;
    }

    public UserReqResponseDTO refresh(Usuario user) throws AuthException {
        UserReqResponseDTO userReqResponseDTO1 = new UserReqResponseDTO();
        Optional<Usuario> userBanco = userService.findByLogin(user.getUsername());

        if (userBanco.isPresent()) {
            String token = jwtService.generateToken(user);
            String refreshToken = jwtService.refreshToken(new HashMap<>(), user);

            userReqResponseDTO1.setToken(token);
            userReqResponseDTO1.setRefreshToken(refreshToken);
            userReqResponseDTO1.setExpirationTime("15 minutos");
            userReqResponseDTO1.setMessage("Token atualizado com sucesso");
            userReqResponseDTO1.setStatusCode(200);
        } else {
            throw new AuthException("Usuário não encontrado");
        }

        return userReqResponseDTO1;
    }

    public UserReqResponseDTO getMyInfo(String login) throws AuthException {
        UserReqResponseDTO userReqResponseDTO = new UserReqResponseDTO();
        Optional<Usuario> user = userService.findByLogin(login);

        if (user.isPresent()) {
            userReqResponseDTO.setStatusCode(200);
        } else {
            throw new AuthException("Usuário não encontrado");
        }

        return userReqResponseDTO;
    }

    public UserReqResponseDTO updateUser(UserReqResponseDTO userReqResponseDTO) throws AuthException {
        UserReqResponseDTO userReqResponseDTO1 = new UserReqResponseDTO();
        Optional<Usuario> user = userService.findByLogin(userReqResponseDTO.getUser().getUsername());

        if (user.isPresent()) {
            user.get().setUsername(userReqResponseDTO.getUser().getUsername());
            user.get().setSenha(passwordEncoder.encode(userReqResponseDTO.getUser().getUsername()));
            Usuario usuario = userService.save(user.get());

            if (usuario.getId() > 0) {
                userReqResponseDTO1.setMessage("Usuário atualizado com sucesso");
                userReqResponseDTO1.setStatusCode(200);
            }
        } else {
            throw new AuthException("Usuário não encontrado");
        }

        return userReqResponseDTO1;
    }
}
