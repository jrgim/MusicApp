package es.usj.jglopez.individualtask.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.usj.jglopez.individualtask.databinding.ActivityCallBinding

class CallActivity : AppCompatActivity() {
    private val view by lazy { ActivityCallBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)
    }
}