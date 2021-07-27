package es.upm.oeg.cidercl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import eu.monnetproject.wsd.no.ml.esa.FileHandling;
import eu.monnetproject.wsd.utils.Language;

/**
 * class to handle stop words
 * 
 * @author Jorge Gracia
 * @author Kartik Asooja
 *
 */
public class StopWords {	

	private HashSet<String> stopWords = new HashSet<String>();
	private Properties config;
	String configFilePath = "eu.monnetproject.wsd.properties";
	public StopWords() {
		loadConfig(configFilePath);
	}

	public String removeStopWords(Language language, String text) {		
		text = text.replace("[",  " ").replace("]", " ").replace("."," ").toLowerCase();  //list of strings converted into a single string are enclosed in square brackets; we remove them here
		StringTokenizer tokenizer = new StringTokenizer(text);
		getStopWords(language);
		StringBuffer textWithoutStopWords = new StringBuffer();
		while(tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if(!stopWords.contains(token)) 
				textWithoutStopWords.append(token + " ");			
		}
		return textWithoutStopWords.toString().trim();
	}
	
	public List<String> removeStopWords(Language language, List<String> text) {
		getStopWords(language);
		List<String> textWithOutStopWords = new ArrayList<String>();
		for(String token : text) {
			if(!stopWords.contains(token)) 
				textWithOutStopWords.add(token);			
		}
		return textWithOutStopWords;
	}

	private void loadConfig(String configFilePath) {
		if(config == null) {
			try {
				Runtime.getRuntime().gc();
				config =  new Properties();
				FileInputStream fis = new FileInputStream(configFilePath);
				//config.load(new FileInputStream(configFilePath));
				config.load(fis);
				fis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private HashSet<String> getStopWords(Language language) {
		//System.out.println("the language is ..." + language.asStringCode());
		//System.out.println("1");
		String filePath = config.getProperty("snowballStopWordsFolder");
		//System.out.println("2 " + filePath );
		filePath = filePath + "/" + language.asStringCode() + ".txt";
		//System.out.println("3 " + filePath );
		File stopWordsFile = new File(filePath);
		//System.out.println("4 ");
		String stopWordsFileString = FileHandling.extractText(stopWordsFile);
		//System.out.println("5 ");
		String[] splits = stopWordsFileString.split("\n");
		//	HashSet<String> stopWords = new HashSet<String>();
		for(String line : splits) {
			line = line.trim();
			if(!line.contentEquals("")) 
				if(!(line.charAt(0) == '|')) {
					if(line.contains("|")) {
						line = line.trim();
						char first = line.charAt(0);
						String myWord = line.substring(line.indexOf(first),line.indexOf(" "));
						stopWords.add(myWord);
					} else {
						stopWords.add(line);						
					}
				}
		}
		return stopWords;
	}
	
	// uncomment for testing
//	public static void main(String[] args) throws IOException {
//
//		String one = "[the reviews of a paper]";
//		String two = "[revisiones de un artículo]";		
//
//		StopWords langOneStopWords = new StopWords();
//		StopWords langTwoStopWords = new StopWords();
//		
//		String newOne = langOneStopWords.removeStopWords(new Language("en"), one);		
//		String newTwo = langTwoStopWords.removeStopWords(new Language("es"), two);
//		
//		System.out.println(newOne);
//		System.out.println(newTwo);
//
//	}

}
