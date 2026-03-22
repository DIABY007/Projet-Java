package com.ebiblio.main;

import com.ebiblio.model.*;
import com.ebiblio.service.Bibliotheque;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Bibliotheque biblio = new Bibliotheque();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\n--- Menu eBiblio ---");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Inscrire un adhérent");
            System.out.println("3. Réaliser un emprunt");
            System.out.println("4. Afficher inventaire livres");
            System.out.println("5. Afficher inventaire adhérents");
            System.out.println("6. Afficher statistiques emprunts");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine();
            switch (choix) {
                case 1:
                    System.out.print("Titre : ");
                    String titre = scanner.nextLine();
                    System.out.print("Type (SCIENTIFIQUE/LITTERAIRE) : ");
                    TypeLivre typeLivre = TypeLivre.valueOf(scanner.nextLine().toUpperCase());
                    System.out.print("Auteur : ");
                    String auteur = scanner.nextLine();
                    System.out.print("Éditeur : ");
                    String editeur = scanner.nextLine();
                    System.out.print("Date d'édition (AAAA-MM-JJ) : ");
                    LocalDate dateEdition = LocalDate.parse(scanner.nextLine());
                    System.out.print("Code unique : ");
                    String codeUnique = scanner.nextLine();
                    System.out.print("Nombre d'exemplaires : ");
                    int nbExemplaires = scanner.nextInt();
                    scanner.nextLine();
                    Livre livre = new Livre(titre, typeLivre, auteur, editeur, dateEdition, codeUnique, nbExemplaires);
                    biblio.ajouterLivre(livre);
                    System.out.println("Livre ajouté.");
                    break;
                case 2:
                    System.out.print("Nom : ");
                    String nom = scanner.nextLine();
                    System.out.print("Prénom : ");
                    String prenom = scanner.nextLine();
                    System.out.print("Identifiant unique : ");
                    String identifiant = scanner.nextLine();
                    System.out.print("Adresse : ");
                    String adresse = scanner.nextLine();
                    System.out.print("Type (ETUDIANT/ENSEIGNANT/VISITEUR) : ");
                    TypeAdherent typeAdherent = TypeAdherent.valueOf(scanner.nextLine().toUpperCase());
                    Adherent adherent = new Adherent(nom, prenom, identifiant, adresse, typeAdherent);
                    biblio.ajouterAdherent(adherent);
                    System.out.println("Adhérent inscrit.");
                    break;
                case 3:
                    System.out.print("Identifiant adhérent : ");
                    String idAdh = scanner.nextLine();
                    Adherent adh = null;
                    for (Adherent a : biblio.getAdherents()) {
                        if (a.getIdentifiantUnique().equals(idAdh)) {
                            adh = a;
                            break;
                        }
                    }
                    if (adh == null) {
                        System.out.println("Adhérent non trouvé.");
                        break;
                    }
                    if (!adh.peutEmprunter()) {
                        System.out.println("Erreur : Nombre maximal d'emprunts atteint ou retard existant.");
                        break;
                    }
                    System.out.print("Code unique livre : ");
                    String codeLivre = scanner.nextLine();
                    Livre lv = null;
                    for (Livre l : biblio.getLivres()) {
                        if (l.getCodeUnique().equals(codeLivre)) {
                            lv = l;
                            break;
                        }
                    }
                    if (lv == null) {
                        System.out.println("Livre non trouvé.");
                        break;
                    }
                    LocalDate dateDebut = LocalDate.now();
                    LocalDate dateLimite = FicheEmprunt.calculerDateLimiteRestitution(adh, dateDebut);
                    FicheEmprunt fiche = new FicheEmprunt(adh, lv, dateDebut, dateLimite);
                    biblio.ajouterFicheEmprunt(fiche);
                    adh.setNbEmpruntsEnCours(adh.getNbEmpruntsEnCours() + 1);
                    adh.setNbEmpruntsEffectues(adh.getNbEmpruntsEffectues() + 1);
                    System.out.println("Emprunt réalisé.");
                    break;
                case 4:
                    biblio.afficherInventaireLivres();
                    break;
                case 5:
                    biblio.afficherInventaireAdherents();
                    break;
                case 6:
                    biblio.afficherStatistiquesEmprunts();
                    break;
                case 0:
                    running = false;
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
        scanner.close();
    }
}
