package com.crazzylab.rdwallpapers.ui.prefs

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.crazzylab.rdwallpapers.R
import com.crazzylab.rdwallpapers.entity.ColorModel
import com.crazzylab.rdwallpapers.extensions.inflate

/**
 * Created by Михаил on 31.01.2018.
 */
class ColorsAdapter(private val colorList: List<ColorModel>,
                    private val themeMode: Int) : RecyclerView.Adapter<ColorsAdapter.ColorsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorsViewHolder {
        val view = parent.inflate(R.layout.colors)
        return ColorsViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int = position

    override fun onBindViewHolder(holder: ColorsViewHolder, position: Int) {
        val item = colorList[position]
        holder.colorImage.setImageResource(item.colorImage)
        holder.colorName.text = item.colorName
        if (getItemViewType(position) == themeMode) {
            holder.colorTick.setImageResource(R.drawable.ic_done)
        }
    }

    override fun getItemCount(): Int = colorList.size

    inner class ColorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val colorImage: ImageView = itemView.findViewById(R.id.colorImage)
        val colorTick: ImageView = itemView.findViewById(R.id.colorTick)
        val colorName: TextView = itemView.findViewById(R.id.colorName)
    }
}
