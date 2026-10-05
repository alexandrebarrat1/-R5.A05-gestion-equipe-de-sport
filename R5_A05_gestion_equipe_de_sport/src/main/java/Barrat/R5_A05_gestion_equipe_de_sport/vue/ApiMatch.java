package Barrat.R5_A05_gestion_equipe_de_sport.vue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import Barrat.R5_A05_gestion_equipe_de_sport.model.Match;
import Barrat.R5_A05_gestion_equipe_de_sport.model.repository.MatchRepository;
import jakarta.validation.Valid;

@RestController
public class ApiMatch {
	public static final ResponseStatusException THROW_ID_INTROUVABLE = new ResponseStatusException(HttpStatus.NOT_FOUND, "Match introuvable");
	
	@Autowired
	private MatchRepository matchRepository;
	
	@PostMapping("/match")
	public Match postMatch(@Valid @RequestBody Match match) {
		match.setIdMatch(null);
		if (!match.matchAvenir()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La date du match créé ne peut pas être dans le passé.");
		}
		match.setResultat(null);
	    return matchRepository.save(match);
	}
	
	
}
