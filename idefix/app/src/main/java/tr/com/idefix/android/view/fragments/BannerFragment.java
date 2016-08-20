package tr.com.idefix.android.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.google.android.gms.tagmanager.DataLayer;
import com.squareup.picasso.Picasso;
import com.zanlabs.widget.infiniteviewpager.InfinitePagerAdapter;
import com.zanlabs.widget.infiniteviewpager.InfiniteViewPager;
import com.zanlabs.widget.infiniteviewpager.indicator.CirclePageIndicator;
import java.util.ArrayList;
import java.util.List;
import mu.comon.utils.DeviceUtils;
import tr.com.idefix.android.R;
import tr.com.idefix.android.contants.Keys;
import tr.com.idefix.android.model.BannerModel;
import tr.com.idefix.android.view.activities.BaseActivity;
import tr.com.idefix.android.view.activities.SearchActivity;

/**
 * Created by utkan on 08/09/15.
 */
public class BannerFragment extends BaseFragment {

  //<editor-fold desc="Fields">
  BannersFragmentAdapter mAdapter;
  @Bind(R.id.pager) InfiniteViewPager mPager;
  @Bind(R.id.indicator) CirclePageIndicator indicator;
  //</editor-fold>

  @Override public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
  ) {
    View view = inflater.inflate(R.layout.fragment_banners, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  private int dpToPx(int dp) {
    if (getActivity() != null) {
      return (int) new DeviceUtils().dpToPx(getActivity(), dp);
    } else {
      return 0;
    }
  }

  @Override public void onStart() {
    super.onStart();
    if (mPager != null) mPager.startAutoScroll();
  }

  @Override public void onStop() {
    if (mPager != null) mPager.stopAutoScroll();
    super.onStop();
  }

  public void setBanners(ArrayList<BannerModel> banners) {

    if (banners != null && banners.size() > 0) {

      mAdapter = new BannersFragmentAdapter(getContext(), banners);

      if (isTablet()) {
        mPager.setPadding(0, dpToPx(20), 0, dpToPx(20));
      }

      mPager.setAdapter(mAdapter);
      mPager.setAutoScrollTime(5000);
      mPager.startAutoScroll();
      indicator.setViewPager(mPager);

      final float density = getResources().getDisplayMetrics().density;
      indicator.setBackgroundColor(Color.parseColor("#00000000"));
      //indicator.setRadius(10);
      indicator.setPageColor(Color.parseColor("#d7dade")); //Color.parseColor("#d7dadf")
      indicator.setFillColor(Color.parseColor("#1e4d96"));
      //indicator.setStrokeWidth(1 * density);
    }
  }

  class BannersFragmentAdapter extends InfinitePagerAdapter {
    private final String URUN = "Ürün";
    private final String KATALOG = "Katalog";
    private final String KATEGORI = "Kategori";

    List<BannerModel> banners;
    Context context;

    public BannersFragmentAdapter(Context context, List<BannerModel> banners) {
      this.context = context;
      this.banners = banners;
    }

    @Override public View getView(int position, View view, ViewGroup container) {
      ViewHolder holder;
      if (view != null) {
        holder = (ViewHolder) view.getTag();
      } else {
        view = LayoutInflater.from(context).inflate(R.layout.item_banners, container, false);
        holder = new ViewHolder(view);
        view.setTag(holder);
      }
      BannerModel item = banners.get(position);
      holder.position = position;
      Picasso.with(getActivity()).load(getUrl(item)).into(holder.image);
      holder.image.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          navigateBanner(item);
        }
      });
      return view;
    }

    private String getUrl(BannerModel model) {

      String url = model.imageUrl();

      if (!TextUtils.isEmpty(url)) {

        if (url.substring(0, 1).equals("/")) {
          url = url.substring(1, url.length());
        }
        if (!url.startsWith("http://")) {
          url = "http://" + url;
        }
       /* ImageView imageView = new ImageView(getActivity());

        if (!TextUtils.isEmpty(model.linkUrl())) {

          imageView.setOnClickListener((View v) -> {
            //navigateBanner(model);
          });
        }
        imageView.setPadding(0, 0, 0, 0);*/
      }

      return url;
    }

    @Override public int getItemCount() {
      return banners == null ? 0 : banners.size();
    }

    private class ViewHolder {
      public int position;
      ImageView image;

      public ViewHolder(View view) {
        image = (ImageView) view.findViewById(R.id.image);
      }
    }

    private void navigateBanner(BannerModel model) {

      if (model.type().contentEquals(URUN)) {
        BaseActivity activity = (BaseActivity) getActivity();
        activity.getNavigator()
            .navigateToProductDetailActivity(getActivity(),
                Integer.parseInt(model.catalogorproduct()), model.catalogorproduct(),
                model.topic());

        activity.getDataLayer()
            .pushEvent(
                "productClick", DataLayer.mapOf(
                    "ecommerce", DataLayer.mapOf("click",
                        DataLayer.mapOf("actionField", DataLayer.mapOf("list", "Homepage"),
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
}
