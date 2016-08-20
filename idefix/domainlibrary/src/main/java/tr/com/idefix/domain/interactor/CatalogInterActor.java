package tr.com.idefix.domain.interactor;

import android.text.TextUtils;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;
import tr.com.idefix.data.entity.AddItemToBasketRequestEntity;
import tr.com.idefix.data.entity.FilterCategoryProductsContentRequestEntity;
import tr.com.idefix.data.entity.FilterCategoryProductsRequestEntity;
import tr.com.idefix.data.entity.OtherProductsRequestEntity;
import tr.com.idefix.data.entity.ReviewRequestEntity;
import tr.com.idefix.data.entity.ReviewsRequestEntity;
import tr.com.idefix.data.exception.ResponseFailException;
import tr.com.idefix.data.repository.CatalogDataRepository;
import tr.com.idefix.data.repository.ICatalogRepository;
import tr.com.idefix.domain.BasketItemResponse;
import tr.com.idefix.domain.BestSeller;
import tr.com.idefix.domain.CategoryTree;
import tr.com.idefix.domain.DomainApplication;
import tr.com.idefix.domain.DomainContext;
import tr.com.idefix.domain.FilterItem;
import tr.com.idefix.domain.FilteredProduct;
import tr.com.idefix.domain.Product;
import tr.com.idefix.domain.ProductOther;
import tr.com.idefix.domain.Review;
import tr.com.idefix.domain.ReviewAddResult;
import tr.com.idefix.domain.TheNewest;
import tr.com.idefix.domain.constants.FilterMap;
import tr.com.idefix.domain.enums.SortEnums;
import tr.com.idefix.domain.mapper.CatalogEntityDataMapper;

/**
 * Created by utkan on 13/09/15.
 */
public class CatalogInterActor implements ICatalogInterActor {

  final int stackSize = 6;
  private final ICatalogRepository repository;
  CatalogEntityDataMapper catalogEntityDataMapper;
  DomainContext domainContext;
  private CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject public CatalogInterActor() {
    this.repository = new CatalogDataRepository();
    domainContext = DomainApplication.getInstance().getDomainApplicationComponent().domainContext();
  }

  @Override public void unSubscribe() {
    compositeSubscription.unsubscribe();
  }

  @Override public void getCatalogTree(int parentID, DefaultSubscriber<CategoryTree> subscriber) {

    compositeSubscription.add(subscriber);

    if (catalogEntityDataMapper == null) {
      catalogEntityDataMapper = new CatalogEntityDataMapper();
    }

    repository.getCategory(parentID).map(catalogEntityDataMapper::transform).subscribe(subscriber);
  }

  @Override public void getBestSellers(
      int parentID, int size, DefaultSubscriber<List<BestSeller>> subscriber
  ) {

    compositeSubscription.add(subscriber);

    if (catalogEntityDataMapper == null) {
      catalogEntityDataMapper = new CatalogEntityDataMapper();
    }

    repository.getBestSellers(parentID, size)
        .map(catalogEntityDataMapper::transform)
        .subscribe(subscriber);
  }

  @Override public void getMostNewests(
      int parentID, int size, DefaultSubscriber<List<TheNewest>> subscriber
  ) {

    compositeSubscription.add(subscriber);

    if (catalogEntityDataMapper == null) {
      catalogEntityDataMapper = new CatalogEntityDataMapper();
    }

    repository.getMostNewests(parentID, size)
        .map(catalogEntityDataMapper::transform)
        .subscribe(subscriber);
  }

  @Override public void getProductDetail(String sku, DefaultSubscriber<Product> subscriber) {

    compositeSubscription.add(subscriber);

    if (catalogEntityDataMapper == null) {
      catalogEntityDataMapper = new CatalogEntityDataMapper();
    }

    repository.getProductDetail(sku).map(catalogEntityDataMapper::transform).subscribe(subscriber);
  }

  @Override public void getProductReviews(
      DefaultSubscriber<List<Review>> subscriber, int productID, int take
  ) {

    compositeSubscription.add(subscriber);

    if (catalogEntityDataMapper == null) {
      catalogEntityDataMapper = new CatalogEntityDataMapper();
    }

    repository.getProductReviews(new ReviewsRequestEntity().productId(productID).take(take))
        .map(catalogEntityDataMapper::transform)
        .subscribe(subscriber);
  }

  @Override public void addItemsToBasket(
      DefaultSubscriber<BasketItemResponse> subscriber, String code, int quantity
  ) {
    String sessionID = getSessionID();

    if (TextUtils.isEmpty(sessionID)) {
      subscriber.onNext(null);
      subscriber.onCompleted();
      return;
    }

    compositeSubscription.add(repository.addItemsToBasket(
        new AddItemToBasketRequestEntity().code(code).quantity(quantity), sessionID)
        .flatMap(entity -> {
          if (entity != null && entity.success() != null && entity.success()) {
            return Observable.just(
                new BasketItemResponse().success(entity.success()).message(entity.message()));
          }
          return Observable.error(new ResponseFailException(

              entity != null ? entity.message() != null ? entity.message() : "" : ""

          ));
        })
        .subscribe(subscriber));
  }

  @Override public void getOtherProducts(
      DefaultSubscriber<List<ProductOther>> subscriber, int type, int id, int currentProductId
  ) {

    compositeSubscription.add(subscriber);

    if (catalogEntityDataMapper == null) {
      catalogEntityDataMapper = new CatalogEntityDataMapper();
    }

    repository.otherProducts(
        new OtherProductsRequestEntity().currentProduct(currentProductId).id(id).type(type))
        .map(catalogEntityDataMapper::transform)
        .subscribe(subscriber);
  }

  @Override public void filterCategoryProducts(
      DefaultSubscriber<FilteredProduct> subscriber, String categoryid, String parentId, int page
  ) {

    filterCategoryProducts(subscriber, categoryid, parentId, null, SortEnums.SOLD_COUNT_DESC, page);
  }

  @Override public void filterCategoryProducts(
      DefaultSubscriber<FilteredProduct> subscriber, String categoryid, String parentId,
      Map<Integer, List<FilterItem>> filters, SortEnums sortEnum, int page
  ) {

    compositeSubscription.add(subscriber);

    if (catalogEntityDataMapper == null) {
      catalogEntityDataMapper = new CatalogEntityDataMapper();
    }

    FilterCategoryProductsRequestEntity content =
        new FilterCategoryProductsRequestEntity().page(page)
            .sortfield(sortEnum.getSortField())
            .sortorder(sortEnum.getSortOrder())
            .size(stackSize)
            .categoryid(categoryid)
            .parentId(Integer.valueOf(parentId))
            .propVal("");

    if (filters != null) {

      for (Map.Entry<Integer, List<FilterItem>> entry : filters.entrySet()) {

        switch (entry.getKey()) {
          case FilterMap.FILTER_TYPE_BRAND: {
            StringBuilder sb = new StringBuilder();

            for (FilterItem fi : entry.getValue()) {
              if (fi.selected()) {
                sb.append(fi.id());
                sb.append(",");
              }
            }
            String string = sb.toString();

            if (!TextUtils.isEmpty(string)) {
              string = string.substring(0, string.length() - 1);
              content.brandIds(string);
            }
          }

          break;

          case FilterMap.FILTER_TYPE_CATEGORY: {
            StringBuilder sb = new StringBuilder();

            for (FilterItem fi : entry.getValue()) {
              if (fi.selected()) {
                sb.append(fi.id());
                sb.append(",");
              }
            }
            String string = sb.toString();

            if (!TextUtils.isEmpty(string)) {
              string = string.substring(0, string.length() - 1);
              content.categoryid(string);
            }
          }
          break;
          case FilterMap.FILTER_TYPE_MEDIA_TYPE: {
            StringBuilder sb = new StringBuilder();

            for (FilterItem fi : entry.getValue()) {
              if (fi.selected()) {
                sb.append(fi.name());
                sb.append(",");
              }
            }
            String string = sb.toString();

            if (!TextUtils.isEmpty(string)) {
              string = string.substring(0, string.length() - 1);
              content.mediatypes(string);
            }
          }
          break;
          case FilterMap.FILTER_TYPE_PRICE: {
            String price = null;
            int minPrice;
            int maxPrice;

            for (FilterItem fi : entry.getValue()) {
              if (fi.selected()) {
                price = fi.name();
              }
            }

            if (!TextUtils.isEmpty(price)) {
              price = price.replaceAll(" {2,}", "")
                  .replaceAll(" {1,}", "")
                  .replaceAll(" ", "")
                  .replaceAll("TL", "");

              String[] prices = price.split("-");

              if (TextUtils.isEmpty(prices[0])) {
                content.minPrice(0);
              } else {
                double minD = Double.parseDouble(prices[0].replace(",", "."));
                minPrice = (int) Math.round(minD);
                content.minPrice(minPrice);
              }
              double maxD = Double.parseDouble(prices[1].replace(",", "."));
              maxPrice = (int) Math.round(maxD);
              content.maxPrice(maxPrice);
            }
          }
          break;
        }
      }
    }

    FilterCategoryProductsContentRequestEntity requestEntity =
        new FilterCategoryProductsContentRequestEntity();

    requestEntity.content(content);
    requestEntity.reponseType = 2;

    repository.filterCategoryProducts(requestEntity)
        .map(catalogEntityDataMapper::transform)
        .subscribe(subscriber);
  }

  @Override public void productReviewsAdd(
      DefaultSubscriber<ReviewAddResult> subscriber, int productId, String title, String review
  ) {
    String sessionID = getSessionID();

    if (TextUtils.isEmpty(sessionID)) {
      subscriber.onNext(null);
      subscriber.onCompleted();
      return;
    }

    compositeSubscription.add(repository.productReviewsAdd(
        new ReviewRequestEntity().productId(productId).title(title).message(review)).map(entity -> {

      if (entity != null && entity.success() != null) {
        return new ReviewAddResult().success(entity.success()).message(entity.message());
      }

      return new ReviewAddResult().success(false);
    }).subscribe(subscriber));
  }

  String getSessionID() {

    return domainContext != null ? domainContext.getLoggedInUser() != null
        ? domainContext.getLoggedInUser().sessionObject() : null : null;
  }
}
