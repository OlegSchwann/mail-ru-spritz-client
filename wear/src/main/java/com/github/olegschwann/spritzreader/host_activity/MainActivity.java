package com.github.olegschwann.spritzreader.host_activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.wearable.activity.WearableActivity;
import com.google.gson.Gson;
import java.util.ArrayList;

import com.github.olegschwann.spritzreader.R;
import com.github.olegschwann.spritzreader.application.Application;
import com.github.olegschwann.spritzreader.database.LettersHeaders;
import com.github.olegschwann.spritzreader.letter_text.FragmentLetterText;
import com.github.olegschwann.spritzreader.letters_list.FragmentLetterList;
import com.github.olegschwann.spritzreader.spritz_reader.FragmentSpritzReader;
import com.github.olegschwann.spritzreader.spritz_reader.Words;

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

        // TODO: Тут жолжен быть роутер, открывающий или Spritz, или список.
        this.fragmentLetterList.setData(new LettersHeaders(
                ((Application) this.getApplication())
                        .database
                        .letterDao()
                        .loadHeaders()));
        showLetterList(false);

    }

    // region ShowFragment
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
    // endregion

    // region InteractionBus
    @Override
    public void transitionToListOfLetters(String letterId) {
        // TODO: use letterId.
        this.fragmentLetterList.setData(new LettersHeaders(
                ((Application) this.getApplication())
                        .database
                        .letterDao()
                        .loadHeaders()));
        showLetterList(true);
    }

    @Override
    public void transitionToTextOfLetter(String letterId, int sentenceIndex) {
        // TODO: use sentenceIndex.
        ArrayList<String> sentenceBody = ((Application) this.getApplication())
                .database
                .letterDao()
                .loadBody(letterId)
                .getSentenceBody();
        this.fragmentLetterText.setSentences(sentenceBody, letterId);
        showFullLetterText(true);
    }

    @Override
    public void transitionToSpritz(String letterId, int sentenceIndex) {
        // TODO: use sentenceIndex.
        Words words = new Gson().fromJson(
             ((Application) this.getApplication())
                 .database
                 .letterDao()
                 .loadBody(letterId)
                 .jsonBody,
             Words.class);
        this.fragmentSpritzReader.setWordsPositionDelay(words, sentenceIndex, 60 * 1000 / 500);
        this.showSpritzReader(true);
    }

    @Override
    public void transitionToTextOfLetterOnPhone(String letterId) {
        // TODO: implement.
        throw new NoSuchFieldError();
    }

    @Override
    public void transitionToListOfLettersOnPhone() {
        // TODO: implement.
        throw new NoSuchFieldError();
    }
    // endregion
}
