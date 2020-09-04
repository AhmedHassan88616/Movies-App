package com.example.moviesapp.adapters.viewholders;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.R;
import com.example.moviesapp.databinding.ProfileImagesLayoutBinding;
import com.squareup.picasso.Picasso;

public class ImageViewHolder extends RecyclerView.ViewHolder {

    public ProfileImagesLayoutBinding profileImagesLayoutBinding;

    public ImageViewHolder(@NonNull ProfileImagesLayoutBinding profileImagesLayoutBinding) {
        super(profileImagesLayoutBinding.getRoot());
        this.profileImagesLayoutBinding = profileImagesLayoutBinding;
    }

    public void setProfileImage(Activity activity, String filePath) {
        Picasso.with(activity).load(filePath).into(profileImagesLayoutBinding.profileImage);
    }
}

