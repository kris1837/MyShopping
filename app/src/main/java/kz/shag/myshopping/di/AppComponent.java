package kz.shag.myshopping.di;

import javax.inject.Singleton;

import dagger.Component;
import kz.shag.myshopping.MyShoppingApplication;
import kz.shag.myshopping.activity.MainActivity;
import kz.shag.myshopping.fragments.ProfileFragment;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(MyShoppingApplication application);

    void inject(MainActivity mainActivity);

    void inject(ProfileFragment profileFragment);

}
