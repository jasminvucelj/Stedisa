package com.stedisa.data;

import org.threeten.bp.LocalDate;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Transaction implements Serializable {
    private long id;
    private float value;
    private String name;
    private String description;
    private Category category;
    private LocalDate date;

    public Transaction(long id, float value, String name, String description, Category category, LocalDate date) {
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

    public String getValueString() {
        return String.format("%.2f", value);
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

    public LocalDate getDate() {
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return name + " " + getValueString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
