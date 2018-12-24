package com.photosaloon.ui.services;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.photosaloon.R;
import com.photosaloon.content.ServicesModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<ServicesModel> mModelList;


    public RecyclerViewAdapter(List<ServicesModel> modelList) {
        mModelList = modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_services, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ServicesModel servicesModel = mModelList.get(position);

        holder.textViewName.setText(servicesModel.getTypeServices());
        holder.sum.setText(getFormattedPrice(holder.context, servicesModel.getPrice()));

        holder.view.setBackground(servicesModel.isSelected()
                ? holder.context.getResources().getDrawable(R.drawable.item_list_bg_select)
                : holder.context.getResources().getDrawable(R.drawable.item_list_bg));

        holder.view.setOnClickListener(view -> {

            holder.view.setBackground(mModelList.get(position).isSelected()
                    ? holder.context.getResources().getDrawable(R.drawable.item_list_bg)
                    : holder.context.getResources().getDrawable(R.drawable.item_list_bg_select));

            mModelList.get(position).setSelected(!servicesModel.isSelected());

        });


    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        private View view;
        private TextView textViewName;
        private TextView sum;
        private Context context;


        public MyViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            view = itemView;
            view.setOnClickListener(this);
            context = itemView.getContext();

            textViewName = itemView.findViewById(R.id.services_name);
            sum = itemView.findViewById(R.id.service_price);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private String getFormattedPrice(@NonNull Context context, int value) {
        String param = getFormattedValue(value);

        @StringRes int resId;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resId = R.string.common_price_template;
        } else {
            resId = R.string.common_price_template_old;
        }

        return context.getString(resId, param);
    }

    private String getFormattedValue(int value) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(value);
    }

    public List<ServicesModel> getSelectItems() {

        List<ServicesModel> servicesModels = new ArrayList<>();
        for (ServicesModel item : mModelList) {
            if (item.isSelected()) {
                servicesModels.add(item);
            }
        }



        return servicesModels;
    }
}
