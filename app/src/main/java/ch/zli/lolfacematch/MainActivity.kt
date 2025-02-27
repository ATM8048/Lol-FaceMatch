package ch.zli.lolfacematch

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
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
        val intent = Intent(this, ResultActivity::class.java)
        startActivity(intent)
    }
}
