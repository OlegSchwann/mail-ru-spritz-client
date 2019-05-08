package com.github.olegschwann.spritzreader.spritz_reader;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.wear.widget.SwipeDismissFrameLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.olegschwann.spritzreader.Types.NullBundleException;
import com.github.olegschwann.spritzreader.host_activity.InteractionBus;
import com.github.olegschwann.spritzreader.R;

public class FragmentSpritzReader extends Fragment {
    public static final String TAG = "SpritzReaderFragment";

    // Видимые элементы интерфейса.
    private TextView leftPart;

    private TextView centerLetter;

    private TextView rightPart;

    private View spritzScreen;

    private SwipeDismissFrameLayout swipeDismissFrameLayout;

    // Данные, которые будут отображаться на экране.
    private Words words;

    // Итератор, выдающий слова письма по одному для отображения.
    // Содержит логику перемещения между предложениями.
    private WordProvider wordProvider;

    // Очередь, управляющая событиями времени.
    // Ей ставятся задачи "вызови это через секунду."
    private Handler timerHandler;

    // Время демонстрации 1 слова в миллисекундах.
    private int delay;

    // Позиция, с которой начинается отображение.
    private int sentenceNumber;

    // Статус отображения: включена ли демонстрация в данный момент.
    private boolean demonstrationActive;

    // Функция для перерисовки экрана Spritz.
    // Вызывает WordProvider.next(), двигает на 1 слово вперёд текст письма.
    private Runnable changeToNextWord;

    // Продвигает предложение на 1 вверх.
    private Runnable toPreviousSentence;

    // Останавливает / включает демонстрацию Spritz, меняя состояние на противоположное.
    private Runnable stopOrStartDemonstration;

    // Продвигает предложение на 1 вниз.
    private Runnable toNextSentence;

    public FragmentSpritzReader() {
        // Required empty public constructor
    }

    public void setWordsPositionDelay(Words words, int position, int delay) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Words.TAG, words);
        bundle.putInt("sentenceNumber", position);
        bundle.putInt("delay", delay);
        setArguments(bundle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // region Recovery
        Bundle bundle = getArguments();
        if (bundle == null) {
            throw new NullBundleException("Necessary to parameterize FragmentSpritzReader with setWordsPositionDelay()");
        }

        this.words = bundle.getParcelable(Words.TAG);
        this.sentenceNumber = bundle.getInt("sentenceNumber");
        this.delay = bundle.getInt("delay");
        // endregion

        this.wordProvider = new WordProvider(this.words);
        this.wordProvider.setSentenceNumber(this.sentenceNumber);
        this.timerHandler = new Handler();

        this.changeToNextWord = new Runnable() {
            @Override
            public void run() {
                int wordDelay = changeToNextWord();
                if (demonstrationActive) {
                    timerHandler.postDelayed(this, wordDelay);
                }
            }
        };

        this.toPreviousSentence = new Runnable() {
            @Override
            public void run() {
                wordProvider.toPreviousSentence();
                if (demonstrationActive) {
                    timerHandler.removeCallbacks(changeToNextWord);
                }
                changeToNextWord();
                if (demonstrationActive) {
                    timerHandler.postDelayed(changeToNextWord, 800);
                }
            }
        };

        this.stopOrStartDemonstration = new Runnable() {
            @Override
            public void run() {
                if (demonstrationActive) {
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
                wordProvider.toNextSentence();
                if (demonstrationActive) {
                    timerHandler.removeCallbacks(changeToNextWord);
                }
                changeToNextWord();
                if (demonstrationActive) {
                    timerHandler.postDelayed(changeToNextWord, 800);
                }
            }
        };
    }

    private int changeToNextWord() {
        try {
            Word word = wordProvider.next();
            leftPart.setText(word.left); // text can be null
            centerLetter.setText(word.center);
            rightPart.setText(word.right);
            int wordDelay = delay;
            if (word.delay != null) {
                wordDelay *= word.delay;
            }
            return wordDelay;
        } catch (WordProvider.NoSuchWordException e) {
            leftPart.setText(e.lastExistingWord.left); // text can be null
            centerLetter.setText(e.lastExistingWord.center);
            rightPart.setText(e.lastExistingWord.right);
            this.demonstrationActive = false;
            // Если пользователь домотал кликами до конца, то показывается последнее слово.
            return 0;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
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

        this.swipeDismissFrameLayout = view.findViewById(R.id.spritz_swipe_dismiss_root);
        this.swipeDismissFrameLayout.addCallback(new SwipeDismissFrameLayout.Callback() {
            @Override
            public void onDismissed(SwipeDismissFrameLayout layout) {
                ((InteractionBus)getActivity()).dismiss();
            }
        });

        // TODO: может быть баг или перезатирание. Нужно ли это?
        changeToNextWord(); // устанавливаем первое слово письма.
        // TODO:  добавить анимацию в начале чтения, как на spritz.com
        // Она должна сфокусировать человека посередине экрана.
        demonstrationActive = true;
        timerHandler.postDelayed(this.changeToNextWord, 800);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
