package Barrat.R5_A05_gestion_equipe_de_sport.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "matchs")
@JsonPropertyOrder({"idMatch", "dateMatch", "nomEquipeAdverse", "aDomicile", "adresse", "resultat"})
public class Match {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idMatch;
	
	@NotNull(message = "La date du match est obligatoire <dateMatch> format : \"AAAA-MM-JJTHH:MM\"")
	private LocalDateTime dateMatch;

	@NotBlank(message = "Le nom de l'equipe adverse est obligatoire <nomEquipeAdverse>")
	@Size(max = 500, message = "Le nom de l'equipe adverse ne doit pas dépasser 500 caractères <nomEquipeAdverse>")
	@Column(length = 500)
    private String nomEquipeAdverse;
    
	@NotNull(message = "L'info sur si le match se joue à domicile est obligatoire <aDomicile>")
    private Boolean aDomicile;
    
    @NotBlank(message = "L'adresse est obligatoire <adresse>")
	@Size(max = 500, message = "L'adresse ne doit pas dépasser 500 caractères <adresse>")
	@Column(length = 500)
    private String adresse;
    
    private Resultat resultat;


    public Long getIdMatch() {
		return idMatch;
	}

	public void setIdMatch(Long idMatch) {
		this.idMatch = idMatch;
	}

	public LocalDateTime getDateMatch() {
		return dateMatch;
	}

	public void setDateMatch(LocalDateTime dateMatch) {
		this.dateMatch = dateMatch;
	}

	public String getNomEquipeAdverse() {
		return nomEquipeAdverse;
	}

	public void setNomEquipeAdverse(String nomEquipeAdverse) {
		this.nomEquipeAdverse = nomEquipeAdverse;
	}

	public Boolean isADomicile() {
		return aDomicile;
	}

	public void setADomicile(Boolean aDomicile) {
		this.aDomicile = aDomicile;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public Resultat getResultat() {
		return resultat;
	}

	public void setResultat(Resultat resultat) {
		this.resultat = resultat;
	}
	
	public boolean matchAvenir() {
	    return this.dateMatch.isAfter(LocalDateTime.now());
	}
}
