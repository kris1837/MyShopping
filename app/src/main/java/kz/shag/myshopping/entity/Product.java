package kz.shag.myshopping.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.PropertyName;

@Entity(tableName = "products")
public class Product implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "cost")
    private double cost;

    @ColumnInfo(name = "image_url")
    @PropertyName("image_url")
    private String imageUrl;

    @ColumnInfo(name = "quantity")
    private int quantity;

    public Product() {
    }

    @Ignore
    public Product(int id, String title, String description, double cost, String imageUrl, int quantity){
        this.id = id;
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }
    @Ignore
    protected Product(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        cost = in.readDouble();
        imageUrl = in.readString();
        quantity = in.readInt();
    }
    @Ignore
    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }
    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeDouble(cost);
        dest.writeString(imageUrl);
        dest.writeInt(quantity);
    }
}
