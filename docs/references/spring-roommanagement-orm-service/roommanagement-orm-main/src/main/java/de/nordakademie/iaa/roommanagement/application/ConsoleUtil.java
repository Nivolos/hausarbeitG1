package de.nordakademie.iaa.roommanagement.application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Provides helper functions for the console.
 */
public class ConsoleUtil {

	/** The date formatter instance. */
	private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
			"dd.MM.yyyy HH:mm");

	/**
	 * Private constructor that prevents class instantiation.
	 */
	private ConsoleUtil() {
		// Do nothing
	}

	/**
	 * Reads a string input from the console.
	 * 
	 * @return the String read.
	 */
	public static String readString() {
		// Read user input
		String stringInput = null;
		while (stringInput == null) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(System.in));
				stringInput = reader.readLine();
			} catch (Exception e) {
				// No valid value entered by user
				System.out.println("Bitte geben Sie eine Zeichenkette ein:");
				stringInput = null;
			}
		}
		return stringInput;
	}

	/**
	 * Reads a int input from the console.
	 *
	 * @return the int read.
	 */
	public static int readInt() {
		// Read user input
		Integer value = null;
		while (value == null) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(System.in));
				value = new Integer(reader.readLine());
			} catch (Exception e) {
				// No valid value entered by user
				System.out.println("Bitte geben Sie eine Ganzzahl ein:");
				value = null;
			}
		}
		return value;
	}

	/**
	 * Reads a long input from the console.
	 *
	 * @return the int read.
	 */
	public static long readLong() {
		// Read user input
		Long value = null;
		while (value == null) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(System.in));
				value = new Long(reader.readLine());
			} catch (Exception e) {
				// No valid value entered by user
				System.out.println("Bitte geben Sie eine Ganzzahl ein:");
				value = null;
			}
		}
		return value;
	}

	/**
	 * Reads a boolean input from the console.
	 * 
	 * @return the boolean read.
	 */
	public static boolean readBoolean() {
		// Read user input
		Boolean value = null;
		while (value == null) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(System.in));
				String line = reader.readLine();
				if (line.equalsIgnoreCase("true")
						|| line.equalsIgnoreCase("false")) {
					value = Boolean.valueOf(line);
				} else if (line.equalsIgnoreCase("ja")) {
					value = Boolean.TRUE;
				} else if (line.equalsIgnoreCase("nein")) {
					value = Boolean.FALSE;
				} else {
					// No valid value entered by user
					System.out
							.println("Bitte geben Sie 'true', 'false', 'ja' oder 'nein' ein:");
				}
			} catch (Exception e) {
				System.out
						.println("Bitte geben Sie 'true', 'false', 'ja' oder 'nein' ein:");
				value = null;
			}
		}
		return value;
	}

	/**
	 * Reads a date from the console.
	 * 
	 * @return the date read.
	 */
	public static Date readDate() {
		// Read user input
		Date value = null;
		while (value == null) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(System.in));
				String line = reader.readLine();
				value = dateFormatter.parse(line);
			} catch (Exception e) {
				// No valid value entered by user
				System.out
						.println("Bitte geben Sie eine Datum im Format [dd.MM.yyyy HH:mm] ein:");
				value = null;
			}
		}
		return value;
	}
}
