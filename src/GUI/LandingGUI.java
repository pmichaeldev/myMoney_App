package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.DatabaseConnectionSource;
import controllers.*;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

import models.DepositMoneyModel;
import views.DepositMoneyView;

/**
 * The default landing GUI where the buttons (which would open other windows) can be found as well as the balance.
 * 
 * @author Philip Michael
 * @created 3/5/2018
 * @lastUpdated 3/12/2018 
 */

public class LandingGUI {
	
	// Static GUI referencing to 'this' for use elsewhere
	public static LandingGUI _GUI;
	
	/**
	 * The controllers for our main functionalities
	 */
	private DisplayBalanceController displayBalance;
	private DepositMoneyController depositMoney;
	//private WithdrawMoneyController withdrawMoney;
	
	/**
	 * GUI ELEMENTS
	 */
	// -- These @FXML private fields are associated to the fx:id of the individual components in the GUI.
	@FXML 
	private Text balance;
	@FXML
	private Button withdraw;
	@FXML
	private Button deposit;
	@FXML
	private Button showHistory;
	@FXML
	private Button recurringPayment;
	@FXML
	private Button clearAccount;
	@FXML
	private TextField inputAmount;
	
	/**
	 * Initialize is called after every element/handler was fetched.
	 * The order is: Constructor > Tie the members/handlers > initialize
	 */
	@FXML
	public void initialize() {
		// Get a connection to our database before we start any ui
		JdbcConnectionSource source = DatabaseConnectionSource.getConnection();
		
		// Creates the static GUI object referencing to the current GUI shown on screen.
		// This is used in the views.
		if(_GUI == null) {
			_GUI = this;
		}
		
		if (source != null) {
			displayBalance = new DisplayBalanceController();

			// WithdrawMoneyController withdrawController = new WithdrawMoneyController();

			depositMoney = new DepositMoneyController();
			
			// Once we are done, close the connection to the database 
			DatabaseConnectionSource.closeConncetion();
		} else {
			System.out.println("Could not make a connection to the database");
		}
	}
	
	/**
	 * HELPER FUNCTIONS
	 */
	/**
	 * Updates the displayed balance on the main GUI screen.
	 * @param balance to update on the balance
	 */
	public void UpdateBalance(float balance) {
		this.balance.setText("$" + balance);
	}
	
	/**
	 * HANDLER FUNCTIONS
	 */
	// -- These handler functions are tied to the onAction="#[namehere]" of the buttons in the GUI.
	// In them, use the controllers respectively to initiate the functionality of each.
	
	
	/**
	 * For withdraw, we'd need to use the Withdraw controller to initiate the withdrawal.
	 * For consistency's sake, we will need to also update the amount in display_balance table.
	 * For that, we will need to also call the DisplayBalance's controller's DAO to update the amount.
	 * Thereafter, the view is updated again for us.
	 * @param event triggered by clicking on the button.
	 */
	@FXML
	protected void withdrawButtonAction(ActionEvent event) {
		System.out.println("Called Withdraw Button Event");
	}
	
	@FXML
	protected void depositButtonAction(ActionEvent event) {
		DepositMoneyView.DepositMoneyViewData input = new DepositMoneyView.DepositMoneyViewData();
		
		input.amount = Float.parseFloat(this.inputAmount.getText());
		
		input.type = "Pay";
		
		depositMoney.update(input);
		
		displayBalance.updateView();
		//ToDo update balance
		
		inputAmount.setText("");
	}
	
	@FXML
	protected void showHistoryButtonAction(ActionEvent event) {
		System.out.println("Called Edit Transaction Button Event");
	}
	
	@FXML
	protected void clearAccountButtonAction(ActionEvent event) {
		System.out.println("Called Delete Account Button Event");
	}
	
	@FXML
	protected void recurringPaymentButtonAction(ActionEvent event) {
		System.out.println("Called Recurring Payment Button Event");
	}
}
