package usecase;

import domaine.Prix;
import domaine.Produit;
import domaine.TypePromo;
import exceptions.DateDejaPresenteException;
import exceptions.PrixNonDisponibleException;
import exceptions.ProduitNonPresentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Question 14 [pointeur] : réorganisation de ListeProduitsTest en groupes @Nested (un par méthode testée)
class ListeProduitsTest {

    private ListeProduits listeProduits;
    private Produit produit1;
    private Produit produit2;
    private Prix prix;
    private static final LocalDate DATE_AUJOURDHUI = LocalDate.now();

    // Question 6 [pointeur] : tests des méthodes de ListeProduits
    // (contient, ajouterProduit, supprimerProduit, trouverProduit, ajouterPrix, trouverPrix)
    @BeforeEach
    void setUp() {
        listeProduits = new ListeProduits();
        produit1 = new Produit("nom1", "marque1", "rayon1");
        produit2 = new Produit("nom2", "marque2", "rayon2");
        prix = new Prix(TypePromo.PUB, 10);
        listeProduits.ajouterProduit(produit1);
    }

    @Nested
    @DisplayName("Tests de contient")
    class Contient {

        @Test
        @DisplayName("Test de contient avec un produit null")
        void testContient1() {
            assertThrows(IllegalArgumentException.class, () -> listeProduits.contient(null));
        }

        @Test
        @DisplayName("Test de contient avec un produit présent")
        void testContient2() {
            assertTrue(listeProduits.contient(produit1));
        }

        @Test
        @DisplayName("Test de contient avec un produit absent")
        void testContient3() {
            assertFalse(listeProduits.contient(produit2));
        }
    }

    @Nested
    @DisplayName("Tests de ajouterProduit")
    class AjouterProduit {

        @Test
        @DisplayName("Test de ajouterProduit avec un produit null")
        void testAjouterProduit1() {
            assertThrows(IllegalArgumentException.class, () -> listeProduits.ajouterProduit(null));
        }

        @Test
        @DisplayName("Test de ajouterProduit avec un produit absent de la liste")
        void testAjouterProduit2() {
            assertTrue(listeProduits.ajouterProduit(produit2));
            assertTrue(listeProduits.contient(produit2));
        }

        @Test
        @DisplayName("Test de ajouterProduit avec un produit déjà présent dans la liste")
        void testAjouterProduit3() {
            assertFalse(listeProduits.ajouterProduit(new Produit("nom1", "marque1", "rayon1")));
        }
    }

    @Nested
    @DisplayName("Tests de supprimerProduit")
    class SupprimerProduit {

        @Test
        @DisplayName("Test de supprimerProduit avec un produit null")
        void testSupprimerProduit1() {
            assertThrows(IllegalArgumentException.class, () -> listeProduits.supprimerProduit(null));
        }

        @Test
        @DisplayName("Test de supprimerProduit avec un produit présent")
        void testSupprimerProduit2() {
            assertTrue(listeProduits.supprimerProduit(produit1));
            assertFalse(listeProduits.contient(produit1));
        }

        @Test
        @DisplayName("Test de supprimerProduit avec un produit absent")
        void testSupprimerProduit3() {
            assertFalse(listeProduits.supprimerProduit(produit2));
        }
    }

    @Nested
    @DisplayName("Tests de trouverProduit")
    class TrouverProduit {

        @Test
        @DisplayName("Test de trouverProduit avec un produit présent : renvoie le produit stocké")
        void testTrouverProduit1() {
            assertSame(produit1, listeProduits.trouverProduit("nom1", "marque1", "rayon1"));
        }

        @Test
        @DisplayName("Test de trouverProduit avec un produit absent : renvoie null")
        void testTrouverProduit2() {
            assertNull(listeProduits.trouverProduit("nom2", "marque2", "rayon2"));
        }

        @Test
        @DisplayName("Test de trouverProduit avec un paramètre invalide : lève IllegalArgumentException")
        void testTrouverProduit3() {
            assertThrows(IllegalArgumentException.class,
                    () -> listeProduits.trouverProduit(null, "marque1", "rayon1"));
        }
    }

    @Nested
    @DisplayName("Tests de ajouterPrix")
    class AjouterPrix {

        @Test
        @DisplayName("Test de ajouterPrix sur un produit absent de la liste")
        void testAjouterPrix1() {
            assertThrows(ProduitNonPresentException.class,
                    () -> listeProduits.ajouterPrix(produit2, DATE_AUJOURDHUI, prix));
        }

        @Test
        @DisplayName("Test de ajouterPrix sur une date déjà présente pour ce produit")
        void testAjouterPrix2() {
            listeProduits.ajouterPrix(produit1, DATE_AUJOURDHUI, prix);
            assertThrows(DateDejaPresenteException.class,
                    () -> listeProduits.ajouterPrix(produit1, DATE_AUJOURDHUI, new Prix()));
        }

        @Test
        @DisplayName("Test que ajouterPrix modifie bien le produit stocké, "
                + "même en passant un produit égal mais de référence différente")
        void testAjouterPrix3() {
            Produit produitEgalAutreReference = new Produit("nom1", "marque1", "rayon1");
            listeProduits.ajouterPrix(produitEgalAutreReference, DATE_AUJOURDHUI, prix);
            assertEquals(prix, produit1.getPrix(DATE_AUJOURDHUI));
        }

        @Test
        @DisplayName("Test de ajouterPrix avec un paramètre null : lève IllegalArgumentException")
        void testAjouterPrix4() {
            assertThrows(IllegalArgumentException.class,
                    () -> listeProduits.ajouterPrix(produit1, DATE_AUJOURDHUI, null));
        }
    }

    @Nested
    @DisplayName("Tests de trouverPrix")
    class TrouverPrix {

        @Test
        @DisplayName("Test de trouverPrix sur un produit absent de la liste")
        void testTrouverPrix1() {
            assertThrows(ProduitNonPresentException.class,
                    () -> listeProduits.trouverPrix(produit2, DATE_AUJOURDHUI));
        }

        @Test
        @DisplayName("Test de trouverPrix sur un produit sans prix disponible à cette date")
        void testTrouverPrix2() {
            assertThrows(PrixNonDisponibleException.class,
                    () -> listeProduits.trouverPrix(produit1, DATE_AUJOURDHUI));
        }

        @Test
        @DisplayName("Test que trouverPrix retrouve bien le prix, "
                + "même en passant un produit égal mais de référence différente")
        void testTrouverPrix3() {
            listeProduits.ajouterPrix(produit1, DATE_AUJOURDHUI, prix);
            Produit produitEgalAutreReference = new Produit("nom1", "marque1", "rayon1");
            assertEquals(prix, listeProduits.trouverPrix(produitEgalAutreReference, DATE_AUJOURDHUI));
        }

        @Test
        @DisplayName("Test de trouverPrix avec un paramètre null : lève IllegalArgumentException")
        void testTrouverPrix4() {
            assertThrows(IllegalArgumentException.class,
                    () -> listeProduits.trouverPrix(null, DATE_AUJOURDHUI));
        }
    }

    // Question 7 [pointeur] : scénario complet sur ListeProduits
    @Nested
    @DisplayName("Scénario complet")
    class ScenarioComplet {

        @Test
        @DisplayName("Test de scénario complet avec produit égal, autre référence et historique de prix")
        void testScenarioCompletListeProduits() {
            ListeProduits liste = new ListeProduits();
            Produit produitStocke = new Produit("lait", "Fairebel", "frais");
            Produit memeProduitAutreReference = new Produit("lait", "Fairebel", "frais");
            Produit produitAbsent = new Produit("pain", "Boulangerie", "boulangerie");
            Prix prixAncien = new Prix();
            Prix prixRecent = new Prix(TypePromo.SOLDE, 20);
            LocalDate dateAncienne = LocalDate.of(2026, 1, 1);
            LocalDate dateRecente = LocalDate.of(2026, 3, 1);

            prixAncien.definirPrix(1, 2.10);
            prixRecent.definirPrix(1, 1.80);

            assertAll(
                    () -> assertTrue(liste.ajouterProduit(produitStocke)),
                    () -> assertFalse(liste.ajouterProduit(memeProduitAutreReference))
            );

            liste.ajouterPrix(memeProduitAutreReference, dateAncienne, prixAncien);
            liste.ajouterPrix(memeProduitAutreReference, dateRecente, prixRecent);

            assertAll(
                    () -> assertEquals(prixRecent, liste.trouverPrix(memeProduitAutreReference, dateRecente)),
                    () -> assertEquals(prixAncien,
                            liste.trouverPrix(memeProduitAutreReference, LocalDate.of(2026, 2, 1))),
                    () -> assertEquals(prixAncien, produitStocke.getPrix(dateAncienne)),
                    () -> assertEquals(prixRecent, produitStocke.getPrix(dateRecente)),
                    () -> assertThrows(ProduitNonPresentException.class,
                            () -> liste.trouverPrix(produitAbsent, dateRecente)),
                    () -> assertThrows(PrixNonDisponibleException.class,
                            () -> liste.trouverPrix(memeProduitAutreReference, LocalDate.of(2025, 12, 31)))
            );
        }
    }

    @Nested
    @DisplayName("Tests de produitsTriesParPrix")
    class ProduitsTriesParPrix {

        // Question 13 : produitsTriesParPrix — validation des paramètres
        @Test
        @DisplayName("Test de produitsTriesParPrix avec un rayon null")
        void testProduitsTriesParPrix1() {
            assertThrows(IllegalArgumentException.class,
                    () -> listeProduits.produitsTriesParPrix(null, DATE_AUJOURDHUI, 1));
        }

        @Test
        @DisplayName("Test de produitsTriesParPrix avec une date null")
        void testProduitsTriesParPrix2() {
            assertThrows(IllegalArgumentException.class,
                    () -> listeProduits.produitsTriesParPrix("rayon1", null, 1));
        }

        @Test
        @DisplayName("Test de produitsTriesParPrix avec une quantité négative ou nulle")
        void testProduitsTriesParPrix3() {
            assertAll(
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> listeProduits.produitsTriesParPrix("rayon1", DATE_AUJOURDHUI, 0)),
                    () -> assertThrows(IllegalArgumentException.class,
                            () -> listeProduits.produitsTriesParPrix("rayon1", DATE_AUJOURDHUI, -1))
            );
        }

        // Question 12 : produitsTriesParPrix — rayon sans produit
        @Test
        @DisplayName("Test de produitsTriesParPrix sur un rayon sans aucun produit : liste vide, pas d'exception")
        void testProduitsTriesParPrix4() {
            assertTrue(listeProduits.produitsTriesParPrix("rayonInexistant", DATE_AUJOURDHUI, 1).isEmpty());
        }

        // Questions 8 à 11 : scénario complet de tri (6 produits, exclusions et ordre attendu)
        @Test
        @DisplayName("Test de produitsTriesParPrix : tri par prix croissant, "
                + "exclusion sur prix indisponible, quantité non autorisée ou autre rayon")
        void testProduitsTriesParPrix5() {
            ListeProduits liste = new ListeProduits();

            Produit cher = new Produit("cher", "marqueA", "rayonX");
            Produit moyen = new Produit("moyen", "marqueB", "rayonX");
            Produit pasCher = new Produit("pasCher", "marqueC", "rayonX");
            Produit sansPrix = new Produit("sansPrix", "marqueD", "rayonX");
            Produit quantiteNonAutorisee = new Produit("quantiteNonAutorisee", "marqueE", "rayonX");
            Produit autreRayon = new Produit("autreRayon", "marqueF", "rayonY");

            Prix prixCher = new Prix();
            prixCher.definirPrix(1, 20);
            Prix prixMoyen = new Prix();
            prixMoyen.definirPrix(1, 12);
            Prix prixPasCher = new Prix();
            prixPasCher.definirPrix(1, 5);
            Prix prixQuantiteElevee = new Prix();
            prixQuantiteElevee.definirPrix(10, 1);
            Prix prixAutreRayon = new Prix();
            prixAutreRayon.definirPrix(1, 1);

            liste.ajouterProduit(cher);
            liste.ajouterProduit(moyen);
            liste.ajouterProduit(pasCher);
            liste.ajouterProduit(sansPrix);
            liste.ajouterProduit(quantiteNonAutorisee);
            liste.ajouterProduit(autreRayon);

            liste.ajouterPrix(cher, DATE_AUJOURDHUI, prixCher);
            liste.ajouterPrix(moyen, DATE_AUJOURDHUI, prixMoyen);
            liste.ajouterPrix(pasCher, DATE_AUJOURDHUI, prixPasCher);
            liste.ajouterPrix(quantiteNonAutorisee, DATE_AUJOURDHUI, prixQuantiteElevee);
            liste.ajouterPrix(autreRayon, DATE_AUJOURDHUI, prixAutreRayon);
            //sansPrix ne reçoit aucun prix

            List<Produit> resultat = liste.produitsTriesParPrix("rayonX", DATE_AUJOURDHUI, 1);

            assertEquals(List.of(pasCher, moyen, cher), resultat);
        }
    }
}
