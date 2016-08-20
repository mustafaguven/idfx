package tr.com.idefix.android.components;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import tr.com.idefix.android.R;

/**
 * Created by mustafaguven on 30.10.2015.
 */
public class ProgressDialogDNR extends ProgressDialog {
  public ProgressDialogDNR(Context context) {
    super(context, R.style.DNRCustomDialog);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setCancelable(false);
    setContentView(R.layout.progress_dialog_dnr);
    setProgressStyle(ProgressDialog.STYLE_SPINNER);
  }

  @Override public void show() {
    if (isShowing()) {
      return;
    }
    try {
      super.show();
    } catch (Exception e) {
    }
  }

  @Override public void dismiss() {
    super.dismiss();
  }
}
