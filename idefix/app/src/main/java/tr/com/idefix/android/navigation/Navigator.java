package tr.com.idefix.android.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import tr.com.idefix.android.contants.Keys;
import tr.com.idefix.android.contants.RequestCodes;
import tr.com.idefix.android.model.MainCategoryModel;
import tr.com.idefix.android.view.activities.BaseActivity;
import tr.com.idefix.android.view.activities.BasketActivity;
import tr.com.idefix.android.view.activities.CategoriesActivity;
import tr.com.idefix.android.view.activities.FilterActivity;
import tr.com.idefix.android.view.activities.ForgetPasswordActivity;
import tr.com.idefix.android.view.activities.LoginActivity;
import tr.com.idefix.android.view.activities.MainActivity;
import tr.com.idefix.android.view.activities.ProductDetailActivity;
import tr.com.idefix.android.view.activities.ReviewActivity;
import tr.com.idefix.android.view.activities.ReviewsActivity;
import tr.com.idefix.android.view.activities.SearchActivity;
import tr.com.idefix.android.view.activities.StoresActivity;
import tr.com.idefix.android.view.activities.UserProfileActivity;
import tr.com.idefix.domain.FilterItem;

import static tr.com.idefix.android.contants.Keys.ALL;
import static tr.com.idefix.android.contants.Keys.MAIN_CATEGORY_MODEL;

/**
 * Class used to navigate through the application.
 */
@Singleton public class Navigator {

  @Inject public void Navigator() {
    //empty
  }

  public void navigateToMainActivity(Context context) {
    navigateToMainActivity(context);
  }

  public void navigateToMainActivity(Context context, Bundle bundle) {
    if (context != null) {
      Intent intent = new Intent(context, MainActivity.class);
      if (bundle != null) {
        intent.putExtras(bundle);
      }
      context.startActivity(intent);
    }
  }

  public void navigateToMainActivityWithNewTask(Context context) {
    if (context != null) {
      Intent intent = new Intent(context, MainActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
      context.startActivity(intent);
    }
  }

  public void navigateToStoresActivity(Context context) {
    if (context != null) {
      Intent intent = new Intent(context, StoresActivity.class);
      context.startActivity(intent);
    }
  }

  public void navigateToLoginActivity(Context context) {
    if (context != null) {
      Intent intent = new Intent(context, LoginActivity.class);
      context.startActivity(intent);
    }
  }

  public void navigateToCategoriesActivity(Context context, MainCategoryModel category) {
    navigateToCategoriesActivity(context, category, false);
  }

  public void navigateToCategoriesActivity(
      Context context, MainCategoryModel category, boolean all
  ) {
    if (context != null) {
      Intent intent = new Intent(context, CategoriesActivity.class);
      intent.putExtra(MAIN_CATEGORY_MODEL, category);
      intent.putExtra(ALL, all);

      context.startActivity(intent);
    }
  }

  public void navigateToForgetPasswordActivity(Context context) {
    if (context != null) {
      context.startActivity(new Intent(context, ForgetPasswordActivity.class));
    }
  }

  public void navigateToUserProfileActivity(Context context) {
    if (context != null) {
      context.startActivity(new Intent(context, UserProfileActivity.class));
    }
  }

  public void navigateToSearchActivity(BaseActivity activity) {
    if (activity != null) {
      Intent intent = new Intent(activity, SearchActivity.class);
      activity.startActivityForResult(intent, RequestCodes.SEARCH);
    }
  }

  public void navigateToBasketActivity(Context context) {
    if (context != null) {
      context.startActivity(new Intent(context, BasketActivity.class));
    }
  }

  public void navigateToProductDetailActivity(Context context, int id, String sku, String title) {
    if (context != null) {
      context.startActivity(new Intent(context, ProductDetailActivity.class).putExtra(Keys.SKU, sku)
          .putExtra(Keys.PRODUCT_ID, id)
          .putExtra(Keys.TITLE, title));
    }
  }

  public void navigateToReviewActivity(Context context, int productId, String name) {
    if (context != null) {
      context.startActivity(
          new Intent(context, ReviewActivity.class).putExtra(Keys.PRODUCT_ID, productId)
              .putExtra(Keys.PRODUCT_NAME, name));
    }
  }

  public void navigateToReviewsActivity(
      Context context, Bundle extras
  ) {
    if (context != null) {
      context.startActivity(new Intent(context, ReviewsActivity.class).putExtras(extras));
    }
  }

  public void navigateToFilterActivity(Context context, HashMap<Integer, List<FilterItem>> filter) {

    if (context != null) {
      BaseActivity baseActivity = (BaseActivity) context;
      baseActivity.startActivityForResult(
          new Intent(context, FilterActivity.class).putExtra(Keys.FILTER, filter),
          RequestCodes.FILTER);
    }
  }
}
