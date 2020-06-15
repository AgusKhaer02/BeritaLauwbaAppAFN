package com.beritalauwbaapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.beritalauwbaapp.core.ApiEndPoint
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var arrayList = ArrayList<DataBerita>()

    var var_berita : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_berita.setHasFixedSize(true)
        rv_berita.layoutManager = LinearLayoutManager(this)

        txtSearchBerita.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->

            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP){
                var_berita = txtSearchBerita.text.toString()
                Toast.makeText(this, "Hasil Pencarian "+var_berita+", Berhasil",Toast.LENGTH_SHORT).show()
                searchBerita(var_berita)

                txtHasilCari.visibility = View.VISIBLE
                txtHasilCari.text = "Hasil Pencarian yang serupa dengan "+var_berita

                return@OnKeyListener true
            }
            false

        })
    }

    override fun onResume() {
        super.onResume()
        loadBerita()
        txtHasilCari.visibility = View.GONE
    }

    private fun loadBerita(){
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat Data...")
        loading.show()
        AndroidNetworking.get(ApiEndPoint.READ)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
//                  Agar data arrayList tidak terduplikat oleh array list yang sebelumnya, maka tambahkan arrayList.clear()
                    arrayList.clear()
//                  pada "result", ini akan memanggil nama JSON pada file webservices.php pada folder webservices kita
                    val jsonArray = response?.optJSONArray("result")

                    if(jsonArray?.length() == 0){
                        loading.dismiss()
                        Toast.makeText(applicationContext, "Data Berita Kosong", Toast.LENGTH_SHORT).show()
                    }

//                  Jangan lupa membuat fungsi looping pada pemganggilan isi dati pada sebuah arrayList
                    for (i in 0 until jsonArray?.length()!!){
                        val jsonObject = jsonArray?.optJSONObject(i)

                        arrayList.add(DataBerita(
                            jsonObject.getString("judul_berita"),
                            jsonObject.getString("tgl_input"),
                            jsonObject.getString("detail_berita"),
                            jsonObject.getString("foto_berita")

                        ))

                        if (jsonArray?.length() - 1 == i){
                            loading.dismiss()
                            val adapter = RVBeritaAdapter(applicationContext, arrayList)
                            adapter.notifyDataSetChanged()
                            rv_berita.adapter = adapter
                        }
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorBody)
                    Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun searchBerita(judulBerita : String) {
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat Data...")
        loading.show()

        AndroidNetworking.get(ApiEndPoint.READ+"?cari=1&judul="+judulBerita)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
//                  Agar data arrayList tidak terduplikat oleh array list yang sebelumnya, maka tambahkan arrayList.clear()
                    arrayList.clear()
//                  pada "result", ini akan memanggil nama JSON pada file webservices.php pada folder webservices kita
                    val jsonArray = response?.optJSONArray("result")

                    if(jsonArray?.length() == 0){
                        loading.dismiss()
                        Toast.makeText(applicationContext, "Data Berita Kosong", Toast.LENGTH_SHORT).show()
                    }

//                  Jangan lupa membuat fungsi looping pada pemganggilan isi dati pada sebuah arrayList
                    for (i in 0 until jsonArray?.length()!!){
                        val jsonObject = jsonArray?.optJSONObject(i)

                        arrayList.add(DataBerita(
                            jsonObject.getString("judul_berita"),
                            jsonObject.getString("tgl_input"),
                            jsonObject.getString("detail_berita"),
                            jsonObject.getString("foto_berita")

                        ))

                        if (jsonArray?.length() - 1 == i){
                            loading.dismiss()
                            val adapter = RVBeritaAdapter(applicationContext, arrayList)
                            adapter.notifyDataSetChanged()
                            rv_berita.adapter = adapter
                        }
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT).show()
                }

            })
    }
}
