package tr.com.idefix.android.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tr.com.idefix.android.R;
import tr.com.idefix.android.view.listeners.OnReviewListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReviewFragment extends BaseFragment {

  @Bind(R.id.review_textInputLayout) TextInputLayout review_textInputLayout;
  @Bind(R.id.title_textInputLayout) TextInputLayout title_textInputLayout;
  @Bind(R.id.send) AppCompatButton send;
  @Bind(R.id.review_edit_text) AppCompatEditText review_edit_text;
  @Bind(R.id.title_edit_text) AppCompatEditText title_edit_text;
  private OnReviewListener onReviewListener;

  public ReviewFragment() {
  }

  @Override public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState
  ) {
    View view = inflater.inflate(R.layout.fragment_review, container, false);
    ButterKnife.bind(this, view);
    ////////////////////////////////////////////////////////////////////////////////////////////
    title_textInputLayout.setErrorEnabled(true);
    review_textInputLayout.setErrorEnabled(true);
    return view;
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);

    if (activity instanceof OnReviewListener) {
      onReviewListener = (OnReviewListener) activity;
    }
  }

  @OnClick(R.id.send) void send() {

    send.setAlpha(0.25f);
    send.setEnabled(false);

    if (title_edit_text.getText().toString().trim().length() == 0) {
      title_textInputLayout.setError("Başlık!");
      return;
    }
    if (review_edit_text.getText().toString().trim().length() == 0) {
      review_textInputLayout.setError("Yorum!");
      return;
    }

    if (onReviewListener != null) {
      onReviewListener.onReview(title_edit_text.getText().toString(),
          review_edit_text.getText().toString());
    }
  }
}
