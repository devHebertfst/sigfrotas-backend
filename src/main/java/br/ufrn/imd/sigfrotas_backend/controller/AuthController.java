package br.ufrn.imd.sigfrotas_backend.controller;



import br.ufrn.imd.sigfrotas_backend.domain.Usuario;
import br.ufrn.imd.sigfrotas_backend.dto.UserReqResponseDTO;
import br.ufrn.imd.sigfrotas_backend.services.auth.UsuarioManagementService;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioManagementService usuarioManagementService;

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario userReqResponseDTO) throws AuthException {
            Usuario userReqResponseDTO1 = usuarioManagementService.register(userReqResponseDTO);
            return ResponseEntity.ok().body(userReqResponseDTO1);
    }

    @PostMapping("/login")
    public ResponseEntity<UserReqResponseDTO> login(@RequestBody Usuario userReqResponseDTO){
        try {
            UserReqResponseDTO userReqResponseDTO1 = usuarioManagementService.login(userReqResponseDTO);
            return ResponseEntity.ok().body(userReqResponseDTO1);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(400).body(null);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserReqResponseDTO> refresh(@RequestBody Usuario userReqResponseDTO){
        try {
            UserReqResponseDTO userReqResponseDTO1 = usuarioManagementService.refresh(userReqResponseDTO);
            return ResponseEntity.ok().body(userReqResponseDTO1);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/test-exception")
    public void testException() throws AuthException {
        throw new AuthException("Teste de AuthException");
    }


}
