package com.example.abdel.movieapp.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.abdel.movieapp.Interfaces.MovieRecyclerInterface;
import com.example.abdel.movieapp.Models.Movie;
import com.example.abdel.movieapp.R;
import com.example.abdel.movieapp.utilities.NetworkUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by abdel on 9/26/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private MovieRecyclerInterface mMovieRecyclerInterface;

    private List<Movie> moviesList;

    private Context context;

    public MovieAdapter(MovieRecyclerInterface mMovieRecyclerInterface, Context context) {
        this.mMovieRecyclerInterface = mMovieRecyclerInterface;
        this.context = context;
    }

    public void setMoviesList(List<Movie> moviesList) {
        this.moviesList = moviesList;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_list_single_item,parent,false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(moviesList.get(position).getPoster());
    }

    @Override
    public int getItemCount() {
        if (moviesList == null)
            return 0;
        return moviesList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView imageView;
        public MovieViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.im_list_movie_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mMovieRecyclerInterface.onClick(moviesList.get(getAdapterPosition()));
        }

        void bind(String path){
            String preferredSize = "w500";
            Uri uri = NetworkUtil.buildImageUri(path,preferredSize);

            Picasso.with(context).load(uri).placeholder(R.mipmap.ic_launcher).into(imageView);
        }
    }
}
