package br.com.alura.forum.infra.security;

import br.com.alura.forum.models.User;
import br.com.alura.forum.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var token = this.recoverToken(request);

        System.out.println(token);

        if(token != null ){
            var userId = tokenService.validateToken(token);
            User user = userRepository.findById(UUID.fromString(userId)).get();
            System.out.println(user.getName());

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }


        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var token = request.getHeader("Authorization");

        System.out.println("Token completo *****"+ token);

        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) return null;

        return token.replace("Bearer ", "");
    }
}
