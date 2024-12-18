package br.ufrn.imd.sigfrotas_backend.controller;

import br.ufrn.imd.sigfrotas_backend.domain.Usuario;
import br.ufrn.imd.sigfrotas_backend.services.auth.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<Usuario> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Usuario findById(@PathVariable Long id) {
        return (Usuario) userService.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    public Usuario save(@RequestBody Usuario user) {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
