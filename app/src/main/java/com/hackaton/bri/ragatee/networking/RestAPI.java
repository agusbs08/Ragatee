package com.hackaton.bri.ragatee.networking;

import com.hackaton.bri.ragatee.model.Cart;
import com.hackaton.bri.ragatee.model.DetailProduk;
import com.hackaton.bri.ragatee.model.History;
import com.hackaton.bri.ragatee.model.Produk;
import com.hackaton.bri.ragatee.model.ProdukWithMap;
import com.hackaton.bri.ragatee.model.Transaksi;
import com.hackaton.bri.ragatee.model.User;
import com.hackaton.bri.ragatee.model.request.CartRequest;
import com.hackaton.bri.ragatee.model.request.Login;
import com.hackaton.bri.ragatee.model.request.Payment;
import com.hackaton.bri.ragatee.model.request.RegistrationRequest;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestAPI {

    @POST("api/auth")
    Call<User> authentication(@Body Login requestLogin);

    @GET("api/crowdfundings")
    Call<List<Produk>> getListProduk(@Query("sort") String sort);

    @GET("api/crowdfundings/{id}")
    Call<DetailProduk> getDetailProduk(@Path("id") String id);

    @GET("api/users/{id}/cart")
    Call<List<Cart>> getListCart(@Path("id") String userId);

    @GET("api/users/{id}/transactions")
    Call<List<Transaksi>> getListTransaksi(@Path("id") String userId);

    @POST("api/users/cart")
    Call<Void> addToCart(@Body CartRequest request);

    @POST("api/users/payment")
    Call<Void> payment(@Body Payment payment);

    @DELETE("api/users/{userId}/cart/{cartItemId}")
    Call<Void> deleteCart(@Path("userId") String userId, @Path("cartItemId") String cartItemId);

    @POST("api/users/verification")
    Call<Void> userRegistration(@Body RegistrationRequest request);

    @GET("api/users/{id}/profile")
    Call<User> getDetailUser(@Path("id") String userId);

    @GET("api/crowdfundings/location/nearest")
    Call<List<ProdukWithMap>> getListProdukWithMap(@Query("lat") double lat, @Query("lng") double lng, @Query("radius") int radius);

    @GET("api/crowdfundings")
    Call<List<Produk>> getListProdukSortRating(@Query("sort") String sort);

    @GET("api/users/{id}/account-transaction-history")
    Call<List<History>> getListHistory(@Path("id") String userId);
}
