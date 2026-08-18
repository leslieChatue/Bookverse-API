-- ============================
-- ROLE
-- ============================
INSERT INTO role (id, libelle_role)
VALUES (1, 'ROLE_ADMIN'), (2, 'ROLE_USER');

-- ============================
-- USER
-- ============================
INSERT INTO utilisateur (id, username, email, password, role_id)
VALUES 
(1, 'leslie', 'leslie@example.com', 'pass123', 2),
(2, 'admin', 'admin@example.com', 'admin123', 1),
(3, 'loic', 'loic@example.com', 'pass456', 2),
(4, 'jessica', 'jessica@example.com', 'pass789', 2);

-- ============================
-- AUTEUR
-- ============================
INSERT INTO auteur (id, nom, prenom, biographie)
VALUES 
(1, 'Hugo', 'Victor', 'Auteur français célèbre'),
(2, 'Rowling', 'J.K.', 'Auteure de Harry Potter'),
(3, 'Colleen', 'Hover', 'Auteur Américain célèbre'),
(4, 'Ali', 'Hazelwood', 'Auteur Américain célèbre');

-- ============================
-- CATEGORIE
-- ============================
INSERT INTO categorie (id, nom)
VALUES 
(1, 'Roman'),
(2, 'Fantasy'),
(3, 'Science'),
(4, 'Développement personnel');

-- ============================
-- LIVRE
-- ============================
INSERT INTO livre (id, titre, isbn, prix, stock, description, date_creation, date_modification, auteur_id, categorie_id)
VALUES
(1, 'Les Misérables', 'ISBN123', 19.99, 50, 'Un classique', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1),
(2, 'Harry Potter 1', 'ISBN456', 24.99, 100, 'Le début de la saga', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2),
(3, 'Verity', 'ISBN789', 10.99, 60, 'Un classique', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 1),
(4, 'Love on brain', 'ISBN126', 9.99, 30, 'Un classique', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 1);

-- ============================
-- PANIER
-- ============================
INSERT INTO panier (id, user_id, date_creation)
VALUES 
(1, 1, CURRENT_DATE),
(2, 3, CURRENT_DATE),
(3, 4, CURRENT_DATE);

-- ============================
-- LIGNE PANIER
-- ============================
INSERT INTO ligne_panier (id, panier_id, livre_id, quantite)
VALUES
(1, 1, 1, 2),
(2, 1, 2, 1),
(3, 2, 3, 1),
(4, 2, 4, 1),
(5, 1, 2, 1);

-- ============================
-- COMMANDE
-- ============================
INSERT INTO commande (id, numero_commande, date_commande, statut, montant_total, user_id)
VALUES
(1, 'CMD001', CURRENT_TIMESTAMP, 'EN_ATTENTE', 44.98, 2),
(2, 'CMD002', CURRENT_TIMESTAMP, 'EN_ATTENTE', 34.98, 2),
(3, 'CMD003', CURRENT_TIMESTAMP, 'EN_ATTENTE', 45.99, 3);

-- ============================
-- LIGNE COMMANDE
-- ============================
INSERT INTO ligne_commande (id, commande_id, livre_id, quantite, prix)
VALUES
(1, 1, 1, 2, 19.99),
(2, 1, 2, 4, 24.99),
(3, 2, 3, 5, 24.99),
(4, 3, 4, 6, 10.99);

-- ============================
-- PAIEMENT
-- ============================
INSERT INTO paiement (id, commande_id, date_paiement, montant, mode_paiement, statut_paiement)
VALUES
(1, 1, CURRENT_TIMESTAMP, 44.98, 'CB', 'VALIDE');
