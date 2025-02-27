package ch.zli.lolfacematch

import android.os.Bundle
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val generateButton = findViewById<Button>(R.id.buttonGenerate)
        generateButton.setOnClickListener {
            navigateToResult()
        }
    }

    private fun navigateToResult() {
        PickImageDialog
            .build(PickSetup())
            .setOnPickResult {
                // TODO: do what you have to...
            }.setOnPickCancel {
                // TODO: do what you have to if user clicked cancel
            }.show(getSupportFragmentManager())
    }

    override fun onPickResult(r: PickResult) {
        if (r.error == null) {
            // If you want the Uri.
            // Mandatory to refresh image from Uri.
            // getImageView().setImageURI(null);

            // Setting the real returned image.
            // getImageView().setImageURI(r.getUri());

            // If you want the Bitmap.

            // Image path
            // r.getPath();
        } else {
            // Handle possible errors
            // TODO: do what you have to do with r.getError();
            Toast.makeText(this, r.error.message, Toast.LENGTH_LONG).show()
        }
    }
}
