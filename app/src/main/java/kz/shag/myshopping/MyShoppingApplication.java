package kz.shag.myshopping;

import android.app.Application;
import android.util.Log;

import kz.shag.myshopping.di.AppComponent;
import kz.shag.myshopping.di.AppModule;
import kz.shag.myshopping.di.DaggerAppComponent;

public class MyShoppingApplication extends Application {

    private AppComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("Application", "Application started");

        applicationComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public AppComponent getComponent(){
        return applicationComponent;
    }
}
