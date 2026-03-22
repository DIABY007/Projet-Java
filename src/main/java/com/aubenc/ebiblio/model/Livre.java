package com.aubenc.ebiblio.model;

import java.time.LocalDate;

public class Livre {
    private int id;
    private String titre;
    private String auteur;
    private String editeur;
    private LocalDate dateEdition;
    private String codeUnique;
    private int nbExemplaires;
    private TypeLivre type;

    public Livre(int id, String titre, String auteur, String editeur, LocalDate dateEdition, String codeUnique, int nbExemplaires, TypeLivre type) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.editeur = editeur;
        this.dateEdition = dateEdition;
        this.codeUnique = codeUnique;
        this.nbExemplaires = nbExemplaires;
        this.type = type;
    }
    public Livre(String titre, String auteur, String editeur, LocalDate dateEdition, String codeUnique, int nbExemplaires, TypeLivre type) {
        this(-1, titre, auteur, editeur, dateEdition, codeUnique, nbExemplaires, type);
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // Getters and setters
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }
    public String getEditeur() { return editeur; }
    public void setEditeur(String editeur) { this.editeur = editeur; }
    public LocalDate getDateEdition() { return dateEdition; }
    public void setDateEdition(LocalDate dateEdition) { this.dateEdition = dateEdition; }
    public String getCodeUnique() { return codeUnique; }
    public void setCodeUnique(String codeUnique) { this.codeUnique = codeUnique; }
    public int getNbExemplaires() { return nbExemplaires; }
    public void setNbExemplaires(int nbExemplaires) { this.nbExemplaires = nbExemplaires; }
    public TypeLivre getType() { return type; }
    public void setType(TypeLivre type) { this.type = type; }
}
