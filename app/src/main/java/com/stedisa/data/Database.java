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
    private List<Category> categories;
    private static Database db = new Database();

    private List<DatabaseChangeListener> listeners;

    private long transactionId = 0;
    private long categoryId = 0;

    private Database() {
        this.costs = new HashMap<>();
        this.incomes = new HashMap<>();
        this.categories = new ArrayList<>();

        this.listeners = new ArrayList<DatabaseChangeListener>();
        createMockupData();
    }

    public void createMockupData() {
        Category c1 = new Category(1, "Hrana", CategoryType.COST, "burger");
        Category c2 = new Category(2, "Piće", CategoryType.COST, "bottle");
        Category c3 = new Category(3, "Odjeća", CategoryType.COST, "shirt");
        Category c4 = new Category(4, "Obuća", CategoryType.COST, "shoe");

        Category c5 = new Category(5, "Plaća", CategoryType.INCOME, "money");
        Category c6 = new Category(6, "Stipendija", CategoryType.INCOME, "wallet");
        Category c7 = new Category(7, "Poklon", CategoryType.INCOME, "gift");

        categories.add(c1);
        categories.add(c2);
        categories.add(c3);
        categories.add(c4);
        categories.add(c5);
        categories.add(c6);
        categories.add(c7);

        Transaction t1 = new Transaction(1, 12f, "Čišps", "", c1, getDate(2017, 7, 2, 18, 0));
        Transaction t2 = new Transaction(2, 6f, "Keksi", "", c1, getDate(2017, 7, 2, 18, 0));
        Transaction t3 = new Transaction(3, 6f, "Coca-cola", "", c2, getDate(2017, 7, 2, 18, 0));
        Transaction t4 = new Transaction(4, 6f, "Pivo", "", c2, getDate(2017, 7, 2, 18, 0));
        Transaction t5 = new Transaction(5, 89f, "Majica", "", c3, getDate(2017, 7, 2, 18, 0));
        Transaction t6 = new Transaction(6, 250f, "Patike", "", c4, getDate(2017, 7, 2, 18, 0));

        Transaction t7 = new Transaction(7, 5000f, "Plaća Svibanj", "", c5, getDate(2017, 7, 2, 18, 0));
        Transaction t8 = new Transaction(8, 500f, "Baka", "", c7, getDate(2017, 7, 2, 18, 0));
        Transaction t9 = new Transaction(9, 200f, "Mama", "", c7, getDate(2017, 7, 2, 18, 0));
        Transaction t10 = new Transaction(10, 1100f, "Stipendija", "", c6, getDate(2017, 7, 2, 18, 0));

        List<Transaction> c1l = new ArrayList<>();
        c1l.add(t1);
        c1l.add(t2);
        costs.put(c1, c1l);

        List<Transaction> c2l = new ArrayList<>();
        c2l.add(t3);
        c2l.add(t4);
        costs.put(c2, c2l);

        List<Transaction> c3l = new ArrayList<>();
        c3l.add(t5);
        costs.put(c3, c3l);

        List<Transaction> c4l = new ArrayList<>();
        c4l.add(t6);
        costs.put(c4, c4l);

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

    public void addListener(DatabaseChangeListener listener) {
        if (!this.listeners.contains(listener)) {
            this.listeners.add(listener);
        }
    }

    private void fire() {
        for (DatabaseChangeListener l : listeners) {
            l.onChange();
        }
    }

    public static Database getInstance() {
        return db;
    }

    public List<Category> getCategories() {
        return categories;
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

    public List<Category> getCostCategories() {
        return getCategoriesByType(CategoryType.COST);
    }

    public List<Category> getIncomeCategories() {
        return getCategoriesByType(CategoryType.INCOME);
    }

    public List<Category> getCategoriesByType(CategoryType type) {
        List<Category> result = new ArrayList<>();
        for (Category c: categories) {
            if (c.getType() == type) {
                result.add(c);
            }
        }
        return result;
    }

    private void add(HashMap<Category, List<Transaction>> transactions, float value, String name, String description, Category category) {
        List<Transaction> transactionList = transactions.get(category);
        Transaction newTransaction = new Transaction(transactionId++, value, name, description, category, new Date());
        if (transactionList == null) {
            transactionList = new ArrayList<>();
            transactionList.add(newTransaction);
            transactions.put(category, transactionList);
        } else {
            transactionList.add(newTransaction);
        }
        fire();
    }

    public void addIncome(float value, String name, String description, Category category) {
        add(incomes, value, name, description, category);
    }

    public void addCost(float value, String name, String description, Category category) {
        add(costs, value, name, description, category);
    }

    public void deleteIncome(Transaction t) {
        delete(incomes, t);
    }

    public void deleteCost(Transaction t) {
        delete(costs, t);
    }

    private void delete(HashMap<Category, List<Transaction>> transactions, Transaction t) {
        List<Transaction> transactionList = transactions.get(t.getCategory());
        transactionList.remove(t);
        fire();
    }

    public void editIncome(Transaction oldT, float value, String name, String description, Category category) {
        edit(incomes, oldT, value, name, description, category);
    }

    public void editCost(Transaction oldT, float value, String name, String description, Category category) {
        edit(costs, oldT, value, name, description, category);
    }

    private void edit(HashMap<Category, List<Transaction>> transactions, Transaction oldT, float value, String name, String description, Category category) {
        List<Transaction> transactionList = transactions.get(oldT.getCategory());
        if (oldT.getCategory() != category) {
            transactionList.remove(oldT);
            oldT.setName(name);
            oldT.setValue(value);
            oldT.setDescription(description);
            oldT.setCategory(category);
            List<Transaction> newTransactionList = transactions.get(category);
            if (newTransactionList == null) {
                newTransactionList = new ArrayList<>();
                newTransactionList.add(oldT);
                transactions.put(category, transactionList);
            } else {
                newTransactionList.add(oldT);
            }
        } else {
            Transaction savedTransaction = null;
            for (Transaction t : transactionList) {
                if (t.equals(oldT)) {
                    savedTransaction = t;
                }
            }
            if (savedTransaction != null) {
                savedTransaction.setName(name);
                savedTransaction.setValue(value);
                savedTransaction.setDescription(description);
            }
        }
        fire();
    }

    public Transaction refreshCost(Transaction t) {
        return refreshInstance(costs, t);
    }

    public Transaction refreshIncome(Transaction t) {
        return refreshInstance(incomes, t);
    }

    private Transaction refreshInstance(HashMap<Category, List<Transaction>> transactions, Transaction transaction) {
        List<Transaction> transactionList = transactions.get(transaction.getCategory());
        for (Transaction t : transactionList) {
            if (t.equals(transaction)) {
                return t;
            }
        }
        return transaction;
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
