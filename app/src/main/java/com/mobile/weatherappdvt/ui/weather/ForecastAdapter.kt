package com.mobile.weatherappdvt.ui.weather

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobile.weatherappdvt.R
import com.mobile.weatherappdvt.databinding.DayForecastListItemBinding
import com.mobile.weatherappdvt.model.ForecastListItem

class ForecastAdapter(private val context: Context) : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    private var forecasts: List<ForecastListItem> = ArrayList()

    class ViewHolder(binding: DayForecastListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val day = binding.day
        val image = binding.image
        val temp = binding.temp
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: DayForecastListItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.day_forecast_list_item, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecast = forecasts[position]

        if (forecasts.isNotEmpty()) {
            holder.day.text = forecast.day
            val imageDrawable = getImageDrawable(forecast.imageDrawableId)
            holder.image.setImageDrawable(imageDrawable)
            holder.temp.text = convertToTempFormat(forecast.temp)
        }
    }

    private fun getImageDrawable(drawableId: Int): Drawable? {
        return drawableId.let { drawable -> ResourcesCompat.getDrawable(context.resources, drawable, null) }
    }

    private fun convertToTempFormat(it: String?): String = context.getString(R.string.temp, it)

    override fun getItemCount(): Int {
        return forecasts.size
    }

    fun updateForecasts(newForecasts: List<ForecastListItem>) {
        forecasts = newForecasts
        notifyDataSetChanged()
    }
}