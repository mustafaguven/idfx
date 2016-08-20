package tr.com.idefix.android.view.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import tr.com.idefix.android.R;
import tr.com.idefix.android.view.listeners.OnBasketClickListener;

/**
 * Created by utkan on 09/10/15.
 */
public class BasketViewComponent extends ImageView implements View.OnClickListener {

  OnBasketClickListener onBasketClickListener;

  public BasketViewComponent(Context context) {
    super(context);
    init();
  }

  public BasketViewComponent(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public BasketViewComponent(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    setImageResource(R.drawable.ic_basket);

    setOnClickListener(this);
  }

  @Override public void onClick(View v) {

    if (onBasketClickListener != null) {
      onBasketClickListener.onBasketClick();
    }
  }

  public void setOnBasketClickListener(OnBasketClickListener l) {
    this.onBasketClickListener = l;
  }
}
