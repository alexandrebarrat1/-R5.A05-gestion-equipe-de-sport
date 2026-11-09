package Barrat.R5_A05_gestion_equipe_de_sport.model;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Poste {
	MENEUR("Meneur"),
	ARRIERE("Arrière"),
    AILIER("Ailier"),
	AILIERFORT("Ailier Fort"),
	PIVOT("Pivot"),
	REMPLACANT("Remplaçant");
	
	public static int MAX_REMPLACANT = 7;
	public static int MAX_JOUEURS = 12;

    private final String value;

    Poste(String value) {
        this.value = value;
    }
    
    @JsonValue
    public String getValue() {
        return value;
    }

    public static Poste stringEnStatut(String statut) {
        for (Poste s : Poste.values()) {
            if (s.value.equals(statut)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Vous n'avez pas donné un poste existant");
    }
    
    public enum StatutPoste {
        POSTE_LIBRE,
        POSTE_TITULAIRE_DEJA_PRIS,
        REMPLACANTS_COMPLETS,
        PLUS_DE_POSTE_VACANT
    }
    
    public StatutPoste verifierPoste(List<Poste> posteDejaPris) {
        if (posteDejaPris.size() >= MAX_JOUEURS) {
            return StatutPoste.PLUS_DE_POSTE_VACANT;
        }

        if (this == REMPLACANT) {
            return Collections.frequency(posteDejaPris, REMPLACANT) < MAX_REMPLACANT
                    ? StatutPoste.POSTE_LIBRE
                    : StatutPoste.REMPLACANTS_COMPLETS;
        }

        return posteDejaPris.contains(this)
                ? StatutPoste.POSTE_TITULAIRE_DEJA_PRIS
                : StatutPoste.POSTE_LIBRE;
    }
}
