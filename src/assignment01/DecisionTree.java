package assignment01;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

/**
 * 
 * @author Leland Stenquist
 *
 */
public class DecisionTree {
  private Node root;

  /**
   * 
   * @param root
   */
  public DecisionTree(Node root) {
      this.root = root;
      this.root.setRoot();
  }
  
  /**
   * 
   * @return
   */
  public Node getRoot() {
    return root;
  }
  
  /**
   * Add a Node to the tree
   * 
   * @param parent
   * @param child
   */
  public void addNode(Node parent, Node child){
    parent.addChild(child);
    child.setParent(parent);
  }
  
  public ArrayList<Node> getLeaves() {
    ArrayList<Node >leaves = new ArrayList<Node >();
    ArrayList<Node >children = root.getChildren();
    while (!(children.isEmpty())){
      ArrayList<Node >sitter = new ArrayList<Node >();
      for (Node child : children){
        if(child.isLeaf())
          leaves.add(child);
        else {
          sitter.addAll(child.getChildren());
        }
      }
      children = sitter;
    }    
    return leaves;
  }
  
  public int getDepth(){
    int depth = 0;
    ArrayList<Node >children = root.getChildren();
    while (!(children.isEmpty())){
      depth++;
      ArrayList<Node >sitter = new ArrayList<Node >();
      for (Node child : children){
        sitter.addAll(child.getChildren());
      }
      children = sitter;
    }        
    //depth = depth(this.root,depth);
    
    return depth;
  }
  
  /**
   * TODO: Verify this function
   * 
   * @return
   */
  public int getNodeCount(){
    int count = 0;
    count = nodeCount(this.root,count);
    return count;
  }

  private int nodeCount(Node node, int count){
    if(node.isLeaf())
      return count + 1;
    
    ArrayList<Node> children = node.getChildren();

    for(Node child : children) {
      count = nodeCount(child, count+1);
    }

    return count;
  }
  
  public void printTree(){
    int count = 0;
    print(root, count);
  }
  
  private void print(Node node, int count){
    if(node.isLeaf()){
      for(int i = 0; i < count; i++){
        System.out.print("\t");
      }
      System.out.println(node.getName() + "-"+node.getParentClass()+"-" + "("+node.getLabel()+")");
      return;
    }
    for(int i = 0; i < count; i++){
      System.out.print("\t");
    }
    System.out.println(node.getName() + "-"+node.getParentClass()+"-");
    for(Node child : node.getChildren()){
      print(child, count+1);
    }
  }
  

  
  public static DecisionTree learnID3(String filename) throws Exception {
    return ID3.createTree(filename);
  }
  
  public static DecisionTree learnID3(String filename, int treeDepth) throws Exception {
    return ID3.createTree(filename, treeDepth);
  }
  
  public static double[] runTestFile(DecisionTree tree, String filename){
    // read the csv using this hand dandy library
    CSVReader reader = null;
    List<String[]> trainingData = null;
    try {
      reader = new CSVReader(new FileReader(filename));
      trainingData = reader.readAll();
      reader.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      if (trainingData==null)
        System.out.println("Your trainingdata was not read in properly.");
      e.printStackTrace();
    }
    
    int correct = 0;
    int wrong = 0;
    
    for(String[] row : trainingData){
      Node node = tree.getRoot();
      Node leaf = traverseTreeToLeaf(node, row);  
      if (leaf == null) {
        wrong++;
        continue;
        //System.out.println("There was no path for this row to take. Please contact the developer.");
        //return;
      }
      if (leaf.getLabel().equals(row[row.length-1]))
        correct++;
      else
        wrong++;
    }
    // TODO: For Debugging purposes
//    System.out.println("Wrong:\t" + wrong + "\tWright:\t" + correct);
    return new double[]{(double)correct/((double)correct+(double)wrong),correct,wrong};
  }

  private static Node traverseTreeToLeaf(Node node, String[] row) {
    if(node.isLeaf())
      return node;
    
    ArrayList<Node> children = node.getChildren();
    String value = row[node.getAttributeNumber()];
    
    for(Node child : children) {
      if(child.getParentClass().equals(value)){
        return traverseTreeToLeaf(child, row);
      }
    }
    return null;
  }
}
