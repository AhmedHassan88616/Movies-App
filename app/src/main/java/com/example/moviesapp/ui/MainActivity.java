package com.example.moviesapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moviesapp.R;
import com.example.moviesapp.adapters.movie.MovieSearchAdapter;
import com.example.moviesapp.adapters.person.PersonSearchAdapter;
import com.example.moviesapp.app.MyApplication;
import com.example.moviesapp.client.RetrofitClient;
import com.example.moviesapp.di.component.ApplicationComponent;
import com.example.moviesapp.di.component.DaggerMainActivityComponent;
import com.example.moviesapp.di.component.MainActivityComponent;
import com.example.moviesapp.di.module.MainActivityContextModule;
import com.example.moviesapp.model.movie.MovieResponse;
import com.example.moviesapp.model.movie.MovieResponseResult;
import com.example.moviesapp.model.person.PersonResponse;
import com.example.moviesapp.model.person.PersonResponseResult;
import com.example.moviesapp.viewmodel.MainActivityViewModel;
import com.google.gson.Gson;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements PersonSearchAdapter.PersonSearchClickListener, MovieSearchAdapter.MovieSearchClickListener {

    private NiceSpinner sourceSpinner;
    private EditText queryEditText;
    private Button querySearchButton;
    private RecyclerView resultRecyclerView;
    private String movie = "By movie title...";
    private String person = "By person name...";

    @Inject
    MovieSearchAdapter movieSearchAdapter;
    @Inject
    PersonSearchAdapter personSearchAdapter;
    private static final String TAG = "MainActivity";

    @Inject
    RetrofitClient retrofitClient;

    @Inject
    ViewModelProvider viewModelProvider;


    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // disable the keyboard on the start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        sourceSpinner = findViewById(R.id.source_spinner);
        queryEditText = findViewById(R.id.query_edit_text);
        querySearchButton = findViewById(R.id.query_search_button);
        resultRecyclerView = findViewById(R.id.results_recycler_view);



        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ApplicationComponent applicationComponent = MyApplication.get(this).getApplicationComponent();
        MainActivityComponent mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityContextModule(new MainActivityContextModule(this))
                .applicationComponent(applicationComponent)
                .build();
        mainActivityComponent.injectMainActivity(this);

        mainActivityViewModel = viewModelProvider.get(MainActivityViewModel.class);

        Paper.init(this);
        final ArrayList<String> category = new ArrayList<>();

        // set list for sourceSpinner
        // person name mean actor name
        category.add(movie);
        category.add(person);

        sourceSpinner.attachDataSource(category);

        // retrieve the position at start and the set the spinner

        if (Paper.book().read("position") != null) {
            int i = Paper.book().read("position");
            sourceSpinner.setSelectedIndex(i);
        }

        int position = sourceSpinner.getSelectedIndex();

        if (position == 0) {
            queryEditText.setHint("Enter any movie title...");
        } else {
            queryEditText.setHint("Enter any person name...");
        }

        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    queryEditText.setHint("Enter any movie title...");
                } else {
                    queryEditText.setHint("Enter any person name...");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // retrieve the results from paper dp and start

        if (Paper.book().read("cache") != null) {
            String results = Paper.book().read("cache");
            if (Paper.book().read("source") != null) {
                String source = Paper.book().read("source");

                if (source.equals("movie")) {
                    MovieResponse movieResponse = new Gson().fromJson(results, MovieResponse.class);

                    if (movieResponse != null) {
                        List<MovieResponseResult> movieResponseResults = movieResponse.getResults();
                        movieSearchAdapter.setMovieResponseResults(movieResponseResults);
                        resultRecyclerView.setAdapter(movieSearchAdapter);

                        // create some animation to recycler view item loading

                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.layout_slide_right);

                        resultRecyclerView.setLayoutAnimation(controller);
                        resultRecyclerView.scheduleLayoutAnimation();

                        // now store the results in paper database to access offline
                        Paper.book().write("cache", new Gson().toJson(movieResponse));

                        // store also the category to set the spinner at app start
                        Paper.book().write("source", "movie");
                    }

                } else {
                    PersonResponse personResponse = new Gson().fromJson(results, PersonResponse.class);
                    if (personResponse != null) {
                        List<PersonResponseResult> personResponseResults = personResponse.getResults();
                        personSearchAdapter.setPersonResponseResults(personResponseResults);
                        resultRecyclerView.setAdapter(personSearchAdapter);

                        // create some animation to recycler view item loading

                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.layout_slide_right);

                        resultRecyclerView.setLayoutAnimation(controller);
                        resultRecyclerView.scheduleLayoutAnimation();

                        // now store the results in paper database to access offline
                        Paper.book().write("cache", new Gson().toJson(personResponse));

                        // store also the category to set the spinner at app start
                        Paper.book().write("source", "person");
                    }
                }
            }
        }


        // get rhe query from the user
        //retrofitClient = RetrofitClient.getInstance();

        querySearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = queryEditText.getText().toString().trim();
                if (query.equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Please enter any text...", Toast.LENGTH_SHORT).show();
                } else {
                    queryEditText.setText("");
                    String finalQuery = query.replaceAll(" ", "+");

                    // get the category to search the query   movie or person

                    if (category.size() > 0) {
                        String categoryName = category.get(sourceSpinner.getSelectedIndex());

                        if (categoryName.equals(movie)) {
                            mainActivityViewModel.getMovieResponseLiveData(retrofitClient, finalQuery).observe(MainActivity.this, new Observer<MovieResponse>() {
                                @Override
                                public void onChanged(MovieResponse movieResponse) {
                                    if (movieResponse != null) {
                                        List<MovieResponseResult> movieResponseResults = movieResponse.getResults();
                                        movieSearchAdapter.setMovieResponseResults(movieResponseResults);
                                        resultRecyclerView.setAdapter(movieSearchAdapter);

                                        // create some animation to recycler view item loading

                                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.layout_slide_right);

                                        resultRecyclerView.setLayoutAnimation(controller);
                                        resultRecyclerView.scheduleLayoutAnimation();

                                        // now store the results in paper database to access offline
                                        Paper.book().write("cache", new Gson().toJson(movieResponse));

                                        // store also the category to set the spinner at app start
                                        Paper.book().write("source", "movie");
                                    }
                                }
                            });

                        } else {

                            mainActivityViewModel.getPersonResponseLiveData(retrofitClient, finalQuery)
                                    .observe(MainActivity.this, new Observer<PersonResponse>() {
                                        @Override
                                        public void onChanged(PersonResponse personResponse) {
                                            List<PersonResponseResult> personResponseResults = personResponse.getResults();
                                            personSearchAdapter.setPersonResponseResults(personResponseResults);
                                            resultRecyclerView.setAdapter(personSearchAdapter);

                                            // create some animation to recycler view item loading

                                            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.layout_slide_right);

                                            resultRecyclerView.setLayoutAnimation(controller);
                                            resultRecyclerView.scheduleLayoutAnimation();

                                            // now store the results in paper database to access offline
                                            Paper.book().write("cache", new Gson().toJson(personResponse));

                                            // store also the category to set the spinner at app start
                                            Paper.book().write("source", "person");
                                        }
                                    });
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Paper.book().write("position", sourceSpinner.getSelectedIndex());
    }

    @Override
    public void openPersonDetailsActivity(String id) {

        Intent intent = new Intent(this, PersonDetailsActivity.class);
        intent.putExtra("id", String.valueOf(id));
        startActivity(intent);

        // create some animation to open new activity
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    public void openMovieDetailsActivity(String id) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);

        // create some animation to open new activity
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
    }
}
