package tr.com.idefix.android.view.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import tr.com.idefix.android.R;
import tr.com.idefix.android.internal.di.HasComponent;
import tr.com.idefix.android.internal.di.components.CommonComponent;
import tr.com.idefix.android.internal.di.components.DaggerCommonComponent;

public class CommonActivity extends BaseActivity implements HasComponent<CommonComponent> {

  private CommonComponent commonComponent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_common);

    initializeInjector();
  }

  private void initializeInjector() {
    this.commonComponent = DaggerCommonComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .build();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_common, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public CommonComponent getComponent() {
    return commonComponent;
  }
}
