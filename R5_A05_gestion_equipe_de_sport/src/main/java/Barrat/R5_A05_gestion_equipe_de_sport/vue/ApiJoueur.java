package Barrat.R5_A05_gestion_equipe_de_sport.vue;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import Barrat.R5_A05_gestion_equipe_de_sport.model.Joueur;
import Barrat.R5_A05_gestion_equipe_de_sport.model.repository.JoueurRepository;
import jakarta.validation.Valid;



@RestController
public class ApiJoueur {
	
	public static final ResponseStatusException THROW_ID_INTROUVABLE = new ResponseStatusException(HttpStatus.NOT_FOUND, "Joueur introuvable");
	
	@Autowired
	private JoueurRepository joueurRepository;

	@GetMapping("/joueur")
    public List<Joueur> getAll() {
		
        return joueurRepository.findAll();
    }
	
	@GetMapping("/joueur/{id}")
    public Joueur getOne(@PathVariable Long id) {
		this.assertJoueurExiste(id);
        return joueurRepository.findById(id).orElseThrow(() -> THROW_ID_INTROUVABLE) ;
    }
	
	@PostMapping("/joueur")
	public Joueur postJoueur(@Valid @RequestBody Joueur joueur) {
		joueur.setIdJoueur(null);
		this.assertConflitNumLicence(joueur);
	    return joueurRepository.save(joueur);
	}

	
	
	@PutMapping("/joueur")
	public Joueur putJoueur(@Valid @RequestBody Joueur joueur) {
		if (joueur.getIdJoueur() == null) {
		    throw THROW_ID_INTROUVABLE;
		}
		Joueur existant = joueurRepository.findById(joueur.getIdJoueur()).orElseThrow(() -> THROW_ID_INTROUVABLE); 

	    if (!existant.getNumeroLicence().equals(joueur.getNumeroLicence())) {
	        assertConflitNumLicence(joueur);
	    }
	    joueur.setCommentaires(existant.getCommentaires());
	    return joueurRepository.save(joueur);
	}
	
	@DeleteMapping("/joueur/{id}")
	public String deleteJoueur(@PathVariable Long id) {
		this.assertJoueurExiste(id);
	    joueurRepository.deleteById(id);
	    return "Joueur d'id " + id + " a été supprimé avec succès";
	}

	private void assertJoueurExiste(Long id) {
		if ( id ==  null || !joueurRepository.existsById(id)) {
	        throw THROW_ID_INTROUVABLE;
	    }
	}
	
	private void assertConflitNumLicence(Joueur joueur) {
		if (joueurRepository.existsByNumeroLicence(joueur.getNumeroLicence())) {
		    throw new ResponseStatusException(HttpStatus.CONFLICT, "Numéro de licence déjà utilisé");
		}
	}
	
	

}
