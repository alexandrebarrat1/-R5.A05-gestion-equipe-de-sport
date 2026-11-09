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

import Barrat.R5_A05_gestion_equipe_de_sport.model.Match;
import Barrat.R5_A05_gestion_equipe_de_sport.model.Participation;
import Barrat.R5_A05_gestion_equipe_de_sport.model.Participation.ParticipationId;
import Barrat.R5_A05_gestion_equipe_de_sport.model.Poste;
import Barrat.R5_A05_gestion_equipe_de_sport.model.dto.ParticipationCreateDto;
import Barrat.R5_A05_gestion_equipe_de_sport.model.dto.ParticipationUpdateDto;
import Barrat.R5_A05_gestion_equipe_de_sport.model.repository.ParticipationRepository;
import Barrat.R5_A05_gestion_equipe_de_sport.model.repository.JoueurRepository;
import Barrat.R5_A05_gestion_equipe_de_sport.model.repository.MatchRepository;
import jakarta.validation.Valid;

@RestController
public class ApiParticipation {

	@Autowired
	private ParticipationRepository participationRepository;
	
	@Autowired
	private JoueurRepository joueurRepository;
	
	@Autowired
	private MatchRepository matchRepository;
	
	public void verifExistanceMatchJoueur(Long matchId, Long joueurId) {
		if (!matchRepository.existsById(matchId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match introuvable");
		if (!joueurRepository.existsById(joueurId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Joueur introuvable");
	}
	
	@PostMapping("/match/{matchId}/participations/{joueurId}")
	public Participation postParticipation(@PathVariable Long matchId,
	                                       @PathVariable Long joueurId,
	                                       @Valid @RequestBody ParticipationCreateDto dto) {
	    verifExistanceMatchJoueur(matchId, joueurId);
	    Match match = matchRepository.findById(matchId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match introuvable"));
	    if (!match.matchAvenir()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un joueur ne peut participer à un match déjà joué");
		}
	    if (participationRepository.existsById(new ParticipationId(joueurId, matchId))) {
	        throw new ResponseStatusException(HttpStatus.CONFLICT, "Participation déjà existante");
	    }
	    Participation participation = dto.toEntity(joueurRepository.getReferenceById(joueurId), match);
	    this.posteLibre(matchId, participation, dto.poste());
	    return participationRepository.save(participation);
	}

	private void posteLibre(Long matchId, Participation existante, Poste nouveauPoste) {
	    List<Poste> autresPostes = participationRepository.findByIdMatchId(matchId).stream()
	            .filter(p -> !p.getId().equals(existante.getId()))
	            .map(Participation::getPoste)
	            .toList();

	    switch (nouveauPoste.verifierPoste(autresPostes)) {
	        case POSTE_TITULAIRE_DEJA_PRIS -> throw new ResponseStatusException(HttpStatus.CONFLICT, "Poste de titulaire déjà pris");
	        case REMPLACANTS_COMPLETS -> throw new ResponseStatusException(HttpStatus.CONFLICT, "Poste de remplaçant complet");
	        case PLUS_DE_POSTE_VACANT -> throw new ResponseStatusException(HttpStatus.CONFLICT, "Plus de poste vacant");
	        case POSTE_LIBRE -> { /* on continue */ }
	    }
	}
	
	@GetMapping("/match/{matchId}/participations/{joueurId}")
	public Participation getParticipation(@PathVariable Long matchId, @PathVariable Long joueurId) {
		return recupParticipation(matchId, joueurId);
	}

	private Participation recupParticipation(Long matchId, Long joueurId) {
		verifExistanceMatchJoueur(matchId, joueurId);
	    return participationRepository.findById(new ParticipationId(joueurId, matchId)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Le joueur ne semble pas participer à ce match"));
	}
	
	@GetMapping("/match/{matchId}/participations")
	public List<Participation> getParticipationsByMatch(@PathVariable Long matchId) {
	    if (!matchRepository.existsById(matchId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match introuvable");
	    return participationRepository.findByIdMatchId(matchId);
	}
	
	@GetMapping("/joueur/{joueurId}/participations")
	public List<Participation> getParticipationsByJoueur(@PathVariable Long joueurId) {
		if (!joueurRepository.existsById(joueurId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Joueur introuvable");
	    return participationRepository.findByIdJoueurId(joueurId);
	}
	
	@PutMapping("/match/{matchId}/participations/{joueurId}")
	public Participation putParticipation(@PathVariable Long matchId,
	                                      @PathVariable Long joueurId,
	                                      @Valid @RequestBody ParticipationUpdateDto dto) {
	    Participation existante = recupParticipation(matchId, joueurId);

	    if (existante.getMatch().matchAvenir()) {
	    	if (dto.poste() == null) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le poste est obligatoire pour un match à venir");
	        }
	    	if (dto.evaluation() != null) {
	            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Impossible d'évaluer un joueur avant le match");
	        }
	        if (dto.poste() == existante.getPoste()) {
	            throw new ResponseStatusException(HttpStatus.CONFLICT, "Le joueur possède déjà ce poste");
	        }
	        this.posteLibre(matchId, existante, dto.poste());
	        existante.setPoste(dto.poste());
	    } else {
	        existante.setEvaluation(dto.evaluation());
	    }
	    return participationRepository.save(existante);
	}
	
	@DeleteMapping("/match/{matchId}/participations/{joueurId}")
	public String deleteParticipation(@PathVariable Long matchId,
            						  @PathVariable Long joueurId) {
		verifExistanceMatchJoueur(matchId, joueurId);
		Match match = matchRepository.findById(matchId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match introuvable"));
	    if (!match.matchAvenir()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un joueur ne peut etre retirer d'un match qu'il a déjà joué");
		}
		participationRepository.deleteById(new ParticipationId(matchId, joueurId));
	    return "La participation du joueur d'id " + joueurId + " au match d'id "+ matchId + " a été supprimé avec succès";
	}
	
	
}
