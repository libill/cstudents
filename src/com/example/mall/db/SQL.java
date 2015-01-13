package com.example.mall.db;

public class SQL {
    public static final String T_FAVORITE = "favorite";


    public static final String CREATE_TABLE_FAVORITE =
            "CREATE TABLE IF NOT EXISTS " + T_FAVORITE + "(" +
                    "id VARCHAR PRIMARY KEY, " +
                    "title VARCHAR, " +
                    "url VARCHAR, " +
                    "createDate VARCHAR " +
                    ")";
}