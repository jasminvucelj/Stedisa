package com.stedisa.data;

import android.content.Context;
import android.util.Pair;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private HashMap<Category, List<Transaction>> costs;
    private HashMap<Category, List<Transaction>> incomes;
    private HashMap<Long, Category> categories;
    private static Database db = new Database();

    private Database() {
        this.costs = new HashMap<>();
        this.incomes = new HashMap<>();
        this.categories = new HashMap<>();
    }

    public void createMockupData() {
//        Category c1 = new Category(1, "Hrana", "burger");
//        Category c2 = new Category(2, "Piće", "bottle");
//        Category c3 = new Category(3, "Odjeća", "shirt");
//        Category c4 = new Category(4, "Obuća", "shoe");

        Category c5 = new Category(5, "Plaća", "money");
        Category c6 = new Category(6, "Stipendija", "wallet");
        Category c7 = new Category(7, "Poklon", "gift");

//        categories.put(c1.getId(), c1);
//        categories.put(c2.getId(), c2);
//        categories.put(c3.getId(), c3);
//        categories.put(c4.getId(), c4);
        categories.put(c5.getId(), c5);
        categories.put(c6.getId(), c6);
        categories.put(c7.getId(), c7);

//        Transaction t1 = new Transaction(1, 12f, "Čišps", "", c1, getDate(2017, 7, 2, 18, 0));
//        Transaction t2 = new Transaction(2, 6f, "Keksi", "", c1, getDate(2017, 7, 2, 18, 0));
//        Transaction t3 = new Transaction(3, 6f, "Coca-cola", "", c2, getDate(2017, 7, 2, 18, 0));
//        Transaction t4 = new Transaction(4, 6f, "Pivo", "", c2, getDate(2017, 7, 2, 18, 0));
//        Transaction t5 = new Transaction(5, 89f, "Majica", "", c3, getDate(2017, 7, 2, 18, 0));
//        Transaction t6 = new Transaction(6, 250f, "Patike", "", c4, getDate(2017, 7, 2, 18, 0));

        Transaction t7 = new Transaction(7, 5000f, "Plaća Svibanj", "", c5, getDate(2017, 7, 2, 18, 0));
        Transaction t8 = new Transaction(8, 500f, "Baka", "", c7, getDate(2017, 7, 2, 18, 0));
        Transaction t9 = new Transaction(9, 200f, "Mama", "", c7, getDate(2017, 7, 2, 18, 0));
        Transaction t10 = new Transaction(10, 1100f, "Stipendija", "", c6, getDate(2017, 7, 2, 18, 0));

//        List<Transaction> c1l = new ArrayList<>();
//        c1l.add(t1);
//        c1l.add(t2);
//        costs.put(c1, c1l);
//
//        List<Transaction> c2l = new ArrayList<>();
//        c2l.add(t3);
//        c2l.add(t4);
//        costs.put(c2, c2l);
//
//        List<Transaction> c3l = new ArrayList<>();
//        c3l.add(t5);
//        costs.put(c3, c3l);
//
//        List<Transaction> c4l = new ArrayList<>();
//        c4l.add(t6);
//        costs.put(c4, c4l);

        List<Transaction> c5l = new ArrayList<>();
        c5l.add(t7);
        incomes.put(c5, c5l);

        List<Transaction> c6l = new ArrayList<>();
        c6l.add(t10);
        incomes.put(c6, c6l);

        List<Transaction> c7l = new ArrayList<>();
        c7l.add(t8);
        c7l.add(t9);
        incomes.put(c7, c7l);
    }

    public static Database getInstance() {
        return db;
    }

    public List<Category> getCategories() {
        return new ArrayList<>(categories.values());
    }

    public Category getCategory(Long id) {
        return categories.get(id);
    }

    public List<Transaction> getAllCosts() {
        return getAll(costs);
    }

    public List<Transaction> getAllIncomes() {
        return getAll(incomes);
    }

    public List<Transaction> getAll(HashMap<Category, List<Transaction>> transactions) {
        List<Transaction> all = new ArrayList<>();
        for (List<Transaction> t : transactions.values()) {
            all.addAll(t);
        }
        return all;
    }

    public List<Pair<Category, Float>> getCostsSumsByCategories() {
        return getSumsByCategories(this.costs);
    }

    public List<Pair<Category, Float>> getIncomesSumsByCategories() {
        return getSumsByCategories(this.incomes);
    }

    public List<Pair<Category, Float>> getSumsByCategories(HashMap<Category, List<Transaction>> transactions) {
        List<Pair<Category, Float>> result = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        for (Map.Entry e : transactions.entrySet()) {
            float sum = 0f;
            for (Transaction t : (List<Transaction>)e.getValue()) {
                if (dateToCalendar(t.getDate()).get(Calendar.MONTH) == now.get(Calendar.MONTH)) {
                    sum += t.getValue();
                }
            }
            result.add(new Pair<Category, Float>((Category)e.getKey(), sum));
        }
        return result;
    }

    public double getCostsSum() {
        double sum = 0.0;
        //TODO dodaj vrijeme
        for (List<Transaction> lt : costs.values()) {
            for (Transaction t: lt) {
                sum += t.getValue();
            }
        }
        return sum;
    }

    public double getIncomesSum() {
        double sum = 0.0;
        //TODO dodaj vrijeme
        for (List<Transaction> lt : incomes.values()) {
            for (Transaction t: lt) {
                sum += t.getValue();
            }
        }
        return sum;
    }

    private static Calendar dateToCalendar(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c;
    }

    private static Date getDate(int year, int month, int day, int hour, int seconds) {
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day, hour, seconds);
        return c.getTime();
    }
}
