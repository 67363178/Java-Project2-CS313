import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;

public class CommandMenuHandler  implements ActionListener{

	GUI mygui;
	Student myStudent=new Student();
	Course myCourse=new Course();
	
	public CommandMenuHandler(GUI gui){
		mygui=gui;
	}
	
	public void actionPerformed(ActionEvent event) {
		String menuName;
		menuName=event.getActionCommand();
		
		if(menuName.equals("Add")){
			resetGUI();
			AddStudent();
			
			mygui.myTreeWithoutGrade.resetString();
			mygui.UpdatedList.append(mygui.myTreeWithoutGrade.toString());
			
			System.out.println("Add Result From BST:");
			mygui.myTreeWithoutGrade.Print();
			
		}
		else if(menuName.equals("Delete")){
			resetGUI();
			DeleteStudent();	
			
			mygui.myTreeWithoutGrade.resetString();
			mygui.UpdatedList.append(mygui.myTreeWithoutGrade.toString());
			
			System.out.println("Delete Result From BST:");
			mygui.myTreeWithoutGrade.Print();
		}
		else if(menuName.equals("Search And Display")){
			resetGUI();
			SearchAndDisplay();
			
		}
		else if(menuName.equals("Search And Add")){
			resetGUI();
			SearchAndAdd();		
			
			mygui.myTreeWithoutGrade.resetString();
			mygui.UpdatedList.append(mygui.myTreeWithoutGrade.toString());
			
			System.out.println("SearchAdd Result From BST:");
			mygui.myTreeWithoutGrade.Print();
		}
		
		else if(menuName.equals("Search And Dele")){
			resetGUI();
			SearchAndDele();	
			
			mygui.myTreeWithoutGrade.resetString();
			mygui.UpdatedList.append(mygui.myTreeWithoutGrade.toString());
			
			System.out.println("Search and Delete Result From BST:");
			mygui.myTreeWithoutGrade.Print();
		}
			
		else if(menuName.equals("Save")){
			
			resetGUI();
			
			
			String[] myArrayForTree;
			
			//Tree with grade and -999 ready to be saved 
			myArrayForTree=TreeGrade(mygui.myTreeWithoutGrade,"Saved");
			System.out.println("Save Result From BST:");
			mygui.myTreeWithoutGrade.Print();
			
			mygui.UpdatedList.append(ArraytoString(myArrayForTree));
			try {
				OutputFileTree(mygui.myTreeWithoutGrade, myArrayForTree);
				
			} catch (FileNotFoundException e) {
			//	// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	

		}

	
	
	/*
	 * SearchAndDele()
	 * because when the name matches, we need to find the courses to be deleted s.t the following run time
	 * Worst case O(n)*O(n)
	 * Average O(lgn)*O(lgn)
	 * Search a student and delete a course
	 */
	private void SearchAndDele() {
		// TODO Auto-generated method stub
		String CourseToDele="";
		String StudentName="";
		StudentName=JOptionPane.showInputDialog("Enter a Student's Name(Search And Delete Course)");
		
		//Tree
		mygui.myTreeWithoutGrade.SeachAndDele(StudentName, CourseToDele);
		
	}

	
	
	/*
	 * SearchAndAdd
	 * Worst case O(n)
	 * Average O(lgn)
	 * Search a student and add a course
	 */
	private void SearchAndAdd() { //Need a Copy that haven't calculated the Gpa yet, should be in the FMH readSource
		// TODO Auto-generated method stub
		String StudentNameAdd="";
		String CourseAndGrade="";
		StudentNameAdd=JOptionPane.showInputDialog("Enter a Student's Name(Search And Add)");
		
		//Tree
		mygui.myTreeWithoutGrade.SeachAndAdd(StudentNameAdd, CourseAndGrade);

	}

	
	
	/*
	 * SearchAndDisplay
	 * O(lgn)
	 */
	private void SearchAndDisplay() {
		// TODO Auto-generated method stub

		String DisplayStudent="";
		DisplayStudent=JOptionPane.showInputDialog("Enter a Student's Name(It Will Be Displayed)");
		
		//Tree
		System.out.println("TREE Display");
		String display=mygui.myTreeWithoutGrade.SearchAndDisplay(DisplayStudent);
		mygui.UpdatedList.append(display);
		
	}

	
	
	/*
	 * DeleteStudent
	 * O(lgn)
	 */
	private void DeleteStudent() {
		String DeleStudentTree="";

		DeleStudentTree=JOptionPane.showInputDialog("Enter a Student Name(It Will Be Deleted)");
		
		//Tree
			mygui.myTreeWithoutGrade.Delete(DeleStudentTree);
		
		
	}

	
	
	/*
	 * AddStudent()
	 * O(lgn)
	 * Adding student name
	 */
	private void AddStudent() {
		String StudentName="";
		myStudent.resetRandomNumber(); //ID number for student		
		
		StudentName=JOptionPane.showInputDialog("Enter Student Name (First Name,LastName)");
		
		try{
		int comma=FindComma(StudentName);
		myStudent.setFirstName(StudentName.substring(0,comma));
		myStudent.setLastName(StudentName.substring(comma+1,StudentName.length()));
		
		//Tree
		mygui.myTreeWithoutGrade.Insert(myStudent.getFName()+ "," + myStudent.getLName()+","+myStudent.IDNumber);

		}
		
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "You Forgot ','");
		}
	}

	
	
	/*
	 * FindComma()
	 * 	O(1)
	 */
	public static int FindComma(String line){
		return line.indexOf(","); //find  the position of ,
	}
	
	
	/*
	 	I couldn't find a better way to calculate grade in a tree so this is the best
	 i can do. So turn my binary in to an array then use the array to calculate grade
	 then turn the array back into binary tree.
	*/
	public static String[] TreeGrade(BSTree tree,String Command){
		//Transfer data to Array
		
		int size=tree.size();
		String [] TempArray=new String[size];
		TempArray=tree.returnArray(size);
		CalculateTreeGrade(TempArray,Command);
		
		tree.DeleteEntireTree();
		for(int i=0; i<TempArray.length;i++){
			tree.Insert(TempArray[i]);
	}
	
		return TempArray;

	}
	
	 /* CalculateGrade()
	 * O(n^2)
	 * 
	 * I am trying to use regular expression to first find the course ID which is a 4 digits IB NUM, 
	 * then find the course credit and A and calculate them
	 * However I don't know if the following code below is the right way to use regular expression, it works find but just weird
	 */
	public static void CalculateTreeGrade(String array[],String command){
		int comma=0;
		String Credit="";
		String Grade="";
		String TempString="";
		String data="";
		
		Pattern checkRegex=Pattern.compile("\\d{4,4}");
		Matcher regexMatcher=checkRegex.matcher(data);
		
		for(int i=0; i<array.length;i++){
			data=array[i];
			TempString=data;
			if(!(data.substring(0, 4).matches("\\d{4}")&&data.substring(5).equals(","))){
				comma=FindComma(data);
				TempString=data.substring(comma+1,data.length());

				comma=FindComma(TempString);
				data=TempString.substring(comma+7,TempString.length());
				
			}
			

			double TotalCredits=0.0;
			double GPA=0.0;
			double TotalGrade=0.0;

			
			while(data.indexOf(",")!=-1){

				comma=FindComma(data);
				TempString=data.substring(comma+1,data.length());
				
				comma=FindComma(TempString);
				Credit=TempString.substring(0,comma);
				TempString=TempString.substring(comma+1,TempString.length());
				
				if(TempString.indexOf(",")==-1){
					Grade=TempString.substring(0,TempString.length());
					
				}
				else{
				comma=FindComma(TempString);
				Grade=TempString.substring(0,comma-5);
				}

				data=TempString;

				TotalCredits=TotalCredits+Double.parseDouble(Credit);
				TotalGrade = TotalGrade+(FindGrade(Grade)*Double.parseDouble(Credit));
			}
			
			GPA=TotalGrade/TotalCredits;
		
			if(command=="GradeOnly")
				array[i]=array[i]+"\n"+TotalCredits+","+GPA;
			if(command=="Saved")
				array[i]=array[i]+"\n-999\n"+TotalCredits+","+GPA;
	}
	}
	

	/*
	 * FindGrade()
	 * O(1)
	 */
	public static double FindGrade(String grade){
		if(grade.equals("A")){
			return 4;
		}
		
		if(grade.equals("A-")){
			return 3.7;
		} 
		
		if(grade.equals("B+")){
			return 3.3;
		}
		
		if(grade.equals("B")){
			return 3.0;
		}
		
		if(grade.equals("B-")){
			return 2.7;
		}
		
		if(grade.equals("C+")){
			return 2.3;
		}
		
		if(grade.equals("C")){
			return 2.0;
		}
		
		if(grade.equals("C-")){
			return 1.7;
		}
		
		if(grade.equals("D+")){
			return 1.3;
		}
		
		if(grade.equals("D")){
			return 1.0;
		}
		
		if(grade.equals("D-")){
			return 0.7;
		}
		
		if(grade.equals("F")){
			return 0.0;
		}
		
		return 0.0;
	}

	
	
	/*
	 * ArraytoString(){
	 * 	O(n)
	 * }
	 */
	public static String ArraytoString(String [] array){
		String myData="";

		for(int i=0;i<array.length; i++){
			myData=myData+array[i]+"\n\n";
		}
		return myData;
			
			
	}

	
	
	/*
	 * resetGUI(){
	 * 	O(n)
	 * }
	 */
	public void resetGUI(){
		for(int i=0;i<=1;i++){
			mygui.UpdatedList.setText("");
			mygui.UpdatedList.append("");
		}
	}
	
	
	/*
	 * Output File O(n)
	 * tree to array
	 * then output array[i]
	 */
	public void OutputFileTree(BSTree tree,String [] myArrayForTree) throws FileNotFoundException{
		String FN= mygui.getFileName();
		PrintWriter outputFile=new PrintWriter(mygui.getFileName());
		
		
		for(int i=0; i<myArrayForTree.length;i++)
			
			System.out.println("my tree array :"+myArrayForTree[i]);
		
		
		for(int i=0; i<myArrayForTree.length;i++){		
			outputFile.println(myArrayForTree[i]);

	}
		
		outputFile.close();
	}
	
}