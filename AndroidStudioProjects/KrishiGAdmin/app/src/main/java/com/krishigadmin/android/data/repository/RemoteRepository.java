package com.krishigadmin.android.data.repository;

import com.krishigadmin.android.data.remote.ApiResponseArray;
import com.krishigadmin.android.data.remote.ApiResponseObject;
import com.krishigadmin.android.data.remote.api.ApiService;
import com.krishigadmin.android.model.Category;
import com.krishigadmin.android.model.Product;
import com.krishigadmin.android.model.ProductBrands;
import com.krishigadmin.android.model.ProductId;
import com.krishigadmin.android.model.SubCategory;
import com.krishigadmin.android.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

@Singleton
public class RemoteRepository {

    private ApiService apiService;

    @Inject
    public RemoteRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Call<ApiResponseObject<User>> signUp(RequestBody jsonObject, String accept, String authorisation) {
        return apiService.signUp(jsonObject, accept, authorisation);
    }

    public Call<ApiResponseObject<User>> editUser(String id, RequestBody jsonObject, String accept, String authorisation, String token) {
        return apiService.editUser(id, jsonObject, accept, authorisation, token);
    }

    public Call<ApiResponseObject<User>> getUserWithId(String id,
                                                       String accept,
                                                       String authorisation,
                                                       String token) {
        return apiService.getUserWithId(id, accept, authorisation, token);
    }

    public Call<ApiResponseObject<User>> getUser(
            String items_per_page,
            String page_number,
            String accept,
            String authorisation,
            String token) {
        return apiService.getUser(items_per_page, page_number, accept, authorisation, token);
    }

    public Call<ApiResponseObject<User>> getUserSearch(String items_per_page,
                                                       String page_number,
                                                       String search_user,
                                                       String accept,
                                                       String authorisation,
                                                       String token

    ) {
        return apiService.getUserSearch(items_per_page,page_number,search_user, accept, authorisation, token);
    }


    public Call<ApiResponseObject<User>> otpVerify(String mobileNumber, String otp) {
        return apiService.otpVerify(mobileNumber, otp);
    }

    public Call<ApiResponseObject<User>> login(RequestBody body,  String role,String accept, String authorisation) {
        return apiService.login(body,role, accept, authorisation);
    }

    public Call<ApiResponseObject<User>> categories(RequestBody body, String accept, String authorisation, String token) {
        return apiService.addCategories(body, accept, authorisation, token);
    }

    public Call<ApiResponseArray<Category>> getCategories(String accept, String authorisation, String token) {
        return apiService.getCategories(accept, authorisation, token);
    }

    public Call<ApiResponseArray<Category>> deleteCategories(String id, String accept, String authorisation, String token) {
        return apiService.deleteCategories(id, accept, authorisation, token);
    }

    public Call<ApiResponseObject<Category>> editCategories(String id, RequestBody body, String accept, String authorisation, String token) {
        return apiService.editCategories(id, body, accept, authorisation, token);
    }

    public Call<ApiResponseArray<Category>> image(MultipartBody.Part image, String token) {
        return apiService.image(image, token);
    }


    public Call<ApiResponseObject<SubCategory>> subCategorySearch(String search_sub_category, String accept, String authorisation, String token) {
        return apiService.subCategorySearch(search_sub_category, accept, authorisation, token);
    }

    public Call<ApiResponseObject<SubCategory>> subCategorySearch(String items_per_page,
                                                                  String page_number, String search_sub_category, String accept, String authorisation, String token) {
        return apiService.subCategorySearch(items_per_page, page_number, search_sub_category, accept, authorisation, token);
    }


    public Call<ApiResponseObject<User>> subCategory(RequestBody body, String accept, String authorisation, String token) {
        return apiService.addSubCategory(body, accept, authorisation, token);
    }

    public Call<ApiResponseObject<SubCategory>> getSubCategories(String accept, String authorisation, String token) {
        return apiService.getSubCategories(accept, authorisation, token);
    }

    public Call<ApiResponseObject<SubCategory>> getSubCategories(String items_per_page,
                                                                 String page_number, String accept, String authorisation, String token) {
        return apiService.getSubCategories(items_per_page, page_number, accept, authorisation, token);
    }


    public Call<ApiResponseArray<SubCategory>> deleteSubCategories(String id, String accept, String authorisation, String token) {
        return apiService.deleteSubCategories(id, accept, authorisation, token);
    }


    public Call<ApiResponseObject<SubCategory>> editSubCategories(String id, RequestBody body, String accept, String authorisation, String token) {
        return apiService.editSubCategories(id, body, accept, authorisation, token);
    }


    public Call<ApiResponseObject<ProductBrands>> addProductBrands(RequestBody body, String accept, String authorisation, String token) {
        return apiService.addProductBrands(body, accept, authorisation, token);
    }

    public Call<ApiResponseObject<ProductBrands>> getProductBrands(String accept, String authorisation, String token) {
        return apiService.getProductBrands(accept, authorisation, token);
    }

    public Call<ApiResponseObject<ProductBrands>> getProductBrands(String items_per_page,
                                                                   String page_number, String accept, String authorisation, String token) {
        return apiService.getProductBrands(items_per_page, page_number, accept, authorisation, token);
    }

    public Call<ApiResponseArray<ProductBrands>> deleteProductBrands(String id, String accept, String authorisation, String token) {
        return apiService.deleteProductBrands(id, accept, authorisation, token);
    }


    public Call<ApiResponseObject<ProductBrands>> editProductBrands(String id, RequestBody body, String accept, String authorisation, String token) {
        return apiService.editProductBrands(id, body, accept, authorisation, token);
    }


    public Call<ApiResponseObject<ProductBrands>> subProductBrandsSearch(String search_brand, String accept, String authorisation, String token) {
        return apiService.subProductBrandsSearch(search_brand, accept, authorisation, token);
    }

    public Call<ApiResponseObject<ProductBrands>> subProductBrandsSearch(String items_per_page,
                                                                         String page_number, String search_brand, String accept, String authorisation, String token) {
        return apiService.subProductBrandsSearch(items_per_page, page_number, search_brand, accept, authorisation, token);
    }

    public Call<ApiResponseObject<Product>> addProduct(RequestBody body, String accept, String authorisation, String token) {
        return apiService.addProduct(body, accept, authorisation, token);
    }


    public Call<ApiResponseObject<Product>> getProduct(
            String items_per_page,
            String page_number, String accept, String authorisation, String token) {
        return apiService.getProduct(items_per_page, page_number, accept, authorisation, token);
    }

    public Call<ApiResponseObject<ProductId>> getProductWithId(
            String id, String accept, String authorisation, String token) {
        return apiService.getProductWithId(id, accept, authorisation, token);
    }


    public Call<ApiResponseObject<Product>> productSearch(String items_per_page,
                                                          String page_number, String search_brand, String accept, String authorisation, String token) {
        return apiService.productSearch(items_per_page, page_number, search_brand, accept, authorisation, token);
    }

    public Call<ApiResponseObject<Product>> productFilter(String category_id,
                                                          String sub_category_id,
                                                          String brand_id,
                                                          String items_per_page,
                                                          String page_number,
                                                          String accept, String authorisation, String token) {
        return apiService.productFilter(category_id, sub_category_id, brand_id, items_per_page, page_number, accept, authorisation, token);
    }

    public Call<ApiResponseArray<Product>> deleteProduct(String id, String accept, String authorisation, String token) {
        return apiService.deleteProduct(id, accept, authorisation, token);
    }


    public Call<ApiResponseObject<Product>> addProductImage(RequestBody body, String accept, String authorisation, String token) {
        return apiService.addProductImage(body, accept, authorisation, token);
    }

    public Call<ApiResponseObject<Product>> editProduct(String id, RequestBody body, String accept, String authorisation, String token) {
        return apiService.editProduct(id, body, accept, authorisation, token);
    }


    public Call<ApiResponseObject<ProductId>> deleteProductImage(String id, String accept, String authorisation, String token) {
        return apiService.deleteProductImage(id, accept, authorisation, token);
    }

    public Call<ApiResponseObject<User>> resendOtp(String mobileNumber) {
        return apiService.resendOtp(mobileNumber);
    }


    public Call<ApiResponseObject<User>> changePassword(String user_id, String current_password, String new_password) {
        return apiService.changePassword(user_id, current_password, new_password);
    }


}
