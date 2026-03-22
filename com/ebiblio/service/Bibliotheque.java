package com.ebiblio.service;

import com.ebiblio.model.Livre;
import com.ebiblio.model.Adherent;
import com.ebiblio.model.FicheEmprunt;
import com.ebiblio.model.TypeAdherent;
import com.ebiblio.model.TypeLivre;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;
import java.time.LocalDate;

public class Bibliotheque {
    private List<Livre> livres;
    private List<Adherent> adherents;
    private List<FicheEmprunt> fichesEmprunt;

    public Bibliotheque() {
        livres = new ArrayList<>();
        adherents = new ArrayList<>();
        fichesEmprunt = new ArrayList<>();
    }

    // Ajout de livre
    public void ajouterLivre(Livre livre) {
        livres.add(livre);
        Collections.sort(livres, Comparator.comparing(Livre::getCodeUnique));
    }

    // Ajout d'adhérent
    public void ajouterAdherent(Adherent adherent) {
        adherents.add(adherent);
        Collections.sort(adherents, Comparator.comparing(Adherent::getIdentifiantUnique));
    }

    // Suppression d'adhérent par identifiant
    public boolean supprimerAdherent(String identifiant) {
        Iterator<Adherent> it = adherents.iterator();
        while (it.hasNext()) {
            Adherent a = it.next();
            if (a.getIdentifiantUnique().equals(identifiant)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    // Suppression de livre par code unique
    public boolean supprimerLivre(String codeUnique) {
        Iterator<Livre> it = livres.iterator();
        while (it.hasNext()) {
            Livre l = it.next();
            if (l.getCodeUnique().equals(codeUnique)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    // Modification d'adhérent par identifiant
    public boolean modifierAdherent(String identifiant, Adherent nouvelAdherent) {
        for (int i = 0; i < adherents.size(); i++) {
            if (adherents.get(i).getIdentifiantUnique().equals(identifiant)) {
                adherents.set(i, nouvelAdherent);
                return true;
            }
        }
        return false;
    }

    // Modification de livre par code unique
    public boolean modifierLivre(String codeUnique, Livre nouveauLivre) {
        for (int i = 0; i < livres.size(); i++) {
            if (livres.get(i).getCodeUnique().equals(codeUnique)) {
                livres.set(i, nouveauLivre);
                return true;
            }
        }
        return false;
    }

    // Recherche d'adhérents par type
    public List<Adherent> rechercherAdherentsParType(TypeAdherent type) {
        List<Adherent> result = new ArrayList<>();
        for (Adherent a : adherents) {
            if (a.getType() == type) {
                result.add(a);
            }
        }
        return result;
    }

    // Recherche de livres par type
    public List<Livre> rechercherLivresParType(TypeLivre type) {
        List<Livre> result = new ArrayList<>();
        for (Livre l : livres) {
            if (l.getType() == type) {
                result.add(l);
            }
        }
        return result;
    }

    // Ajout d'une fiche d'emprunt
    public void ajouterFicheEmprunt(FicheEmprunt fiche) {
        fichesEmprunt.add(fiche);
    }

    // Vérification des retards
    public void verifierRetards() {
        for (FicheEmprunt fiche : fichesEmprunt) {
            if (fiche.getDateLimiteRestitution().isBefore(LocalDate.now())) {
                fiche.getAdherent().setNbEmpruntsDepasses(
                    fiche.getAdherent().getNbEmpruntsDepasses() + 1
                );
            }
        }
    }

    // Getters
    public List<Livre> getLivres() { return livres; }
    public List<Adherent> getAdherents() { return adherents; }
    public List<FicheEmprunt> getFichesEmprunt() { return fichesEmprunt; }

        // Méthode d'inventaire : liste complète des livres
        public void afficherInventaireLivres() {
            System.out.println("Inventaire des livres :");
            for (Livre l : livres) {
                System.out.println(l);
            }
        }

        // Méthode d'inventaire : liste complète des adhérents
        public void afficherInventaireAdherents() {
            System.out.println("Inventaire des adhérents :");
            for (Adherent a : adherents) {
                System.out.println(a);
            }
        }

        // Statistiques : nombre total d'emprunts effectués pour chaque livre
        public void afficherStatistiquesEmprunts() {
            System.out.println("Statistiques d'utilisation des livres :");
            for (Livre l : livres) {
                int total = 0;
                for (FicheEmprunt f : fichesEmprunt) {
                    if (f.getLivre().getCodeUnique().equals(l.getCodeUnique())) {
                        total++;
                    }
                }
                System.out.println(l + " | Emprunts effectués : " + total);
            }
        }
}
