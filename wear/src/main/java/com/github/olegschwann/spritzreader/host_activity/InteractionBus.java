package com.github.olegschwann.spritzreader.host_activity;

import android.support.annotation.Nullable;

public interface InteractionBus {
    // letterId - при открытии список прокручивается до этого письма, если не Null
    void transitionToListOfLetters(@Nullable String letterId);

    // sentenceIndex - при открытии список прокручивается до этого предложения.
    void transitionToTextOfLetter(String letterId, int sentenceIndex);

    void transitionToSpritz(String letterId, int sentenceIndex);

    void transitionToTextOfLetterOnPhone(String letterId);

    void transitionToListOfLettersOnPhone();
}
