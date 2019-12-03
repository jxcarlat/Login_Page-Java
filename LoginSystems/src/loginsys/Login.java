package loginsys;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
public class Login extends JFrame {

	private JFrame frame;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JTextField txtName;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setBounds(180, 10, 45, 26);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(82, 95, 79, 13);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblSecurityQuestion = new JLabel("Security Question");
		lblSecurityQuestion.setBounds(82, 150, 120, 13);
		frame.getContentPane().add(lblSecurityQuestion);
		
		JLabel lblSecurityAnswer = new JLabel("Security Answer");
		lblSecurityAnswer.setBounds(82, 180, 120, 13);
		frame.getContentPane().add(lblSecurityAnswer);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(82, 206, 67, 13);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(82, 120, 67, 13);
		frame.getContentPane().add(lblName);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"What was the name of your first pet?", 
		"What was the model of your first car?", "Where were you born?"}));
		comboBox.setBounds(190, 150, 230, 21);
		frame.getContentPane().add(comboBox);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(190, 92, 96, 19);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		txtName = new JTextField();
		txtName.setBounds(190, 120, 96, 19);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		textField = new JTextField();
		textField.setBounds(190, 180, 96, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(190, 203, 96, 19);
		frame.getContentPane().add(txtPassword);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String passText = new String(txtPassword.getPassword());
					Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root","Raptors_@re_Kool");
					Statement myStat = myConn.createStatement();
					ResultSet myRs = myStat.executeQuery("select * from user2");
					boolean userExists = false;
					boolean correctPassword = false;
					while(myRs.next()) {
						String username = myRs.getString("Username");
						String password = myRs.getString("Password");
						String username2 = txtUsername.getText();
					if(username.equals(username2)){
						userExists = true;
						if(password.equals(passText))
						{
							correctPassword = true;
							break;
						}
					}
					
					}
					if(userExists && correctPassword)
					{
						JOptionPane.showMessageDialog(null, "Login Successful, Welcome "+ txtUsername.getText());
						txtPassword.setText(null);
						txtUsername.setText(null);
						txtName.setText(null);
						textField.setText(null);
					}
					else if(userExists)
					{
						JOptionPane.showMessageDialog(null, "Password is Incorrect, Please try again", "Login Error",JOptionPane.ERROR_MESSAGE);
						txtPassword.setText(null);
						txtUsername.setText(null);
						txtName.setText(null);
						textField.setText(null);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "User doesn't exist, Please try again", "Login Error",JOptionPane.ERROR_MESSAGE);
						txtPassword.setText(null);
						txtUsername.setText(null);
						txtName.setText(null);
						textField.setText(null);
					}
				}
				catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(20, 232, 85, 21);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Register");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String passText = new String(txtPassword.getPassword());
					Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root","Raptors_@re_Kool");
					String query = " insert into user2 (Username, Password, Name, PasswordAnswer)" + " values (?, ?, ?, ?)";
					PreparedStatement preparedStmt = myConn.prepareStatement(query);
					preparedStmt.setString(1, txtUsername.getText());
					preparedStmt.setString(2, passText);
					preparedStmt.setString(3, txtName.getText());
					preparedStmt.setString(4, textField.getText());
					Statement myStat = myConn.createStatement();
					ResultSet myRs = myStat.executeQuery("select * from user2");
					boolean registeredUser = false;
					while(myRs.next()) {
						String username = myRs.getString("Username");
						String username2 = txtUsername.getText();
					if(username.equals(username2)){
						JOptionPane.showMessageDialog(null, txtUsername.getText() + " Already exists. Please try again.");
						txtPassword.setText(null);
						txtUsername.setText(null);
						txtName.setText(null);
						textField.setText(null);
						registeredUser = true;
						break;
						}
					}
					if(registeredUser)
					{
						myConn.close();
					}
					else {
					JOptionPane.showMessageDialog(null, "Registration successful");
					preparedStmt.execute();
					txtPassword.setText(null);
					txtUsername.setText(null);
					txtName.setText(null);
					textField.setText(null);
					myConn.close();
					}
				}
				catch (Exception exc) {
					exc.printStackTrace();
				}
				
			}
		});
		btnNewButton_1.setBounds(135, 232, 85, 21);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Forgot Password?");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO change functionality to perform Forgot password functionality
				try {
				String answer = new String(textField.getText());
				String passText = new String(txtPassword.getPassword());
				Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root","Raptors_@re_Kool");
				Statement myStat = myConn.createStatement();
				ResultSet myRs = myStat.executeQuery("select * from user2");
				String query = " UPDATE login.user2 set Password = '" + passText + 
						"' WHERE Username = '" +txtUsername.getText()+ "'";
				PreparedStatement preparedStmt = myConn.prepareStatement(query);
				boolean passwordChange = false;
				while(myRs.next()) {
				String passAnswer = myRs.getString("PasswordAnswer");
				if(answer.equals(passAnswer)) {
					JOptionPane.showMessageDialog(null, "Password change successful");
					preparedStmt.execute();
					txtPassword.setText(null);
					txtUsername.setText(null);
					txtName.setText(null);
					textField.setText(null);
					passwordChange = true;
					myConn.close();
				}
				}
				if(!passwordChange)
				{
					JOptionPane.showMessageDialog(null, "Security Question answer is not correct", "Password Change Error",JOptionPane.ERROR_MESSAGE);
					myConn.close();
				}
				}
				catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(250, 232, 166, 21);
		frame.getContentPane().add(btnNewButton_2);
	}
}
