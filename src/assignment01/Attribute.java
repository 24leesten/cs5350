package assignment01;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author Leland Stenquist
 *
 */
public class Attribute extends ID3Structure implements Comparable<Attribute>{

  private ArrayList<String> values;
  private Label label;

  /**
   * 
   * @param indicator
   * @param label
   */
  public Attribute(int indicator, Label label) {
    super(indicator);
    this.values = new ArrayList<String>();
    this.label = label;
    // TODO Auto-generated constructor stub
  }

  /**
   * 
   * @return
   */
  public double getInfoGain() {
    return label.getEntropy() - this.getEntropy();
  }
  
  /**
   * @return this.describers
   */
  public ArrayList<String> getValues(){
    return this.values;
  }

  /**
   * 
   */
  @Override
  public int compareTo(Attribute o) {
    // TODO Auto-generated method stub
    return Comparators.InfoGain.compare(this, o);
  }
  
  /**
   * 
   * @author Leland Stenquist
   *
   */
  public static class Comparators {

    /**
     * 
     */
    public static Comparator<Attribute> InfoGain = new Comparator<Attribute>() {
        @Override
        public int compare(Attribute o1, Attribute o2) {
            return Double.compare(o2.getInfoGain(),o1.getInfoGain());
        }
    };
  }

  /**
   * 
   * @param trainingData
   * @param index
   */
  public void addValues(List<String[]> trainingData, int index) {   
    for(String[] row : trainingData)
      if (!(this.values.contains(row[index])))
        this.values.add(row[index]);        
  }
  
  /**
   * 
   * @return
   */
  public Label getLabel(){
    return this.label;
  }
}
