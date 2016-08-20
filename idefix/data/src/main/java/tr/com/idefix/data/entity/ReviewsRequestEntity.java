package tr.com.idefix.data.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 10.18.15.
 */
@Setter
@Getter
@Accessors(chain = true, fluent = true)
public class ReviewsRequestEntity {
    int productId;
    int take;
}
