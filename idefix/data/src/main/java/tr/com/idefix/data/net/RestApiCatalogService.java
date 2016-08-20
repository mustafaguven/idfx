package tr.com.idefix.data.net;


import android.text.TextUtils;

import java.util.List;

import retrofit.http.Body;
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
import tr.com.idefix.data.entity.PictureModelEntity;
import tr.com.idefix.data.entity.ProductDetailResponseEntity;
import tr.com.idefix.data.entity.ReviewAddResponseEntity;
import tr.com.idefix.data.entity.ReviewListResponseEntity;
import tr.com.idefix.data.entity.ReviewRequestEntity;
import tr.com.idefix.data.entity.ReviewsRequestEntity;
import tr.com.idefix.data.exception.ResponseFailException;
import tr.com.idefix.data.exception.ResponseNullException;

/**
 * RestApi for retrieving data from the network.
 */
public class RestApiCatalogService
        extends RestApiBaseService<RestApiCatalog>
        implements RestApiCatalog {

    static final String API_CATALOG_URL = API_BASE_URL + "apiCatalog/";

    public RestApiCatalogService() {

        super(API_CATALOG_URL, RestApiCatalog.class);
    }

    public RestApiCatalog getApi() {
        return restApi;
    }

    @Override
    public Observable<List<MainCategoryEntity>> getMainCategories(
            @Body EmptyRequestBody emptyRequestBody) {
        return getApi().getMainCategories(emptyRequestBody);
    }

    @Override
    public Observable<BannerResponseEntity> getBanners(@Body EmptyRequestBody emptyRequestBody) {
        return getApi()
                .getBanners(emptyRequestBody)
                .flatMap(entity -> {
                    if (entity == null ||
                            entity.getSuccess() == null ||
                            !entity.getSuccess()) {
                        return Observable.error(new ResponseNullException());
                    }

                    return Observable.just(entity);
                })
                ;
    }

    @Override
    public Observable<CategoryTreeResponseEntity> getCategoryTree(
            @Body CategoryRequestEntity categoryRequestEntity) {
        return getApi()
                .getCategoryTree(categoryRequestEntity);
    }

    @Override
    public Observable<BestSellersResponseEntity> getBestSellers(
            @Body BestSellersRequestEntity bestSellersRequestEntity) {
        return getApi()
                .getBestSellers(bestSellersRequestEntity);
    }

    @Override
    public Observable<NewestResponseEntity> getMostNewests(
            @Body NewestRequestEntity newestRequestEntity) {
        return getApi()
                .getMostNewests(newestRequestEntity);
    }

    @Override
    public Observable<ProductDetailResponseEntity> getProductDetail(
            @Body EmptyRequestBody emptyRequestBody,
            @Query(value = "code", encodeValue = false) String sku) {

        return getApi()
                .getProductDetail(emptyRequestBody, sku)
                .doOnNext(entity -> {
                    if (entity != null) {
                        if (entity.product() != null &&
                                entity.product().pictureModels() != null &&
                                entity.product().pictureModels().size() > 0) {
                            for (PictureModelEntity pe : entity.product().pictureModels()) {
                                pe.imageUrl(RestApiBaseService.API_BASE_IMAGE_URL + pe.imageUrl());
                                pe.fullSizeImageUrl(RestApiBaseService.API_BASE_IMAGE_URL + pe.fullSizeImageUrl());
                            }
                        }
                    }
                })
                ;
    }

    @Override
    public Observable<ReviewListResponseEntity> getProductReviews(
            @Body ReviewsRequestEntity reviewsRequestEntity) {
        return getApi()
                .getProductReviews(reviewsRequestEntity)
                .flatMap(entity -> {

                    if (entity != null &&
                            entity.success() != null &&
                            entity.success() &&
                            entity.reviews() != null &&
                            entity.reviews().size() > 0) {
                        return Observable.just(entity);
                    }

                    return null;
                })
                ;
    }

    @Override
    public Observable<BasketItemResponseEntity> addItemsToBasket(
            @Body AddItemToBasketRequestEntity addItemToBasketRequestEntity,
            @Query(value = "signedRequest", encodeValue = false) String sessionID) {
        return getApi()
                .addItemsToBasket(addItemToBasketRequestEntity, sessionID)
                .flatMap(entity -> {
                    if (entity != null && entity.success() != null) {
                        if (entity.success()) {
                            return Observable.just(entity);
                        } else {
                            return Observable.error(new ResponseFailException(
                                    !TextUtils.isEmpty(entity.message()) ? entity.message() : ""
                            ));
                        }
                    }
                    return null;
                })
                ;
    }

    @Override
    public Observable<OtherProductsResponseEntity> otherProducts(
            @Body OtherProductsRequestEntity otherProductsRequestEntity) {
        return getApi()
                .otherProducts(otherProductsRequestEntity)
                .flatMap(entity -> {

                    if (entity != null) {
                        if (entity.success() != null) {
                            if (entity.success()) {
                                return Observable.just(entity);
                            } else {
                                return Observable.error(new ResponseNullException());
                            }
                        }
                    } else {
                        return Observable.error(new ResponseNullException());
                    }
                    return null;
                })
                ;
    }

    @Override
    public Observable<FilterCategoryProductsResponseEntity> filterCategoryProducts(
            @Body FilterCategoryProductsContentRequestEntity filterCategoryProductsContentRequestEntity) {
        return getApi()
                .filterCategoryProducts(filterCategoryProductsContentRequestEntity)
                ;
    }

    @Override
    public Observable<ReviewAddResponseEntity> productReviewsAdd(
            @Body ReviewRequestEntity reviewRequestEntity) {
        return getApi()
                .productReviewsAdd(reviewRequestEntity);
    }

}
