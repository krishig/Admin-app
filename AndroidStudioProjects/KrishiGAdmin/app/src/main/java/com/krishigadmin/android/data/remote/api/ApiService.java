package com.krishigadmin.android.data.remote.api;

import com.krishigadmin.android.data.remote.ApiConfiguration;
import com.krishigadmin.android.data.remote.ApiResponseArray;
import com.krishigadmin.android.data.remote.ApiResponseObject;
import com.krishigadmin.android.model.Category;
import com.krishigadmin.android.model.Customer;
import com.krishigadmin.android.model.Home;
import com.krishigadmin.android.model.Order;
import com.krishigadmin.android.model.PostalPincodeResponse;
import com.krishigadmin.android.model.Product;
import com.krishigadmin.android.model.ProductBrands;
import com.krishigadmin.android.model.ProductId;
import com.krishigadmin.android.model.SubCategory;
import com.krishigadmin.android.model.User;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    String BASE_URL = "https://api.postalpincode.in/";

    @GET("pincode/{pincode}")
    Call<ArrayList<PostalPincodeResponse>> getPincodeDetails(@Path("pincode") String pincode);


    @POST(ApiConfiguration.user)
    Call<ApiResponseObject<User>> signUp(@Body RequestBody body,
                                         @Header("accept") String accept,
                                         @Header("Content-Type") String authorisation
    );

    @GET(ApiConfiguration.user)
    Call<ApiResponseObject<User>> getUserWithId(@Query("id") String id,
                                                @Header("accept") String accept,
                                                @Header("Content-Type") String authorisation,
                                                @Header("Authorization") String token
    );

    @GET(ApiConfiguration.user)
    Call<ApiResponseObject<User>> getUser(@Query("items_per_page") String items_per_page,
                                          @Query("page_number") String page_number,
                                          @Header("accept") String accept,
                                          @Header("Content-Type") String authorisation,
                                          @Header("Authorization") String token
    );

    @GET(ApiConfiguration.userSearch)
    Call<ApiResponseObject<User>> getUserSearch(@Query("items_per_page") String items_per_page,
                                                @Query("page_number") String page_number,
                                                @Query("search_user") String search_user,
                                                @Header("accept") String accept,
                                                @Header("Content-Type") String authorisation,
                                                @Header("Authorization") String token
    );

    @PATCH(ApiConfiguration.user)
    Call<ApiResponseObject<User>> editUser(@Query("id") String id,
                                           @Body RequestBody body,
                                           @Header("accept") String accept,
                                           @Header("Content-Type") String authorisation,
                                           @Header("Authorization") String token
    );


    @POST(ApiConfiguration.login)
    Call<ApiResponseObject<User>> login(@Body RequestBody body,
                                        @Query("user_role") String role,
                                        @Header("accept") String accept,
                                        @Header("Content-Type") String authorisation
    );

    @POST(ApiConfiguration.categories)
    Call<ApiResponseObject<User>> addCategories(@Body RequestBody body,
                                                @Header("accept") String accept,
                                                @Header("Content-Type") String authorisation,
                                                @Header("Authorization") String token
    );

    @GET(ApiConfiguration.categories)
    Call<ApiResponseArray<Category>> getCategories(@Header("accept") String accept,
                                                   @Header("Content-Type") String authorisation,
                                                   @Header("Authorization") String token
    );

    @DELETE(ApiConfiguration.categories)
    Call<ApiResponseArray<Category>> deleteCategories(@Query("id") String id,
                                                      @Header("accept") String accept,
                                                      @Header("Content-Type") String authorisation,
                                                      @Header("Authorization") String token
    );

    @PATCH(ApiConfiguration.categories)
    Call<ApiResponseObject<Category>> editCategories(@Query("id") String id,
                                                     @Body RequestBody body,
                                                     @Header("accept") String accept,
                                                     @Header("Content-Type") String authorisation,
                                                     @Header("Authorization") String token
    );


    @Multipart
    @POST(ApiConfiguration.image)
    Call<ApiResponseArray<Category>> image(@Part MultipartBody.Part file,
                                           @Header("Authorization") String token
    );


    @GET(ApiConfiguration.subCategorySearch)
    Call<ApiResponseObject<SubCategory>> subCategorySearch(@Query("search_sub_category") String search_sub_category,
                                                           @Header("accept") String accept,
                                                           @Header("Content-Type") String authorisation,
                                                           @Header("Authorization") String token
    );

    @GET(ApiConfiguration.subCategorySearch)
    Call<ApiResponseObject<SubCategory>> subCategorySearch(@Query("items_per_page") String items_per_page,
                                                           @Query("page_number") String page_number,
                                                           @Query("search_sub_category") String search_sub_category,
                                                           @Header("accept") String accept,
                                                           @Header("Content-Type") String authorisation,
                                                           @Header("Authorization") String token
    );

    @POST(ApiConfiguration.subCategory)
    Call<ApiResponseObject<User>> addSubCategory(@Body RequestBody body,
                                                 @Header("accept") String accept,
                                                 @Header("Content-Type") String authorisation,
                                                 @Header("Authorization") String token
    );

    @GET(ApiConfiguration.subCategory)
    Call<ApiResponseObject<SubCategory>> getSubCategories(@Header("accept") String accept,
                                                          @Header("Content-Type") String authorisation,
                                                          @Header("Authorization") String token
    );

    @GET(ApiConfiguration.subCategory)
    Call<ApiResponseObject<SubCategory>> getSubCategories(@Query("items_per_page") String items_per_page,
                                                          @Query("page_number") String page_number,
                                                          @Header("accept") String accept,
                                                          @Header("Content-Type") String authorisation,
                                                          @Header("Authorization") String token
    );


    @DELETE(ApiConfiguration.subCategory)
    Call<ApiResponseArray<SubCategory>> deleteSubCategories(@Query("id") String id,
                                                            @Header("accept") String accept,
                                                            @Header("Content-Type") String authorisation,
                                                            @Header("Authorization") String token
    );


    @PATCH(ApiConfiguration.subCategory)
    Call<ApiResponseObject<SubCategory>> editSubCategories(@Query("id") String id,
                                                           @Body RequestBody body,
                                                           @Header("accept") String accept,
                                                           @Header("Content-Type") String authorisation,
                                                           @Header("Authorization") String token
    );


    @POST(ApiConfiguration.productBrands)
    Call<ApiResponseObject<ProductBrands>> addProductBrands(@Body RequestBody body,
                                                            @Header("accept") String accept,
                                                            @Header("Content-Type") String authorisation,
                                                            @Header("Authorization") String token
    );

    @GET(ApiConfiguration.productBrands)
    Call<ApiResponseObject<ProductBrands>> getProductBrands(@Header("accept") String accept,
                                                            @Header("Content-Type") String authorisation,
                                                            @Header("Authorization") String token
    );

    @GET(ApiConfiguration.productBrands)
    Call<ApiResponseObject<ProductBrands>> getProductBrands(@Query("items_per_page") String items_per_page,
                                                            @Query("page_number") String page_number,
                                                            @Header("accept") String accept,
                                                            @Header("Content-Type") String authorisation,
                                                            @Header("Authorization") String token
    );

    @DELETE(ApiConfiguration.productBrands)
    Call<ApiResponseArray<ProductBrands>> deleteProductBrands(@Query("id") String id,
                                                              @Header("accept") String accept,
                                                              @Header("Content-Type") String authorisation,
                                                              @Header("Authorization") String token
    );


    @PATCH(ApiConfiguration.productBrands)
    Call<ApiResponseObject<ProductBrands>> editProductBrands(@Query("id") String id,
                                                             @Body RequestBody body,
                                                             @Header("accept") String accept,
                                                             @Header("Content-Type") String authorisation,
                                                             @Header("Authorization") String token
    );

    @GET(ApiConfiguration.productBrandsSearch)
    Call<ApiResponseObject<ProductBrands>> subProductBrandsSearch(@Query("search_brand") String search_brand,
                                                                  @Header("accept") String accept,
                                                                  @Header("Content-Type") String authorisation,
                                                                  @Header("Authorization") String token
    );

    @GET(ApiConfiguration.productBrandsSearch)
    Call<ApiResponseObject<ProductBrands>> subProductBrandsSearch(@Query("items_per_page") String items_per_page,
                                                                  @Query("page_number") String page_number,
                                                                  @Query("search_brand") String search_brand,
                                                                  @Header("accept") String accept,
                                                                  @Header("Content-Type") String authorisation,
                                                                  @Header("Authorization") String token
    );


    @POST(ApiConfiguration.product)
    Call<ApiResponseObject<Product>> addProduct(@Body RequestBody body,
                                                @Header("accept") String accept,
                                                @Header("Content-Type") String authorisation,
                                                @Header("Authorization") String token
    );


    @GET(ApiConfiguration.product)
    Call<ApiResponseObject<ProductId>> getProductWithId(
            @Query("id") String id,
            @Header("accept") String accept,
            @Header("Content-Type") String authorisation,
            @Header("Authorization") String token
    );

    @GET(ApiConfiguration.product)
    Call<ApiResponseObject<Product>> getProduct(
            @Query("items_per_page") String items_per_page,
            @Query("page_number") String page_number,
            @Header("accept") String accept,
            @Header("Content-Type") String authorisation,
            @Header("Authorization") String token
    );

    @DELETE(ApiConfiguration.product)
    Call<ApiResponseArray<Product>> deleteProduct(@Query("id") String id,
                                                  @Header("accept") String accept,
                                                  @Header("Content-Type") String authorisation,
                                                  @Header("Authorization") String token
    );


    @PATCH(ApiConfiguration.product)
    Call<ApiResponseObject<Product>> editProduct(@Query("id") String id,
                                                 @Body RequestBody body,
                                                 @Header("accept") String accept,
                                                 @Header("Content-Type") String authorisation,
                                                 @Header("Authorization") String token
    );

    @GET(ApiConfiguration.productSearch)
    Call<ApiResponseObject<Product>> productSearch(@Query("items_per_page") String items_per_page,
                                                   @Query("page_number") String page_number,
                                                   @Query("search_product") String search_sub_category,
                                                   @Header("accept") String accept,
                                                   @Header("Content-Type") String authorisation,
                                                   @Header("Authorization") String token
    );

    @GET(ApiConfiguration.productFilter)
    Call<ApiResponseObject<Product>> productFilter(@Query("category_id") String category_id,
                                                   @Query("sub_category_id") String sub_category_id,
                                                   @Query("brand_id") String brand_id,
                                                   @Query("items_per_page") String items_per_page,
                                                   @Query("page_number") String page_number,
                                                   @Header("accept") String accept,
                                                   @Header("Content-Type") String authorisation,
                                                   @Header("Authorization") String token
    );

    @GET(ApiConfiguration.getHomeData)
    Call<ApiResponseObject<Home>> getHomeData(@Path("all") String date,
                                              @Query("pageSize") String items_per_page,
                                              @Query("pageNumber") String page_number,
                                              @Query("status") String status,
                                              @Header("accept") String accept,
                                              @Header("Content-Type") String authorisation,
                                              @Header("Authorization") String token
    );


    @POST(ApiConfiguration.productImage)
    Call<ApiResponseObject<Product>> addProductImage(@Body RequestBody body,
                                                     @Header("accept") String accept,
                                                     @Header("Content-Type") String authorisation,
                                                     @Header("Authorization") String token
    );

    @DELETE(ApiConfiguration.productImage)
    Call<ApiResponseObject<ProductId>> deleteProductImage(@Query("id") String id,
                                                          @Header("accept") String accept,
                                                          @Header("Content-Type") String authorisation,
                                                          @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST(ApiConfiguration.otpVerify)
    Call<ApiResponseObject<User>> otpVerify(@Field("mobile") String mobileNumber, @Field("otp") String otp);


    @FormUrlEncoded
    @POST(ApiConfiguration.resendOtp)
    Call<ApiResponseObject<User>> resendOtp(@Field("mobile") String mobileNumber);


    @FormUrlEncoded
    @POST(ApiConfiguration.changePassword)
    Call<ApiResponseObject<User>> changePassword(@Field("user_id") String user_id,
                                                 @Field("current_password") String current_password,
                                                 @Field("new_password") String new_password
    );

    @GET(ApiConfiguration.getAllOrder)
    Call<ApiResponseObject<Order>> getAllOrder(
            @Query("pageSize") String items_per_page,
            @Query("pageNumber") String page_number,
            @Header("accept") String accept,
            @Header("Content-Type") String authorisation,
            @Header("Authorization") String token
    );

    @GET(ApiConfiguration.searchOrder)
    Call<ApiResponseObject<Order>> searchOrder(@Query("pageSize")   String items_per_page,
                                               @Query("pageNumber") String page_number,
                                               @Query("orderId") String orderId,
                                               @Header("accept") String accept,
                                               @Header("Content-Type") String authorisation,
                                               @Header("Authorization") String token
    );

    @GET(ApiConfiguration.fetchOrderDetail)
    Call<ApiResponseObject<Order>> fetchOrderDetail(@Path("orderId") String orderId,
                                                    @Header("accept") String accept,
                                                    @Header("Content-Type") String authorisation,
                                                    @Header("Authorization") String token
    );

    @GET(ApiConfiguration.orderFilter)
    Call<ApiResponseObject<Order>> orderFilter(@Query("orderId") String orderId,
                                               @Query("pageSize") String items_per_page,
                                               @Query("pageNumber") String page_number,
                                               @Query("createdDate") String createdDate,
                                               @Query("outOfDeliveryDate") String outOfDeliveryDate,
                                               @Query("deliveredDate") String deliveredDate,
                                               @Query("status") String status,
                                               @Header("accept") String accept,
                                               @Header("Content-Type") String authorisation,
                                               @Header("Authorization") String token
    );

    @PUT(ApiConfiguration.updateOrderDetail)
    Call<ApiResponseObject<Order>> updateOrderDetail(@Path("id") String orderId,
                                                     @Body RequestBody body,
                                                     @Header("accept") String accept,
                                                     @Header("Content-Type") String authorisation,
                                                     @Header("Authorization") String token
    );


    @GET(ApiConfiguration.getCustomer)
    Call<ApiResponseObject<Customer>> getCustomer(@Query("pageNumber") String pageNumber,
                                                  @Query("pageSize") String pageSize,
                                                  @Header("accept") String accept,
                                                  @Header("Content-Type") String authorisation,
                                                  @Header("Authorization") String token
    );

    @GET(ApiConfiguration.searchCustomer)
    Call<ApiResponseObject<Customer>> searchCustomer(@Query("pageNumber") String pageNumber,
                                                     @Query("pageSize") String pageSize,
                                                     @Query("mobileNumber") String findByMobile,
                                                     @Header("accept") String accept,
                                                     @Header("Content-Type") String authorisation,
                                                     @Header("Authorization") String token
    );

    @GET(ApiConfiguration.getCustomerWithId)
    Call<ApiResponseObject<Customer.Content>> getCustomerWithId(@Path("id") String customerId,
                                                                @Header("accept") String accept,
                                                                @Header("Content-Type") String authorisation,
                                                                @Header("Authorization") String token
    );

    @PUT(ApiConfiguration.putCustomer)
    Call<ApiResponseObject<Customer>> putCustomer(@Path("id") String customerId,
                                                  @Body RequestBody body,
                                                  @Header("accept") String accept,
                                                  @Header("Content-Type") String authorisation,
                                                  @Header("Authorization") String token
    );
}
