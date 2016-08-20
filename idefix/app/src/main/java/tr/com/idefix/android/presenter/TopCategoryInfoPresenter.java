package tr.com.idefix.android.presenter;

import android.os.Bundle;
import com.google.android.gms.tagmanager.DataLayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tr.com.idefix.android.mapper.CatalogDataMapper;
import tr.com.idefix.android.model.TopCategoryInfoItemModel;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.android.view.TopCategoryInfoFragmentView;
import tr.com.idefix.domain.BestSeller;
import tr.com.idefix.domain.FilterItem;
import tr.com.idefix.domain.FilteredProduct;
import tr.com.idefix.domain.TheNewest;
import tr.com.idefix.domain.enums.SortEnums;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICatalogInterActor;

import static tr.com.idefix.android.contants.Keys.ALL;
import static tr.com.idefix.android.contants.Keys.CATEGORY_LEVEL;
import static tr.com.idefix.android.contants.Keys.CATEGORY_PATH;
import static tr.com.idefix.android.contants.Keys.PARENT_CATEGORY_ID;

/**
 * Created by utkan on 30/09/15.
 */
public class TopCategoryInfoPresenter extends BasePresenter implements ITopCategoryInfoPresenter {

  //<editor-fold desc="Fields">
  private final ICatalogInterActor catalogInterActor;
  private final CatalogDataMapper catalogDataMapper;
  Map<Integer, List<FilterItem>> filterMap;
  SortEnums sortEnum = SortEnums.SOLD_COUNT_DESC;
  private int parent_category_id;
  private TopCategoryInfoFragmentView view;
  private List<TopCategoryInfoItemModel> bestSellerItems;
  private List<TopCategoryInfoItemModel> theNewestItems;
  private int category_level;
  private String category_path;
  private FilteredProduct filteredProduct;
  private List<TopCategoryInfoItemModel> filteredProducts;
  private int currentPage;

  //</editor-fold>

  public TopCategoryInfoPresenter(
      ICatalogInterActor catalogInterActor, CatalogDataMapper catalogDataMapper
  ) {
    this.catalogInterActor = catalogInterActor;
    this.catalogDataMapper = catalogDataMapper;
    currentPage = 1;
  }

  @Override public void processArguments(Bundle arguments) {

    if (arguments != null) {

      boolean isAll = arguments.getBoolean(ALL, false);

      if (arguments.containsKey(PARENT_CATEGORY_ID)) {

        parent_category_id = arguments.getInt(PARENT_CATEGORY_ID);
        category_level = arguments.getInt(CATEGORY_LEVEL);
        category_path = arguments.getString(CATEGORY_PATH);

        if (category_level == 0 && !isAll) {

          catalogInterActor.getBestSellers(parent_category_id, 6,
              new DefaultSubscriber<List<BestSeller>>() {
                @Override public void onStart() {
                  super.onStart();
                  view.showProgress();
                }

                @Override public void onNext(List<BestSeller> bestSellers) {
                  super.onNext(bestSellers);

                  if (bestSellers != null && bestSellers.size() > 0) {
                    bestSellerItems = catalogDataMapper.transform(bestSellers);

                    List<Object> asasas = DataLayer.listOf(2, 2);

                    for (int i = 0; i < bestSellers.size(); i++) {
                      BestSeller bestSeller = bestSellers.get(i);
                      Map<String, Object> sasas = DataLayer.mapOf(

                          "name", bestSeller.name(), // Ürün Adı
                          "id", bestSeller.id(), // Ürün ID’si
                          "price", bestSeller.price(), // Ürün Fiyatı
                          "brand", bestSeller.brandName(),
                          // Ürün Markası (kitaplarda “Diğer” gönderilmeli)
                          "category", "Kitap/Romantik",
                          // Ürün Kategorisi. Burada gönderirken Ana Kategori/En Alt Kategori
                          // şeklinde
                          "list", "Main Category",
                          // Ürünün Görüntülendiği Sayfa Tipi (Search Results, Main Category,
                          // SubCategory, Homepage)
                          "position", i + 1);
                    }
                  } else {
                    getMostNewests();
                  }
                }

                @Override public void onCompleted() {
                  super.onCompleted();
                  view.addBestSellerSection(bestSellerItems);
                  getMostNewests();
                  view.hideProgress();
                }

                @Override public void onError(Throwable e) {
                  super.onError(e);
                  getMostNewests();
                  view.hideProgress();
                }
              });
        } else {
          getFilteredProduct(sortEnum);
        }
      }
    }
  }

  @Override public void resume() {
    //        bus.register(this);
  }

  @Override public void pause() {
    //        bus.unregister(this);
  }

  @Override public void destroy() {
    catalogInterActor.unSubscribe();
  }

  @Override public void setView(IView iView) {
    this.view = (TopCategoryInfoFragmentView) iView;
  }

  void getMostNewests() {

    catalogInterActor.getMostNewests(parent_category_id, 6,
        new DefaultSubscriber<List<TheNewest>>() {
          @Override public void onStart() {
            super.onStart();
            view.showProgress();
          }

          @Override public void onNext(List<TheNewest> theNewests) {
            super.onNext(theNewests);
            if (theNewests != null && theNewests.size() > 0) {
              theNewestItems = catalogDataMapper.transform(theNewests);
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
            view.addTheNewestsSection(theNewestItems);
            view.hideProgress();
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            view.hideProgress();
          }
        });
  }

  @Override public Map<Integer, List<FilterItem>> getFilter() {
    if (filteredProduct != null) {
      return filteredProduct.filters();
    } else {
      return null;
    }
  }

  @Override public void updateFilterItems(FilterItem filterItem) {

    boolean quit = false;

    for (Map.Entry<Integer, List<FilterItem>> entry : filterMap.entrySet()) {

      if (entry.getKey() == filterItem.filterType()) {

        for (FilterItem fi : entry.getValue()) {
          if (filterItem.filterType() == fi.filterType()) {
            if (filterItem.id() == fi.id()) {
              fi.selected(filterItem.selected());
              quit = true;
              break;
            }
          }
        }
        if (quit) break;
      }
    }

    List<FilterItem> fiList = new ArrayList<>();

    for (Map.Entry<Integer, List<FilterItem>> entry : filterMap.entrySet()) {

      for (FilterItem fi : entry.getValue()) {
        if (fi.selected()) {
          fiList.add(fi);
        }
      }
    }

    view.updateFilterView(fiList);

    getFilteredProduct(sortEnum);
  }

  @Override public void updateFilterItems(List<FilterItem> filterItems) {
    for (FilterItem filterItem : filterItems) {

      filterMap = filteredProduct.filters();

      for (Map.Entry<Integer, List<FilterItem>> entry : filterMap.entrySet()) {

        if (filterItem.filterType() == entry.getKey()) {

          for (FilterItem fi : entry.getValue()) {
            if (filterItem.filterType() == fi.filterType()) {
              if (filterItem.id() == fi.id()) {
                fi.selected(filterItem.selected());
              }
            }
          }
        }
      }
    }

    getFilteredProduct(sortEnum);
  }

  @Override public void getMore(int page) {
    catalogInterActor.filterCategoryProducts(new DefaultSubscriber<FilteredProduct>() {

      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onNext(FilteredProduct productFiltered) {
        super.onNext(filteredProduct);

        filteredProduct = productFiltered;

        if (filterMap != null) {
          filteredProduct.filters(filterMap);
        }

        if (filteredProduct != null && filteredProduct.products().size() > 0) {

          filteredProducts = catalogDataMapper.transform(filteredProduct.products());

          view.onMoreRecordsRetrieved(filteredProducts);
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
    }, category_path, String.valueOf(parent_category_id), filterMap, sortEnum, page);
  }

  void getFilteredProduct(SortEnums sort_enum) {

    catalogInterActor.filterCategoryProducts(new DefaultSubscriber<FilteredProduct>() {

      @Override public void onStart() {
        super.onStart();
        view.showProgress();
      }

      @Override public void onNext(FilteredProduct productFiltered) {
        super.onNext(filteredProduct);

        filteredProduct = productFiltered;

        if (filterMap != null) {
          filteredProduct.filters(filterMap);
        }

        if (filteredProduct != null && filteredProduct.products().size() > 0) {

          filteredProducts = catalogDataMapper.transform(filteredProduct.products());

          view.bindFilteredProducts(filteredProducts);
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
    }, category_path, String.valueOf(parent_category_id), filterMap, sort_enum, 1);
  }

  @Override public int getHitCount() {
    if (filteredProduct != null) {
      return filteredProduct.hitCount();
    }
    return 0;
  }

  @Override public SortEnums getSortEnum() {
    return sortEnum;
  }

  @Override public void setSortEnum(SortEnums selectedSortEnum) {
    this.sortEnum = selectedSortEnum;

    getFilteredProduct(sortEnum);
  }
}
