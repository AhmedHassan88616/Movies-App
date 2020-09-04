package com.example.moviesapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesapp.R;
import com.example.moviesapp.adapters.person.PersonProfileImageAdapter;
import com.example.moviesapp.app.MyApplication;
import com.example.moviesapp.client.RetrofitClient;
import com.example.moviesapp.di.component.ApplicationComponent;
import com.example.moviesapp.di.component.DaggerPersonDetailsActivityComponent;
import com.example.moviesapp.di.component.PersonDetailsActivityComponent;
import com.example.moviesapp.di.module.PersonDetailsActivityContextModule;
import com.example.moviesapp.model.person.PersonDetails;
import com.example.moviesapp.model.person.PersonImageProfile;
import com.example.moviesapp.model.person.PersonImages;
import com.example.moviesapp.viewmodel.PersonDetailActivityViewModel;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

public class PersonDetailsActivity extends AppCompatActivity implements PersonProfileImageAdapter.PersonProfileImageListener {


    private KenBurnsView personDetailProfileImageView;

    private LinearLayout personDetailKnownAsLayout;
    private LinearLayout personBirthdayLayout;
    private LinearLayout personPlaceOfBirthLayout;
    private LinearLayout personDeathdayLayout;
    private LinearLayout personKnownForDepartmentLayout;
    private LinearLayout personBiographyLayout;
    private LinearLayout personPopularityLayout;
    private LinearLayout personHomepageLayout;
    private LinearLayout personDetailProfileImagesLayout;
    private RecyclerView personDetailProfileImagesRecyclerView;

    @Inject
    PersonProfileImageAdapter personProfileImageAdapter;

    @Inject
    ViewModelProvider viewModelProvider;
    private TextView personDetailKnownAs;
    private TextView personBirthday;
    private TextView personPlaceOfBirth;
    private TextView personDeathday;
    private TextView personKnownForDepartment;
    private TextView personBiography;
    private TextView personPopularity;
    private TextView personHomepage;
    private TextView personDetailName;


    private static final String TAG = "PersonDetailsActivity";
    @Inject
    RetrofitClient retrofitClient;
    private String id;

    private PersonDetailActivityViewModel personDetailActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);

        Intent intent = getIntent();

        // initiate the retrofit services
        retrofitClient = RetrofitClient.getInstance();

        personDetailProfileImageView = findViewById(R.id.person_profile_image_view);
        personDetailKnownAsLayout = findViewById(R.id.person_detail_known_as_layout);
        personBirthdayLayout = findViewById(R.id.person_birthday_layout);
        personPlaceOfBirthLayout = findViewById(R.id.person_place_of_birth_layout);
        personDeathdayLayout = findViewById(R.id.person_deathday_layout);
        personKnownForDepartmentLayout = findViewById(R.id.person_known_for_department_layout);
        personBiographyLayout = findViewById(R.id.person_biography_layout);
        personPopularityLayout = findViewById(R.id.person_popularity_layout);
        personHomepageLayout = findViewById(R.id.person_homepage_layout);
        personDetailProfileImagesLayout = findViewById(R.id.person_detail_profile_images_layout);


        personDetailName = findViewById(R.id.person_detail_name);
        personDetailKnownAs = findViewById(R.id.person_detail_known_as);
        personBirthday = findViewById(R.id.person_detail_birthday);
        personPlaceOfBirth = findViewById(R.id.person_place_of_birth);
        personDeathday = findViewById(R.id.person_deathday);
        personKnownForDepartment = findViewById(R.id.person_known_for_department);
        personBiography = findViewById(R.id.person_biography);
        personPopularity = findViewById(R.id.person_popularity);
        personHomepage = findViewById(R.id.person_homepage);


        personDetailProfileImagesRecyclerView = findViewById(R.id.person_detail_profile_images_recycler_view);


        personDetailProfileImagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));


        ApplicationComponent applicationComponent = MyApplication.get(this).getApplicationComponent();
        PersonDetailsActivityComponent personDetailsActivityComponent = DaggerPersonDetailsActivityComponent.builder()
                .personDetailsActivityContextModule(new PersonDetailsActivityContextModule(this))
                .applicationComponent(applicationComponent)
                .build();
        personDetailsActivityComponent.injectPersonDetailsActivity(this);

        personDetailActivityViewModel = viewModelProvider.get(PersonDetailActivityViewModel.class);


        if (null != intent && null != intent.getExtras()) {

            id = intent.getExtras().getString("id");

            preparePersonDetailsResponse();

            preparePersonImages();

        }
    }

    private void preparePersonImages() {

        personDetailActivityViewModel.getPersonImagesLiveData(retrofitClient, id)
                .observe(PersonDetailsActivity.this, new Observer<PersonImages>() {
                    @Override
                    public void onChanged(PersonImages personImages) {
                        List<PersonImageProfile> personImageProfileList = personImages.getPersonImageProfiles();
                        if (personImageProfileList != null && personImageProfileList.size() != 0) {
                            personDetailProfileImagesLayout.setVisibility(View.VISIBLE);
                            personProfileImageAdapter.setPersonImageProfileList(personImageProfileList);
                            personDetailProfileImagesRecyclerView.setAdapter(personProfileImageAdapter);

                            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(PersonDetailsActivity.this, R.anim.layout_slide_right);
                            personDetailProfileImagesRecyclerView.setLayoutAnimation(controller);
                            personDetailProfileImagesRecyclerView.scheduleLayoutAnimation();

                        } else {
                            personDetailProfileImagesLayout.setVisibility(View.GONE);
                        }
                    }
                });


    }

    private void preparePersonDetailsResponse() {

        personDetailActivityViewModel.getPersonDetailsLiveData(retrofitClient, id)
                .observe(PersonDetailsActivity.this, new Observer<PersonDetails>() {
                    @Override
                    public void onChanged(PersonDetails personDetails) {
                        if (null != personDetails) {
                            preparePersonDetails(personDetails);
                        } else {
                            Toast.makeText(PersonDetailsActivity.this, "no any data found!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private void preparePersonDetails(PersonDetails personDetailsResponse) {

        String profilePath = personDetailsResponse.getProfilePath();

        String birthday = personDetailsResponse.getBirthday();
        String placeOfBirth = personDetailsResponse.getPlaceOfBirth();
        String deathday = personDetailsResponse.getDeathday();
        String department = personDetailsResponse.getKnownForDepartment();
        String biography = personDetailsResponse.getBiography();
        String homepage = personDetailsResponse.getHomepage();
        String name = personDetailsResponse.getName();

        List<String> personKnownAs = personDetailsResponse.getAlsoKnownAs();

        Picasso.with(this).load(profilePath).into(personDetailProfileImageView);


        if (null != name) {
            if (name.length() != 0) {
                personDetailName.append(name);
                personDetailName.setVisibility(View.VISIBLE);
            } else {
                personDetailName.setVisibility(View.GONE);
            }
        } else {
            personDetailName.setVisibility(View.GONE);
        }


        if (null != personKnownAs) {
            if (personKnownAs.size() != 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < personKnownAs.size(); ++i) {
                    if (i == personKnownAs.size() - 1) {
                        stringBuilder.append(personKnownAs.get(i) + ".");
                    } else {
                        stringBuilder.append(personKnownAs.get(i) + ", ");
                    }
                }
                personDetailKnownAs.append(stringBuilder.toString());
                personDetailKnownAsLayout.setVisibility(View.VISIBLE);
            } else {
                personDetailKnownAsLayout.setVisibility(View.GONE);
            }
        } else {
            personDetailKnownAsLayout.setVisibility(View.GONE);
        }


        if (null != placeOfBirth) {
            if (placeOfBirth.length() != 0) {
                personPlaceOfBirth.append(placeOfBirth);
                personPlaceOfBirthLayout.setVisibility(View.VISIBLE);
            } else {
                personPlaceOfBirthLayout.setVisibility(View.GONE);
            }
        } else {
            personPlaceOfBirthLayout.setVisibility(View.GONE);
        }


        if (null != birthday) {
            if (birthday.length() != 0) {
                personBirthday.append(birthday);
                personPlaceOfBirthLayout.setVisibility(View.VISIBLE);
            } else {
                personPlaceOfBirthLayout.setVisibility(View.GONE);
            }
        } else {
            personPlaceOfBirthLayout.setVisibility(View.GONE);
        }


        if (null != deathday) {
            if (deathday.length() != 0) {
                personDeathday.append(deathday);
                personDeathdayLayout.setVisibility(View.VISIBLE);
            } else {
                personDeathdayLayout.setVisibility(View.GONE);
            }
        } else {
            personDeathdayLayout.setVisibility(View.GONE);
        }


        if (null != department) {
            if (department.length() != 0) {
                personKnownForDepartment.append(department);
                personKnownForDepartmentLayout.setVisibility(View.VISIBLE);
            } else {
                personKnownForDepartmentLayout.setVisibility(View.GONE);
            }
        } else {
            personKnownForDepartmentLayout.setVisibility(View.GONE);
        }


        if (null != biography) {
            if (biography.length() != 0) {
                personBiography.append(biography);
                personBiographyLayout.setVisibility(View.VISIBLE);
            } else {
                personBiographyLayout.setVisibility(View.GONE);
            }
        } else {
            personBiographyLayout.setVisibility(View.GONE);
        }


        if (null != homepage) {
            if (homepage.length() != 0) {
                personHomepage.append(homepage);
                personHomepageLayout.setVisibility(View.VISIBLE);
            } else {
                personHomepageLayout.setVisibility(View.GONE);
            }
        } else {
            personHomepageLayout.setVisibility(View.GONE);
        }


    }


    // set animation for back to main activity
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void openImageViewerActivity(String imageUrl, ActivityOptions compat) {
        Intent imageViewerIntent = new Intent(this, ImageViewerActivity.class);
        imageViewerIntent.putExtra("image_url", imageUrl);
        startActivity(imageViewerIntent, compat.toBundle());
    }
}
