package Barrat.R5_A05_gestion_equipe_de_sport.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Commentaire {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCommentaire;
    private String description;
    private String dateAvis;
    
    @ManyToOne
    @JoinColumn(name = "joueur_id")
    @JsonIgnore
    private Joueur joueur;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateAvis() {
		return dateAvis;
	}

	public void setDateAvis(String dateAvis) {
		this.dateAvis = dateAvis;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	public int getIdCommentaire() {
		return idCommentaire;
	}
    
}

