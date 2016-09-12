package assignment01;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.opencsv.CSVReader;

/**
 * A Class to create a tree based on the ID3 algorithm
 * 
 * @author Leland Stenquist
 *
 */
public class ID3 {
  
  private static int treeDepth;
  private static String commonLbl;
  private static int customTreeDepth;
  
  public static DecisionTree createTree(String filename) throws Exception {
    return createTree(filename, -1);
  }
  
  /**
   * call this function by giving it a csv file. 
   * 
   * @param filename
   * @return DecisionTree
   * @throws Exception
   */
  public static DecisionTree createTree(String filename, int cstmTreeDepth) throws Exception {
    
    // read the csv using this hand dandy library
    CSVReader reader = new CSVReader(new FileReader(filename));
    List<String[]> trainingData = reader.readAll();
    reader.close();
    
    // make sure the file has information in it
    if(trainingData.isEmpty())
      throw new Exception("There is no training data for this algorithm to parse");
    // get the depth of the tree
    treeDepth = trainingData.get(0).length;
    customTreeDepth = treeDepth;
    if (cstmTreeDepth > -1)
      customTreeDepth = cstmTreeDepth;
    
      
    
    // throw an exception if the tree is not deep enough to be created
    if (treeDepth < 2) 
      throw new Exception("You do not have enough columns to have attributes in your tree.");

    
    // Figure out the amount of elements so that they can be created
    int attrCount = treeDepth - 1;
    List<Attribute> attributes = new ArrayList<Attribute>();
    // create the label
    Label label = new Label(treeDepth - 1);
    label.setEntropy(entropy(trainingData, treeDepth - 1));
    commonLbl = findCommonLabel(trainingData);
    
    // Get the entropy and add All the attributes to a list
    for(int i = 0; i < attrCount; i++){
      Attribute newAttribute = new Attribute(i, label);
      newAttribute.setEntropy( entropy(trainingData, i));
      newAttribute.addValues(trainingData, i);
      attributes.add(newAttribute);
    }
    
    // sort the list
    attributes.sort(Attribute.Comparators.InfoGain);

    // Call the ID3 Algorithm
    Integer counter = 0;
    DecisionTree decisionTree = new DecisionTree(id3(trainingData, attributes.remove(0), attributes, counter));  
    return decisionTree;
  }

  /**
   * @param attributes 
   * @param attribute 
   * @param trainingData 
   * @throws Throwable 
   * 
   */
  private static Node id3(List<String[]> trainingData, Attribute attribute, List<Attribute> attributes, Integer counter){
    // If there is no attribute just make a leaf node
    
    Node root = new Node("Leaf");
    if (attribute == null || counter == customTreeDepth){
      root.setLeaf();
      if(trainingData.isEmpty())
        root.setLabel(commonLbl);
      else
        root.setLabel(findCommonLabel(trainingData));
      return root;
    }
    
    // if there is no training data set the leaf to the most common label
    if(trainingData.isEmpty()  ) {
      root.setLeaf();
      root.setLabel(commonLbl);
      return root;
    }
    
    // If all values are true or false then we classify here.
    if(constantLabel(trainingData) != null){
      root.setLeaf();
      root.setLabel(trainingData.get(0)[treeDepth - 1]);
      return root;
    }
    
    // We know that the root exist. Get it
    root = new Node(attribute.getTitle());
    root.setAttributeNumber(attribute.getAttrNum());
    
    // loop through all the values in the attribute
    for(String val : attribute.getValues()) {     
      List<String[]> newTrainingData = cleanList(trainingData, val, attribute.getAttrNum());
      
      // Get the new entropies
      for (Attribute  attrib : attributes){
        if(newTrainingData.isEmpty())
          attrib.setEntropy(0.0);
        else{
          double ent = entropy(newTrainingData, attrib.getAttrNum());
          attrib.setEntropy(ent);
        }
      }
     
      // get the highest information gain
      attributes.sort(Attribute.Comparators.InfoGain);
      
      // get the new attribute
      Attribute nextAttribute;
      if(attributes.isEmpty())
        nextAttribute = null;
      else
        nextAttribute = attributes.get(0);
      
      // make the recursive call and add information to the node
      Node child = id3(newTrainingData, nextAttribute , new ArrayList<Attribute>(attributes),new Integer(counter+1));
      root.addChild(child);
      child.setParent(root);
      child.setParentClass(val);
    }
    attributes.remove(0);
    
    return root;
  }

  /**
   * Get the entropy
   * 
   * @param trainingData
   * @param index
   * @return
   */
  private static double entropy(List<String[]> trainingData, int index){
    
    Map<String,Double> attrIDs = new HashMap<String,Double>();
    Map<String,HashMap<String, Double>> lblIDs = new HashMap<String,HashMap<String, Double>>();
    
    for(String[] row : trainingData){
      String lblVal = row[row.length-1];
      String attrVal = row[index];
      if (attrIDs.containsKey(attrVal)){
        attrIDs.put(attrVal, attrIDs.get(attrVal) + 1); 
        if(lblIDs.get(row[index]).containsKey(lblVal))
          lblIDs.get(row[index]).put(lblVal, lblIDs.get(row[index]).get(lblVal) + 1);
        else {
          lblIDs.get(row[index]).put(lblVal,1.0);
        }
      }
      else{
        attrIDs.put(attrVal, 1.0);
        lblIDs.put(row[index], new HashMap<String, Double>());
        lblIDs.get(row[index]).put(lblVal,1.0);
      }
    }
    // TODO: For testing purposes
//    System.out.println("Map:\t" + index);
//    for (Map.Entry<String,Double> entry : attrIDs.entrySet()) {
//      System.out.println("\tKEY:\t" + entry.getKey() + "\tVAL:\t" + entry.getValue());
//      for (Map.Entry<String,Double> ent : lblIDs.get(entry.getKey()).entrySet())
//        System.out.println("\t\tLABEL:\t" + ent.getKey() + "\tVALUE:\t" + ent.getValue());
//    }
    
    double entropy = 0; 
    double total = trainingData.size();

    if(index == trainingData.get(0).length - 1)
      for (Map.Entry<String,Double> lbl : attrIDs.entrySet()) {
        entropy = entropy + (-(lbl.getValue() / total) * log2(lbl.getValue() / total));
      }
    else{     
      for (Map.Entry<String,Double> attr : attrIDs.entrySet()) {
        double attrEntropy = 0;
        for (Map.Entry<String,Double> lbl : lblIDs.get(attr.getKey()).entrySet()){
          attrEntropy = attrEntropy + (-(lbl.getValue() / attr.getValue()) * log2(lbl.getValue() / attr.getValue()));
        }
        entropy = entropy + ((attr.getValue() / total) * attrEntropy);
      }
    }
    // TODO: For testing purposes
//    System.out.println("\tTotal:\t" + total);
//    System.out.println("\tEntropy:\t" + entropy);
    return entropy;
  }
  
  /**
   * simple helper function to get log at root 2
   * 
   * @param x
   * @return
   */
  private static double log2(double x){
    return Math.log(x)/ Math.log(2);
  }
  
  /**
   * Clear out the list based on the value of the attribute being looked at
   * 
   * @param trainingData
   * @param val
   * @param index
   * @return
   */
  private static List<String[]> cleanList(List<String[]> trainingData, String val, int index) {
    List<String[]> newTrainingData = new ArrayList<String[]>();
    for (String[] row : trainingData){
      if(row[index].equals(val)){
        newTrainingData.add(row);
      }
    }
    return newTrainingData;
  }
  
  /**
   * Find the most frequent label
   * 
   * @param trainingData
   * @return
   */
  private static String findCommonLabel(List<String[]> trainingData) {
    Map<String,Integer> counts = new HashMap<String,Integer>();
    for(String[] row : trainingData){
      int index = row.length - 1;
      if(counts.containsKey(row[index]))
        counts.put(row[index], counts.get(row[index]) + 1);
      else
        counts.put(row[index], 1);
    }
    String commonLabel = "";
    int max = 0;
    for(Entry<String, Integer> val : counts.entrySet()){
      if (val.getValue() > max) {
       max = val.getValue();
       commonLabel = val.getKey();
      }
    }
    return commonLabel;
  }
  
  /**
   * check if the label is constant in the training data
   * 
   * @param trainingData
   * @return
   */
  private static String constantLabel(List<String[]> trainingData){
    String constant = trainingData.get(0)[treeDepth-1];

    for(String[] row : trainingData){
      if(!(row[treeDepth-1].equals(constant))){
        return null;
      }
    }
      
    return constant;
  }

}
