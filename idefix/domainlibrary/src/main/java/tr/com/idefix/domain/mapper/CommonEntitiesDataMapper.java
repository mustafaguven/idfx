package tr.com.idefix.domain.mapper;

import java.util.ArrayList;
import java.util.List;
import tr.com.idefix.data.entity.CityEntity;
import tr.com.idefix.data.entity.CountryEntity;
import tr.com.idefix.data.entity.StoreEntity;
import tr.com.idefix.data.entity.StoreResponseEntity;
import tr.com.idefix.domain.City;
import tr.com.idefix.domain.Country;
import tr.com.idefix.domain.Store;

public class CommonEntitiesDataMapper {

  public List<Country> transform(List<CountryEntity> listEntity) {
    List<Country> retList = null;
    if (listEntity != null) {
      retList = new ArrayList<>(listEntity.size());

      for (CountryEntity entity : listEntity) {

        Country country = new Country();
        country.id(entity.getValue()).text(entity.getText());
        retList.add(country);
      }
    }

    return retList;
  }

  public List<Store> transform(StoreResponseEntity entity) {

    List<Store> retList = null;
    if (entity != null && entity.getStores() != null && entity.getStores().size() > 0) {
      retList = new ArrayList<>(entity.getStores().size());

      for (StoreEntity storeEntity : entity.getStores()) {

        Store store = new Store();

        store.cityName(storeEntity.getCityName())
            .coordinates(storeEntity.getCoordinates())
            .description(storeEntity.getDescription())
            .storeName(storeEntity.getStoreName());

        retList.add(store);
      }
    }

    return retList;
  }

  public List<City> transformCity(List<CityEntity> listEntity) {
    List<City> retList = null;
    if (listEntity != null) {
      retList = new ArrayList<>(listEntity.size());

      for (CityEntity entity : listEntity) {

        City city = new City();
        city.id(entity.getId()).text(entity.getName());
        retList.add(city);
      }
    }

    return retList;
  }
}
