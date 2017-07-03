package com.stedisa.data;

import java.io.Serializable;
import java.util.Objects;

public class Category implements Serializable {
    private long id;
    private String name;
    private String icon;
    private CategoryType type;

    public Category(long id, String name, CategoryType type, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public CategoryType getType() {
        return type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
