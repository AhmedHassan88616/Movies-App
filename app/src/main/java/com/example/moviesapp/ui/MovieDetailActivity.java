package com.example.moviesapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
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

import com.codewaves.youtubethumbnailview.ThumbnailLoader;
import com.example.moviesapp.R;
import com.example.moviesapp.adapters.movie.MovieCreditsCastAdapter;
import com.example.moviesapp.adapters.movie.MovieCreditsCrewAdapter;
import com.example.moviesapp.adapters.movie.MoviePosterImageAdapter;
import com.example.moviesapp.adapters.movie.MovieProductionCompaniesAdapter;
import com.example.moviesapp.adapters.movie.MovieVideosAdapter;
import com.example.moviesapp.app.MyApplication;
import com.example.moviesapp.client.RetrofitClient;
import com.example.moviesapp.di.component.ApplicationComponent;
import com.example.moviesapp.di.component.DaggerMovieDetailsActivityComponent;
import com.example.moviesapp.di.component.MovieDetailsActivityComponent;
import com.example.moviesapp.di.module.MovieDetailsActivityContextModule;
import com.example.moviesapp.model.movie.MovieBackdropsandPosters;
import com.example.moviesapp.model.movie.MovieDetailGenre;
import com.example.moviesapp.model.movie.MovieDetails;
import com.example.moviesapp.model.movie.MovieDetailsProductionCompany;
import com.example.moviesapp.model.movie.MovieDetailsProductionCountry;
import com.example.moviesapp.model.movie.MovieImages;
import com.example.moviesapp.model.movie.MovieVideos;
import com.example.moviesapp.model.movie.MovieVideosResult;
import com.example.moviesapp.model.movie.credits.MovieCredits;
import com.example.moviesapp.model.movie.credits.MovieCreditsCast;
import com.example.moviesapp.model.movie.credits.MovieCreditsCrew;
import com.example.moviesapp.viewmodel.MovieDetailActivityViewModel;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

public class MovieDetailActivity extends AppCompatActivity implements MovieVideosAdapter.MovieVideosListener, MoviePosterImageAdapter.MoviePosterImageListener, MovieCreditsCrewAdapter.MovieCreditsCrewListener, MovieCreditsCastAdapter.MovieCreditsCastListener {

    private static final String TAG = "MovieDetailActivity";
    private KenBurnsView moviePosterImageView;
    private CircleImageView movieDetailPosterCircleImageView;
    private CircleProgress movieDetailRatingBar;


    private LinearLayout movieDetailOriginalTitleLayout;
    private LinearLayout movieDetailOriginalLanguageLayout;
    private LinearLayout movieDetailAdultLayout;
    private LinearLayout movieDetailStatusLayout;
    private LinearLayout movieDetailRuntimeLayout;
    private LinearLayout movieDetailBudgetLayout;
    private LinearLayout movieDetailRevenueLayout;
    private LinearLayout movieDetailGenreLayout;
    private LinearLayout movieDetailProductionCountryLayout;
    private LinearLayout movieDetailReleaseDateLayout;
    private LinearLayout movieDetailHomepageLayout;
    private LinearLayout movieDetailOverviewLayout;
    private LinearLayout movieDetailCastLayout;
    private LinearLayout movieDetailCrewLayout;
    private LinearLayout movieDetailProductionCompanyLayout;
    private LinearLayout movieDetailImagesLayout;
    private LinearLayout movieDetailVideosLayout;


    private TextView movieDetailOriginalTitle;
    private TextView movieDetailOriginalLanguage;
    private TextView movieDetailAdult;
    private TextView movieDetailStatus;
    private TextView movieDetailRuntime;
    private TextView movieDetailBudget;
    private TextView movieDetailRevenue;
    private TextView movieDetailGenre;
    private TextView movieDetailProductionCountry;
    private TextView movieDetailReleaseDate;
    private TextView movieDetailHomepage;
    private TextView movieDetailOverview;
    private TextView movieDetailTitle;


    private RecyclerView movieDetailCastRecyclerView;
    private RecyclerView movieDetailCrewRecyclerView;
    private RecyclerView movieDetailProductionCompanyRecyclerView;
    private RecyclerView movieDetailImagesRecyclerView;
    private RecyclerView movieDetailVideosRecyclerView;


    @Inject
     RetrofitClient retrofitClient;
    @Inject
     MovieCreditsCastAdapter movieCreditsCastAdapter;
    @Inject
     MovieCreditsCrewAdapter movieCreditsCrewAdapter;
    @Inject
     MovieProductionCompaniesAdapter movieProductionCompaniesAdapter;
    @Inject
     MoviePosterImageAdapter moviePosterImageAdapter;
    @Inject
     MovieVideosAdapter movieVideosAdapter;

    @Inject
    ViewModelProvider viewModelProvider;
    private String movieId;


    private MovieDetailActivityViewModel movieDetailActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ThumbnailLoader.initialize("AIzaSyA9PzztzVrSoAFAhc2oWBs1BIstzFXsah4");

        Intent intent = getIntent();


        moviePosterImageView = findViewById(R.id.movie_poster_image_view);
        movieDetailPosterCircleImageView = findViewById(R.id.movie_detail_poster_circle_image_view);
        movieDetailRatingBar = findViewById(R.id.movie_detail_rating_bar);


        movieDetailOriginalTitleLayout = findViewById(R.id.movie_detail_original_title_layout);
        movieDetailOriginalLanguageLayout = findViewById(R.id.movie_detail_original_language_layout);
        movieDetailAdultLayout = findViewById(R.id.movie_detail_adult_layout);
        movieDetailStatusLayout = findViewById(R.id.movie_detail_status_layout);
        movieDetailRuntimeLayout = findViewById(R.id.movie_detail_runtime_layout);
        movieDetailBudgetLayout = findViewById(R.id.movie_detail_budget_layout);
        movieDetailRevenueLayout = findViewById(R.id.movie_detail_revenue_layout);
        movieDetailGenreLayout = findViewById(R.id.movie_detail_genre_layout);
        movieDetailProductionCountryLayout = findViewById(R.id.movie_detail_production_country_layout);
        movieDetailReleaseDateLayout = findViewById(R.id.movie_detail_release_date_layout);
        movieDetailHomepageLayout = findViewById(R.id.movie_detail_homepagelayout);
        movieDetailOverviewLayout = findViewById(R.id.movie_detail_overview_layout);
        movieDetailCastLayout = findViewById(R.id.movie_detail_cast_layout);
        movieDetailCrewLayout = findViewById(R.id.movie_detail_crew_layout);
        movieDetailProductionCompanyLayout = findViewById(R.id.movie_detail_production_company_layout);
        movieDetailImagesLayout = findViewById(R.id.movie_detail_images_layout);
        movieDetailVideosLayout = findViewById(R.id.movie_detail_videos_layout);


        movieDetailOriginalTitle = findViewById(R.id.movie_detail_original_title);
        movieDetailOriginalLanguage = findViewById(R.id.movie_detail_original_language);
        movieDetailAdult = findViewById(R.id.movie_detail_adult);
        movieDetailStatus = findViewById(R.id.movie_detail_status);
        movieDetailRuntime = findViewById(R.id.movie_detail_runtime);
        movieDetailBudget = findViewById(R.id.movie_detail_budget);
        movieDetailRevenue = findViewById(R.id.movie_detail_revenue);
        movieDetailGenre = findViewById(R.id.movie_detail_genre);
        movieDetailProductionCountry = findViewById(R.id.movie_detail_production_country);
        movieDetailReleaseDate = findViewById(R.id.movie_detail_release_date);
        movieDetailHomepage = findViewById(R.id.movie_detail_homepage);
        movieDetailOverview = findViewById(R.id.movie_detail_overview);
        movieDetailTitle = findViewById(R.id.movie_detail_title);

        movieDetailCastRecyclerView = findViewById(R.id.movie_detail_cast_recycler_view);
        movieDetailCastRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        movieDetailCrewRecyclerView = findViewById(R.id.movie_detail_crew_recycler_view);
        movieDetailCrewRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        movieDetailProductionCompanyRecyclerView = findViewById(R.id.movie_detail_production_company_recycler_view);
        movieDetailProductionCompanyRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));


        movieDetailImagesRecyclerView = findViewById(R.id.movie_detail_images_recycler_view);
        movieDetailImagesRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));


        movieDetailVideosRecyclerView = findViewById(R.id.movie_detail_videos_recycler_view);
        movieDetailVideosRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));



        //retrofitClient = RetrofitClient.getInstance();
        ApplicationComponent applicationComponent = MyApplication.get(this).getApplicationComponent();
        MovieDetailsActivityComponent movieDetailsActivityComponent = DaggerMovieDetailsActivityComponent.builder()
                .movieDetailsActivityContextModule(new MovieDetailsActivityContextModule(this))
                .applicationComponent(applicationComponent)
                .build();
        movieDetailsActivityComponent.injectMovieDetailsActivity(this);


        movieDetailActivityViewModel = viewModelProvider.get(MovieDetailActivityViewModel.class);


        if (null != intent || null != intent.getExtras()) {
            if (intent.getExtras().getString("id") != null) {
                movieId = intent.getExtras().getString("id");


                prepareMovieDetails();

                prepareMovieCredits();

                prepareMovieImages();

                prepareMovieVideos();

            }
        }

    }

    private void prepareMovieVideos() {

        movieDetailActivityViewModel.getMovieVideosLiveData(retrofitClient, movieId)
                .observe(MovieDetailActivity.this, new Observer<MovieVideos>() {
                    @Override
                    public void onChanged(MovieVideos movieVideos) {
                        if (movieVideos != null) {

                            List<MovieVideosResult> movieVideosResultList = movieVideos.getMovieVideosResults();

                            if (movieVideosResultList != null && movieVideosResultList.size() > 0) {
                                MovieVideosAdapter movieVideosAdapter = new MovieVideosAdapter(MovieDetailActivity.this);
                                movieVideosAdapter.setMovieVideosResultList(movieVideosResultList);
                                movieDetailVideosRecyclerView.setAdapter(movieVideosAdapter);
                                movieDetailVideosLayout.setVisibility(View.VISIBLE);

                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailActivity.this, R.anim.layout_slide_right);
                                movieDetailVideosRecyclerView.setLayoutAnimation(controller);
                                movieDetailVideosRecyclerView.scheduleLayoutAnimation();
                            } else {
                                movieDetailVideosLayout.setVisibility(View.GONE);
                            }
                        } else {
                            movieDetailVideosLayout.setVisibility(View.GONE);
                        }
                    }
                });


    }

    private void prepareMovieImages() {

        movieDetailActivityViewModel.getMovieImagesLiveData(retrofitClient, movieId)
                .observe(MovieDetailActivity.this, new Observer<MovieImages>() {
                    @Override
                    public void onChanged(MovieImages movieImages) {

                        if (movieImages != null) {
                            List<MovieBackdropsandPosters> movieBackdropsList = movieImages.getBackdrops();
                            List<MovieBackdropsandPosters> moviePostersList = movieImages.getMoviePosters();
                            ArrayList<MovieBackdropsandPosters> movieBackdropsandPostersArrayList = new ArrayList<>();

                            if (movieBackdropsList == null && moviePostersList == null) {
                                movieBackdropsandPostersArrayList.clear();
                                movieDetailImagesLayout.setVisibility(View.GONE);
                            } else {
                                movieBackdropsandPostersArrayList.addAll(movieBackdropsList);
                                movieBackdropsandPostersArrayList.addAll(moviePostersList);
                            }

                            if (movieBackdropsandPostersArrayList.size() > 0) {
                                moviePosterImageAdapter.setMovieBackdropsandPostersList(movieBackdropsandPostersArrayList);
                                movieDetailImagesRecyclerView.setAdapter(moviePosterImageAdapter);
                                movieDetailImagesLayout.setVisibility(View.VISIBLE);

                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailActivity.this, R.anim.layout_slide_right);
                                movieDetailImagesRecyclerView.setLayoutAnimation(controller);
                                movieDetailImagesRecyclerView.scheduleLayoutAnimation();

                            } else {
                                movieDetailImagesLayout.setVisibility(View.GONE);

                            }


                        }

                    }
                });


    }

    private void prepareMovieDetails() {

        movieDetailActivityViewModel.getMovieDetailsLiveData(retrofitClient, movieId)
                .observe(MovieDetailActivity.this, new Observer<MovieDetails>() {
                    @Override
                    public void onChanged(MovieDetails movieDetails) {


                        String posterPath = movieDetails.getPosterPath();
                        String backdropPath = movieDetails.getBackdropPath();
                        String title = movieDetails.getTitle();
                        String originalTitle = movieDetails.getOriginalTitle();
                        String originalLanguage = movieDetails.getOriginalLanguage();

                        Double voteAverage = movieDetails.getVoteAverage() * 10;

                        Boolean adult = movieDetails.getAdult();
                        String status = movieDetails.getStatus();

                        Integer runTime = movieDetails.getRuntime();
                        Integer budget = movieDetails.getBudget();
                        Integer revenue = movieDetails.getRevenue();

                        List<MovieDetailGenre> movieDetailGenreList = movieDetails.getMovieDetailGenres();
                        List<MovieDetailsProductionCountry> movieDetailsProductionCountryList = movieDetails.getProductionCountries();
                        List<MovieDetailsProductionCompany> movieDetailsProductionCompanyList = movieDetails.getProductionCompanies();

                        String releaseDate = movieDetails.getReleaseDate();
                        String homepage = movieDetails.getHomepage();
                        String overview = movieDetails.getOverview();

                        Picasso.with(MovieDetailActivity.this).load(posterPath).into(moviePosterImageView);
                        Picasso.with(MovieDetailActivity.this).load(backdropPath).into(movieDetailPosterCircleImageView);
                        int rating = voteAverage.intValue();
                        movieDetailRatingBar.setProgress(rating);
                        movieDetailTitle.setText(title);

                        if (originalTitle != null) {
                            if (originalTitle.length() > 0) {
                                movieDetailOriginalTitle.setText(originalTitle);
                                movieDetailOriginalTitleLayout.setVisibility(View.VISIBLE);
                            } else {
                                movieDetailOriginalTitleLayout.setVisibility(View.GONE);
                            }
                        } else {
                            movieDetailOriginalTitleLayout.setVisibility(View.GONE);
                        }


                        if (originalLanguage != null) {
                            if (originalLanguage.length() > 0) {
                                movieDetailOriginalLanguage.setText(originalLanguage);
                                movieDetailOriginalLanguageLayout.setVisibility(View.VISIBLE);
                            } else {
                                movieDetailOriginalLanguageLayout.setVisibility(View.GONE);
                            }
                        } else {
                            movieDetailOriginalLanguageLayout.setVisibility(View.GONE);
                        }


                        if (adult) {
                            movieDetailAdult.setText("yes");
                            movieDetailAdultLayout.setVisibility(View.VISIBLE);
                        } else {
                            movieDetailAdultLayout.setVisibility(View.GONE);
                        }

                        if (status != null) {
                            if (status.length() > 0) {
                                movieDetailStatus.setText(status);
                                movieDetailStatusLayout.setVisibility(View.VISIBLE);
                            } else {
                                movieDetailStatusLayout.setVisibility(View.GONE);
                            }
                        } else {
                            movieDetailStatusLayout.setVisibility(View.GONE);
                        }


                        if (status != null) {
                            if (status.length() > 0) {
                                movieDetailStatus.setText(status);
                                movieDetailStatusLayout.setVisibility(View.VISIBLE);
                            } else {
                                movieDetailStatusLayout.setVisibility(View.GONE);
                            }
                        } else {
                            movieDetailStatusLayout.setVisibility(View.GONE);
                        }

                        if (runTime != null && runTime != 0) {
                            movieDetailRuntime.setText(String.valueOf(runTime));
                            movieDetailRuntimeLayout.setVisibility(View.VISIBLE);
                        } else {
                            movieDetailRuntimeLayout.setVisibility(View.GONE);
                        }


                        if (budget != null && budget != 0) {
                            movieDetailBudget.setText(String.valueOf(budget));
                            movieDetailBudgetLayout.setVisibility(View.VISIBLE);
                        } else {
                            movieDetailBudgetLayout.setVisibility(View.GONE);
                        }


                        if (revenue != null && revenue != 0) {
                            movieDetailRevenue.setText(String.valueOf(revenue));
                            movieDetailRevenueLayout.setVisibility(View.VISIBLE);
                        } else {
                            movieDetailRevenueLayout.setVisibility(View.GONE);
                        }


                        if (releaseDate != null) {
                            if (releaseDate.length() > 0) {
                                movieDetailReleaseDate.setText(releaseDate);
                                movieDetailReleaseDateLayout.setVisibility(View.VISIBLE);
                            } else {
                                movieDetailReleaseDateLayout.setVisibility(View.GONE);
                            }
                        } else {
                            movieDetailReleaseDateLayout.setVisibility(View.GONE);
                        }

                        if (homepage != null) {
                            if (homepage.length() > 0) {
                                movieDetailHomepage.setText(homepage);
                                movieDetailHomepageLayout.setVisibility(View.VISIBLE);
                            } else {
                                movieDetailHomepageLayout.setVisibility(View.GONE);
                            }
                        } else {
                            movieDetailHomepageLayout.setVisibility(View.GONE);
                        }
                        if (overview != null) {
                            if (overview.length() > 0) {
                                movieDetailOverview.setText(overview);
                                movieDetailOverviewLayout.setVisibility(View.VISIBLE);
                            } else {
                                movieDetailOverviewLayout.setVisibility(View.GONE);
                            }
                        } else {
                            movieDetailOverviewLayout.setVisibility(View.GONE);
                        }


                        if (movieDetailGenreList != null && movieDetailGenreList.size() > 0) {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int i = 0; i < movieDetailGenreList.size(); i++) {
                                if (i == movieDetailGenreList.size() - 1) {
                                    stringBuilder.append(movieDetailGenreList.get(i) + ".");
                                } else {
                                    stringBuilder.append(movieDetailGenreList.get(i) + ", ");
                                }
                            }
                            movieDetailGenre.setText(stringBuilder.toString());
                            movieDetailGenreLayout.setVisibility(View.VISIBLE);
                        } else {
                            movieDetailGenreLayout.setVisibility(View.GONE);
                        }


                        if (movieDetailsProductionCountryList != null && movieDetailsProductionCountryList.size() > 0) {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int i = 0; i < movieDetailsProductionCountryList.size(); i++) {
                                if (i == movieDetailsProductionCountryList.size() - 1) {
                                    stringBuilder.append(movieDetailsProductionCountryList.get(i) + ".");
                                } else {
                                    stringBuilder.append(movieDetailsProductionCountryList.get(i) + ", ");
                                }
                            }
                            movieDetailProductionCountry.setText(stringBuilder.toString());
                            movieDetailProductionCountryLayout.setVisibility(View.VISIBLE);
                        } else {
                            movieDetailProductionCountryLayout.setVisibility(View.GONE);
                        }


                        if (movieDetailsProductionCompanyList != null && movieDetailsProductionCompanyList.size() > 0) {
                            movieProductionCompaniesAdapter.setMovieDetailsProductionCompanyList(movieDetailsProductionCompanyList);
                            movieDetailProductionCompanyRecyclerView.setAdapter(movieProductionCompaniesAdapter);
                            movieDetailProductionCompanyLayout.setVisibility(View.VISIBLE);

                            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailActivity.this, R.anim.layout_slide_right);
                            movieDetailProductionCompanyRecyclerView.setLayoutAnimation(controller);
                            movieDetailProductionCompanyRecyclerView.scheduleLayoutAnimation();

                        } else {
                            movieDetailProductionCompanyLayout.setVisibility(View.GONE);
                        }
                    }
                });


    }

    private void prepareMovieCredits() {

        movieDetailActivityViewModel.getMovieCreditsLiveData(retrofitClient, movieId)
                .observe(MovieDetailActivity.this, new Observer<MovieCredits>() {
                    @Override
                    public void onChanged(MovieCredits movieCredits) {

                        if (null != movieCredits) {

                            List<MovieCreditsCast> movieCreditsCastList = movieCredits.getMovieCreditsCast();

                            if (movieCreditsCastList != null && movieCreditsCastList.size() > 0) {
                                movieCreditsCastAdapter.setMovieCreditsCastList(movieCreditsCastList);
                                movieDetailCastRecyclerView.setAdapter(movieCreditsCastAdapter);
                                movieDetailCastLayout.setVisibility(View.VISIBLE);

                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailActivity.this, R.anim.layout_slide_right);
                                movieDetailCastRecyclerView.setLayoutAnimation(controller);
                                movieDetailCastRecyclerView.scheduleLayoutAnimation();

                            } else {
                                movieDetailCastLayout.setVisibility(View.GONE);
                            }


                            List<MovieCreditsCrew> movieCreditsCrewList = movieCredits.getMovieCreditsCrew();

                            if (movieCreditsCrewList != null && movieCreditsCrewList.size() > 0) {
                                movieCreditsCrewAdapter.setMovieCreditsCrewList(movieCreditsCrewList);
                                movieDetailCrewRecyclerView.setAdapter(movieCreditsCrewAdapter);
                                movieDetailCrewLayout.setVisibility(View.VISIBLE);

                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailActivity.this, R.anim.layout_slide_right);
                                movieDetailCrewRecyclerView.setLayoutAnimation(controller);
                                movieDetailCrewRecyclerView.scheduleLayoutAnimation();

                            } else {
                                movieDetailCrewLayout.setVisibility(View.GONE);
                            }


                        } else {
                            movieDetailCastLayout.setVisibility(View.GONE);
                            movieDetailCrewLayout.setVisibility(View.GONE);

                        }


                    }
                });


//        Single<MovieCredits> movieCreditsSingle = retrofitClient.getMovieCreditsById(movieId);
//        movieCreditsSingle.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).
//                subscribe(new SingleObserver<MovieCredits>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//
//
//
//                    }
//
//                    @Override
//                    public void onSuccess(MovieCredits movieCredits) {
//                        if (null != movieCredits) {
//
//                            List<MovieCreditsCast> movieCreditsCastList = movieCredits.getMovieCreditsCast();
//
//                            if (movieCreditsCastList != null && movieCreditsCastList.size() > 0) {
//                                MovieCreditsCastAdapter movieCreditsCastAdapter = new MovieCreditsCastAdapter(MovieDetailActivity.this, movieCreditsCastList);
//                                movieDetailCastRecyclerView.setAdapter(movieCreditsCastAdapter);
//                                movieDetailCastLayout.setVisibility(View.VISIBLE);
//
//                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailActivity.this, R.anim.layout_slide_right);
//                                movieDetailCastRecyclerView.setLayoutAnimation(controller);
//                                movieDetailCastRecyclerView.scheduleLayoutAnimation();
//
//                            } else {
//                                movieDetailCastLayout.setVisibility(View.GONE);
//                            }
//
//
//                            List<MovieCreditsCrew> movieCreditsCrewList = movieCredits.getMovieCreditsCrew();
//
//                            if (movieCreditsCrewList != null && movieCreditsCrewList.size() > 0) {
//                                MovieCreditsCrewAdapter movieCreditsCrewAdapter = new MovieCreditsCrewAdapter(MovieDetailActivity.this, movieCreditsCrewList);
//                                movieDetailCrewRecyclerView.setAdapter(movieCreditsCrewAdapter);
//                                movieDetailCrewLayout.setVisibility(View.VISIBLE);
//
//                                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MovieDetailActivity.this, R.anim.layout_slide_right);
//                                movieDetailCrewRecyclerView.setLayoutAnimation(controller);
//                                movieDetailCrewRecyclerView.scheduleLayoutAnimation();
//
//                            } else {
//                                movieDetailCrewLayout.setVisibility(View.GONE);
//                            }
//
//
//                        } else {
//                            movieDetailCastLayout.setVisibility(View.GONE);
//                            movieDetailCrewLayout.setVisibility(View.GONE);
//
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "onFailure: asd" + e.getMessage());
//                    }
//                });


    }

    // set animation for back to main activity
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void openVideoPlayerActivity(String position, ArrayList<MovieVideosResult> movieVideosResultArrayList, ActivityOptionsCompat compat) {
        Intent intent = new Intent(this, VideoPlayerActivity.class);

        // put the current video and other videos
        intent.putExtra("position", position);
        intent.putExtra("video", movieVideosResultArrayList);
        startActivity(intent, compat.toBundle());
    }

    @Override
    public void openImageViewerActivity(String imageUrl, ActivityOptions compat) {
        Intent imageViewerIntent = new Intent(this, ImageViewerActivity.class);
        imageViewerIntent.putExtra("image_url", imageUrl);
        startActivity(imageViewerIntent, compat.toBundle());
    }

    @Override
    public void openPersonDetailsActivity(String id) {
        Intent intent = new Intent(this, PersonDetailsActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);

        // create some animation to open new activity
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


}
