# Application Lecture — Kotlin / Android Studio

Application Android de lecture de livres avec 2 interfaces : liste des chapitres et discussions contextuelles.

---

## Data Classes — `model/DataClasses.kt`

4 data classes : `Livre`, `Chapitre`, `Commentaire`, `Playlist`

---

## Routage

**Aller** (clic sur "Commentaires") — `ChapitreListActivity.kt` :
```kotlin
val intent = Intent(this, DiscussionsActivity::class.java).apply {
    putExtra(DiscussionsActivity.EXTRA_CHAPITRE_TITRE, ...)
    putExtra(DiscussionsActivity.EXTRA_CHAPITRE_PROGRESSION, ...)
}
startActivity(intent)
```

**Retour** (bouton ←) — `DiscussionsActivity.kt` :
```kotlin
btnRetour.setOnClickListener { finish() }
```
##  Résultats

| Liste des chapitres | Discussions contextuelles |
|---|---|
| Cette interface affiche la liste des chapitres avec leur progression et un sous-menu dépliable (Playlist, Commentaires). | Cette interface affiche les discussions contextuelles du chapitre sélectionné, avec les commentaires des utilisateurs et un champ pour en ajouter un nouveau. |
| <img width="371" height="803" alt="1_" src="https://github.com/user-attachments/assets/fe24a699-3c20-48f5-8ea5-ab790a0b8272" /> | <img width="368" height="816" alt="2_" src="https://github.com/user-attachments/assets/73b1171f-df93-464a-9445-2e88d5eab66b" />|
