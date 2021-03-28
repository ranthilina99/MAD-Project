package ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madmini.R;

public class AdminOrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView userName,userphone,userTotalprice,userDtaetime,useraddress;
    public Button showOrders;
    public AdminOrdersViewHolder(@NonNull View itemView) {
        super(itemView);


        userName = itemView.findViewById(R.id.order_user_name);
        userphone = itemView.findViewById(R.id.order_phone_number);
        userTotalprice = itemView.findViewById(R.id.order_total);
        userDtaetime = itemView.findViewById(R.id.order_date_time);
        useraddress = itemView.findViewById(R.id.order_address_city);

        showOrders=itemView.findViewById(R.id.show_all_products);
    }

    @Override
    public void onClick(View view) {

    }
}
