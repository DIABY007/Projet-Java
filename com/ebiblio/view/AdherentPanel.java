package com.ebiblio.view;

import com.ebiblio.model.Adherent;
import com.aubenc.ebiblio.service.GestionBibliotheque;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdherentPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField nomField, prenomField, idField;
    private JComboBox<String> typeBox;
    private JButton ajouterBtn, modifierBtn, supprimerBtn;
    private GestionBibliotheque gestion;

    public AdherentPanel(GestionBibliotheque gestion) {
        this.gestion = gestion;
        setLayout(new BorderLayout());

        // Table
        model = new DefaultTableModel(new Object[]{"ID", "Nom", "Prénom", "Type"}, 0);
        table = new JTable(model);
        rafraichirTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Formulaire
        JPanel form = new JPanel(new GridLayout(2, 5));
        nomField = new JTextField();
        prenomField = new JTextField();
        typeBox = new JComboBox<>(new String[]{"ETUDIANT", "ENSEIGNANT", "VISITEUR"});
        ajouterBtn = new JButton("Ajouter");
        modifierBtn = new JButton("Modifier");
        supprimerBtn = new JButton("Supprimer");

        form.add(new JLabel("Nom"));
        form.add(new JLabel("Prénom"));
        form.add(new JLabel("Type"));
        form.add(new JLabel("Actions"));
        form.add(nomField);
        form.add(prenomField);
        form.add(typeBox);
        JPanel actions = new JPanel();
        actions.add(ajouterBtn);
        actions.add(modifierBtn);
        actions.add(supprimerBtn);
        form.add(actions);
        add(form, BorderLayout.SOUTH);

        // Listeners
        ajouterBtn.addActionListener(e -> {
            Adherent a = new Adherent(nomField.getText(), prenomField.getText(), "", "", Adherent.Type.valueOf((String)typeBox.getSelectedItem()));
            gestion.ajouterAdherent(a);
            rafraichirTable();
        });
        modifierBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) table.getValueAt(row, 0);
                Adherent a = new Adherent(id, nomField.getText(), prenomField.getText(), "", "", Adherent.Type.valueOf((String)typeBox.getSelectedItem()));
                gestion.modifierAdherent(a);
                rafraichirTable();
            }
        });
        supprimerBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) table.getValueAt(row, 0);
                gestion.supprimerAdherent(id);
                rafraichirTable();
            }
        });
    }

    private void rafraichirTable() {
        model.setRowCount(0);
        List<Adherent> adherents = gestion.getAdherents();
        for (Adherent a : adherents) {
            model.addRow(new Object[]{a.getId(), a.getNom(), a.getPrenom(), a.getType()});
        }
    }
}
