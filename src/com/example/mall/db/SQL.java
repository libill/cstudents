package com.example.mall.db;

/**
 * Created by Charwin on 14-6-12.
 */
public class SQL {
    public static final String T_FAVORITE = "favorite";


    public static final String CREATE_TABLE_FAVORITE =
            "CREATE TABLE IF NOT EXISTS " + T_FAVORITE + "(" +
                    "id VARCHAR PRIMARY KEY, " +
                    "app VARCHAR, " +
                    "carrier VARCHAR, " +
                    "username VARCHAR, " +
                    "password VARCHAR, " +
                    "email VARCHAR, " +
                    "mobile VARCHAR, " +
                    "bpUnit VARCHAR, " +
                    "heightUnit VARCHAR, " +
                    "weightUnit VARCHAR, " +
                    "ts VARCHAR " +
                    ")";
}
