// ============================================================
// Interface 2 : Liste des chapitres
// ============================================================

package com.example.livre.ui.theme
import com.example.livre.ui.DiscussionsActivity
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.livre.R
import com.example.livre.model.Chapitre
import com.example.livre.model.Commentaire
import com.example.livre.model.Livre
import com.example.livre.model.Playlist

class ChapitreListActivity : AppCompatActivity() {

    private lateinit var tvTitreLivre: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvPourcentage: TextView
    private lateinit var recyclerView: RecyclerView

    // Données simulées
    private val livre = Livre(
        id = 1,
        titre = "Titre du livre",
        auteur = "Auteur",
        progressionTotale = 5,
        chapitres = listOf(
            Chapitre(
                id = 1, numero = 1, titre = "Chapitre 1",
                progressionDebut = 3, estComplete = true,
                playlistsRecommandees = listOf(Playlist(1, "Ambiance mystère", 12)),
                commentaires = listOf(
                    Commentaire(1, "Yasmine", "J'ai trouvé le premier chapitre vraiment captivant. Il instaure immédiatement un ton mystérieux.", "il y a 2 heures", 3, 35),
                    Commentaire(2, "Ikram", "J'ai trouvé le premier chapitre vraiment captivant. Il instaure immédiatement un ton mystérieux.", "il y a 1 heure", 1, 1),
                    Commentaire(3, "Literary_Lover", "J'ai trouvé le premier chapitre vraiment captivant. Il instaure immédiatement un ton mystérieux.", "il y a 30 minutes", 0, 0)
                )
            ),
            Chapitre(id = 2, numero = 2, titre = "Chapitre 2", progressionDebut = 5, estComplete = true),
            Chapitre(id = 3, numero = 3, titre = "Chapitre 3", progressionDebut = 8, estComplete = false),
            Chapitre(id = 4, numero = 4, titre = "Chapitre 4", progressionDebut = 12, estComplete = false),
            Chapitre(id = 5, numero = 5, titre = "Chapitre 5", progressionDebut = 15, estComplete = false),
            Chapitre(id = 6, numero = 6, titre = "Chapitre 6", progressionDebut = 18, estComplete = false),
            Chapitre(id = 7, numero = 7, titre = "Chapitre 7", progressionDebut = 21, estComplete = false)
        )
    )

    // Set pour garder en mémoire quels chapitres sont dépliés
    private val chapitresDeplis = mutableSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapitre_list)

        tvTitreLivre = findViewById(R.id.tvTitreLivre)
        progressBar = findViewById(R.id.progressBar)
        tvPourcentage = findViewById(R.id.tvPourcentage)
        recyclerView = findViewById(R.id.recyclerViewChapitres)

        tvTitreLivre.text = livre.titre
        progressBar.progress = livre.progressionTotale
        tvPourcentage.text = "${livre.progressionTotale}%"

        // Le chapitre 1 est déplié par défaut
        chapitresDeplis.add(1)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ChapitreAdapter(
            chapitres = livre.chapitres,
            chapitresDeplis = chapitresDeplis,
            onChapitreClick = { chapitre ->
                // Toggle déplier/replier
                if (chapitresDeplis.contains(chapitre.id)) {
                    chapitresDeplis.remove(chapitre.id)
                } else {
                    chapitresDeplis.add(chapitre.id)
                }
                recyclerView.adapter?.notifyDataSetChanged()
            },
            onCommentairesClick = { chapitre ->
                // ROUTAGE : navigation vers DiscussionsActivity
                val intent = Intent(this, DiscussionsActivity::class.java).apply {
                    putExtra(DiscussionsActivity.EXTRA_CHAPITRE_ID, chapitre.id)
                    putExtra(DiscussionsActivity.EXTRA_CHAPITRE_TITRE, "${livre.titre} - chapitre ${chapitre.numero}")
                    putExtra(DiscussionsActivity.EXTRA_CHAPITRE_PROGRESSION, chapitre.progressionDebut)
                }
                startActivity(intent)
            }
        )
    }
}

// ============================================================
// ADAPTER
// ============================================================

class ChapitreAdapter(
    private val chapitres: List<Chapitre>,
    private val chapitresDeplis: Set<Int>,
    private val onChapitreClick: (Chapitre) -> Unit,
    private val onCommentairesClick: (Chapitre) -> Unit
) : RecyclerView.Adapter<ChapitreAdapter.ChapitreViewHolder>() {

    inner class ChapitreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitre: TextView = itemView.findViewById(R.id.tvChapitreTitre)
        val tvProgression: TextView = itemView.findViewById(R.id.tvChapitreProgression)
        val ivStatut: ImageView = itemView.findViewById(R.id.ivStatutChapitre)
        val ivChevron: ImageView = itemView.findViewById(R.id.ivChevron)
        val layoutSousMenu: View = itemView.findViewById(R.id.layoutSousMenu)
        val layoutCommentaires: View = itemView.findViewById(R.id.layoutCommentaires)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapitreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chapitre, parent, false)
        return ChapitreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChapitreViewHolder, position: Int) {
        val chapitre = chapitres[position]
        val estDeplie = chapitresDeplis.contains(chapitre.id)

        holder.tvTitre.text = "Chapitre ${chapitre.numero}"
        holder.tvProgression.text = "${chapitre.progressionDebut}%"

        // Icône statut
        if (chapitre.estComplete) {
            holder.ivStatut.setImageResource(android.R.drawable.checkbox_on_background)
        } else {
            holder.ivStatut.setImageResource(android.R.drawable.checkbox_off_background)
        }

        // Chevron
        if (estDeplie) {
            holder.ivChevron.setImageResource(android.R.drawable.arrow_down_float)
        } else {
            holder.ivChevron.setImageResource(android.R.drawable.ic_media_next)
        }

        // Sous-menu visible si déplié
        holder.layoutSousMenu.visibility = if (estDeplie) View.VISIBLE else View.GONE

        // Clic sur l'item → déplier/replier
        holder.itemView.setOnClickListener { onChapitreClick(chapitre) }

        // ROUTAGE : clic sur Commentaires → DiscussionsActivity
        holder.layoutCommentaires.setOnClickListener { onCommentairesClick(chapitre) }
    }

    override fun getItemCount(): Int = chapitres.size
}
