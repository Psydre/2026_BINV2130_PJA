# Atelier 3 : JUnit — questionnaire à choix multiple

20 questions à réponse unique, tirées de `03A_1_theorie.md` (questions 1 à 12) et `03B_1_theorie.md` (questions 13 à 20).

---

### Question 1 — Non-régression

Quel est l'intérêt principal d'un test unitaire par rapport à une vérification « à l'œil » de la sortie console ?

- A) Il rejoue automatiquement la vérification à chaque modification et signale immédiatement une régression
- B) Il remplace la documentation
- C) Il évite d'écrire un `main`
- D) Il rend le programme plus rapide

**Réponse : A** — Un test qui passe reste vrai tant que le code ne change pas. Si quelqu'un casse le comportement plus tard, le test échoue tout de suite.

---

### Question 2 — Annotation d'un test

Quelle annotation marque une méthode comme test JUnit 5 ?

- A) `@Unit`
- B) `@TestCase`
- C) `@RunWith`
- D) `@Test`

**Réponse : D** — Chaque méthode annotée `@Test` est exécutée indépendamment par JUnit.

---

### Question 3 — `@DisplayName`

À quoi sert `@DisplayName` ?

- A) À renommer la méthode de test dans le code source
- B) À donner un nom lisible au test, affiché dans les résultats d'exécution
- C) À désactiver un test
- D) À définir l'ordre d'exécution des tests

**Réponse : B** — C'est purement de l'affichage : le nom de la méthode Java ne change pas.

---

### Question 4 — `@BeforeEach`

Quand la méthode annotée `@BeforeEach` est-elle exécutée ?

- A) Une seule fois, avant l'ensemble des tests de la classe
- B) Après chaque test
- C) Avant chaque méthode `@Test`, avec des objets neufs à chaque fois
- D) Uniquement si le test précédent a échoué

**Réponse : C** — C'est ce qui garantit qu'un test ne peut pas polluer un autre : chacun repart d'une fixture identique et indépendante.

---

### Question 5 — Rôle d'une assertion

Pourquoi un test sans assertion ne prouve-t-il rien ?

- A) Parce qu'il ne compare jamais le résultat obtenu à ce qui était attendu : il passe du moment que le code ne plante pas
- B) Parce qu'il faut au moins deux assertions par test
- C) Parce qu'il s'exécute trop vite
- D) Parce que JUnit refuse de l'exécuter

**Réponse : A** — C'est l'assertion qui transforme un simple appel de méthode en vérification automatisée.

---

### Question 6 — Ordre des arguments d'`assertEquals`

Dans `assertEquals(20, prix.getPrix(1))`, que représente chaque argument ?

- A) D'abord la valeur attendue, puis la valeur obtenue
- B) D'abord la valeur obtenue, puis la valeur attendue
- C) L'ordre n'a aucune importance
- D) Le premier argument est le message d'erreur

**Réponse : A** — La convention est *attendu, obtenu*. L'inverser ne change pas le verdict du test, mais rend le message d'échec trompeur.

---

### Question 7 — `assertEquals` ou `assertSame`

Quelle est la différence entre `assertEquals` et `assertSame` ?

- A) `assertSame` ne fonctionne que sur les nombres
- B) `assertEquals` compare avec `==`, `assertSame` compare avec `equals`
- C) `assertEquals` compare avec `equals`, `assertSame` compare avec `==`
- D) Il n'y a aucune différence

**Réponse : C** — Pour un énuméré, les deux donnent le même verdict (une seule instance par constante), mais `assertEquals` reste préférable pour des objets ordinaires.

---

### Question 8 — `assertAll`

Quel est l'apport d'`assertAll` par rapport à plusieurs assertions écrites à la suite ?

- A) Il permet de tester plusieurs classes à la fois
- B) Il transforme les échecs en avertissements
- C) Il exécute toutes les vérifications et rapporte tous les échecs, au lieu de s'arrêter au premier
- D) Il accélère le test

**Réponse : C** — Avec des assertions successives, la première qui échoue interrompt le test et masque les suivantes.

---

### Question 9 — `assertThrows`

Comment vérifie-t-on qu'un constructeur lance bien une `IllegalArgumentException` ?

- A) Avec `assertEquals(IllegalArgumentException.class, new Prix(null, 15))`
- B) Avec `assertThrows(IllegalArgumentException.class, () -> new Prix(null, 15))`
- C) En entourant l'appel d'un `try` / `catch` et en appelant `fail()` dans le `try`
- D) Avec `assertNotNull(new Prix(null, 15))`

**Réponse : B** — Le code à tester est passé sous forme de lambda. Le test échoue si aucune exception n'est lancée, ou si son type ne correspond pas.

---

### Question 10 — Valeur de retour d'`assertThrows`

Que renvoie `assertThrows` ?

- A) `void`
- B) Un `boolean`
- C) Le nombre d'exceptions levées
- D) L'exception capturée, ce qui permet de vérifier son message

**Réponse : D** — On peut donc écrire `IllegalArgumentException e = assertThrows(...)` puis tester `e.getMessage()`. Cela reste optionnel : ne vérifiez le message que s'il fait partie du comportement attendu, sinon un simple changement de texte casserait le test.

---

### Question 11 — Test paramétré

Que fait JUnit avec ce test ?

```java
@ParameterizedTest
@ValueSource(doubles = {-7, -4, 0})
void testConstructeurValeurInvalide(double valeur) {
    assertThrows(IllegalArgumentException.class, () -> new Prix(TypePromo.SOLDE, valeur));
}
```

- A) Il exécute la méthode une fois, avec la première valeur
- B) Il l'exécute trois fois, comme trois tests distincts
- C) Le code ne compile pas : `@ParameterizedTest` ne remplace pas `@Test`
- D) Il l'exécute une fois, avec un tableau des trois valeurs

**Réponse : B** — Un test paramétré remplace plusieurs tests presque identiques qui ne diffèrent que par une valeur d'entrée.

---

### Question 12 — Vérifier qu'un test teste vraiment

Quel réflexe permet de s'assurer qu'un test, généré par IA ou écrit à la main, vérifie réellement quelque chose ?

- A) Vérifier qu'il contient au moins trois assertions
- B) Vérifier qu'il s'exécute en moins d'une seconde
- C) Casser volontairement la méthode testée et vérifier que le test passe au rouge
- D) Le relancer plusieurs fois de suite

**Réponse : C** — Un test qui reste vert alors que le code testé est cassé ne détecte aucune régression, quelle que soit son origine.

---

### Question 13 — Tester `equals`

Que doit couvrir un test d'`equals` correctement écrit ?

- A) Uniquement le cas de deux objets égaux
- B) Uniquement le cas `null`
- C) L'égalité attendue, et chaque attribut qui, s'il diffère, doit rendre les objets différents
- D) La comparaison d'un objet avec lui-même, ce qui suffit

**Réponse : C** — Tester seulement le cas positif laisse passer un `equals` qui renverrait toujours `true`.

---

### Question 14 — Tester `hashCode`

Que vérifie un test de `hashCode` ?

- A) Que `hashCode` n'est jamais négatif
- B) Que deux objets égaux selon `equals` renvoient le même `hashCode`
- C) Que deux objets différents renvoient des `hashCode` différents
- D) Que le hash vaut une valeur numérique précise

**Réponse : B** — On teste la cohérence avec `equals`, jamais une valeur de hash particulière, qui dépend de l'implémentation.

---

### Question 15 — Fixtures avec des dates

Pourquoi déclarer `private static final LocalDate DATE_AUJOURDHUI = LocalDate.now();` plutôt que d'appeler `LocalDate.now()` dans chaque test ?

- A) Parce que `LocalDate.now()` ne peut pas être appelée dans un `@Test`
- B) Parce qu'une constante est obligatoire dans une classe de test
- C) Parce que `LocalDate.now()` est lente
- D) Pour que tous les tests utilisent exactement la même valeur, au lieu de dates légèrement différentes d'un test à l'autre

**Réponse : D** — Calculer les dates une seule fois évite qu'un test dépende de l'instant précis où il s'exécute.

---

### Question 16 — Exceptions métier

`assertThrows` fonctionne-t-il avec une exception définie dans le projet, comme `DateDejaPresenteException` ?

- A) Oui, mais seulement si elle hérite de `RuntimeException`
- B) Non, il faut un `try` / `catch` manuel
- C) Non, uniquement avec les exceptions du JDK
- D) Oui, exactement de la même façon qu'avec `IllegalArgumentException`

**Réponse : D** — `assertThrows` ne fait aucune distinction entre exceptions du JDK et exceptions métier.

---

### Question 17 — Comparer des listes

Que vérifie `assertEquals(List.of(pasCher, cher), resultat)` ?

- A) Le contenu **et** l'ordre des éléments
- B) Que les deux listes sont la même instance
- C) Uniquement la taille de la liste
- D) Uniquement que la liste contient les deux produits

**Réponse : A** — Une seule assertion couvre donc à la fois le tri et les exclusions : tout produit en trop, manquant ou mal placé fait échouer le test.

---

### Question 18 — Élément exclu d'un résultat

`produitsTriesParPrix` exclut silencieusement les produits sans prix disponible, en attrapant les exceptions métier en interne. Comment teste-t-on ce comportement ?

- A) Ce comportement n'est pas testable
- B) En vérifiant que la méthode renvoie `null`
- C) Avec `assertThrows` sur l'exception métier
- D) En vérifiant l'absence du produit dans la liste renvoyée

**Réponse : D** — Puisque aucune exception ne sort de la méthode, il n'y a pas d'`assertThrows` à écrire : c'est le contenu du résultat qui porte la vérification.

---

### Question 19 — `@Nested`

Pourquoi une classe interne annotée `@Nested` doit-elle être **non statique** ?

- A) Pour que ses tests s'exécutent en parallèle
- B) Parce que c'est ce qui lui donne accès à la fixture construite dans le `@BeforeEach` de la classe externe
- C) Parce qu'une classe statique ne peut pas contenir de méthodes `@Test`
- D) Parce que JUnit refuse d'instancier une classe statique

**Réponse : B** — Une classe interne statique ne dépend d'aucune instance de la classe englobante, donc ne verrait pas ses attributs.

---

### Question 20 — Effet de `@Nested` sur les tests

Que change `@Nested` au comportement des tests regroupés ?

- A) Rien : c'est purement de l'organisation
- B) Les tests d'un groupe partagent leur état
- C) Les tests s'exécutent dans l'ordre de déclaration
- D) La fixture est recréée une seule fois par groupe

**Réponse : A** — On peut donc réorganiser une classe existante en groupes `@Nested` sans toucher au corps des tests, et vérifier que tout reste vert.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
