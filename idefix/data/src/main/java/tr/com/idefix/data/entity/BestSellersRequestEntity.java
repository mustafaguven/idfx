package tr.com.idefix.data.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 30/09/15.
 */
@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class BestSellersRequestEntity {
    int categoryid;
    int size;
}
