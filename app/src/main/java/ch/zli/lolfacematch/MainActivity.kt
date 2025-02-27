package ch.zli.lolfacematch

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.vansuita.pickimage.bean.PickResult
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.vansuita.pickimage.listeners.IPickResult

class MainActivity :
    AppCompatActivity(),
    IPickResult {
    private lateinit var faceAnalyzer: FaceAnalyzer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        faceAnalyzer = FaceAnalyzer()
        val generateButton = findViewById<Button>(R.id.buttonGenerate)
        generateButton.setOnClickListener {
            navigateToResult()
        }
    }

    private fun navigateToResult() {
        PickImageDialog
            .build(PickSetup())
            .setOnPickResult { result ->
                onPickResult(result)
            }.setOnPickCancel {
                // TODO: do what you have to if user clicked cancel
            }.show(supportFragmentManager)
    }

    override fun onPickResult(r: PickResult) {
        Log.d("Test", r.bitmap.toString())
        if (r.error == null) {
            Log.d("ImagePicker", "Bild erfolgreich ausgewählt!")
            Log.d("ImagePicker", "Bitmap vorhanden: ${r.bitmap != null}")

            if (r.bitmap != null) {
                faceAnalyzer.analyzeFace(
                    r.bitmap,
                    0,
                    onResult = { faces ->
                        Log.d("FaceAnalyzer", "Gesichter erkannt: ${faces.size}")
                        for (face in faces) {
                            val faceFeatures = faceAnalyzer.getFaceFeatures(face)
                        }
                    },
                    onFailure = { exception ->
                        Log.e("FaceAnalyzer", "Fehler bei der Gesichtserkennung", exception)
                    },
                )
            } else {
                Log.e("ImagePicker", "Kein Bitmap erhalten!")
            }
        } else {
            Log.e("ImagePicker", "Fehler beim Bildauswählen: ${r.error.message}")
            Toast.makeText(this, r.error.message, Toast.LENGTH_LONG).show()
        }
    }
}
