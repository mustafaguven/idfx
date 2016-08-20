package tr.com.idefix.android.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.tagmanager.DataLayer;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;
import java.util.List;
import timber.log.Timber;
import tr.com.idefix.android.contants.Keys;
import tr.com.idefix.android.mapper.CatalogDataMapper;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.android.view.ProductDetailActivityView;
import tr.com.idefix.domain.Alarm;
import tr.com.idefix.domain.BasketCount;
import tr.com.idefix.domain.BasketItemResponse;
import tr.com.idefix.domain.DRItem;
import tr.com.idefix.domain.GroupedProductAttribute;
import tr.com.idefix.domain.Product;
import tr.com.idefix.domain.ProductOther;
import tr.com.idefix.domain.Review;
import tr.com.idefix.domain.Wish;
import tr.com.idefix.domain.events.UserInOutEvent;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICatalogInterActor;
import tr.com.idefix.domain.interactor.ICustomerInterActor;

/**
 * Created by utkan on 14/10/15.
 */
public class ProductDetailPresenter extends BasePresenter implements IProductDetailPresenter {

  //<editor-fold desc="Fields">
  private final CatalogDataMapper catalogDataMapper;
  private final ICatalogInterActor catalogInterActor;
  private final ICustomerInterActor customerInterActor;
  ProductDetailActivityView view;
  private String sku;
  private Product product;
  private String title;
  private List<ProductOther> productOthers;
  private GroupedProductAttribute groupedProductAttribute;
  private boolean ebook;
  private int eBookIndex = -1;
  private DataLayer datalayer;
  //</editor-fold>

  public ProductDetailPresenter(
      ICatalogInterActor catalogInterActor, ICustomerInterActor customerInterActor,
      CatalogDataMapper catalogDataMapper
  ) {

    super();

    this.catalogInterActor = catalogInterActor;
    this.customerInterActor = customerInterActor;
    this.catalogDataMapper = catalogDataMapper;
  }

  @Override public void resume() {
    bus.register(this);
  }

  @Override public void pause() {
    bus.unregister(this);
  }

  @Override public void destroy() {

  }

  @Override public void setView(IView iView) {
    view = (ProductDetailActivityView) iView;
  }

  @Override public void processIntent(Intent intent) {

    //        if (domainContext.getSelectedTopCategoryID() == 5699) {
    ////            E-Kitap
    //        }
    Timber.i(domainContext.getSelectedTopCategoryID() + "");
    Timber.i(domainContext.getSelectedTopCategoryName());
    if (intent != null) {
      Bundle extras = intent.getExtras();

      if (extras != null && extras.containsKey(Keys.TITLE)) {
        title = extras.getString(Keys.TITLE);
        view.setTitle(title);
      }
      //<editor-fold desc="ProductDetail">
      if (extras != null && extras.containsKey(Keys.SKU)) {
        sku = extras.getString(Keys.SKU);

        if (!TextUtils.isEmpty(sku)) {

          Timber.i("sku: %s", sku);

          catalogInterActor.getProductDetail(sku, new DefaultSubscriber<Product>() {

            @Override public void onStart() {
              super.onStart();
              view.showProgress();
            }

            @Override public void onNext(Product product) {

              super.onNext(product);

              ProductDetailPresenter.this.product = product;

              if (product != null) {

                if (domainContext.getCachedWish() != null &&
                    domainContext.getCachedWish().items() != null &&
                    domainContext.getCachedWish().items().size() > 0) {
                  for (DRItem drItem : domainContext.getCachedWish().items()) {
                    if (drItem.sku().equals(sku)) {
                      view.setItemFavorite();
                      break;
                    }
                  }
                }

                if (domainContext.getCachedAlarm() != null &&
                    domainContext.getCachedAlarm().items() != null &&
                    domainContext.getCachedAlarm().items().size() > 0) {
                  for (DRItem drItem : domainContext.getCachedAlarm().items()) {
                    if (drItem.sku().equals(sku)) {
                      view.setItemWarned();
                      break;
                    }
                  }
                }

                if (product.pictures() != null && product.pictures().size() > 0) {
                  view.setImage(product.pictures().get(0).fullSizeImageUrl());
                }
                view.setName(product.name());

                if (product.productAttributes() != null && product.productAttributes().size() > 0) {
                  view.bindProductAttributes(product.productAttributes());
                }

                if (!TextUtils.isEmpty(product.oldPriceString())) {
                  view.bindOldPrice(product.oldPriceString());
                }
                view.bindPrice(product.priceString());

                if (product.groupedProductAttributes() != null
                    && product.groupedProductAttributes().size() > 0) {
                  view.bindGroupedProductAttributes(product.groupedProductAttributes());
                }

                if (product.productPersons() != null && product.productPersons().size() > 0) {
                  view.bindProductPersons(product.productPersons());
                }

                if (!TextUtils.isEmpty(product.brand())) {
                  view.bindBrand(product.brand());
                }

                if (!TextUtils.isEmpty(product.shortDescription()) || !TextUtils.isEmpty(
                    product.fullDescription())) {
                  view.bindShortDescription(product.shortDescription());
                  view.bindFullDescription(product.fullDescription());
                }

                view.setEnableSepeteEkle(true);

                //<editor-fold desc="others">
                if (product.productPersons() != null && product.productPersons().size() > 0) {
                  //                                    for (ProductPerson productPerson :
                  // product.productPersons()) {
                  //                                        if (productPerson.productId() != null
                  // && productPerson.groupName().equals("Yazar")) {

                  catalogInterActor.getOtherProducts(new DefaultSubscriber<List<ProductOther>>() {

                    @Override public void onStart() {
                      super.onStart();
                      view.showProgress();
                    }

                    @Override public void onNext(List<ProductOther> productOthers) {
                      super.onNext(productOthers);

                      if (productOthers != null && productOthers.size() > 0) {
                        ProductDetailPresenter.this.productOthers = productOthers;
                        view.bindOtherProducts(productOthers);
                      }
                    }

                    @Override public void onCompleted() {
                      super.onCompleted();
                      view.hideProgress();
                    }

                    @Override public void onError(Throwable e) {
                      super.onError(e);
                      view.hideProgress();
                    }
                  }, 0, product.productPersons().get(0).personId(), product.id());
                  //                                            break;
                  //                                        }
                  //                                    }
                } else if (product.brandId() != null) {
                  catalogInterActor.getOtherProducts(new DefaultSubscriber<List<ProductOther>>() {

                    @Override public void onStart() {
                      super.onStart();
                      view.showProgress();
                    }

                    @Override public void onNext(List<ProductOther> productOthers) {
                      super.onNext(productOthers);

                      if (productOthers != null && productOthers.size() > 0) {
                        ProductDetailPresenter.this.productOthers = productOthers;
                        view.bindOtherProducts(productOthers);
                      }
                    }

                    @Override public void onCompleted() {
                      super.onCompleted();
                      view.hideProgress();
                    }

                    @Override public void onError(Throwable e) {
                      super.onError(e);
                      view.hideProgress();
                    }
                  }, 1, product.brandId(), product.id());
                }

                // Measure a view of product details.

                datalayer.push(
                    "ecommerce", DataLayer.mapOf("detail", DataLayer.mapOf(
                        "products", DataLayer.listOf(
                            DataLayer.mapOf("name", product.name(), "id", product.id(), "price",
                                product.priceString(), "brand", product.brand(), "category",
                                "undefined")))));
                //</editor-fold>

                //Urun Detay icerik bazli Custom Dimension'lar

                ProductDetailPresenter.this.datalayer.push(
                    DataLayer.mapOf("CD_PageType", "product", "CD_ProductID", product.id(),

                        "CD_ProductID2", product.sku(),

                        "CD_MobileAppDeeplink", "undefined",

                        "CD_MobileWebURL", "undefined",

                        "CD_ProductCategory", "undefined",

                        "CD_ProductSubCategory", "undefined",

                        "CD_ProductBrand", product.brand(),

                        "CD_ProductName", product.name(),

                        "CD_ProductCategoryID", "undefined",

                        "CD_ProductRatingScore", "0%",     //urun rating puani

                        "CD_ProductReviewCount", "0",      //urun kullanici yorum adedi

                        "CD_ProductStock", "N",            //urun stokta mi degil mi Y/N

                        "CD_ProductFreeShipping", "Y",     //ucretsiz kargo var mi Y/N
                        "CD_ProductValue", product.priceString(),    //urunun satis fiyati
                        "CD_ProductActualValue", product.oldPriceString())
                    //urune ait indirimsiz fiyat

                );
              }
            }

            @Override public void onError(Throwable e) {
              super.onError(e);
              view.hideProgress();
              Timber.e(e, "<--");
            }

            @Override public void onCompleted() {
              super.onCompleted();
              view.hideProgress();
            }
          });
        }
      }
      //</editor-fold>

      if (extras != null && extras.containsKey(Keys.PRODUCT_ID)) {
        int productID = extras.getInt(Keys.PRODUCT_ID);

        catalogInterActor.getProductReviews(new DefaultSubscriber<List<Review>>() {

          List<Review> r;

          @Override public void onStart() {
            super.onStart();
            view.showProgress();
          }

          @Override public void onNext(List<Review> reviews) {
            super.onNext(reviews);

            r = reviews;

            if (reviews != null && reviews.size() > 0) {
              view.bindReviews(reviews);
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();

            if (r == null || r.size() == 0) {
              view.noReview();
            }
            view.hideProgress();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            view.hideProgress();
          }
        }, productID, 3);
      }
    }
  }

  @Override public GroupedProductAttribute getGroupedProductAttribute(int i) {
    GroupedProductAttribute groupedProductAttribute = product.groupedProductAttributes().get(i);
    ebook = groupedProductAttribute.attributeName().equals("e-Kitap");
    return groupedProductAttribute;
  }

  @Override public boolean selectEBook() {
    if (domainContext.getSelectedTopCategoryID() != null
        && domainContext.getSelectedTopCategoryID() == 5699) {
      //            E-Kitap

      if (product.groupedProductAttributes() != null
          && product.groupedProductAttributes().size() > 0) {
        for (int i = 0; i < product.groupedProductAttributes().size(); i++) {

          GroupedProductAttribute groupedProductAttribute =
              product.groupedProductAttributes().get(i);

          if (groupedProductAttribute.attributeName().equals("e-Kitap")) {

            eBookIndex = i;
            return true;
          }
        }
      }
    }
    return false;
  }

  @Override public int getEBookIndex() {
    return eBookIndex;
  }

  @Override public void favorite(boolean checked) {

    if (domainContext.getLoggedInUser() != null) {

      if (checked) {

        customerInterActor.addItemToWishList(new DefaultSubscriber<Wish>() {

          @Override public void onStart() {
            super.onStart();
            view.showProgress();
          }

          @Override public void onNext(Wish wish) {
            super.onNext(wish);
          }

          @Override public void onCompleted() {
            super.onCompleted();
            view.setEnabledFavoriteView(true);
            view.hideProgress();
            view.itemAddedToWishList();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            view.hideProgress();
            view.itemAddedToWishList();
          }
        }, sku);
      } else {
        customerInterActor.removeItemFromWishList(new DefaultSubscriber<Boolean>() {

          @Override public void onStart() {
            super.onStart();
            view.showProgress();
          }

          @Override public void onNext(Boolean b) {
            super.onNext(b);
          }

          @Override public void onCompleted() {
            super.onCompleted();
            view.setEnabledFavoriteView(true);
            view.hideProgress();
            view.itemRemovedFromWishList();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            view.hideProgress();
            view.itemRemovedFromWishList();
          }
        }, sku);
      }
    }
  }

  @Override public boolean isloggedIn() {
    return domainContext.getLoggedInUser() != null;
  }

  @Override public void warn(boolean checked) {
    warn(checked, null, null);
  }

  @Override public void warn(boolean checked, String days, String price) {
    if (checked) {
      customerInterActor.addItemToAlarmList(new DefaultSubscriber<Alarm>() {

        @Override public void onStart() {
          super.onStart();
          view.showProgress();
        }

        @Override public void onNext(Alarm alarm) {
          super.onNext(alarm);
        }

        @Override public void onCompleted() {
          super.onCompleted();
          view.setEnabledWarnView(true);
          view.hideProgress();
          view.itemAddedToAlarmList();
        }

        @Override public void onError(Throwable e) {
          super.onError(e);
          view.setItemWarned(!checked);
          view.hideProgress();
          view.itemAddedToAlarmList();
        }
      }, sku, days, price);
    } else {
      customerInterActor.removeItemFromAlarmList(new DefaultSubscriber<Boolean>() {

        @Override public void onStart() {
          super.onStart();
          view.showProgress();
        }

        @Override public void onNext(Boolean b) {
          super.onNext(b);
        }

        @Override public void onCompleted() {
          super.onCompleted();
          view.setEnabledWarnView(true);
          view.hideProgress();
          view.itemRemovedFromAlarmList();
        }

        @Override public void onError(Throwable e) {
          super.onError(e);
          view.setItemWarned(!checked);
          view.hideProgress();
          view.itemRemovedFromAlarmList();
        }
      }, sku);
    }
  }

  @Override public String getShareText() {
    if (product != null) {
      return product.webLink();
    }
    return null;
  }

  @Override public int getProductId() {

    if (product != null) {
      return product.id();
    }
    return 0;
  }

  @Override public String getProductName() {
    if (product != null) {
      return product.name();
    }
    return null;
  }

  @Override
  public void setSelectedGroupedProductAttribute(GroupedProductAttribute groupedProductAttribute) {
    this.groupedProductAttribute = groupedProductAttribute;

    ebook = false;
    if (groupedProductAttribute.attributeName().equals("e-Kitap")) {
      ebook = true;
    }
  }

  @Override public void addItemToBasket(int quantity) {
    catalogInterActor.addItemsToBasket(new DefaultSubscriber<BasketItemResponse>() {

      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onNext(BasketItemResponse r) {
        super.onNext(r);

        if (r != null && r.success()) {
          //resume();
        }

        // Measure adding a product to a shopping cart by using an "add"
        // actionFieldObject and a list of productFieldObjects.

        ProductDetailPresenter.this.datalayer.pushEvent(
            "addToCart", DataLayer.mapOf(
                "ecommerce", DataLayer.mapOf("currencyCode", "TRY", "add",
                    DataLayer.mapOf(// 'add' actionFieldObject measures.
                        "products", DataLayer.listOf(
                            DataLayer.mapOf("name", product.name(), "id", product.id(), "price",
                                product.priceString(), "brand", product.brand(), "category",
                                "undefined", "quantity", quantity))))));
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.enableBasket();
        view.hideProgress();
        view.itemAddedToBasket();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.enableBasket();
        view.hideProgress();
        view.showError(e.getMessage() == null ? "Bilinmeyen Hata" : e.getMessage());
      }
    }, groupedProductAttribute == null ? sku : groupedProductAttribute.sku(), quantity);
  }

  @Override public String getSku() {
    return sku;
  }

  @Override public String getTitle() {
    return title;
  }

  @Override public boolean isInfavList(String sku) {

    if (domainContext.getCachedWish() != null && !TextUtils.isEmpty(sku)) {
      Wish list = domainContext.getCachedWish();

      if (list.items().size() > 0) {
        for (DRItem it : list.items()) {
          if (!TextUtils.isEmpty(it.sku()) && it.sku().equals(sku)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  @Override public boolean isInAlarmList(String sku) {

    if (domainContext.getCachedAlarm() != null && !TextUtils.isEmpty(sku)) {
      Alarm list = domainContext.getCachedAlarm();

      if (list.items().size() > 0) {
        for (DRItem it : list.items()) {
          if (!TextUtils.isEmpty(it.sku()) && it.sku().equals(sku)) {
            return true;
          }
        }
      }
    }

    return false;
  }

  @Override public void favorite(boolean isChecked, String sku) {
    if (isChecked) {
      customerInterActor.addItemToWishList(new DefaultSubscriber<Wish>() {
        @Override public void onStart() {
          super.onStart();
          view.showProgress();
        }

        @Override public void onNext(Wish wish) {
          super.onNext(wish);
        }

        @Override public void onCompleted() {
          super.onCompleted();
          view.hideProgress();
        }

        @Override public void onError(Throwable e) {
          super.onError(e);
          view.hideProgress();
        }
      }, sku);
    } else {
      customerInterActor.removeItemFromWishList(new DefaultSubscriber<Boolean>() {

        @Override public void onStart() {
          super.onStart();
          view.showProgress();
        }

        @Override public void onNext(Boolean aBoolean) {
          super.onNext(aBoolean);
        }

        @Override public void onCompleted() {
          super.onCompleted();
          view.hideProgress();
        }

        @Override public void onError(Throwable e) {
          super.onError(e);
          view.hideProgress();
        }
      }, sku);
    }
  }

  @Override public void addItemToAlarmList(String days, String price, String sku) {
    customerInterActor.addItemToAlarmList(new DefaultSubscriber<Alarm>() {
      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onNext(Alarm alarm) {
        super.onNext(alarm);

        Timber.i("alarms: %s", alarm != null ? alarm.items().size() : "-");
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.hideProgress();
        view.itemAddedToAlarmList();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.hideProgress();
        view.itemAddedToAlarmList();
      }
    }, sku, days, price);
  }

  @Override public void removeItemFromAlarmList(String sku) {
    customerInterActor.removeItemFromAlarmList(new DefaultSubscriber<Boolean>() {
      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onNext(Boolean aBoolean) {
        super.onNext(aBoolean);
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.hideProgress();
        view.itemRemovedFromAlarmList();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.hideProgress();
        view.itemRemovedFromAlarmList();
      }
    }, sku);
  }

  @Override public void getBasketCount() {
    customerInterActor.getBasketCount(new DefaultSubscriber<BasketCount>() {

      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onNext(BasketCount basketCount) {
        super.onNext(basketCount);

        if (basketCount != null) {
          view.setCartItemCount(basketCount.cartItemCount());
        }
      }

      @Override public void onCompleted() {
        super.onCompleted();
        view.hideProgress();
      }

      @Override public void onError(Throwable e) {
        super.onError(e);
        view.hideProgress();
      }
    });
  }

  @Produce public UserInOutEvent produceUserLogedinEvent() {
    return new UserInOutEvent(domainContext.getLoggedInUser());
  }

  @Subscribe public void userLogedin(UserInOutEvent userInOutEvent) {

    if (userInOutEvent.getLoggedInUser() != null) {

      customerInterActor.getBasketCount(new DefaultSubscriber<BasketCount>() {

        @Override public void onStart() {
          super.onStart();
          view.showProgress();
        }

        @Override public void onNext(BasketCount basketCount) {
          super.onNext(basketCount);

          if (basketCount != null) {
            view.setCartItemCount(basketCount.cartItemCount());
          }
        }

        @Override public void onCompleted() {
          super.onCompleted();
          view.hideProgress();
        }

        @Override public void onError(Throwable e) {
          super.onError(e);
          view.hideProgress();
        }
      });
    }

    if (productOthers != null && productOthers.size() > 0) {

      view.bindOtherProducts(productOthers);
    }
  }

  @Override public boolean isEBook() {
    return ebook;
  }

  @Override public void setDataLayer(DataLayer dataLayer) {
    this.datalayer = dataLayer;
  }
}
