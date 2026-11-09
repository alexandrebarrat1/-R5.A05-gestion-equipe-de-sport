package Barrat.R5_A05_gestion_equipe_de_sport.model.dto;

import Barrat.R5_A05_gestion_equipe_de_sport.model.Joueur;
import Barrat.R5_A05_gestion_equipe_de_sport.model.Match;
import Barrat.R5_A05_gestion_equipe_de_sport.model.Participation;
import Barrat.R5_A05_gestion_equipe_de_sport.model.Poste;
import jakarta.validation.constraints.NotNull;

public record ParticipationCreateDto(@NotNull Poste poste) {
	public Participation toEntity(Joueur joueur, Match match) {
        Participation p = new Participation();
        p.setPoste(poste);
        p.setJoueur(joueur);
        p.setMatch(match);
        return p;
    }
}
