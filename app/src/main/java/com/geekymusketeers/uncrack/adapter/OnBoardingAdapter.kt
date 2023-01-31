package com.geekymusketeers.uncrack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.geekymusketeers.uncrack.R
import com.geekymusketeers.uncrack.model.Onboarding


class OnBoardingAdapter(
    private var slides : List<Onboarding>

) : RecyclerView.Adapter<OnBoardingAdapter.SlideAdapterViewHolder>(){



    inner class SlideAdapterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slide,parent,false)
        return SlideAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return slides.size
    }

    override fun onBindViewHolder(holder: SlideAdapterViewHolder, position: Int) {

        holder.itemView.apply {
            val current = slides[position]
            holder.itemView.findViewById<TextView>(R.id.SlideTitle).text = current.slidetitle
            holder.itemView.findViewById<LottieAnimationView>(R.id.lottieAnimationView).setImageResource(current.slideimage)

        }

    }
}