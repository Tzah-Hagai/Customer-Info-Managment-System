import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** 
* Class that represents a Student, inherits from {@link Person}.
*/ 
public class Student extends Person
{
	private String studentID;
	private JTextField TstudentID;

	/** 
    * Student no-argument constructor - initialized the values with nulls via the arguments constructor {@link #Student Student}
    */
	public Student()
	{
		this("", "", "", "", "");
	}
	
	/** 
    * Student constructor, initializes private vars with given values - 
    * passed via the super {@link Person#Person Person} constructor.
    * also initializes the new Gui elements and adds them
    * to a panel via the {@link Person#createPanel createPanel()} method.
    * @param id the id of the student.
    * @param name the name of the student.
    * @param surname the surname of the student.
    * @param tel the telephone of the student.
    * @param studentID the student ID number.
    */
	public Student(String id, String name, String surname, String tel, String studentID)
	{
		super(id, name, surname, tel);
		this.studentID = studentID;
		TstudentID = new JTextField(this.studentID, 30);
		setSize(450, 250);
		setTitle(this.getClass().getSimpleName()+" Clubber's Data");
		createPanel(TstudentID, "Student ID");
	}
	
	/** 
	* This method implements the abstract method {@link ClubAbstractEntity#match match()},
    * inherited from {@link ClubAbstractEntity} abstract class.
	* Calls the super {@link Person#match match()} method
	* or checks if the given key is equal to the student id number.
	* This method is used to find a student.
	* @param key the given id/student id number.
    * @return if the student was found or not.
	*/
	@Override
	public boolean match(String key)
	{
		try
		{
			if(super.match(key) || key.equals(studentID.substring(4))) return true;
			return false;
		}
		catch(StringIndexOutOfBoundsException e){}
		return false;
	}
	
	/** 
    * This method implements the abstract method {@link ClubAbstractEntity#validateData validateData()},
    * inherited from {@link ClubAbstractEntity} abstract class.
    * Calls the super {@link Person#validateData validateData()} method -
    * validates the student text fields.
	* also, this method validates the new text field using a specific regex.
	* This method uses the {@link NightClubMgmtApp#CheckIfExists CheckIfExists()} static method to check
    * if the student already exists in the clubbers ArrayList.
    * also, this method uses the {@link Person#resetAsterisks resetAsterisks()} method to reset the Asterisks.
    * @return if the validation was successful or not.
    */
	@Override
	protected boolean validateData()
	{
		String str = TstudentID.getText();
		String regex = "^[A-Z]{3}\\/[1-9][0-9]{4}$";
		if(super.validateData())
		{
			try
			{
				if(NightClubMgmtApp.CheckIfExists(str.substring(4), this))
				{	
					JOptionPane.showMessageDialog(this,""+this.getClass().getSimpleName()+" with Student ID: "+str+" Already exists!", "Error", JOptionPane.ERROR_MESSAGE);
					resetAsterisks();
					return false;
				}
			}
			catch(StringIndexOutOfBoundsException e){}
			if(str.matches(regex)) return true;
			else 
				{
					resetAsterisks();
					asterisk.get(4).setVisible(true);
					return false;
				}
		}
		return false;
	}
	
	/** 
    * This method implements the abstract method {@link ClubAbstractEntity#commit commit()},
    * inherited from {@link ClubAbstractEntity} abstract class.
    * Calls the super {@link Person#commit commit()} method -
    * saves the text fields information into the student variables.
    * also, this method saves the new text field into the student id number.
    */
	@Override
	protected void commit()
	{
		super.commit();
		studentID = TstudentID.getText();
	}
	
	/** 
    * This method implements the abstract method {@link ClubAbstractEntity#rollBack rollBack()},
    * inherited from {@link ClubAbstractEntity} abstract class.
    * Calls the super {@link Person#rollBack rollBack()} method -
    * loads the student information into the text fields.
    * also, this method loads the studet id number into the new text field.
    */
	@Override
	protected void rollBack()
	{
		super.rollBack();
		TstudentID.setText(studentID);
	}

}