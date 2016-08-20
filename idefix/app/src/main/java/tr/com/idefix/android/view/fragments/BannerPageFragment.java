package tr.com.idefix.android.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.google.android.gms.tagmanager.DataLayer;
import com.squareup.picasso.Picasso;
import mu.comon.utils.JsonUtils;
import tr.com.idefix.android.contants.Keys;
import tr.com.idefix.android.model.BannerModel;
import tr.com.idefix.android.view.activities.BaseActivity;
import tr.com.idefix.android.view.activities.SearchActivity;

/**
 * Created on 9.7.15.
 */
public final class BannerPageFragment extends Fragment {

  private static final String KEY_CONTENT = "BannerPageFragment:Content";
  private final String URUN = "Ürün";
  private final String KATALOG = "Katalog";
  private final String KATEGORI = "Kategori";
  private BannerModel mContent;

  public static Fragment newInstance(BannerModel bannerModel) {

    BannerPageFragment fragment = new BannerPageFragment();

    fragment.mContent = bannerModel;

    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
      mContent = new JsonUtils().getObjectFrom(savedInstanceState.getString(KEY_CONTENT),
          BannerModel.class);
    }
  }

  @Override public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
  ) {

    LinearLayout layout = new LinearLayout(getActivity());
    layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT));
    layout.setGravity(Gravity.CENTER);

    String url = mContent.imageUrl();

    if (!TextUtils.isEmpty(url)) {

      if (url.substring(0, 1).equals("/")) {
        url = url.substring(1, url.length());
      }
      if (!url.startsWith("http://")) {
        url = "http://" + url;
      }
      ImageView imageView = new ImageView(getActivity());

      if (!TextUtils.isEmpty(mContent.linkUrl())) {

        imageView.setOnClickListener((View v) -> {
          navigateBanner(mContent);
        });
      }
      imageView.setPadding(0, 0, 0, 0);

      Picasso.with(getActivity()).load(url).into(imageView);

      layout.addView(imageView);
    }

    return layout;
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(KEY_CONTENT, new JsonUtils().getJson(mContent));
  }

  private void navigateBanner(BannerModel model) {

    if (model.type().contentEquals(URUN)) {
      BaseActivity activity = (BaseActivity) getActivity();
      activity.getNavigator()
          .navigateToProductDetailActivity(getActivity(),
              Integer.parseInt(model.catalogorproduct()), model.catalogorproduct(), model.topic());

      activity.getDataLayer()
          .pushEvent("productClick", DataLayer.mapOf(
              "ecommerce", DataLayer.mapOf(
                  "click", DataLayer.mapOf("actionField", DataLayer.mapOf("list", "Homepage"),
                      // Ürünün Clicklendiği Sayfa Tipi (Search Results, Main Category,
                      // SubCategory, Homepage)
                      "products", DataLayer.listOf(
                          DataLayer.mapOf("name", "undefined", "id", model.catalogorproduct(),
                              "price", "undefined", "brand", "undefined", "category",
                              model.topic()))))));
    } else if (model.type().contentEquals(KATALOG)) {

      Intent i = new Intent(getActivity(), SearchActivity.class);
      i.putExtra(Keys.OPEN_FOR_CATALOG, model.catalogorproduct());
      i.putExtra(Keys.CATALOG_TOPIC, model.topic());
      startActivity(i);
    } else if (model.type().contentEquals(KATEGORI)) {
      //MainCategoryModel yaratip navigator'dan navigateToCategoriesActivity'a gönder

    }
  }
}
