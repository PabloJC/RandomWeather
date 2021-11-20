package com.example.randomweather.ui.utils.extensions

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.floatingactionbutton.FloatingActionButton

fun FloatingActionButton.showAnimationRotate(){
    animate().rotationBy(360f).setDuration(500).start()
}

fun Activity.showToast(@StringRes res: Int){
    Toast.makeText(this, res, Toast.LENGTH_SHORT).show()
}