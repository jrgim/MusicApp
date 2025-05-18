package es.usj.jglopez.individualtask.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import es.usj.jglopez.individualtask.databinding.ActivityEmailBinding

class EmailActivity : AppCompatActivity() {
    private val view by lazy { ActivityEmailBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(view.root)
    }
}