package sid.cider.em.app.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import es.upm.oeg.cidercl.aligner.ProcessAlignment;
import es.upm.oeg.cidercl.extraction.OntologyExtractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.UUID;

import es.unizar.sid.semanticmeasures.impl.monolingual.CosineSimilarityBetweenOntologyEntities;
import es.upm.oeg.cidercl.aligner.*;

import org.apache.commons.io.FilenameUtils;
import org.semanticweb.owl.align.Alignment;

@RestController
public class OntologyController {
	
	
	
	@RequestMapping(value = "upload-error", method = RequestMethod.POST)
	@ResponseBody
	public String testERRORS(
		@RequestParam("file1") MultipartFile file1,
		@RequestParam("file2") MultipartFile file2
	) throws Exception {
		String onto1Path = this.storeFile(file1,1);
		String onto2Path = this.storeFile(file2,2);

		URI o1 = new URI("file:" + onto1Path);
    	URI o2 = new URI("file:" + onto2Path);
    
   
    	
    	
	
		// delete output file
//		Files.delete(outputFilePath);
//		Files.delete(Paths.get(onto1Path));
//		Files.delete(Paths.get(onto2Path));
    	    	
		return "hello";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	@RequestMapping(value = "upload-onto", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Resource> testFileUpload(
		@RequestParam("file1") MultipartFile file1,
		@RequestParam("file2") MultipartFile file2
	) throws Exception {
		String onto1Path = this.storeFile(file1, 1);
		String onto2Path = this.storeFile(file2, 2);

		URI o1 = new URI("file:" + onto1Path);
    	URI o2 = new URI("file:" + onto2Path);
    	
		String outputFile = "result" + UUID.randomUUID().toString() + ".rdf";
        double threshold = 0.0025;
        
        System.out.println(o1.toString()+o2.toString()+outputFile+threshold);
    	ProcessAlignment align =  new ProcessAlignment(o1,o2, outputFile, threshold);
    	align.run();
    	
    	// Get output file
    	Path outputFilePath = Paths.get(outputFile);
		Resource resultFile = new UrlResource(outputFilePath.toUri());
		
		// Make response with output file
		ResponseEntity<Resource> response = ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + resultFile.getFilename() + "\"").body(resultFile);
		
	
		// delete output file
//		Files.delete(outputFilePath);
//		Files.delete(Paths.get(onto1Path));
//		Files.delete(Paths.get(onto2Path));
    	    	
		return response;
	}
	
	public String storeFile(MultipartFile file, int index) throws Exception {
		Path rootLocation = Paths.get("test");
		
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		//String filename = UUID.randomUUID().toString() + "." + extension;
		String filename = "test-"+ index+"-"+file.getOriginalFilename();
		Path destinationFile = rootLocation.resolve(
			Paths.get(filename)
		).normalize().toAbsolutePath();
		
		try {
			InputStream inputStream = file.getInputStream();
			Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			inputStream.close();
		} catch (IOException e) {
			System.err.println("Error storing the files:\n" + e.getMessage());
		}
				
		return destinationFile.toString();
	}
	
	
	@RequestMapping(path = "align/error", method = RequestMethod.GET)
	public int uploadERROR2 () throws Exception {
    	URI o1 = new URI("file:test/test-1-onto.rdf");  
    	URI o2 = new URI("file:test/test-2-onto.rdf");
		String outputFile = "pruebaconnombreshash_101-203.rdf";
        double threshold = 0.0025;
        System.out.println(o1.toString()+o2.toString()+outputFile+threshold);
    	ProcessAlignment align =  new ProcessAlignment(o1,o2, outputFile, threshold);
    	align.run();
    	return 666;
	}
	
	
	
	@RequestMapping(path = "align/ontology", method = RequestMethod.GET)
	public int testEndpoint () throws Exception {
    	URI o1 = new URI("file:test/OAEI11/benchmark/101/onto.rdf");
    	URI o2 = new URI("file:test/OAEI11/benchmark/203/onto.rdf");
		String outputFile = "CIDER-CL_101-203.rdf";
        double threshold = 0.0025;
    	ProcessAlignment align =  new ProcessAlignment(o1,o2, outputFile, threshold);
    	align.run();
    	return 666;
	}

	
	@RequestMapping(path = "align/ontology-online", method = RequestMethod.GET)
	public int testEndpoint3 () throws Exception {
    	URI o1 = new URI("http://oaei.ontologymatching.org/2011/benchmarks/101/onto.rdf");
    	URI o2 = new URI("http://oaei.ontologymatching.org/2011/benchmarks/203/onto.rdf");
		String outputFile = "CIDER-CL_101-203-online.rdf";
        double threshold = 0.0025;
    	ProcessAlignment align =  new ProcessAlignment(o1,o2, outputFile, threshold);
    	align.run();
    	return 666;
	}
	
	@RequestMapping("align/entities-fetch")
	@ResponseBody
	 public Param test(@RequestBody Param entities){
		String uri1 = entities.uri1;
		String uri2 = entities.uri2;	
		String ontology1 = entities.onto1;
		String ontology2 = entities.onto2;			
		String lang = entities.lang;
		//System.out.println(uri1 + "\n" + uri2 + "\n" + ontology1 + "\n"+ ontology2 + "\n" + lang);
		CosineSimilarityBetweenOntologyEntities measure = new CosineSimilarityBetweenOntologyEntities(lang);
		entities.alignmentScore = measure.getValue(OntologyExtractor.modelObtaining(ontology1), uri1 , OntologyExtractor.modelObtaining(ontology2), uri2);
		//System.out.println(measure.getValue(OntologyExtractor.modelObtaining(ontology1), uri1 , OntologyExtractor.modelObtaining(ontology2), uri2));
    	return entities;
	 }  
	
	
		
;
}

class Param{
    
    public String uri1;
    public String uri2;
    public String onto1;
    public String onto2;
    public String lang;
    public double alignmentScore;
 
    public void setURI1(String uri1) {
        this.uri1 = uri1;
    }
    public void setURI2(String uri2) {
        this.uri2 = uri2;
    }
    public void setOnto1(String onto1) {
        this.onto1 = onto1;
    }
    public void setOnto2(String onto2) {
        this.onto2 = onto2;
    }
    public void setLang(String lang) {
        this.lang = lang;}
    
    
    public void setSim(double sim) {
        this.alignmentScore = sim;
   
}}









	