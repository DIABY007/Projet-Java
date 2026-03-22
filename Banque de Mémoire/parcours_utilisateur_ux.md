# Parcours Utilisateur et Analyse UX de l’Interface eBiblio

## Parcours Utilisateur (Interface Graphique)

### 1. Lancement de l’application
- L’utilisateur lance l’application via le menu principal (Main.java).
- La fenêtre principale (`MainFrame`) s’ouvre avec une barre latérale (sidebar) à gauche et un panneau principal à droite.

### 2. Navigation
- La sidebar propose 4 onglets :
  - **Livres**
  - **Adhérents**
  - **Emprunts**
  - **Tableau de bord**
- Un clic sur un onglet affiche le panneau correspondant dans la partie principale.

### 3. Gestion des Livres
- L’onglet **Livres** affiche un tableau listant tous les livres (code, titre, auteur, type).
- Un formulaire en bas permet d’ajouter, modifier ou supprimer un livre via les champs et boutons dédiés.
- Après chaque action, le tableau se met à jour automatiquement.

### 4. Gestion des Adhérents
- L’onglet **Adhérents** affiche un tableau listant tous les membres (ID, nom, prénom, type).
- Un formulaire en bas permet d’ajouter, modifier ou supprimer un adhérent.
- Les modifications sont immédiatement visibles dans le tableau.

### 5. Gestion des Emprunts
- L’onglet **Emprunts** affiche un tableau des emprunts en cours (adhérent, livre, date début, date limite).
- Les emprunts sont créés via des listes déroulantes pour sélectionner l’adhérent et le livre.
- Les dates sont saisies ou générées automatiquement.
- Le tableau se met à jour après chaque opération.

### 6. Tableau de bord
- L’onglet **Tableau de bord** affiche un message d’accueil ou des statistiques globales.

---

## Analyse de l’Expérience Utilisateur (UX)

### Points positifs
- **Navigation claire** : la sidebar permet un accès rapide à chaque fonctionnalité.
- **Formulaires simples** : chaque action (ajout, modification, suppression) est accessible via des champs et boutons explicites.
- **Rafraîchissement automatique** : les tableaux se mettent à jour instantanément après chaque opération, ce qui évite toute confusion.
- **Utilisation de JTable** : affichage structuré et lisible des données.
- **Feedback immédiat** : l’utilisateur voit directement le résultat de ses actions.

### Points à améliorer
- **Absence de messages d’erreur/confirmation** : l’interface ne semble pas afficher de pop-up ou de message en cas d’erreur ou de succès (à ajouter pour rassurer l’utilisateur).
- **Design minimaliste** : l’interface est fonctionnelle mais très sobre, sans éléments visuels avancés (icônes, couleurs, etc.).
- **Accessibilité** : pas d’indications sur la prise en charge du clavier ou des lecteurs d’écran.
- **Aide contextuelle** : il n’y a pas d’aide ou de tooltip pour guider l’utilisateur sur les champs à remplir.

### Conclusion
L’interface graphique de eBiblio est efficace pour la gestion de bibliothèque, avec une navigation intuitive et des opérations rapides. Pour une expérience utilisateur optimale, il serait pertinent d’ajouter des messages de feedback, d’améliorer le design visuel et d’intégrer une aide contextuelle.
