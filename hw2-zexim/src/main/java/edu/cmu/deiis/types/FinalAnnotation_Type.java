
/* First created by JCasGen Fri Oct 10 12:45:51 EDT 2014 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Fri Oct 10 12:45:51 EDT 2014
 * @generated */
public class FinalAnnotation_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (FinalAnnotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = FinalAnnotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new FinalAnnotation(addr, FinalAnnotation_Type.this);
  			   FinalAnnotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new FinalAnnotation(addr, FinalAnnotation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = FinalAnnotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.deiis.types.FinalAnnotation");
 
  /** @generated */
  final Feature casFeat_annotatedText;
  /** @generated */
  final int     casFeatCode_annotatedText;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getAnnotatedText(int addr) {
        if (featOkTst && casFeat_annotatedText == null)
      jcas.throwFeatMissing("annotatedText", "edu.cmu.deiis.types.FinalAnnotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_annotatedText);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAnnotatedText(int addr, String v) {
        if (featOkTst && casFeat_annotatedText == null)
      jcas.throwFeatMissing("annotatedText", "edu.cmu.deiis.types.FinalAnnotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_annotatedText, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public FinalAnnotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_annotatedText = jcas.getRequiredFeatureDE(casType, "annotatedText", "uima.cas.String", featOkTst);
    casFeatCode_annotatedText  = (null == casFeat_annotatedText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_annotatedText).getCode();

  }
}



    