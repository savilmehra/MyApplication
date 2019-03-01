
package com.realtimelocation.myapplication;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MusicDataEntity {

    @SerializedName("Table_Entity")
    @Expose
    private List<TableEntity> tableEntity = null;

    public List<TableEntity> getTableEntity() {
        return tableEntity;
    }

    public void setTableEntity(List<TableEntity> tableEntity) {
        this.tableEntity = tableEntity;
    }

}
