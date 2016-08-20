package tr.com.idefix.domain.interactor;

import java.util.List;
import tr.com.idefix.domain.Banner;
import tr.com.idefix.domain.MainCategory;
import tr.com.idefix.domain.SettingsItem;

/**
 * Created by utkan on 05/09/15.
 */
public interface ISplashInterActor extends IInterActor {
  void getMainCategories(DefaultSubscriber<List<MainCategory>> defaultSubscriber);

  void getBanners(DefaultSubscriber<List<Banner>> defaultSubscriber);

  void getSettings(DefaultSubscriber<SettingsItem> subscriber);
}
