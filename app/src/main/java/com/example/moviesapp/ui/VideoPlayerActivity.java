package com.example.moviesapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codewaves.youtubethumbnailview.ThumbnailLoader;
import com.codewaves.youtubethumbnailview.ThumbnailView;
import com.example.moviesapp.R;
import com.example.moviesapp.adapters.movie.MovieVideosAdapter;
import com.example.moviesapp.app.MyApplication;
import com.example.moviesapp.di.component.ApplicationComponent;
import com.example.moviesapp.di.component.DaggerVideoPlayerActivityComponent;
import com.example.moviesapp.di.component.VideoPlayerActivityComponent;
import com.example.moviesapp.di.module.VideoPlayerActivityContextModule;
import com.example.moviesapp.model.movie.MovieVideosResult;
import com.example.moviesapp.utils.FullScreenHelper;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

import javax.inject.Inject;

public class VideoPlayerActivity extends AppCompatActivity implements MovieVideosAdapter.MovieVideosListener {

    private ThumbnailView videoPlayerThumbnail;
    private YouTubePlayerView playerView;
    private RecyclerView otherVideosRecyclerView;
    private FullScreenHelper fullScreenHelper;
    private TextView videoTitle;
    private TextView noResultFound;
    private ProgressBar progressBar;

    @Inject
     MovieVideosAdapter adapter;


    private static final String TAG = "VideoPlayerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);


        Intent intent = getIntent();

        ThumbnailLoader.initialize("AIzaSyA9PzztzVrSoAFAhc2oWBs1BIstzFXsah4");
        fullScreenHelper = new FullScreenHelper(this);

        videoPlayerThumbnail = findViewById(R.id.video_player_thumbnail);
        videoTitle = findViewById(R.id.play_video_title);
        noResultFound = findViewById(R.id.no_result_found);

        playerView = findViewById(R.id.video_player_view);
        getLifecycle().addObserver(playerView);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);

        otherVideosRecyclerView = findViewById(R.id.other_videos_recycler_view);
        otherVideosRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        ApplicationComponent applicationComponent = MyApplication.get(this).getApplicationComponent();
        VideoPlayerActivityComponent videoPlayerActivityComponent = DaggerVideoPlayerActivityComponent.builder()
                .videoPlayerActivityContextModule(new VideoPlayerActivityContextModule(this))
                .applicationComponent(applicationComponent)
                .build();
        videoPlayerActivityComponent.injectVideoPlayerActivity(this);



        // now get the current video and other videos

        if (intent != null && intent.getExtras() != null) {
            ArrayList<MovieVideosResult> movieVideosResultArrayList = intent.getExtras().getParcelableArrayList("video");
            int position = Integer.parseInt(intent.getExtras().getString("position"));

            if (movieVideosResultArrayList != null && movieVideosResultArrayList.size() > 0) {
                final String videoId = movieVideosResultArrayList.get(position).getKey();
                String title = movieVideosResultArrayList.get(position).getName();

                if (title != null) {
                    videoTitle.setText(title);
                }

                if (videoId != null) {
                    String baseUrl = "https://www.youtube.com/watch?v=";
                    videoPlayerThumbnail.loadThumbnail(baseUrl + videoId);

                    playerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(YouTubePlayer youTubePlayer) {

                            // when video is ready hide thumbnail and progressbar
                            videoPlayerThumbnail.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);


                            // show the youtube player
                            playerView.setVisibility(View.VISIBLE);

                            if (getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {
                                youTubePlayer.loadVideo(videoId, 0);
                            } else {
                                youTubePlayer.cueVideo(videoId, 0);
                            }


                        }
                    });

                    playerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
                        @Override
                        public void onYouTubePlayerEnterFullScreen() {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                            fullScreenHelper.enterFullScreen();
                        }

                        @Override
                        public void onYouTubePlayerExitFullScreen() {
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            fullScreenHelper.exitFullScreen();
                        }
                    });
                }
            }

            // load other videos in recycler view
            ArrayList<MovieVideosResult> movieVideosResultArrayList1 = new ArrayList<>(movieVideosResultArrayList);

            // remove current Video
            movieVideosResultArrayList1.remove(position);

            if (movieVideosResultArrayList1.size() > 0) {
                noResultFound.setVisibility(View.GONE);

                otherVideosRecyclerView.setVisibility(View.VISIBLE);

                adapter.setMovieVideosResultList(movieVideosResultArrayList1);
                otherVideosRecyclerView.setAdapter(adapter);

                // create animation for loading items
                LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_slide_bottom);
                otherVideosRecyclerView.setLayoutAnimation(controller);
                otherVideosRecyclerView.scheduleLayoutAnimation();


            } else {
                noResultFound.setVisibility(View.VISIBLE);


                otherVideosRecyclerView.setVisibility(View.GONE);
            }
        }
    }


    // exit the full screen on back pressed

    @Override
    public void onBackPressed() {
        if (playerView.isFullScreen()) {
            playerView.exitFullScreen();
        } else {
            otherVideosRecyclerView.setVisibility(View.GONE);
            playerView.setVisibility(View.GONE);
            videoPlayerThumbnail.setVisibility(View.VISIBLE);

            super.onBackPressed();
        }
    }

    @Override
    public void openVideoPlayerActivity(String position, ArrayList<MovieVideosResult> movieVideosResultArrayList, ActivityOptionsCompat compat) {
        Intent intent = new Intent(this, VideoPlayerActivity.class);

        // put the current video and other videos
        intent.putExtra("position", position);
        intent.putExtra("video", movieVideosResultArrayList);
        startActivity(intent, compat.toBundle());
    }
}
