package com.beritalauwbaapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beritalauwbaapp.core.ApiEndPoint
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.berita_list.view.*

class RVBeritaAdapter(private val context: Context, private val arrayList: ArrayList<DataBerita>) : RecyclerView.Adapter<RVBeritaAdapter.Holder>() {
    class Holder(val view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.berita_list,parent,false))
    }

    override fun getItemCount(): Int = arrayList!!.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.view.cvList.setOnClickListener {
            val i = Intent(context,ViewBerita::class.java)
            i.putExtra("judul_berita", arrayList?.get(position)?.judul_berita)
            i.putExtra("tgl_input", arrayList?.get(position)?.tgl_input)
            i.putExtra("detail_berita", arrayList?.get(position)?.detail_berita)
            i.putExtra("foto_berita", arrayList?.get(position)?.foto_berita)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }

        holder.view.lbJudulBerita.text = arrayList?.get(position)?.judul_berita
        holder.view.lbTanggalInput.text = arrayList?.get(position)?.tgl_input
        holder.view.lbDetailBerita.text = arrayList?.get(position)?.detail_berita
        Glide.with(context)
            .load(ApiEndPoint.IMAGES+arrayList?.get(position)?.foto_berita)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.view.fotoBerita)
    }
}