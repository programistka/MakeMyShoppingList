package net.programistka.shoppingadvisor.presenters;

public class DbConfig {
    private String dbName;

    public DbConfig() {
        dbName = "shopping_advisor.db";
    }

    public DbConfig(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }
}
