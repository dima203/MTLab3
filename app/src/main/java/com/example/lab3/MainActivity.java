package com.example.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private Button showCartButton;
    private TextView selectedItemCount;
    private TextView totalAmountTextView;
    private TextView itemCountLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        selectedItemCount = findViewById(R.id.selectedItemCount);
        totalAmountTextView = findViewById(R.id.totalAmountTextView);


        listView = findViewById(R.id.productListView);
        showCartButton = findViewById(R.id.showCartButton);

        productList = new ArrayList<>();

        productList.add(new Product(1, "Яблоко", 50));
        productList.add(new Product(2, "Консервы", 200));
        productList.add(new Product(3, "Каштан", 30));
        productList.add(new Product(4, "Кофе", 70));
        productList.add(new Product(5, "Яйцо", 60));
        productList.add(new Product(6, "Рыба", 120));
        productList.add(new Product(7, "Мясо", 400));
        productList.add(new Product(8, "Фундук", 40));
        productList.add(new Product(9, "Курут", 80));
        productList.add(new Product(10, "Лимон", 45));
        productList.add(new Product(11, "Молоко", 130));
        productList.add(new Product(12, "Арахис", 35));
        productList.add(new Product(13, "Пеммикан", 90));
        productList.add(new Product(14, "Изюм", 35));
        productList.add(new Product(15, "Копченая рыба", 150));
        productList.add(new Product(16, "Тан", 155));
        productList.add(new Product(17, "Сухарь", 65));
        productList.add(new Product(18, "Грецкие орехи", 30));

        productAdapter = new ProductAdapter(this, productList);
        listView.setAdapter(productAdapter);


        showCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Product> selectedProducts = new ArrayList<>();
                for (Product product : productList) {
                    if (product.isChecked()) {
                        selectedProducts.add(product);
                    }
                }


                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                intent.putParcelableArrayListExtra("selectedProducts", new ArrayList<>(selectedProducts));
                startActivity(intent);
            }
        });


        productAdapter.setOnProductCheckedListener(new ProductAdapter.OnProductCheckedListener() {
            @Override
            public void onProductChecked(int checkedCount) {
                selectedItemCount.setText(String.format("Товаров: %d", checkedCount));
            }

            @Override
            public void onTotalAmountChanged(double totalAmount) {
                updateTotalAmount(totalAmount);
            }
        });
    }

    private void updateTotalAmount(double totalAmount) {
        totalAmountTextView.setText(String.format("На сумму: %.2f TYG", totalAmount));
    }

    private double calculateTotalAmount() {
        double totalAmount = 0;
        for (Product product : productList) {
            if (product.isChecked()) {
                totalAmount += product.getPrice();
            }
        }
        return totalAmount;
    }
}
