package org.insacc.mobilechallenge.Events;

/**
 * Created by can on 31.07.2017.
 */

public class ScrollToPositionEvent {

    private int mPosition;

    public ScrollToPositionEvent(int position) {
        this.mPosition = position;
    }

    public int getPosition() {
        return mPosition;
    }
}
