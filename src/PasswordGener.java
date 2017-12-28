import java.util.*;
import java.io.*;
import java.text.*;

public class PasswordGener {

	private static String currTime; // current time
	private static int passLength; // length of password
	private static int specialRange; //special password selection range
	private static int range;
	private static boolean isInt; // check for integer12 type input
	private static boolean needSpecial; // check for whether user need special characters
	
	static Random rand = new Random();
	// each character is randomly pick from this array
	static String sLetter[] = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
			"r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "H", "I", "J", "K", "L", "M",
			"N", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "X", "Z", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "0", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+" };

	private static void initialize() {
		currTime = "";
		passLength = 0;
		specialRange = 75;
		range = 63;
		isInt = false;
		needSpecial = false;
	}

	public static void lenPassword() {
		Scanner input = new Scanner(System.in);
		do {
			System.out.println("Please enter the length of password: ");
			if (input.hasNextInt()) {
				passLength = input.nextInt();
				if (passLength > 5 && passLength < 17) {
					isInt = true;
				} else {
					System.out.println("The length of password is not vaild!"
							+ " We recommend the length of password between 6 and 16 character long.");
					isInt = false;
				}
			} else {
				System.out.println("Plase enter valid number for length!");
				isInt = false;
				input.next();
			}
		} while (!(isInt));

	}

	public static void specialChar() {
		String yes = "yes";
		String no = "no";
		boolean vaildInput = false;
		Scanner kbd = new Scanner(System.in);
		String putin;
		do {
			System.out.println("Do you need special character in your password (yes or no)?");
			if (kbd.hasNext()) {
				putin = kbd.next();
				if (yes.equalsIgnoreCase(putin)) {
					vaildInput = true;
					needSpecial = true;
				} else if (no.equalsIgnoreCase(putin)) {
					vaildInput = true;
					needSpecial = false;
				}
//				} else {
//					System.out.println("Your input is not vaild!");
//					vaildInput = false;
//				}  //repetitive??
			} else {
				System.out.println("Your input is not vaild!");
				vaildInput = false;
			}
		} while (!(vaildInput));
	}

	public static ArrayList<String> password(String[] sLetter) {
		ArrayList<String> iniPassword = new ArrayList<String>(); // iniPassword is the password in ArrayList form
		int passwordIndex = 0; // passwordIndex will be randomly assigned new integer
		if (needSpecial) { // password has special characters
			for (int i = 0; i < passLength; ++i) {
				passwordIndex = rand.nextInt(specialRange); // only randomly generate integers from 0 to 75
				iniPassword.add(sLetter[passwordIndex]);
			}
		} else { // password hasn't special characters
			for (int i = 0; i < passLength; ++i) {
				passwordIndex = rand.nextInt(range); // randomly generate integers from 0 to 63, last 12 elements in array
													// sLetter are special characters
				iniPassword.add(sLetter[passwordIndex]);
			}
		}
		return iniPassword;
	}

	public static void writing(String password) {
		// write password into Password.txt
		try {
			FileWriter fw = new FileWriter("Password.txt", true); // new content will not cover previous content by
																	// declaring true
			BufferedWriter bw = new BufferedWriter(fw); // decorator
			bw.write(password);
			bw.close();
		} catch (IOException e) {
			System.out.println("ERROR");
		}
	}

	public static void main(String[] args) {
		initialize();

		// get current date and time
		Date dt = new Date(); // dt is the current time
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"); // set format for current time(dt)
		currTime = df.format(dt); // use format() from DateForat to get certain format for current time

		lenPassword();
		specialChar();

		// convert arraylist to string
		String realpassword = password(sLetter).toString().replace(" ", "").replace(",", "").replace("[", "")
				.replace("]", ""); // convert to string from ArrayList format
		// finally write password into Password.txt
		writing(currTime + "\tPassword: " + realpassword + "\n");
	}

}
