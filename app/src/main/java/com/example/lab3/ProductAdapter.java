package com.example.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.CompoundButton;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<Product> productList;
    private OnProductCheckedListener onProductCheckedListener;
    private double totalAmount;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        totalAmount = 0.0;
    }

    public interface OnProductCheckedListener {
        void onProductChecked(int checkedCount);
        void onTotalAmountChanged(double totalAmount);
    }

    public void setOnProductCheckedListener(OnProductCheckedListener listener) {
        this.onProductCheckedListener = listener;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.product_item, null);

            viewHolder.productID = convertView.findViewById(R.id.productID);
            viewHolder.productName = convertView.findViewById(R.id.productName);
            viewHolder.productPrice = convertView.findViewById(R.id.productPrice);
            viewHolder.productCheckbox = convertView.findViewById(R.id.productCheckbox);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Product product = productList.get(position);

        viewHolder.productID.setText(product.getId() + "  ");
        viewHolder.productName.setText(product.getName());
        viewHolder.productPrice.setText(String.valueOf(product.getPrice()) + " TYG");
        viewHolder.productCheckbox.setChecked(product.isChecked());

        viewHolder.productCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                product.setChecked(isChecked);

                int checkedCount = getCheckedProductsCount();
                if (onProductCheckedListener != null) {
                    onProductCheckedListener.onProductChecked(checkedCount);

                    // Добавьте следующие строки, чтобы обновить сумму при изменении чекбокса
                    double totalAmount = calculateTotalAmount();
                    onProductCheckedListener.onTotalAmountChanged(totalAmount);
                }
            }
        });

        return convertView;
    }


    private double calculateTotalAmount() {
        double total = 0;
        for (Product product : productList) {
            if (product.isChecked()) {
                total += product.getPrice();
            }
        }
        return total;
    }


    static class ViewHolder {
        TextView productID;
        TextView productName;
        TextView productPrice;
        CheckBox productCheckbox;
    }

    private int getCheckedProductsCount() {
        int count = 0;
        for (Product product : productList) {
            if (product.isChecked()) {
                count++;
            }
        }
        return count;
    }
}
