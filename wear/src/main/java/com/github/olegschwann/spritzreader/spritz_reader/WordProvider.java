package com.github.olegschwann.spritzreader.spritz_reader;

import java.util.NoSuchElementException;

// Класс итератор, выдаёт слова по одному,
// поддерживает перемотку на предложение вперёд и назад.
public class WordProvider {
    private Words letter;
    private int currentSentence;
    private int currentWord;

    WordProvider(Words letter) {
        this.letter = letter;
        this.currentWord = 0;
        this.currentSentence = 0;
    }

    class NoSuchWordException extends NoSuchElementException{
        public Word lastExistingWord;

        // Нужно показывать последнее слово, если случайно отмотали за пределы текста.
        NoSuchWordException(Word w){
            this.lastExistingWord = w;
        }
    }


    // Получить следующее слово, продвинуться на 1.
    public Word next() throws NoSuchWordException {
        if (this.currentSentence >= this.letter.size()) {
            int lastSentence = this.letter.size() - 1;
            int lastWorlds = this.letter.get(lastSentence).size() - 1;
            throw new NoSuchWordException(this.letter.get(lastSentence).get(lastWorlds));
        }

        Word word = this.letter.get(this.currentSentence).get(this.currentWord);

        this.currentWord += 1;
        if (this.currentWord >= this.letter.get(this.currentSentence).size()) {
            this.currentWord = 0;
            this.currentSentence += 1;
        }

        return word;
    }

    // Перейти к началу предыдущего предложения
    // Или к началу текущего, если мы находимся в первом предложении.
    public void toPreviousSentence() {
        if (this.currentSentence != 0) {
            this.currentSentence -= 1;
        }
        this.currentWord = 0;
    }

    // Перейти к первому слову следующего предложения, или
    // сохранить бросание NoSuchElementException на каждый вызов Next
    public void toNextSentence() {
        if (this.currentSentence != this.letter.size()) {
            this.currentSentence += 1;
        }
        this.currentWord = 0;
    }

    // Установить номер предложения, с которого будет начато воспроизведение.
    public void setSentenceNumber(int number) {
        this.currentSentence = number;
        this.currentWord = 0;
    }
}
