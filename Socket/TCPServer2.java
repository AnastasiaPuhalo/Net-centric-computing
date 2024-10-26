import java.io.*;
import java.net.*;

class TCPServer2 {
	public static void main(String args[]) throws Exception {
		String clientSentence;
		String responseSentence;
		ServerSocket welcomeSocket = new ServerSocket(6789);
		System.out.println ("Waiting for connection.....");

        

		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println ("Connection successful");
		    System.out.println ("Waiting for input.....");

			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());

            clientSentence = inFromClient.readLine();

            while (!clientSentence.equals("DONE")) {
                

                System.out.println("From client at " + connectionSocket.getInetAddress() 
                                + ": " + clientSentence);

                responseSentence = "Result: " + calculate(clientSentence) + "\n";
                outToClient.writeBytes(responseSentence);   
                clientSentence = inFromClient.readLine();  
                	    
            }
            outToClient.writeBytes("CLOSE\n");
            connectionSocket.close(); 
		}
	}

    //method to take a string and perfoem an operation
    public static double calculate(String expression) {
        // Remove any extra spaces
        expression = expression.trim();

        // Find the operator (+, -, *, or /)
        char operator = 0;
        int operatorIndex = -1;
        
        if (expression.contains("+")) {
            operator = '+';
            operatorIndex = expression.indexOf("+");
        } else if (expression.contains("-")) {
            operator = '-';
            operatorIndex = expression.indexOf("-");
        } else if (expression.contains("*")) {
            operator = '*';
            operatorIndex = expression.indexOf("*");
        } else if (expression.contains("/")) {
            operator = '/';
            operatorIndex = expression.indexOf("/");
        } else {
            throw new IllegalArgumentException("Invalid expression: No valid operator found");
        }

        // Split the expression into left and right parts based on the operator
        String leftOperandStr = expression.substring(0, operatorIndex).trim();
        String rightOperandStr = expression.substring(operatorIndex + 1).trim();

        // Convert the operands to double
        double leftOperand = Double.parseDouble(leftOperandStr);
        double rightOperand = Double.parseDouble(rightOperandStr);

        // Perform the calculation based on the operator
        switch (operator) {
            case '+':
                return leftOperand + rightOperand;
            case '-':
                return leftOperand - rightOperand;
            case '*':
                return leftOperand * rightOperand;
            case '/':
                if (rightOperand == 0) {
                    throw new ArithmeticException("Division by zero is not allowed.");
                }
                return leftOperand / rightOperand;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }
    
}
