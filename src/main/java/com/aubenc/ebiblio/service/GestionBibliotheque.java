package com.aubenc.ebiblio.service;

import com.aubenc.ebiblio.model.*;
import com.aubenc.ebiblio.model.Adherent;
import com.aubenc.ebiblio.model.Livre;
import com.aubenc.ebiblio.dao.*;
import com.aubenc.ebiblio.config.DatabaseConnection;
import java.util.ArrayList;
import java.util.List;

public class GestionBibliotheque {
        public void supprimerEmprunt(int id) {
            empruntDAO.supprimer(id);
        }
    private com.aubenc.ebiblio.dao.LivreDAO livreDAO = new com.aubenc.ebiblio.dao.LivreDAOImpl();
    private com.aubenc.ebiblio.dao.AdherentDAO adherentDAO = new com.aubenc.ebiblio.dao.AdherentDAOImpl();
    private com.aubenc.ebiblio.dao.EmpruntDAO empruntDAO = new com.aubenc.ebiblio.dao.EmpruntDAOImpl();

    public void ajouterAdherent(Adherent adherent) {
        adherentDAO.ajouter(adherent);
    }

    public void ajouterLivre(Livre livre) {
        livreDAO.ajouter(livre);
    }

    public void ajouterEmprunt(FicheEmprunt fiche) {
        // Vérification retard
        java.time.LocalDate today = java.time.LocalDate.now();
        for (FicheEmprunt e : getEmprunts()) {
            if (e.getAdherent().getId() == fiche.getAdherent().getId() && today.isAfter(e.getDateLimite())) {
                throw new IllegalStateException("Cet adhérent a un emprunt en retard et ne peut pas emprunter tant que sa situation n'est pas régularisée.");
            }
        }
        int max = getMaxLivres(fiche.getAdherent().getType());
        int enCours = 0;
        for (FicheEmprunt e : getEmprunts()) {
            if (e.getAdherent().getId() == fiche.getAdherent().getId()) {
                enCours++;
            }
        }
        if (enCours >= max) {
            throw new IllegalStateException("Limite d'emprunts atteinte pour cet adhérent.");
        }
        empruntDAO.ajouter(fiche);
    }

    public boolean supprimerAdherent(int id) {
        adherentDAO.supprimer(String.valueOf(id));
        return true;
    }

    public boolean supprimerLivre(int id) {
        livreDAO.supprimer(id);
        return true;
    }

    public boolean modifierAdherent(Adherent modif) {
        adherentDAO.modifier(modif);
        return true;
    }

    public boolean modifierLivre(Livre modif) {
        livreDAO.modifier(modif);
        return true;
    }

    public List<Livre> rechercherLivresParType(TypeLivre type) {
        List<Livre> tous = livreDAO.listerTous();
        List<Livre> result = new ArrayList<>();
        for (Livre l : tous) {
            if (l.getType() == type) result.add(l);
        }
        return result;
    }

    public List<Adherent> rechercherAdherentsParType(TypeAdherent type) {
        List<Adherent> tous = adherentDAO.listerTous();
        List<Adherent> result = new ArrayList<>();
        for (Adherent a : tous) {
            if (a.getType() == type) result.add(a);
        }
        return result;
    }

    // Règles métier
    public int getMaxLivres(TypeAdherent type) {
        switch (type) {
            case ETUDIANT: return 2;
            case ENSEIGNANT: return 4;
            case VISITEUR: return 1;
            default: return 0;
        }
    }

    public int getDureeEmprunt(TypeAdherent type) {
        switch (type) {
            case ENSEIGNANT: return 21;
            case ETUDIANT:
            case VISITEUR: return 7;
            default: return 0;
        }
    }

    public List<FicheEmprunt> getRetards() {
        List<FicheEmprunt> retards = new ArrayList<>();
        String sql = "SELECT * FROM Emprunts WHERE date_limite < CURDATE() AND (date_retour IS NULL OR date_retour > date_limite)";
        try (java.sql.Connection conn = com.aubenc.ebiblio.config.DatabaseConnection.getInstance().getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
             java.sql.ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                AdherentDAO adherentDAO = new AdherentDAOImpl();
                LivreDAO livreDAO = new LivreDAOImpl();
                Adherent adherent = adherentDAO.trouverParIdUnique(rs.getString("adherent_idUnique"));
                Livre livre = livreDAO.trouverParCodeUnique(rs.getString("livre_codeUnique"));
                retards.add(new FicheEmprunt(
                    adherent,
                    livre,
                    rs.getDate("date_emprunt").toLocalDate(),
                    rs.getDate("date_limite").toLocalDate()
                ));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return retards;
    }

    // Inventaires
    public List<Livre> getLivres() { return livreDAO.listerTous(); }
    public List<Adherent> getAdherents() { return adherentDAO.listerTous(); }
    public List<FicheEmprunt> getEmprunts() { return empruntDAO.listerTous(); }
}
