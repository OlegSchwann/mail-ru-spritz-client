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
    private Runnable[] сallMatrix;

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
        this.сallMatrix = new Runnable[]{
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
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            this.сallMatrix[
                    (int)(event.getY() / v.getHeight() * this.сallMatrix.length)
            ].run();
        }
        return true;
    }
}
