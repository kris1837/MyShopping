package kz.shag.myshopping.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import kz.shag.myshopping.R;
import kz.shag.myshopping.activity.CartActivity;
import kz.shag.myshopping.dao.ProductDao;
import kz.shag.myshopping.entity.Product;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<Product> mProducts;
    private OnCartAdapterEventListener listener;

    public void setListener(OnCartAdapterEventListener listener){
        this.listener = listener;
    }

    public CartAdapter(){ }

    public void setmProducts(List<Product> mProducts) {
        this.mProducts = mProducts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Product product = mProducts.get(position);

        TextView itemNameTextView = holder.itemNameTextView;
        itemNameTextView.setText(product.getTitle());

        TextView itemPriceTextView = holder.itemPriceTextView;
        itemPriceTextView.setText(String.valueOf(product.getCost()));

        TextView itemCountTextView = holder.itemCountTextView;
        itemCountTextView.setText(String.valueOf(product.getQuantity()));

        ImageView itemImageView = holder.itemImageView;
        if(product.getImageUrl() != ""){
            //new updateAsyncTask((CartActivity) listener, product.getImageUrl()).execute(itemImageView);
        }

        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPlusButtonClick(product);
            }
        });
        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMinusButtonClick(product);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteButtonClick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemNameTextView;
        TextView itemPriceTextView;
        TextView itemCountTextView;
        ImageView itemImageView;

        Button plusButton;
        Button minusButton;
        Button deleteButton;

        ViewHolder(View itemView){
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemPriceTextView = itemView.findViewById(R.id.itemPriceTextView);
            itemCountTextView = itemView.findViewById(R.id.itemCountTextView);
            itemImageView = itemView.findViewById(R.id.itemImageView);

            plusButton = itemView.findViewById(R.id.plusItemButton);
            minusButton = itemView.findViewById(R.id.minusItemButton);
            deleteButton = itemView.findViewById(R.id.deleteItemButton);
        }
    }

    private static class updateAsyncTask extends AsyncTask<ImageView, Void, Void> {
        private CartActivity listener;
        private String imageUrl;

        public updateAsyncTask(CartActivity listenerm, String imageUrl){
            this.imageUrl = imageUrl;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(ImageView... imageViews) {
            Glide.with((CartActivity) listener).load(imageUrl).centerCrop().into(imageViews[0]);
            return null;
        }
    }
}
