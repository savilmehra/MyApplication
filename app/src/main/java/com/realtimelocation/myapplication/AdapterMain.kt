package com.realtimelocation.myapplication

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.realtimelocation.myapplication.databinding.MainRvItemBinding


private var db: RoomDatabase? = null

class AdapterMain(
    private val mContext: Context, private val _list: List<String>?, private var isArtist: Boolean,private  var numberofColumb:Int
) : RecyclerView.Adapter<AdapterMain.RowViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        db = RoomDatabase.getAppDatabase(mContext)
        val binding: MainRvItemBinding = DataBindingUtil.inflate(mInflater, R.layout.main_rv_item, parent, false)

        return RowViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return _list!!.size
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) =
        holder.bind(_list!![holder.adapterPosition], holder)

    inner class RowViewHolder(val binding: MainRvItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(name: String, holder: RowViewHolder) {
            with(binding) {
                if (name != null)
                    artistName.text = name
                GetAllSongs(binding, name).execute()

            }
        }
    }

    private inner class GetAllSongs(private val bind: MainRvItemBinding, private val name: String) :
        AsyncTask<Void, Void, List<String>>() {

        override fun doInBackground(vararg voids: Void): List<String> {
            if (isArtist)
                return db!!.daoForRoom().getAllSsongsOfArtist(name)
            else
                return db!!.daoForRoom().getAllSsongsOfAlbumb(name)
        }

        override fun onPostExecute(songs: List<String>) {
            super.onPostExecute(songs)
            val gridLayoutManager = GridLayoutManager(mContext, numberofColumb, LinearLayoutManager.HORIZONTAL, false)
            bind.rvSongs!!.setLayoutManager(gridLayoutManager)
            val adapter = SubAdapter(mContext, songs)
            bind.rvSongs!!.setAdapter(adapter)

        }
    }
}
