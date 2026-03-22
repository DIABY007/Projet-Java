package com.aubenc.ebiblio.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel sidebar;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private com.aubenc.ebiblio.service.GestionBibliotheque gestion;

    public MainFrame() {
        gestion = new com.aubenc.ebiblio.service.GestionBibliotheque();
        setTitle("eBiblio - Gestion Bibliothèque");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar
        sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(4, 1));
        sidebar.setBackground(new Color(240, 240, 240));
        sidebar.setPreferredSize(new Dimension(150, 0));

        JButton livresBtn = new JButton("Livres");
        JButton adherentsBtn = new JButton("Adhérents");
        JButton empruntsBtn = new JButton("Emprunts");
        JButton dashboardBtn = new JButton("Tableau de bord");

        sidebar.add(livresBtn);
        sidebar.add(adherentsBtn);
        sidebar.add(empruntsBtn);
        sidebar.add(dashboardBtn);

        add(sidebar, BorderLayout.WEST);

        // Main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        LivrePanel livrePanel = new LivrePanel(gestion);
        AdherentPanel adherentPanel = new AdherentPanel(gestion);
        EmpruntPanel empruntPanel = new EmpruntPanel(gestion);
        JPanel dashboardPanel = new JPanel(new BorderLayout());
        JLabel titreStats = new JLabel("Classement des livres les plus empruntés", SwingConstants.CENTER);
        dashboardPanel.add(titreStats, BorderLayout.NORTH);
        // Tableau des statistiques
        String[] colonnes = {"Code unique", "Titre", "Auteur", "Nombre d'emprunts"};
        java.util.List<com.aubenc.ebiblio.model.Livre> livres = gestion.getLivres();
        java.util.List<com.aubenc.ebiblio.model.FicheEmprunt> emprunts = gestion.getEmprunts();
        java.util.Map<String, Integer> stats = new java.util.HashMap<>();
        for (com.aubenc.ebiblio.model.FicheEmprunt f : emprunts) {
            String code = f.getLivre().getCodeUnique();
            stats.put(code, stats.getOrDefault(code, 0) + 1);
        }
        Object[][] data = new Object[livres.size()][4];
        for (int i = 0; i < livres.size(); i++) {
            com.aubenc.ebiblio.model.Livre l = livres.get(i);
            data[i][0] = l.getCodeUnique();
            data[i][1] = l.getTitre();
            data[i][2] = l.getAuteur();
            data[i][3] = stats.getOrDefault(l.getCodeUnique(), 0);
        }
        // Tri décroissant sur le nombre d'emprunts
        java.util.Arrays.sort(data, (a, b) -> Integer.compare((int)b[3], (int)a[3]));
        JTable statsTable = new JTable(data, colonnes);
        dashboardPanel.add(new JScrollPane(statsTable), BorderLayout.CENTER);
        mainPanel.add(livrePanel, "Livres");
        mainPanel.add(adherentPanel, "Adhérents");
        mainPanel.add(empruntPanel, "Emprunts");
        mainPanel.add(dashboardPanel, "Tableau de bord");
        add(mainPanel, BorderLayout.CENTER);

        // Button actions
        livresBtn.addActionListener(e -> {
            livrePanel.repaint();
            cardLayout.show(mainPanel, "Livres");
        });
        adherentsBtn.addActionListener(e -> {
            adherentPanel.repaint();
            cardLayout.show(mainPanel, "Adhérents");
        });
        empruntsBtn.addActionListener(e -> {
            empruntPanel.repaint();
            cardLayout.show(mainPanel, "Emprunts");
        });
        dashboardBtn.addActionListener(e -> cardLayout.show(mainPanel, "Tableau de bord"));
    }

    public void afficher() {
        setVisible(true);
    }
}
