package tr.com.idefix.data.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created by utkan on 03/10/15.
 */
@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class NewestRequestEntity {
    int id;
    int size;
}
