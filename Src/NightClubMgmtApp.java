import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/** 
* Nightclub application class, inherits from JFrame class.
*/ 
public class NightClubMgmtApp extends JFrame
{                       
	private static ArrayList<ClubAbstractEntity> clubbers;
	private JButton searchButton;
	private JButton createButton;
	
	/** 
	* NightClubMgmtApp no-argument constructor -
	* initialize a new JFrame, creates two
	* buttons and adds them to the frame.
	* Creates a new ButtonHandler for the buttons and
	* listens to their events.
	* Uses {@link #loadClubbersDBFromFile loadClubbersDBFromFile()} method.
	* also, creates a new window listener that calls {@link #writeClubbersDBtoFile writeClubbersDBtoFile()} method
	* when the window is closed.
	*/ 
	public NightClubMgmtApp()                  
	{
		setSize(450, 220);
		setTitle("Welcome to B.K NightClub's Database");
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setLayout(new GridLayout(2,1,1,5));
		searchButton = new JButton("Search for a Customer");
		createButton = new JButton("Add a new Customer");
		add(searchButton);
		add(createButton);
		clubbers = new ArrayList<>();
		loadClubbersDBFromFile();
		ButtonsHandler handler = new ButtonsHandler();
        searchButton.addActionListener(handler);
        createButton.addActionListener(handler);
        addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent event)
			{
				writeClubbersDBtoFile();
				System.exit(0);
			}
		});
	}
	
	// inner class for button event handling
	/** 
    * Inner class for button event handling.
    * implements {@link java.awt.event.ActionListener} and
    * {@link java.io.Serializable} interfaces.
    */ 
	private class ButtonsHandler implements ActionListener, Serializable
    {
      // handle button event
      /** 
	  * Overrides actionPerformed method - 
	  * uses the {@link #manipulateDB manipulateDB()}
	  * and the {@link #createNewClubber createNewClubber()} methods.
	  * @param event the action performed event.
	  */
      @Override
      public void actionPerformed(ActionEvent event)
      {
         if (event.getSource() == searchButton)
         {
         	 manipulateDB();
         }
         
         if (event.getSource() == createButton)
         {
         	 String[] options = {"Person", "Student", "Soldier"}; 
         	 String result = (String)JOptionPane.showInputDialog(
               getContentPane(),
               "Select the type of Customer", 
               "Add a new Customer",            
               JOptionPane.PLAIN_MESSAGE,
               null,            
               options, 
               options[0] 
               );
             if (result == null) return;
             createNewClubber(result);
         }
       }
     }
	
    /** 
	* This method is used to search through the clubbers ArrayList
	* using the match method based on their type.
	* if the clubber is found it will display his information.
	*/  
	private void manipulateDB()
	{
		boolean found = false;
		boolean flag = true;
		while(flag)
		{
		String input = JOptionPane.showInputDialog(this, "Enter Customer ID", "Customer Search", JOptionPane.PLAIN_MESSAGE);
		if(input == null) return;
		for(ClubAbstractEntity clubber : clubbers)
		if(clubber.match(input))
		{
		found = true;
		clubber.setVisible(true);
		flag = false;
		}
		if(!found)
			{
				JOptionPane.showMessageDialog(this,"Customer with key "+input+ " does not exist", "Error", JOptionPane.INFORMATION_MESSAGE);
				flag = true;
			}
		}
	}// End of method manipulateDB
	
	/** 
	* This method is used to load the clubbers information from the data file
	* into the clubbers ArrayList.
	*/  
	@SuppressWarnings("unchecked")
	private void loadClubbersDBFromFile()
	{
		try 
		{
			FileInputStream streamIn = new FileInputStream("BKCustomers.dat");
			ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
			clubbers = (ArrayList<ClubAbstractEntity>)objectinputstream.readObject();
            objectinputstream.close();
            streamIn.close();
		}
		catch(FileNotFoundException fileExc)
		{
			JOptionPane.showMessageDialog(this,"File not found!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(SecurityException secExc)
		{
			JOptionPane.showMessageDialog(this,"Security Issue!", "Error", JOptionPane.ERROR_MESSAGE);
		}	
		catch(IOException ioExc)
		{
			JOptionPane.showMessageDialog(this,"Cannot load file!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(ClassNotFoundException classExc)
		{
			JOptionPane.showMessageDialog(this,"Cannot find Clubbers!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/** 
	* This method is used to save the clubbers ArrayList into the data file.
	* Also it deletes any object with an empty ID or other identifier.
	*/
	private void writeClubbersDBtoFile()
	{
		//Write all the objects’ data in clubbers ArrayList into the file
		try
		{
			FileOutputStream streamOut = new FileOutputStream("BKCustomers.dat");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(streamOut);
			clubbers.removeIf(clubber -> clubber.match("")); //removes empty clubbers
			objectOutputStream.writeObject(clubbers);
			objectOutputStream.close();
            streamOut.close();
		}
        catch(FileNotFoundException fileExc)
		{
			JOptionPane.showMessageDialog(this,"File not found!", "Error", JOptionPane.ERROR_MESSAGE);
		}	
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this,"Cannot save file!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/** 
	* This method is used to create a new clubber based on the chosen type
	* and adds it to the clubbers ArrayList.
	* @param type the chosen clubber type.
	*/
	private void createNewClubber(String type)
	{
		Person temp = new Person();
		switch(type)
		{
	 	 case "Student":
	 	 	 		temp = new Student();
			break;
		 case "Soldier":
		 	 		temp = new Soldier();
			break;
	 	 default:
	 	}
	 	temp.setVisible(true);
		temp.cancelButton.setEnabled(false);
		clubbers.add(temp);
	}
	
	/** 
	* This method is used to check if a clubber(besides him) already exists in the ArrayList
	* based on his ID or other identifier.
	* @param key the input identifier string.
	* @param obj the clubber who used the method.
	* @return if the clubber already exists or not.
	*/
	public static boolean CheckIfExists(String key, ClubAbstractEntity obj)
	{
		for(ClubAbstractEntity clubber : clubbers)
			{
				if(clubber.match(key) && clubber != obj) return true;
			}
		return false;
	}
	
	/** 
	* Main - creates a new application.
	* @param args Command-Line Arguments.
	*/
	public static void main(String[] args)
	{
		NightClubMgmtApp appliction = new NightClubMgmtApp();
	}
	                   
}//End of class NightClubMgmtApp