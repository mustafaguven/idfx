package tr.com.idefix.data.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import tr.com.idefix.data.entity.AddItemToBasketRequestEntity;
import tr.com.idefix.data.entity.BasketItemResponseEntity;
import tr.com.idefix.data.entity.BestSellersResponseEntity;
import tr.com.idefix.data.entity.CategoryTreeResponseEntity;
import tr.com.idefix.data.entity.EmptyRequestBody;
import tr.com.idefix.data.entity.FilterCategoryProductsContentRequestEntity;
import tr.com.idefix.data.entity.FilterCategoryProductsResponseEntity;
import tr.com.idefix.data.entity.NewestResponseEntity;
import tr.com.idefix.data.entity.OtherProductsRequestEntity;
import tr.com.idefix.data.entity.OtherProductsResponseEntity;
import tr.com.idefix.data.entity.ProductDetailResponseEntity;
import tr.com.idefix.data.entity.ReviewAddResponseEntity;
import tr.com.idefix.data.entity.ReviewListResponseEntity;
import tr.com.idefix.data.entity.ReviewRequestEntity;
import tr.com.idefix.data.entity.ReviewsRequestEntity;
import tr.com.idefix.data.repository.datasource.catalog.CatalogDataStore;
import tr.com.idefix.data.repository.datasource.catalog.CatalogDataStoreFactory;

/**
 * Created by utkan on 14/09/15.
 */
@Singleton
public class CatalogDataRepository
        implements ICatalogRepository {

    private final CatalogDataStoreFactory catalogDataStoreFactory;

    @Inject
    public CatalogDataRepository() {
        this.catalogDataStoreFactory = new CatalogDataStoreFactory();
    }

    @Override
    public Observable<CategoryTreeResponseEntity> getCategory(int id) {
        final CatalogDataStore catalogDataStore = catalogDataStoreFactory.createCloudDataStore();
        return catalogDataStore.getCategoryTree(id);
    }

    @Override
    public Observable<BestSellersResponseEntity> getBestSellers(int id, int size) {
        final CatalogDataStore catalogDataStore = catalogDataStoreFactory.createCloudDataStore();
        return catalogDataStore.getBestSellers(id, size);
    }

    @Override
    public Observable<NewestResponseEntity> getMostNewests(int id, int size) {
        final CatalogDataStore catalogDataStore = catalogDataStoreFactory.createCloudDataStore();
        return catalogDataStore.getMostNewests(id, size);
    }

    @Override
    public Observable<ProductDetailResponseEntity> getProductDetail(String sku) {
        final CatalogDataStore catalogDataStore = catalogDataStoreFactory.createCloudDataStore();
        return catalogDataStore.getProductDetail(new EmptyRequestBody(), sku);
    }

    @Override
    public Observable<ReviewListResponseEntity> getProductReviews(
            ReviewsRequestEntity reviewsRequestEntity) {
        final CatalogDataStore catalogDataStore = catalogDataStoreFactory.createCloudDataStore();
        return catalogDataStore.getProductReviews(reviewsRequestEntity);
    }

    @Override
    public Observable<BasketItemResponseEntity> addItemsToBasket(AddItemToBasketRequestEntity addItemToBasketRequestEntity, String sessionId) {
        final CatalogDataStore catalogDataStore = catalogDataStoreFactory.createCloudDataStore();
        return catalogDataStore.addItemsToBasket(addItemToBasketRequestEntity,
                sessionId);
    }

    @Override
    public Observable<OtherProductsResponseEntity> otherProducts(
            OtherProductsRequestEntity otherProductsRequestEntity) {
        final CatalogDataStore catalogDataStore = catalogDataStoreFactory.createCloudDataStore();
        return catalogDataStore.otherProducts(otherProductsRequestEntity);
    }

    @Override
    public Observable<FilterCategoryProductsResponseEntity> filterCategoryProducts(
            FilterCategoryProductsContentRequestEntity filterCategoryProductsContentRequestEntity) {
        final CatalogDataStore catalogDataStore = catalogDataStoreFactory.createCloudDataStore();
        return catalogDataStore.filterCategoryProducts(filterCategoryProductsContentRequestEntity);
    }

    @Override
    public Observable<ReviewAddResponseEntity> productReviewsAdd(ReviewRequestEntity reviewRequestEntity) {
        final CatalogDataStore catalogDataStore = catalogDataStoreFactory.createCloudDataStore();
        return catalogDataStore.productReviewsAdd(reviewRequestEntity);
    }
}
