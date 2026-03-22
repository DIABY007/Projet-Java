package com.aubenc.ebiblio.main;

import com.aubenc.ebiblio.model.*;
import com.aubenc.ebiblio.service.GestionBibliotheque;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new com.aubenc.ebiblio.view.MainFrame().afficher();
        });
    }
}
