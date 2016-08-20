package tr.com.idefix.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import tr.com.idefix.data.entity.BannerResponseEntity;
import tr.com.idefix.data.entity.MainCategoryEntity;
import tr.com.idefix.data.entity.SettingsResponseEntity;
import tr.com.idefix.data.repository.datasource.splash.SplashDataStore;
import tr.com.idefix.data.repository.datasource.splash.SplashDataStoreFactory;

/**
 * Created by utkan on 05/09/15.
 */
@Singleton
public class SplashDataRepository
        implements ISplashRepository {

    private final SplashDataStoreFactory splashDataStoreFactory;

    @Inject
    public SplashDataRepository() {
        this.splashDataStoreFactory = new SplashDataStoreFactory();
    }

    @Override
    public Observable<List<MainCategoryEntity>> getMainCategories() {
        final SplashDataStore splashDataStore = splashDataStoreFactory.createCloudDataStore();
        return splashDataStore.getMainCategories();
    }

    @Override
    public Observable<BannerResponseEntity> getBanners() {
        final SplashDataStore splashDataStore = splashDataStoreFactory.createCloudDataStore();
        return splashDataStore.getBanners();
    }


    @Override
    public Observable<SettingsResponseEntity> getSettings() {
        final SplashDataStore splashDataStore = splashDataStoreFactory.createCloudDataStore();
        return splashDataStore.getSettings();
    }

}
