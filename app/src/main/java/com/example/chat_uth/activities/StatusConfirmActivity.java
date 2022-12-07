package com.example.chat_uth.activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.chat_uth.R;
import com.example.chat_uth.adapters.StatusPagerAdapter;
import com.example.chat_uth.models.Status;
import com.example.chat_uth.providers.AuthProvider;
import com.example.chat_uth.providers.ImageProvider;
import com.example.chat_uth.utils.ShadowTransformer;

import java.util.ArrayList;
import java.util.Date;

public class StatusConfirmActivity extends AppCompatActivity {

    // VARIABLES GLOBALES =========================================================================
    ViewPager mViewPager;
    // CIERRE =====================================
    ArrayList<String> data;
    ImageProvider mImageProvider;
    AuthProvider mAuthProvider;

    ArrayList<Status> mStatus = new ArrayList<>();
    // ============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_confirm);
        setStatusBarColor();

        // INSTANCIAS ==============================================================================
        mViewPager = findViewById(R.id.viewPager);
        mAuthProvider = new AuthProvider();

        data = getIntent().getStringArrayListExtra("data");
        mImageProvider = new ImageProvider();
        // ========================================================================================

        // INCLUYE EN messages TODOS LOS MENSAJES QUE SE ALMACENARIAN EN LA BDD
        if (data != null){
            for (int i = 0; i < data.size(); i++){
                Status s = new Status();
                long now = new Date().getTime();
                // VENCIMIENTO DE TIEMPO 3 MINUTOS
                long limit = now + (60 * 1000 * 60 * 24);
                s.setIdUser(mAuthProvider.getId());
                s.setComment("");
                s.setTimestamp(now);
                s.setTimestampLimit(limit);
                s.setUrl(data.get(i));
                mStatus.add(s);
            }
        }

        StatusPagerAdapter pagerAdapter = new StatusPagerAdapter(
                getApplicationContext(),
                getSupportFragmentManager(),
                dpToPixels(2, this),
                data
        );
        ShadowTransformer transformer = new ShadowTransformer(mViewPager, pagerAdapter);
        transformer.enableScaling(true);

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setPageTransformer(false, transformer);

    }

    // METODO PARA ENVIAR TODA LA INFORMACION A LA BDD
    public void send(){
        mImageProvider.uploadMultipleStatus(StatusConfirmActivity.this, mStatus);
        finish();
    }


    public void setComment(int position, String comment){
      mStatus.get(position).setComment(comment);
        //messages.get(position).setMessage(message);
    }


    public static float dpToPixels(int dp, Context context){
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    private void setStatusBarColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorFullBlack, this.getTheme()));
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorFullBlack));
        }
    }
}