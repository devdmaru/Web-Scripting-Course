<%@page contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@page language="java" import="dbUtils.*" %>
<%@page language="java" import="model.Arsenal_Players.*" %>
<%@page language="java" import="java.util.ArrayList" %>

<%@page language="java" import="com.google.gson.*" %>

<%

    StringDataList list = new StringDataList();

    DbConn dbc = new DbConn();
    list.dbError = dbc.getErr(); // returns "" if connection is good, else db error msg.

    if (list.dbError.length() == 0) { // got open connection 

        String playerNameStartsWith = request.getParameter("q");
        if (playerNameStartsWith == null) {
            playerNameStartsWith = "";
        }

        // playerNameList is an object with an array of country objects inside, 
        // plus a possible dbError.
        System.out.println("jsp page ready to search for players with " + playerNameStartsWith);
        list = new StringDataList(playerNameStartsWith, dbc);
    } 

    // PREVENT DB connection leaks:
    dbc.close(); // EVERY code path that opens a db connection, must also close it.

    Gson gson = new Gson();
    out.print(gson.toJson(list).trim()); 
%>