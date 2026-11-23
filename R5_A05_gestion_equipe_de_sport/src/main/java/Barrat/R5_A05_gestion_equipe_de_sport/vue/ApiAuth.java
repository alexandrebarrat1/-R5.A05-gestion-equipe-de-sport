package Barrat.R5_A05_gestion_equipe_de_sport.vue;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Barrat.R5_A05_gestion_equipe_de_sport.model.dto.ConnexionDto;
import Barrat.R5_A05_gestion_equipe_de_sport.security.JwtDTO;
import Barrat.R5_A05_gestion_equipe_de_sport.security.TokenGenerator;
import jakarta.validation.Valid;

@RestController
public class ApiAuth {

    private final AuthenticationManager authenticationManager;
    private final TokenGenerator tokenGenerator;

    public ApiAuth(AuthenticationManager authenticationManager, TokenGenerator tokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
    }

    @PostMapping("/auth/login")
    public JwtDTO postJwtDTO(@Valid @RequestBody ConnexionDto connexion) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(connexion.login(), connexion.mdp()));

        String token = tokenGenerator.generateJwtToken(auth);

        List<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new JwtDTO(token, connexion.login(), roles);
    }
}
