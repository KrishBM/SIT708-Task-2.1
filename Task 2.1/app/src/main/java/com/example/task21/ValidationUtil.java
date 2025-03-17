package com.example.task21;

import android.content.Context;
import android.widget.Toast;

/**
 * Utility class for input validation in the Unit Converter app.
 */
public class ValidationUtil {

    /**
     * Validates if the input string is a valid number.
     *
     * @param context Context for showing toast messages
     * @param input String to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidInput(Context context, String input) {
        // Check if input is empty
        if (input == null || input.trim().isEmpty()) {
            Toast.makeText(context, "Please enter a value to convert", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            // Try to parse the input as a double
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            // If parsing fails, show error message
            Toast.makeText(context, "Please enter a valid number", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * Validates if the source and destination units are different.
     * Shows a warning if they are the same.
     *
     * @param context Context for showing toast messages
     * @param fromUnit Source unit position
     * @param toUnit Destination unit position
     * @return true (always returns true as same unit conversion is allowed but with warning)
     */
    public static boolean validateUnitSelection(Context context, int fromUnit, int toUnit) {
        if (fromUnit == toUnit) {
            Toast.makeText(context, "Source and destination units are the same", Toast.LENGTH_SHORT).show();
        }
        return true; // Always return true as this is just a warning
    }

    /**
     * Validates temperature input for specific restrictions.
     * For example, Kelvin cannot be negative.
     *
     * @param context Context for showing toast messages
     * @param value Temperature value
     * @param unit Temperature unit (0=Celsius, 1=Fahrenheit, 2=Kelvin)
     * @return true if valid, false otherwise
     */
    public static boolean isValidTemperature(Context context, double value, int unit) {
        // Check if Kelvin is negative (which is physically impossible)
        if (unit == 2 && value < 0) {
            Toast.makeText(context, "Kelvin cannot be negative", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Check if value would result in negative Kelvin
        if (unit == 0 && value < -273.15) { // Celsius below absolute zero
            Toast.makeText(context, "Temperature below absolute zero (-273.15°C)", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (unit == 1 && value < -459.67) { // Fahrenheit below absolute zero
            Toast.makeText(context, "Temperature below absolute zero (-459.67°F)", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}