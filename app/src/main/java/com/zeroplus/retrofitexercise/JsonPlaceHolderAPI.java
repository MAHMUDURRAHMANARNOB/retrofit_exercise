package com.zeroplus.retrofitexercise;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderAPI {

    //regular
    /*@GET("posts")
    Call<List<Post>> getPosts();*/

    //with parameter -- /posts?userid=1
    /*@GET("posts")
    Call<List<Post>> getPosts(@Query("userId") int userId);*/

    //with multiple parameter
    @GET("posts")
    Call<List<Post>> getPosts(
            @Query("userId") Integer userId, // nullable int -> Integer
            //@Query("userId") Integer[] userId, // Array of Integer can be used if we want to put multiple same type values.
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    @GET("posts")
    Call<List<Post>> getPosts(
            //to decide when we call these methods.
            @QueryMap Map<String, String> parameters
    );

    /*@GET("posts/2/comments")
    Call<List<Comment>> getComments();*/

    @GET("posts/{id}/comments")
        //custom url -- url manipulation
    Call<List<Comment>> getComments(@Path("id") int postId);

    @GET
    Call<List<Comment>> getComments(@Url String url);


    //POST
    //send parameter through body
    @POST("posts")
    Call<Post> createPost(@Body Post post);

    @FormUrlEncoded //Encoded the url.. as @Query for GET
    @POST("posts")
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );

    @FormUrlEncoded
    @POST("posts") //same as querymap
    Call<Post> createPost(@FieldMap Map<String, String> fields);


    //PUT, PUSH, DELETE
    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id, @Body Post post);

    @PATCH("posts/{id}")
    Call<Post> patchPost(@Path("id") int id, @Body Post post);

    //Difference between put and patch:
    //Both are used to update in existing resource,  with the one we have send through request @Body.
    /*in PUT we completely replace the existing resource with the object we sent via @BODY..
      we always have to send a full object. and the server will replace the whole object.
      and if the object does not exist, it will create a new object like a POST or we will get a 404.*/
    //in PATCH will only change the fields we sends.


    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);

}
