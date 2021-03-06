package kz.shag.myshopping.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import kz.shag.myshopping.R;
import kz.shag.myshopping.activity.IProductClickListener;
import kz.shag.myshopping.activity.MainActivity;
import kz.shag.myshopping.activity.ProductActivity;
import kz.shag.myshopping.entity.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Filterable {

    Context context;
    List<Product> products;
    List<Product> filteredProducts;
    IProductClickListener callback;

    public ProductAdapter(List<Product> products, IProductClickListener callback) {
        this.products = products;
        this.filteredProducts = products;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product product = filteredProducts.get(position);
        holder.priceTextView.setText(Double.toString(product.getCost()));
        holder.nameTextView.setText(product.getTitle());
        holder.buyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                callback.onClick(product);
            }
        });

        Glide.with(context).load(product.getImageUrl()).into(holder.productImageView);
    }

    @Override
    public int getItemCount() {
        return filteredProducts.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filteredProducts = products;
                } else {
                    List<Product> filteredList = new ArrayList<>();
                    for (Product row : products) {
                        // here we are looking for name
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filteredProducts = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredProducts;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredProducts = (List<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView, priceTextView;
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
