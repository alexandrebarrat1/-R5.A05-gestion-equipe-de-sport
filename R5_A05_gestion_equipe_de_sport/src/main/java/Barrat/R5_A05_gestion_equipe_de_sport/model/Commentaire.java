package Barrat.R5_A05_gestion_equipe_de_sport.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Commentaire {
	
	@Id
	private int idCommentaire;
    private String description;
    private String dateAvis;
    
    @ManyToOne
    @JoinColumn(name = "joueur_id")
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

