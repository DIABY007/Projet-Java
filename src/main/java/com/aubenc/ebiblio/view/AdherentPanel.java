package com.aubenc.ebiblio.view;

import com.aubenc.ebiblio.model.Adherent;
import com.aubenc.ebiblio.service.GestionBibliotheque;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdherentPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField nomField, prenomField, idField;
    private JComboBox<String> typeBox, filtreTypeBox;
    private JButton ajouterBtn, modifierBtn, supprimerBtn, retardBtn;
    private GestionBibliotheque gestion;

    public AdherentPanel(GestionBibliotheque gestion) {
        this.gestion = gestion;
        setLayout(new BorderLayout());

        // Filtre par type en haut
        JPanel filtrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtreTypeBox = new JComboBox<>(new String[]{"TOUS", "ETUDIANT", "ENSEIGNANT", "VISITEUR"});
        filtrePanel.add(new JLabel("Filtrer par type : "));
        filtrePanel.add(filtreTypeBox);
        retardBtn = new JButton("Afficher les retardataires");
        filtrePanel.add(retardBtn);
        add(filtrePanel, BorderLayout.NORTH);

        // Table avec toutes les colonnes demandées
        model = new DefaultTableModel(new Object[]{
            "ID", "Nom", "Prénom", "Type", "Adresse", "Emprunts effectués", "Emprunts en cours", "Emprunts dépassés"
        }, 0);
        table = new JTable(model);
        rafraichirTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        ajouterBtn = new JButton("Ajouter");
        modifierBtn = new JButton("Modifier");
        supprimerBtn = new JButton("Supprimer");
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionsPanel.add(ajouterBtn);
        actionsPanel.add(modifierBtn);
        actionsPanel.add(supprimerBtn);
        add(actionsPanel, BorderLayout.SOUTH);

        // Listeners
        ajouterBtn.addActionListener(e -> {
            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Ajouter un adhérent", true);
            JPanel panel = new JPanel(new GridLayout(5, 1, 8, 8));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            JTextField nomField = new JTextField();
            JTextField prenomField = new JTextField();
            JTextField adresseField = new JTextField();
            JComboBox<String> typeBox = new JComboBox<>(new String[]{"ETUDIANT", "ENSEIGNANT", "VISITEUR"});
            panel.add(new JLabel("Nom"));
            panel.add(nomField);
            panel.add(new JLabel("Prénom"));
            panel.add(prenomField);
            panel.add(new JLabel("Adresse"));
            panel.add(adresseField);
            panel.add(new JLabel("Type"));
            panel.add(typeBox);
            JButton validerBtn = new JButton("Valider");
            panel.add(validerBtn);
            dialog.add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            validerBtn.addActionListener(ev -> {
                try {
                    Adherent a = new Adherent(nomField.getText(), prenomField.getText(), null, adresseField.getText(), com.aubenc.ebiblio.model.TypeAdherent.valueOf((String)typeBox.getSelectedItem()));
                    gestion.ajouterAdherent(a);
                    rafraichirTable();
                    JOptionPane.showMessageDialog(this, "Adhérent ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Erreur lors de l'ajout : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });
            dialog.setVisible(true);
        });
        modifierBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                String nom = String.valueOf(model.getValueAt(row, 1));
                String prenom = String.valueOf(model.getValueAt(row, 2));
                String type = String.valueOf(model.getValueAt(row, 3));
                String adresse = String.valueOf(model.getValueAt(row, 4));
                JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Modifier un adhérent", true);
                JPanel panel = new JPanel(new GridLayout(6, 1, 8, 8));
                panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
                JTextField idField = new JTextField(String.valueOf(id));
                idField.setEditable(false);
                JTextField nomField = new JTextField(nom);
                JTextField prenomField = new JTextField(prenom);
                JTextField adresseField = new JTextField(adresse);
                JComboBox<String> typeBox = new JComboBox<>(new String[]{"ETUDIANT", "ENSEIGNANT", "VISITEUR"});
                typeBox.setSelectedItem(type);
                panel.add(new JLabel("ID"));
                panel.add(idField);
                panel.add(new JLabel("Nom"));
                panel.add(nomField);
                panel.add(new JLabel("Prénom"));
                panel.add(prenomField);
                panel.add(new JLabel("Adresse"));
                panel.add(adresseField);
                panel.add(new JLabel("Type"));
                panel.add(typeBox);
                JButton validerBtn = new JButton("Valider");
                panel.add(validerBtn);
                dialog.add(panel);
                dialog.pack();
                dialog.setLocationRelativeTo(this);
                validerBtn.addActionListener(ev -> {
                    try {
                        if (idField.getText() == null || idField.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(dialog, "Impossible de modifier : l'adhérent n'a pas d'ID.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Adherent a = new Adherent(id, nomField.getText(), prenomField.getText(), null, adresseField.getText(), com.aubenc.ebiblio.model.TypeAdherent.valueOf((String)typeBox.getSelectedItem()));
                        gestion.modifierAdherent(a);
                        rafraichirTable();
                        JOptionPane.showMessageDialog(this, "Adhérent modifié.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(dialog, "Erreur lors de la modification : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                });
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un adhérent à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        supprimerBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Object value = model.getValueAt(row, 0);
                if (value instanceof Integer) {
                    int id = (int) value;
                    try {
                        gestion.supprimerAdherent(id);
                        rafraichirTable();
                        JOptionPane.showMessageDialog(this, "Adhérent supprimé.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erreur lors de la suppression : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "ID adhérent invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un adhérent à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        filtreTypeBox.addActionListener(e -> rafraichirTable());
        retardBtn.addActionListener(e -> rafraichirTableRetardataires());
    }

    private void rafraichirTable() {
        model.setRowCount(0);
        List<Adherent> adherents;
        String filtre = (String) filtreTypeBox.getSelectedItem();
        if (filtre != null && !filtre.equals("TOUS")) {
            adherents = gestion.rechercherAdherentsParType(com.aubenc.ebiblio.model.TypeAdherent.valueOf(filtre));
        } else {
            adherents = gestion.getAdherents();
        }
        List<com.aubenc.ebiblio.model.FicheEmprunt> fiches = gestion.getEmprunts();
        java.time.LocalDate today = java.time.LocalDate.now();
        for (Adherent a : adherents) {
            int effectues = 0;
            int enCours = 0;
            int depasses = 0;
            for (com.aubenc.ebiblio.model.FicheEmprunt f : fiches) {
                if (f.getAdherent().getId() == a.getId()) {
                    effectues++;
                    // Si l'emprunt n'est pas rendu (pas de date de retour ou date de retour > date limite)
                    // On suppose que la fiche d'emprunt n'a pas de champ dateRetour, donc tous sont "en cours" jusqu'à suppression
                    enCours++;
                    if (today.isAfter(f.getDateLimite())) {
                        depasses++;
                    }
                }
            }
            model.addRow(new Object[]{
                a.getId(), a.getNom(), a.getPrenom(), a.getType(), a.getAdresse(),
                effectues, enCours, depasses
            });
        }
    }

    private void rafraichirTableRetardataires() {
        model.setRowCount(0);
        List<Adherent> adherents = gestion.getAdherents();
        for (Adherent a : adherents) {
            if (a.getNbEmpruntsDepasses() > 0) {
                model.addRow(new Object[]{
                    a.getIdUnique(), a.getNom(), a.getPrenom(), a.getType(), a.getAdresse(),
                    a.getNbEmpruntsEffectues(), a.getNbEmpruntsEnCours(), a.getNbEmpruntsDepasses()
                });
            }
        }
    }
}
