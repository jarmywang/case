package com.example.demo

import android.os.Handler
import android.os.Message

import androidx.viewpager.widget.ViewPager

import java.lang.ref.WeakReference

/**
 * PageBannerHandler
 * Created by Wang on 2015/11/2.
 */
class PageBannerHandler(private val viewPager: ViewPager, private val initCurrentItem: Int, //使用弱引用避免Handler泄露.这里的泛型参数可以不是Activity，也可以是Fragment等
                        private val weakReference: WeakReference<*>) : Handler() {
    private val TAG = "PageBannerHandler"
    var currentItem: Int = 0
        private set

    init {
        currentItem = initCurrentItem
    }

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        //        Log.d(TAG, "receive message " + msg.what);
        if (weakReference.get() == null) {
            //Activity已经回收，无需再处理UI了
            return
        }
        //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
        if (this.hasMessages(MSG_UPDATE_IMAGE) && currentItem != initCurrentItem) {
            this.removeMessages(MSG_UPDATE_IMAGE)
        }
        when (msg.what) {
            MSG_UPDATE_IMAGE -> {
                currentItem++
                viewPager.currentItem = currentItem
                //准备下次播放
                this.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY)
            }
            MSG_KEEP_SILENT -> {
            }
            MSG_BREAK_SILENT -> this.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY)
            MSG_PAGE_CHANGED ->
                //记录当前的页号，避免播放的时候页面显示不正确。
                currentItem = msg.arg1
        }//只要不发送消息就暂停了
    }

    companion object {
        /**
         * 请求更新显示的View。
         */
        val MSG_UPDATE_IMAGE = 1
        /**
         * 请求暂停轮播。
         */
        val MSG_KEEP_SILENT = 2
        /**
         * 请求恢复轮播。
         */
        val MSG_BREAK_SILENT = 3
        /**
         * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
         * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
         * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
         */
        val MSG_PAGE_CHANGED = 4

        //轮播间隔时间
        val MSG_DELAY: Long = 5000
    }
}

