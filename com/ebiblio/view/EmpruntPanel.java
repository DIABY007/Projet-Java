package com.ebiblio.view;

import com.ebiblio.model.FicheEmprunt;
import com.aubenc.ebiblio.service.GestionBibliotheque;
import com.ebiblio.model.Adherent;
import com.ebiblio.model.Livre;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class EmpruntPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<Integer> adherentBox, livreBox;
    private JTextField dateEmpruntField, dateLimiteField, dateRetourField;
    private JButton ajouterBtn, modifierBtn, supprimerBtn;
    private GestionBibliotheque gestion;

    public EmpruntPanel(GestionBibliotheque gestion) {
        this.gestion = gestion;
        setLayout(new BorderLayout());

        // Table
        model = new DefaultTableModel(new Object[]{"ID", "Adhérent", "Livre", "Date Emprunt", "Date Limite", "Date Retour"}, 0);
        table = new JTable(model);
        rafraichirTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Formulaire
        JPanel form = new JPanel(new GridLayout(2, 6));
        adherentBox = new JComboBox<>();
        livreBox = new JComboBox<>();
        dateEmpruntField = new JTextField();
        dateLimiteField = new JTextField();
        dateRetourField = new JTextField();
        ajouterBtn = new JButton("Ajouter");
        modifierBtn = new JButton("Modifier");
        supprimerBtn = new JButton("Supprimer");

        form.add(new JLabel("Adhérent"));
        form.add(new JLabel("Livre"));
        form.add(new JLabel("Date Emprunt"));
        form.add(new JLabel("Date Limite"));
        form.add(new JLabel("Date Retour"));
        form.add(new JLabel("Actions"));
        form.add(adherentBox);
        form.add(livreBox);
        form.add(dateEmpruntField);
        form.add(dateLimiteField);
        form.add(dateRetourField);
        JPanel actions = new JPanel();
        actions.add(ajouterBtn);
        actions.add(modifierBtn);
        actions.add(supprimerBtn);
        form.add(actions);
        add(form, BorderLayout.SOUTH);

        // Remplir les JComboBox
        rafraichirComboBox();

        // Listeners
        ajouterBtn.addActionListener(e -> {
            Integer adherentId = (Integer)adherentBox.getSelectedItem();
            Integer livreId = (Integer)livreBox.getSelectedItem();
            if (adherentId == null || livreId == null) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un adhérent et un livre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Adherent adherent = null;
            for (Adherent a : gestion.getAdherents()) {
                if (a.getId() == adherentId) {
                    adherent = a;
                    break;
                }
            }
            if (adherent == null) {
                JOptionPane.showMessageDialog(this, "Adhérent introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!adherent.peutEmprunter()) {
                JOptionPane.showMessageDialog(this, "L'adhérent a atteint la limite d'emprunts ou possède un retard. Emprunt impossible.", "Blocage", JOptionPane.WARNING_MESSAGE);
                return;
            }
            FicheEmprunt f = new FicheEmprunt(0,
                adherentId,
                livreId,
                Date.valueOf(dateEmpruntField.getText()),
                Date.valueOf(dateLimiteField.getText()),
                dateRetourField.getText().isEmpty() ? null : Date.valueOf(dateRetourField.getText()));
            gestion.ajouterEmprunt(f);
            rafraichirTable();
        });
        modifierBtn.addActionListener(e -> {
            int selectedId = getSelectedEmpruntId();
            if (selectedId != -1) {
                FicheEmprunt f = new FicheEmprunt(selectedId,
                    (Integer)adherentBox.getSelectedItem(),
                    (Integer)livreBox.getSelectedItem(),
                    Date.valueOf(dateEmpruntField.getText()),
                    Date.valueOf(dateLimiteField.getText()),
                    dateRetourField.getText().isEmpty() ? null : Date.valueOf(dateRetourField.getText()));
                gestion.modifierEmprunt(f);
                rafraichirTable();
            }
        });
        supprimerBtn.addActionListener(e -> {
            int selectedId = getSelectedEmpruntId();
            if (selectedId != -1) {
                gestion.supprimerEmprunt(selectedId);
                rafraichirTable();
            }
        });
    }

    private void rafraichirTable() {
        model.setRowCount(0);
        List<FicheEmprunt> emprunts = gestion.getEmprunts();
        for (FicheEmprunt f : emprunts) {
            model.addRow(new Object[]{f.getId(), f.getAdherentId(), f.getLivreId(), f.getDateEmprunt(), f.getDateLimite(), f.getDateRetour()});
        }
    }

    private void rafraichirComboBox() {
        adherentBox.removeAllItems();
        livreBox.removeAllItems();
        for (Adherent a : gestion.getAdherents()) {
            adherentBox.addItem(a.getId());
        }
        for (Livre l : gestion.getLivres()) {
            livreBox.addItem(l.getId());
        }
    }

    private int getSelectedEmpruntId() {
        int row = table.getSelectedRow();
        if (row != -1) {
            return (int) model.getValueAt(row, 0);
        }
        return -1;
    }
}
