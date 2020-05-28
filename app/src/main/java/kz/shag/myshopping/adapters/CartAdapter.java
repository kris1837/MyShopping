package kz.shag.myshopping.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;
import java.util.List;

import kz.shag.myshopping.R;
import kz.shag.myshopping.entity.Product;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    public interface OnCartAdapterEventListener{
        public void onPlusButtonClick(Product product);
        public void onMinusButtonClick(Product product);
        public void onDeleteButtonClick(Product product);
    }

    private List<Product> mProducts;
    private OnCartAdapterEventListener listener;

    public void setListener(OnCartAdapterEventListener listener){
        this.listener = listener;
    }

    public CartAdapter(List<Product> products){
        mProducts = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Product product = mProducts.get(position);

        TextView itemNameTextView = holder.itemNameTextView;
        itemNameTextView.setText(product.getTitle());

        TextView itemPriceTextView = holder.itemPriceTextView;
        itemPriceTextView.setText(Double.toString(product.getCost()));

        TextView itemCountTextView = holder.itemCountTextView;
        itemCountTextView.setText(product.getQuantity());

        ImageView itemImageView = holder.itemImageView;
        //нужно загружать из product.getImageUri

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)v;

                switch(btn.getId()) {
                    case R.id.plusItemButton:
                        listener.onPlusButtonClick(product);
                        break;
                    case R.id.minusItemButton:
                        listener.onMinusButtonClick(product);
                        break;
                    case R.id.deleteItemButton:
                        listener.onDeleteButtonClick(product);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView itemNameTextView;
        public TextView itemPriceTextView;
        public TextView itemCountTextView;
        public ImageView itemImageView;

        public ViewHolder(View itemView){
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemPriceTextView = itemView.findViewById(R.id.itemPriceTextView);
            itemCountTextView = itemView.findViewById(R.id.itemCountTextView);
            itemImageView = itemView.findViewById(R.id.itemImageView);
        }
    }
}
