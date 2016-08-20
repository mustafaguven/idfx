package tr.com.idefix.android.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.List;
import tr.com.idefix.android.R;
import tr.com.idefix.domain.Review;

/**
 * Created on 10.19.15.
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

  private List<Review> reviews;
  private Context mContext;

  public ReviewsAdapter(Context context, List<Review> reviews) {
    this.reviews = reviews;
    this.mContext = context;
  }

  @Override public ReviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_review, null);

    ReviewViewHolder viewHolder = new ReviewViewHolder(view);
    return viewHolder;
  }

  @Override public void onBindViewHolder(ReviewViewHolder holder, int i) {
    Review review = reviews.get(i);

    holder.review.setText(review.review());
    holder.date.setText(review.date());
  }

  @Override public int getItemCount() {
    return (null != reviews ? reviews.size() : 0);
  }

  public void addList(List<Review> l) {
    this.reviews.addAll(l);
    notifyDataSetChanged();
  }

  class ReviewViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.review_card_text) TextView review;
    @Bind(R.id.review_card_date) TextView date;

    public ReviewViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
}
