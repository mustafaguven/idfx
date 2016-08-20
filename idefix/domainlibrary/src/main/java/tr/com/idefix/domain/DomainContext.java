package tr.com.idefix.domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import javax.inject.Inject;
import javax.inject.Singleton;
import mu.comon.cache.MemoryCache;
import mu.comon.cache.SharedPreferencesCache;
import timber.log.Timber;

import static tr.com.idefix.domain.constants.Keys.COUNTRIES_CACHE_KEY;
import static tr.com.idefix.domain.constants.Keys.IS_BARCODE_INFO_SHOWN;
import static tr.com.idefix.domain.constants.Keys.LOGGEDIN_USER_CACHE_KEY;
import static tr.com.idefix.domain.constants.Keys.LOGGEDIN_USER_INFO_CACHE_KEY;
import static tr.com.idefix.domain.constants.Keys.PERMISSION_ASKED;
import static tr.com.idefix.domain.constants.Keys.STORES_CACHE_KEY;

/**
 * Created by utkan on 02/09/15.
 */
@Singleton public final class DomainContext {

  //<editor-fold desc="Fields">
  static MemoryCache cache;
  static Type type_countries;
  static Type type_cities;
  SharedPreferences preferences;
  private Type type_storeList;
  private Type type_loggedInUser;
  private LoggedInUser loggedInUser;
  private CustomerInfo customerInfo;
  private Wish wish;
  private Alarm alarm;
  private int removableWishItemCount;
  private int removableAlarmItemCount;
  private int movableWishItemCount;
  private int movableAlarmItemCount;
  private Integer selectedTopCategoryID;
  private String selectedTopCategoryName;
  private SettingsItem settingsItem;

  private boolean isBarcodeShown;
  //</editor-fold>

  @Inject public DomainContext(Context context) {

    cache = MemoryCache.getInstance();
    SharedPreferencesCache spCache = SharedPreferencesCache.getInstance(context);
    preferences = PreferenceManager.getDefaultSharedPreferences(context);

    cache.setNextCacher(spCache);
  }

  public void cacheCountries(List<Country> countries) {
    cache.putCache(COUNTRIES_CACHE_KEY, countries);
  }

  public void cacheCitiesByCountryId(int id, List<City> cities) {
    cache.putCache(String.format("CITY_%s", id), cities);
  }

  public List<Banner> validateBannerCache(List<Banner> bannerList) {

    if (bannerList != null) {
      ListIterator<Banner> iter = bannerList.listIterator();
      Date current = new Date();
      while (iter.hasNext()) {
        Banner banner = iter.next();
        if (banner.endDate() != null && banner.endDate() < current.getTime()) {
          iter.remove();
        }
      }
    }

    return bannerList;
  }

  public void cacheStores(List<Store> stores) {
    cache.putCache(STORES_CACHE_KEY, stores);
  }

  //////////// GETTERS ////////////

  public LoggedInUser getLoggedInUser() {
    if (loggedInUser == null) {

      if (type_loggedInUser == null) {
        type_loggedInUser = new TypeToken<LoggedInUser>() {
        }.getType();
      }
      loggedInUser = getCache().getCacheByType(LOGGEDIN_USER_CACHE_KEY, type_loggedInUser);

      if (loggedInUser != null && loggedInUser.rememberMe()) {
        return loggedInUser;
      }
    }
    return loggedInUser;
  }

  public void setLoggedInUser(LoggedInUser l) {
    this.loggedInUser = l;

    if (loggedInUser != null) {
      if (loggedInUser.rememberMe()) {
        getCache().putCache(LOGGEDIN_USER_CACHE_KEY, loggedInUser);
      }
    } else {
      Timber.w("setLoggedInUser null");
      getCache().removeCache(LOGGEDIN_USER_CACHE_KEY);
    }
  }

  public List<Store> getCachedStores() {
    if (type_storeList == null) {
      type_storeList = new TypeToken<List<Store>>() {
      }.getType();
    }
    return cache.getCacheByType(STORES_CACHE_KEY, type_storeList);
  }

  public List<Country> getCachedCountries() {

    if (type_countries == null) {
      type_countries = new TypeToken<List<Country>>() {
      }.getType();
    }
    return cache.getCacheByType(COUNTRIES_CACHE_KEY, type_countries);
  }

  public List<City> getCachedCitiesByCountryId(int id) {

    if (type_cities == null) {
      type_cities = new TypeToken<List<City>>() {
      }.getType();
    }
    return cache.getCacheByType(String.format("CITY_%s", id), type_cities);
  }

  //////////// GETTERS ////////////
  //////////// SETTERS ////////////

  public CustomerInfo getCustomerInfo() {

    if (customerInfo == null) {
      customerInfo = getCache().getCache(LOGGEDIN_USER_INFO_CACHE_KEY);
    }
    return customerInfo;
  }

  public void setCustomerInfo(CustomerInfo ci) {

    this.customerInfo = ci;

    if (customerInfo != null) {
      getCache().putCache(LOGGEDIN_USER_INFO_CACHE_KEY, customerInfo);
    } else {
      customerInfo = null;
      getCache().removeCache(LOGGEDIN_USER_INFO_CACHE_KEY);
    }
  }

  public MemoryCache getCache() {
    return cache;
  }

  public <T> T getCache(String key, Class<?> clazz) {
    return getCache().getCache(key, clazz);
  }

  public void setWishes(Wish wish) {
    this.wish = wish;
  }

  public Wish getCachedWish() {
    return wish;
  }

  public void setAlarms(Alarm a) {
    this.alarm = a;
  }

  public Alarm getCachedAlarm() {
    return alarm;
  }

  public void removeSkuFromWishCache(String sku) {
    if (wish != null && wish.items() != null) {
      int index = -1;
      for (int i = 0; i < wish.items().size(); i++) {
        if (wish.items().get(i).sku().equals(sku)) {
          index = i;
          break;
        }
      }

      if (index != -1) {
        wish.items().remove(index);
      }
    }
  }

  public void removeSkuFromAlarmCache(String sku) {
    if (alarm != null && alarm.items() != null) {
      int index = -1;
      for (int i = 0; i < alarm.items().size(); i++) {
        if (alarm.items().get(i).sku().equals(sku)) {
          index = i;
          break;
        }
      }

      if (index != -1) {
        alarm.items().remove(index);
      }
    }
  }

  public int getRemovableWishItemCount() {
    return removableWishItemCount;
  }

  public void setRemovableWishItemCount(int count) {
    this.removableWishItemCount = count;
  }

  public int getRemovableAlarmItemCount() {
    return removableAlarmItemCount;
  }

  public void setRemovableAlarmItemCount(int count) {
    this.removableAlarmItemCount = count;
  }

  public int getMovableWishItemCount() {
    return movableWishItemCount;
  }

  public void setMovableWishItemCount(int count) {
    this.movableWishItemCount = count;
  }

  public int getMovableAlarmItemCount() {
    return movableAlarmItemCount;
  }

  public void setMovableAlarmItemCount(int count) {
    this.movableAlarmItemCount = count;
  }

  public void setSelectedTopCategory(Integer id, String name) {
    selectedTopCategoryID = id;
    selectedTopCategoryName = name;
  }

  public Integer getSelectedTopCategoryID() {
    return selectedTopCategoryID;
  }

  public void setSelectedTopCategoryID(Integer selectedTopCategoryID) {
    this.selectedTopCategoryID = selectedTopCategoryID;
  }

  public String getSelectedTopCategoryName() {
    return selectedTopCategoryName;
  }

  public void setSelectedTopCategoryName(String selectedTopCategoryName) {
    this.selectedTopCategoryName = selectedTopCategoryName;
  }

  public SettingsItem getSettingsItem() {
    return this.settingsItem;
  }

  public void setSettingsItem(SettingsItem settingsItem) {
    this.settingsItem = settingsItem;
  }

/*    public Boolean isPermissionAsked() {
        Type type_boolean = new TypeToken<Boolean>() {
        }.getType();

        return cache.getCacheByType(Keys.PERMISSION_ASKED, type_boolean);
    }

    public void setPermissionAsked(Boolean val) {
        cache.putCache(Keys.PERMISSION_ASKED, val);
    }*/

  public Boolean isPermissionAsked() {
    return preferences.getBoolean(PERMISSION_ASKED, false);
  }

  public void setPermissionAsked(Boolean permissionAsked) {
    SharedPreferences.Editor editor = preferences.edit();
    editor.putBoolean(PERMISSION_ASKED, permissionAsked);
    editor.commit();
  }

  public void setBarcodeInfoShown(boolean value) {
    SharedPreferences.Editor editor = preferences.edit();
    editor.putBoolean(IS_BARCODE_INFO_SHOWN, value);
    editor.commit();
  }

  public Boolean isBarcodeInfoShown() {
    return preferences.getBoolean(IS_BARCODE_INFO_SHOWN, false);
  }

  //////////// SETTERS ////////////
}
