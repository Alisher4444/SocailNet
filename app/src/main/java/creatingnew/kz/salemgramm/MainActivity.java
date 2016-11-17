package creatingnew.kz.salemgramm;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class MainActivity extends AppCompatActivity {


    private FloatingActionButton addButton;
    StickyListHeadersListView stickyListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (FloatingActionButton) findViewById(R.id.addButton);
        stickyListView = (StickyListHeadersListView) findViewById(R.id.stickyListView);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonClick();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getPosts();
        getProfiles();
    }

    private void getProfiles() {

    }


    private void getPosts() {
        Backendless.Data.of(Post.class).find(new AsyncCallback<BackendlessCollection<Post>>() {
            @Override
            public void handleResponse(final BackendlessCollection<Post> response1) {
                Backendless.Data.of(Profile.class).find(new AsyncCallback<BackendlessCollection<Profile>>() {
                    @Override
                    public void handleResponse(BackendlessCollection<Profile> response) {
                        displayPosts(response.getData(),response1.getData());
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Log.e("Main","failed into Profile "+ fault.getMessage());
                    }
                });

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Log.e(",ain","failed to get posts"+ fault.getMessage());
            }
        });
    }





    private void displayPosts(List<Profile> data, List<Post> data2) {
        PostAdapter postAdapter = new PostAdapter(data2,this,data);
        stickyListView.setAdapter(postAdapter);

    }

    private void onAddButtonClick() {
        Intent intent = new Intent(this, AddPostActivity.class);
        startActivity(intent);
    }
}
