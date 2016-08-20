package tr.com.idefix.android.internal.di.components;

import dagger.Component;
import tr.com.idefix.android.internal.di.PerActivity;
import tr.com.idefix.android.internal.di.modules.ActivityModule;
import tr.com.idefix.android.internal.di.modules.UserProfileActivityModule;
import tr.com.idefix.android.view.activities.UserProfileActivity;
import tr.com.idefix.android.view.fragments.MyInformationFragment;
import tr.com.idefix.android.view.fragments.MyListsFragment;
import tr.com.idefix.android.view.fragments.MyOrdersFragment;

/**
 * Created by mustafaguven on 13.10.2015.
 */

@PerActivity @Component(dependencies = ApplicationComponent.class, modules = {
    ActivityModule.class, UserProfileActivityModule.class
}) public interface UserProfileActivityComponent extends ActivityComponent {
  void inject(UserProfileActivity userProfileActivity);

  void injectMyInformationFragment(MyInformationFragment myInformationFragment);

  void injectMyOrdersFragment(MyOrdersFragment myOrdersFragment);

  void injectMyListFragment(MyListsFragment myListsFragment);
}
