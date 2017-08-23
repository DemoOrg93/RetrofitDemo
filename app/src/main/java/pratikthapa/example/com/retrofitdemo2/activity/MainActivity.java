package pratikthapa.example.com.retrofitdemo2.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import pratikthapa.example.com.retrofitdemo2.R;
import pratikthapa.example.com.retrofitdemo2.adapter.MoviesAdapter;
import pratikthapa.example.com.retrofitdemo2.model.Movie;
import pratikthapa.example.com.retrofitdemo2.rest.ApiClient;
import pratikthapa.example.com.retrofitdemo2.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();


    // TODO - insert your themoviedb.org API KEY here
    private final static String API_KEY = "4ee85c7bc825503dad4cf43e7462ae33";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Movie.MoviesResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<Movie.MoviesResponse>() {
            @Override
            public void onResponse(Call<Movie.MoviesResponse> call, Response<Movie.MoviesResponse> response) {
                int statusCode = response.code();
                List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<Movie.MoviesResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}