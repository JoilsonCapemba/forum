package br.com.alura.forum.infra.security;

import br.com.alura.forum.models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("forum.jwt.secret")
    private String secret;

    public String generateToken(User user){
        try {
            return JWT.create()
                    .withIssuer("Forum Alura")
                    .withSubject(user.getId().toString())
                    .withExpiresAt(generateExpirationDate())
                    .sign(Algorithm.HMAC256(secret));
        }catch (JWTCreationException e){
            throw new RuntimeException("Erro ao gerar o token");
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("Forum Alura")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTCreationException e){
            return "";
        }
    }

    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
