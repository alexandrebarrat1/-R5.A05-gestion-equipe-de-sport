package Barrat.R5_A05_gestion_equipe_de_sport;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BonjourController {

    @GetMapping("/bonjour")
    public String bonjour() {
        return "Bonjour le monde !";
    }
}