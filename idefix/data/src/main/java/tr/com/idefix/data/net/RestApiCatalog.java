package tr.com.idefix.data.net;


import java.util.List;

import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;
import tr.com.idefix.data.entity.AddItemToBasketRequestEntity;
import tr.com.idefix.data.entity.BannerResponseEntity;
import tr.com.idefix.data.entity.BasketItemResponseEntity;
import tr.com.idefix.data.entity.BestSellersRequestEntity;
import tr.com.idefix.data.entity.BestSellersResponseEntity;
import tr.com.idefix.data.entity.CategoryRequestEntity;
import tr.com.idefix.data.entity.CategoryTreeResponseEntity;
import tr.com.idefix.data.entity.EmptyRequestBody;
import tr.com.idefix.data.entity.FilterCategoryProductsContentRequestEntity;
import tr.com.idefix.data.entity.FilterCategoryProductsResponseEntity;
import tr.com.idefix.data.entity.MainCategoryEntity;
import tr.com.idefix.data.entity.NewestRequestEntity;
import tr.com.idefix.data.entity.NewestResponseEntity;
import tr.com.idefix.data.entity.OtherProductsRequestEntity;
import tr.com.idefix.data.entity.OtherProductsResponseEntity;
import tr.com.idefix.data.entity.ProductDetailResponseEntity;
import tr.com.idefix.data.entity.ReviewAddResponseEntity;
import tr.com.idefix.data.entity.ReviewListResponseEntity;
import tr.com.idefix.data.entity.ReviewRequestEntity;
import tr.com.idefix.data.entity.ReviewsRequestEntity;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApiCatalog {

    @POST("/MainCategories")
    Observable<List<MainCategoryEntity>> getMainCategories(
            @Body EmptyRequestBody emptyRequestBody);

    @POST("/GetBanners")
    Observable<BannerResponseEntity> getBanners(
            @Body EmptyRequestBody emptyRequestBody);

    @POST("/CategoryWithTree")
    Observable<CategoryTreeResponseEntity> getCategoryTree(
            @Body CategoryRequestEntity categoryRequestEntity);

/*    @POST("/CategoryMostSold")
    Observable<BestSellersResponseEntity> getBestSellers(
            @Body BestSellersRequestEntity bestSellersRequestEntity);*/

    @POST("/SearchMostSold")
    Observable<BestSellersResponseEntity> getBestSellers(
            @Body BestSellersRequestEntity bestSellersRequestEntity);

    @POST("/CategoryNewest")
    Observable<NewestResponseEntity> getMostNewests(
            @Body NewestRequestEntity newestRequestEntity);

    @POST("/ProductDetails")
    Observable<ProductDetailResponseEntity> getProductDetail(
            @Body EmptyRequestBody emptyRequestBody, @Query(value = "code", encodeValue = false) String sku);

    @POST("/GetProductReviewsByProductId")
    Observable<ReviewListResponseEntity> getProductReviews(
            @Body ReviewsRequestEntity reviewsRequestEntity);

    @POST("/AddItemsToBasket")
    Observable<BasketItemResponseEntity> addItemsToBasket(
            @Body AddItemToBasketRequestEntity addItemToBasketRequestEntity,
            @Query(value = "signedRequest", encodeValue = false) String sessionID);

    @POST("/OtherProducts")
    Observable<OtherProductsResponseEntity> otherProducts(
            @Body OtherProductsRequestEntity otherProductsRequestEntity);

    @POST("/FilterCategoryProducts")
    Observable<FilterCategoryProductsResponseEntity> filterCategoryProducts(
            @Body FilterCategoryProductsContentRequestEntity filterCategoryProductsContentRequestEntity);

    @POST("/ProductReviewsAdd")
    Observable<ReviewAddResponseEntity> productReviewsAdd(
            @Body ReviewRequestEntity reviewRequestEntity);
}
