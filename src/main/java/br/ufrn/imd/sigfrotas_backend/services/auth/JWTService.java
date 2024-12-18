package br.ufrn.imd.sigfrotas_backend.services.auth;

import br.ufrn.imd.sigfrotas_backend.domain.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTService {

    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private static final long EXPIRATION_TIME = 900_000; // 15 minutos em milissegundos

    public JWTService() {
        String secretKey = "my-super-secret-key";
        this.algorithm = Algorithm.HMAC256(secretKey);

        // Configura o verificador de tokens
        this.verifier = JWT.require(algorithm).build();
    }

    /**
     * Gera um token JWT para um usuário específico.
     *
     * @param user Usuário autenticado
     * @return Token JWT
     */
    public String generateToken(Usuario user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    /**
     * Gera um token de refresh para um usuário.
     *
     * @param claims Dados adicionais para incluir no token
     * @param user   Usuário autenticado
     * @return Token JWT de refresh
     */
    public String refreshToken(Map<String, Object> claims, Usuario user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withPayload(claims) // Adiciona os dados extras no payload
                .sign(algorithm);
    }

    /**
     * Extrai o login do usuário a partir do token JWT.
     *
     * @param token Token JWT
     * @return Login do usuário
     */
    public String getLoginFromToken(String token) {
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

    /**
     * Valida se o token é válido para o usuário.
     *
     * @param token Token JWT
     * @param user  Usuário autenticado
     * @return True se válido, false caso contrário
     */
    public boolean isTokenValid(String token, Usuario user) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject().equals(user.getUsername()) && !isTokenExpired(decodedJWT);
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    /**
     * Verifica se o token está expirado.
     *
     * @param decodedJWT Token decodificado
     * @return True se expirado, false caso contrário
     */
    private boolean isTokenExpired(DecodedJWT decodedJWT) {
        return decodedJWT.getExpiresAt().before(new Date());
    }
}
