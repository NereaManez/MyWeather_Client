package com.dam.proyectodamdaw.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.proyectodamdaw.Parameters;
import com.dam.proyectodamdaw.R;
import com.dam.proyectodamdaw.base.ImageDownloader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    //private List<User> list;
    private Root root;
    private LayoutInflater inflater;
    private View.OnClickListener onClickListener;
    private Context context;

    public MyRecyclerViewAdapter(Context context, Root root) {
        this.root = root;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_view_element, parent, false);
        view.setOnClickListener(onClickListener);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {
        String iconUrl = root.list.get(position).weather.get(0).icon;
        ImageDownloader.DownloadImage(context,Parameters.URL_ICON_PRE + iconUrl + Parameters.URL_ICON_POST, holder.weatherImg, holder.weatherImg.getScaleType(), R.mipmap.ic_launcher);

        holder.weatherText.setText(root.list.get(position).weather.get(0).description);
        holder.tempText.setText(root.list.get(position).main.temp + "ºC");
        holder.maxTempText.setText(root.list.get(position).main.temp_max + "ºC");
        holder.minTempText.setText(root.list.get(position).main.temp_min + "ºC");

        Date date = new Date(root.list.get(position).dt * 1000L);

        holder.day.setText(new SimpleDateFormat("EEEE", new Locale( "es" , "ES" )).format(date));
        holder.date.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        holder.hour.setText(new SimpleDateFormat("HH:mm").format(date));
    }

    @Override
    public int getItemCount() {
        return root.list.size();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView weatherImg;
        TextView day;
        TextView date;
        TextView hour;
        TextView weatherText;
        TextView tempText;
        TextView maxTempText;
        TextView minTempText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            weatherImg = itemView.findViewById(R.id.weatherImg);
            day = itemView.findViewById(R.id.day);
            date = itemView.findViewById(R.id.date);
            hour = itemView.findViewById(R.id.hour);
            weatherText = itemView.findViewById(R.id.weatherText);
            tempText = itemView.findViewById(R.id.tempText);
            maxTempText = itemView.findViewById(R.id.maxTempText);
            minTempText = itemView.findViewById(R.id.minTempText);
        }
    }
}