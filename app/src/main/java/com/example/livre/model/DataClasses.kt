// ============================================================
// FICHIER : DataClasses.kt
// À placer dans : com/example/livre/model/
// ============================================================

package com.example.livre.model

data class Livre(
    val id: Int,
    val titre: String,
    val auteur: String,
    val progressionTotale: Int,
    val chapitres: List<Chapitre>
)

data class Chapitre(
    val id: Int,
    val numero: Int,
    val titre: String,
    val progressionDebut: Int,
    val estComplete: Boolean,
    val playlistsRecommandees: List<Playlist> = emptyList(),
    val commentaires: List<Commentaire> = emptyList()
)

data class Commentaire(
    val id: Int,
    val auteur: String,
    val texte: String,
    val tempsDepuis: String,
    val nbReponses: Int = 0,
    val nbLikes: Int = 0
)

data class Playlist(
    val id: Int,
    val nom: String,
    val nbTitres: Int
)