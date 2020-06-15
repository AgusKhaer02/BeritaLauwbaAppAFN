package com.beritalauwbaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.beritalauwbaapp.core.ApiEndPoint
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_view_berita.*

class ViewBerita : AppCompatActivity() {

    lateinit var i : Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_berita)
        i = intent

        txtJudulBerita.setText(i.getStringExtra("judul_berita"))
        txtTanggalInput.setText(i.getStringExtra("tgl_input"))
        txtDetailBerita.setText(i.getStringExtra("detail_berita"))

        Glide.with(applicationContext)
            .load(ApiEndPoint.IMAGES+i.getStringExtra("foto_berita"))
            .placeholder(R.drawable.ic_launcher_background)
            .into(imgFotoBerita)
    }
}
