package com.zeroplus.retrofitexercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textResult;

    private JsonPlaceHolderAPI jsonPlaceHolderAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = findViewById(R.id.text_view_result);

        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);

        //getPost();
        //getComment();
        //createPost();
        //updatePost();
        deletePost();
    }

    private void getPost() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("-order", "desc");


        /*network_req*/
        /*Call<List<Post>> call = jsonPlaceHolderAPI.getPosts(4,"id","desc");*/

        /*for map*/
        Call<List<Post>> call = jsonPlaceHolderAPI.getPosts(parameters);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textResult.setText("Code " + response.code());
                    return;
                }
                List<Post> posts = response.body();
                for (Post post : posts) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Description: " + post.getDescription() + "\n\n";

                    textResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    /*private void getComment(){

        //network_req
        Call<List<Comment>> call = jsonPlaceHolderAPI.getComments(3);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    textResult.setText("Code " + response.code());
                    return;
                }
                List<Comment> comments = response.body();
                for (Comment comment : comments) {
                    String content = "";
                    content += "ID: " + comment.getId() + "\n";
                    content += "PostID: " + comment.getPostId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n";

                    textResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }*/
    private void getComment() {

        /*network_req*/
        /*Call<List<Comment>> call = jsonPlaceHolderAPI.getComments(3);*/
        Call<List<Comment>> call = jsonPlaceHolderAPI
                .getComments("posts/3/comments");

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    textResult.setText("Code " + response.code());
                    return;
                }
                List<Comment> comments = response.body();
                for (Comment comment : comments) {
                    String content = "";
                    content += "ID: " + comment.getId() + "\n";
                    content += "PostID: " + comment.getPostId() + "\n";
                    content += "Name: " + comment.getName() + "\n";
                    content += "Email: " + comment.getEmail() + "\n";
                    content += "Text: " + comment.getText() + "\n";

                    textResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    private void createPost() {
        Post post = new Post(23, "New Title", "New Text");
//        Call<Post> call = jsonPlaceHolderAPI.createPost(post);

//        Call<Post> call = jsonPlaceHolderAPI.createPost(23,"new Title","very new text");

        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "25");
        fields.put("title", "hello");
        fields.put("description", "asad");
        Call<Post> call = jsonPlaceHolderAPI.createPost(fields);


        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    textResult.setText("Code " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code" + response.code() + "\n";
                content += "Id" + postResponse.getId() + "\n";
                content += "UserID" + postResponse.getUserId() + "\n";
                content += "Title" + postResponse.getTitle() + "\n";
                content += "Text" + postResponse.getDescription() + "\n";

                textResult.setText(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    private void updatePost() {
        Post post = new Post(12, "null", "new text");
        Call<Post> call = jsonPlaceHolderAPI.patchPost(5, post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    textResult.setText("Code " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "Id: " + postResponse.getId() + "\n";
                content += "UserID: " + postResponse.getUserId() + "\n";
                content += "Title: " + postResponse.getTitle() + "\n";
                content += "Text: " + postResponse.getDescription() + "\n";

                textResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textResult.setText(t.getMessage());
            }
        });
    }

    private void deletePost() {
        Call<Void> call = jsonPlaceHolderAPI.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textResult.setText("Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textResult.setText(t.getMessage());

            }
        });
    }

}