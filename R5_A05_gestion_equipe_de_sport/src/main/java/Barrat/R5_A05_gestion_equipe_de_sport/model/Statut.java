package Barrat.R5_A05_gestion_equipe_de_sport.model;

public enum Statut {
    ACTIF("actif"),
    INACTIF("inactif"),
    SUSPENDU("suspendu");

    private final String value;

    Statut(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Statut stringEnStatut(String statut) {
        for (Statut s : Statut.values()) {
            if (s.value.equals(statut)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Vous n'avez pas donné un statut existant");
    }
}
