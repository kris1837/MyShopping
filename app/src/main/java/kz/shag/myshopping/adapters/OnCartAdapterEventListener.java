package kz.shag.myshopping.adapters;

import kz.shag.myshopping.entity.Product;

public interface OnCartAdapterEventListener{
    void onPlusButtonClick(Product product);
    void onMinusButtonClick(Product product);
    void onDeleteButtonClick(Product product);
}
