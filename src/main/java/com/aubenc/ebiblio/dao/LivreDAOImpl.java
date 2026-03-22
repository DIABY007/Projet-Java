// Méthode déplacée à l'intérieur de la classe
package com.aubenc.ebiblio.dao;

import com.aubenc.ebiblio.model.Livre;
import com.aubenc.ebiblio.config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAOImpl implements LivreDAO {
    @Override
    public void supprimer(int id) {
        String sql = "DELETE FROM Livres WHERE id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Livre trouverParCodeUnique(String codeUnique) {
        String sql = "SELECT * FROM Livres WHERE codeUnique=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codeUnique);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Livre(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("auteur"),
                    rs.getString("editeur"),
                    rs.getDate("dateEdition").toLocalDate(),
                    rs.getString("codeUnique"),
                    rs.getInt("nbExemplaires"),
                    com.aubenc.ebiblio.model.TypeLivre.valueOf(rs.getString("type"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
// ...existing code...
        @Override
        public Livre trouverParId(int id) {
            String sql = "SELECT * FROM Livres WHERE id=?";
            try (Connection conn = DatabaseConnection.getInstance().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new Livre(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getString("editeur"),
                        rs.getDate("dateEdition").toLocalDate(),
                        rs.getString("codeUnique"),
                        rs.getInt("nbExemplaires"),
                        com.aubenc.ebiblio.model.TypeLivre.valueOf(rs.getString("type"))
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    @Override
    public void ajouter(Livre livre) {
        String sql = "INSERT INTO Livres (titre, auteur, editeur, dateEdition, codeUnique, nbExemplaires, type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setString(3, livre.getEditeur());
            stmt.setDate(4, java.sql.Date.valueOf(livre.getDateEdition()));
            stmt.setString(5, livre.getCodeUnique());
            stmt.setInt(6, livre.getNbExemplaires());
            stmt.setString(7, livre.getType().name());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                livre.setId(generatedId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifier(Livre livre) {
        String sql = "UPDATE Livres SET titre=?, auteur=?, editeur=?, dateEdition=?, nbExemplaires=?, type=? WHERE codeUnique=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setString(3, livre.getEditeur());
            stmt.setDate(4, java.sql.Date.valueOf(livre.getDateEdition()));
            stmt.setInt(5, livre.getNbExemplaires());
            stmt.setString(6, livre.getType().name());
            stmt.setString(7, livre.getCodeUnique());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @Override
    public List<Livre> listerTous() {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM Livres";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                livres.add(new Livre(
                    rs.getInt("id"),
                    rs.getString("titre"),
                    rs.getString("auteur"),
                    rs.getString("editeur"),
                    rs.getDate("dateEdition").toLocalDate(),
                    rs.getString("codeUnique"),
                    rs.getInt("nbExemplaires"),
                    com.aubenc.ebiblio.model.TypeLivre.valueOf(rs.getString("type"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }
}
