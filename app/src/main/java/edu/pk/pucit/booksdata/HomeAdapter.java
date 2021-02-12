package edu.pk.pucit.booksdata;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements Filterable {

    Context context;

    private final ArrayList<HomeModel> homeModels;
    private final ArrayList<HomeModel> idFull;

    HomeAdapter(Context context, ArrayList<HomeModel> booksArrayList){
        this.context = context;
        this.homeModels = booksArrayList;
        idFull = new ArrayList<>(homeModels);
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(context).inflate(R.layout.item_card,null);
        return new ViewHolder(rowView);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.item_Title.setText(homeModels.get(position).getDayList());

        holder.item_cardView.setOnClickListener(v -> {
            Intent i = new Intent(context, SecondActivity.class);
            i.putExtra("day", homeModels.get(position).getDayList());
            i.putExtra("apiId", homeModels.get(position).getIdList());
            context.startActivity(i);
        });

        int randomDrawable = CommonUtils.getRandomDrawable();
        holder.item_cardView.setBackgroundResource(randomDrawable);

        if (randomDrawable == R.drawable.ic_card_gradient_1){
            holder.item_Img.setImageResource(R.drawable.ic_bulb_brain);
        } else if (randomDrawable == R.drawable.ic_card_gradient_3){
            holder.item_Img.setImageResource(R.drawable.ic_speak);
        }
    }

    @Override
    public int getItemCount() {
        return homeModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView item_Title;
        ImageView item_Img;
        LinearLayoutCompat item_cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_cardView = itemView.findViewById(R.id.item_cardView);
            item_Title = itemView.findViewById(R.id.item_Title);
            item_Img = itemView.findViewById(R.id.item_Img);
        }
    }

    @Override
    public Filter getFilter() {
        return homeFilter;
    }

    private final Filter homeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<HomeModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(idFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (HomeModel item:
                        idFull) {
                    if(item.getDayList().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            homeModels.clear();
            homeModels.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();
        }
    };
}
