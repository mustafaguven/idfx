package tr.com.idefix.android.view.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import tr.com.idefix.android.R;
import tr.com.idefix.android.view.listeners.OnSearchClickListener;

/**
 * Created by utkan on 09/10/15.
 */
public class SearchViewComponent extends ImageView implements View.OnClickListener {

  OnSearchClickListener onSearchClickListener;

  public SearchViewComponent(Context context) {
    super(context);
    init();
  }

  public SearchViewComponent(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public SearchViewComponent(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    setImageResource(R.drawable.ic_search_white);

    setOnClickListener(this);
  }

  @Override public void onClick(View v) {

    if (onSearchClickListener != null) {
      onSearchClickListener.onSearchClick();
    }
  }

  public void setOnSearchClickListener(OnSearchClickListener l) {
    this.onSearchClickListener = l;
  }
}
