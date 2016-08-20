package mu.comon.utils;

import com.google.gson.Gson;

/**
 * Created by utkan on 08/09/15.
 */
public class JsonUtils {

  public String getJson(Object src) {
    String json;

    json = new Gson().toJson(src);
    return json;
  }

  public <T> T getObjectFrom(String json, Class<T> clazz) {
    return new Gson().fromJson(json, clazz);
  }
}
