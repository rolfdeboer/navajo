package com.dexels.navajo.adapter;

import javax.naming.Context;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.sql.*;
import org.dexels.grus.DbConnectionBroker;
import com.dexels.navajo.util.*;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

/**
 * This built in mappable class should be used as a general class for using arbitrary SQL queries and processing the ResultSet
 */
public class SQLMap implements Mappable {

  private final static int INFINITE = -1;

  public String driver;
  public String url;
  public String username;
  public String password;
  public String update;
  public String query;
  public int rowCount = 0;
  public int viewCount = 0;
  public int remainCount = 0;
  public ResultSetMap [] resultSet = null;
  public int startIndex = 1;
  public int endIndex = INFINITE;
  public String parameter = "";

  private DbConnectionBroker broker = null;
  private Connection con = null;
  private PreparedStatement statement = null;
  private ArrayList parameters = null;


  private static DbConnectionBroker fixedBroker = null;
  private boolean useFixedBroker = false;

  private static double totaltiming = 0.0;
  private static int requestCount = 0;

  class FinalizeThread extends Thread {
    private DbConnectionBroker broker = null;

    public FinalizeThread(DbConnectionBroker broker) {
      this.broker = broker;
    }

    public void run() {
      broker.destroy();
    }
  }

  public void load(Context context, Parameters parms, Navajo inMessage, Access access, ArrayList keyList) throws MappableException, UserException {
    // Check whether property file sqlmap.properties exists.
    ResourceBundle properties = null;
    properties = ResourceBundle.getBundle("sqlmap");
    if (properties != null) {
        // If propery file exists create a static connectionbroker that can be accessed by multiple instances of
        // SQLMap!!!
        this.driver = properties.getString("driver");
        this.url = properties.getString("url");
        this.username = properties.getString("username");
        this.password = properties.getString("password");
        if (fixedBroker == null) {
          fixedBroker = createConnectionBroker(driver, url, username, password);
        }
        if (fixedBroker == null)
          throw new UserException(-1, "in SQLMap. Could not open database connection [driver = " +
                                  driver + ", url = " + url + ", username = " + username + ", password = " + password + "]");
        useFixedBroker = true;
      }
      rowCount = 0;
  }

  public void store() throws MappableException, UserException {
    // Kill temporary broker.
    if (broker != null) {
      FinalizeThread t = new FinalizeThread(broker);
      t.start();
    }
  }

  public int getRowCount() {
    return this.rowCount;
  }

  public int getViewCount() {
    return this.viewCount;
  }

  public int getRemainCount() {
    return this.remainCount;
  }

  public void setRowCount(int i) {
    this.rowCount = i;
  }

  public void setDriver(String newDriver) {
    driver = newDriver;
    useFixedBroker = false;
  }

  public String getDriver() {
    return driver;
  }
  public void setUrl(String newUrl) {
    url = newUrl;
    useFixedBroker = false;
  }
  public String getUrl() {
    return url;
  }
  public void setUsername(String newUsername) {
    username = newUsername;
    useFixedBroker = false;
  }
  public String getUsername() {
    return username;
  }
  public void setPassword(String newPassword) {
    password = newPassword;
    useFixedBroker = false;
  }
  public String getPassword() {
    return password;
  }

  public void setUpdate(String newUpdate) throws UserException {
    update = newUpdate;
    parameters = new ArrayList();
  }


  /**
   * Use this method to define a new query.
   * All parameters used by a previous query are removed.
   */
  public void setQuery(String newQuery) {
    Util.debugLog("query = " + newQuery);
    System.out.println("query = " + newQuery);
    query = newQuery;
    parameters = new ArrayList();
  }

  public void setParameter(String param) {
    if (parameters == null)
      parameters = new ArrayList();
    Util.debugLog("adding parameter: " + param);
    if (param.indexOf(";") != -1) {
      java.util.StringTokenizer tokens = new java.util.StringTokenizer(param, ";");
      while (tokens.hasMoreTokens()) {
        parameters.add(tokens.nextToken());
      }
    } else {
      parameters.add(param);
    }
  }

  private static synchronized DbConnectionBroker createConnectionBroker(String driver, String url, String username, String password) throws UserException {
    DbConnectionBroker db = null;
    try {
      db = new DbConnectionBroker(driver, url, username, password, 2, 10, "/tmp/log.db", 0.1);
    } catch (Exception e) {
      e.printStackTrace();
      throw new UserException(-1, "Could not create connectiobroker: " + "[driver = " +
                                  driver + ", url = " + url + ", username = " + username + ", password = " + password + "]:" + e.getMessage());
    }
    return db;
  }

  private String getType(int i) {
    switch (i) {
      case java.sql.Types.DOUBLE: return "DOUBLE";
      case Types.FLOAT: return "FLOAT";
      case Types.INTEGER: return "INTEGER";
      case Types.DATE: return "DATE";
      case Types.VARCHAR: return "VARCHAR";
      case Types.BIT: return "BOOLEAN";
      default: return "UNSUPPORTED";
    }
  }

  /**
   * NOTE: DO NOT USE THIS METHOD ON LARGE RESULTSETS WITHOUT SETTING ENDINDEX.
   *
   */
  public ResultSetMap [] getResultSet() throws UserException {

    System.out.print("TIMING SQLMAP, start query...");
    long start = System.currentTimeMillis();
    requestCount++;
    ResultSet rs = null;
    try {
    if (!useFixedBroker) {
      broker = createConnectionBroker(driver, url, username, password);
      if (broker == null)
        throw new UserException(-1, "in SQLMap. Could not open database connection [driver = " +
                                  driver + ", url = " + url + ", username = " + username + ", password = " + password + "]");
      con = broker.getConnection();
    }
    else {
      con = fixedBroker.getConnection();
    }
    if (resultSet == null) {
        if (query != null)
          statement = con.prepareStatement(query);
        else
          statement = con.prepareStatement(update);

        if (con == null)
          throw new UserException(-1, "in SQLMap. Could not open database connection [driver = " +
                                  driver + ", url = " + url + ", username = " + username + ", password = " + password + "]");
        if (parameters != null) {
          for (int i = 0; i < parameters.size(); i++) {
            String param = (String) parameters.get(i);
            statement.setString(i+1, param);
          }
        }
        if (query != null)
          rs = statement.executeQuery();
        else
          statement.executeUpdate();
      }

      if (rs != null) {
        ResultSetMetaData meta = rs.getMetaData();
        int columns = meta.getColumnCount();
        ArrayList dummy = new ArrayList();
        int index = 1;
        remainCount = 0;
        while (rs.next()) {
         if ((index >= startIndex) && ((endIndex == INFINITE) || (index <= endIndex))) {
           ResultSetMap rm = new ResultSetMap();
           for (int i = 1; i < (columns + 1); i++) {
              String param = meta.getColumnName(i);
              int type = meta.getColumnType(i);
              Object value = null;
              switch (type) {
                case Types.INTEGER: if (rs.getString(i) != null) value = new Integer(rs.getInt(i)); break;
                case Types.VARCHAR: if (rs.getString(i) != null) value = new String(rs.getString(i)); break;
                case Types.DOUBLE: if (rs.getString(i) != null) value = new Double(rs.getDouble(i)); break;
                case Types.FLOAT: if (rs.getString(i) != null) value = new Double(rs.getString(i)); break;
                case Types.DATE: java.util.Calendar c = java.util.Calendar.getInstance();
                                 if (rs.getString(i) != null) {
                                    Date d = rs.getDate(i, c);
                                    value = c.getTime();
                                 }
                                 break;
                case Types.BIT: if (rs.getString(i) != null) value = new Boolean(rs.getBoolean(i));break;
                default: if (rs.getString(i) != null) value = new String(rs.getString(i)); break;
              }
              if (value == null)
                value = new String("null");
              rm.values.put(param, value);
           }
           dummy.add(rm);
           viewCount++;
         } else if (index >= startIndex) {
           remainCount++;
         }
         rowCount++;
         index++;
        }
        resultSet = new ResultSetMap[dummy.size()];
        resultSet = (ResultSetMap []) dummy.toArray(resultSet);
      }
    } catch (SQLException sqle) {
      sqle.printStackTrace();
      throw new UserException(-1, sqle.getMessage());
    } finally {
      try {
        if (con != null) {
          if (!useFixedBroker)
            broker.freeConnection(con);
          else
            fixedBroker.freeConnection(con);
        }
        if (rs != null)
          rs.close();
        if (statement != null)
          statement.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    long end = System.currentTimeMillis();
    double total = (end - start) / 1000.0;
    totaltiming += total;
    System.out.println("finished " + total + " seconds. Average query time: " + (totaltiming/requestCount) + " (" + requestCount + ")");
    return resultSet;
  }
  public void setStartIndex(int newStartIndex) {
    startIndex = newStartIndex;
  }
  public int getStartIndex() {
    return startIndex;
  }
  public void setEndIndex(int newEndIndex) {
    endIndex = newEndIndex;
  }
  public int getEndIndex() {
    return endIndex;
  }
}
