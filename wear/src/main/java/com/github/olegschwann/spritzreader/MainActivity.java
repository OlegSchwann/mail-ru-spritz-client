package com.github.olegschwann.spritzreader;

import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.wearable.activity.WearableActivity;

import com.github.olegschwann.spritzreader.letter_text.FragmentLetterText;
import com.github.olegschwann.spritzreader.letters_list.FragmentLetterList;

public class MainActivity extends WearableActivity implements OnFragmentInteractionListener{
    // 3 screen fragments
    private FragmentLetterList fragmentLetterList;
    private FragmentLetterText fragmentLetterText;
    private SpritzReader spritzReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();

        this.fragmentLetterList = (FragmentLetterList) fragmentManager.findFragmentByTag(FragmentLetterList.TAG);
        if (this.fragmentLetterList == null) {
            this.fragmentLetterList = new FragmentLetterList();
        }

        this.fragmentLetterText = (FragmentLetterText) fragmentManager.findFragmentByTag(FragmentLetterText.TAG);
        if (this.fragmentLetterText == null) {
            this.fragmentLetterText = new FragmentLetterText();
        }

        this.spritzReader = (SpritzReader) fragmentManager.findFragmentByTag(SpritzReader.TAG);
        if (this.spritzReader == null) {
            this.spritzReader = new SpritzReader();
        }

        showLetterList(false);
//        showSpritzReader(false);
//        showFullLetterText(false);
    }

    // Сюда приходят все события от фрагментов. Паттерн router.
    public void onFragmentInteraction(Uri uri){

    }

    private void showLetterList(boolean addToHistory) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content, this.fragmentLetterList, FragmentLetterList.TAG);
        if (addToHistory){
            // Не надо добавлять в историю первое состояние после запуска приложения,
            // когда фрагмент вставляется в пустую activity.
            transaction.addToBackStack(FragmentLetterList.TAG);

        }
        transaction.commit();
    }

    private void showFullLetterText(boolean addToHistory) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content, this.fragmentLetterText, FragmentLetterText.TAG);
        if (addToHistory){
            transaction.addToBackStack(FragmentLetterText.TAG);

        }
        transaction.commit();
    }

    private void showSpritzReader(boolean addToHistory) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content, this.spritzReader, SpritzReader.TAG);
        if (addToHistory){
            transaction.addToBackStack(SpritzReader.TAG);

        }
        transaction.commit();
    }
}
