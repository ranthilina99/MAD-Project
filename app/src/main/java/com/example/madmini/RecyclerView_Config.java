package com.example.madmini;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Config {


    private Context mContext;
    private AppointmentAdapter mAppointmentsAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Appoinment> appoinments, List<String> keys){
        mContext = context;
        mAppointmentsAdapter = new AppointmentAdapter(appoinments, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAppointmentsAdapter);

    }
    class AppointmentItemView extends RecyclerView.ViewHolder{
        private TextView PetName;
        private TextView OwnerName;
        private TextView Type;
        private TextView Date;

        private String key;
        //constructor
        public AppointmentItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.appo_list_item, parent, false));

            PetName = (TextView) itemView.findViewById(R.id.title_txtViewPetName);
            OwnerName =(TextView) itemView.findViewById(R.id.OwnerNAme_authoe_txtve);
            Type = (TextView) itemView.findViewById(R.id.txtView_Type);
            Date = (TextView) itemView.findViewById(R.id.textView_Date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(mContext, AppoDetailsActivity.class);
                    intent.putExtra("key",key);
                    intent.putExtra("ownerName",OwnerName.getText().toString());
                    intent.putExtra("petName",PetName.getText().toString());
                    intent.putExtra("Date",Date.getText().toString());
                    intent.putExtra("Type",Type.getText().toString());
                    //intent.putExtra("Breed",)
                    mContext.startActivity(intent);
                }
            });

        }
        public void bind(Appoinment appoinment, String key){
            PetName.setText(appoinment.getpetName());
            OwnerName.setText(appoinment.getownerName());
            Type.setText(appoinment.getType());
            Date.setText(appoinment.getDate());
            this.key = key;
        }
    }
    class AppointmentAdapter extends RecyclerView.Adapter<AppointmentItemView>{
        private List<Appoinment> mAppoinmentList;
        private List<String> mKeys;

        public AppointmentAdapter(List<Appoinment> mAppoinmentList, List<String> mKeys) {
            this.mAppoinmentList = mAppoinmentList;
            this.mKeys = mKeys;
        }
        public AppointmentAdapter(){
            super();
        }



        @NonNull
        @Override
        public AppointmentItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AppointmentItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull AppointmentItemView holder, int position) {
            holder.bind(mAppoinmentList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mAppoinmentList.size();
        }

    }


}
