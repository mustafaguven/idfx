package tr.com.idefix.domain.mapper;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import tr.com.idefix.data.entity.BannerEntity;
import tr.com.idefix.data.entity.BannerResponseEntity;
import tr.com.idefix.data.entity.MainCategoryEntity;
import tr.com.idefix.data.entity.SettingsResponseEntity;
import tr.com.idefix.domain.Banner;
import tr.com.idefix.domain.MainCategory;
import tr.com.idefix.domain.SettingsItem;

public class SplashEntityDataMapper {

  public List<MainCategory> transform(List<MainCategoryEntity> entityList) {

    List<MainCategory> retList = null;

    if (entityList != null) {

      retList = new ArrayList<>();

      for (MainCategoryEntity entity : entityList) {

        MainCategory mainCategory = new MainCategory();
        mainCategory.setId(entity.id())
            .setName(entity.name())
            .setDisplayOrder(entity.displayOrder());

        retList.add(mainCategory);
      }

      Collections.sort(retList, (lhs, rhs) -> {
        if (lhs.getDisplayOrder() < rhs.getDisplayOrder()) return -1;
        if (lhs.getDisplayOrder() == rhs.getDisplayOrder()) return 0;
        return 1;
      });
    }

    return retList;
  }

  public List<Banner> transform(BannerResponseEntity bannerResponseEntity) {

    List<Banner> retList = null;

    if (bannerResponseEntity != null &&
        bannerResponseEntity.getSuccess() != null &&
        bannerResponseEntity.getSuccess() &&
        bannerResponseEntity.getBannerEntityList() != null &&
        bannerResponseEntity.getBannerEntityList().size() > 0) {

      retList = new ArrayList<>();

      for (BannerEntity entity : bannerResponseEntity.getBannerEntityList()) {

        Banner banner = new Banner();

        if (entity.getActive() != null && entity.getActive()) {

          banner.active(true);

          String startDate = entity.getStartDate();
          String endDate = entity.getEndDate();

          if (!TextUtils.isEmpty(startDate)) {
            startDate = startDate.substring(6, startDate.length() - 2);
            banner.startDate(Long.parseLong(startDate));
          }
          if (!TextUtils.isEmpty(endDate)) {
            endDate = endDate.substring(6, endDate.length() - 2);
            banner.endDate(Long.parseLong(endDate));
          }

          Date current = new Date();

          if (banner.startDate() > 0 && banner.startDate() < current.getTime()) {
            // active
          } else {
            banner.active(false);
          }

          if (banner.endDate() > 0 && banner.endDate() > current.getTime()) {
            // active
          } else {
            banner.active(false);
          }
        }

        if (banner.active()) {

          banner.displayOrder(entity.getDisplayOrder())
              .bannerHtml(entity.getBannerHtml())
              .categoryId(entity.getCategoryId())
              .imageUrl(entity.getImageUrl())
              .linkUrl(entity.getLinkUrl())
              .type(entity.getBannerType())
              .catalogorproduct(entity.getCatalogOrProduct())
              .topic(entity.getTopic());

          retList.add(banner);
        }
      }
    }

    if (retList != null && retList.size() > 0) {

      Collections.sort(retList, (lhs, rhs) -> {
        if (lhs.displayOrder() < rhs.displayOrder()) return -1;
        if (lhs.displayOrder() == rhs.displayOrder()) return 0;
        return 1;
      });
    }

    return retList;
  }

  public SettingsItem transform(SettingsResponseEntity entity) {
    SettingsItem item = null;
    if (entity != null) {
      item = new SettingsItem();
      item.imageServer(entity.ImageServer());
    }

    return item;
  }
}
