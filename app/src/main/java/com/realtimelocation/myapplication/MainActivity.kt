package com.realtimelocation.myapplication

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.gson.Gson
import com.realtimelocation.myapplication.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.nio.charset.StandardCharsets


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val spinner = parent as Spinner
        if (spinner.id == R.id.spinner1) {
            GetAllArtists(spinner.getSelectedItem() as String).execute()
            songType = spinner.getSelectedItem() as String
        } else if (spinner.id == R.id.spinner2) {
            numberofColumb = spinner.getSelectedItem() as Int
            GetAllArtists(songType).execute()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    private var songType: String = "Artist"
    private var numberofColumb: Int = 1
    private lateinit var context: Context
    private var musicDataEntity: MusicDataEntity? = null
    private var db: RoomDatabase? = null
    private var rvMain: RecyclerView? = null
    private var leftSpinnerList: List<String>? = null
    private var rightSpinnerList: List<Int>? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var spinner: Spinner
    private lateinit var spinner2: Spinner
    var listSpinner1 = arrayOf("Artist", "Album")
    var listSpinner2 = arrayOf(1, 2, 3, 4, 5)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        context = this
        val gson = Gson()
        db = RoomDatabase.getAppDatabase(this)

        DeleteData().execute()
        spinner = findViewById(R.id.spinner1) as Spinner
        spinner2 = findViewById(R.id.spinner2) as Spinner
        spinner.setOnItemSelectedListener(this@MainActivity)
        spinner2.setOnItemSelectedListener(this@MainActivity)
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, listSpinner1)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(aa)
        val bb = ArrayAdapter(this, android.R.layout.simple_spinner_item, listSpinner2)
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.setAdapter(bb)
        musicDataEntity = gson.fromJson(loadJSONFromAsset(), MusicDataEntity::class.java!!)
        if (musicDataEntity != null && musicDataEntity!!.tableEntity.size > 0)
            SaveData(musicDataEntity!!.tableEntity).execute()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


    }


    private fun loadJSONFromAsset(): String? {
        var json: String = ""
        try {
            val `is` = getAssets().open("music_data.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                json = String(buffer, StandardCharsets.UTF_8)
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }

    private inner class SaveData(private val tableEntity: List<TableEntity>?) :
        AsyncTask<Void, Void, Void>() {
        init {
            db = RoomDatabase.getAppDatabase(this@MainActivity)

        }

        override fun doInBackground(vararg voids: Void): Void? {

            try {
                if (tableEntity != null && tableEntity.size > 0) {
                    for (i in tableEntity.indices) {
                        val tableEntity = tableEntity[i]
                        db!!.daoForRoom().insert(tableEntity)
                    }
                }

            } catch (e: Exception) {

            }

            // TinyDB(applicationContext).putBoolean("loader_data_inserted_in_room", true)
            return null
        }

        override fun onPreExecute() {
            super.onPreExecute()
            GetAllArtists("Artist").execute()
        }

    }


    private inner class GetAllArtists(private val s: String) : AsyncTask<Void, Void, List<String>>() {

        override fun doInBackground(vararg voids: Void): List<String> {
            if (s.equals("Artist"))
                return db!!.daoForRoom().allArtists
            else
                return db!!.daoForRoom().allAlbumb
        }

        override fun onPostExecute(artistsList: List<String>) {
            super.onPostExecute(artistsList)

            if (artistsList != null && artistsList.size > 0) {
                binding.rvMain!!.setLayoutManager(LinearLayoutManager(this@MainActivity))
                if (s.equals("Artist")) {
                    val adapter = AdapterMain(context, artistsList, true, numberofColumb)
                    binding.rvMain!!.setAdapter(adapter)
                } else {
                    val adapter = AdapterMain(context, artistsList, false, numberofColumb)
                    binding.rvMain!!.setAdapter(adapter)
                }


            }

        }
    }


    private inner class DeleteData :
        AsyncTask<Void, Void, Void>() {
        init {
            db = RoomDatabase.getAppDatabase(this@MainActivity)

        }

        override fun doInBackground(vararg voids: Void): Void? {


            try {
                db!!.daoForRoom().nukeTable()
            } catch (e: Exception) {
            }




            return null
        }


    }
}
