package com.ebiblio.dao;

import com.ebiblio.model.Livre;
import java.util.List;

public interface LivreDAO {
    void ajouter(Livre livre);
    void modifier(Livre livre);
    void supprimer(int id);
    Livre trouverParId(int id);
    List<Livre> listerTous();
}
