package com.abc;

import com.abc.reader.InputFileReader;

import java.util.*;

public class ConcordanceGenerator {
    private static final String SPACES= "                   ";
    private static final String WORD_DELEMETER= " ";
    private static final String EMPTY_STRING="";
    private static final String REGEX="[.,?:!]";
    private static final Set<String> ESCAPE_WORDS=new HashSet<>(Arrays.asList("i.e."));

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filePath= scanner.nextLine();
        //TODO validate for file path
        List<String> lines= InputFileReader.readFileContents(filePath);

        Map<String,Word> wordsMap= new TreeMap<>();
        int sentence=1;
        for(String line: lines){
            if(line.trim().equals(EMPTY_STRING)) continue;
            String[] words = line.split(WORD_DELEMETER);
            for(String word: words){
                word=word.toLowerCase(Locale.ENGLISH);
                boolean shouldIncreaseSentenceCounter=false;
                //TODO write regex instead individual checks for .?!
                if(!ESCAPE_WORDS.contains(word) && (word.indexOf(".")!=-1) || word.indexOf("!")!=-1
                || word.indexOf("?")!=-1){
                    shouldIncreaseSentenceCounter=true;
                }
                if(!ESCAPE_WORDS.contains(word)){
                    word= word.replaceAll(REGEX, EMPTY_STRING);
                }
                if(word.equals(EMPTY_STRING)) continue;
                wordsMap.putIfAbsent(word, new Word(word));
                Word existingword= wordsMap.get(word);
                existingword.incrementWordCount();
                existingword.addSentenceNumber(sentence);
                if(shouldIncreaseSentenceCounter) {
                    sentence++;
                }
            }
        }
        for(String key: wordsMap.keySet()){
            System.out.println(wordsMap.get(key).toString());
        }
    }

    static class Word{
        final private String word;
        final private List<String> sentences= new ArrayList<>();
        private int count=0;
        public Word(String word){
            this.word=word;
        }
        public void addSentenceNumber(int sentenceNo){
            this.sentences.add(Integer.toString(sentenceNo));
        }

        public void incrementWordCount(){
            this.count++;
        }

        @Override
        public String toString() {
            return word+SPACES+"{"+count+":"+ String.join(",", sentences)+ "}";
        }
    }
}
