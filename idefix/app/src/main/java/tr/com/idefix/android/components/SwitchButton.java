package tr.com.idefix.android.components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tr.com.idefix.android.R;

/**
 * Created by mustafaguven on 14.10.2015.
 */
public class SwitchButton extends RelativeLayout {

  @Bind(R.id.selectedBackground) RelativeLayout selectedBackground;
  @Bind(R.id.btnFirst) RelativeLayout btnFirst;
  @Bind(R.id.btnSecond) RelativeLayout btnSecond;
  @Bind(R.id.lblFirst) TextView lblFirst;
  @Bind(R.id.lblSecond) TextView lblSecond;
  @Bind(R.id.switchButtonContainer) RelativeLayout switchButtonContainer;
  boolean isAnimationFinished = true;
  private View view;
  private int mSelectedPosition = 0;
  private String[] mTitles = new String[] { "FirstItem", "SecondItem" };
  private OnSwitchListener onSwitchListener = null;

  public SwitchButton(Context context) {
    super(context);
    init();
  }

  public SwitchButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  public void setTitles(String[] titles) {
    this.mTitles = titles;
  }

  public void setOnSwitchListener(OnSwitchListener onSwitchListener) {
    this.onSwitchListener = onSwitchListener;
  }

  @OnClick({ R.id.btnFirst, R.id.btnSecond }) void requestToSwitch(View v) {
    if ((v.getId() == R.id.btnFirst && mSelectedPosition == 0) || (v.getId() == R.id.btnSecond
        && mSelectedPosition == 1) || !isAnimationFinished) {
      return;
    }
    mSelectedPosition = v.getId() == R.id.btnFirst ? 0 : 1;
    onSwitched(v);
  }

  private void onSwitched(View v) {
    isAnimationFinished = false;
    if (this.onSwitchListener != null) {
      onSwitchListener.onSwitch(v.getId() == R.id.btnFirst);
    }
    lblFirst.setTypeface(null, mSelectedPosition == 0 ? Typeface.BOLD : Typeface.NORMAL);
    lblSecond.setTypeface(null, mSelectedPosition == 1 ? Typeface.BOLD : Typeface.NORMAL);
    selectedBackground.animate()
        .setDuration(500)
        .translationX(
            selectedBackground.getX() + (selectedBackground.getWidth() * (v.getId() == R.id.btnFirst
                ? -1 : 1)))
        .setInterpolator(new DecelerateInterpolator(2))
        .setListener(new AnimatorListenerAdapter() {
          @Override public void onAnimationEnd(Animator animation) {
            isAnimationFinished = true;
            super.onAnimationEnd(animation);
          }
        })
        .start();
  }

  private void init() {
    view = LayoutInflater.from(getContext()).inflate(R.layout.switchbutton, this);
    ButterKnife.bind(view, this);
    this.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override public void onGlobalLayout() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
              getViewTreeObserver().removeGlobalOnLayoutListener(this);
            } else {
              getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }

            RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams((switchButtonContainer.getWidth() / 2),
                    LayoutParams.MATCH_PARENT);
            selectedBackground.setLayoutParams(params);
            btnFirst.setLayoutParams(params);

            RelativeLayout.LayoutParams paramsForSecond =
                new RelativeLayout.LayoutParams((switchButtonContainer.getWidth() / 2),
                    LayoutParams.MATCH_PARENT);
            paramsForSecond.addRule(RelativeLayout.RIGHT_OF, btnFirst.getId());
            btnSecond.setLayoutParams(paramsForSecond);
            lblFirst.setText(mTitles[0]);
            lblSecond.setText(mTitles[1]);
          }
        });
  }

  public interface OnSwitchListener {
    void onSwitch(boolean isFirstItemSelected);
  }
}
