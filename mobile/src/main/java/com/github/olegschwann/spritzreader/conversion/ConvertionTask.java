package com.github.olegschwann.spritzreader.conversion;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConvertionTask extends AsyncTask<Void, Void, Void> {
    final char[] vowels = {'а', 'о', 'и', 'е', 'ё', 'э', 'ы', 'у', 'ю', 'я'};

    private Context mContext;

    public ConvertionTask(Context context){
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("task lifecycle", "Begin");
    }


    private static String loadDictionaryJSON(Context ctx, String filename) {
        String json = null;
        try {
            InputStream is = ctx.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static int indexOf(Pattern pattern, String s) {
        Matcher matcher = pattern.matcher(s);
        return matcher.find() ? matcher.start() : -1;
    }

    private ArrayList<String> getSentencesArray(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream is = mContext.getAssets().open(filename);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8 ));

        String str;

        while ((str = br.readLine()) != null) {
            sb.append(str);
            sb.append(' ');
        }

        br.close();

        String rawText =  sb.toString();
        ArrayList<String> res = new ArrayList<String>();

        Pattern senntenceEnding = Pattern.compile("[.!?]");

        while (indexOf(senntenceEnding, rawText) > 0) {
            int sentenceFinishPos = indexOf(senntenceEnding, rawText);
            res.add(rawText.substring(0, sentenceFinishPos + 1));

            rawText = rawText.substring(sentenceFinishPos + 1);
        }


        return res;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        JSONObject JSONRoot = new JSONObject();
                try {
                    JSONObject dictionary;
                    dictionary = new JSONObject(loadDictionaryJSON(mContext, "words_accent.json"));

                    ArrayList<String> SENTENCE = getSentencesArray("example.txt");

                    JSONRoot.put("from", "Александр Попов");
                    JSONRoot.put("subject", "Лекция: 'Цифровая трансформация'");

                    JSONArray JSONBody = new JSONArray();

                    for (String sentence : SENTENCE) {
                        String [] words = sentence.split(" ", 0);

                        JSONArray JSONSentence = new JSONArray();

                        for (String word : words) {
                            JSONObject JSONWord = new JSONObject();

                            int accentPos;
                            if (dictionary.has(word)) {
                                accentPos = dictionary.getInt(word);
                            } else {
                                accentPos = word.length() / 2;
                            }

                            String left = "";
                            String center = "";
                            String right = "";


                            if ((accentPos > word.length() - 1) || (word.length() == 1)) {
                                center = word;
                            } else if (accentPos == 0) {
                                center = String.valueOf(word.charAt(accentPos));
                                right = word.substring(accentPos + 1);
                            } else if (accentPos == word.length() - 1) {
                                left = word.substring(0, accentPos);
                                center = String.valueOf(word.charAt(accentPos));
                            } else {
                                left = word.substring(0, accentPos);
                                center = String.valueOf(word.charAt(accentPos));
                                right = word.substring(accentPos + 1);
                            }

                            if (left.length() > 0)    JSONWord.put("l", left);
                            if (center.length() > 0) JSONWord.put("c", center);
                            if (right.length() > 0)   JSONWord.put("r", right);
//                            JSONWord.put("d", "хз");

                            JSONSentence.put(JSONWord);
                        }
                        JSONBody.put(JSONSentence);
                    }

                    JSONRoot.put("body", JSONBody);

                } catch (JSONException | IOException e) {
                    Log.d("parsed", e.toString());
                    e.printStackTrace();
                }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        Log.d("task lifecycle", "End");
    }
}
