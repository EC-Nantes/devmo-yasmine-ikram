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
