# Atelier 4 : comment la remise zip est corrigée

Cette page explique comment votre archive est notée, pour que vous sachiez à l'avance ce qui
compte et ce qui ne compte pas. Le barème lui-même est dans
[`04_zip-a-rendre.md`](01-seance1/AJ_atelier04_seance1/04_zip-a-rendre.md#barème).

## En deux étapes

1. **Une vérification automatique.** Un script vérifie chaque archive selon la grille de
   correction, critère par critère.
2. **Une relecture à la main.** Un enseignant relit ensuite chaque remise, sans exception, avant
   que les résultats soient publiés, pour écarter une erreur du script.

Seule la **dernière** version déposée sur mooVin est corrigée.

## Ce que le script vérifie

| Critère | Points | Ce qui fait perdre les points |
|---|---|---|
| Archive `.zip` valide | 4 | un `.rar`, un `.7z`, un `.tar.gz` ou un fichier abîmé |
| `src` et `test` à la racine | 4 | un dossier qui les enveloppe, comme `AJ_atelier04_seance1/` |
| `src/TodoList.java` présent | 4 | le fichier absent, ou rangé ailleurs que dans `src/` |
| `test/TodoListTest.java` présent | 4 | le fichier absent, ou rangé ailleurs que dans `test/` |
| Nom du fichier | 4 | un nom qui n'est pas le vôtre, un accent, un espace, un autre format |

Une archive qui ne s'ouvre pas ne peut pas être vérifiée : les critères sur son contenu sont
alors perdus aussi. Un `.rar` vaut donc 0/20.

Pour le nom, le script compare avec votre nom officiel, sans accent ni espace : José
Vander Meulen écrit `AJ_atelier04_seance1_VANDERMEULEN-Jose.zip`.

## Ce qui ne coûte rien

Le code Java n'est pas évalué. Un dossier `out`, `.idea` ou `target`, ou des fichiers `.class`
dans l'archive, ne retirent aucun point. Évitez-les quand même le jour de l'examen.

## Les résultats

Les résultats sont publiés sur mooVin dans un fichier Excel : une ligne par matricule, avec la
note sur 20 et les points obtenus pour chacun des cinq critères ci-dessus. 

Une erreur dans votre correction ? Signalez-la à votre enseignant de séance ou via le
[formulaire de remarques](https://forms.gle/UhpPjfS36XXmKS2F7), avec votre matricule.

---

*Une remarque ou une erreur repérée ? [Signalez-le ici](https://forms.gle/UhpPjfS36XXmKS2F7).*

*Cheat sheet de cette semaine : [consultez-la en ligne](https://astounding-queijadas-0f428a.netlify.app/04-tdd-fr.html).*

*Cette fiche a été rédigée conjointement avec [Claude Code](https://claude.com/claude-code) et [Codex](https://openai.com/codex).*
