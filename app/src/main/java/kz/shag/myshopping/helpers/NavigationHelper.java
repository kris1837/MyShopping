package kz.shag.myshopping.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import kz.shag.myshopping.activity.CartActivity;
import kz.shag.myshopping.activity.MainActivity;
import kz.shag.myshopping.activity.ProductActivity;
import kz.shag.myshopping.activity.PurchaseDataActivity;
import kz.shag.myshopping.activity.SignInActivity;
import kz.shag.myshopping.activity.SignUpActivity;
import kz.shag.myshopping.entity.Product;

import static androidx.core.content.ContextCompat.startActivity;

public class NavigationHelper {
    public static void goToMainActivity(AppCompatActivity context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void goToCart(Activity context) {
        Intent intent = new Intent(context, CartActivity.class);
        context.startActivity(intent);
    }

    public static void goToPurchase(AppCompatActivity context, ArrayList<Product> products) {
        Intent intent = new Intent(context, PurchaseDataActivity.class);
        intent.putParcelableArrayListExtra("products", products);
        context.startActivity(intent);
    }

    public static void goToProductActivity(AppCompatActivity context, Product product) {
        Intent intent = new Intent(context, PurchaseDataActivity.class);
        intent.putExtra("product", (Parcelable) product);
        context.startActivity(intent);
    }

    public static void goToSignInActivity(AppCompatActivity context) {
        Intent intent = new Intent(context, SignInActivity.class);
        context.startActivity(intent);
    }

    public static void goToSignUpActivity(AppCompatActivity context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        context.startActivity(intent);
    }
}
