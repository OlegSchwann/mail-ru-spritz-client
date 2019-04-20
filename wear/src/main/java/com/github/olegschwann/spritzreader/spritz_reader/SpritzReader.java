package com.github.olegschwann.spritzreader.spritz_reader;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.olegschwann.spritzreader.OnFragmentInteractionListener;
import com.github.olegschwann.spritzreader.R;


public class SpritzReader extends Fragment {
    public static final String TAG = "SpritzReaderFragment";

    private OnFragmentInteractionListener mListener;

    // источник, выдающий слова письма по одному.
    private WordProvider words;

    private TextView leftPart;
    private TextView centerLetter;
    private TextView rightPart;
    private View spritzScreen;

    private Handler timerHandler;
    // время демонстрации 1 слова в миллисекундах.
    private int delay;

    // статус: включена ли демонстрация в данный момент.
    private boolean demonstrationActive;

    // Функция обратного вызова для перерисовки экрана Spritz.
    // Вызывая WordProvider.next(), двигает на 1 слово вперёд текст письма.
    private Runnable changeToNextWord;

    // Продвигает предложение на 1 вверх.
    private Runnable toPreviousSentence;

    // Останавливает демонстрацию spritz, если она есть в данный момент.
    // или наоборот, включает.
    private Runnable stopOrStartDemonstration;

    // Продвигает предложение на 1 вниз.
    private Runnable toNextSentence;

    public SpritzReader() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.words = new WordProvider();
        this.timerHandler = new Handler();

        this.changeToNextWord = new Runnable() {
            @Override
            public void run() {
                int wordDelay = mChangeToNextWord();
                if (demonstrationActive) {
                    timerHandler.postDelayed(this, wordDelay);
                }
            }
        };

        this.toPreviousSentence = new Runnable() {
            @Override
            public void run() {
                words.toPreviousSentence();
                if(demonstrationActive){
                    timerHandler.removeCallbacks(changeToNextWord);
                }
                mChangeToNextWord();
                if(demonstrationActive){
                    timerHandler.postDelayed(changeToNextWord, 800);
                }
            }
        };

        this.stopOrStartDemonstration = new Runnable() {
            @Override
            public void run() {
                if(demonstrationActive){
                    timerHandler.removeCallbacks(changeToNextWord);
                } else {
                    timerHandler.postDelayed(changeToNextWord, 800);
                }
                demonstrationActive = !demonstrationActive;
            }
        };

        this.toNextSentence = new Runnable() {
            @Override
            public void run() {
                words.toNextSentence();
                if(demonstrationActive){
                    timerHandler.removeCallbacks(changeToNextWord);
                }
                mChangeToNextWord();
                if(demonstrationActive){
                    timerHandler.postDelayed(changeToNextWord, 800);
                }
            }
        };

        // TODO: нормальный внутренний интерфейс инициализации фрагмента, параметризуемый данными письма.
        this.delay = 60*1000/500;
    }

    private int mChangeToNextWord() {
        try {
            Word word = words.next();
            leftPart.setText(word.left); // text can be null
            centerLetter.setText(word.center);
            rightPart.setText(word.right);
            int wordDelay = delay;
            if (word.delay != null) {
                wordDelay *= word.delay;
            }
            return wordDelay;
        } catch (WordProvider.NoSuchWordException e){
            leftPart.setText(e.lastExistingWord.left); // text can be null
            centerLetter.setText(e.lastExistingWord.center);
            rightPart.setText(e.lastExistingWord.right);
            this.demonstrationActive = false;
            // Если пользователь домотал кликами до конца, то показывается последнее слово.
            return 0;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.spritz_reader_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.leftPart = (TextView) view.findViewById(R.id.spritz_left_part);
        this.centerLetter = (TextView) view.findViewById(R.id.spritz_center_letter);
        this.rightPart = (TextView) view.findViewById(R.id.spritz_right_part);

        this.spritzScreen = (View) view.findViewById(R.id.spritz_screen);
        this.spritzScreen.setOnTouchListener(new ClickRouter(
                this.toPreviousSentence,
                this.stopOrStartDemonstration,
                this.toNextSentence
        ));

        mChangeToNextWord(); // устанавливаем первое слово письма.
        // TODO:  добавить анимацию в начале чтения, как на spritz.com
        // Она должна сфокусировать человека посередине экрана.
        demonstrationActive = true;
        timerHandler.postDelayed(this.changeToNextWord, 800);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
