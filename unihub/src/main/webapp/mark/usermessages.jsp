<%@ page language="java" import="java.util.*,
                                 javax.ejb.EJB,
                                 javax.naming.*,
                                 com.unihub.app.UserStatefulBI,
                                 com.unihub.app.User,
                                 com.unihub.app.Dbase,
                                 com.unihub.app.ListingsObj,
                                 com.unihub.app.Stuff,
                                 com.unihub.app.Message,
                                java.security.*,
                                javax.servlet.*,
                                javax.servlet.http.*"
    isELIgnored="false"%>
<%@ taglib uri="/WEB-INF/tlds/devjsp-taglib.tld" prefix="devjsp"%>

<!DOCTYPE html>

<%@ include file="../delvison/header.jsp"%>
<%! @EJB UserStatefulBI usr; %>
<% 
    String usernm = (String)session.getAttribute("username");
    Context context = new InitialContext();
    usr = (UserStatefulBI) context.lookup("ejb:unihub-ear/unihub-ejb//UserStatefulBean!com.unihub.app.UserStatefulBI");
%>   

<body style="background-color:#CCC">
  <% Dbase ubase = Dbase.create(); %>
  <% //get the user from the database that matches the current session's user
     User currentuser = ubase.getUser(usernm); %>
  <table>
  <tr>
    <td>
      <center><h4>Sent Messages</h4>
      <% for(Message m: currentuser.getSentMessages()) { %>
      <hr>
      <p><%=m.toString()%>
      </p>
      <% } %>
    </td>
    <td>
      <center><h4>Recieved Messages</h4>
      <% for(Message m: currentuser.getReceivedMessages()) { %>
      <hr>
      <p><%= m.toString() %>
      </p>
      <% } %>
    </td>
  </tr>
  </table>
</body>
</html>
