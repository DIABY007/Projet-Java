package com.ebiblio.model;

public class Adherent {
    private String nom;
    private String prenom;
    private String identifiantUnique;
    private String adresse;
    private TypeAdherent type;
    private int nbEmpruntsEffectues;
    private int nbEmpruntsEnCours;
    private int nbEmpruntsDepasses;

    public Adherent(String nom, String prenom, String identifiantUnique, String adresse, TypeAdherent type) {
        this.nom = nom;
        this.prenom = prenom;
        this.identifiantUnique = identifiantUnique;
        this.adresse = adresse;
        this.type = type;
        this.nbEmpruntsEffectues = 0;
        this.nbEmpruntsEnCours = 0;
        this.nbEmpruntsDepasses = 0;
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getIdentifiantUnique() { return identifiantUnique; }
    public void setIdentifiantUnique(String identifiantUnique) { this.identifiantUnique = identifiantUnique; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public TypeAdherent getType() { return type; }
    public void setType(TypeAdherent type) { this.type = type; }

    public int getNbEmpruntsEffectues() { return nbEmpruntsEffectues; }
    public void setNbEmpruntsEffectues(int nbEmpruntsEffectues) { this.nbEmpruntsEffectues = nbEmpruntsEffectues; }

    public int getNbEmpruntsEnCours() { return nbEmpruntsEnCours; }
    public void setNbEmpruntsEnCours(int nbEmpruntsEnCours) { this.nbEmpruntsEnCours = nbEmpruntsEnCours; }

    public int getNbEmpruntsDepasses() { return nbEmpruntsDepasses; }
    public void setNbEmpruntsDepasses(int nbEmpruntsDepasses) { this.nbEmpruntsDepasses = nbEmpruntsDepasses; }
        /**
         * Vérifie si l'adhérent peut emprunter un livre selon les règles métier.
         * @return true si l'emprunt est possible, false sinon
         */
        public boolean peutEmprunter() {
            int maxEmprunts;
            switch (type) {
                case ETUDIANT:
                    maxEmprunts = 2;
                    break;
                case ENSEIGNANT:
                    maxEmprunts = 4;
                    break;
                case VISITEUR:
                    maxEmprunts = 1;
                    break;
                default:
                    maxEmprunts = 0;
            }
            if (nbEmpruntsEnCours >= maxEmprunts) {
                return false;
            }
            if (nbEmpruntsDepasses > 0) {
                return false;
            }
            return true;
        }

    @Override
    public String toString() {
        return "[ID: " + identifiantUnique + "] " + nom + " " + prenom + " - " + type + ", Adresse: " + adresse;
    }
}
