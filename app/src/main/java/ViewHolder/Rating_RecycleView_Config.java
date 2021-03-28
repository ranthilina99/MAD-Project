package ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madmini.Model.Rating;
import com.example.madmini.R;

import java.util.List;

public class Rating_RecycleView_Config {


    private Context mContext;
    private RatingAdapter ratingAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Rating> ratings, List<String> keys){
        mContext = context;
        ratingAdapter = new RatingAdapter(ratings, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(ratingAdapter);

    }
    class RatingView extends RecyclerView.ViewHolder{
        private TextView UserName;
        private TextView percentage;
        private RatingBar rati;


        private String key;
        //constructor
        public RatingView(ViewGroup parent){
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.rating_item_layout, parent, false));

            UserName = (TextView) itemView.findViewById(R.id.txt_uname);
            percentage =(TextView) itemView.findViewById(R.id.txt_percentag);
            rati = (RatingBar) itemView.findViewById(R.id.ratingBarI);



        }
        public void bind(Rating rating, String key){
            UserName.setText(rating.getUserName());
            //percentage.setText((int) rating.getPrecentage());
            rati.setRating(rating.getRatedvalue());

            // Float price2  =Float.parseFloat(setText((int) rating.getPrecentage());

            float str=rating.getPrecentage();

            //num.setText(String.valueOf(returnnum));
            percentage.setText(String.valueOf(str)+"%");



            this.key = key;
        }
    }
    class RatingAdapter extends RecyclerView.Adapter<RatingView>{
        private List<Rating> mRatingList;
        private List<String> mKeys;

        public RatingAdapter(List<Rating> mRatingList, List<String> mKeys) {
            this.mRatingList = mRatingList;
            this.mKeys = mKeys;
        }

        public RatingAdapter() {
            super();
        }

        @NonNull
        @Override
        public RatingView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RatingView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RatingView holder, int position) {
            holder.bind(mRatingList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mRatingList.size();
        }
    }

}
