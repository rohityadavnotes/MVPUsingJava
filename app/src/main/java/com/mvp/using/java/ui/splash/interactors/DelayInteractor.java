package com.mvp.using.java.ui.splash.interactors;

public interface DelayInteractor {

    void delay(long delay, DelayListener delayListener);

    interface DelayListener {
        void delayFinish();
    }
}
