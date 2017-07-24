import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class FileMenuHandler implements ActionListener{
	
	GUI myGUI;

	
	public FileMenuHandler(GUI gui){
		myGUI=gui;
	
	}
	

	
	//ActionPerformer, see what menu name the user clicked on, then corresponded the following method.
	@Override
	public void actionPerformed(ActionEvent event) {
		String menuName;
		menuName=event.getActionCommand();
		
		if(menuName.equals("Open"))
			try {
				openFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Erro");
			}
		else if (menuName.equals("Quit")) System.exit(0);
		
	}

	
	
	private void openFile() throws IOException {
		JFileChooser chooser;
		int status;
		chooser=new JFileChooser();
		status =chooser.showOpenDialog(null);
		if(status==JFileChooser.APPROVE_OPTION)
			readSource(chooser.getSelectedFile());
	}

	
	
		
		/*
		 * O(n^2)
		 */
	private void readSource(File chosenFile) throws IOException{
		String chosenFileName=chosenFile.getName();
		TextFileInput inFile=new TextFileInput(chosenFileName);
		myGUI.setFileName(chosenFileName);
		
		String in=inFile.readLine(); //Read the input
		
		String data; //data in the file
		
		Student myStudent=new Student();
		Course myCourse=new Course();
		int NumOfStudent=0; //this determine the position in an array, myArray[NumOfStudent]
		
		String tempdata="";
		int comma; //to find the comma position
		int count=0, countForCourse=0; //count is to determine that Student Name is in line 1 here count =1
		double credits=0.0, TempG=0.0;
		int gobalcount=0; //for how many course taken in Arraylist belong to the student class
		
		
		while (in != null) { 
			data=in;;
			count++;
			tempdata=data.substring(0,data.length());
			
			//STUDENT'S INFORMATION
			if(count ==1){ //if count ==1 which mean the line contains student information
				credits = 0.0; //reset credits and TempG
				TempG=0.0;	// temp. grade after converting the letter grade to a double.
				
				comma=FindComma(tempdata);		
				myStudent.setLastName(tempdata.substring(0, comma));	//Last Name
				tempdata=tempdata.substring(comma+1,tempdata.length());		
				comma=FindComma(tempdata);
				myStudent.setFirstName(tempdata.substring(0, comma));	//First Name
				System.out.println("IDN :\n"+tempdata.substring(comma+1,comma+7));
				myStudent.setIDN(tempdata.substring(comma+1,comma+7));	//ID Number
			}			
			
			//END OF STUDENT, GO TO NEXT STUDENT
			if(tempdata.substring(0,4).equals("-999")){ // if the string = -999 which mean it is the end of the info that contain course
				String tempCourse="";
				myCourse.setNumOfCredit(credits);
				myStudent.setGPA((TempG/credits));

				for(int i=0; i<countForCourse; i++){ // See how may courses a student, since the courses store in the array list in student class, then we use the num(countForCourse)

						if(i==countForCourse-1) tempCourse=tempCourse+myStudent.CoursesNum.get(gobalcount);	
						else tempCourse=tempCourse+myStudent.CoursesNum.get(gobalcount)+"\n";	
					
					gobalcount=gobalcount+1;
				}
				
				TempG=TempG/credits;
				
				//resize array O(n)
				if(NumOfStudent==myGUI.myArrayWithoutGrade.length-1){
					String [] TempArrayWithoutGrade=new String [NumOfStudent*2];
					for(int i=0; i<myGUI.myArrayWithoutGrade.length; i++){
						TempArrayWithoutGrade[i]=myGUI.myArrayWithoutGrade[i];                                              
						
					}
					myGUI.myArrayWithoutGrade=TempArrayWithoutGrade;
					
								
				}
				if(countForCourse==0) myGUI.myArrayWithoutGrade[NumOfStudent]=myStudent.toString();			
				else myGUI.myArrayWithoutGrade[NumOfStudent]=myStudent.toString()+"\n"+tempCourse;
						
				count=-1;	//reset count
				NumOfStudent=NumOfStudent+1;//See how many students, it's a number for array position
				
				countForCourse=0; //when all the courses have read into the array, reset the countForCourses to 0.
			}

			
			//COURSE'S INFORMATION
			if(count>=2){ // if line ==2, it contains Course's information (line 1 contains student's information;Name,IDN)
				double tempCredit=0.0, tempGrade=0.0;
				
				countForCourse=countForCourse+1;// countForCourses determine how many courses a student takes
				
				myStudent.ListOfCourses(tempdata); //add the Course in to the array list in student class
				
				myCourse.setCourseNum(tempdata.substring(0,4));		
				
				comma=FindComma(tempdata);
				tempdata=tempdata.substring(comma+1,tempdata.length());
				comma=FindComma(tempdata);

			
				myCourse.setNumOfCredit(Double.parseDouble(tempdata.substring(0, comma)));
				credits=credits+Double.parseDouble(tempdata.substring(0, comma));
				
				myCourse.setGrade(tempdata.substring(comma+1,tempdata.length()));

				
				tempCredit=myCourse.getNumOfCredit();
				tempGrade=FindGrade(myCourse.getGrade());

				
				TempG=TempG+(tempCredit*tempGrade);
				
				count++;
			}
			
			
			in=inFile.readLine();
			
			

		}//while
		
		int NumOfElem=0;
		
		/*
		 * O(n)
		 */
		for(int j=0; j<myGUI.myArrayWithoutGrade.length; j++){
			if(myGUI.myArrayWithoutGrade[j]!=null){
				NumOfElem++;
			}
		}
		String [] NewArray=new String [NumOfElem];
		String [] NewArrayWithNoGrade=new String [NumOfElem];
		
		/*
		 * O(n)
		 */
		for(int i=0; i<NewArray.length; i++){
			
			NewArrayWithNoGrade[i]=myGUI.myArrayWithoutGrade[i];
		}
		
		myGUI.myArrayWithoutGrade=NewArrayWithNoGrade;		
	
		System.out.println("Your Text file data (Before Command) were printed in order using Binary Search Tree: \n");
		for(int i=0; i<myGUI.myArrayWithoutGrade.length;i++){
			myGUI.myTreeWithoutGrade.Insert(myGUI.myArrayWithoutGrade[i]);
	}
	
		myGUI.myTreeWithoutGrade.Print();
		System.out.println("WAITING FOR NEXT THE INSTURCTION\n");

		myGUI.SortedList.append(myGUI.myTreeWithoutGrade.toString());

	}

	
	
		/*
		 * Find Comma
		 * 	O(1)
		 */
	public static int FindComma(String line){
			return line.indexOf(","); //find  the position of ,
		}
	
		
		
		/*
		 * FindGrade(){
		 * 	Big O(1)
		 * }
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
		 * CompareTo
		 * O(1)
		 */
	public int compareTo(String x, String y){
			if(x==null&&y==null) return 0;
			if(x==null) return 1;
			if(y==null) return -1;
			return x.compareTo(y);
		}

		
		
		/*
		 * return String
		 * O(n)
		 */
	public static String ArraytoString(String [] array){
			String myData="";

			for(int i=0;i<array.length; i++){
				myData=myData+array[i]+"\n";
			}
			return myData;
			
			
		}

}
	
	
