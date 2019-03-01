package com.realtimelocation.myapplication

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.realtimelocation.myapplication.databinding.MainRvItemBinding
import com.realtimelocation.myapplication.databinding.SongsRecyclerViewBinding
import android.icu.lang.UCharacter.GraphemeClusterBreak.L




private var db: RoomDatabase? = null

class SubAdapter(
    private val mContext: Context, private val _list: List<String>?
) : RecyclerView.Adapter<SubAdapter.RowViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        db = RoomDatabase.getAppDatabase(mContext)
        val binding: SongsRecyclerViewBinding = DataBindingUtil.inflate(mInflater, R.layout.songs_recycler_view, parent, false)

        return RowViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return _list!!.size
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) =
        holder.bind(_list!![holder.adapterPosition], holder)

    inner class RowViewHolder(val binding: SongsRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(name: String, holder: RowViewHolder) {
            with(binding) {
                songName.text = name




            }
        }
    }


}
