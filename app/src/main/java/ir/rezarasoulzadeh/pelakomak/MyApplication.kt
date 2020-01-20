package ir.rezarasoulzadeh.pelakomak

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val config = ImagePipelineConfig
            .newBuilder(this)
            .setDownsampleEnabled(true)
            .setDiskCacheEnabled(true)
            .build()
        Fresco.initialize(this,config)
    }

}