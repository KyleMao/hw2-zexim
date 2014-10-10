package edu.cmu.deiis.analysisengine;

import java.util.HashSet;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.deiis.types.*;

/**
 * An annotator that combines the POS and ABNER annotators' results.
 * 
 * @author kylemao
 * 
 */
public class CombineAnnotator extends JCasAnnotator_ImplBase {

  private Set<String> mPosSet;

  /**
   * Initialize the annotator.
   * 
   * @see org.apache.uima.analysis_component.AnalysisComponent_ImplBase#initialize(UimaContext)
   */
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);
    mPosSet = new HashSet<String>();
  }

  /**
   * Combine the annotations from two annotators sentence by sentence.
   * 
   * @see org.apache.uima.analysis_component.JCasAnnotator_ImplBase#process(org.apache.uima.jcas.JCas)
   */
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // Put the named entities recognized by PosAnnotator in a sentence into mPosSet.
    mPosSet.clear();
    for (org.apache.uima.jcas.tcas.Annotation geneEntity : aJCas
            .getAnnotationIndex(Annotation.type)) {
      Annotation thisGeneEntity = (Annotation) geneEntity;
      if (thisGeneEntity.getCasProcessorId().equals(PosAnnotator.class.getSimpleName())) {
        mPosSet.add(thisGeneEntity.getAnnotatedText());
      }
    }

    // For the recognized genes in AbnerAnnotator, if the text is contained in one of the
    // PosAnnotator annotations or contains one of them, add it to the FinalAnnotation.
    if (!mPosSet.isEmpty()) {
      for (org.apache.uima.jcas.tcas.Annotation geneEntity : aJCas
              .getAnnotationIndex(Annotation.type)) {
        Annotation candidateGene = (Annotation) geneEntity;
        if (candidateGene.getCasProcessorId().equals(AbnerAnnotator.class.getSimpleName())) {
          if (mPosSet.contains(candidateGene.getAnnotatedText())) {
            FinalAnnotation foundGene = new FinalAnnotation(aJCas);
            foundGene.setAnnotatedText(candidateGene.getAnnotatedText());
            foundGene.setBegin(candidateGene.getBegin());
            foundGene.setEnd(candidateGene.getEnd());
            foundGene.addToIndexes();
          } else {
            String candidateText = candidateGene.getAnnotatedText();
            for (String posText : mPosSet) {
              if ((candidateText.contains(posText)) || (posText.contains(candidateText))) {
                FinalAnnotation foundGene = new FinalAnnotation(aJCas);
                foundGene.setAnnotatedText(candidateGene.getAnnotatedText());
                foundGene.setBegin(candidateGene.getBegin());
                foundGene.setEnd(candidateGene.getEnd());
                foundGene.addToIndexes();
                break;
              }
            }
          }
        }
      }
    }

  }

}
