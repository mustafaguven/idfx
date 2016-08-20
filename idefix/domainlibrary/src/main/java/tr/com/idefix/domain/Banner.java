package tr.com.idefix.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 9.6.15.
 */
@Getter @Setter @Accessors(chain = true, fluent = true) public class Banner {
  private Integer categoryId;
  private Integer widgetzoneId;
  private String bannerHtml;
  private Integer displayOrder;
  private Boolean active;
  private Long startDate;
  private Long endDate;
  private String topic;
  private String imageUrl;
  private String linkUrl;
  private String type;
  private String catalogorproduct;
}
