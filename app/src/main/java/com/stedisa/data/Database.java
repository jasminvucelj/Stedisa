package com.stedisa.data;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;

import org.threeten.bp.LocalDate;
import org.threeten.bp.temporal.ChronoField;
import org.threeten.bp.temporal.ChronoUnit;
import org.threeten.bp.temporal.TemporalField;

import java.sql.Time;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private HashMap<Category, List<Transaction>> costs;
    private HashMap<Category, List<Transaction>> incomes;
    private List<Category> categories;

    private TemporalField temporalField;
    private Currency currency;

    private static Database db = new Database();

    private List<DatabaseChangeListener> listeners;

    private long transactionId = 0;
    private long categoryId = 0;

    private Database() {
        this.costs = new HashMap<>();
        this.incomes = new HashMap<>();
        this.categories = new ArrayList<>();

        this.listeners = new ArrayList<DatabaseChangeListener>();
        this.temporalField = ChronoField.MONTH_OF_YEAR;
        createMockupData();
    }

    public void createMockupData() {
        Category c1 = new Category(categoryId++, "Hrana", CategoryType.COST, "burger");
        Category c2 = new Category(categoryId++, "Piće", CategoryType.COST, "bottle");
        Category c3 = new Category(categoryId++, "Odjeća", CategoryType.COST, "shirt");
        Category c4 = new Category(categoryId++, "Obuća", CategoryType.COST, "shoe");

        Category c5 = new Category(categoryId++, "Plaća", CategoryType.INCOME, "money");
        Category c6 = new Category(categoryId++, "Stipendija", CategoryType.INCOME, "wallet");
        Category c7 = new Category(categoryId++, "Poklon", CategoryType.INCOME, "gift");

        categories.add(c1);
        categories.add(c2);
        categories.add(c3);
        categories.add(c4);
        categories.add(c5);
        categories.add(c6);
        categories.add(c7);

        LocalDate now = LocalDate.now();
        LocalDate fourthMonth = LocalDate.of(2017, 4, 5);
        LocalDate fifthMonth = LocalDate.of(2017, 5, 5);
        LocalDate sixthMonth = LocalDate.of(2017, 6, 5);
        Transaction t1 = new Transaction(transactionId++, 12f, "Čišps", "", c1, now);
        Transaction t2 = new Transaction(transactionId++, 6f, "Keksi", "", c1, fourthMonth);
        Transaction t3 = new Transaction(transactionId++, 6f, "Coca-cola", "", c2, fifthMonth);
        Transaction t4 = new Transaction(transactionId++, 6f, "Pivo", "", c2, sixthMonth);
        Transaction t5 = new Transaction(transactionId++, 89f, "Majica", "", c3, now);
        Transaction t6 = new Transaction(transactionId++, 250f, "Patike", "", c4, fifthMonth);

        Transaction t7 = new Transaction(transactionId++, 2000f, "Plaća Svibanj", "", c5, fifthMonth);
        Transaction t8 = new Transaction(transactionId++, 500f, "Baka", "", c7, sixthMonth);
        Transaction t9 = new Transaction(transactionId++, 200f, "Mama", "", c7, fourthMonth);
        Transaction t10 = new Transaction(transactionId++, 1100f, "Stipendija", "", c6, now);

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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(String code) {
        this.currency = Currency.getInstance(code);
    }

    public TemporalField getTemporalField() {
        return temporalField;
    }

    public void setTemporalField(TemporalField temporalField) {
        this.temporalField = temporalField;
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
        for (List<Transaction> lt : transactions.values()) {
            for (Transaction t : lt) {
                if (checkPeriod(t.getDate())) {
                    all.add(t);
                }
            }
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
        Transaction newTransaction = new Transaction(transactionId++, value, name, description, category, LocalDate.now());
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

    public void addCategory(String name, CategoryType type, String icon) {
        categories.add(new Category(categoryId++, name, type, icon));
        fire();
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
        //not found in that category, search every then
        for (List<Transaction> lt : transactions.values()) {
            for (Transaction t : lt) {
                if (t.equals(transaction)) {
                    return t;
                }
            }
        }
        //this should not happen;
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
                if (checkPeriod(t.getDate())) {
                    sum += t.getValue();
                }
            }
            result.add(new Pair<Category, Float>((Category)e.getKey(), sum));
        }
        return result;
    }

    public float getCostsSum(boolean checkPeriod) {
        return getSum(costs, checkPeriod);
    }

    public float getIncomesSum(boolean checkPeriod) {
        return getSum(incomes, checkPeriod);
    }

    private float getSum(HashMap<Category, List<Transaction>> transactions, boolean checkPeriod) {
        float sum = 0f;
        for (List<Transaction> lt : transactions.values()) {
            for (Transaction t: lt) {
                if (checkPeriod) {
                    if (checkPeriod(t.getDate())) {
                        sum += t.getValue();
                    }
                } else {
                    sum += t.getValue();
                }
            }
        }
        return sum;
    }

    public int getIncomeCount() {
        return getCount(incomes);
    }

    public int getCostCount() {
        return getCount(costs);
    }

    private int getCount(HashMap<Category, List<Transaction>> transactions) {
        int count = 0;
        for (List<Transaction> lt : transactions.values()) {
            count += lt.size();
        }
        return count;
    }

    public float getIncomeAverage() {
        return getAverage(incomes);
    }

    public float getCostAverage() {
        return getAverage(costs);
    }

    private float getAverage(HashMap<Category, List<Transaction>> transactions) {
        float sum = 0f;
        LocalDate minDate = LocalDate.now();
        for (List<Transaction> lt : transactions.values()) {
            for (Transaction t : lt) {
                sum += t.getValue();
                if (t.getDate().isBefore(minDate)) {
                    minDate = t.getDate();
                }
            }
        }
        long periodCount = Math.max(temporalField.getBaseUnit().between(minDate, LocalDate.now()), 1);
        return sum / periodCount;
    }

    public Pair<List<String>, Pair<List<Float>, List<Float>>> getValuesByPeriods() {
        LocalDate minDate = LocalDate.now();
        for (List<Transaction> lt : this.incomes.values()) {
            for (Transaction t : lt) {
                if (t.getDate().isBefore(minDate)) {
                    minDate = t.getDate();
                }
            }
        }
        for (List<Transaction> lt : this.costs.values()) {
            for (Transaction t : lt) {
                if (t.getDate().isBefore(minDate)) {
                    minDate = t.getDate();
                }
            }
        }

        List<Float> incomes = new ArrayList<>();
        List<Float> costs = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        do {
            float sumIncome = 0f;
            float sumCost = 0f;
            for (List<Transaction> lt : this.incomes.values()) {
                for (Transaction t : lt) {
                    if (checkPeriod(minDate, t.getDate())) {
                        sumIncome += t.getValue();
                    }
                }
            }
            for (List<Transaction> lt : this.costs.values()) {
                for (Transaction t : lt) {
                    if (checkPeriod(minDate, t.getDate())) {
                        sumCost += t.getValue();
                    }
                }
            }
            String s = getPeriodString(minDate);
            incomes.add(sumIncome);
            costs.add(sumCost);
            labels.add(s);
            if (checkPeriod(minDate)) {
                break;
            }
            minDate = minDate.plus(1, temporalField.getBaseUnit());
        } while(true);

        return new Pair<>(labels, new Pair<>(incomes, costs));
    }

    private String getPeriodString(LocalDate date) {
        if (temporalField == ChronoField.YEAR) {
            return Integer.toString(date.getYear());
        }
        if (temporalField == ChronoField.MONTH_OF_YEAR) {
            return Integer.toString(date.getMonth().getValue()) + "/" + Integer.toString(date.getYear());
        }
        if (temporalField == ChronoField.ALIGNED_WEEK_OF_YEAR) {
            return Integer.toString(date.get(ChronoField.ALIGNED_WEEK_OF_YEAR)) + "/" + Integer.toString(date.getYear());
        }
        if (temporalField == ChronoField.DAY_OF_MONTH) {
            return Integer.toString(date.getDayOfMonth()) + "/" + Integer.toString(date.getMonth().getValue()) + "/" + Integer.toString(date.getYear());
        }
        return "";
    }

    private boolean checkPeriod(LocalDate first, LocalDate second) {
        boolean sameYear = first.get(ChronoField.YEAR) == second.get(ChronoField.YEAR);
        if (temporalField == ChronoField.YEAR) {
            return sameYear;
        }
        boolean sameMonth = first.get(ChronoField.MONTH_OF_YEAR) == second.get(ChronoField.MONTH_OF_YEAR);
        if (temporalField == ChronoField.MONTH_OF_YEAR) {
            return sameYear && sameMonth;
        }
        boolean sameWeek = first.get(ChronoField.ALIGNED_WEEK_OF_YEAR) == second.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        if (temporalField == ChronoField.ALIGNED_WEEK_OF_YEAR) {
            return sameYear && sameWeek;
        }
        boolean sameDay = first.get(ChronoField.DAY_OF_MONTH) == second.get(ChronoField.DAY_OF_MONTH);
        if (temporalField == ChronoField.DAY_OF_MONTH) {
            return sameYear && sameMonth && sameDay;
        }
        return false;
    }

    private boolean checkPeriod(LocalDate date) {
        return checkPeriod(LocalDate.now(), date);
    }
}
