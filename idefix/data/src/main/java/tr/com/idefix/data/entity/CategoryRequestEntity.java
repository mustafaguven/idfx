package tr.com.idefix.data.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 9.12.15.
 */
@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class CategoryRequestEntity {
    int id;
}
