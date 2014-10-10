package edu.cmu.deiis.analysisengine;

import java.util.Iterator;
import java.util.Vector;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.deiis.types.Annotation;
import abner.Tagger;

/**
 * An annotator based on ABNER.
 * 
 * @author kylemao
 * @version 0.1
 * 
 */
public class AbnerAnnotator extends JCasAnnotator_ImplBase {

  private Tagger mTagger;

  /**
   * Initialize the tagger using BioCreative model.
   * 
   * @see org.apache.uima.analysis_component.AnalysisComponent_ImplBase#initialize(UimaContext)
   */
  @Override
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);

    // Load the BioCreative model
    mTagger = new Tagger(Tagger.BIOCREATIVE);
  }

  /**
   * Reads a sentence and annotate it with ABNER.
   * 
   * @see org.apache.uima.analysis_component.JCasAnnotator_ImplBase#process(org.apache.uima.jcas.JCas)
   */
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    String sentenceText = aJCas.getDocumentText();
    Vector<String[][]> subSentences = mTagger.getSegments(sentenceText);
    ;

    int thisBegin = 0;
    for (Iterator<String[][]> sentIterator = subSentences.iterator(); sentIterator.hasNext();) {
      String[][] segments = (String[][]) sentIterator.next();

      // segments[0] is a vector of the text segments, whereas segments[1] is a vector of the tags
      // corresponding to segments[0]
      for (int i = 0; i < segments[0].length; i++) {
        int segLength = (segments[0][i].replaceAll("\\s", "").length());
        // Tag "O" means segments not recognized as named entities
        if (!segments[1][i].equals("O")) {
          Annotation geneEntity = new Annotation(aJCas);
          geneEntity.setBegin(thisBegin);
          geneEntity.setEnd(thisBegin + segLength - 1);
          geneEntity.setConfidence(1.0);
          geneEntity.setAnnotatedText(segments[0][i]);
          geneEntity.setCasProcessorId(AbnerAnnotator.class.getSimpleName());
          geneEntity.addToIndexes();
        }
        thisBegin += segLength;
      }
    }
  }

}
