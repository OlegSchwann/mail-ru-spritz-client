package com.github.olegschwann.spritzreader;

import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.wearable.activity.WearableActivity;

public class MainActivity extends WearableActivity implements OnFragmentInteractionListener{
    // 3 screen fragments
    private LetterList letterList;
    private FullLetterText fullLetterText;
    private SpritzReader spritzReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FIXME: How to use android.support.v4.app.FragmentActivity#getSupportFragmentManager() ?
        FragmentManager fragmentManager = getFragmentManager();

        this.letterList = (LetterList) fragmentManager.findFragmentByTag(LetterList.TAG);
        if (this.letterList == null) {
            this.letterList = new LetterList();
        }

        this.fullLetterText = (FullLetterText) fragmentManager.findFragmentByTag(FullLetterText.TAG);
        if (this.fullLetterText == null) {
            this.fullLetterText = new FullLetterText();
        }

        this.spritzReader = (SpritzReader) fragmentManager.findFragmentByTag(SpritzReader.TAG);
        if (this.spritzReader == null) {
            this.spritzReader = new SpritzReader();
        }

        showLetterList(false);
    }

    // Сюда приходят все события от фрагментов. Паттерн router.
    public void onFragmentInteraction(Uri uri){

    }

    private void showLetterList(boolean addToHistory) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.content, this.letterList, LetterList.TAG);
        if (addToHistory){
            // Не надо добавлять в историю первое состояние после запуска приложения,
            // когда фрагмент вставляется в пустую activity.
            transaction.addToBackStack(LetterList.TAG);

        }
        transaction.commit();
    }

    private void showFullLetterText(boolean addToHistory) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.content, this.fullLetterText, FullLetterText.TAG);
        if (addToHistory){
            transaction.addToBackStack(FullLetterText.TAG);

        }
        transaction.commit();
    }

    private void showSpritzReader(boolean addToHistory) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.content, this.spritzReader, SpritzReader.TAG);
        if (addToHistory){
            transaction.addToBackStack(SpritzReader.TAG);

        }
        transaction.commit();
    }
}
