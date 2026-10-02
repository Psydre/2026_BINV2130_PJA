package domaine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProduitTest {

    // Question 1 : Préparation du test
    private static final LocalDate DATE_ANNEE_PASSEE = LocalDate.now().minusYears(1);

    private static final LocalDate DATE_MOIS_PASSEE = LocalDate.now().minusMonths(1);

    private static final LocalDate DATE_AUJOURDHUI = LocalDate.now();

    private Produit produitSansPrix;

    private Produit produitAvecPrix;

    private Prix prixAucune;

    private Prix prixPub;

    private Prix prixSolde;

    @BeforeEach
    void setUp() {
        produitSansPrix = new Produit("nom1", "marque1", "rayon1");
        produitAvecPrix = new Produit("nom2", "marque2", "rayon2");

        prixAucune = new Prix();
        prixAucune.definirPrix(1, 20);
        prixAucune.definirPrix(10, 10);

        prixPub = new Prix(TypePromo.PUB, 10);
        prixPub.definirPrix(3, 15);
        prixPub.definirPrix(10, 8);

        prixSolde = new Prix(TypePromo.SOLDE, 30);
        prixSolde.definirPrix(2, 18);
        prixSolde.definirPrix(10, 9);

        produitAvecPrix.ajouterPrix(DATE_ANNEE_PASSEE, prixAucune);
        produitAvecPrix.ajouterPrix(DATE_MOIS_PASSEE, prixPub);
        produitAvecPrix.ajouterPrix(DATE_AUJOURDHUI, prixSolde);
    }

    // Question 2 : Tests des prix (ajouterPrix et getPrix)

    @Test
    @DisplayName("Test que la méthode getPrix avec une date située entre deux dates de définition de prix")
    void testGetPrix8() {
        assertEquals(prixPub, produitAvecPrix.getPrix(DATE_AUJOURDHUI.minusDays(1)));
    }

    // Question 3 : equals — deux produits de même état sont égaux

    @Test
    @DisplayName("Test que deux produits ayant même nom, marque et rayon sont égaux")
    void testEquals1() {
        Produit produit = new Produit("nom2", "marque2", "rayon2");
        assertEquals(produitAvecPrix, produit);
    }

    // Question 4 : equals — un seul attribut différent suffit à briser l'égalité

    @Test
    @DisplayName("Test que deux produits ayant deux noms différents ne sont pas égaux")
    void testEquals2() {
        Produit produit = new Produit("nom", "marque2", "rayon2");
        assertNotEquals(produitAvecPrix, produit);
    }

    @Test
    @DisplayName("Test que deux produits ayant deux marques différentes ne sont pas égaux")
    void testEquals3() {
        Produit produit = new Produit("nom2", "marque", "rayon2");
        assertNotEquals(produitAvecPrix, produit);
    }

    @Test
    @DisplayName("Test que deux produits ayant deux rayons différents ne sont pas égaux")
    void testEquals4() {
        Produit produit = new Produit("nom2", "marque2", "rayon");
        assertNotEquals(produitAvecPrix, produit);
    }

    // Question 5 : hashCode cohérent avec equals

    @Test
    @DisplayName("Test que deux produits ayant même nom, marque et rayon ont le même hashCode")
    void testHashCode5() {
        Produit produit = new Produit("nom2", "marque2", "rayon2");
        assertEquals(produitAvecPrix.hashCode(), produit.hashCode());
    }

}
