package ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madmini.Interface.ItemClickListner;
import com.example.madmini.R;

public class AdminViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView textItemNameAdm,txtItemDiscriptionAdm,txtitemPriceAdm,txtitembrandAdm,txtStatusAdm,txtPetTypeAdm,txtItetypeAdm;
    public ImageView imageViewAdm;
    //public ItemClickListner itemlistner;

    public AdminViewHolder(@NonNull View itemView) {
        super(itemView);

        imageViewAdm =(ImageView) itemView.findViewById(R.id.item_image_adm);
        textItemNameAdm =(TextView) itemView.findViewById(R.id.item_name_adm);
        txtItemDiscriptionAdm =(TextView) itemView.findViewById(R.id.item_description_adm);
        txtitemPriceAdm =(TextView) itemView.findViewById(R.id.item_price_adm);
        txtitembrandAdm =(TextView) itemView.findViewById(R.id.item_brand_adm);
        txtStatusAdm =(TextView) itemView.findViewById(R.id.item_status_adm);
        txtPetTypeAdm =(TextView) itemView.findViewById(R.id.item_pet_adm);
        txtItetypeAdm =(TextView) itemView.findViewById(R.id.item_itemT_adm);
    }

    @Override
    public void onClick(View view) {

    }
}
