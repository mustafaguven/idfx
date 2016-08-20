package tr.com.idefix.android.view.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import com.google.android.gms.tagmanager.DataLayer;
import com.squareup.picasso.Picasso;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;
import tr.com.idefix.android.R;
import tr.com.idefix.android.contants.Keys;
import tr.com.idefix.android.internal.di.HasComponent;
import tr.com.idefix.android.internal.di.components.DaggerProductDetailActivityComponent;
import tr.com.idefix.android.internal.di.components.ProductDetailActivityComponent;
import tr.com.idefix.android.presenter.IProductDetailPresenter;
import tr.com.idefix.android.view.ProductDetailActivityView;
import tr.com.idefix.domain.GroupedProductAttribute;
import tr.com.idefix.domain.ProductAttribute;
import tr.com.idefix.domain.ProductOther;
import tr.com.idefix.domain.ProductPerson;
import tr.com.idefix.domain.Review;

public class ProductDetailActivity extends BaseActivity
    implements HasComponent<ProductDetailActivityComponent>, ProductDetailActivityView {

  //<editor-fold desc="Fields">

  @Bind(R.id.favorite_view_one_one) CheckBox favorite_view_one_one;

  @Bind(R.id.bell_view_one_one) CheckBox bell_view_one_one;

  @Bind(R.id.favorite_view_one_two) CheckBox favorite_view_one_two;

  @Bind(R.id.bell_view_one_two) CheckBox bell_view_one_two;

  @Bind(R.id.favorite_view_two_one) CheckBox favorite_view_two_one;

  @Bind(R.id.bell_view_two_one) CheckBox bell_view_two_one;

  @Bind(R.id.favorite_view_two_two) CheckBox favorite_view_two_two;

  @Bind(R.id.bell_view_two_two) CheckBox bell_view_two_two;

  @Bind(R.id.favorite_view_three_one) CheckBox favorite_view_three_one;

  @Bind(R.id.bell_view_three_one) CheckBox bell_view_three_one;

  @Bind(R.id.favorite_view_three_two) CheckBox favorite_view_three_two;

  @Bind(R.id.bell_view_three_two) CheckBox bell_view_three_two;

  @Bind(R.id.otherProduct_one_one) CardView otherProduct_one_one;

  @Bind(R.id.otherProduct_one_two) CardView otherProduct_one_two;

  @Bind(R.id.otherProduct_two_one) CardView otherProduct_two_one;

  @Bind(R.id.otherProduct_two_two) CardView otherProduct_two_two;

  @Bind(R.id.otherProduct_three_one) CardView otherProduct_three_one;

  @Bind(R.id.otherProduct_three_two) CardView otherProduct_three_two;

  @Bind(R.id.basket_count_text) AppCompatTextView basket_count_text;

  @Bind(R.id.basket_minus) ImageView basket_minus;

  @Bind(R.id.basket_plus) ImageView basket_plus;

  @Bind(R.id.otherProductsContainerOne) LinearLayout otherProductsContainerOne;

  @Bind(R.id.image_one_one) ImageView image_one_one;

  @Bind(R.id.title_one_one) AppCompatTextView title_one_one;

  @Bind(R.id.oldPrice_one_one) AppCompatTextView oldPrice_one_one;

  @Bind(R.id.price_one_one) AppCompatTextView price_one_one;

  @Bind(R.id.image_one_two) ImageView image_one_two;

  @Bind(R.id.title_one_two) AppCompatTextView title_one_two;

  @Bind(R.id.oldPrice_one_two) AppCompatTextView oldPrice_one_two;

  @Bind(R.id.price_one_two) AppCompatTextView price_one_two;

  @Bind(R.id.otherProductsContainerTwo) LinearLayout otherProductsContainerTwo;

  @Bind(R.id.image_two_one) ImageView image_two_one;

  @Bind(R.id.title_two_one) AppCompatTextView title_two_one;

  @Bind(R.id.oldPrice_two_one) AppCompatTextView oldPrice_two_one;

  @Bind(R.id.price_two_one) AppCompatTextView price_two_one;

  @Bind(R.id.image_two_two) ImageView image_two_two;

  @Bind(R.id.title_two_two) AppCompatTextView title_two_two;

  @Bind(R.id.oldPrice_two_two) AppCompatTextView oldPrice_two_two;

  @Bind(R.id.price_two_two) AppCompatTextView price_two_two;

  @Bind(R.id.otherProductsContainerThree) LinearLayout otherProductsContainerThree;

  @Bind(R.id.image_three_one) ImageView image_three_one;

  @Bind(R.id.title_three_one) AppCompatTextView title_three_one;

  @Bind(R.id.oldPrice_three_one) AppCompatTextView oldPrice_three_one;

  @Bind(R.id.price_three_one) AppCompatTextView price_three_one;

  @Bind(R.id.image_three_two) ImageView image_three_two;

  @Bind(R.id.title_three_two) AppCompatTextView title_three_two;

  @Bind(R.id.oldPrice_three_two) AppCompatTextView oldPrice_three_two;

  @Bind(R.id.price_three_two) AppCompatTextView price_three_two;

  @Bind(R.id.toolbar) Toolbar toolbar;

  @Inject IProductDetailPresenter presenter;

  @Bind(R.id.product_image) ImageView product_image;

  @Bind(R.id.product_name) TextView product_name;

  @Bind(R.id.productAttrContainer) LinearLayout productAttrContainer;

  @Bind(R.id.first_attr) TextView first_attr;

  @Bind(R.id.old_price) TextView old_price;

  @Bind(R.id.price) TextView price;

  @Bind(R.id.brand) TextView brand;

  @Bind(R.id.brand_title) TextView brand_title;

  @Bind(R.id.description_title) TextView description_title;

  @Bind(R.id.description_long) TextView description_long;

  @Bind(R.id.description_short) TextView description_short;

  @Bind(R.id.description_excol) AppCompatCheckBox description_excol;

  @Bind(R.id.persons_container) LinearLayout persons_container;

  @Bind(R.id.lblFavorile) TextView lblFavorile;

  @Bind(R.id.lblFiyatiDusunceUyar) TextView lblFiyatiDusunceUyar;

  @Bind(R.id.favorite_view) CheckBox favorite_view;

  @Bind(R.id.warn_view) CheckBox warn_view;

  @Bind(R.id.review_view) CheckBox review_view;

  @Bind(R.id.category_title) AppCompatTextView category_title;

  @Bind(R.id.basket_count) AppCompatTextView basket_count;

  @Bind(R.id.review_card_first) CardView review_card_first;
  @Bind(R.id.review_car_first_text) TextView review_car_first_text;
  @Bind(R.id.review_car_first_date) TextView review_car_first_date;

  @Bind(R.id.review_card_second) CardView review_card_second;
  @Bind(R.id.review_car_second_text) TextView review_car_second_text;
  @Bind(R.id.review_car_second_date) TextView review_car_second_date;

  @Bind(R.id.review_others) TextView review_others;

  @Bind(R.id.review_no) TextView review_no;

  @Bind(R.id.otherProductsContainer) LinearLayout otherProductsContainer;

  @Bind(R.id.add_item_to_basket) AppCompatButton add_item_to_basket;

  @Bind(R.id.first_gattr_container) LinearLayout first_gattr_container;
  @Bind(R.id.first_gattr) TextView first_gattr;
  @Bind(R.id.first_gattr_hr) View first_gattr_hr;

  @Bind(R.id.second_gattr_container) LinearLayout second_gattr_container;
  @Bind(R.id.second_gattr) TextView second_gattr;
  @Bind(R.id.second_gattr_hr) View second_gattr_hr;

  @Bind(R.id.third_gattr_container) LinearLayout third_gattr_container;
  @Bind(R.id.third_gattr) TextView third_gattr;
  @Bind(R.id.third_gattr_hr) View third_gattr_hr;

  @Bind(R.id.fourth_gattr_container) LinearLayout fourth_gattr_container;
  @Bind(R.id.fourth_gattr) TextView fourth_gattr;
  @Bind(R.id.fourth_gattr_hr) View fourth_gattr_hr;

  @Bind(R.id.groupedProductAttrContainer) LinearLayout groupedProductAttrContainer;

  ProductDetailActivityComponent productDetailActivityComponent;
  private boolean skipChangesFavorite;
  private boolean skipChangesWarn;
  //</editor-fold>

  @OnClick(R.id.barcode_icon) void onBarcodeClicked() {
    startActivity(BarcodeInfoActivity.createLauncher(this));
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_product_detail);
    ////////////////////////////////////////////////////////////////////////////////////////////
    ButterKnife.bind(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    productDetailActivityComponent = DaggerProductDetailActivityComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
    productDetailActivityComponent.inject(this);
    ////////////////////////////////////////////////////////////////////////////////////////////
    presenter.setView(this);
    presenter.processIntent(getIntent());
    presenter.setDataLayer(getDataLayer());
    ////////////////////////////////////////////////////////////////////////////////////////////
    deviceUtils.setStatusBarColor(this, getResources().getColor(R.color.bg_red_toolbar_start));
    ////////////////////////////////////////////////////////////////////////////////////////////
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    toolbar.setNavigationOnClickListener(v -> finish());
  }

  @Override protected void onResume() {
    //TODO:known bug check alarm and fav for this product and other products
    super.onResume();
    presenter.resume();
  }

  @Override protected void onPause() {
    super.onPause();
    presenter.pause();
  }

  @Override public ProductDetailActivityComponent getComponent() {
    return productDetailActivityComponent;
  }

  @Override public void setTitle(String title) {
    category_title.setText(title);
  }

  @Override public void setImage(String imageUrl) {

    //TODO: BURAYLA SONRA İLGİLENİLECEK!!!
    Picasso.with(this).load(imageUrl)

                /*.transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {

                        int originalWidth = source.getWidth();
                        int originalHeight = source.getHeight();
                        int newWidth = -1;
                        int newHeight = -1;
                        float multFactor = -1.0F;
                        if (originalHeight > originalWidth) {
                            newHeight = (int) deviceUtils.dpToPx(ProductDetailActivity.this, 200);
                            multFactor = (float) originalWidth / (float) originalHeight;
                            newWidth = (int) (newHeight * multFactor);
                        } else if (originalWidth > originalHeight) {
                            newWidth = deviceUtils.getDeviceWidth(ProductDetailActivity.this);
                            multFactor = (float) originalHeight / (float) originalWidth;
                            newHeight = (int) (newWidth * multFactor);
                        } else if (originalHeight == originalWidth) {
                            //TODO: check if device landscape or potrait than choose
                            newHeight = (int) deviceUtils.dpToPx(ProductDetailActivity.this, 200);
                            newWidth = (int) deviceUtils.dpToPx(ProductDetailActivity.this, 200);
                        }
                        Bitmap retVal = Bitmap.createScaledBitmap(source, newWidth, newHeight,
                        false);
                        if (retVal != source) {
                            source.recycle();
                        }
                        return retVal;
                    }

                    @Override
                    public String key() {
                        return imageUrl;
                    }
                })*/.into(product_image);
  }

  @Override public void setName(String name) {
    product_name.setText(name);
  }

  @Override public void setCartItemCount(int cartItemCount) {
    basket_count.setVisibility(cartItemCount > 0 ? View.VISIBLE : View.INVISIBLE);
    basket_count.setText(String.valueOf(cartItemCount));
  }

  @Override public void bindProductAttributes(List<ProductAttribute> productAttributes) {
    productAttrContainer.setVisibility(View.VISIBLE);

    first_attr.setText(productAttributes.get(0).textPrompt());

    if (TextUtils.isEmpty(productAttributes.get(0).textPrompt())) {
      productAttrContainer.setVisibility(View.GONE);
    }
  }

  @Override
  public void bindGroupedProductAttributes(List<GroupedProductAttribute> groupedProductAttributes) {

    groupedProductAttrContainer.setVisibility(View.VISIBLE);

    for (int i = 0; i < groupedProductAttributes.size(); i++) {

      GroupedProductAttribute gProductAttr = groupedProductAttributes.get(i);

      if (i == 0) {
        first_gattr_container.setVisibility(View.VISIBLE);
        first_gattr.setText(gProductAttr.attributeName());

        first_gattr.setTextColor(Color.parseColor("#000000"));
        first_gattr_hr.setBackgroundColor(Color.parseColor("#000000"));

        if (!TextUtils.isEmpty(gProductAttr.oldPriceString())) {
          old_price.setVisibility(View.VISIBLE);
          old_price.setText(gProductAttr.oldPriceString());
        }
        price.setText(gProductAttr.priceString());

        presenter.setSelectedGroupedProductAttribute(gProductAttr);
      }
      if (i == 1) {
        second_gattr_container.setVisibility(View.VISIBLE);
        second_gattr.setText(gProductAttr.attributeName());
      }
      if (i == 2) {
        third_gattr_container.setVisibility(View.VISIBLE);
        third_gattr.setText(gProductAttr.attributeName());
      }
      if (i == 3) {
        fourth_gattr_container.setVisibility(View.VISIBLE);
        fourth_gattr.setText(gProductAttr.attributeName());
      }
    }

    productAttrContainer.setVisibility(View.GONE);
    ButterKnife.findById(this, R.id.productAttrContainerHr).setVisibility(View.GONE);

    if (presenter.selectEBook()) {
      if (presenter.getEBookIndex() > -1) {
        if (presenter.getEBookIndex() == 0) {
          firstGAttrSelected();
        }
        if (presenter.getEBookIndex() == 1) {
          secondGAttrSelected();
        }
        if (presenter.getEBookIndex() == 2) {
          thirdGAttrSelected();
        }
        if (presenter.getEBookIndex() == 3) {
          fourthGAttrSelected();
        }
      }
    }
  }

  @OnClick(R.id.first_gattr_container) void firstGAttrSelected() {

    first_gattr.setTextColor(Color.parseColor("#000000"));
    first_gattr_hr.setBackgroundColor(Color.parseColor("#000000"));

    GroupedProductAttribute gProductAttr = presenter.getGroupedProductAttribute(0);
    presenter.setSelectedGroupedProductAttribute(gProductAttr);

    if (!TextUtils.isEmpty(gProductAttr.oldPriceString())) {
      old_price.setVisibility(View.VISIBLE);
      old_price.setText(gProductAttr.oldPriceString());
    } else {
      old_price.setVisibility(View.GONE);
      old_price.setText("");
    }
    price.setText(gProductAttr.priceString());

    second_gattr.setTextColor(Color.parseColor("#969a9f"));
    second_gattr_hr.setBackgroundColor(Color.parseColor("#969a9f"));

    third_gattr.setTextColor(Color.parseColor("#969a9f"));
    third_gattr_hr.setBackgroundColor(Color.parseColor("#969a9f"));

    fourth_gattr.setTextColor(Color.parseColor("#969a9f"));
    fourth_gattr_hr.setBackgroundColor(Color.parseColor("#969a9f"));
  }

  @OnClick(R.id.second_gattr_container) void secondGAttrSelected() {

    first_gattr.setTextColor(Color.parseColor("#969a9f"));
    first_gattr_hr.setBackgroundColor(Color.parseColor("#969a9f"));

    second_gattr.setTextColor(Color.parseColor("#000000"));
    second_gattr_hr.setBackgroundColor(Color.parseColor("#000000"));

    GroupedProductAttribute gProductAttr = presenter.getGroupedProductAttribute(1);
    presenter.setSelectedGroupedProductAttribute(gProductAttr);

    if (presenter.isEBook()) {
      basket_count_text.setText("1");
    }

    if (!TextUtils.isEmpty(gProductAttr.oldPriceString())) {
      old_price.setVisibility(View.VISIBLE);
      old_price.setText(gProductAttr.oldPriceString());
    } else {
      old_price.setVisibility(View.GONE);
      old_price.setText("");
    }
    price.setText(gProductAttr.priceString());

    third_gattr.setTextColor(Color.parseColor("#969a9f"));
    third_gattr_hr.setBackgroundColor(Color.parseColor("#969a9f"));

    fourth_gattr.setTextColor(Color.parseColor("#969a9f"));
    fourth_gattr_hr.setBackgroundColor(Color.parseColor("#969a9f"));
  }

  @OnClick(R.id.third_gattr_container) void thirdGAttrSelected() {

    first_gattr.setTextColor(Color.parseColor("#969a9f"));
    first_gattr_hr.setBackgroundColor(Color.parseColor("#969a9f"));

    second_gattr.setTextColor(Color.parseColor("#969a9f"));
    second_gattr_hr.setBackgroundColor(Color.parseColor("#969a9f"));

    third_gattr.setTextColor(Color.parseColor("#000000"));
    third_gattr_hr.setBackgroundColor(Color.parseColor("#000000"));

    GroupedProductAttribute gProductAttr = presenter.getGroupedProductAttribute(2);
    presenter.setSelectedGroupedProductAttribute(gProductAttr);

    if (!TextUtils.isEmpty(gProductAttr.oldPriceString())) {
      old_price.setVisibility(View.VISIBLE);
      old_price.setText(gProductAttr.oldPriceString());
    } else {
      old_price.setVisibility(View.GONE);
      old_price.setText("");
    }
    price.setText(gProductAttr.priceString());

    fourth_gattr.setTextColor(Color.parseColor("#969a9f"));
    fourth_gattr_hr.setBackgroundColor(Color.parseColor("#969a9f"));
  }

  @OnClick(R.id.fourth_gattr_container) void fourthGAttrSelected() {

    first_gattr.setTextColor(Color.parseColor("#969a9f"));
    first_gattr_hr.setBackgroundColor(Color.parseColor("#969a9f"));

    second_gattr.setTextColor(Color.parseColor("#969a9f"));
    second_gattr_hr.setBackgroundColor(Color.parseColor("#969a9f"));

    third_gattr.setTextColor(Color.parseColor("#969a9f"));
    third_gattr_hr.setBackgroundColor(Color.parseColor("#969a9f"));

    fourth_gattr.setTextColor(Color.parseColor("#000000"));
    fourth_gattr_hr.setBackgroundColor(Color.parseColor("#000000"));

    GroupedProductAttribute gProductAttr = presenter.getGroupedProductAttribute(3);
    presenter.setSelectedGroupedProductAttribute(gProductAttr);

    if (!TextUtils.isEmpty(gProductAttr.oldPriceString())) {
      old_price.setVisibility(View.VISIBLE);
      old_price.setText(gProductAttr.oldPriceString());
    } else {
      old_price.setVisibility(View.GONE);
      old_price.setText("");
    }
    price.setText(gProductAttr.priceString());
  }

  @Override public void bindOldPrice(String oldPrice) {
    old_price.setVisibility(View.VISIBLE);
    old_price.setText(oldPrice);
    old_price.setPaintFlags(old_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
  }

  @Override public void bindPrice(String p) {
    price.setText(p);
  }

  @Override public void bindProductPersons(List<ProductPerson> productPersons) {
    persons_container.setVisibility(View.VISIBLE);

    for (ProductPerson pp : productPersons) {

      persons_container.addView(getPersonTextView(25, pp.groupName(), 17, Typeface.BOLD));
      persons_container.addView(getPersonTextView(5, pp.name(), 14, Typeface.NORMAL));
    }
  }

  TextView getPersonTextView(int topMargin, String text, int size, int typeface) {
    TextView textView = new TextView(this);
    LinearLayout.LayoutParams llp =
        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    llp.setMargins(0, (int) deviceUtils.dpToPx(this, topMargin), 0, 0);
    textView.setLayoutParams(llp);
    textView.setTextColor(Color.BLACK);
    textView.setText(text);
    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    textView.setTypeface(null, typeface);
    textView.setGravity(Gravity.CENTER);

    return textView;
  }

  @Override public void bindBrand(String b) {
    brand.setVisibility(View.VISIBLE);
    brand_title.setVisibility(View.VISIBLE);
    brand.setText(b);
  }

  @Override public void bindFullDescription(String s) {
    description_title.setVisibility(View.VISIBLE);
    description_long.setText(Html.fromHtml(s));
    description_excol.setVisibility(View.VISIBLE);
  }

  @Override public void bindShortDescription(String s) {
    description_title.setVisibility(View.VISIBLE);
    description_short.setText(s);
    description_short.setVisibility(View.VISIBLE);
    description_excol.setVisibility(View.VISIBLE);
  }

  @OnCheckedChanged(R.id.description_excol) void expandOrCollapseDescription(boolean checked) {
    if (checked) {
      description_long.setVisibility(View.VISIBLE);
      description_short.setVisibility(View.GONE);
    } else {
      description_long.setVisibility(View.GONE);
      description_short.setVisibility(View.VISIBLE);
    }
  }

  @OnCheckedChanged(R.id.favorite_view) void favorite(boolean checked) {

    if (skipChangesFavorite) {
      skipChangesFavorite = false;
      return;
    }
    if (!presenter.isloggedIn()) {
      navigator.navigateToLoginActivity(this);
      favorite_view.setChecked(false);
      lblFavorile.setTextColor(Color.parseColor("#a7a7a7"));
      return;
    }

    favorite_view.setEnabled(false);
    presenter.favorite(checked);
  }

  @OnCheckedChanged(R.id.warn_view) void warn(boolean checked) {

    if (skipChangesWarn) {
      skipChangesWarn = false;
      return;
    }
    if (!presenter.isloggedIn()) {
      navigator.navigateToLoginActivity(this);
      warn_view.setChecked(false);
      lblFiyatiDusunceUyar.setTextColor(Color.parseColor("#a7a7a7"));
      return;
    }

    warn_view.setEnabled(false);

    if (checked) {
      final Dialog dialog = new Dialog(this);
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
      dialog.setCancelable(false);
      dialog.setContentView(R.layout.dialog_bell);
      dialog.setTitle("");

      dialog.findViewById(R.id.cancel).setOnClickListener(v -> {
        dialog.dismiss();
        warn_view.setEnabled(true);
        skipChangesWarn = true;
        warn_view.setChecked(!checked);
      });

      EditText indays = (EditText) dialog.findViewById(R.id.indays);
      EditText below_price = (EditText) dialog.findViewById(R.id.below_price);

      dialog.findViewById(R.id.ok).setOnClickListener(v -> {

        if (below_price.getText().toString().length() != 0) {
          presenter.warn(checked, indays.getText().toString(), below_price.getText().toString());
          dialog.dismiss();
        } else {
          below_price.setError("Fiyat girmek zorunludur");
        }
      });

      dialog.show();
    } else {
      presenter.warn(checked);
    }
  }

  @Override public void setItemFavorite() {

    skipChangesFavorite = true;
    favorite_view.setChecked(true);
    lblFavorile.setTextColor(Color.parseColor("#000000"));
  }

  @Override public void setEnabledFavoriteView(boolean b) {
    favorite_view.setEnabled(b);
  }

  @Override public void setEnabledWarnView(boolean b) {
    warn_view.setEnabled(b);
  }

  @Override public void setItemWarned() {
    skipChangesWarn = true;
    warn_view.setChecked(true);
    lblFiyatiDusunceUyar.setTextColor(Color.parseColor("#000000"));
  }

  @Override public void setItemWarned(boolean b) {
    skipChangesWarn = true;
    warn_view.setChecked(b);
  }

  @OnCheckedChanged(R.id.share_view) void share(boolean checked) {

    try {
      Intent sendIntent = new Intent();
      sendIntent.setAction(Intent.ACTION_SEND);
      sendIntent.putExtra(Intent.EXTRA_TEXT, presenter.getShareText());
      sendIntent.setType("text/plain");
      startActivity(sendIntent);
    } catch (Exception e) {
      Timber.e(e, "can not share");
    }
  }

  @OnCheckedChanged(R.id.review_view) void review(boolean checked) {
    if (presenter.isloggedIn() && presenter.getProductId() != 0) {
      navigator.navigateToReviewActivity(this, presenter.getProductId(),
          presenter.getProductName());
    } else {
      navigator.navigateToLoginActivity(this);
    }
  }

  @OnClick(R.id.search_icon) void search() {
    navigator.navigateToSearchActivity(this);
  }

  @OnClick({ R.id.basket_count, R.id.basket_icon }) void viewBasket() {
    if (presenter.isloggedIn()) {
      navigator.navigateToBasketActivity(this);
    }
  }

  @Override public void bindReviews(List<Review> reviews) {

    Review firstReview = reviews.get(0);
    if (firstReview != null) {
      review_card_first.setVisibility(View.VISIBLE);
      review_car_first_text.setText(firstReview.review());
      review_car_first_date.setText(firstReview.date());
    }

    if (reviews.size() >= 1) {
      Review secondReview = reviews.get(1);

      review_card_second.setVisibility(View.VISIBLE);
      review_car_second_text.setText(secondReview.review());
      review_car_second_date.setText(secondReview.date());
    }

    if (reviews.size() >= 2) {
      review_others.setVisibility(View.VISIBLE);
    }
  }

  @Override public void noReview() {
    review_no.setVisibility(View.VISIBLE);
  }

  @OnClick(R.id.review_others) void otherReviews() {
    Bundle extras = getIntent().getExtras();
    extras.putString(Keys.TITLE, product_name.getText().toString());
    navigator.navigateToReviewsActivity(this, extras);
  }

  void bindOtherProduct(
      ProductOther model, LinearLayout rowContainer, CardView cardView, ImageView imageView,
      AppCompatTextView title, AppCompatTextView oldPriceView, AppCompatTextView priceView,
      CheckBox fav, CheckBox alarm
  ) {

    rowContainer.setVisibility(View.VISIBLE);

    cardView.setVisibility(View.VISIBLE);

    cardView.setOnClickListener(v -> {
      navigator.navigateToProductDetailActivity(ProductDetailActivity.this, model.id(), model.sku(),
          product_name.getText().toString());

      getDataLayer().pushEvent("productClick", DataLayer.mapOf(
          "ecommerce", DataLayer.mapOf("click",
              DataLayer.mapOf("actionField", DataLayer.mapOf("list", "SubCategory"),
                  // Ürünün Clicklendiği Sayfa Tipi (Search Results, Main Category,
                  // SubCategory, Homepage)
                  "products", DataLayer.listOf(
                      DataLayer.mapOf("name", product_name.getText().toString(), "id", model.id(),
                          "price", "undefined", "brand", "undefined", "category", "undefined"))))));
    });

    Picasso.with(this).load(model.imageUrl()).into(imageView);

    title.setText(model.name());

    if (model.oldPrice() != null) {
      oldPriceView.setText(String.valueOf(model.oldPrice()) + " TL");
      oldPriceView.setPaintFlags(old_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    priceView.setText(String.valueOf(model.price()) + " TL");

    fav.setOnClickListener(null);
    alarm.setOnClickListener(null);
    fav.setOnCheckedChangeListener(null);
    alarm.setOnCheckedChangeListener(null);

    if (!presenter.isloggedIn()) {
      fav.setOnClickListener(v -> {
        navigator.navigateToLoginActivity(ProductDetailActivity.this);
        fav.setChecked(false);
      });
      alarm.setOnClickListener(v -> {
        navigator.navigateToLoginActivity(ProductDetailActivity.this);
        alarm.setChecked(false);
      });
    } else {
      fav.setChecked(presenter.isInfavList(model.sku()));

      fav.setOnCheckedChangeListener(
          (buttonView, isChecked) -> presenter.favorite(isChecked, model.sku()));

      alarm.setChecked(presenter.isInAlarmList(model.sku()));

      alarm.setOnCheckedChangeListener((buttonView, isChecked) -> {

        if (isChecked) {

          final Dialog dialog = new Dialog(ProductDetailActivity.this);
          dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
          dialog.setCancelable(false);
          dialog.setContentView(R.layout.dialog_bell);
          dialog.setTitle("");

          dialog.findViewById(R.id.cancel).setOnClickListener(v -> dialog.dismiss());

          EditText indays = (EditText) dialog.findViewById(R.id.indays);
          EditText below_price = (EditText) dialog.findViewById(R.id.below_price);

          dialog.findViewById(R.id.ok).setOnClickListener(v -> {
            presenter.addItemToAlarmList(indays.getText().toString(),
                below_price.getText().toString(), model.sku());
            dialog.dismiss();
          });

          dialog.show();
        } else {
          presenter.removeItemFromAlarmList(model.sku());
        }
      });
    }
  }

  @Override public void bindOtherProducts(List<ProductOther> productOthers) {

    if (productOthers != null && productOthers.size() > 0) {
      otherProductsContainer.setVisibility(View.VISIBLE);

      if (productOthers.size() > 0) {

        bindOtherProduct(productOthers.get(0), otherProductsContainerOne, otherProduct_one_one,
            image_one_one, title_one_one, oldPrice_one_one, price_one_one, favorite_view_one_one,
            bell_view_one_one);
      }

      if (productOthers.size() > 1) {
        bindOtherProduct(productOthers.get(1), otherProductsContainerOne, otherProduct_one_two,
            image_one_two, title_one_two, oldPrice_one_two, price_one_two, favorite_view_one_two,
            bell_view_one_two);
      }
      ///
      if (productOthers.size() > 2) {
        bindOtherProduct(productOthers.get(2), otherProductsContainerTwo, otherProduct_two_one,
            image_two_one, title_two_one, oldPrice_two_one, price_two_one, favorite_view_two_one,
            bell_view_two_one);
      }

      if (productOthers.size() > 3) {
        bindOtherProduct(productOthers.get(3), otherProductsContainerTwo, otherProduct_two_two,
            image_two_two, title_two_two, oldPrice_two_two, price_two_two, favorite_view_two_two,
            bell_view_two_two);
      }

      ///
      if (productOthers.size() > 4) {
        bindOtherProduct(productOthers.get(4), otherProductsContainerThree, otherProduct_three_one,
            image_three_one, title_three_one, oldPrice_three_one, price_three_one,
            favorite_view_three_one, bell_view_three_one);
      }

      if (productOthers.size() > 5) {
        bindOtherProduct(productOthers.get(5), otherProductsContainerThree, otherProduct_three_two,
            image_three_two, title_three_two, oldPrice_three_two, price_three_two,
            favorite_view_three_two, bell_view_three_two);
      }
    }
  }

  @OnClick(R.id.basket_plus) void basketPlus() {
    try {

      // check if it e-kitap then return
      if (presenter.isEBook()) {
        return;
      }
      basket_count_text.setText(
          String.valueOf(Integer.parseInt(basket_count_text.getText().toString()) + 1));
    } catch (Exception e) {
      basket_count_text.setText("1");
    }
  }

  @OnClick(R.id.basket_minus) void basketMinus() {
    try {
      int nowNum = Integer.parseInt(basket_count_text.getText().toString());
      if (nowNum == 1) return;
      basket_count_text.setText(String.valueOf(nowNum - 1));
    } catch (Exception e) {
      basket_count_text.setText("1");
    }
  }

  @OnClick(R.id.add_item_to_basket) void add_item_to_basket() {

    if (presenter.isloggedIn()) {

      add_item_to_basket.setEnabled(false);
      add_item_to_basket.setAlpha(0.5f);
      presenter.addItemToBasket(Integer.parseInt(basket_count_text.getText().toString()));
    } else {
      navigator.navigateToLoginActivity(this);
    }
  }

  @Override public void enableBasket() {
    add_item_to_basket.setEnabled(true);
    add_item_to_basket.setAlpha(1f);
  }

  @Override public void setBasketCount(int count) {
    basket_count.setText(String.valueOf(count));
    basket_count.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
  }

  @Override public void itemAddedToWishList() {
    showDialog("Ürün favori listenize eklendi.");
    lblFavorile.setTextColor(Color.parseColor("#000000"));
  }

  @Override public void itemRemovedFromWishList() {
    showDialog("Ürün favori listenizden çıkarıldı.");
    lblFavorile.setTextColor(Color.parseColor("#a7a7a7"));
  }

  @Override public void itemAddedToAlarmList() {
    showDialog("Ürün alarm listenize eklendi.");
    lblFiyatiDusunceUyar.setTextColor(Color.parseColor("#000000"));
  }

  @Override public void itemRemovedFromAlarmList() {
    showDialog("Ürün alarm listenizden çıkarıldı.");
    lblFiyatiDusunceUyar.setTextColor(Color.parseColor("#a7a7a7"));
  }

  @Override public void itemAddedToBasket() {
    showDialog("Ürün başarıyla sepetinize eklendi.");
    presenter.getBasketCount();
  }

  @Override public void showError(String message) {
    showDialog(message);
  }

  @Override public void setEnableSepeteEkle(boolean b) {
    add_item_to_basket.setEnabled(b);
  }

  void showDialog(String message) {
    final Dialog dialog = new Dialog(this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.dialog_ok);
    dialog.setTitle("");

    View container = dialog.findViewById(R.id.container);
    container.getLayoutParams().width = (int) (deviceUtils.getDeviceWidth(this) * 0.90);
    dialog.findViewById(R.id.ok).setOnClickListener(v -> dialog.dismiss());

    TextView content = (TextView) dialog.findViewById(R.id.content);
    content.setText(message);

    dialog.show();
  }
}
