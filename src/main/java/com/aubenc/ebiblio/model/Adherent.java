package com.aubenc.ebiblio.model;

public class Adherent {
    private int id;
    private String nom;
    private String prenom;
    private String idUnique;
    private String adresse;
    private TypeAdherent type;
    private int nbEmpruntsEffectues;
    private int nbEmpruntsEnCours;
    private int nbEmpruntsDepasses;

    public Adherent(int id, String nom, String prenom, String idUnique, String adresse, TypeAdherent type) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.idUnique = idUnique;
        this.adresse = adresse;
        this.type = type;
        this.nbEmpruntsEffectues = 0;
        this.nbEmpruntsEnCours = 0;
        this.nbEmpruntsDepasses = 0;
    }
    public Adherent(String nom, String prenom, String idUnique, String adresse, TypeAdherent type) {
        this(-1, nom, prenom, idUnique, adresse, type);
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // Getters and setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getIdUnique() { return idUnique; }
    public void setIdUnique(String idUnique) { this.idUnique = idUnique; }
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
}
