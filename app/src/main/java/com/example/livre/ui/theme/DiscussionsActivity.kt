// ============================================================
// Interface 1 : Discussions contextuelles
// ============================================================

package com.example.livre.ui
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.livre.R
import com.example.livre.model.Commentaire
import com.google.android.material.button.MaterialButton

class DiscussionsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CHAPITRE_ID = "extra_chapitre_id"
        const val EXTRA_CHAPITRE_TITRE = "extra_chapitre_titre"
        const val EXTRA_CHAPITRE_PROGRESSION = "extra_chapitre_progression"
    }

    private lateinit var tvTitreChapitre: TextView
    private lateinit var tvProgression: TextView
    private lateinit var recyclerViewCommentaires: RecyclerView
    private lateinit var etNouveauCommentaire: EditText
    private lateinit var btnAjouter: Button
    private lateinit var btnRetour: ImageView

    private val commentaires = mutableListOf(
        Commentaire(1, "Yasmine", "J'ai trouvé le premier chapitre vraiment captivant. Il instaure immédiatement un ton mystérieux.", "il y a 2 heures", 3, 35),
        Commentaire(2, "Ikram", "J'ai trouvé le premier chapitre vraiment captivant. Il instaure immédiatement un ton mystérieux.", "il y a 1 heure", 1, 1),
        Commentaire(3, "Literary_Lover", "J'ai trouvé le premier chapitre vraiment captivant. Il instaure immédiatement un ton mystérieux.", "il y a 30 minutes", 0, 0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discussions)

        // Récupération des données de l'Intent (routage depuis ChapitreListActivity)
        val titreChapitre = intent.getStringExtra(EXTRA_CHAPITRE_TITRE) ?: "Chapitre"
        val progression = intent.getIntExtra(EXTRA_CHAPITRE_PROGRESSION, 0)

        tvTitreChapitre = findViewById(R.id.tvTitreChapitre)
        tvProgression = findViewById(R.id.tvProgression)
        recyclerViewCommentaires = findViewById(R.id.recyclerViewCommentaires)
        etNouveauCommentaire = findViewById(R.id.etNouveauCommentaire)
        btnAjouter = findViewById(R.id.btnAjouterCommentaire)
        btnRetour = findViewById(R.id.btnRetour)

        tvTitreChapitre.text = titreChapitre
        tvProgression.text = "$progression%"

        recyclerViewCommentaires.layoutManager = LinearLayoutManager(this)
        recyclerViewCommentaires.adapter = CommentaireAdapter(commentaires)

        // ROUTAGE : bouton ← retour vers ChapitreListActivity
        btnRetour.setOnClickListener {
            finish()
        }

        // Ajouter un commentaire
        btnAjouter.setOnClickListener {
            val texte = etNouveauCommentaire.text.toString().trim()
            if (texte.isNotEmpty()) {
                ajouterCommentaire(texte)
                etNouveauCommentaire.setText("")
            }
        }
    }

    private fun ajouterCommentaire(texte: String) {
        val nouveau = Commentaire(
            id = commentaires.size + 1,
            auteur = "Moi",
            texte = texte,
            tempsDepuis = "à l'instant",
            nbReponses = 0,
            nbLikes = 0
        )
        commentaires.add(nouveau)
        recyclerViewCommentaires.adapter?.notifyItemInserted(commentaires.size - 1)
        recyclerViewCommentaires.scrollToPosition(commentaires.size - 1)
    }
}

// ============================================================
// ADAPTER : CommentaireAdapter
// ============================================================

class CommentaireAdapter(
    private val commentaires: List<Commentaire>
) : RecyclerView.Adapter<CommentaireAdapter.CommentaireViewHolder>() {

    inner class CommentaireViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        val tvAuteur: TextView = itemView.findViewById(R.id.tvAuteur)
        val tvTemps: TextView = itemView.findViewById(R.id.tvTemps)
        val tvTexte: TextView = itemView.findViewById(R.id.tvTexteCommentaire)
        val tvNbReponses: TextView = itemView.findViewById(R.id.tvNbReponses)
        val tvNbLikes: TextView = itemView.findViewById(R.id.tvNbLikes)
        val ivLike: ImageView = itemView.findViewById(R.id.ivLike)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentaireViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_commentaire, parent, false)
        return CommentaireViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentaireViewHolder, position: Int) {
        val commentaire = commentaires[position]

        holder.tvAuteur.text = commentaire.auteur
        holder.tvTemps.text = commentaire.tempsDepuis
        holder.tvTexte.text = commentaire.texte
        holder.tvNbReponses.text = commentaire.nbReponses.toString()
        holder.tvNbLikes.text = if (commentaire.nbLikes > 0) commentaire.nbLikes.toString() else ""
        holder.ivAvatar.setImageResource(android.R.drawable.ic_menu_myplaces)

        holder.ivLike.setOnClickListener {
            // like à implémenter
        }
    }

    override fun getItemCount(): Int = commentaires.size
}
