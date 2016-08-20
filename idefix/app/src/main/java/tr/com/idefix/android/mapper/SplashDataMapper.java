package tr.com.idefix.android.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.model.BannerModel;
import tr.com.idefix.android.model.MainCategoryModel;
import tr.com.idefix.domain.Banner;
import tr.com.idefix.domain.MainCategory;

/**
 * Created by utkan on 01/09/15.
 */
@PerActivity public class SplashDataMapper {

  @Inject public SplashDataMapper() {
  }

  public List<MainCategoryModel> transformMainCategory(List<MainCategory> mainCategories) {

    List<MainCategoryModel> retList = null;

    if (mainCategories != null) {

      retList = new ArrayList<>(mainCategories.size());

      for (MainCategory mainCategory : mainCategories) {

        MainCategoryModel mainCategoryModel =
            new MainCategoryModel().id(mainCategory.getId()).name(mainCategory.getName());

        retList.add(mainCategoryModel);
      }
    }

    return retList;
  }

  public List<BannerModel> transformBanner(List<Banner> bannerList) {

    List<BannerModel> retList = null;

    if (bannerList != null) {

      retList = new ArrayList<>(bannerList.size());

      for (Banner banner : bannerList) {

        BannerModel bannerModel = new BannerModel().topic(banner.topic())
            .linkUrl(banner.linkUrl())
            .imageUrl(banner.imageUrl())
            .imageUrl(banner.imageUrl())
            .type(banner.type())
            .catalogorproduct(banner.catalogorproduct())
            .bannerHtml(banner.bannerHtml());

        retList.add(bannerModel);
      }
    }

    return retList;
  }
}
