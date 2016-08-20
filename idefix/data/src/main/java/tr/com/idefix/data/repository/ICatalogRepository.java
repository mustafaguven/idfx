package tr.com.idefix.data.repository;

import rx.Observable;
import tr.com.idefix.data.entity.AddItemToBasketRequestEntity;
import tr.com.idefix.data.entity.BasketItemResponseEntity;
import tr.com.idefix.data.entity.BestSellersResponseEntity;
import tr.com.idefix.data.entity.CategoryTreeResponseEntity;
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

/**
 * Created by utkan on 13/09/15.
 */
public interface ICatalogRepository {

    Observable<CategoryTreeResponseEntity> getCategory(int id);

    Observable<BestSellersResponseEntity> getBestSellers(int id, int size);

    Observable<NewestResponseEntity> getMostNewests(int id, int size);

    Observable<ProductDetailResponseEntity> getProductDetail(String sku);

    Observable<ReviewListResponseEntity> getProductReviews(
            ReviewsRequestEntity reviewsRequestEntity);

    Observable<BasketItemResponseEntity> addItemsToBasket(
            AddItemToBasketRequestEntity addItemToBasketRequestEntity, String sessionId);

    Observable<OtherProductsResponseEntity> otherProducts(
            OtherProductsRequestEntity otherProductsRequestEntity);

    Observable<FilterCategoryProductsResponseEntity> filterCategoryProducts(
            FilterCategoryProductsContentRequestEntity filterCategoryProductsContentRequestEntity);

    Observable<ReviewAddResponseEntity> productReviewsAdd(ReviewRequestEntity reviewRequestEntity);
}
