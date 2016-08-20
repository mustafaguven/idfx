package tr.com.idefix.data.repository.datasource.catalog;

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

public interface CatalogDataStore {

    Observable<CategoryTreeResponseEntity> getCategoryTree(int id);

    Observable<BestSellersResponseEntity> getBestSellers(int id, int size);

    Observable<NewestResponseEntity> getMostNewests(int id, int size);

    Observable<ProductDetailResponseEntity> getProductDetail(
            EmptyRequestBody emptyRequestBody,
            String sku);

    Observable<ReviewListResponseEntity> getProductReviews(
            ReviewsRequestEntity reviewsRequestEntity);

    Observable<BasketItemResponseEntity> addItemsToBasket(
            AddItemToBasketRequestEntity addItemToBasketRequestEntity,
            String id);

    Observable<OtherProductsResponseEntity> otherProducts(
            OtherProductsRequestEntity otherProductsRequestEntity);

    Observable<FilterCategoryProductsResponseEntity> filterCategoryProducts(
            FilterCategoryProductsContentRequestEntity filterCategoryProductsContentRequestEntity);

    Observable<ReviewAddResponseEntity> productReviewsAdd(ReviewRequestEntity reviewRequestEntity);
}
