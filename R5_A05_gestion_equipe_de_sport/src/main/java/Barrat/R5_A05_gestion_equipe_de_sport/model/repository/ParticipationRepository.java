package Barrat.R5_A05_gestion_equipe_de_sport.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Barrat.R5_A05_gestion_equipe_de_sport.model.Participation;
import Barrat.R5_A05_gestion_equipe_de_sport.model.Participation.ParticipationId;

public interface ParticipationRepository extends JpaRepository<Participation, ParticipationId> {
	List<Participation> findByIdMatchId(Long matchId);
    List<Participation> findByIdJoueurId(Long joueurId);
}
