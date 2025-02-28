package ch.zli.lolfacematch.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import ch.zli.lolfacematch.R
import ch.zli.lolfacematch.analysis.FaceAnalyzer
import ch.zli.lolfacematch.data.Champion
import ch.zli.lolfacematch.data.ChampionFetcher
import com.vansuita.pickimage.bean.PickResult
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.vansuita.pickimage.listeners.IPickResult

class MainActivity :
    AppCompatActivity(),
    IPickResult {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
    }

    override fun onPickResult(r: PickResult) {
        if (r.error == null || r.bitmap != null) {
            val faceAnalyzer = FaceAnalyzer()
            faceAnalyzer.analyzeFace(
                r.bitmap,
                0,
                onResult = { faces ->
                    if (faces.isNotEmpty()) {
                        val faceFeatures = faceAnalyzer.getFaceFeatures(faces[0])
                        val fetcher = ChampionFetcher()
                        fetcher.fetchChampionsData { champions ->
                            if (champions != null) {
                                val randomChamp = fetcher.getRandomChampion(faceFeatures)
                                navigateToResultActivity(randomChamp)
                            }
                        }
                    } else {
                        navigateToResultActivity(
                            Champion(id = "Teemo", title = "The Swift Scout", name = "Teemo", tags = listOf("Marksman")),
                        )
                    }
                },
                onFailure = { exception -> Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show() },
            )
        } else {
            Toast.makeText(this, r.error.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun navigateToResultActivity(randomChamp: Champion) {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("champion", randomChamp)
        startActivity(intent)
    }

    fun openPickImageDialog(view: View) {
        PickImageDialog
            .build(PickSetup())
            .setOnPickResult { result -> onPickResult(result) }
            .setOnPickCancel {}
            .show(supportFragmentManager)
    }
}
