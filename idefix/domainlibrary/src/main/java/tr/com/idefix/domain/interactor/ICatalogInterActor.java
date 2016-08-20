package tr.com.idefix.domain.interactor;

import java.util.List;
import java.util.Map;
import tr.com.idefix.domain.BasketItemResponse;
import tr.com.idefix.domain.BestSeller;
import tr.com.idefix.domain.CategoryTree;
import tr.com.idefix.domain.FilterItem;
import tr.com.idefix.domain.FilteredProduct;
import tr.com.idefix.domain.Product;
import tr.com.idefix.domain.ProductOther;
import tr.com.idefix.domain.Review;
import tr.com.idefix.domain.ReviewAddResult;
import tr.com.idefix.domain.TheNewest;
import tr.com.idefix.domain.enums.SortEnums;

/**
 * Created by utkan on 13/09/15.
 */
public interface ICatalogInterActor extends IInterActor {

  void getCatalogTree(int parentID, DefaultSubscriber<CategoryTree> subscriber);

  void getBestSellers(int parentID, int size, DefaultSubscriber<List<BestSeller>> subscriber);

  void getMostNewests(int parentID, int size, DefaultSubscriber<List<TheNewest>> subscriber);

  void getProductDetail(String sku, DefaultSubscriber<Product> subscriber);

  void getProductReviews(DefaultSubscriber<List<Review>> subscriber, int productID, int take);

  void addItemsToBasket(
      DefaultSubscriber<BasketItemResponse> subscriber, String code, int quantity
  );

  void getOtherProducts(
      DefaultSubscriber<List<ProductOther>> subscriber, int type, int id, int currentProductId
  );

  void filterCategoryProducts(
      DefaultSubscriber<FilteredProduct> subscriber, String categoryid, String parentId, int page
  );

  void filterCategoryProducts(
      DefaultSubscriber<FilteredProduct> subscriber, String categoryid, String parentid,
      Map<Integer, List<FilterItem>> filters, SortEnums sortEnum, int page
  );

  void productReviewsAdd(
      DefaultSubscriber<ReviewAddResult> subscriber, int productId, String title, String review
  );
}
