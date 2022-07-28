import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/** 
* Abstract class of a generic-type Customer, inherits from JFrame class.
*/ 
public abstract class ClubAbstractEntity extends JFrame
{
	public JButton okButton;
	public JButton cancelButton;
	public JPanel centerPanel;
	
	/** 
	* ClubAbstractEntity no-argument constructor -
	* initialize a new JFrame and creates two
	* panels and two buttons and adds them to the frame.
	* Creates a new ButtonHandler for the buttons and
	* listens to their events.
	*/ 
	public ClubAbstractEntity()
	{
		setLayout(new BorderLayout());
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(okButton);
		bottomPanel.add(cancelButton);
		add(bottomPanel, BorderLayout.SOUTH);
		add(centerPanel, BorderLayout.CENTER);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		// create new ButtonHandler for button event handling
        ButtonsHandler handler = new ButtonsHandler();
        okButton.addActionListener(handler);
        cancelButton.addActionListener(handler);
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
	  * uses the {@link #validateData validateData()}, the {@link #commit commit()}
	  * and the {@link #rollBack rollBack()} methods.
	  * @param event the action performed event.
	  */
      @Override
      public void actionPerformed(ActionEvent event)
      {
         if (event.getSource() == okButton)
         {
         	if (validateData())
         	 	 {
         	 	  commit();
         	 	  cancelButton.setEnabled(true);
         	 	  setVisible(false);
         	 	 }
         }
         else 
         {
         	 rollBack();
         	 setVisible(false);
         }
      }
   }
   
   /** 
   * a Method used to add a Gui Component to the center panel.
   * @param guiComponent the Gui Component.
   */
   protected void addToCenter(Component guiComponent)
   {
   	   centerPanel.add(guiComponent);
   }
   
   // Abstract method - finding customer id
   /** 
   * an Abstract method used to find a customer.
   * @param key the given id.
   * @return if the Customer was found or not.
   */
   public abstract boolean match(String key);
   
   // Abstract method - valdiates the object info
   /** 
   * an Abstract method to valdiates the customer information.
   * @return if the validation was successful or not.
   */
   protected abstract boolean validateData();
   
   // Abstract method - saves the text fields info in the object
   /** 
   * an Abstract method to save the text fields information into the customer object.
   */
   protected abstract void commit();
   
   // Abstract method - copys the object info into the text fields
   /** 
   * an Abstract method to load the customer information into the text fields.
   */
   protected abstract void rollBack();
   
}