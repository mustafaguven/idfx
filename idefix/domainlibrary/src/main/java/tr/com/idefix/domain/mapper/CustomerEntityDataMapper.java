package tr.com.idefix.domain.mapper;

import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;
import tr.com.idefix.data.entity.AddItemToAlarmListResponseEntity;
import tr.com.idefix.data.entity.AddItemToWishListResponseEntity;
import tr.com.idefix.data.entity.AlarmItemEntity;
import tr.com.idefix.data.entity.AlarmListResponseEntity;
import tr.com.idefix.data.entity.BasketCountEntity;
import tr.com.idefix.data.entity.CustomerInfoEntity;
import tr.com.idefix.data.entity.ItemEntity;
import tr.com.idefix.data.entity.WishListResponseEntity;
import tr.com.idefix.domain.Alarm;
import tr.com.idefix.domain.BasketCount;
import tr.com.idefix.domain.CustomerInfo;
import tr.com.idefix.domain.DRItem;
import tr.com.idefix.domain.Wish;

public class CustomerEntityDataMapper {

  public BasketCount transform(BasketCountEntity basketCountEntity) {

    BasketCount basketCount = new BasketCount();

    if (basketCountEntity != null &&
        basketCountEntity.success() != null &&
        basketCountEntity.success()) {
      basketCount.cartItemCount(basketCountEntity.cartItemCount());
    }
    return basketCount;
  }

  public CustomerInfo transform(CustomerInfoEntity customerInfoEntity) {
    Timber.i("transform");
    if (customerInfoEntity != null) {
      CustomerInfo customerInfo = new CustomerInfo();

      customerInfo.username(customerInfoEntity.getUsername())
          .lastName(customerInfoEntity.getLastName())
          .firstName(customerInfoEntity.getFirstName())
          .email(customerInfoEntity.getEmail())
          .gender(String.valueOf(customerInfoEntity.getGender()).trim())
          .dateofbirthday(customerInfoEntity.getDateOfBirthDay() == null ? 0
              : customerInfoEntity.getDateOfBirthDay())
          .dateofbirthmonth(customerInfoEntity.getDateOfBirthMonth() == null ? 0
              : customerInfoEntity.getDateOfBirthMonth() - 1)
          .dateofbirthyear(customerInfoEntity.getDateOfBirthYear() == null ? 1900
              : customerInfoEntity.getDateOfBirthYear())
          .countryId(customerInfoEntity.getCountryId() == null ? 77
              : customerInfoEntity.getCountryId()) //turkiye
          .cityId(customerInfoEntity.getCity() == null ? "27249"
              : customerInfoEntity.getCity()) //istanbul
          .phone(customerInfoEntity.getPhone())
          .gsm(customerInfoEntity.getGSM())
          .newsletter(customerInfoEntity.getNewsletter());
      return customerInfo;
    }
    return null;
  }

  public Wish transform(AddItemToWishListResponseEntity entity) {
    if (entity != null &&
        entity.wishList() != null &&
        entity.wishList().items() != null &&
        entity.wishList().items().size() > 0) {

      Wish wish = new Wish();

      List<DRItem> drItems = new ArrayList<>(entity.wishList().items().size());

      for (ItemEntity itemEntity : entity.wishList().items()) {

        drItems.add(new DRItem().imageUrl(itemEntity.picture().imageUrl())
            .id(itemEntity.id())
            .unitPrice(itemEntity.unitPrice())
            .name(itemEntity.productName())
            .productID(itemEntity.productId())
            .categoryName(itemEntity.categoryName())
            .sku(itemEntity.sku()));
      }

      wish.items(drItems);

      return wish;
    }
    return null;
  }

  public Wish transform(WishListResponseEntity entity) {
    if (entity != null && entity.itemList() != null && entity.itemList().size() > 0) {
      Wish wish = new Wish();

      List<DRItem> drItems = new ArrayList<>(entity.itemList().size());

      for (ItemEntity itemEntity : entity.itemList()) {

        drItems.add(new DRItem().imageUrl(itemEntity.picture().imageUrl())
            .id(itemEntity.id())
            .unitPrice(itemEntity.unitPrice())
            .name(itemEntity.productName())
            .productID(itemEntity.productId())
            .categoryName(itemEntity.categoryName())
            .sku(itemEntity.sku())
            .sale_status(itemEntity.saleStatus()));
      }

      wish.items(drItems);

      return wish;
    }
    return null;
  }

  public Alarm transform(AddItemToAlarmListResponseEntity entity) {
    if (entity != null &&
        entity.alarmList() != null &&
        entity.alarmList().items() != null &&
        entity.alarmList().items().size() > 0) {

      Alarm alarm = new Alarm();

      List<DRItem> drItems = new ArrayList<>(entity.alarmList().items().size());

      for (AlarmItemEntity itemEntity : entity.alarmList().items()) {

        drItems.add(new DRItem().imageUrl(itemEntity.picture().imageUrl())
            .id(itemEntity.id())
            .name(itemEntity.productName())
            .productID(itemEntity.productId())
            .categoryName(itemEntity.categoryName())
            .unitPrice(itemEntity.unitPrice())
            .endDate(itemEntity.endDate())
            .customerEnteredPrice(itemEntity.customerEnteredPrice())
            .unitPrice(itemEntity.unitPrice())
            .sku(itemEntity.sku()));
      }

      alarm.items(drItems);

      return alarm;
    }
    return null;
  }

  public Alarm transform(AlarmListResponseEntity entity) {
    if (entity != null &&
        entity.alarmList() != null &&
        entity.alarmList().items() != null &&
        entity.alarmList().items().size() > 0) {
      Alarm alarm = new Alarm();

      List<DRItem> drItems = new ArrayList<>(entity.alarmList().items().size());

      for (AlarmItemEntity itemEntity : entity.alarmList().items()) {

        drItems.add(new DRItem().imageUrl(itemEntity.picture().imageUrl())
            .id(itemEntity.id())
            .name(itemEntity.productName())
            .productID(itemEntity.productId())
            .categoryName(itemEntity.categoryName())
            .unitPrice(itemEntity.unitPrice())
            .endDate(itemEntity.endDate())
            .customerEnteredPrice(itemEntity.customerEnteredPrice())
            .unitPrice(itemEntity.unitPrice())
            .sku(itemEntity.sku()));
      }

      alarm.items(drItems);

      return alarm;
    }
    return null;
  }
}
