package com.unihub.app;

import java.math.BigInteger;
import java.io.*;
import java.util.ArrayList;
import java.security.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author Mark
 * 
 * A user model.
 */
public class User implements javax.servlet.http.HttpSessionBindingListener {
    
    private String name, school, email, password;
    private int id, reputation;
    private ArrayList<Message> sentMessages, recievedMessages;
    private ArrayList<String> watched;

    public User(String n, String p, String e, String s) {
        id = -1;
        name = n;
        password = p;
        email = e;
        school = s;
        reputation = 0;
        sentMessages = new ArrayList<Message>();
        recievedMessages = new ArrayList<Message>();
        watched = new ArrayList<String>();
    }

    public User() {
      id = -1;
      name = "bob";
      password = "bob";
      email = "bob@bob.com";
      school = "bobU";
      reputation = 9001;
      sentMessages = new ArrayList<Message>();
      recievedMessages = new ArrayList<Message>();
      watched = new ArrayList<String>();
    }

    public boolean isLoggedIn(HttpSession session) {
        String foundName = (String)session.getAttribute("username");
        return (foundName.equals(this.name));	
    }

    public String gravatar() throws NoSuchAlgorithmException {
      String plaintext = email;
      MessageDigest m = MessageDigest.getInstance("MD5");
      m.reset();
      m.update(plaintext.getBytes());
      byte[] digest = m.digest();
      BigInteger bigInt = new BigInteger(1,digest);
      String hashtext = bigInt.toString(16);
      // Now we need to zero pad it if you actually want the full 32 chars.
      while(hashtext.length() < 32 ){
        hashtext = "0"+hashtext;
      }
      return "http://www.gravatar.com/avatar/" + hashtext;  
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public int getReputation() {
      return reputation;
    }

    public void setReputation(int rep) {
      reputation = rep;
    }

    protected void incRep() {
      reputation += 1;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the school
     */
    public String getSchool() {
        return school;
    }

    /**
     * @param school the school to set
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public void addToSent(Message m) {
      sentMessages.add(m);
    }

    public void addToRecieved(Message m) {
      recievedMessages.add(m);
    }

    public ArrayList<Message> getSentMessages() {
      return sentMessages;
    }
    
    public ArrayList<Message> getRecievedMessages() {
      return recievedMessages;
    }
 
    public ArrayList<String> getWatched() {
      return watched;
    }
    
    public void watch (String uname) {
      watched.add(uname);
    }

    public void unwatch (String uname) {
      watched.remove(uname);
    }

    public boolean isWatching (String n) {
     // Dbase ubase = Dbase.create();
      if (watched.contains(n)) return true;
      return false;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        System.out.println("Successfully Logged Out");
    }
    
    
}
