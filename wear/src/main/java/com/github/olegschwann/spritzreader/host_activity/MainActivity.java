package com.github.olegschwann.spritzreader.host_activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.wearable.activity.WearableActivity;

import com.github.olegschwann.spritzreader.R;
import com.github.olegschwann.spritzreader.application.Application;
import com.github.olegschwann.spritzreader.letter_text.FragmentLetterText;
import com.github.olegschwann.spritzreader.letters_list.FragmentLetterList;
import com.github.olegschwann.spritzreader.spritz_reader.FragmentSpritzReader;
import com.github.olegschwann.spritzreader.spritz_reader.Words;
import com.google.gson.Gson;

public class MainActivity extends WearableActivity implements InteractionBus {
    // 3 screen fragments
    private FragmentLetterList fragmentLetterList;
    private FragmentLetterText fragmentLetterText;
    private FragmentSpritzReader fragmentSpritzReader;

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

        this.fragmentSpritzReader = (FragmentSpritzReader) fragmentManager.findFragmentByTag(FragmentSpritzReader.TAG);
        if (this.fragmentSpritzReader == null) {
            this.fragmentSpritzReader = new FragmentSpritzReader();
        }

//        this.fragmentLetterList.setData(new LettersHeaders(((Application) this.getApplication()).database.letterDao().loadHeaders()));
//        showLetterList(false);

        this.fragmentLetterText
                .setSentences(
                        ((Application)this.getApplication())
                                .database
                                .letterDao()
                                .loadBody("1")
                                .getSentenceBody()
                );
        showFullLetterText(false);

//        this.fragmentSpritzReader.setWordsAndDelay(
//                new Gson().fromJson(
//                        ((Application)this.getApplication())
//                                .database
//                                .letterDao()
//                                .loadBody("1")
//                                .jsonBody,
//                        Words.class
//                ),
//                60 * 1000 / 500
//        );
//        showSpritzReader(false);
    }

    private void showLetterList(boolean addToHistory) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content, this.fragmentLetterList, FragmentLetterList.TAG);
        if (addToHistory) {
            // Не надо добавлять в историю первое состояние после запуска приложения,
            // когда фрагмент вставляется в пустую activity.
            transaction.addToBackStack(FragmentLetterList.TAG);

        }
        transaction.commit();
    }

    private void showFullLetterText(boolean addToHistory) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content, this.fragmentLetterText, FragmentLetterText.TAG);
        if (addToHistory) {
            transaction.addToBackStack(FragmentLetterText.TAG);

        }
        transaction.commit();
    }

    private void showSpritzReader(boolean addToHistory) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content, this.fragmentSpritzReader, FragmentSpritzReader.TAG);
        if (addToHistory) {
            transaction.addToBackStack(FragmentSpritzReader.TAG);

        }
        transaction.commit();
    }

    // TODO: implement.

    @Override
    public void transitionToListOfLetters(int letterId) {
        if (letterId == 0) {
            // открыть первое письмо.
        }
        throw new NoSuchFieldError();
    }

    @Override
    public void transitionToTextOfLetter(int letterId, int sentenceIndex) {
        throw new NoSuchFieldError();
    }

    @Override
    public void transitionToSpritz(int letterId, int sentenceIndex) {
        throw new NoSuchFieldError();
    }

    @Override
    public void transitionToTextOfLetterOnPhone(int letterId) {
        throw new NoSuchFieldError();
    }

    @Override
    public void transitionToListOfLettersOnPhone() {
        throw new NoSuchFieldError();
    }
}
