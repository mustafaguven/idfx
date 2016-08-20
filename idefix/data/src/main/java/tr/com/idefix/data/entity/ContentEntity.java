package tr.com.idefix.data.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by mustafaguven on 24.10.2015.
 */
@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class ContentEntity {
    int page;
    String sortfield;
    String sortorder;
    int size;
    String term;
    String categoryid;
    String brandIds;
    String mediatypes;
    double minPrice;
    double maxPrice;
    String catalogId;
    String parentid;

}
