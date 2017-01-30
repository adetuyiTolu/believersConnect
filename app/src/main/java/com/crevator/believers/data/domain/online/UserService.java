package com.crevator.believers.data.domain.online;

import com.crevator.believers.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Slimfit on 1/23/2017.
 */

public interface UserService {
    @GET("hotelsng/users")
    Call<List<User>> getUsers();

    @GET("users")
    Call<List<User>> search(@Query("q") String query);

    @GET("users/{id}")
    Call<User> getUser(@Path("id") Long id);

    @POST("users")
    Call<User> saveUser(@Body User user);

    @PUT("users/{id}")
    Call<User> updateUser(@Path("id") Long id, @Body User user);

    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") Long id);


}
