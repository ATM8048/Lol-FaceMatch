package ch.zli.lolfacematch

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)
        val champion = intent.getSerializableExtra("champion") as? Champion
        if (champion != null) {
            val nameOfChampionTextElement = findViewById<TextView>(R.id.textViewChampionName)
            val imageOfTheChampionElement = findViewById<ImageView>(R.id.ImageViewChampionImage)
            val imageUrl = "https://ddragon.leagueoflegends.com/cdn/14.3.1/img/champion/${champion.name}.png"
            nameOfChampionTextElement.text = champion.name
            Picasso
                .get()
                .load(imageUrl)
                .into(imageOfTheChampionElement)
        }
        findViewById<Button>(R.id.button_back).setOnClickListener {
            finish()
        }
    }
}
