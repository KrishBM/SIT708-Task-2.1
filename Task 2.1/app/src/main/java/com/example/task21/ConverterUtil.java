package com.example.task21;

public class ConverterUtil {

    // Length conversion constants
    private static final double INCH_TO_CM = 2.54;
    private static final double FOOT_TO_CM = 30.48;
    private static final double YARD_TO_CM = 91.44;
    private static final double KM_TO_CM = 100000;
    private static final double MILE_TO_CM = 160934;

    // Weight conversion constants
    private static final double KG_TO_G = 1000;
    private static final double OUNCE_TO_G = 28.3495;
    private static final double POUND_TO_G = 453.592;
    private static final double TON_TO_G = 907185;

    /**
     * Converts length values between different units.
     *
     * @param value      the input value to convert
     * @param fromUnit   the index of the source unit (0=cm, 1=inch, 2=foot, 3=yard, 4=km, 5=mile)
     * @param toUnit     the index of the target unit (0=cm, 1=inch, 2=foot, 3=yard, 4=km, 5=mile)
     * @return           the converted value
     */
    public static double convertLength(double value, int fromUnit, int toUnit) {
        // Convert to base unit (cm)
        double valueInCm = convertToCm(value, fromUnit);

        // Convert from cm to target unit
        return convertFromCm(valueInCm, toUnit);
    }

    /**
     * Converts a length value to centimeters.
     *
     * @param value    the input value
     * @param fromUnit the unit index to convert from
     * @return         the value in centimeters
     */
    private static double convertToCm(double value, int fromUnit) {
        switch (fromUnit) {
            case 0: // cm
                return value;
            case 1: // inch
                return value * INCH_TO_CM;
            case 2: // foot
                return value * FOOT_TO_CM;
            case 3: // yard
                return value * YARD_TO_CM;
            case 4: // km
                return value * KM_TO_CM;
            case 5: // mile
                return value * MILE_TO_CM;
            default:
                return 0;
        }
    }

    /**
     * Converts a centimeter value to another length unit.
     *
     * @param valueInCm the value in centimeters
     * @param toUnit    the unit index to convert to
     * @return          the converted value
     */
    private static double convertFromCm(double valueInCm, int toUnit) {
        switch (toUnit) {
            case 0: // cm
                return valueInCm;
            case 1: // inch
                return valueInCm / INCH_TO_CM;
            case 2: // foot
                return valueInCm / FOOT_TO_CM;
            case 3: // yard
                return valueInCm / YARD_TO_CM;
            case 4: // km
                return valueInCm / KM_TO_CM;
            case 5: // mile
                return valueInCm / MILE_TO_CM;
            default:
                return 0;
        }
    }

    /**
     * Converts weight values between different units.
     *
     * @param value    the input value to convert
     * @param fromUnit the index of the source unit (0=g, 1=kg, 2=oz, 3=lb, 4=ton)
     * @param toUnit   the index of the target unit (0=g, 1=kg, 2=oz, 3=lb, 4=ton)
     * @return         the converted value
     */
    public static double convertWeight(double value, int fromUnit, int toUnit) {
        // Convert to base unit (g)
        double valueInGrams = convertToGrams(value, fromUnit);

        // Convert from grams to target unit
        return convertFromGrams(valueInGrams, toUnit);
    }

    /**
     * Converts a weight value to grams.
     *
     * @param value    the input value
     * @param fromUnit the unit index to convert from
     * @return         the value in grams
     */
    private static double convertToGrams(double value, int fromUnit) {
        switch (fromUnit) {
            case 0: // g
                return value;
            case 1: // kg
                return value * KG_TO_G;
            case 2: // oz
                return value * OUNCE_TO_G;
            case 3: // lb
                return value * POUND_TO_G;
            case 4: // ton
                return value * TON_TO_G;
            default:
                return 0;
        }
    }

    /**
     * Converts a gram value to another weight unit.
     *
     * @param valueInGrams the value in grams
     * @param toUnit       the unit index to convert to
     * @return             the converted value
     */
    private static double convertFromGrams(double valueInGrams, int toUnit) {
        switch (toUnit) {
            case 0: // g
                return valueInGrams;
            case 1: // kg
                return valueInGrams / KG_TO_G;
            case 2: // oz
                return valueInGrams / OUNCE_TO_G;
            case 3: // lb
                return valueInGrams / POUND_TO_G;
            case 4: // ton
                return valueInGrams / TON_TO_G;
            default:
                return 0;
        }
    }

    /**
     * Converts temperature values between different units.
     *
     * @param value    the input value to convert
     * @param fromUnit the index of the source unit (0=Celsius, 1=Fahrenheit, 2=Kelvin)
     * @param toUnit   the index of the target unit (0=Celsius, 1=Fahrenheit, 2=Kelvin)
     * @return         the converted value
     */
    public static double convertTemperature(double value, int fromUnit, int toUnit) {
        // Temperature conversion requires specific formulas for each case
        if (fromUnit == toUnit) {
            return value; // No conversion needed
        }

        // First convert to Celsius as base unit
        double valueInCelsius;
        switch (fromUnit) {
            case 0: // From Celsius
                valueInCelsius = value;
                break;
            case 1: // From Fahrenheit
                valueInCelsius = (value - 32) / 1.8;
                break;
            case 2: // From Kelvin
                valueInCelsius = value - 273.15;
                break;
            default:
                return value;
        }

        // Convert from Celsius to target unit
        switch (toUnit) {
            case 0: // To Celsius
                return valueInCelsius;
            case 1: // To Fahrenheit
                return (valueInCelsius * 1.8) + 32;
            case 2: // To Kelvin
                return valueInCelsius + 273.15;
            default:
                return value;
        }
    }
}