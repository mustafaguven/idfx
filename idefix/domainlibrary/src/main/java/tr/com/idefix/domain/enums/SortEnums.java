package tr.com.idefix.domain.enums;

/**
 * Created by utkan on 30/10/15.
 */
public enum SortEnums {

  PRICE_DESC("Fiyata Göre Azalan", "price", "desc"),
  PRICE_ASC("Fiyata Göre Artan", "price", "asc"),

  COMMENT_COUNT_DESC("En Çok Yorum Alan", "commentcount", "desc"),

  SOLD_COUNT_DESC("En Çok Satan", "soldcount", "desc"),

  CREATE_DATE_DESC("Yeni", "createdate", "desc"),
  CREATE_DATE_ASC("Eski", "createdate", "asc"),

  NAME_SORTABLE_ASC("A - Z", "name.sortable", "asc"),
  NAME_SORTABLE_DESC("Z - A", "name.sortable", "desc"),

  DISCOUNT_DESC("İndirime göre azalan", "discount", "desc"),
  DISCOUNT_ASC("İndirime göre artan", "discount", "asc");

  String text;
  String sortField;
  String sortOrder;

  SortEnums(String text, String sortField, String sortOrder) {
    this.text = text;
    this.sortField = sortField;
    this.sortOrder = sortOrder;
  }

  public String getText() {
    return text;
  }

  public String getSortField() {
    return sortField;
  }

  public String getSortOrder() {
    return sortOrder;
  }
}
