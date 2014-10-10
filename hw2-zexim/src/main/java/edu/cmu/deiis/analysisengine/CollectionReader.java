package edu.cmu.deiis.analysisengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;

import edu.cmu.deiis.types.Sentence;

/**
 * A collection reader that reads the input file and stores each sentence in a CAS.
 * 
 * @author kylemao
 * @version 0.1
 * 
 */
public class CollectionReader extends CollectionReader_ImplBase {

  /**
   * Name of configuration parameter that must be set to the path of the input file.
   */
  public static final String PARAM_INPUT_FILE = "InputFile";

  private BufferedReader mBufferdReader;

  /**
   * Reads the input file into a BufferedReader.
   * 
   * @see org.apache.uima.collection.CollectionReader_ImplBase#initialize()
   */
  @Override
  public void initialize() throws ResourceInitializationException {
    try {
      File inputFile = new File(((String) getConfigParameterValue(PARAM_INPUT_FILE)).trim());
      mBufferdReader = new BufferedReader(new FileReader(inputFile));
    } catch (IOException e) {
      System.out.println("Loading input file failed!");
    }
  }

  /**
   * Gets the next sentence from the input file.
   * 
   * @see org.apache.uima.collection.CollectionReader#getNext(org.apache.uima.cas.CAS)
   */
  @Override
  public void getNext(CAS aCas) throws IOException, CollectionException {
    JCas jcas = null;
    try {
      jcas = aCas.getJCas();
    } catch (CASException e) {
      throw new CollectionException(e);
    }

    String lineString = mBufferdReader.readLine().trim();
    String sentenceId = lineString.substring(0, lineString.indexOf(" "));
    String sentenceText = lineString.substring(lineString.indexOf(" "));

    jcas.setDocumentText(sentenceText);
    Sentence sentence = new Sentence(jcas);
    sentence.setSentenceId(sentenceId);
    sentence.addToIndexes();
  }

  /**
   * Closes the BufferedReader.
   * 
   * @see org.apache.uima.collection.base_cpm.BaseCollectionReader#close()
   */
  @Override
  public void close() throws IOException {
    try {
      mBufferdReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Simply returns null.
   * 
   * @see org.apache.uima.collection.base_cpm.BaseCollectionReader#getProgress()
   */
  @Override
  public Progress[] getProgress() {
    return null;
  }

  /**
   * Checks if there are lines to be processed in the input file.
   * 
   * @see org.apache.uima.collection.base_cpm.BaseCollectionReader#hasNext()
   */
  @Override
  public boolean hasNext() throws IOException, CollectionException {
    return (mBufferdReader != null && mBufferdReader.ready());
  }

}