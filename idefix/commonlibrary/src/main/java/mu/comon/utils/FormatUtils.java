package mu.comon.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 10.3.15.
 */
public class FormatUtils {
  public static String getTrFormat(Double aDouble) {
    if (aDouble != null) {
      DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
      decimalFormatSymbols.setDecimalSeparator(',');
      decimalFormatSymbols.setGroupingSeparator('.');
      DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", decimalFormatSymbols);

      return decimalFormat.format(aDouble);
    }
    return null;
  }

  public static String getTrFormatWithCurrency(Double aDouble) {
    return getTrFormat(aDouble) + " TL";
  }

  public static boolean isEmailValid(String email) {
    boolean isValid = false;

    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
    CharSequence inputStr = email;

    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(inputStr);
    if (matcher.matches()) {
      isValid = true;
    }
    return isValid;
  }

  public static String isValidPhone(String phoneNumber) {
    String patternTemp = "xxx-xxx-xx-xx";
    Pattern pattern = Pattern.compile("\\d{3}-\\d{3}-\\d{2}-\\d{2}");
    Matcher matcher = pattern.matcher(phoneNumber);
    String validNo = null;
    if (phoneNumber.contentEquals(patternTemp)) return "";

    if (matcher.matches()) {
      validNo = phoneNumber.replaceAll("[^\\d]", "");
    }
    return validNo;
  }
}
