package com.ebiblio.view;

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
        com.ebiblio.view.LivrePanel livrePanel = new com.ebiblio.view.LivrePanel(gestion);
        com.ebiblio.view.AdherentPanel adherentPanel = new com.ebiblio.view.AdherentPanel(gestion);
        com.ebiblio.view.EmpruntPanel empruntPanel = new com.ebiblio.view.EmpruntPanel(gestion);
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.add(new JLabel("Bienvenue sur le tableau de bord eBiblio"));
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
