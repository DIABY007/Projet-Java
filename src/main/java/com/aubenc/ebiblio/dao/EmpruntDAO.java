package com.aubenc.ebiblio.dao;

import com.aubenc.ebiblio.model.FicheEmprunt;
import java.util.List;

public interface EmpruntDAO {
    void ajouter(FicheEmprunt emprunt);
    void modifier(FicheEmprunt emprunt);
    void supprimer(int id);
    FicheEmprunt trouverParId(int id);
    List<FicheEmprunt> listerTous();
}
