package Barrat.R5_A05_gestion_equipe_de_sport.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Barrat.R5_A05_gestion_equipe_de_sport.model.Commentaire;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
}
