package assignment01;

/**
 * 
 * @author Leland Stenquist
 *
 */
public class ID3Structure {
  private boolean isTarget;
  private int attrNum;
  private double entropy;
  private String title;
  
  /**
   * 
   * @param indicator
   */
  public ID3Structure(int indicator){
    this.attrNum = indicator;
    this.title = "C-" + indicator;
    setTarget(false);
  }
  
  /**
   * 
   * @param indicator
   * @param title
   */
  public ID3Structure(int indicator, String title){
    this.attrNum = indicator;
    this.title = title;
    setTarget(false);
  }
  
  /**
   * 
   * @return
   */
  public boolean isTarget() {
    return isTarget;
  }

  /**
   * 
   * @param isTarget
   */
  public void setTarget(boolean isTarget) {
    this.isTarget = isTarget;
  }

  /**
   * 
   * @return
   */
  public int getAttrNum() {
    return this.attrNum;
  }
  
  /**
   * 
   * @return
   */
  public String getTitle(){
    return this.title;
  }

  /**
   * @return the entropy
   */
  public double getEntropy() {
    return entropy;
  }

  /**
   * @param entropy the entropy to set
   */
  public void setEntropy(double entropy) {
    this.entropy = entropy;
  }
}
