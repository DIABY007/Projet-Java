package com.ebiblio.model;

import java.time.LocalDate;

public class Livre {
    private String titre;
    private TypeLivre type;
    private String auteur;
    private String editeur;
    private LocalDate dateEdition;
    private String codeUnique;
    private int nombreExemplaires;

    public Livre(String titre, TypeLivre type, String auteur, String editeur, LocalDate dateEdition, String codeUnique, int nombreExemplaires) {
        this.titre = titre;
        this.type = type;
        this.auteur = auteur;
        this.editeur = editeur;
        this.dateEdition = dateEdition;
        this.codeUnique = codeUnique;
        this.nombreExemplaires = nombreExemplaires;
    }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public TypeLivre getType() { return type; }
    public void setType(TypeLivre type) { this.type = type; }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public String getEditeur() { return editeur; }
    public void setEditeur(String editeur) { this.editeur = editeur; }

    public LocalDate getDateEdition() { return dateEdition; }
    public void setDateEdition(LocalDate dateEdition) { this.dateEdition = dateEdition; }

    public String getCodeUnique() { return codeUnique; }
    public void setCodeUnique(String codeUnique) { this.codeUnique = codeUnique; }

    public int getNombreExemplaires() { return nombreExemplaires; }
    public void setNombreExemplaires(int nombreExemplaires) { this.nombreExemplaires = nombreExemplaires; }

    @Override
    public String toString() {
        return "[Code: " + codeUnique + "] " + titre + " - " + auteur + " (" + type + ")";
    }
}
