package Barrat.R5_A05_gestion_equipe_de_sport.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import Barrat.R5_A05_gestion_equipe_de_sport.model.Joueur;

public interface JoueurRepository extends JpaRepository<Joueur, Long> {
	boolean existsByNumeroLicence(String numeroLicence);
}
