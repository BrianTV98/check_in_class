package com.example.check_in.until

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.check_in.R
import com.squareup.picasso.Picasso
import java.lang.Exception

@BindingAdapter("app:imagUrl")
fun loadImageFromUrl(view: ImageView, imagUrl : String?){
    if(imagUrl!=null){
        try{
            Picasso.get().load(imagUrl).error(R.drawable.ic_launcher_foreground).into(view)
        }
       catch (e: Exception){
           Picasso.get().load("https://scontent-hkt1-1.xx.fbcdn.net/v/t1.15752-9/103638335_815196839008017_8544258557241099513_n.jpg?_nc_cat=110&_nc_sid=b96e70&_nc_ohc=QSPywHWokcEAX_nVoG6&_nc_ht=scontent-hkt1-1.xx&oh=eaffcb5b64974861bcc44b30ebb285d5&oe=5F0B9D6B").into(view)
       }
    }
    else   Picasso.get().load("https://scontent-hkt1-1.xx.fbcdn.net/v/t1.15752-9/103638335_815196839008017_8544258557241099513_n.jpg?_nc_cat=110&_nc_sid=b96e70&_nc_ohc=QSPywHWokcEAX_nVoG6&_nc_ht=scontent-hkt1-1.xx&oh=eaffcb5b64974861bcc44b30ebb285d5&oe=5F0B9D6B").into(view)
}