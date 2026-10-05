package Barrat.R5_A05_gestion_equipe_de_sport.model;

import java.sql.Date;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@JsonPropertyOrder({"idCommentaire", "description", "dateAvis"})
public class Commentaire {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCommentaire ;
	
	@NotBlank(message = "La description du commentaire est obligatoire <description>")
	@Size(max = 500, message = "La description ne doit pas dépasser 500 caractères <description>")
	@Column(length = 500)
	private String description;
    
    private Date dateAvis;
    
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

	public Date getDateAvis() {
		return dateAvis;
	}

	public void setDateAvis(Date dateAvis) {
		this.dateAvis = dateAvis;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	public Long getIdCommentaire() {
		return idCommentaire;
	}
	
	public void setIdCommentaire(Long idCommentaire) {
		this.idCommentaire =  idCommentaire;
	}
    
}

