package Barrat.R5_A05_gestion_equipe_de_sport.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;



@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"joueur_id", "match_id"}))
@JsonPropertyOrder({"id", "poste", "evaluation"})
public class Participation {
	
	@Embeddable
	public record ParticipationId(
	        @Column(name = "joueur_id") Long joueurId,
	        @Column(name = "match_id") Long matchId) implements Serializable {}

	@EmbeddedId
    private ParticipationId id;

    @ManyToOne(optional = false)
    @MapsId("joueurId")
    @JoinColumn(name = "joueur_id")
    @JsonIgnore
    private Joueur joueur;

    @ManyToOne(optional = false)
    @MapsId("matchId")
    @JoinColumn(name = "match_id")
    @JsonIgnore
    private Match match;

    @NotNull(message = "Le poste du joueur qui participe à un match doit être renseigné <poste>")
    @Enumerated(EnumType.STRING)
    private Poste poste;

    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Float evaluation;

	public ParticipationId getId() {
		return id;
	}

	public void setId(ParticipationId id) {
		this.id = id;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Poste getPoste() {
		return poste;
	}

	public void setPoste(Poste poste) {
		this.poste = poste;
	}

	public Float getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Float evaluation) {
		this.evaluation = evaluation;
	}

}
