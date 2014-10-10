

/* First created by JCasGen Fri Oct 10 12:45:51 EDT 2014 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Fri Oct 10 12:45:51 EDT 2014
 * XML source: /home/kylemao/git/hw2-zexim/hw2-zexim/src/main/resources/descriptors/deiis_types.xml
 * @generated */
public class FinalAnnotation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(FinalAnnotation.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected FinalAnnotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public FinalAnnotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public FinalAnnotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public FinalAnnotation(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: annotatedText

  /** getter for annotatedText - gets 
   * @generated
   * @return value of the feature 
   */
  public String getAnnotatedText() {
    if (FinalAnnotation_Type.featOkTst && ((FinalAnnotation_Type)jcasType).casFeat_annotatedText == null)
      jcasType.jcas.throwFeatMissing("annotatedText", "edu.cmu.deiis.types.FinalAnnotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((FinalAnnotation_Type)jcasType).casFeatCode_annotatedText);}
    
  /** setter for annotatedText - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAnnotatedText(String v) {
    if (FinalAnnotation_Type.featOkTst && ((FinalAnnotation_Type)jcasType).casFeat_annotatedText == null)
      jcasType.jcas.throwFeatMissing("annotatedText", "edu.cmu.deiis.types.FinalAnnotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((FinalAnnotation_Type)jcasType).casFeatCode_annotatedText, v);}    
  }

    