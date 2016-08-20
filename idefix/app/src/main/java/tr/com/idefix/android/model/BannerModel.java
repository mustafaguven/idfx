package tr.com.idefix.android.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Created on 9.6.15.
 */
@Getter @Setter @Accessors(chain = true, fluent = true) public class BannerModel extends BaseModel {

  private String bannerHtml;
  private String topic;
  private String imageUrl;
  private String linkUrl;
  private String type;
  private String catalogorproduct;
}
