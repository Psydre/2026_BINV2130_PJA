# Atelier 3 : JUnit – séance 1 : solutions

*Énoncé : [`03A_2_exercices.md`](../AJ_atelier03_seance1/03A_2_exercices.md) — théorie : [`03A_1_theorie.md`](../AJ_atelier03_seance1/03A_1_theorie.md).*

## Tests de la classe `Prix`

### Question 1

**Énoncé :** Préparation du test

*Préparation du test* — [`test/domaine/PrixTest.java`](test/domaine/PrixTest.java) :

```java
class PrixTest {
    // ...
    private Prix prixAucune;

    private Prix prixPub;

    private Prix prixSolde;

    @BeforeEach
    void setUp() {
        prixAucune = new Prix();
        prixAucune.definirPrix(1, 20);
        prixAucune.definirPrix(10, 10);

        prixPub = new Prix(TypePromo.PUB, 10);
        prixPub.definirPrix(3, 15);
        prixPub.definirPrix(10, 8);

        prixSolde = new Prix(TypePromo.SOLDE, 30);
        prixSolde.definirPrix(2, 18);
        prixSolde.definirPrix(10, 9);
    }
    // ...
}
```

### Question 2

**Énoncé :** Test des « getters » (utilisez les attributs définis)

*Tests des getters* — [`test/domaine/PrixTest.java`](test/domaine/PrixTest.java) :

```java
class PrixTest {
    private Prix prixAucune;
    // ...

    @Test
    @DisplayName("getValeurPromo sur prixAucune renvoie 0")
    void testGetValeurPromoAucune() {
        assertEquals(0, prixAucune.getValeurPromo());
    }

    @Test
    @DisplayName("getTypePromo sur prixAucune renvoie null")
    void testGetTypePromoAucune() {
        assertNull(prixAucune.getTypePromo());
    }
    // ...
}
```

### Question 3

**Énoncé :** Test du constructeur

*Test du constructeur avec TypePromo null* — [`test/domaine/PrixTest.java`](test/domaine/PrixTest.java) :

```java
class PrixTest {
    // ...

    @Test
    @DisplayName("Test du constructeur avec paramètre Promo null")
    void testPrix1() {
        assertThrows(IllegalArgumentException.class, () -> new Prix(null, 15));
    }
    // ...
}
```

### Question 4

**Énoncé :** Regroupement d'assertions

*Regroupement d'assertions (assertAll)* — [`test/domaine/PrixTest.java`](test/domaine/PrixTest.java) :

```java
class PrixTest {
    private Prix prixSolde;
    // ...

    @Test
    @DisplayName("getValeurPromo et getTypePromo sur prixSolde renvoient les valeurs du constructeur")
    void testGettersSolde() {
        assertAll(
                () -> assertEquals(30, prixSolde.getValeurPromo()),
                () -> assertSame(TypePromo.SOLDE, prixSolde.getTypePromo())
        );
    }
    // ...
}
```

## Tests de `getPrixPromo`

### Question 5

**Énoncé :** `getPrixPromo` sans promo

*getPrixPromo sans promo* — [`test/domaine/PrixTest.java`](test/domaine/PrixTest.java) :

```java
class PrixTest {
    private Prix prixAucune;
    // ...

    @Test
    @DisplayName("getPrixPromo sur prixAucune renvoie le même résultat que getPrix")
    void testGetPrixPromoAucune() {
        assertEquals(prixAucune.getPrix(1), prixAucune.getPrixPromo(1));
    }
    // ...
}
```

### Question 6

**Énoncé :** `getPrixPromo` avec promo `PUB`

*getPrixPromo avec promo PUB* — [`test/domaine/PrixTest.java`](test/domaine/PrixTest.java) :

```java
class PrixTest {
    private Prix prixPub;
    // ...

    @Test
    @DisplayName("getPrixPromo sur prixPub soustrait le montant fixe de valeurPromo")
    void testGetPrixPromoPub() {
        assertEquals(prixPub.getPrix(3) - prixPub.getValeurPromo(), prixPub.getPrixPromo(3));
    }
    // ...
}
```

### Question 7

**Énoncé :** `getPrixPromo` avec promo `SOLDE`

*getPrixPromo avec promo SOLDE* — [`test/domaine/PrixTest.java`](test/domaine/PrixTest.java) :

```java
class PrixTest {
    private Prix prixSolde;
    // ...

    @Test
    @DisplayName("getPrixPromo sur prixSolde applique le pourcentage de valeurPromo")
    void testGetPrixPromoSolde() {
        assertEquals(prixSolde.getPrix(2) * (1 - prixSolde.getValeurPromo() / 100), prixSolde.getPrixPromo(2));
    }
    // ...
}
```

### Question 8

**Énoncé :** `getPrixPromo` et le plancher de `DESTOCKAGE`

*getPrixPromo et le plancher de DESTOCKAGE* — [`test/domaine/PrixTest.java`](test/domaine/PrixTest.java) :

```java
class PrixTest {
    // ...

    @Test
    @DisplayName("getPrixPromo avec une promo DESTOCKAGE ne descend jamais sous 1 euro")
    void testGetPrixPromoDestockagePlancher() {
        Prix prixDestockage = new Prix(TypePromo.DESTOCKAGE, 95);
        prixDestockage.definirPrix(1, 5);
        assertEquals(1, prixDestockage.getPrixPromo(1));
    }
    // ...
}
```

### Question 9

**Énoncé :** `getPrixPromo` avec une quantité invalide

*getPrixPromo avec une quantité invalide* — [`test/domaine/PrixTest.java`](test/domaine/PrixTest.java) :

```java
class PrixTest {
    private Prix prixPub;
    // ...

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("Test de la méthode getPrixPromo avec une quantité < ou = à 0")
    void testGetPrixPromo1(int quantite) {
        assertThrows(IllegalArgumentException.class, () -> prixPub.getPrixPromo(quantite));
    }
    // ...
}
```

## Tests paramétrés et de `definirPrix`

### Question 10

**Énoncé :** Constructeur — valeur de promo invalide

*Constructeur — valeur de promo invalide* — [`test/domaine/PrixTest.java`](test/domaine/PrixTest.java) :

```java
class PrixTest {
    // ...

    @ParameterizedTest
    @DisplayName("Test du constructeur avec une valeur de promo < ou = à 0")
    @ValueSource(doubles = {-7, -4, 0})
    void testPrix2(double valeur) {
        assertThrows(IllegalArgumentException.class, () -> new Prix(TypePromo.SOLDE, valeur));
    }
    // ...
}
```

### Question 11

**Énoncé :** `definirPrix` — quantité invalide

*definirPrix — quantité invalide* — [`test/domaine/PrixTest.java`](test/domaine/PrixTest.java) :

```java
class PrixTest {
    private Prix prixPub;
    // ...

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("Test de la méthode definirPrix avec une quantité < ou = à 0")
    void definirPrix1(int quantite) {
        assertThrows(IllegalArgumentException.class, () -> prixPub.definirPrix(quantite, 15));
    }
    // ...
}
```

### Question 12

**Énoncé :** `definirPrix` — prix unitaire invalide

*definirPrix — prix unitaire invalide* — [`test/domaine/PrixTest.java`](test/domaine/PrixTest.java) :

```java
class PrixTest {
    private Prix prixPub;
    // ...

    @ParameterizedTest
    @ValueSource(doubles = {-3, 0})
    @DisplayName("Test de la méthode definirPrix avec une valeur < ou = à 0")
    void definirPrix2(double valeur) {
        assertThrows(IllegalArgumentException.class, () -> prixPub.definirPrix(15, valeur));
    }
    // ...
}
```

### Question 13

**Énoncé :** `definirPrix` — remplacement d'un palier existant

*definirPrix — remplacement d'un palier existant* — [`test/domaine/PrixTest.java`](test/domaine/PrixTest.java) :

```java
class PrixTest {
    private Prix prixAucune;
    // ...

    @Test
    @DisplayName("Test du remplacement de la valeur pour une quantité déjà existante")
    void definirPrix3() {
        prixAucune.definirPrix(10, 6);
        assertEquals(6, prixAucune.getPrix(10));
    }
    // ...
}
```

## Tests de `getPrix`

### Question 14

**Énoncé :** `getPrix` — quantité invalide

*getPrix — quantité invalide* — [`test/domaine/PrixTest.java`](test/domaine/PrixTest.java) :

```java
class PrixTest {
    private Prix prixPub;
    // ...

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("Test de la méthode getPrix avec une quantité < ou = à 0")
    void testGetPrix1(int quantite) {
        assertThrows(IllegalArgumentException.class, () -> prixPub.getPrix(quantite));
    }
    // ...
}
```

### Question 15

**Énoncé :** `getPrix` — balayage des paliers

*getPrix — balayage des paliers* — [`test/domaine/PrixTest.java`](test/domaine/PrixTest.java) :

```java
class PrixTest {
    private Prix prixAucune;
    // ...

    @ParameterizedTest
    @CsvFileSource(resources = "paliers.csv", numLinesToSkip = 1)
    @DisplayName("Test de la méthode getPrix pour prixAucune sur les paliers de la question 1")
    void testGetPrix2(int quantite, double prixAttendu) {
        assertEquals(prixAttendu, prixAucune.getPrix(quantite));
    }
    // ...
}
```

### Question 16

**Énoncé :** `getPrix` — seuil minimal d'une promo

*getPrix — seuil minimal d'une promo* — [`test/domaine/PrixTest.java`](test/domaine/PrixTest.java) :

```java
class PrixTest {
    private Prix prixPub;
    // ...

    @Test
    @DisplayName("Test de getPrix avec une quantité sous la plus petite quantité de prixPub")
    void testGetPrix3() {
        assertThrows(QuantiteNonAutoriseeException.class, () -> prixPub.getPrix(2));
    }
    // ...
}
```

### Question 17

**Énoncé :** `getPrix` — seuil minimal, deuxième cas

*getPrix — seuil minimal, deuxième cas* — [`test/domaine/PrixTest.java`](test/domaine/PrixTest.java) :

```java
class PrixTest {
    private Prix prixSolde;
    // ...

    @Test
    @DisplayName("Test de getPrix avec une quantité sous la plus petite quantité de prixSolde")
    void testGetPrix4() {
        assertThrows(QuantiteNonAutoriseeException.class, () -> prixSolde.getPrix(1));
    }
    // ...
}
```

## Test de scénario sur plusieurs paliers

### Question 18

**Énoncé :** Test de scénario sur plusieurs paliers

*Test de scénario sur plusieurs paliers* — [`test/domaine/PrixTest.java`](test/domaine/PrixTest.java) :

```java
class PrixTest {
    // ...

    @Test
    @DisplayName("Test de scénario complet sur plusieurs paliers")
    void testGetPrixScenarioPlusieursPaliers() {
        Prix prix = new Prix();
        prix.definirPrix(3, 20);
        prix.definirPrix(5, 18);
        prix.definirPrix(10, 15);

        assertAll(
                () -> assertEquals(20, prix.getPrix(3)),
                () -> assertEquals(18, prix.getPrix(5)),
                () -> assertEquals(15, prix.getPrix(10)),
                () -> assertEquals(20, prix.getPrix(4)),
                () -> assertEquals(18, prix.getPrix(9)),
                () -> assertThrows(QuantiteNonAutoriseeException.class, () -> prix.getPrix(2))
        );

        prix.definirPrix(5, 16);

        assertAll(
                () -> assertEquals(16, prix.getPrix(5)),
                () -> assertEquals(16, prix.getPrix(9)),
                () -> assertEquals(15, prix.getPrix(10))
        );
    }
}
```

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
