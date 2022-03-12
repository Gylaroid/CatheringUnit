package ru.catheringunit.entity;

import java.util.List;

public class TableMeta {
    private String tableName;
    private List<FieldMeta> tableFields;

    public List<FieldMeta> getTableFields() {
        return tableFields;
    }

    public void setTableFields(List<FieldMeta> tableFields) {
        this.tableFields = tableFields;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
