package com.example.gifsearch

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imageView = findViewById<ImageView>(R.id.detailImageView)
        val gifUrl = intent.getStringExtra("gif_url")

        Glide.with(this)
            .load(gifUrl)
            .into(imageView)
    }
}
