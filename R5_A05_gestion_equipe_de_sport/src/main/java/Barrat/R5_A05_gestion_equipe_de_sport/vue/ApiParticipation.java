package Barrat.R5_A05_gestion_equipe_de_sport.vue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import Barrat.R5_A05_gestion_equipe_de_sport.model.Match;
import Barrat.R5_A05_gestion_equipe_de_sport.model.Participation;
import Barrat.R5_A05_gestion_equipe_de_sport.model.Participation.ParticipationId;
import Barrat.R5_A05_gestion_equipe_de_sport.model.repository.ParticipationRepository;
import Barrat.R5_A05_gestion_equipe_de_sport.model.repository.JoueurRepository;
import Barrat.R5_A05_gestion_equipe_de_sport.model.repository.MatchRepository;
import jakarta.validation.Valid;

@RestController
public class ApiParticipation {
	public static final ResponseStatusException THROW_ID_JOUEUR_INTROUVABLE = new ResponseStatusException(HttpStatus.NOT_FOUND, "Joueur introuvable");
	public static final ResponseStatusException THROW_ID_MATCH_INTROUVABLE = new ResponseStatusException(HttpStatus.NOT_FOUND, "Match introuvable");
	public static final ResponseStatusException THROW_ID_PARTICIPATION_INTROUVABLE = new ResponseStatusException(HttpStatus.NOT_FOUND, "Le joueur ne semble pas participer à ce match");


	@Autowired
	private ParticipationRepository participationRepository;
	
	@Autowired
	private JoueurRepository joueurRepository;
	
	@Autowired
	private MatchRepository matchRepository;
	
	public void verifExistanceMatchJoueur(Long matchId, Long joueurId) {
		if (!matchRepository.existsById(matchId)) throw THROW_ID_MATCH_INTROUVABLE;
		if (!joueurRepository.existsById(joueurId)) throw THROW_ID_JOUEUR_INTROUVABLE;
	}
	
	@PostMapping("/match/{matchId}/participations/{joueurId}")
	public Participation postParticipation(@PathVariable Long matchId,
	                                       @PathVariable Long joueurId,
	                                       @Valid @RequestBody Participation participation) {
	    verifExistanceMatchJoueur(matchId, joueurId);
	    Match match = matchRepository.findById(matchId).orElseThrow(() -> THROW_ID_MATCH_INTROUVABLE);
	    if (!match.matchAvenir()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La date du match créé ne peut pas être dans le passé.");
		}
	    if (participationRepository.existsById(new ParticipationId(joueurId, matchId))) {
	        throw new ResponseStatusException(HttpStatus.CONFLICT, "Participation déjà existante");
	    }
	    List<Participation> participations = participationRepository.findByIdMatchId(matchId);
	    switch (participation.getPoste().verifierPoste(participations.stream().map(x -> x.getPoste()).toList())) {
	        case POSTE_TITULAIRE_DEJA_PRIS -> throw new ResponseStatusException(HttpStatus.CONFLICT, "Poste de titulaire déjà pris");
	        case REMPLACANTS_COMPLETS -> throw new ResponseStatusException(HttpStatus.CONFLICT, "Poste de Remplacant complet");
	        case PLUS_DE_POSTE_VACANT -> throw new ResponseStatusException(HttpStatus.CONFLICT, "Plus de poste vacant");
	        case POSTE_LIBRE -> { /* on continue */ }
	    }
	    participation.setJoueur(joueurRepository.getReferenceById(joueurId));
	    participation.setMatch(match);
	    participation.setEvaluation(null);
	    return participationRepository.save(participation);
	}
	
	@GetMapping("/match/{matchId}/participations/{joueurId}")
	public Participation getParticipation(@PathVariable Long matchId, @PathVariable Long joueurId) {
		verifExistanceMatchJoueur(matchId, joueurId);
	    return participationRepository.findById(new ParticipationId(joueurId, matchId)).orElseThrow(() -> THROW_ID_PARTICIPATION_INTROUVABLE);
	}
	
	@GetMapping("/match/{matchId}/participations")
	public List<Participation> getParticipationsByMatch(@PathVariable Long matchId) {
	    if (!matchRepository.existsById(matchId)) throw THROW_ID_MATCH_INTROUVABLE;
	    return participationRepository.findByIdMatchId(matchId);
	}
	
	@GetMapping("/joueur/{joueurId}/participations")
	public List<Participation> getParticipationsByJoueur(@PathVariable Long joueurId) {
		if (!joueurRepository.existsById(joueurId)) throw THROW_ID_JOUEUR_INTROUVABLE;
	    return participationRepository.findByIdJoueurId(joueurId);
	}
	
	
}
