package tr.com.idefix.domain.mapper;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tr.com.idefix.data.entity.BaseItemEntity;
import tr.com.idefix.data.entity.BasketCountEntity;
import tr.com.idefix.data.entity.BestSellersResponseEntity;
import tr.com.idefix.data.entity.BrandEntity;
import tr.com.idefix.data.entity.CategoryEntity;
import tr.com.idefix.data.entity.CategoryTreeResponseEntity;
import tr.com.idefix.data.entity.DictionaryEntity;
import tr.com.idefix.data.entity.FilterCategoryProductsResponseEntity;
import tr.com.idefix.data.entity.GroupedProductAttributeEntity;
import tr.com.idefix.data.entity.MediaTypeEntity;
import tr.com.idefix.data.entity.NewestResponseEntity;
import tr.com.idefix.data.entity.OtherProductsResponseEntity;
import tr.com.idefix.data.entity.PictureModelEntity;
import tr.com.idefix.data.entity.PriceEntity;
import tr.com.idefix.data.entity.ProductAttributeEntity;
import tr.com.idefix.data.entity.ProductDetailResponseEntity;
import tr.com.idefix.data.entity.ProductEntity;
import tr.com.idefix.data.entity.ProductOtherEntity;
import tr.com.idefix.data.entity.ProductPersonEntity;
import tr.com.idefix.data.entity.ReviewEntity;
import tr.com.idefix.data.entity.ReviewListResponseEntity;
import tr.com.idefix.data.entity.SearchSubResponseEntity;
import tr.com.idefix.data.net.RestApiBaseService;
import tr.com.idefix.domain.BasketCount;
import tr.com.idefix.domain.BestSeller;
import tr.com.idefix.domain.Category;
import tr.com.idefix.domain.CategoryTree;
import tr.com.idefix.domain.DRItem;
import tr.com.idefix.domain.FilterItem;
import tr.com.idefix.domain.FilteredProduct;
import tr.com.idefix.domain.GroupedProductAttribute;
import tr.com.idefix.domain.Picture;
import tr.com.idefix.domain.Product;
import tr.com.idefix.domain.ProductAttribute;
import tr.com.idefix.domain.ProductOther;
import tr.com.idefix.domain.ProductPerson;
import tr.com.idefix.domain.Review;
import tr.com.idefix.domain.TheNewest;
import tr.com.idefix.domain.constants.FilterMap;

import static tr.com.idefix.data.net.RestApiBaseService.API_BASE_IMAGE_URL;

public class CatalogEntityDataMapper {

  public BasketCount transform(BasketCountEntity basketCountEntity) {

    BasketCount basketCount = new BasketCount();

    if (basketCountEntity != null &&
        basketCountEntity.success() != null &&
        basketCountEntity.success()) {
      basketCount.cartItemCount(basketCountEntity.cartItemCount());
    }
    return basketCount;
  }

  public CategoryTree transform(CategoryTreeResponseEntity categoryTreeResponseEntity) {

    CategoryTree categoryTree = null;

    if (categoryTreeResponseEntity != null) {

      categoryTree = new CategoryTree();

      if (categoryTreeResponseEntity.category() != null) {
        CategoryEntity catEntity = categoryTreeResponseEntity.category();
        Category category = new Category();

        String seo = catEntity.seo();
        if (seo.lastIndexOf("/") > -1) {
          seo = seo.substring(0, seo.lastIndexOf("/"));
        }

        category.id(catEntity.id())
            .bottom(catEntity.bottom())
            .documentCount(catEntity.documentCount())
            .level(catEntity.level())
            .name(catEntity.name())
            .parentId(catEntity.parentId())
            .parentPath(catEntity.parentPath())
            .seo(seo);
        categoryTree.category(category);
      }

      if (categoryTreeResponseEntity.categoryTree() != null) {
        List<CategoryEntity> list = categoryTreeResponseEntity.categoryTree();
        ArrayList<Category> categoryList = new ArrayList<>();
        for (CategoryEntity catEntity : list) {

          Category category = new Category();

          categoryList.add(category.id(catEntity.id())
              .bottom(catEntity.bottom())
              .documentCount(catEntity.documentCount())
              .level(catEntity.level())
              .name(catEntity.name())
              .parentId(catEntity.parentId())
              .parentPath(catEntity.parentPath())
              .seo(catEntity.seo()));
        }
        categoryTree.categoryTree(categoryList);
      }
    }
    return categoryTree;
  }

  public List<BestSeller> transform(BestSellersResponseEntity bestSellersResponseEntity) {

    List<BestSeller> retList = null;

    if (bestSellersResponseEntity != null &&
        bestSellersResponseEntity.mostsold() != null &&
        bestSellersResponseEntity.mostsold().products() != null &&
        bestSellersResponseEntity.mostsold().products().size() > 0) {

      retList = new ArrayList<>();
      //return transform(bestSellersResponseEntity.bestSellers(), BestSeller.class);

      for (ProductOtherEntity item : bestSellersResponseEntity.mostsold().products()) {

        BestSeller retItem = new BestSeller();

        if (retItem != null) {
          retItem.id(item.variationId())
              .name(item.name())
              .imageUrl(API_BASE_IMAGE_URL + item.imageUrl())
              .price(item.price())
              .oldPrice(item.oldPrice())
              .sku(item.stockCode())
              .brandName(item.brandName());
        }

        retList.add(retItem);
      }
    }
    return retList;
  }

  public List<TheNewest> transform(NewestResponseEntity newestResponseEntity) {

    if (newestResponseEntity != null &&
        newestResponseEntity.newestEntities() != null &&
        newestResponseEntity.newestEntities().size() > 0) {

      return transform(newestResponseEntity.newestEntities(), TheNewest.class);
    }

    return null;
  }

  public <K extends DRItem, T extends BaseItemEntity> List<K> transform(
      List<T> baseItemEntities, Class<K> kClass
  ) {

    if (baseItemEntities != null && baseItemEntities.size() > 0) {

      List<K> retList = new ArrayList<>();

      for (T item : baseItemEntities) {

        K retItem = null;

        if (kClass == TheNewest.class) {
          retItem = (K) new TheNewest();
        } else if (kClass == BestSeller.class) {
          retItem = (K) new BestSeller();
        }
        if (retItem != null) {
          retItem.id(item.variationId())
              .name(item.name())
              .imageUrl(API_BASE_IMAGE_URL + item.imageUrl())
              .price(item.price())
              .oldPrice(item.oldPrice())
              .sku(item.stockCode())
              .brandName(item.brandName());
        }

        retList.add(retItem);
      }
      return retList;
    }

    return null;
  }

  public Product transform(ProductDetailResponseEntity productDetailResponseEntity) {
    if (productDetailResponseEntity != null) {

      ProductEntity productEntity = productDetailResponseEntity.product();
      if (productEntity != null) {

        if (!TextUtils.isEmpty(productEntity.productPrice().oldPrice())
            && productEntity.productPrice()
            .oldPrice()
            .equals(productEntity.productPrice().price())) {
          productEntity.productPrice().oldPrice("");
        }
        Product product = new Product();
        ////////////////////////////////////////////////////////////////////////////////////
        List<PictureModelEntity> pics = productEntity.pictureModels();

        if (pics != null && pics.size() > 0) {

          product.pictures(new ArrayList<>());

          for (PictureModelEntity pictureModelEntity : pics) {

            product.pictures()
                .add(new Picture().imageUrl(pictureModelEntity.imageUrl())
                    .fullSizeImageUrl(pictureModelEntity.fullSizeImageUrl()));
          }
        }
        ////////////////////////////////////////////////////////////////////////////////////
        List<ProductAttributeEntity> listAttr = productEntity.productAttributes();

        if (listAttr != null && listAttr.size() > 0) {

          List<ProductAttribute> lpa = new ArrayList<>(listAttr.size());

          for (ProductAttributeEntity productAttributeEntity : listAttr) {
            lpa.add(new ProductAttribute().textPrompt(
                productAttributeEntity.textPrompt().replace("N/A", "")));
          }

          product.productAttributes(lpa);
        }
        ////////////////////////////////////////////////////////////////////////////////////
        String webLink = RestApiBaseService.API_BASE_URL.substring(0,
            RestApiBaseService.API_BASE_URL.length() - 1);
        product.name(productEntity.name())
            .shortDescription(productEntity.shortDescription())
            .fullDescription(productEntity.fullDescription())
            .webLink(webLink + productEntity.seName())
            .id(productEntity.id());

        if (TextUtils.isEmpty(product.shortDescription()) && !TextUtils.isEmpty(
            product.fullDescription())) {
          product.shortDescription(product.fullDescription());
        }
        ////////////////////////////////////////////////////////////////////////////////////

        if (productEntity.groupedProductAttributes() != null
            && productEntity.groupedProductAttributes().size() > 0) {

          List<GroupedProductAttributeEntity> groupeds = productEntity.groupedProductAttributes();

          GroupedProductAttributeEntity groupedProductAttributeEntity = groupeds.get(0);

          if (groupedProductAttributeEntity.oldPriceString()
              .equals(groupedProductAttributeEntity.priceString())) {
            groupedProductAttributeEntity.oldPriceString("");
          }
          product.attributeName(groupedProductAttributeEntity.attributeName())
              .priceString(groupedProductAttributeEntity.priceString())
              .oldPriceString(groupedProductAttributeEntity.oldPriceString())
              .sku(groupedProductAttributeEntity.sku());

          List<GroupedProductAttribute> l = new ArrayList<>(groupeds.size());
          for (GroupedProductAttributeEntity gp : groupeds) {

            if (gp.oldPriceString().equals(gp.priceString())) {
              gp.oldPriceString("");
            }

            l.add(new GroupedProductAttribute().attributeName(gp.attributeName())
                .oldPriceString(gp.oldPriceString())
                .priceString(gp.priceString())
                .productId(gp.productId())
                .sku(gp.sku()));
          }
          product.groupedProductAttributes(l);
        }
        ////////////////////////////////////////////////////////////////////////////////////
        if (productEntity.productPersons() != null && productEntity.productPersons().size() > 0) {
          List<ProductPersonEntity> pers = productEntity.productPersons();
          List<ProductPerson> pers_d = new ArrayList<>(pers.size());
          for (ProductPersonEntity ppe : pers) {
            pers_d.add(new ProductPerson().name(ppe.name())
                .groupName(ppe.groupName())
                .personId(ppe.personId())
                .productId(ppe.productId()));
          }
          product.productPersons(pers_d);
        }
        ////////////////////////////////////////////////////////////////////////////////////
        if (productEntity.productBrands() != null && productEntity.productBrands().size() > 0) {
          product.brand(productEntity.productBrands().get(0).name());
          product.brandId(productEntity.productBrands().get(0).brandId());
        }
        ////////////////////////////////////////////////////////////////////////////////////

        return product;
      }
    }
    return null;
  }

  public List<Review> transform(ReviewListResponseEntity entity) {

    if (entity != null && entity.success() != null && entity.success()) {
      if (entity.reviews() != null && entity.reviews().size() > 0) {
        List<Review> reviews = new ArrayList<>(entity.reviews().size());

        for (ReviewEntity reviewEntity : entity.reviews()) {

          reviews.add(
              new Review().date(reviewEntity.writtenOnStr()).review(reviewEntity.reviewText()));
        }

        return reviews;
      }
    }
    return null;
  }

  public List<ProductOther> transform(
      OtherProductsResponseEntity entity
  ) {

    if (entity != null) {
      if (entity.success() != null &&
          entity.success() &&
          entity.products() != null &&
          entity.products().products() != null &&
          entity.products().products().size() > 0) {
        List<ProductOtherEntity> listEntity = entity.products().products();
        List<ProductOther> retList = new ArrayList<>(listEntity.size());

        for (ProductOtherEntity productOtherEntity : listEntity) {
          retList.add(new ProductOther().imageUrl(
              RestApiBaseService.API_BASE_IMAGE_URL + productOtherEntity.imageUrl())
              .name(productOtherEntity.name())
              .oldPrice(productOtherEntity.oldPrice())
              .price(productOtherEntity.price())
              .sku(productOtherEntity.stockCode())
              .id(productOtherEntity.id()));
        }

        return retList;
      }
    }

    return null;
  }

  public FilteredProduct transform(FilterCategoryProductsResponseEntity entity) {
    if (entity != null) {

      FilteredProduct filteredProduct = new FilteredProduct();

      if (entity.searchResponse() != null) {
        Map<Integer, List<FilterItem>> filterList = new HashMap<>();

        SearchSubResponseEntity responseEntity = entity.searchResponse();

        //                if (responseEntity.categories() != null) {
        //                    {
        //                        List<CategoryEntity> list = responseEntity.categories();
        //                        if (list.size() > 0) {
        //
        //                            filterList
        //                                    .put(
        //                                            FilterMap.FILTER_TYPE_CATEGORY,
        //                                            getFilterItemList(
        //                                                    list,
        //                                                    FilterMap.FILTER_TYPE_CATEGORY));
        //                        }
        //                    }
        //                }

        if (responseEntity.brands() != null) {
          {
            List<BrandEntity> list = responseEntity.brands();
            if (list.size() > 0) {

              filterList.put(FilterMap.FILTER_TYPE_BRAND,
                  getFilterItemList(list, FilterMap.FILTER_TYPE_BRAND));
            }
          }
        }

        if (responseEntity.mediaTypes() != null) {
          {
            List<MediaTypeEntity> list = responseEntity.mediaTypes();
            if (list.size() > 0) {

              filterList.put(FilterMap.FILTER_TYPE_MEDIA_TYPE,
                  getFilterItemList(list, FilterMap.FILTER_TYPE_MEDIA_TYPE));
            }
          }
        }

        if (responseEntity.prices() != null) {

          List<PriceEntity> list = responseEntity.prices();
          if (list.size() > 0) {

            filterList.put(FilterMap.FILTER_TYPE_PRICE,
                getFilterItemList(responseEntity.prices(), FilterMap.FILTER_TYPE_PRICE));
          }
        }

        List<DRItem> products = new ArrayList<>();

        if (responseEntity.products() != null && responseEntity.products().size() > 0) {

          List<ProductOtherEntity> list = responseEntity.products();

          for (ProductOtherEntity entityP : list) {

            DRItem drItem = new DRItem();

            drItem.sku(entityP.stockCode())
                .name(entityP.name())
                .id(entityP.id() == 0 ? entityP.variationId() : entityP.id())
                .productID(entityP.id() == 0 ? entityP.variationId() : entityP.id())
                .imageUrl(RestApiBaseService.API_BASE_IMAGE_URL + entityP.imageUrl())
                .oldPrice(entityP.oldPrice())
                .price(entityP.price());

            products.add(drItem);
          }
        }

        filteredProduct.filters(filterList).hitCount(responseEntity.hitCount()).products(products);
      }

      return filteredProduct;
    }
    return null;
  }

  <T extends DictionaryEntity> List<FilterItem> getFilterItemList(
      List<T> dictionaryEntityList, int type
  ) {

    List<FilterItem> filterItems = new ArrayList<>(dictionaryEntityList.size());

    for (DictionaryEntity entity : dictionaryEntityList) {

      filterItems.add(new FilterItem().name(entity.name()).id(entity.id()).filterType(type));
    }
    return filterItems;
  }
}
