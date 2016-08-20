package tr.com.idefix.data.repository.datasource.catalog;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tr.com.idefix.data.entity.AddItemToBasketRequestEntity;
import tr.com.idefix.data.entity.BasketItemResponseEntity;
import tr.com.idefix.data.entity.BestSellersRequestEntity;
import tr.com.idefix.data.entity.BestSellersResponseEntity;
import tr.com.idefix.data.entity.CategoryRequestEntity;
import tr.com.idefix.data.entity.CategoryTreeResponseEntity;
import tr.com.idefix.data.entity.EmptyRequestBody;
import tr.com.idefix.data.entity.FilterCategoryProductsContentRequestEntity;
import tr.com.idefix.data.entity.FilterCategoryProductsResponseEntity;
import tr.com.idefix.data.entity.NewestRequestEntity;
import tr.com.idefix.data.entity.NewestResponseEntity;
import tr.com.idefix.data.entity.OtherProductsRequestEntity;
import tr.com.idefix.data.entity.OtherProductsResponseEntity;
import tr.com.idefix.data.entity.ProductDetailResponseEntity;
import tr.com.idefix.data.entity.ReviewAddResponseEntity;
import tr.com.idefix.data.entity.ReviewListResponseEntity;
import tr.com.idefix.data.entity.ReviewRequestEntity;
import tr.com.idefix.data.entity.ReviewsRequestEntity;
import tr.com.idefix.data.net.RestApiCatalog;


public class CloudCatalogDataStore implements CatalogDataStore {

    private final RestApiCatalog restApi;

    public CloudCatalogDataStore(RestApiCatalog restApi) {
        this.restApi = restApi;
    }

    @Override
    public Observable<CategoryTreeResponseEntity> getCategoryTree(int id) {
        return restApi
                .getCategoryTree(new CategoryRequestEntity().id(id))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<BestSellersResponseEntity> getBestSellers(int id, int size) {
        return restApi
                .getBestSellers(new BestSellersRequestEntity()
                                .categoryid(id)
                                .size(size)
                )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<NewestResponseEntity> getMostNewests(int id, int size) {
        return restApi
                .getMostNewests(new NewestRequestEntity()
                                .id(id)
                                .size(size)
                )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ProductDetailResponseEntity> getProductDetail(
            EmptyRequestBody emptyRequestBody,
            String sku) {

        return restApi
                .getProductDetail(emptyRequestBody, sku)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ReviewListResponseEntity> getProductReviews(
            ReviewsRequestEntity reviewsRequestEntity) {
        return restApi
                .getProductReviews(reviewsRequestEntity)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    @Override
    public Observable<BasketItemResponseEntity> addItemsToBasket(
            AddItemToBasketRequestEntity addItemToBasketRequestEntity,
            String sessionId) {
        return restApi
                .addItemsToBasket(addItemToBasketRequestEntity, sessionId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<OtherProductsResponseEntity> otherProducts(
            OtherProductsRequestEntity otherProductsRequestEntity) {
        return restApi
                .otherProducts(otherProductsRequestEntity)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    @Override
    public Observable<FilterCategoryProductsResponseEntity> filterCategoryProducts(
            FilterCategoryProductsContentRequestEntity filterCategoryProductsContentRequestEntity) {
        return restApi
                .filterCategoryProducts(filterCategoryProductsContentRequestEntity)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    @Override
    public Observable<ReviewAddResponseEntity> productReviewsAdd(ReviewRequestEntity reviewRequestEntity) {
        return restApi
                .productReviewsAdd(reviewRequestEntity)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
