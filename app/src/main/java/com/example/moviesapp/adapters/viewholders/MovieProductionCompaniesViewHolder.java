package com.example.moviesapp.adapters.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.R;
import com.example.moviesapp.databinding.ProductionCompanyLayoutBinding;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

public class MovieProductionCompaniesViewHolder extends RecyclerView.ViewHolder {
    public ProductionCompanyLayoutBinding productionCompanyLayoutBinding;

    public MovieProductionCompaniesViewHolder(@NonNull ProductionCompanyLayoutBinding productionCompanyLayoutBinding) {
        super(productionCompanyLayoutBinding.getRoot());
        this.productionCompanyLayoutBinding=productionCompanyLayoutBinding;

    }

    public void setMovieProductionCompaniesImageView(Context context, String imageUrl) {
        Picasso.with(context).load(imageUrl).into(productionCompanyLayoutBinding.movieProductionCompanyImageView);
    }
}
