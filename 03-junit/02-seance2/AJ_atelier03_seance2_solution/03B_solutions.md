# Atelier 3 : JUnit – séance 2 : solutions

*Énoncé : [`03B_2_exercices.md`](../AJ_atelier03_seance2/03B_2_exercices.md) — théorie : [`03B_1_theorie.md`](../AJ_atelier03_seance2/03B_1_theorie.md).*

## Tests de la classe `Produit`

### Question 1

**Énoncé :** Préparation du test

*Préparation du test* — [`test/domaine/ProduitTest.java`](test/domaine/ProduitTest.java) :

```java
class ProduitTest {
    // ...
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
    // ...
}
```

### Question 2

**Énoncé :** Test des prix (`ajouterPrix` et `getPrix`)

*Tests des prix (ajouterPrix et getPrix)* — [`test/domaine/ProduitTest.java`](test/domaine/ProduitTest.java) :

```java
class ProduitTest {
    private Produit produitAvecPrix;
    private Prix prixPub;
    // ...

    @Test
    @DisplayName("Test que la méthode getPrix avec une date située entre deux dates de définition de prix")
    void testGetPrix8() {
        assertEquals(prixPub, produitAvecPrix.getPrix(DATE_AUJOURDHUI.minusDays(1)));
    }
    // ...
}
```

### Question 3

**Énoncé :** `equals` — deux produits de même état

*equals — deux produits de même état sont égaux* — [`test/domaine/ProduitTest.java`](test/domaine/ProduitTest.java) :

```java
class ProduitTest {
    private Produit produitAvecPrix;
    // ...

    @Test
    @DisplayName("Test que deux produits ayant même nom, marque et rayon sont égaux")
    void testEquals1() {
        Produit produit = new Produit("nom2", "marque2", "rayon2");
        assertEquals(produitAvecPrix, produit);
    }
    // ...
}
```

### Question 4

**Énoncé :** `equals` — un seul attribut différent

*equals — un seul attribut différent suffit à briser l'égalité* — [`test/domaine/ProduitTest.java`](test/domaine/ProduitTest.java) :

```java
class ProduitTest {
    private Produit produitAvecPrix;
    // ...

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
    // ...
}
```

### Question 5

**Énoncé :** `hashCode` cohérent avec `equals`

*hashCode cohérent avec equals* — [`test/domaine/ProduitTest.java`](test/domaine/ProduitTest.java) :

```java
class ProduitTest {
    private Produit produitAvecPrix;
    // ...

    @Test
    @DisplayName("Test que deux produits ayant même nom, marque et rayon ont le même hashCode")
    void testHashCode5() {
        Produit produit = new Produit("nom2", "marque2", "rayon2");
        assertEquals(produitAvecPrix.hashCode(), produit.hashCode());
    }

}
```

## Tests de la classe `ListeProduits`

### Question 6

**Énoncé :** Tests des méthodes de `ListeProduits`

**tests des méthodes de ListeProduits**

La classe complète est dans `test/usecase/ListeProduitsTest.java`. Sa
structure vaut plus que chaque test pris isolément :

- un `@BeforeEach` construit une petite fixture commune (une liste, deux
  produits, un prix) au lieu de la répéter dans chaque test ;
- chaque méthode publique de `ListeProduits` reçoit son propre bloc de tests
  (`contient`, `ajouterProduit`, `supprimerProduit`, `trouverProduit`,
  `ajouterPrix`, `trouverPrix`), et chaque bloc suit le même canevas :
  paramètre invalide (`assertThrows`), cas nominal, cas limite
  (élément absent/déjà présent) ;
- les exceptions métier (`DateDejaPresenteException`,
  `ProduitNonPresentException`, `PrixNonDisponibleException`) sont vérifiées
  avec `assertThrows`, jamais avec un `try/catch` manuel.

*tests des méthodes de ListeProduits* — voir [`test/usecase/ListeProduitsTest.java`](test/usecase/ListeProduitsTest.java).

### Question 7

**Énoncé :** Scénario complet sur `ListeProduits`

**scénario complet sur ListeProduits**

`testScenarioCompletListeProduits` enchaîne l'ajout, la détection de doublon,
l'égalité entre produits, l'historique de prix et les exceptions métier dans
une seule situation réaliste. Point clé : `p1` est stocké, mais toutes les
opérations suivantes (`ajouterProduit`, `ajouterPrix`, `trouverPrix`) passent
`p2`, une **autre référence** égale à `p1` au sens de `equals`. Le second
`ajouterProduit(p2)` renvoie donc `false` (doublon), et les prix ajoutés via
`p2` se retrouvent bien sur le produit stocké `p1` — c'est la vérification que
`ListeProduits` travaille par égalité et non par identité. Les deux `assertAll`
regroupent les vérifications de succès (bon prix selon la date, exacte ou
intermédiaire) et les deux cas d'erreur (`ProduitNonPresentException` pour un
produit absent, `PrixNonDisponibleException` pour une date trop ancienne) sans
s'arrêter à la première assertion qui échoue.

*scénario complet sur ListeProduits* — voir [`test/usecase/ListeProduitsTest.java`](test/usecase/ListeProduitsTest.java).

## Tests de `produitsTriesParPrix`

### Question 8

**Énoncé :** Tri par prix croissant

**tri par prix croissant**

La solution couvre les questions 8 à 11 dans un seul scénario,
`testProduitsTriesParPrix5` (voir `test/usecase/ListeProduitsTest.java`) :
six produits construits pour ne différer que par le critère testé. Pour le
tri, trois produits triables (`pasCher`, `moyen`, `cher`) ; l'assertion
finale `assertEquals(List.of(pasCher, moyen, cher), resultat)` vérifie d'un
coup le contenu **et** l'ordre croissant — tout produit excédentaire ou mal
placé ferait échouer l'égalité de listes.

*scénario complet de tri (6 produits, exclusions et ordre attendu)* — voir [`test/usecase/ListeProduitsTest.java`](test/usecase/ListeProduitsTest.java).

### Question 9

**Énoncé :** Exclusion — pas de prix à la date demandée

**exclusion — pas de prix à la date demandée**

Dans le même scénario, `sansPrix` est ajouté au rayon mais ne reçoit jamais
de prix : `produitsTriesParPrix` attrape en interne la
`PrixNonDisponibleException` (multi-catch) et le produit est simplement
absent du résultat. L'égalité de listes de la question 8 suffit à le
vérifier : s'il apparaissait, elle échouerait.

*scénario complet de tri (6 produits, exclusions et ordre attendu)* — voir [`test/usecase/ListeProduitsTest.java`](test/usecase/ListeProduitsTest.java).

### Question 10

**Énoncé :** Exclusion — quantité minimale trop élevée

**exclusion — quantité minimale trop élevée**

Toujours dans le même scénario, `quantiteNonAutorisee` a un prix défini à
partir de 10 unités alors que la demande porte sur 1 unité :
`QuantiteNonAutoriseeException` en interne, même mécanisme d'exclusion, même
vérification par l'égalité de listes.

*scénario complet de tri (6 produits, exclusions et ordre attendu)* — voir [`test/usecase/ListeProduitsTest.java`](test/usecase/ListeProduitsTest.java).

### Question 11

**Énoncé :** Exclusion — autre rayon

**exclusion — autre rayon**

`autreRayon` a le prix le plus bas du scénario mais appartient à `rayonY` :
le filtre sur le rayon l'écarte avant tout calcul de prix. Son prix
volontairement imbattable garantit que, s'il n'était pas filtré, il
apparaîtrait en tête du résultat et ferait échouer l'assertion.

*scénario complet de tri (6 produits, exclusions et ordre attendu)* — voir [`test/usecase/ListeProduitsTest.java`](test/usecase/ListeProduitsTest.java).

### Question 12

**Énoncé :** Rayon sans produit

*produitsTriesParPrix — rayon sans produit* — [`test/usecase/ListeProduitsTest.java`](test/usecase/ListeProduitsTest.java) :

```java
class ListeProduitsTest {
    // ...
    class ProduitsTriesParPrix {
        // ...
        @Test
        @DisplayName("Test de produitsTriesParPrix sur un rayon sans aucun produit : liste vide, pas d'exception")
        void testProduitsTriesParPrix4() {
            assertTrue(listeProduits.produitsTriesParPrix("rayonInexistant", DATE_AUJOURDHUI, 1).isEmpty());
        }
        // ...
    }
}
```

### Question 13

**Énoncé :** Paramètres invalides

*produitsTriesParPrix — validation des paramètres* — [`test/usecase/ListeProduitsTest.java`](test/usecase/ListeProduitsTest.java) :

```java
class ListeProduitsTest {
    // ...
    class ProduitsTriesParPrix {
        // ...
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
        // ...
    }
}
```

## Réorganiser les tests avec `@Nested`

### Question 14

**Énoncé :** Regrouper les tests de `ListeProduitsTest` avec `@Nested`

**réorganisation avec `@Nested`**

La version finale de `ListeProduitsTest` regroupe ses tests par méthode testée
dans des classes internes non statiques annotées `@Nested` et `@DisplayName`
(`Contient`, `AjouterProduit`, `SupprimerProduit`, `TrouverProduit`,
`AjouterPrix`, `TrouverPrix`, `ScenarioComplet`, `ProduitsTriesParPrix`). Les
attributs et le `@BeforeEach` restent sur la classe externe : comme les classes
`@Nested` ne sont pas statiques, elles accèdent à cette fixture partagée. Le
corps des tests est identique à la version « à plat » — seule l'organisation
change, ce qui se vérifie en relançant la classe et en constatant que tous les
tests restent verts.

*réorganisation de ListeProduitsTest en groupes @Nested (un par méthode testée)* — voir [`test/usecase/ListeProduitsTest.java`](test/usecase/ListeProduitsTest.java).

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
