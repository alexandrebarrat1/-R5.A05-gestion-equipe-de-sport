package Barrat.R5_A05_gestion_equipe_de_sport.vue;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Barrat.R5_A05_gestion_equipe_de_sport.model.Commentaire;
import Barrat.R5_A05_gestion_equipe_de_sport.model.repository.CommentaireRepository;
import jakarta.validation.Valid;

@RestController
public class ApiCommentaire {

	@Autowired
	private CommentaireRepository commentaireRepository;
	
	@PostMapping("/commentaire")
	public Commentaire postJoueur(@Valid @RequestBody Commentaire commentaire) {
		commentaire.setIdCommentaire(null);
		commentaire.setDateAvis(Date.valueOf(LocalDate.now()));
		
	    return commentaireRepository.save(commentaire);
	}
}
