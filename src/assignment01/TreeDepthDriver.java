package assignment01;

public class TreeDepthDriver {

  public static void main(String[] args) {
    int depth = 2;
    DecisionTree dt = null;
    double[] errors = new double[6];
    int maxDepth = 0; 
    for (int i = 0; i < 6; i++){
      try {
        dt = DecisionTree.learnID3("res\\SettingA\\CVSplits\\training_0"+i+".data",depth);
        double[] td1 = DecisionTree.runTestFile(dt, "res\\SettingA\\CVSplits\\training_0"+((i+1)%6)+".data");
        double[] td2 = DecisionTree.runTestFile(dt, "res\\SettingA\\CVSplits\\training_0"+((i+2)%6)+".data");
        double[] td3 = DecisionTree.runTestFile(dt, "res\\SettingA\\CVSplits\\training_0"+((i+3)%6)+".data");
        double[] td4 = DecisionTree.runTestFile(dt, "res\\SettingA\\CVSplits\\training_0"+((i+4)%6)+".data");
        double[] td5 = DecisionTree.runTestFile(dt, "res\\SettingA\\CVSplits\\training_0"+((i+5)%6)+".data");
        double error = (td1[1]+td2[1]+td3[1]+td4[1]+td5[1])/(td1[1]+td2[1]+td3[1]+td4[1]+td5[1]+td1[2]+td2[2]+td3[2]+td4[2]+td5[2]);
        errors[i]=error;
        if (dt.getDepth() > maxDepth){
          maxDepth = dt.getDepth();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    System.out.print("Max Tree Depth: " + maxDepth);
    System.out.println("\tAllowed Tree Depth: " + depth);
    int i = 0;
    double mean = 0;
    for (double error : errors){
      System.out.println("Error0" + i + "\t" + error);
      mean = mean + error;
      i++;
    }
    mean = mean / 6.0;
    System.out.print("Mean: " + mean);
    double sd = 0;
    for (double error : errors){
       sd = sd + Math.pow((error - mean),2.0);
    }
    sd = Math.sqrt(sd/6.0);
    System.out.println("\tSD: " + sd);
  }

}
