package com.example.moviesapp.adapters.movie;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.R;
import com.example.moviesapp.adapters.viewholders.MovieCreditsViewHolder;
import com.example.moviesapp.adapters.viewholders.MovieProductionCompaniesViewHolder;
import com.example.moviesapp.databinding.ProductionCompanyLayoutBinding;
import com.example.moviesapp.model.movie.MovieDetailsProductionCompany;
import com.example.moviesapp.model.movie.credits.MovieCreditsCast;
import com.example.moviesapp.ui.PersonDetailsActivity;

import java.util.List;

public class MovieProductionCompaniesAdapter extends RecyclerView.Adapter<MovieProductionCompaniesViewHolder> {

    private Activity activity;
    private List<MovieDetailsProductionCompany> movieDetailsProductionCompanyList;

    public MovieProductionCompaniesAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setMovieDetailsProductionCompanyList(List<MovieDetailsProductionCompany> movieDetailsProductionCompanyList) {
        this.movieDetailsProductionCompanyList = movieDetailsProductionCompanyList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieProductionCompaniesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductionCompanyLayoutBinding productionCompanyLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.production_company_layout, parent, false);
        return new MovieProductionCompaniesViewHolder(productionCompanyLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieProductionCompaniesViewHolder holder, int position) {
        final MovieDetailsProductionCompany movieDetailsProductionCompany = movieDetailsProductionCompanyList.get(position);
        holder.productionCompanyLayoutBinding.setMovieDetailsProductionCompany(movieDetailsProductionCompany);
        holder.setMovieProductionCompaniesImageView(activity, movieDetailsProductionCompany.getLogoPath());

        holder.productionCompanyLayoutBinding.movieProductionCompanyName.setText(movieDetailsProductionCompany.getName());


    }

    @Override
    public int getItemCount() {
        return movieDetailsProductionCompanyList.size();
    }
}
