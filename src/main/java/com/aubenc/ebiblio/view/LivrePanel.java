package com.aubenc.ebiblio.view;

import com.aubenc.ebiblio.model.Livre;
import com.aubenc.ebiblio.service.GestionBibliotheque;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LivrePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField codeField, titreField, auteurField, editeurField, dateEditionField, nbExemplairesField;
    private JComboBox<String> typeBox, filtreTypeBox;
    private JButton ajouterBtn, modifierBtn, supprimerBtn;
    private GestionBibliotheque gestion;

    public LivrePanel(GestionBibliotheque gestion) {
        // ...existing code...
        this.gestion = gestion;
        setLayout(new BorderLayout());

        // Filtre par type en haut
        JPanel filtrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtreTypeBox = new JComboBox<>(new String[]{"TOUS", "SCIENTIFIQUE", "LITTERAIRE"});
        filtrePanel.add(new JLabel("Filtrer par type : "));
        filtrePanel.add(filtreTypeBox);
        add(filtrePanel, BorderLayout.NORTH);

        // Table avec toutes les colonnes demandées
        model = new DefaultTableModel(new Object[]{
            "ID", "Titre", "Auteur", "Editeur", "Date d'édition", "Code unique", "Nb exemplaires", "Type"
        }, 0);
        table = new JTable(model);
        rafraichirTable();
        add(new JScrollPane(table), BorderLayout.CENTER);
        // Remplir les champs lors de la sélection d'une ligne (après instanciation de table)
        table.getSelectionModel().addListSelectionListener(e -> {
            // Plus de remplissage des champs globaux, car ils sont locaux au JDialog
            // Sélection uniquement pour activer le bouton Modifier/Supprimer
        });

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
            JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Ajouter un livre", true);
            JPanel panel = new JPanel(new GridLayout(8, 1, 8, 8));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            JTextField codeField = new JTextField();
            JTextField titreField = new JTextField();
            JTextField auteurField = new JTextField();
            JTextField editeurField = new JTextField();
            JTextField dateEditionField = new JTextField();
            JTextField nbExemplairesField = new JTextField();
            JComboBox<String> typeBox = new JComboBox<>(new String[]{"SCIENTIFIQUE", "LITTERAIRE"});
            panel.add(new JLabel("Code unique"));
            panel.add(codeField);
            panel.add(new JLabel("Titre"));
            panel.add(titreField);
            panel.add(new JLabel("Auteur"));
            panel.add(auteurField);
            panel.add(new JLabel("Editeur"));
            panel.add(editeurField);
            panel.add(new JLabel("Date édition (AAAA-MM-JJ, ex: 2024-03-18)"));
            panel.add(dateEditionField);
            panel.add(new JLabel("Nb exemplaires"));
            panel.add(nbExemplairesField);
            panel.add(new JLabel("Type"));
            panel.add(typeBox);
            JButton validerBtn = new JButton("Valider");
            panel.add(validerBtn);
            dialog.add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            validerBtn.addActionListener(ev -> {
                String dateText = dateEditionField.getText();
                java.time.LocalDate dateEdition = null;
                try {
                    if (dateText.matches("\\d{2}/\\d{2}/\\d{4}")) {
                        String[] parts = dateText.split("/");
                        dateText = parts[2] + "-" + parts[1] + "-" + parts[0];
                    } else if (dateText.matches("\\d{2}-\\d{2}-\\d{4}")) {
                        String[] parts = dateText.split("-");
                        dateText = parts[2] + "-" + parts[1] + "-" + parts[0];
                    }
                    dateEdition = java.time.LocalDate.parse(dateText);
                    Livre l = new Livre(
                        titreField.getText(),
                        auteurField.getText(),
                        editeurField.getText(),
                        dateEdition,
                        codeField.getText(),
                        Integer.parseInt(nbExemplairesField.getText()),
                        com.aubenc.ebiblio.model.TypeLivre.valueOf((String)typeBox.getSelectedItem())
                    );
                    gestion.ajouterLivre(l);
                    rafraichirTable();
                    JOptionPane.showMessageDialog(this, "Livre ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } catch (java.time.format.DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(dialog, "Format de date invalide. Utilisez AAAA-MM-JJ (ex: 2024-03-18).", "Erreur", JOptionPane.ERROR_MESSAGE);
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
                String titre = (String) model.getValueAt(row, 1);
                String auteur = (String) model.getValueAt(row, 2);
                String editeur = (String) model.getValueAt(row, 3);
                Object dateEditionObj = model.getValueAt(row, 4);
                String codeUnique = String.valueOf(model.getValueAt(row, 5));
                String nbExemplaires = String.valueOf(model.getValueAt(row, 6));
                String type = (model.getValueAt(row, 7) instanceof com.aubenc.ebiblio.model.TypeLivre) ? ((com.aubenc.ebiblio.model.TypeLivre)model.getValueAt(row, 7)).name() : String.valueOf(model.getValueAt(row, 7));
                String dateEdition = (dateEditionObj instanceof java.time.LocalDate) ? ((java.time.LocalDate)dateEditionObj).toString() : String.valueOf(dateEditionObj);
                JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Modifier un livre", true);
                JPanel panel = new JPanel(new GridLayout(8, 1, 8, 8));
                panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
                JTextField codeField = new JTextField(codeUnique);
                JTextField titreField = new JTextField(titre);
                JTextField auteurField = new JTextField(auteur);
                JTextField editeurField = new JTextField(editeur);
                JTextField dateEditionField = new JTextField(dateEdition);
                JTextField nbExemplairesField = new JTextField(nbExemplaires);
                JComboBox<String> typeBox = new JComboBox<>(new String[]{"SCIENTIFIQUE", "LITTERAIRE"});
                typeBox.setSelectedItem(type);
                panel.add(new JLabel("Code unique"));
                panel.add(codeField);
                panel.add(new JLabel("Titre"));
                panel.add(titreField);
                panel.add(new JLabel("Auteur"));
                panel.add(auteurField);
                panel.add(new JLabel("Editeur"));
                panel.add(editeurField);
                panel.add(new JLabel("Date édition (AAAA-MM-JJ, ex: 2024-03-18)"));
                panel.add(dateEditionField);
                panel.add(new JLabel("Nb exemplaires"));
                panel.add(nbExemplairesField);
                panel.add(new JLabel("Type"));
                panel.add(typeBox);
                JButton validerBtn = new JButton("Valider");
                panel.add(validerBtn);
                dialog.add(panel);
                dialog.pack();
                dialog.setLocationRelativeTo(this);
                validerBtn.addActionListener(ev -> {
                    String dateText = dateEditionField.getText();
                    java.time.LocalDate dateEditionVal = null;
                    try {
                        if (dateText.matches("\\d{2}/\\d{2}/\\d{4}")) {
                            String[] parts = dateText.split("/");
                            dateText = parts[2] + "-" + parts[1] + "-" + parts[0];
                        } else if (dateText.matches("\\d{2}-\\d{2}-\\d{4}")) {
                            String[] parts = dateText.split("-");
                            dateText = parts[2] + "-" + parts[1] + "-" + parts[0];
                        }
                        dateEditionVal = java.time.LocalDate.parse(dateText);
                        Livre l = new Livre(
                            titreField.getText(),
                            auteurField.getText(),
                            editeurField.getText(),
                            dateEditionVal,
                            codeField.getText(),
                            Integer.parseInt(nbExemplairesField.getText()),
                            com.aubenc.ebiblio.model.TypeLivre.valueOf((String)typeBox.getSelectedItem())
                        );
                        gestion.modifierLivre(l);
                        rafraichirTable();
                        JOptionPane.showMessageDialog(this, "Livre modifié.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                        dialog.dispose();
                    } catch (java.time.format.DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(dialog, "Format de date invalide. Utilisez AAAA-MM-JJ (ex: 2024-03-18).", "Erreur", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(dialog, "Erreur lors de la modification : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                });
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un livre à modifier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        supprimerBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int id = (int) model.getValueAt(row, 0);
                try {
                    gestion.supprimerLivre(id);
                    rafraichirTable();
                    JOptionPane.showMessageDialog(this, "Livre supprimé.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un livre à supprimer.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        filtreTypeBox.addActionListener(e -> rafraichirTable());
    }

    private void rafraichirTable() {
        model.setRowCount(0);
        List<Livre> livres;
        String filtre = (String) filtreTypeBox.getSelectedItem();
        if (filtre != null && !filtre.equals("TOUS")) {
            livres = gestion.rechercherLivresParType(com.aubenc.ebiblio.model.TypeLivre.valueOf(filtre));
        } else {
            livres = gestion.getLivres();
        }
        for (Livre l : livres) {
            model.addRow(new Object[]{
                l.getId(), l.getTitre(), l.getAuteur(), l.getEditeur(), l.getDateEdition(), l.getCodeUnique(), l.getNbExemplaires(), l.getType()
            });
        }
    }
}
