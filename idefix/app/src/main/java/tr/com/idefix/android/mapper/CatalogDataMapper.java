package tr.com.idefix.android.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.model.CategoryModel;
import tr.com.idefix.android.model.TopCategoryInfoItemModel;
import tr.com.idefix.domain.Category;
import tr.com.idefix.domain.DRItem;

/**
 * Created by utkan on 01/09/15.
 */
@PerActivity public class CatalogDataMapper {

  @Inject public CatalogDataMapper() {
  }

  public void transform(
      List<Category> categories, Action1<List<CategoryModel>> onNext
  ) {

    if (categories != null) {

      Observable.just(categories)
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread())
          .map(categories1 -> {

            List<CategoryModel> categoryModels = new ArrayList<>(categories1.size());

            for (Category category : categories1) {

              categoryModels.add(new CategoryModel().name(category.name())
                  .id(category.id())
                  .level(category.level())
                  .bottom(category.bottom())
                  .parentPath(category.parentPath())
                  .seo(category.seo()));
            }
            return categoryModels;
          })
          .subscribe(onNext);
    }
  }

  public <T extends DRItem> List<TopCategoryInfoItemModel> transform(List<T> drItems) {

    if (drItems != null && drItems.size() > 0) {

      List<TopCategoryInfoItemModel> topCategoryInfoItems = new ArrayList<>(drItems.size());

      for (DRItem drItem : drItems) {

        TopCategoryInfoItemModel item = new TopCategoryInfoItemModel();

        topCategoryInfoItems.add(item.name(drItem.name())
            .id(drItem.id())
            .imageUrl(drItem.imageUrl())
            .price(drItem.price())
            .oldPrice(drItem.oldPrice())
            .sku(drItem.sku()));
      }
      return topCategoryInfoItems;
    }
    return null;
  }
}
