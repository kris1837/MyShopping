package kz.shag.myshopping.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kz.shag.myshopping.R;
import kz.shag.myshopping.entity.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    Context context;
    List<Product> products;

    public ProductAdapter(List<Product> products){
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row,parent,false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.priceTextView.setText(Double.toString(products.get(position).getCost()));
        holder.nameTextView.setText(products.get(position).getTitle());
        //Uncommit after importing glide
        //Glide.with(this).load("http://goo.gl/gEgYUd").into(holder.productImageView);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView,priceTextView;
        Button buyButton;
        ImageView productImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            priceTextView = itemView.findViewById(R.id.textViewPrice);
            buyButton = itemView.findViewById(R.id.buttonBuy);
            productImageView = itemView.findViewById(R.id.imageViewProduct);
        }
    }
}
