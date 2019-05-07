package com.github.olegschwann.spritzreader.spritz_reader;

/* Распределение обработчиков нажатий на экране:
□ □ □ □ □ ▤ ▩ ■ ■ ▩ ▤ □ □ □ □ □ ⎫
□ □ □ ▨ ■ ■ ■ ■ ■ ■ ■ ■ ▧ □ □ □ ⎬ toPreviousSentence
□ □ ▨ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ▧ □ □ ⎮
□ ▨ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ▧ □ ⎭
□ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ □
▥ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ▥ ⎫
▩ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ▩ ⎬ stopDemonstration
■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ⎮
■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ⎮
▩ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ▩ ⎮
▥ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ▥ ⎭
□ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ □
□ ▧ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ▨ □ ⎫
□ □ ▧ ■ ■ ■ ■ ■ ■ ■ ■ ■ ■ ▨ □ □ ⎬ toNextSentence
□ □ □ ▧ ■ ■ ■ ■ ■ ■ ■ ■ ▨ □ □ □ ⎮
□ □ □ □ □ ▤ ▩ ■ ■ ▩ ▤ □ □ □ □ □ ⎭
Каждый обработчик занимает горизонтальную полосу заданной толщины.*/

import android.view.MotionEvent;
import android.view.View;

public class ClickRouter implements View.OnTouchListener {
    private Runnable[] callMatrix;

    ClickRouter(
            Runnable toPreviousSentence,
            Runnable stopDemonstration,
            Runnable toNextSentence
    ) {
        Runnable nullRunnable = new Runnable() {
            @Override
            public void run() {
            }
        };
        this.callMatrix = new Runnable[]{
                toPreviousSentence, toPreviousSentence, toPreviousSentence, toPreviousSentence,
                nullRunnable,
                stopDemonstration, stopDemonstration, stopDemonstration,
                stopDemonstration, stopDemonstration, stopDemonstration,
                nullRunnable,
                toNextSentence, toNextSentence, toNextSentence, toNextSentence
        };
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.performClick();  // Provides event ascent for third-party handlers.
        if (event.getAction() == MotionEvent.ACTION_UP){
            this.callMatrix[
                    (int)(event.getY() / v.getHeight() * this.callMatrix.length)
            ].run();
        }
        return true;
    }
}
