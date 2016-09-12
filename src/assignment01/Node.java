package assignment01;

import java.util.ArrayList;

/**
 * 
 * @author Leland Stenquist
 *
 */
public class Node {

  private String label;
  private String name;
  private String parentClass;
  private int attributeNumber;
  private Node parent;
  private ArrayList<Node> children;
  private boolean leaf = false;
  private boolean root = false;
  
  /**
   * 
   * @param name
   */
  public Node(String name){
    this.name = name;
    this.children = new ArrayList<Node>();
  }

  /**
   * @return the label
   */
  public String getLabel() {
    return this.label;
  }
  
  /**
   * @return the name
   */
  public String getName() {
    return this.name;
  }

  /**
   * @return the parentClass
   */
  public String getParentClass() {
    return this.parentClass;
  }
  
  /**
   * @return the parentClass
   */
  public void setParentClass(String parentClass) {
    this.parentClass = parentClass;
  }

  /**
   * @return the parent
   */
  public Node getParent() {
    return this.parent;
  }
  
  /**
   * @param parent
   */
  public void setParent(Node parent) {
    this.parent = parent;
  }

/**
 * 
 * @param child
 */
  public void addChild(Node child){
    children.add(child);
  }
  
  /**
   * 
   * @return
   */
  public boolean isLeaf(){
    return this.leaf;
  }
  
  /**
   * 
   */
  public void setLeaf(){
    this.leaf = true;
  }
  
  /**
   * 
   * @param label
   * @throws Exception
   */
  public void setLabel(String label) {
    if(leaf)
      this.label = label;
  }

  /**
   * 
   */
  public void setRoot() {
    // TODO Auto-generated method stub
    root = true;
  }
  
  /**
   * 
   * @return
   */
  public boolean isRoot() {
    // TODO Auto-generated method stub
    return this.root;
  }
  
  /**
   * 
   * @return
   */
  public boolean hasChildren(){
    return (this.children.size() > 0);
  }
  
  /**
   * 
   * @return
   */
  public ArrayList<Node> getChildren(){
    return this.children;
  }

  /**
   * @return the attributeNumber
   */
  public int getAttributeNumber() {
    return attributeNumber;
  }

  /**
   * @param attributeNumber the attributeNumber to set
   */
  public void setAttributeNumber(int attributeNumber) {
    this.attributeNumber = attributeNumber;
  }
  /**
   *   private String label;
   *   private String name;
   *   private String parentClass;
   *   private int attributeNumber;
   *   private Node parent;
   *   private ArrayList<Node> children;
   *   private boolean leaf = false;
   *   private boolean root = false;
   * @param node
   */
  public static void printNode(Node node){
    System.out.println("\nName:\t" + node.getName());
    if (node.isLeaf())
      System.out.println("\tLabel:\t" + node.getLabel());
    else
      System.out.println("\tAttr #:\t" + node.getAttributeNumber());
    if (!node.isRoot())
      System.out.println("\tParent Class:\t" + node.getParentClass());
    System.out.println("\tRoot:\t" + node.isRoot());
    System.out.println("\tLeaf:\t" + node.isLeaf());
    if (!(node.isRoot()))
      System.out.println("\tParent:\t" + node.getParent().getName());
    if (node.getChildren().size() > 0){
      System.out.println("\tChildren:\t");
      for (Node child : node.getChildren())
        System.out.println("\t\t" + child.getName());
    }
    System.out.println();
  }
}
