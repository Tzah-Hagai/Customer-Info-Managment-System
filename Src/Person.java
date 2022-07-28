import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** 
* Class that represents a Person, inherits from {@link ClubAbstractEntity} abstract class.
*/ 
public class Person extends ClubAbstractEntity
{
	private String id, name, surname, tel;
	private JTextField Tid, Tname, Tsurname, Ttel;
	public ArrayList<JLabel> asterisk;
	
	/** 
    * Person no-argument constructor - initialized the values with nulls via the arguments constructor {@link #Person Person}
    */
	public Person()
	{
		this("", "", "", "");
	}
	
	/** 
    * Person constructor, initializes private vars with given values.
    * also initializes all the Gui elements and adds them
    * to a panel via the {@link #createPanel createPanel()} method.
    * @param id the id of the person.
    * @param name the name of the person.
    * @param surname the surname of the person.
    * @param tel the telephone of the person.
    */
	public Person(String id, String name, String surname, String tel)
	{
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.tel = tel;
		Tid = new JTextField(this.id, 30);
		Tname = new JTextField(this.name, 30);
		Tsurname = new JTextField(this.surname, 30);
		Ttel = new JTextField(this.tel, 30);
		asterisk = new ArrayList<>();
		String[] lblArr = {"ID", "Name", "Surname", "Tel"}; 
		JTextField[] jtxtArr = {Tid, Tname, Tsurname, Ttel};
		JPanel[] panelsArr = new JPanel[jtxtArr.length];
		setSize(450, 220);
		setTitle(this.getClass().getSimpleName()+" Clubber's Data");
        for (int i = 0;i < jtxtArr.length;i++)
          {
          	  createPanel(jtxtArr[i], lblArr[i]);
          }
	}
	
	/** 
    * This method implements the abstract method {@link ClubAbstractEntity#match match()},
    * inherited from {@link ClubAbstractEntity} abstract class.
    * This method is used to find a person.
    * @param key the given id.
    * @return if the person was found or not.
    */ 
	@Override
	public boolean match(String key)
	{
		if(key.equals(id)) return true;
		return false;
	}
	
	/** 
    * This method implements the abstract method {@link ClubAbstractEntity#validateData validateData()},
    * inherited from {@link ClubAbstractEntity} abstract class.
    * This method is used to validate the person text fields using a specific regex.
    * also it uses the {@link NightClubMgmtApp#CheckIfExists CheckIfExists()} static method to check
    * if the person already exists in the clubbers ArrayList.
    * This method uses the {@link #resetAsterisks resetAsterisks()} method to reset the Asterisks.
    * @return if the validation was successful or not.
    */ 
	@Override
	protected boolean validateData()
	{
		String[] inputsArr = {Tid.getText(), Tname.getText(), Tsurname.getText(), Ttel.getText()};
		String[] regexArr = {"^[0-9]-[0-9]{7}\\|[1-9]$", "^[A-Z][a-z]+[A-Z, a-z]*", "([A-Z][a-z]*['-’]?)+"
		, "^\\+\\([1-9][0-9]{0,2}\\)[1-9][0-9]{0,2}-[1-9][0-9]{6}$"};
		if(NightClubMgmtApp.CheckIfExists(Tid.getText(), this))
			{	
				JOptionPane.showMessageDialog(this,""+this.getClass().getSimpleName()+" with ID: "+Tid.getText()+" Already exists!", "Error", JOptionPane.ERROR_MESSAGE);
				resetAsterisks();
				return false;
			}	
		for (int i = 0;i < inputsArr.length;i++)
			{
				if(!inputsArr[i].matches(regexArr[i]))
					{
						resetAsterisks();
						asterisk.get(i).setVisible(true);
						return false;
					}
			}	
		return true;
	}
	
	/** 
    * This method implements the abstract method {@link ClubAbstractEntity#commit commit()},
    * inherited from {@link ClubAbstractEntity} abstract class.
    * This method is used to save the text fields information into the person variables.
    * also, it uses the {@link #resetAsterisks resetAsterisks()} method to reset the Asterisks.
    */ 
	@Override
	protected void commit()
	{
		id = Tid.getText();
		name = Tname.getText();
		surname = Tsurname.getText();
		tel = Ttel.getText();
		resetAsterisks();
	}
	
	/** 
    * This method implements the abstract method {@link ClubAbstractEntity#rollBack rollBack()},
    * inherited from {@link ClubAbstractEntity} abstract class.
    * This method is used to load the person information into the text fields.
    * also, it uses the {@link #resetAsterisks resetAsterisks()} method to reset the Asterisks.
    */ 
	@Override
	protected void rollBack()
	{
		Tid.setText(id);
		Tname.setText(name);
		Tsurname.setText(surname);
		Ttel.setText(tel);
		resetAsterisks();
	}
	
	/** 
    * This method is used to create a new panel with a given
    * JTextField and a String. also, it creates an Asterisk JLabel.
    * finally it adds the panel to the center panel using {@link ClubAbstractEntity#addToCenter addToCenter()}
    * @param jText the given JTextField.
    * @param lbl the given String used to create a JLabel.
    */
	public void createPanel(JTextField jText, String lbl)
	{
		JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel(lbl));
        panel.add(jText);
        JLabel ast = new JLabel("*");
        ast.setForeground(Color.red);
        ast.setVisible(false);
        panel.add(ast);
        asterisk.add(ast);
        addToCenter(panel);
	}
	
	/** 
    * This method is used to clear all of the Asterisks JLabels.
    */
	protected void resetAsterisks()
	{
		for(JLabel aster : asterisk) aster.setVisible(false);
	}
	
}