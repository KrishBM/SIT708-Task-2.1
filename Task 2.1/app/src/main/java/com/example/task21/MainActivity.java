package com.example.task21;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // UI Components
    private Spinner spinnerConversionType, spinnerFromUnit, spinnerToUnit;
    private EditText etValue;
    private Button btnConvert;
    private TextView tvResult;

    // Variables for conversion
    private String currentConversionType;
    private String[] conversionTypes, lengthUnits, weightUnits, temperatureUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        initializeViews();
        setupSpinners();
        setupListeners();
    }

    private void initializeViews() {
        // Find all views by ID
        spinnerConversionType = findViewById(R.id.spinnerConversionType);
        spinnerFromUnit = findViewById(R.id.spinnerFromUnit);
        spinnerToUnit = findViewById(R.id.spinnerToUnit);
        etValue = findViewById(R.id.etValue);
        btnConvert = findViewById(R.id.btnConvert);
        tvResult = findViewById(R.id.tvResult);

        // Get string arrays from resources
        conversionTypes = getResources().getStringArray(R.array.conversion_types);
        lengthUnits = getResources().getStringArray(R.array.length_units);
        weightUnits = getResources().getStringArray(R.array.weight_units);
        temperatureUnits = getResources().getStringArray(R.array.temperature_units);

        // Set default conversion type
        currentConversionType = conversionTypes[0]; // Default to Length
    }

    private void setupSpinners() {
        // Setup Conversion Type Spinner
        ArrayAdapter<String> conversionTypeAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, conversionTypes);
        conversionTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConversionType.setAdapter(conversionTypeAdapter);

        // Initially setup unit spinners with length units (default)
        updateUnitSpinners(lengthUnits);
    }

    private void setupListeners() {
        // Conversion Type Spinner Listener
        spinnerConversionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentConversionType = conversionTypes[position];
                // Update unit spinners based on conversion type
                switch (position) {
                    case 0: // Length
                        updateUnitSpinners(lengthUnits);
                        break;
                    case 1: // Weight
                        updateUnitSpinners(weightUnits);
                        break;
                    case 2: // Temperature
                        updateUnitSpinners(temperatureUnits);
                        break;
                }
                // Clear result when conversion type changes
                tvResult.setText("");
                // Clear input field when conversion type changes
                etValue.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Unit spinners listeners to clear result when unit selection changes
        AdapterView.OnItemSelectedListener unitListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Clear result when unit selection changes
                tvResult.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        };

        spinnerFromUnit.setOnItemSelectedListener(unitListener);
        spinnerToUnit.setOnItemSelectedListener(unitListener);

        // Convert Button Click Listener
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performConversion();
            }
        });
    }

    private void updateUnitSpinners(String[] units) {
        // Update From Unit Spinner
        ArrayAdapter<String> fromUnitAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, units);
        fromUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromUnit.setAdapter(fromUnitAdapter);

        // Update To Unit Spinner
        ArrayAdapter<String> toUnitAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, units);
        toUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerToUnit.setAdapter(toUnitAdapter);
    }

    private void performConversion() {
        // Validate input using ValidationUtil
        if (!ValidationUtil.isValidInput(this, etValue.getText().toString())) {
            return;
        }

        // Get input value
        double inputValue = Double.parseDouble(etValue.getText().toString());

        // Get selected units
        int fromUnitPosition = spinnerFromUnit.getSelectedItemPosition();
        int toUnitPosition = spinnerToUnit.getSelectedItemPosition();
        String fromUnit = spinnerFromUnit.getSelectedItem().toString();
        String toUnit = spinnerToUnit.getSelectedItem().toString();

        // Validate unit selection (shows warning if same units)
        ValidationUtil.validateUnitSelection(this, fromUnitPosition, toUnitPosition);

        // Additional validation for temperature
        if (currentConversionType.equals("Temperature")) {
            if (!ValidationUtil.isValidTemperature(this, inputValue, fromUnitPosition)) {
                return;
            }
        }

        // Check if source and destination units are the same
        if (fromUnitPosition == toUnitPosition) {
            // If units are the same, result is the same as input
            tvResult.setText(String.format("%.2f %s", inputValue, toUnit));
            return;
        }

        // Perform conversion using ConverterUtil
        double result = 0;
        switch (currentConversionType) {
            case "Length":
                result = ConverterUtil.convertLength(inputValue, fromUnitPosition, toUnitPosition);
                break;
            case "Weight":
                result = ConverterUtil.convertWeight(inputValue, fromUnitPosition, toUnitPosition);
                break;
            case "Temperature":
                result = ConverterUtil.convertTemperature(inputValue, fromUnitPosition, toUnitPosition);
                break;
        }

        // Display result with 2 decimal places and the unit
        tvResult.setText(String.format("%.2f %s", result, toUnit));
    }
}