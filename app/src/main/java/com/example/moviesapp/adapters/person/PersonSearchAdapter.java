package com.example.moviesapp.adapters.person;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesapp.databinding.SearchLayoutItemsBinding;
import com.example.moviesapp.R;
import com.example.moviesapp.model.person.PersonResponseResult;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PersonSearchAdapter extends RecyclerView.Adapter<PersonSearchAdapter.PersonViewHolder> {


    private PersonSearchClickListener personSearchClickListener ;
    private List<PersonResponseResult> personResponseResults;

    public PersonSearchAdapter(PersonSearchClickListener personSearchClickListener) {
        this.personSearchClickListener = personSearchClickListener;
    }

    public void setPersonResponseResults(List<PersonResponseResult> personResponseResults) {
        this.personResponseResults = personResponseResults;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        SearchLayoutItemsBinding searchLayoutItemsBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.search_layout_items, parent, false);

        return new PersonViewHolder(searchLayoutItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {


        PersonResponseResult responseResult = personResponseResults.get(position);
        holder.searchLayoutItemsBinding.setPersonResponseResult(responseResult);
        holder.setPosterImageView((Context)personSearchClickListener, responseResult.getProfilePath().toString());

        final int id = responseResult.getId();


        holder.searchLayoutItemsBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personSearchClickListener.openPersonDetailsActivity(String.valueOf(id));
/*

                Intent intent = new Intent(activity, PersonDetailsActivity.class);
                intent.putExtra("id", String.valueOf(id));
                activity.startActivity(intent);

                // create some animation to open new activity
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
*/

            }
        });
    }

    @Override
    public int getItemCount() {
        return personResponseResults.size();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {

        private SearchLayoutItemsBinding searchLayoutItemsBinding;

        public PersonViewHolder(@NonNull SearchLayoutItemsBinding searchLayoutItemsBinding) {
            super(searchLayoutItemsBinding.getRoot());

            this.searchLayoutItemsBinding = searchLayoutItemsBinding;


            RandomTransitionGenerator generator = new RandomTransitionGenerator(1000, new DecelerateInterpolator());
            searchLayoutItemsBinding.posterImageView.setTransitionGenerator(generator);

        }

        public void setPosterImageView(Context context, String posterUrl) {
            Picasso.with(context).load(posterUrl).into(searchLayoutItemsBinding.posterImageView);
        }
    }

    public interface PersonSearchClickListener{
        void openPersonDetailsActivity(String id);
    }
}
