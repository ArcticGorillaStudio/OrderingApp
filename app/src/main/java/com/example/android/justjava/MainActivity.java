/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    String[] emailOdbiorcy = {"mates@vp.pl"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {

        EditText nameField = (EditText) findViewById(R.id.name_field);
       String name = nameField.getText().toString();

      CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.WhippedCreamCheckBox);
       CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolateCheckBox);

     boolean hasWhippedCream =  whippedCreamCheckBox.isChecked();
      boolean hasChocolate = chocolateCheckBox.isChecked();

       int price = calculatePrice(hasWhippedCream, hasChocolate);

      String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
     //  displayMessage(priceMessage);

        composeEmail(emailOdbiorcy,"Order for " + name, priceMessage);



    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * displaying text to screen
     * @param message
     */
    private void displayMessage(String message){
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(message);
    }

    /**
     *  Calculates the price of the order.
     * @return total price
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        //price of 1 cup of coffe
        int basePrice = 5;

        // add $1 if the user select whipped cream
        if(addWhippedCream){
            basePrice += 1;
        }

        // add $2 if the user select chocolate
        if(addChocolate){
            basePrice += 2;
        }

        //calculate the total order price by multiplying by quantity
        return quantity * basePrice;
    }

    public void increment(View view){
        quantity = quantity + 1;
        displayQuantity(quantity);
    }
    public void decrement(View view){
        if(!(quantity <= 0)){
            quantity--;
            displayQuantity(quantity);
        }
    }

    /**
     *
     *
     * @param price of the orders
     * @param addWhippedCream is wheather or not the user wants whiped cream topping
     * @return text summary
     */

    public String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name){
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd Chocolate? " + addChocolate;
        priceMessage += "\nQuantity " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";


        return priceMessage;
    }

    public void composeEmail(String[] addresses, String subject, String mailBody) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, mailBody);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}