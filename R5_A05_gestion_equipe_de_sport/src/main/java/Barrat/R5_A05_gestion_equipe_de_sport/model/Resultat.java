package Barrat.R5_A05_gestion_equipe_de_sport.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Resultat {
	VICTOIRE("Victoire"),
	NUL("Nul"),
    DEFAITE("Défaite");

    private final String value;

    Resultat(String value) {
        this.value = value;
    }
    
    @JsonValue
    public String getValue() {
        return value;
    }

    public static Resultat stringEnStatut(String statut) {
        for (Resultat s : Resultat.values()) {
            if (s.value.equals(statut)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Vous n'avez pas donné un resultat existant");
    }

}
