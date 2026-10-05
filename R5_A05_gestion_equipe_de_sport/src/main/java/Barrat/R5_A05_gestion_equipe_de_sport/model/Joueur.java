package Barrat.R5_A05_gestion_equipe_de_sport.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.Nullable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@JsonPropertyOrder({"idJoueur", "nom", "prenom", "dateNaissance", "numeroLicence", "taille", "poids", "statut"})
public class Joueur {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJoueur = null;
	
    
    @NotBlank(message = "Le nom est obligatoire <nom>")
    private String nom;
    
    @NotBlank(message = "Le prenom est obligatoire <prenom>")
    private String prenom;
    
    @NotNull(message = "La date de naissance est obligatoire <dateNaissance> (yyyy-mm-dd")
    private Date dateNaissance;
    
    @Positive(message = "La taille du joueur est obligatoire <taille>")
    private float taille;
    
    @Positive(message = "La poids du joueur est obligatoire <poids>")
    private float poids;
    
    @Column(unique = true)
    @NotBlank(message = "La numero de licence est obligatoire <numeroLicence>")
    private String numeroLicence;
    
    @NotNull(message = "La statut est obligatoire <statut>")
    private Statut statut;
    
    @OneToMany(mappedBy = "joueur", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Commentaire> commentaires = new ArrayList<>();

	public String getNumeroLicence() {
		return numeroLicence;
	}

	public void setNumeroLicence(String numeroLicence) {
		this.numeroLicence = numeroLicence;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public float getTaille() {
		return taille;
	}

	public void setTaille(float taille) {
		this.taille = taille;
	}

	public float getPoids() {
		return poids;
	}

	public void setPoids(float poids) {
		this.poids = poids;
	}

	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}
	
	
	public List<Commentaire> getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(List<Commentaire> commentaires) {
		this.commentaires = commentaires;
	}

	public Long getIdJoueur() {
		return idJoueur;
	}
	
	public void setIdJoueur(Long idJoueur) {
		this.idJoueur = idJoueur;
	}

}