package Barrat.R5_A05_gestion_equipe_de_sport.vue;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import Barrat.R5_A05_gestion_equipe_de_sport.model.Commentaire;
import Barrat.R5_A05_gestion_equipe_de_sport.model.Joueur;
import Barrat.R5_A05_gestion_equipe_de_sport.model.repository.CommentaireRepository;
import Barrat.R5_A05_gestion_equipe_de_sport.model.repository.JoueurRepository;
import jakarta.validation.Valid;

@RestController
public class ApiCommentaire {
	public static final ResponseStatusException THROW_ID_INTROUVABLE = new ResponseStatusException(HttpStatus.NOT_FOUND, "Commentaire introuvable");


	@Autowired
	private CommentaireRepository commentaireRepository;
	
	@Autowired
	private JoueurRepository joueurRepository;
	
	@PostMapping("/joueur/{idJoueur}/commentaire")
	public Commentaire postCommentaire(@PathVariable Long idJoueur,
	                                   @Valid @RequestBody Commentaire commentaire) {
	    Joueur joueur = joueurRepository.findById(idJoueur)
	            .orElseThrow(() -> ApiJoueur.THROW_ID_INTROUVABLE);

	    commentaire.setIdCommentaire(null);
	    commentaire.setDateAvis(Date.valueOf(LocalDate.now()));
	    commentaire.setJoueur(joueur);

	    return commentaireRepository.save(commentaire);
	}
	
	@GetMapping("/commentaire")
    public List<Commentaire> getAll() {
		
        return commentaireRepository.findAll();
    }
	
	@GetMapping("/commentaire/{id}")
    public Commentaire getOne(@PathVariable Long id) {
		this.assertCommentaireExiste(id);
        return commentaireRepository.findById(id).orElseThrow(() -> THROW_ID_INTROUVABLE) ;
    }
	
	@DeleteMapping("/commentaire/{id}")
	public String deleteCommentaire(@PathVariable Long id) {
		this.assertCommentaireExiste(id);
		commentaireRepository.deleteById(id);
	    return "Commentaire d'id " + id + " a été supprimé avec succès";
	}
	
	private void assertCommentaireExiste(Long id) {
		if ( id ==  null || !commentaireRepository.existsById(id)) {
	        throw THROW_ID_INTROUVABLE;
	    }
	}
	
}
