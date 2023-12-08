package com.learningwithmanos.uniexercise.utils


import android.widget.ImageView
import com.bumptech.glide.Glide
import com.learningwithmanos.uniexercise.heroes.data.Thumbnail
import de.hdodenhof.circleimageview.CircleImageView

fun CircleImageView.loadImage(url: String?){
    Glide.with(context)
        .load(url)
        //.apply(RequestOptions().override(250,250))
        .into(this)

}

fun Thumbnail.getThumbnail() :String {
    val str: String = this.path.replace("http", "https")

    return "${str}.${this.ext}"
}

fun ImageView.loadImage(url: String?){
    Glide.with(context)
        .load(url)
        .into(this)
}
