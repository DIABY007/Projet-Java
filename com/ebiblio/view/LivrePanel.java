package com.ebiblio.view;

import com.ebiblio.model.Livre;
import com.aubenc.ebiblio.service.GestionBibliotheque;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LivrePanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField idField, titreField, auteurField;
    private JComboBox<String> typeBox;
    private JButton ajouterBtn, modifierBtn, supprimerBtn;
    private GestionBibliotheque gestion;

    public LivrePanel(GestionBibliotheque gestion) {
        this.gestion = gestion;
        setLayout(new BorderLayout());

        // Table
        model = new DefaultTableModel(new Object[]{"ID", "Titre", "Auteur", "Type"}, 0);
        table = new JTable(model);
        rafraichirTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Formulaire
        JPanel form = new JPanel(new GridLayout(2, 5));
        titreField = new JTextField();
        auteurField = new JTextField();
        typeBox = new JComboBox<>(new String[]{"SCIENTIFIQUE", "LITTERAIRE"});
        ajouterBtn = new JButton("Ajouter");
        modifierBtn = new JButton("Modifier");
        supprimerBtn = new JButton("Supprimer");

        form.add(new JLabel("Titre"));
        form.add(new JLabel("Auteur"));
        form.add(new JLabel("Type"));
        form.add(new JLabel("Actions"));
        form.add(titreField);
        form.add(auteurField);
        form.add(typeBox);
        JPanel actions = new JPanel();
        actions.add(ajouterBtn);
        actions.add(modifierBtn);
        actions.add(supprimerBtn);
        form.add(actions);
        add(form, BorderLayout.SOUTH);

        // Listeners
        ajouterBtn.addActionListener(e -> {
            Livre l = new Livre(titreField.getText(), auteurField.getText(), "", null, "", 0, com.aubenc.ebiblio.model.TypeLivre.valueOf((String)typeBox.getSelectedItem()));
            gestion.ajouterLivre(l);
            rafraichirTable();
        });
        modifierBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) table.getValueAt(row, 0);
                Livre l = new Livre(id, titreField.getText(), auteurField.getText(), "", null, "", 0, com.aubenc.ebiblio.model.TypeLivre.valueOf((String)typeBox.getSelectedItem()));
                gestion.modifierLivre(l);
                rafraichirTable();
            }
        });
        supprimerBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) table.getValueAt(row, 0);
                gestion.supprimerLivre(id);
                rafraichirTable();
            }
        });
    }

    private void rafraichirTable() {
        model.setRowCount(0);
        List<Livre> livres = gestion.getLivres();
        for (Livre l : livres) {
            model.addRow(new Object[]{l.getId(), l.getTitre(), l.getAuteur(), l.getType()});
        }
    }
}
