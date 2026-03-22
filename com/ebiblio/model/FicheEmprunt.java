package com.ebiblio.model;

import java.time.LocalDate;

public class FicheEmprunt {
    private Adherent adherent;
    private Livre livre;
    private LocalDate dateDebut;
    private LocalDate dateLimiteRestitution;

    public FicheEmprunt(Adherent adherent, Livre livre, LocalDate dateDebut, LocalDate dateLimiteRestitution) {
        this.adherent = adherent;
        this.livre = livre;
        this.dateDebut = dateDebut;
        this.dateLimiteRestitution = dateLimiteRestitution;
    }

    public Adherent getAdherent() { return adherent; }
    public void setAdherent(Adherent adherent) { this.adherent = adherent; }

    public Livre getLivre() { return livre; }
    public void setLivre(Livre livre) { this.livre = livre; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateLimiteRestitution() { return dateLimiteRestitution; }
    public void setDateLimiteRestitution(LocalDate dateLimiteRestitution) { this.dateLimiteRestitution = dateLimiteRestitution; }

        /**
         * Calcule la date limite de restitution selon le type d'adhérent.
         * @param adherent L'adhérent concerné
         * @param dateDebut La date de début de l'emprunt
         * @return La date limite de restitution
         */
        public static LocalDate calculerDateLimiteRestitution(Adherent adherent, LocalDate dateDebut) {
            switch (adherent.getType()) {
                case ENSEIGNANT:
                    return dateDebut.plusWeeks(3);
                case ETUDIANT:
                case VISITEUR:
                    return dateDebut.plusWeeks(1);
                default:
                    return dateDebut;
            }
        }
    @Override
    public String toString() {
        return "Emprunt: " + adherent + " | Livre: " + livre + " | Début: " + dateDebut + " | Limite: " + dateLimiteRestitution;
    }
}
