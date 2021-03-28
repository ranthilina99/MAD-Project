package ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madmini.Interface.ItemClickListner;
import com.example.madmini.R;

public class CartViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

    //accuses  the cart item design eliment like text view

    public TextView txtProductName,txtProductPrice,txtProductQuentity;
    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductName=itemView.findViewById(R.id.cart_product_name);
        txtProductPrice=itemView.findViewById(R.id.cart_product_price);
        txtProductQuentity=itemView.findViewById(R.id.cart_product_quantity);
    }


    @Override
    public void onClick(View view) {

        itemClickListner.onClick(view,getAdapterPosition(),false);

    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
