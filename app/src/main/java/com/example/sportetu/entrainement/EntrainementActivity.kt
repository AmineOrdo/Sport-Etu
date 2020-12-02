package com.example.sportetu.entrainement

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportetu.Adapter
import com.example.sportetu.R
import kotlinx.android.synthetic.main.activity_entrainement.*


class EntrainementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrainement)



        entrainementRecyclerView.layoutManager = LinearLayoutManager(this)
        entrainementRecyclerView.adapter = Adapter()
    }
}