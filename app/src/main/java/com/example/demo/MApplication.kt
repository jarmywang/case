package com.example.demo

import android.app.Application
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
//import io.flutter.embedding.engine.FlutterEngine
//import io.flutter.embedding.engine.FlutterEngineCache
//import io.flutter.embedding.engine.dart.DartExecutor


class MApplication : Application() {
//    lateinit var flutterEngine: FlutterEngine
    override fun onCreate() {
        super.onCreate()
//        // Instantiate a FlutterEngine.
//        flutterEngine = FlutterEngine(this)
//        // Configure an initial route.
//        flutterEngine.navigationChannel.setInitialRoute("main");
//        // Start executing Dart code to pre-warm the FlutterEngine.
//        flutterEngine.dartExecutor.executeDartEntrypoint(
//                DartExecutor.DartEntrypoint.createDefault()
//        )
//        // Cache the FlutterEngine to be used by FlutterActivity or FlutterFragment.
//        FlutterEngineCache
//                .getInstance()
//                .put("my_engine_id", flutterEngine)

        val map = HashMap<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)
    }
}
