package tr.com.idefix.data.repository;

import java.util.List;

import rx.Observable;
import tr.com.idefix.data.entity.BannerResponseEntity;
import tr.com.idefix.data.entity.MainCategoryEntity;
import tr.com.idefix.data.entity.SettingsResponseEntity;

/**
 * Created by utkan on 05/09/15.
 */
public interface ISplashRepository {

    Observable<List<MainCategoryEntity>> getMainCategories();

    Observable<BannerResponseEntity> getBanners();

    Observable<SettingsResponseEntity> getSettings();

}
