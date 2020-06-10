package kz.shag.myshopping.fragments;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import kz.shag.myshopping.R;
import kz.shag.myshopping.di.AppModule;
import kz.shag.myshopping.di.DaggerAppComponent;
import kz.shag.myshopping.helpers.NavigationHelper;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    @Inject
    SharedPreferences preferences;

    private final String APP_PREFERENCES = "ProfilePreferences";
    private final String APP_PREFERENCES_USERNAME = "Username";
    private final String APP_PREFERENCES_FULLNAME = "Fullname";
    private final String APP_PREFERENCES_PHONE = "Phone";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerAppComponent.builder()
                .appModule(new AppModule(getActivity().getApplication()))
                .build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Button uiLogin = view.findViewById(R.id.uiLogin);
        Button uiRegistration = view.findViewById(R.id.uiRegistration);

        uiLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationHelper.goToSignInActivity((AppCompatActivity) getActivity());
            }
        });

        uiRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationHelper.goToSignUpActivity((AppCompatActivity) getActivity());
            }
        });

        String fullname = preferences.getString(APP_PREFERENCES_FULLNAME, "");
    }
}
