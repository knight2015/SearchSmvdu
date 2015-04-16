/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import JavaSrc.Connections;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author knight
 */
public class RegisterAction extends ActionSupport {

    public RegisterAction() {
    }

    private String username;
    private String password;
    private String email;
    private String gender;
    private String fname;
    private String lname;

    Connection con = Connections.conn();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    @Override
    public void validate() {

        String query = "select email from user where username='" + username + "'";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                addActionMessage("User Name not avaible...!!!");
            } else {
                String query1 = "select * from user where email='" + email + "'";
                st = con.createStatement();
                rs = st.executeQuery(query1);
                if (rs.next()) {
                    addActionMessage("Email not avaible...!!!");
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

    }

    @Override
    public String execute() throws Exception {
        String query = "select email from user where username='" + username + "'";
        Statement st;
        try {
            
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                return "fail";

            } else {
                query = "insert into user(Name, Password, Email, Username, Gender, image, dateuser) values(?,?,?,?,?,?,?)";
                try {
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, fname + " " + lname);
                    ps.setString(2, password);
                    ps.setString(3, email);
                    ps.setString(4, username);
                    ps.setString(5, gender);
                    ps.setString(6, "images/profile.jpg");
                    ps.setDate(7, new java.sql.Date((new Date(System.currentTimeMillis())).getTime()));
                    ps.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(RegisterAction.class.getName()).log(Level.SEVERE, null, ex);
                }
                return "success";

            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return "fail";
    }

}
