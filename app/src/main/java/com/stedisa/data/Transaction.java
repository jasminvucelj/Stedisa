package com.stedisa.data;

import java.util.Date;

public class Transaction {
    private long id;
    private float value;
    private String name;
    private String description;
    private Category category;
    private Date date;

    public Transaction(long id, float value, String name, String description, Category category, Date date) {
        this.id = id;
        this.value = value;
        this.name = name;
        this.description = description;
        this.category = category;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public float getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public Date getDate() {
        return date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return name + String.format("%.2f", value);
    }
}
