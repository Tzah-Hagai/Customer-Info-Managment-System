import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** 
* Class that represents a Soldier, inherits from {@link Person}.
*/ 
public class Soldier extends Person
{
	private String personalNum;
	private JTextField TpersonalNum;
	
	/** 
    * Soldier no-argument constructor - initialized the values with nulls via the arguments constructor {@link #Soldier Soldier}
    */
	public Soldier()
	{
		this("", "", "", "", "");
	}
	
	/** 
    * Soldier constructor, initializes private vars with given values - 
    * passed via the super {@link Person#Person Person} constructor.
    * also initializes the new Gui elements and adds them
    * to a panel via the {@link Person#createPanel createPanel()} method.
    * @param id the id of the soldier.
    * @param name the name of the soldier.
    * @param surname the surname of the soldier.
    * @param tel the telephone of the soldier.
    * @param personalNum the personal number of the soldier.
    */
	public Soldier(String id, String name, String surname, String tel, String personalNum)
	{
		super(id, name, surname, tel);
		this.personalNum = personalNum;
		TpersonalNum = new JTextField(this.personalNum, 30);
		setSize(450, 250);
		setTitle(this.getClass().getSimpleName()+" Clubber's Data");
		createPanel(TpersonalNum, "Personal No.");
	}
	
	/** 
	* This method implements the abstract method {@link ClubAbstractEntity#match match()},
    * inherited from {@link ClubAbstractEntity} abstract class.
	* Calls the super {@link Person#match match()} method
	* or checks if the given key is equal to the soldier personal number.
	* This method is used to find a soldier.
	* @param key the given id/personal number.
    * @return if the soldier was found or not.
	*/
	@Override
	public boolean match(String key)
	{
		if(super.match(key) || key.equals(personalNum)) return true;
		return false;
	}
	
	/** 
    * This method implements the abstract method {@link ClubAbstractEntity#validateData validateData()},
    * inherited from {@link ClubAbstractEntity} abstract class.
    * Calls the super {@link Person#validateData validateData()} method -
    * validates the soldier text fields.
	* also, this method validates the new text field using a specific regex.
	* This method uses the {@link NightClubMgmtApp#CheckIfExists CheckIfExists()} static method to check
    * if the soldier already exists in the clubbers ArrayList.
    * also, this method uses the {@link Person#resetAsterisks resetAsterisks()} method to reset the Asterisks.
    * @return if the validation was successful or not.
    */
	@Override
	protected boolean validateData()
	{
		String str = TpersonalNum.getText();
		String regex = "^[R,O,C]\\/[1-9][0-9]{6}$";
		if(super.validateData())
		{
			if(NightClubMgmtApp.CheckIfExists(str, this))
			{	
				JOptionPane.showMessageDialog(this,""+this.getClass().getSimpleName()+" with Personal Num: "+str+" Already exists!", "Error", JOptionPane.ERROR_MESSAGE);
				resetAsterisks();
				return false;
			}
			
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
    * saves the text fields information into the soldier variables.
    * also, this method saves the new text field into the soldier personal number.
    */
	@Override
	protected void commit()
	{
		super.commit();
		personalNum = TpersonalNum.getText();
	}
	
	/** 
    * This method implements the abstract method {@link ClubAbstractEntity#rollBack rollBack()},
    * inherited from {@link ClubAbstractEntity} abstract class.
    * Calls the super {@link Person#rollBack rollBack()} method -
    * loads the soldier information into the text fields.
    * also, this method loads the soldier personal number into the new text field.
    */
	@Override
	protected void rollBack()
	{
		super.rollBack();
		TpersonalNum.setText(personalNum);
	}
	
}