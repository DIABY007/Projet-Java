package com.aubenc.ebiblio.dao;

import com.aubenc.ebiblio.model.FicheEmprunt;
import com.aubenc.ebiblio.model.Adherent;
import com.aubenc.ebiblio.model.Livre;
import com.aubenc.ebiblio.config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDAOImpl implements EmpruntDAO {
    @Override
    public void ajouter(FicheEmprunt emprunt) {
        String sql = "INSERT INTO Emprunts (adherent_id, livre_id, date_emprunt, date_limite) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            int adherentId = emprunt.getAdherent().getId();
            int livreId = emprunt.getLivre().getId();
            if (adherentId == -1 || livreId == -1) {
                System.err.println("Erreur : Adhérent ou livre introuvable. L'emprunt n'a pas été enregistré.");
                return;
            }
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, adherentId);
                stmt.setInt(2, livreId);
                stmt.setDate(3, java.sql.Date.valueOf(emprunt.getDateDebut()));
                stmt.setDate(4, java.sql.Date.valueOf(emprunt.getDateLimite()));
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getAdherentIdByUnique(String idUnique, Connection conn) throws SQLException {
        String sql = "SELECT id FROM Adherents WHERE idUnique=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idUnique);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        }
        return -1;
    }
    private int getLivreIdByCodeUnique(String codeUnique, Connection conn) throws SQLException {
        String sql = "SELECT id FROM Livres WHERE codeUnique=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codeUnique);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        }
        return -1;
    }

    @Override
    public void modifier(FicheEmprunt emprunt) {
        String sql = "UPDATE Emprunts SET adherent_idUnique=?, livre_codeUnique=?, date_emprunt=?, date_limite=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, emprunt.getAdherent().getIdUnique());
            stmt.setString(2, emprunt.getLivre().getCodeUnique());
            stmt.setDate(3, java.sql.Date.valueOf(emprunt.getDateDebut()));
            stmt.setDate(4, java.sql.Date.valueOf(emprunt.getDateLimite()));
            stmt.setInt(5, emprunt.getId()); // Assurez-vous que FicheEmprunt possède un champ id
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimer(int id) {
        String sql = "DELETE FROM Emprunts WHERE id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FicheEmprunt trouverParId(int id) {
        String sql = "SELECT * FROM Emprunts WHERE id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Récupérer l'adhérent et le livre via leurs DAO
                AdherentDAO adherentDAO = new AdherentDAOImpl();
                LivreDAO livreDAO = new LivreDAOImpl();
                Adherent adherent = adherentDAO.trouverParIdUnique(rs.getString("adherent_idUnique"));
                Livre livre = livreDAO.trouverParCodeUnique(rs.getString("livre_codeUnique"));
                return new FicheEmprunt(
                    adherent,
                    livre,
                    rs.getDate("date_emprunt").toLocalDate(),
                    rs.getDate("date_limite").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<FicheEmprunt> listerTous() {
        List<FicheEmprunt> emprunts = new ArrayList<>();
        String sql = "SELECT e.id, e.date_emprunt, e.date_limite, a.id AS adherent_id, a.nom AS adherent_nom, a.prenom AS adherent_prenom, a.idUnique AS adherent_idUnique, a.adresse AS adherent_adresse, a.type AS adherent_type, l.id AS livre_id, l.titre AS livre_titre, l.auteur AS livre_auteur, l.editeur AS livre_editeur, l.dateEdition AS livre_dateEdition, l.codeUnique AS livre_codeUnique, l.nbExemplaires AS livre_nbExemplaires, l.type AS livre_type FROM Emprunts e JOIN Adherents a ON e.adherent_id = a.id JOIN Livres l ON e.livre_id = l.id";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getInstance().getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Adherent adherent = new Adherent(
                    rs.getInt("adherent_id"),
                    rs.getString("adherent_nom"),
                    rs.getString("adherent_prenom"),
                    rs.getString("adherent_idUnique"),
                    rs.getString("adherent_adresse"),
                    com.aubenc.ebiblio.model.TypeAdherent.valueOf(rs.getString("adherent_type"))
                );
                Livre livre = new Livre(
                    rs.getInt("livre_id"),
                    rs.getString("livre_titre"),
                    rs.getString("livre_auteur"),
                    rs.getString("livre_editeur"),
                    rs.getDate("livre_dateEdition") != null ? rs.getDate("livre_dateEdition").toLocalDate() : null,
                    rs.getString("livre_codeUnique"),
                    rs.getInt("livre_nbExemplaires"),
                    com.aubenc.ebiblio.model.TypeLivre.valueOf(rs.getString("livre_type"))
                );
                emprunts.add(new FicheEmprunt(
                    rs.getInt("id"),
                    adherent,
                    livre,
                    rs.getDate("date_emprunt").toLocalDate(),
                    rs.getDate("date_limite").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return emprunts;
    }
}
