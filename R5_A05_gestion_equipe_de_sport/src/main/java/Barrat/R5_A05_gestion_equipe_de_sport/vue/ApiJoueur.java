package Barrat.R5_A05_gestion_equipe_de_sport.vue;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Barrat.R5_A05_gestion_equipe_de_sport.model.Joueur;
import Barrat.R5_A05_gestion_equipe_de_sport.model.repository.JoueurRepository;
import jakarta.validation.Valid;



@RestController
public class ApiJoueur {
	
	@Autowired
	private JoueurRepository joueurRepository;

	@GetMapping("/joueur")
    public String getAll() {
		
        return "get All";
    }
	
	@GetMapping("/joueur/{id}")
    public Joueur getOne(@PathVariable long id) {
		
        return joueurRepository.findById(id).orElseThrow() ;
    }
	
	@PostMapping("/joueur")
	public @Valid Joueur postJoueur(@Valid @RequestBody Joueur joueur) {
		joueur.setIdJoueur(null);
	    return joueurRepository.save(joueur);
		
	}

}
