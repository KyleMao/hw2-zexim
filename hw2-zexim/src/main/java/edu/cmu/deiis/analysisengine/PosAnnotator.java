package edu.cmu.deiis.analysisengine;

import java.util.Map;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.deiis.types.Annotation;
import edu.cmu.deiis.types.Sentence;

/**
 * An annotator based on the POS tags of words and post processing.
 * 
 * @author kylemao
 * @version 0.1
 * 
 */
public class PosAnnotator extends JCasAnnotator_ImplBase {

  private PosTagNamedEntityRecognizer mRecognizer;

  /**
   * Initialize a POS tag named entity recognizer.
   * 
   * @see org.apache.uima.analysis_component.AnalysisComponent_ImplBase#initialize(UimaContext)
   */
  @Override
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    try {
      mRecognizer = new PosTagNamedEntityRecognizer();
    } catch (ResourceInitializationException e) {
      e.printStackTrace();
    }
  }

  /**
   * Reads a sentence from the CAS and annotates it with POS tag information.
   * 
   * @see org.apache.uima.analysis_component.JCasAnnotator_ImplBase#process(org.apache.uima.jcas.JCas)
   */
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    String sentenceText = aJCas.getDocumentText();
    Map<Integer, Integer> begin2end = mRecognizer.getGeneSpans(sentenceText);
    for (Map.Entry<Integer, Integer> entry : begin2end.entrySet()) {
      String entityText = sentenceText.substring(entry.getKey(), entry.getValue());

      // Get the index of non-whitespace characters.
      int begin = sentenceText.substring(0, entry.getKey()).replaceAll("\\s", "").length();
      int end = sentenceText.substring(0, entry.getValue()).replaceAll("\\s", "").length() - 1;

      if (end > begin) {
        Annotation geneEntity = new Annotation(aJCas);
        geneEntity.setBegin(begin);
        geneEntity.setEnd(end);
        geneEntity.setConfidence(1.0);
        geneEntity.setAnnotatedText(entityText);
        geneEntity.setCasProcessorId(PosAnnotator.class.getSimpleName());
        geneEntity.addToIndexes();
      }
    }
  }

}