package tr.com.idefix.android.azure;

/**
 * Created by mustafaguven on 07.01.2016.
 */

import android.os.Build;

public final class PnsSpecificRegistrationFactory {
  private static final PnsSpecificRegistrationFactory mInstance;
  private static Registration.RegistrationType mRegistrationType;

  static {
    mRegistrationType = Registration.RegistrationType.gcm;
    mInstance = new PnsSpecificRegistrationFactory();
  }

  private PnsSpecificRegistrationFactory() {
    boolean isAmazondevice = Build.MANUFACTURER.compareToIgnoreCase("Amazon") == 0;
    if (isAmazondevice) {
      mRegistrationType = Registration.RegistrationType.adm;
    }
  }

  public static PnsSpecificRegistrationFactory getInstance() {
    return mInstance;
  }

  public void setRegistrationType(Registration.RegistrationType type) {
    mRegistrationType = type;
  }

  public Registration createNativeRegistration(String notificationHubPath) {
    switch (mRegistrationType.ordinal() + 1) {
      case 2:
        return new GcmNativeRegistration(notificationHubPath);
      case 3:
        return new AdmNativeRegistration(notificationHubPath);
      case 4:
        return new BaiduNativeRegistration(notificationHubPath);
      default:
        throw new AssertionError("Ivalid registration type!");
    }
  }

  public TemplateRegistration createTemplateRegistration(String notificationHubPath) {
    switch (mRegistrationType.ordinal() + 1) {
      case 2:
        return new GcmTemplateRegistration(notificationHubPath);
      case 3:
        return new AdmTemplateRegistration(notificationHubPath);
      case 4:
        return new BaiduTemplateRegistration(notificationHubPath);
      default:
        throw new AssertionError("Invalid registration type!");
    }
  }

  public boolean isTemplateRegistration(String xml) {
    String tempelateRegistrationCustomNode;
    switch (mRegistrationType.ordinal() + 1) {
      case 2:
        tempelateRegistrationCustomNode = "GcmTemplateRegistrationDescription";
        break;
      case 3:
        tempelateRegistrationCustomNode = "AdmTemplateRegistrationDescription";
        break;
      case 4:
        tempelateRegistrationCustomNode = "BaiduTemplateRegistrationDescription";
        break;
      default:
        throw new AssertionError("Invalid registration type!");
    }

    return xml.contains("<" + tempelateRegistrationCustomNode);
  }

  public String getPNSHandleFieldName() {
    switch (mRegistrationType.ordinal() + 1) {
      case 2:
        return "GcmRegistrationId";
      case 3:
        return "AdmRegistrationId";
      case 4:
        return "BaiduUserId-BaiduChannelId";
      default:
        throw new AssertionError("Invalid registration type!");
    }
  }

  public String getAPIOrigin() {
    switch (mRegistrationType.ordinal() + 1) {
      case 2:
        return "AndroidSdkAdm";
      case 3:
        return "AndroidSdkGcm";
      case 4:
        return "AndroidSdkBaidu";
      default:
        throw new AssertionError("Invalid registration type!");
    }
  }
}
