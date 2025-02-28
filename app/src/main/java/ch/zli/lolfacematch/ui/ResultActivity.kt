package ch.zli.lolfacematch.ui

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import ch.zli.lolfacematch.R
import ch.zli.lolfacematch.data.Champion
import com.squareup.picasso.Picasso

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        val champion = intent.getSerializableExtra("champion") as? Champion
        if (champion != null) {
            displayResult(champion)
        }
        findViewById<Button>(R.id.button_back).setOnClickListener {
            finish()
        }
    }

    private fun displayResult(champion: Champion) {
        val nameOfChampionTextElement = findViewById<TextView>(R.id.textViewChampionName)
        val imageOfTheChampionElement = findViewById<ImageView>(R.id.ImageViewChampionImage)
        nameOfChampionTextElement.text = champion.name
        Picasso
            .get()
            .load("https://ddragon.leagueoflegends.com/cdn/14.3.1/img/champion/${champion.name}.png")
            .into(imageOfTheChampionElement)
    }
}
