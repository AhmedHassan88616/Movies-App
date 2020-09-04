package com.example.moviesapp.adapters.person;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.R;
import com.example.moviesapp.adapters.viewholders.ImageViewHolder;
import com.example.moviesapp.databinding.ProfileImagesLayoutBinding;
import com.example.moviesapp.model.person.PersonImageProfile;
import com.example.moviesapp.ui.ImageViewerActivity;

import java.util.List;

public class PersonProfileImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    PersonProfileImageListener personProfileImageListener;
    List<PersonImageProfile> personImageProfileList;

    public PersonProfileImageAdapter(PersonProfileImageListener personProfileImageListener) {
        this.personProfileImageListener = personProfileImageListener;
    }

    public void setPersonImageProfileList(List<PersonImageProfile> personImageProfileList) {
        this.personImageProfileList = personImageProfileList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ProfileImagesLayoutBinding profileImagesLayoutBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.profile_images_layout, parent, false);
        return new ImageViewHolder(profileImagesLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {

        final PersonImageProfile personImageProfile = personImageProfileList.get(position);
        holder.profileImagesLayoutBinding.setPersonImageProfile(personImageProfile);
        holder.setProfileImage((Activity) personProfileImageListener, personImageProfile.getFilePath());
        holder.profileImagesLayoutBinding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent imageViewerIntent=new Intent(activity, ImageViewerActivity.class);
                ActivityOptions compat=ActivityOptions.makeSceneTransitionAnimation((Activity)personProfileImageListener,holder.profileImage, ViewCompat.getTransitionName(holder.profileImage));
                imageViewerIntent.putExtra("image_url",personImageProfile.getFilePath());
                activity.startActivity(imageViewerIntent,compat.toBundle());*/
                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation((Activity) personProfileImageListener, holder.profileImagesLayoutBinding.profileImage, ViewCompat.getTransitionName(holder.profileImagesLayoutBinding.profileImage));
                String imageUrl = personImageProfile.getFilePath();
                personProfileImageListener.openImageViewerActivity(imageUrl, compat);

            }
        });
    }

    @Override
    public int getItemCount() {
        return personImageProfileList.size();
    }

    public interface PersonProfileImageListener {
        void openImageViewerActivity(String imageUrl, ActivityOptions compat);
    }


}
