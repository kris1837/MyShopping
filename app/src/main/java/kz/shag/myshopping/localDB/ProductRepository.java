package kz.shag.myshopping.localDB;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import kz.shag.myshopping.dao.ProductDao;
import kz.shag.myshopping.entity.Product;

public class ProductRepository {
    private ProductDao productDao;

    public ProductRepository(Context context) {
        this.productDao = LocalDataBase
                .getInstance(context).productDao();
    }

    LiveData<List<Product>> getAllProducts(){
        return productDao.getAll();
    }

    public void updateProduct(Product product){
        new updateAsyncTask(productDao).execute(product);
    }

    public void deleteProduct(Product product){
        new deleteAsyncTask(productDao).execute(product);
    }

    public void insertProduct(Product product){
        new insertAsyncTask(productDao).execute(product);
    }

    private static class insertAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;

        public insertAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            this.productDao.insertProduct(products[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;
        public deleteAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            this.productDao.deleteProduct(products[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Product, Void, Void>{
        private ProductDao productDao;
        public updateAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            this.productDao.updateProduct(products[0]);
            return null;
        }
    }
}
