package com.aubenc.ebiblio.view;

import com.aubenc.ebiblio.model.FicheEmprunt;
import com.aubenc.ebiblio.service.GestionBibliotheque;
import com.aubenc.ebiblio.model.Adherent;
import com.aubenc.ebiblio.model.Livre;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmpruntPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private GestionBibliotheque gestion;
    private JButton retardBtn, validerBtn;
    private JComboBox<String> adherentBox, livreBox;
    private java.util.Map<String, Adherent> adherentMap = new java.util.HashMap<>();
    private java.util.Map<String, Livre> livreMap = new java.util.HashMap<>();

    public EmpruntPanel(GestionBibliotheque gestion) {
        this.gestion = gestion;
        setLayout(new BorderLayout());

        // Bouton pour afficher les emprunts en retard
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        retardBtn = new JButton("Afficher les emprunts en retard");
        topPanel.add(retardBtn);
        add(topPanel, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel(new Object[]{"Adhérent", "Livre", "Date Emprunt", "Date Limite"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Formulaire de sélection en bas
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton ajouterBtn = new JButton("Ajouter un emprunt");
        JButton retourBtn = new JButton("Enregistrer le retour");
        formPanel.add(ajouterBtn);
        formPanel.add(retourBtn);
        add(formPanel, BorderLayout.SOUTH);

        // Remplir les JComboBox et la table
        rafraichirTable();

        retardBtn.addActionListener(e -> rafraichirTableRetard());
        ajouterBtn.addActionListener(e -> {
            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Ajouter un emprunt", true);
            JPanel panel = new JPanel(new GridLayout(5, 1, 8, 8));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            JComboBox<String> adherentField = new JComboBox<>();
            JComboBox<String> livreField = new JComboBox<>();
            JTextField dateEmpruntField = new JTextField();
            JTextField dateLimiteField = new JTextField();
            // Remplir les combos avec UX améliorée
            adherentField.removeAllItems();
            livreField.removeAllItems();
            for (Adherent a : gestion.getAdherents()) {
                String display = a.getId() + " - " + a.getNom() + " " + a.getPrenom();
                adherentField.addItem(display);
                adherentMap.put(display, a);
            }
            for (Livre l : gestion.getLivres()) {
                String display = l.getCodeUnique() + " - " + l.getTitre();
                livreField.addItem(display);
                livreMap.put(display, l);
            }
            panel.add(new JLabel("Adhérent"));
            panel.add(adherentField);
            panel.add(new JLabel("Livre"));
            panel.add(livreField);
            panel.add(new JLabel("Date emprunt (AAAA-MM-JJ)"));
            panel.add(dateEmpruntField);
            panel.add(new JLabel("Date limite (AAAA-MM-JJ)"));
            panel.add(dateLimiteField);
            JButton validerDialogBtn = new JButton("Valider");
            panel.add(validerDialogBtn);
            dialog.add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            validerDialogBtn.addActionListener(ev -> {
                String adhDisplay = (String) adherentField.getSelectedItem();
                String livreDisplay = (String) livreField.getSelectedItem();
                String dateEmprunt = dateEmpruntField.getText();
                String dateLimite = dateLimiteField.getText();
                if (adhDisplay == null || livreDisplay == null) {
                    JOptionPane.showMessageDialog(dialog, "Veuillez sélectionner un adhérent et un livre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    java.time.LocalDate dateDebut = java.time.LocalDate.parse(dateEmprunt);
                    java.time.LocalDate dateFin = java.time.LocalDate.parse(dateLimite);
                    Adherent adherent = adherentMap.get(adhDisplay);
                    Livre livre = livreMap.get(livreDisplay);
                    gestion.ajouterEmprunt(new com.aubenc.ebiblio.model.FicheEmprunt(adherent, livre, dateDebut, dateFin));
                    rafraichirTable();
                    JOptionPane.showMessageDialog(this, "Emprunt ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } catch (java.time.format.DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(dialog, "Format de date invalide. Utilisez AAAA-MM-JJ.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Erreur lors de l'ajout : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });
            dialog.setVisible(true);
        });
        retourBtn.addActionListener(e -> enregistrerRetour());
    }

    /**
     * Enregistre le retour du livre sélectionné dans le tableau.
     */
    private void enregistrerRetour() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un emprunt à rendre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Récupère la fiche emprunt
        List<FicheEmprunt> emprunts = gestion.getEmprunts();
        FicheEmprunt fiche = emprunts.get(selectedRow);
        Livre livre = fiche.getLivre();
        Adherent adherent = fiche.getAdherent();
        java.time.LocalDate dateRetour = java.time.LocalDate.now();
        // Gestion du stock
        livre.setNbExemplaires(livre.getNbExemplaires() + 1);
        gestion.modifierLivre(livre);
        // Mise à jour adhérent
        adherent.setNbEmpruntsEnCours(Math.max(0, adherent.getNbEmpruntsEnCours() - 1));
        gestion.modifierAdherent(adherent);
        // Gestion du retard
        boolean enRetard = dateRetour.isAfter(fiche.getDateLimite());
        if (enRetard) {
            JOptionPane.showMessageDialog(this, "L'adhérent était en retard pour ce retour.", "Retard", JOptionPane.WARNING_MESSAGE);
        }
        // Suppression de l'emprunt
        gestion.supprimerEmprunt(fiche.getId());
        // Rafraîchissement
        rafraichirTable();
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame instanceof com.aubenc.ebiblio.view.MainFrame) {
            frame.repaint();
        }
        JOptionPane.showMessageDialog(this, "Retour enregistré avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
    }

    private void rafraichirTable() {
        model.setRowCount(0);
        List<FicheEmprunt> emprunts = gestion.getEmprunts();
        for (FicheEmprunt f : emprunts) {
            String adherentDisplay = f.getAdherent().getId() + " - " + f.getAdherent().getNom() + " " + f.getAdherent().getPrenom();
            String livreDisplay = f.getLivre().getId() + " - " + f.getLivre().getTitre();
            model.addRow(new Object[]{adherentDisplay, livreDisplay, f.getDateDebut(), f.getDateLimite()});
        }
    }

    private void rafraichirTableRetard() {
        model.setRowCount(0);
        List<FicheEmprunt> retards = gestion.getRetards();
        for (FicheEmprunt f : retards) {
            model.addRow(new Object[]{f.getAdherent().getId(), f.getLivre().getId(), f.getDateDebut(), f.getDateLimite()});
        }
        if (retards.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun emprunt en retard.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void validerEmprunt() {
        String adhDisplay = (String) adherentBox.getSelectedItem();
        String livreDisplay = (String) livreBox.getSelectedItem();
        if (adhDisplay == null || livreDisplay == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un adhérent et un livre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Adherent adherent = adherentMap.get(adhDisplay);
        Livre livre = livreMap.get(livreDisplay);
        if (adherent == null || livre == null) {
            JOptionPane.showMessageDialog(this, "Adhérent ou livre introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Vérification du quota
        int max = gestion.getMaxLivres(adherent.getType());
        if (adherent.getNbEmpruntsEnCours() >= max) {
            JOptionPane.showMessageDialog(this, "L'adhérent a atteint son quota d'emprunts.", "Blocage", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Vérification des retards
        if (adherent.getNbEmpruntsDepasses() > 0) {
            JOptionPane.showMessageDialog(this, "L'adhérent possède un ou plusieurs retards. Emprunt bloqué.", "Blocage", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Vérification du stock
        if (livre.getNbExemplaires() <= 0) {
            JOptionPane.showMessageDialog(this, "Aucun exemplaire disponible pour ce livre.", "Blocage", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Calcul de la date limite
        java.time.LocalDate dateDebut = java.time.LocalDate.now();
        int nbJours = gestion.getDureeEmprunt(adherent.getType());
        java.time.LocalDate dateLimite = dateDebut.plusDays(nbJours);
        // Création de la fiche d'emprunt
        FicheEmprunt fiche = new FicheEmprunt(adherent, livre, dateDebut, dateLimite);
        gestion.ajouterEmprunt(fiche);
        // Décrémenter le stock
        livre.setNbExemplaires(livre.getNbExemplaires() - 1);
        gestion.modifierLivre(livre);
        // Mettre à jour les compteurs adhérent
        adherent.setNbEmpruntsEnCours(adherent.getNbEmpruntsEnCours() + 1);
        adherent.setNbEmpruntsEffectues(adherent.getNbEmpruntsEffectues() + 1);
        gestion.modifierAdherent(adherent);
        rafraichirTable();
        // Rafraîchir le dashboard
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame instanceof com.aubenc.ebiblio.view.MainFrame) {
            frame.repaint();
        }
        JOptionPane.showMessageDialog(this, "Emprunt enregistré avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
    }
}
