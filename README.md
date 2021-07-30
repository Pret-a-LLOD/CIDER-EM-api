
# CIDER-EM

Context and Inference baseD ontology alignER. 

**CIDER-EM** is a word-embedding-based system for monolingual and cross-lingual ontology alignment deveoped by the SID group (University of Zaragoza). It evolves the [CIDER-CL](https://github.com/jogracia/cider-cl) tool, deveoped at OEG (Universidad Politécnica de Madrid) and SID (University of Zaragoza) by including the use of word embeddings.
   	

>    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

>    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

>    You should have received a copy of the GNU General Public License
    along with this program.  If not, see [http://www.gnu.org/licenses/].

----------
### Features
- It operates as **aligner** between two ontologies
- It aligns **classes** and **properties**, but not instances.
- The REST Api can currently operate in monolingual mode: 
	 * **Monolingual.** 
	     * It gets the word embeding from the ontology's language.
	     * It computes the word-embedding-based value of the relatedness between two entities from two ontologies in the same language.
		 * 	It performs elementary computations of *"cosine similarity"* to compare several features of the ontological description of the compared entities. 
		 * 	Such features are combined by means of multilayer perceptrons (one for classes and another one for properties) to produce a final value.

- This aligner is not intended to operate with large ontologies.

Currently, the **REST Api** has the following endpoints:

1. `POST /align/entities`.  Given the URIs of two ontologies and two ontology elements, and a language code, return the cosine similarity between the two ontology entities. The body should have the following format (example): 

```
{
  "uri1": "http://oaei.ontologymatching.org/2011/benchmarks/101/onto.rdf#author",
  "uri2": "http://oaei.ontologymatching.org/2011/benchmarks/203/onto.rdf#author",
  "onto1": "http://oaei.ontologymatching.org/2011/benchmarks/101/onto.rdf",
  "onto2": "http://oaei.ontologymatching.org/2011/benchmarks/203/onto.rdf",
  "lang": "en"
}
```
A successful result will look as follows: 

```
{
    "uri1": "http://oaei.ontologymatching.org/2011/benchmarks/101/onto.rdf#author",
    "uri2": "http://oaei.ontologymatching.org/2011/benchmarks/203/onto.rdf#author",
    "onto1": "http://oaei.ontologymatching.org/2011/benchmarks/101/onto.rdf",
    "onto2": "http://oaei.ontologymatching.org/2011/benchmarks/203/onto.rdf",
    "lang": "en",
    "similarity": 0.9739285255652004
}
```  

2. `GET /align/ontologies?uri1={uri1}&uri2={uri2}`, e.g. `/align/ontologies?uri1=http://oaei.ontologymatching.org/2011/benchmarks/101/onto.rdf&uri2=http://oaei.ontologymatching.org/2011/benchmarks/203/onto.rdf` Given the URIs of two ontologies, return an ontology alignment in RDF following [the Alignment Format](https://moex.gitlabpages.inria.fr/alignapi/format.html). 

## Installation 

Requirements: To run this API locally, please ensure that you have Java and the build automation tool Maven installed. 

To install the project locally, follow these steps: 

1. Download some word embeddings [here](https://drive.google.com/drive/folders/188jUDHGBYrLYKLbVTVY0mmsT8OgvojW5?usp=sharing). They need to be placed in the following path: `src/main/resources/word-embeddings` directory. 
2. In the root directory, run the command `mvn install` . 
3. Once the project has been successfully built, run in the same directory the command `java -jar target/cider-em-0.0.1-SNAPSHOT.jar` to start the server.
4. Access the API at http://localhost:8080 . 

As an alternative, there is a Docker image at https://hub.docker.com/r/pretallod/link-cider-em . 

----------
> **Note:** This version is *under development*. You can find the last working version of the aligner [here](https://github.com/jogracia/cider-cl).

