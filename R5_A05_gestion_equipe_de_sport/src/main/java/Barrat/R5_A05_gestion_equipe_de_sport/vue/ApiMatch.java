package Barrat.R5_A05_gestion_equipe_de_sport.vue;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

import Barrat.R5_A05_gestion_equipe_de_sport.model.Match;
import Barrat.R5_A05_gestion_equipe_de_sport.model.repository.MatchRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

@RestController
public class ApiMatch {
	private static final ResponseStatusException THROW_CREATE_UPDATE_DATE_PASSER = new ResponseStatusException(HttpStatus.BAD_REQUEST, "La date du match créé ne peut pas être dans le passé.");

	public static final ResponseStatusException THROW_ID_INTROUVABLE = new ResponseStatusException(HttpStatus.NOT_FOUND, "Match introuvable");
	
	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired
	private Validator validator;
	
	@PostMapping("/match")
	public Match postMatch(@Valid @RequestBody Match match) {
		match.setIdMatch(null);
		if (!match.matchAvenir()) {
			throw THROW_CREATE_UPDATE_DATE_PASSER;
		}
		match.setResultat(null);
	    return matchRepository.save(match);
	}
	
	@GetMapping("/match")
    public List<Match> getAll() {
        return matchRepository.findAll();
    }
	
	@GetMapping("/match/{id}")
    public Match getOne(@PathVariable Long id) {
		this.assertMatchExiste(id);
        return matchRepository.findById(id).orElseThrow(() -> THROW_ID_INTROUVABLE) ;
    }
	
	@PutMapping("/match")
	public Match putMatch(@RequestBody Match match) {
		this.assertMatchExiste(match.getIdMatch());
		Match existant = matchRepository.findById(match.getIdMatch()).orElseThrow(() -> THROW_ID_INTROUVABLE); 
		
		if (existant.matchAvenir()) {
			Set<ConstraintViolation<Match>> erreurs = validator.validate(match);
	        if (!erreurs.isEmpty()) {
	            String msg = erreurs.stream()
	                    .map(ConstraintViolation::getMessage)
	                    .collect(Collectors.joining(" ; "));
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, msg);
	        }
			if (!match.matchAvenir()) {
				throw THROW_CREATE_UPDATE_DATE_PASSER;
			}
			existant = match;
			match.setResultat(null);
		} else {
			existant.setResultat(match.getResultat());
		}
	    return matchRepository.save(existant);
	}
	
	@DeleteMapping("/match/{id}")
	public String deleteMatch(@PathVariable Long id) {
		this.assertMatchExiste(id);
		matchRepository.deleteById(id);
	    return "Match d'id " + id + " a été supprimé avec succès";
	}

	private void assertMatchExiste(Long id) {
		if ( id ==  null || !matchRepository.existsById(id)) {
	        throw THROW_ID_INTROUVABLE;
	    }
	}
	
}
