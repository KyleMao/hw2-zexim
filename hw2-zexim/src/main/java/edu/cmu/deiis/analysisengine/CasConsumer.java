package edu.cmu.deiis.analysisengine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;

import edu.cmu.deiis.types.*;

/**
 * A CAS consumer that reads gene annotations from CASs and output to a file.
 * 
 * @author kylemao
 * @version 0.1
 * 
 */
public class CasConsumer extends CasConsumer_ImplBase {

  /**
   * Name of configuration parameter that must be set to the path of the output file.
   */
  public static final String PARAM_OUTPUT_FILE = "OutputFile";

  private BufferedWriter mBufferedWriter;

  /**
   * Prepare a BufferedWriter for the output file.
   * 
   * @see org.apache.uima.collection.CasConsumer_ImplBase#initialize()
   */
  public void initialize() throws ResourceInitializationException {
    try {
      File outputFile = new File(((String) getConfigParameterValue(PARAM_OUTPUT_FILE)).trim());
      if (!outputFile.exists()) {
        outputFile.createNewFile();
      }
      mBufferedWriter = new BufferedWriter(new FileWriter(outputFile, false));
    } catch (IOException e) {
      System.out.println("Creating output file failed!");
    }
  }

  /**
   * Reads a CAS of gene entity and writes the annotation result to the output file.
   * 
   * @see org.apache.uima.collection.base_cpm.CasObjectProcessor#processCas(org.apache.uima.cas.CAS)
   */
  @Override
  public void processCas(CAS aCAS) throws ResourceProcessException {
    JCas jcas = null;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new ResourceProcessException(e);
    }

    String sentenceId = "";
    FSIterator<TOP> sentenceIterator = jcas.getJFSIndexRepository().getAllIndexedFS(Sentence.type);
    if (sentenceIterator.hasNext()) {
      Sentence sentence = (Sentence) sentenceIterator.next();
      sentenceId = sentence.getSentenceId();
    }

    for (org.apache.uima.jcas.tcas.Annotation geneEntity : jcas
            .getAnnotationIndex(FinalAnnotation.type)) {
      FinalAnnotation thisGeneEntity = (FinalAnnotation) geneEntity;
      String outputString = sentenceId + "|" + ((Integer) thisGeneEntity.getBegin()).toString()
              + " " + ((Integer) thisGeneEntity.getEnd()).toString() + "|"
              + thisGeneEntity.getAnnotatedText() + "\n";
      try {
        mBufferedWriter.write(outputString);
        mBufferedWriter.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Closes the BufferedWriter.
   * 
   * @see org.apache.uima.collection.CasConsumer_ImplBase#destroy()
   */
  public void destroy() {
    try {
      mBufferedWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}