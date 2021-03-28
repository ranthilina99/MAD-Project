package ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madmini.R;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView textItemName,txtItemDiscription,txtitemPrice,txtitembrand,txtStatus;
    public ImageView imageView;
   // public ItemClickListner itemlistner;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView =(ImageView) itemView.findViewById(R.id.item_image);
        textItemName =(TextView) itemView.findViewById(R.id.item_name);
        txtItemDiscription =(TextView) itemView.findViewById(R.id.item_description);
        txtitemPrice =(TextView) itemView.findViewById(R.id.item_price);
        txtitembrand =(TextView) itemView.findViewById(R.id.item_brand);
        txtStatus =(TextView) itemView.findViewById(R.id.item_status);
    }


    @Override
    public void onClick(View view) {

    }
}
