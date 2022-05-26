package com.abc;

import com.abc.reader.InputFileReader;

import java.io.FileNotFoundException;
import java.util.*;

public class ConcordanceGenerator {
    private static final String SPACES= "                   ";
    private static final String WORD_DELEMETER= " ";
    private static final String EMPTY_STRING="";
    private static final String REGEX="[.,?:!]";
    private static final Set<String> ESCAPE_WORDS=new HashSet<>(Collections.singletonList("i.e."));

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String filePath= scanner.nextLine();

        var concordance=new ConcordanceGenerator().generateConcordance(filePath);
        for(String line: concordance){
            System.out.println(line);
        }
    }

    public List<String> generateConcordance(String filePath) throws FileNotFoundException {
        //TODO validate for filePath
        List<String> lines= InputFileReader.readFileContents(filePath);

        Map<String,Word> wordsMap= new TreeMap<>();
        List<String> response= new LinkedList<>();
        int sentence=1;
        for(String line: lines){
            if(line.trim().equals(EMPTY_STRING)) continue;
            String[] words = line.split(WORD_DELEMETER);
            for(String word: words){
                word=word.toLowerCase(Locale.ENGLISH);
                boolean shouldIncreaseSentenceCounter=false;
                //TODO write regex instead individual checks for .?!
                if(!ESCAPE_WORDS.contains(word) && (word.contains(".")) || word.contains("!")|| word.contains("?")){
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
            response.add(wordsMap.get(key).toString());
        }
        return response;
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
