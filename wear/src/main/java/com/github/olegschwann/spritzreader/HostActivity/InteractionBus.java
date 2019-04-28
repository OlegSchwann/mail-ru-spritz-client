package com.github.olegschwann.spritzreader.HostActivity;

import android.support.annotation.Nullable;

public interface InteractionBus {
    // letterId - при открытии список прокручивается до этого письма, если не Null
    void transitionToListOfLetters(@Nullable int letterId);

    // sentenceIndex - при открытии список прокручивается до этого предложения.
    void transitionToTextOfLetter(int letterId, int sentenceIndex);

    void transitionToSpritz(int letterId, int sentenceIndex);

    void transitionToTextOfLetterOnPhone(int letterId);

    void transitionToListOfLettersOnPhone();
}
