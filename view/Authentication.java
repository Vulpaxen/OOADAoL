package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Authentication extends Stage{
	private VBox root = new VBox(10);
	private Scene scene = new Scene(root, 500, 300);
    private TextField usernameField = new TextField();
    private TextField emailField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private PasswordField confirmPasswordField = new PasswordField();
    private Label statusLabel = new Label();
	
	public Authentication() {
		super(StageStyle.DECORATED);
        this.setScene(scene);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.TOP_CENTER);
        
        this.setTitle("Mystic Grills");
        showLoginPage();
	}

	private void showLoginPage() {
		Label titleLabel = new Label("Login");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setAlignment(Pos.CENTER);
        
        HBox marginTitle = new HBox(50);
        
        emailField.setPromptText("Email");
        passwordField.setPromptText("Password");
        
        Button loginBtn = new Button("Login");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        
        statusLabel.setTextFill(Color.BLACK);
		
        loginBtn.setOnAction(e -> {
            try {
                String userEmail = emailField.getText();
                String userPassword = passwordField.getText();
                String userRole = UserController.authenticateUser(userEmail, userPassword);

                if (userRole != null) {
                    if (userRole.equals("Customer")) {
                    	CustomerPanel customerPanel = new CustomerPanel();
                    	customerPanel.show();
                    } else if(userRole.equals("Chef")) {
                    	ChefPanel chefPanel = new ChefPanel();
                        chefPanel.show();
                    } else if(userRole.equals("Waiter")) {
                    	WaiterPanel waiterPanel = new WaiterPanel();
                    	//waiterPanel.show();
                    } else if(userRole.equals("Cashier")) {
                    	CashierPanel cashierPanel = new CashierPanel();
                    	//cashierPanel.show();
                    } else if(userRole.equals("Admin")) {
                    	AdminPanel adminPanel = new AdminPanel();
                    	//adminPanel.show();
                    }
                    
                    ((Stage) loginBtn.getScene().getWindow()).close();
                } else {
                    statusLabel.setText("Login failed. Please check your credentials.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
		Label registerLink = new Label("I don't have an account");
        registerLink.setUnderline(true);
        registerLink.setTextFill(Color.BLUE);
        registerLink.setCursor(Cursor.HAND);
        registerLink.setOnMouseClicked(e -> showRegisterPage());
        registerLink.setAlignment(Pos.CENTER);
        
        
        VBox container = new VBox(10);
        container.setPadding(new Insets(20));
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(titleLabel, marginTitle, emailField, passwordField, loginBtn, registerLink, statusLabel);
        
        root.getChildren().clear();
        root.getChildren().add(container);
	}

	private void showRegisterPage() {
		Label titleLabel = new Label("Register");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setAlignment(Pos.CENTER);
        
        
        usernameField.setPromptText("Username");
        emailField.setPromptText("Email");
        passwordField.setPromptText("Password");
        confirmPasswordField.setPromptText("Password");
        
        Button regBtn = new Button("Register");
        regBtn.setMaxWidth(Double.MAX_VALUE);
        
        statusLabel.setTextFill(Color.BLACK);
		
        regBtn.setOnAction(e -> {
            try {
                String userRole = "Customer";
                String userName = usernameField.getText();
                String userEmail = emailField.getText();
                String userPassword = passwordField.getText();
                String confirmPassword = confirmPasswordField.getText();

                String registrationResult = UserController.createUser(userRole, userName, userEmail, userPassword);
                showLoginPage();
                statusLabel.setText(registrationResult);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        Label loginLink = new Label("I already have an account");
        loginLink.setUnderline(true);
        loginLink.setTextFill(Color.BLUE);
        loginLink.setCursor(Cursor.HAND);
        loginLink.setOnMouseClicked(e -> showLoginPage());
        loginLink.setAlignment(Pos.CENTER);
        
		VBox container = new VBox(10);
        container.setPadding(new Insets(20));
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(titleLabel, usernameField, emailField,passwordField, confirmPasswordField, regBtn, loginLink, statusLabel);
        
        root.getChildren().clear();
        root.getChildren().add(container);
	}
}
