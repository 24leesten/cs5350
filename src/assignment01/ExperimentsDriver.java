package assignment01;

/**
 * 
 * @author Leland Stenquist
 *
 */
public class ExperimentsDriver {

  /**
   * 
   * @param args
   */
  public static void main(String[] args) {
    DecisionTree dt = null;
    try {
      dt = DecisionTree.learnID3("res\\SettingB\\training.data",2);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if(dt != null)
//      dt.printTree();
//    for (Node child : dt.getLeaves()){
//      Node.printNode(child);
//    }
    System.out.println(dt.getDepth());
    double[] results = DecisionTree.runTestFile(dt, "res\\SettingB\\test.data");
    for (double d : results){
      if(d < 1)
        System.out.println(1-d);
      else
        System.out.println(d);
    }
  }
}
