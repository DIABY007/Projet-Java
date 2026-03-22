package com.aubenc.ebiblio.model;

import java.time.LocalDate;

public class FicheEmprunt {
        private int id;
    private Adherent adherent;
    private Livre livre;
    private LocalDate dateDebut;
    private LocalDate dateLimite;

    public FicheEmprunt(int id, Adherent adherent, Livre livre, LocalDate dateDebut, LocalDate dateLimite) {
        this.id = id;
        this.adherent = adherent;
        this.livre = livre;
        this.dateDebut = dateDebut;
        this.dateLimite = dateLimite;
    }

    public FicheEmprunt(Adherent adherent, Livre livre, LocalDate dateDebut, LocalDate dateLimite) {
        this(-1, adherent, livre, dateDebut, dateLimite);
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // Getters and setters
    public Adherent getAdherent() { return adherent; }
    public void setAdherent(Adherent adherent) { this.adherent = adherent; }
    public Livre getLivre() { return livre; }
    public void setLivre(Livre livre) { this.livre = livre; }
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public LocalDate getDateLimite() { return dateLimite; }
    public void setDateLimite(LocalDate dateLimite) { this.dateLimite = dateLimite; }
}
