import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class BSTree {
	private static Node root;
	
	public BSTree(){
		root=null;
	}
	
	public static int FindComma(String line){
		return line.indexOf(","); //find  the position of ,
	}
	
	/*
	 * SearchAndAdd -use arraywithoutgrade;
	 * Worst case O(n)
	 * Average O(lgn)
	 */
	public static void SeachAndAdd(String Name, String Course){
		Node Temp;
		String Rtemp;
		int comma;
		
		Temp=root;

		//Find Name O(lgn) if the tree is not sorted, or only left or right child.
		while (root!=null){
			Rtemp=Temp.data;
			comma=FindComma(Rtemp);
			Rtemp=Rtemp.substring(comma+1, Rtemp.length());
			comma=comma+1+FindComma(Rtemp);
			Rtemp=Temp.data.substring(0,comma);	
			
			int compare=Name.compareTo(Rtemp);


			if(compare<0&&Temp.left!=null){
				Temp=Temp.left;
			}
			else if(compare>0&&Temp.right!=null){
				Temp=Temp.right;
			}
			
			else if(compare==0){
				Course=	JOptionPane.showInputDialog("Enter a Course and Grade: (Course ID, Credits, Letter Grade)");
				
				comma=FindComma(Course);
				try{
				if(Course.substring(0,comma).length()==4&&Integer.parseInt(Course.substring(0,comma))<10000){
					
					Temp.data=Temp.data+"\n"+Course;
				}
				}
				catch (Exception e){ 
				JOptionPane.showMessageDialog(null, "Course ID must be 4 digits, Please try again");
				}
				break;
			}
			else {
				JOptionPane.showMessageDialog(null, "Name not found In Binary Tree");
				break;
			}
		}//while
			
		
		
	}

	
	
	
	 /*
	 * SearchAndDelete
	 * 
	 * because when the name matches, we need to find the courses to be deleted s.t the following run time
	 * Worst case O(n)*O(n)
	 * Average O(lgn)*O(lgn)
	 */
	public static void SeachAndDele(String Name, String Course){
		Node Temp;
		String T, Rtemp, StudentINFO, CourseINFO;
		int comma;
		boolean Find=false;
		Temp=root;
		
		//Find Name
		while (root!=null){
			Rtemp=Temp.data;
			comma=FindComma(Rtemp);
			Rtemp=Rtemp.substring(comma+1, Rtemp.length());		
			comma=comma+1+FindComma(Rtemp);
			Rtemp=Temp.data.substring(0,comma);	
			
			int compare=Name.compareTo(Rtemp);
			if(compare<0&&Temp.left!=null){
				Temp=Temp.left;
			}
			else if(compare>0&&Temp.right!=null){
				Temp=Temp.right;				
			}
			
			else if(Rtemp.compareTo(Name)==0){
				T=Temp.data;
				Course=JOptionPane.showInputDialog("Enter a Course ID (####)");
	
				//Find Course
				StudentINFO=T;
				comma=FindComma(StudentINFO);			
				StudentINFO=T.substring(comma+1,T.length());
				comma=comma+1+FindComma(StudentINFO);
				StudentINFO=T.substring(0, comma+7); //Student Name +Id, since ID must be 6 digits we know where the next line is 
				
				CourseINFO=T;		
				comma=FindComma(CourseINFO);
				CourseINFO=T.substring(comma+1,T.length());
				comma=FindComma(CourseINFO);
				CourseINFO=CourseINFO.substring(comma+8,CourseINFO.length());//Temp becomes courses #s, no long contain student information
				
				Pattern checkRegex=Pattern.compile("\\d{4,4}");
				Matcher regexMatcher=checkRegex.matcher(CourseINFO);
				
				int i=0;			
				
				//UpdatedDatas have 3 conditions. 1.delete the first course. 2.delete the second, or in the middle. 3.delete the last course
				String UpdatedData="";
				String UpdatedData3="";
				String UpdatedData2="";
				String s="";


				try{
					while(regexMatcher.find()){
						s=regexMatcher.group();
						
						if(CourseINFO.substring(regexMatcher.start(), regexMatcher.end()).equals(Course)){
							Find=true;
							
							
							if(regexMatcher.start()==0){
							T=StudentINFO+"\n"+CourseINFO.substring(regexMatcher.end()+1, CourseINFO.length());
							
			
								while(!(CourseINFO.substring(regexMatcher.end()+i, regexMatcher.end()+i+4).matches("\\d{4,4}"))){
				
									i++;
									UpdatedData=StudentINFO+"\n"+CourseINFO.substring(regexMatcher.end()+i,CourseINFO.length());
									
								}
							Temp.data=UpdatedData;
							
							break;
							
							}
							
							else{ 
								while(!(CourseINFO.substring(regexMatcher.end()+i, regexMatcher.end()+i+4).matches("\\d{4,4}"))){
			
									i++;
					
									UpdatedData=CourseINFO.substring(regexMatcher.end()+i,CourseINFO.length());
									
								}
								UpdatedData2=StudentINFO+"\n"+CourseINFO.substring(0,regexMatcher.start())+UpdatedData;
								Temp.data=UpdatedData2;
							
							}
							
							break;
						}
						
					} //While reg.find()

					
				
				} //Try
				catch(Exception e){
					if(s.equals(Course)){ //deleting the last course
						try{
						UpdatedData3=StudentINFO+"\n"+CourseINFO.substring(0,regexMatcher.start()-1);
						Temp.data=UpdatedData3;
						
						}
						
						catch(Exception q){ //delete the only last element, it will only contain student info after deleting the last course
							UpdatedData3=StudentINFO;
							Temp.data=UpdatedData3;
						
						}
						
					}
					
				}
				
				
				if(Find==false) {
					JOptionPane.showMessageDialog(null, "Not Found In Binary Tree\n Please Try Again");
					
				}
				break;
			}//if compare ==0
			
			else {
				JOptionPane.showMessageDialog(null, "Name Dose Not Exist In Binary Tree");
				break;
			}
			
		}	//while	
			
		}

	
	/*
	 * Delete O(lgn)
	 */
	//Rec Delete
	public void Delete(String Name){
		root=Delete(root,Name);
	}	
	private static Node Delete(Node root, String Name){

		if(root==null) return root;
		
		Node Temp=root;		
		String Rtemp=Temp.data;
		String Student=FINDNAME(Temp,Rtemp);
		
		int compare=Name.compareTo(Student);
		
		if(compare<0&&root.left!=null){
			root.left=Delete(root.left,Name);
		}
		else if(compare>0&&root.right!=null){
			root.right=Delete(root.right,Name);
		}
		else if(compare ==0){
			
			if(root.left==null) return root.right;
			else if(root.right==null) return root.left;
			Node MinNode=minimumElement(root.right);
			root.data=MinNode.data;
			Rtemp=root.data;
			Student=FINDNAME(Temp,Rtemp);
			
			root.right=Delete(root.right,Student);
		}
		else JOptionPane.showMessageDialog(null, "Name Dose Not Exist In Binary Tree");
		return root;
		
	}		
	
	/*
	 * FindName O(1)
	 */
	public static String FINDNAME(Node root,String n){

		int comma;
		comma=FindComma(n);
		n=n.substring(comma+1, n.length());
		comma=comma+1+FindComma(n);
		n=root.data.substring(0,comma);
		
		return n;
	}	
	/*
	 * minElement for deletion O(lgn))
	 */
	private static Node minimumElement(Node root) {
		// TODO Auto-generated method stub
		if(root.left==null) return root;
		else{
			return minimumElement(root.left);
		}
	}


	
	
	/*
	 * Insert O(lgn)
	 * compare data, < go to left, > go to right
	 */
	public void Insert(String x){
		root=Insert(x,root);
	}	
	private static Node Insert(String x, Node root){
		if(root ==null){
			root=new Node(x);
			return root;
		}
		int compare=x.compareTo(root.data);
		if(compare<0)
			root.left=Insert(x,root.left);
		else if(compare>0)
			root.right=Insert(x,root.right);
		
			return root;
	}
	
	/*
	 *Tree to String O(n) all elements to string
	 */
	public String toString(){
		String returnString="";
		returnString=toString(root);
		return returnString;
	}
	
	private String returnString1="";
	
	public void resetString(){
		returnString1="";
	}
	
	public  String toString(Node root){
		if(root != null){ 
			toString(root.left); 

			returnString1=returnString1+root.data+"\n\n";
			toString(root.right);
		}
		return returnString1;
	}
	
	
	/*
	 * Print InOrder O(n)
	 */
	public void Print(){
		inOrder(root);
	}
	public static void inOrder(Node root){
		
		if(root != null){ 
			inOrder(root.left); 
			System.out.println("Data in Binary Tree:\n"+root.data+"\n");
			inOrder(root.right);
		}
	}
	
	
	/*
	 * set Tree = null
	 * O(n)
	 */
	public void DeleteEntireTree(){
		root=DeleteEntireTree(root);
	}
	public static Node DeleteEntireTree(Node root){
		return root=null;
	}


	/*
	 * Search and display
	 * O(lgn)
	 */
	public String SearchAndDisplay(String Name){
		return SearchAndDisplay(root,Name);
	}
	private static String SearchAndDisplay(Node root, String Name){
		if(root ==null){
			JOptionPane.showMessageDialog(null,"\nName does not exit!\n");
			return null;
		}
		
		Node Temp=root;		
		String Rtemp=Temp.data;
		String Student=FINDNAME(Temp,Rtemp);
	
		int compare=Name.compareTo(Student);

		if(compare<0){
			SearchAndDisplay(root.left,Name);
			
		}
		else if(compare>0){
			SearchAndDisplay(root.right,Name);

		}
		else{
			
			return root.data;
			
		}

		
		return null;
	}
	
	/*
	 * size of tree
	 * O(n)
	 */
	public int size(){
		return size(root);
	}	
	private static int size(Node root){
		if(root ==null)return 0;
		else return(size(root.left)+1+size(root.right));
	}
	
	
	
	/*The following code is to put tree data into a linked list then into an array
	because if i put tree data into an array, we do not know what would be the array size
	Since this is not a balanced tree, there is a chance where an array have a lot of 
	null values. In order to prevent this happens, I use linked list, or you can use array list,
	then put to linked list data into an array. 
	*/
	private static String [] array;
	private static LinkedList list = new LinkedList();
	
	public String[] returnArray(int size){
		array=new String[size];
		list=returnList();
		list.toArray(array);
		return array;
	}
	
	public LinkedList returnList(){
		toList();
		return list;
	}

	
	public void toList(){
		if(root!=null){
			toList(root);
		}
	}
	private static void toList(Node root){
		if(root!=null){
			toList(root.left);
			list.add(root.data);		
			toList(root.right);
		}
	}

			
		/*
	public LinkedList returnList(LinkedList list){
		toList(list);
		return list;
		
	}
	
	public void toList(LinkedList list){
		if(root!=null){
			toList(root,list);
		}
	}
	private static void toList(Node root,LinkedList list){
		if(root!=null){
			list.add(root.data);
						
			toList(root.left,list);
			toList(root.right,list);
		}
	}
	*/
}
