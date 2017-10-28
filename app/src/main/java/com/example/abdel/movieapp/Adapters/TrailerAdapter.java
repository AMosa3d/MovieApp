package com.example.abdel.movieapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.abdel.movieapp.Interfaces.DetailManagerInterface;
import com.example.abdel.movieapp.Models.Trailer;
import com.example.abdel.movieapp.R;

import java.util.List;

/**
 * Created by abdel on 10/22/2017.
 */

public class TrailerAdapter extends BaseAdapter {

    private DetailManagerInterface mDetailManagerInterface;

    private List<Trailer> trailersList;

    private Context context;

    public TrailerAdapter(DetailManagerInterface detailManagerInterface, Context context) {
        this.mDetailManagerInterface = detailManagerInterface;
        this.context = context;
    }

    public void setTrailerList(List<Trailer> trailersList) {
        this.trailersList = trailersList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (trailersList == null)
            return 0;
        return trailersList.size();
    }

    @Override
    public Object getItem(int position) {
        return trailersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView = inflater.inflate(R.layout.trailer_list_single_item,parent,false);

        TextView textView = (TextView) convertView.findViewById(R.id.trailer_name);

        textView.setText("Trailer " + (position+1));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailManagerInterface.onTrailerClick(trailersList.get(position));
            }
        });

        return convertView;
    }

}
