package com.liufd.shoppingcart.bean;


import java.util.List;

public class ShoppingCartBean {
    private List<ItemsBean> items;

    public static class ItemsBean {

        private String name;
        private float price;
        private int count;
        private boolean isCheck;

        public ItemsBean(String name, float price, int count,boolean isCheck) {
            this.name = name;
            this.price = price;
            this.count = count;
            this.isCheck=isCheck;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
