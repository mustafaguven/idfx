package tr.com.idefix.data.repository.datasource.splash;


import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tr.com.idefix.data.entity.BannerResponseEntity;
import tr.com.idefix.data.entity.EmptyRequestBody;
import tr.com.idefix.data.entity.MainCategoryEntity;
import tr.com.idefix.data.entity.SettingsResponseEntity;
import tr.com.idefix.data.net.RestApiCatalog;
import tr.com.idefix.data.net.RestApiCatalogService;
import tr.com.idefix.data.net.RestApiCommon;
import tr.com.idefix.data.net.RestApiCommonService;

/**
 * {@link SplashDataStore} implementation based on connections to the api (Cloud).
 */
public class CloudSplashDataStore implements SplashDataStore {

    private final RestApiCatalog restApi;
    private final RestApiCommon restApiCommon;


    public CloudSplashDataStore(RestApiCatalogService restApiCatalogService, RestApiCommonService restApiCommonService) {
        this.restApi = restApiCatalogService;
        this.restApiCommon = restApiCommonService;
    }

    @Override
    public Observable<List<MainCategoryEntity>> getMainCategories() {

        return restApi
                .getMainCategories(new EmptyRequestBody())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    @Override
    public Observable<BannerResponseEntity> getBanners() {
        return restApi
                .getBanners(new EmptyRequestBody())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    @Override
    public Observable<SettingsResponseEntity> getSettings() {
        return restApiCommon
                .getSettings(new EmptyRequestBody())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }
}
