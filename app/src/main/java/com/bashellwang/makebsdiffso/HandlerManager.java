package com.bashellwang.makebsdiffso;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.Future;

/**
 * 主线程切换
 */
public class HandlerManager {
    private static final String TAG = HandlerManager.class.getSimpleName();
    private Handler mUiHandler;
    private Future mTask;

    private HandlerManager() {
    }

    private static class Holder {
        private static final HandlerManager INSTANCE = new HandlerManager();
    }

    public static HandlerManager get() {
        return Holder.INSTANCE;
    }

    public void init() {
        Log.i(TAG, "BBUILoop init");
        mUiHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.i(TAG, "ui handler thread id=" + Thread.currentThread().getId());
                Runnable info = (Runnable) msg.obj;
                if (info != null) {
                    info.run();
                } else {
                    Log.e(TAG, "UI callback is null");
                }
            }
        };
    }

    public void mainPost(Runnable call) {
        if (call == null) {
            return;
        }

        if (isMainLooper()) {
            call.run();
            return;
        }
        mUiHandler.post(call);
    }

    public void delayMainPost(Runnable runnable, long delayMillis) {
        if (runnable == null) {
            return;
        }
        mUiHandler.postDelayed(runnable, delayMillis);
    }

    public void cancelPost(Runnable r) {
        if (mUiHandler != null) {
            mUiHandler.removeCallbacks(r);
        }
        if (mTask != null && !mTask.isCancelled()) {
            mTask.cancel(true);
            mTask = null;
        }
    }

    public static boolean isMainLooper() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
