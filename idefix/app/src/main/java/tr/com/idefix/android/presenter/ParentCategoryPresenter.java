package tr.com.idefix.android.presenter;

import android.os.Bundle;
import timber.log.Timber;
import tr.com.idefix.android.mapper.CatalogDataMapper;
import tr.com.idefix.android.view.IView;
import tr.com.idefix.android.view.ParentCategoryFragmentView;
import tr.com.idefix.domain.CategoryTree;
import tr.com.idefix.domain.interactor.DefaultSubscriber;
import tr.com.idefix.domain.interactor.ICatalogInterActor;

import static tr.com.idefix.android.contants.Keys.PARENT_CATEGORY_ID;

/**
 * Created by utkan on 16/09/15.
 */
public class ParentCategoryPresenter implements IParentCategoryPresenter {

  private final ICatalogInterActor catalogInterActor;
  private final CatalogDataMapper catalogDataMapper;
  ParentCategoryFragmentView view;
  private int parent_category_id;

  public ParentCategoryPresenter(
      ICatalogInterActor catalogInterActor, CatalogDataMapper catalogDataMapper
  ) {
    this.catalogInterActor = catalogInterActor;
    this.catalogDataMapper = catalogDataMapper;
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void destroy() {

  }

  @Override public void setView(IView iView) {
    view = (ParentCategoryFragmentView) iView;
  }

  @Override public void processArguments(Bundle arguments) {

    if (arguments != null) {
      if (arguments.containsKey(PARENT_CATEGORY_ID)) {
        parent_category_id = arguments.getInt(PARENT_CATEGORY_ID);
        catalogInterActor.getCatalogTree(parent_category_id, new DefaultSubscriber<CategoryTree>() {
          @Override public void onStart() {
            super.onStart();
            view.showProgress();
          }

          @Override public void onNext(CategoryTree categoryTree) {
            super.onNext(categoryTree);
            catalogDataMapper.transform(categoryTree.categoryTree(), list -> {
              Timber.i("size: %s", list.size());
              view.renderCategories(list);
            });
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
    }
  }
}
