package tr.com.idefix.android.view.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import tr.com.idefix.android.R;

public class BarcodeActivity extends BaseActivity {

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.zxing_barcode_scanner) DecoratedBarcodeView barcodeScannerView;
  private CaptureManager capture;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.barcode_activity);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_white_back);
    toolbar.setNavigationOnClickListener(v -> finish());

    capture = new CaptureManager(this, barcodeScannerView);
    capture.initializeFromIntent(getIntent(), savedInstanceState);
    capture.decode();
  }

  @Override protected void onResume() {
    super.onResume();
    capture.onResume();
  }

  @Override protected void onPause() {
    super.onPause();
    capture.onPause();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    capture.onDestroy();
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    capture.onSaveInstanceState(outState);
  }
}
