package com.example.lab3;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private ListView cartListView;
    private ProductAdapterCart cartAdapter;

    private double calculateTotalAmount(List<Product> products) {
        double totalAmount = 0;
        for (Product product : products) {
            if (product.isChecked()) {
                totalAmount += product.getPrice();
            }
        }
        return totalAmount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);


        List<Product> selectedProducts = getIntent().getParcelableArrayListExtra("selectedProducts");


        cartListView = findViewById(R.id.cartListView);
        cartAdapter = new ProductAdapterCart(this, selectedProducts);
        cartListView.setAdapter(cartAdapter);


        TextView cartTotalAmount = findViewById(R.id.cartTotalAmount);
        double totalAmount = calculateTotalAmount(selectedProducts);
        cartTotalAmount.setText(String.format("Итого: %.2f BYN", totalAmount));

    }

}
