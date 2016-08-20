package tr.com.idefix.data.repository.datasource.splash;

import java.util.List;

import rx.Observable;
import tr.com.idefix.data.entity.BannerResponseEntity;
import tr.com.idefix.data.entity.MainCategoryEntity;
import tr.com.idefix.data.entity.SettingsResponseEntity;

/**
 * Interface that represents a data store from where data is retrieved.
 */
public interface SplashDataStore {

    Observable<List<MainCategoryEntity>> getMainCategories();

    Observable<BannerResponseEntity> getBanners();

    Observable<SettingsResponseEntity> getSettings();

}
