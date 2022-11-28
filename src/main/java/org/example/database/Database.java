package org.example.database;

import org.example.entities.*;

import java.sql.*;
import java.util.ArrayList;

public class Database extends Configs {
    Connection dbConnection = null;
    private static final String SQL_LOGIN = "SELECT * FROM new_test.users WHERE login = ? AND password = ?;";
    private static final String SQL_SHOW_ADDITIONAL_INFO = "SELECT  new_test.users.id, new_test.users.login, new_test.users.password, new_test.users.money, new_test.additional_info.city, new_test.additional_info.country" + "\n" +
            "FROM new_test.users" + "\n" +
            "INNER JOIN new_test.additional_info ON new_test.users.id=new_test.additional_info.user_id" + "\n" +
            "where new_test.users.id = ?;";
    private static final String SQL_SHOW_INFO = "SELECT * FROM new_test.users WHERE id = ?;";
    private static final String ID = "SELECT id FROM new_test.users WHERE login = ? AND password = ?;";
    private static final String SQL_GET_ALL_PRODUCTS = "SELECT * FROM new_test.products;";
    private static final String SQL_CHANGE_USER_MONEY = "UPDATE new_test.users SET money = ? WHERE id = ?";
    private static final String SQL_CHANGE_PRODUCT_QUANTITY = "UPDATE new_test.products SET quantity = ? WHERE id = ?";
    private static final String SQL_BUY_PRODUCT = "INSERT INTO new_test.users_products ( user_id, product_id, product_quantity) VALUES (?,?,?)";
    private static final String MONEY = "SELECT money FROM new_test.users WHERE id = ?;";
    private static final String QUANTITY = "SELECT quantity FROM new_test.products WHERE id = ?;";
    private static final String PRICE = "SELECT price FROM new_test.products WHERE id = ?;";

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");

        this.dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return this.dbConnection;
    }

    public void signUpUser(Integer id, String city, String country) {
        String insert = "INSERT INTO " + Const.INFO_TABLE + "(" + Const.USER_INFO_ID + "," + Const.USERS_CITY + "," + Const.USERS_COUNTRY + ")" +
                "VALUES (?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setInt(1, id);
            prSt.setString(2, city);
            prSt.setString(3, country);


            prSt.executeUpdate();
            System.out.println("successful connection");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void signUpUserWithoutAdditional(String login, String password, Integer money) {
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USERS_LOGIN + "," + Const.USERS_PASS + "," + Const.USERS_MONEY + ")" +
                "VALUES (?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, login);
            prSt.setString(2, password);
            prSt.setInt(3, money);


            prSt.executeUpdate();
            System.out.println("successful connection");

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int id(String login, String password) {
        int id = 0;
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(ID);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    private ResultSet getResultSet(String SQL, String... params) {
        ResultSet rs = null;
        try {
            PreparedStatement statement = (getDbConnection()).prepareStatement(SQL);
            for (int i = 1; i <= params.length; i++) {
                statement.setString(i, params[i - 1]);
            }
            rs = statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public User login(String login, String password) {
        ResultSet result = getResultSet(SQL_LOGIN, login, password);
        try {
            if (result.next()) {
                return new User(result.getInt("id"), result.getString("login"), result.getString("password"));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public ArrayList<Products> getAllProducts() {
        ArrayList<Products> products = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(SQL_GET_ALL_PRODUCTS);
            ResultSet resultSet = preparedStatement.executeQuery(SQL_GET_ALL_PRODUCTS);
            while (resultSet.next()) {
                products.add(new Products(
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getInt("id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public void changeMoney(Integer userMoney, Integer id) {
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(SQL_CHANGE_USER_MONEY);
            preparedStatement.setInt(1, userMoney);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void buyProducts(Integer id, Integer productId, Integer quantity) {
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(SQL_BUY_PRODUCT);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, productId);
            preparedStatement.setInt(3, quantity);

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int money(Integer id) {
        int money = 0;
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(MONEY);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                money = resultSet.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return money;
    }

    public int productQuantity(Integer userChoice) {
        int quantity = 0;
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(QUANTITY);
            preparedStatement.setInt(1, userChoice);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                quantity = resultSet.getInt("quantity");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return quantity;
    }

    public void changeQuantity(Integer quantity, Integer userChoice) {
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(SQL_CHANGE_PRODUCT_QUANTITY);
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, userChoice);

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int price(Integer userChoice) {
        int price = 0;
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(PRICE);
            preparedStatement.setInt(1, userChoice);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                price = resultSet.getInt("price");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return price;
    }

    public ArrayList<User> getUserInfo(Integer id) {
        ArrayList<User> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(SQL_SHOW_ADDITIONAL_INFO);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            PreparedStatement preparedStatement1 = getDbConnection().prepareStatement(SQL_SHOW_INFO);
            preparedStatement1.setInt(1, id);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                while (true) {
                    users.add(new User(resultSet.getInt("id"), resultSet.getString("login"), resultSet.getString("password"), resultSet.getInt("money"), resultSet.getString("city"), resultSet.getString("country")));
                    break;
                }
            }
            else if (resultSet1.next()) {
                while (true) {
                    users.add(new User(resultSet1.getInt("id"), resultSet1.getString("login"), resultSet1.getString("password"), resultSet1.getInt("money")));
                    break;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

}

