package es.unizar.sid.semanticmeasures.impl.crosslingual;


import java.util.ArrayList;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.nd4j.linalg.api.ndarray.INDArray;

import es.unizar.sid.semanticmeasures.Relatedness;
import es.upm.oeg.cidercl.util.StopWords;
import eu.monnetproject.wsd.utils.Language;


public abstract class WordVectorsCL implements Relatedness{
	
	//private static final String SRC_VECTORS_PATH = "./embeddings/crosslingual/en-es/SRC_MAPPED_en.EMB";
	//private static final Word2Vec src_vector = WordVectorSerializer.readWord2VecModel(SRC_VECTORS_PATH);
	//private static final String TRG_VECTORS_PATH = "./embeddings/crosslingual/en-es/TRG_MAPPED_es.EMB";
	//private static final Word2Vec trg_vector = WordVectorSerializer.readWord2VecModel(TRG_VECTORS_PATH);
	
    private Word2Vec src_vec;
    private String langS;
    private Word2Vec trg_vec;
    private String langT;
	
	public WordVectorsCL(String langS, String langT){		
		//this.src_vec = src_vector;
		String SRC_VECTORS_PATH = "G:\\Unidades compartidas\\LanguageResources_LOD\\CIDER-EM_embeddings\\crosslingual\\" + langS + "-" + langT + "\\SRC_MAPPED_" + langS + ".EMB";
		//String SRC_VECTORS_PATH = "./embeddings/crosslingual/" + langS + "-" + langT + "/SRC_MAPPED_" + langS + ".EMB";
		this.src_vec = WordVectorSerializer.readWord2VecModel(SRC_VECTORS_PATH);
		//this.trg_vec = trg_vector;
		//String TRG_VECTORS_PATH = "./embeddings/crosslingual/" + langS + "-" + langT + "/TRG_MAPPED_" + langT + ".EMB";
		String TRG_VECTORS_PATH = "G:\\Unidades compartidas\\LanguageResources_LOD\\CIDER-EM_embeddings\\crosslingual\\" + langS + "-" + langT + "\\TRG_MAPPED_" + langT + ".EMB";
		
		this.trg_vec = WordVectorSerializer.readWord2VecModel(TRG_VECTORS_PATH);
		this.langS = langS;
		this.langT = langT;
	}
	
	public WordVectorsCL(){
	}
	
	public Word2Vec getSRC_map() {return this.src_vec;}
	public Word2Vec getTRG_map() {return this.trg_vec;}
	public String getLangS() {return this.langS;}
	public String getLangT() {return this.langT;}
    
    /*
     * Compute the cosine similarity between two words
     */
//    public double score(String s, String t) {		
//	    return vec.similarity(StringTools.splitCamelCase(s),StringTools.splitCamelCase(t));
//	}

	public INDArray getMeanVectorFromWords(ArrayList<String> words, String lang) {
		
		Word2Vec vec = null;
		if(lang.equals(this.langS)) 		
			vec = this.src_vec;
		else {
			if(lang.equals(this.langT))
				vec = this.trg_vec;
		}		
		INDArray meanVector = null;
		ArrayList<String> wordsInModel = new ArrayList<>();			
		if(vec != null) {
			StopWords stopWords = new StopWords();
			for(String w : words) {				
				if(vec.hasWord(w)) wordsInModel.add(w);	
				else {
					String [] array = w.split("(?=\\p{Upper})");
					if (array.length >1) {						
						for (String a : array){
							a  = stopWords.removeStopWords(new Language(lang), a);
							if(!a.equals("") && vec.hasWord(a)) wordsInModel.add(a);
						}
					}					
				}
			}
		}		
		if(!wordsInModel.isEmpty()) meanVector = vec.getWordVectorsMean(wordsInModel);	
		
		return meanVector;
	}

	
}