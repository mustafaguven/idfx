package tr.com.idefix.android.azure;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by mustafaguven on 07.01.2016.
 */
class ConnectionStringParser {
  private String _value;
  private int _pos;
  private ConnectionStringParser.ParserState _state;

  private ConnectionStringParser(String value) {
    this._value = value;
    this._pos = 0;
    this._state = ConnectionStringParser.ParserState.EXPECT_KEY;
  }

  public static Map<String, String> parse(String connectionString) {
    ConnectionStringParser connectionStringParser = new ConnectionStringParser(connectionString);
    return connectionStringParser.parse();
  }

  private Map<String, String> parse() {
    HashMap result = new HashMap();
    String key = null;
    String value = null;

    while (true) {
      this.skipWhitespaces();
      if (this._pos == this._value.length()
          && this._state != ConnectionStringParser.ParserState.EXPECT_VALUE) {
        if (this._state == ConnectionStringParser.ParserState.EXPECT_ASSIGNMENT) {
          throw this.createException(this._pos, "Missing character %s", new Object[] { "=" });
        }

        return result;
      }

      switch (this._state.ordinal() + 1) {
        case 1:
          key = this.extractKey();
          this._state = ConnectionStringParser.ParserState.EXPECT_ASSIGNMENT;
          break;
        case 2:
          this.skipOperator('=');
          this._state = ConnectionStringParser.ParserState.EXPECT_VALUE;
          break;
        case 3:
          value = this.extractValue();
          this._state = ConnectionStringParser.ParserState.EXPECT_SEPARATOR;
          result.put(key, value);
          key = null;
          value = null;
          break;
        default:
          this.skipOperator(';');
          this._state = ConnectionStringParser.ParserState.EXPECT_KEY;
      }
    }
  }

  private IllegalArgumentException createException(
      int position, String errorString, Object... args
  ) {
    errorString = String.format(errorString, args);
    errorString = String.format("Error parsing connection string: %s. Position %s",
        new Object[] { errorString, Integer.valueOf(this._pos) });
    errorString = String.format("Invalid connection string: %s.", new Object[] { errorString });
    return new IllegalArgumentException(errorString);
  }

  private void skipWhitespaces() {
    while (this._pos < this._value.length() && Character.isWhitespace(
        this._value.charAt(this._pos))) {
      ++this._pos;
    }
  }

  private String extractKey() {
    int pos = this._pos;
    char c = this._value.charAt(this._pos++);
    String text;
    if (c != 34 && c != 39) {
      label28:
      {
        if (c != 59 && c != 61) {
          while (true) {
            if (this._pos < this._value.length()) {
              c = this._value.charAt(this._pos);
              if (c != 61) {
                ++this._pos;
                continue;
              }
            }

            text = this._value.substring(pos, this._pos).trim();
            break label28;
          }
        }

        throw this.createException(pos, "Missing key", new Object[0]);
      }
    } else {
      text = this.extractString(c);
    }

    if (text.length() == 0) {
      throw this.createException(pos, "Empty key", new Object[0]);
    } else {
      return text;
    }
  }

  private String extractString(char quote) {
    int pos;
    for (pos = this._pos;
        this._pos < this._value.length() && this._value.charAt(this._pos) != quote; ++this._pos) {
      ;
    }

    if (this._pos == this._value.length()) {
      throw this.createException(
          this._pos, "Missing character", new Object[] { Character.valueOf(quote) });
    } else {
      return this._value.substring(pos, this._pos++);
    }
  }

  private void skipOperator(char operatorChar) {
    if (this._value.charAt(this._pos) != operatorChar) {
      throw this.createException(
          this._pos, "Missing character", new Object[] { Character.valueOf(operatorChar) });
    } else {
      ++this._pos;
    }
  }

  private String extractValue() {
    String result = "";
    if (this._pos < this._value.length()) {
      char c = this._value.charAt(this._pos);
      if (c != 39 && c != 34) {
        int pos = this._pos;
        boolean flag = false;

        while (this._pos < this._value.length() && !flag) {
          c = this._value.charAt(this._pos);
          if (c == 59) {
            if (this.isStartWithKnownKey()) {
              flag = true;
            } else {
              ++this._pos;
            }
          } else {
            ++this._pos;
          }
        }

        result = this._value.substring(pos, this._pos).trim();
      } else {
        ++this._pos;
        result = this.extractString(c);
      }
    }

    return result;
  }

  private boolean isStartWithKnownKey() {
    Locale defaultLocale = Locale.getDefault();
    return this._value.length() <= this._pos + 1 || this._value.substring(this._pos + 1)
        .toLowerCase(defaultLocale)
        .startsWith("endpoint") || this._value.substring(this._pos + 1)
        .toLowerCase(defaultLocale)
        .startsWith("stsendpoint") || this._value.substring(this._pos + 1)
        .toLowerCase(defaultLocale)
        .startsWith("sharedsecretissuer") || this._value.substring(this._pos + 1)
        .toLowerCase(defaultLocale)
        .startsWith("sharedsecretvalue") || this._value.substring(this._pos + 1)
        .toLowerCase(defaultLocale)
        .startsWith("sharedaccesskeyname") || this._value.substring(this._pos + 1)
        .toLowerCase(defaultLocale)
        .startsWith("sharedaccesskey");
  }

  private static enum ParserState {
    EXPECT_KEY,
    EXPECT_ASSIGNMENT,
    EXPECT_VALUE,
    EXPECT_SEPARATOR;

    private ParserState() {
    }
  }
}
