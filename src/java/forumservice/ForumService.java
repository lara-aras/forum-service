/*
 * Filename: ForumService.java
 * Author: Lara Aras
 * Created: 15/11/2021
 * Operating System: Windows 10 Enterprise
 * Version: Project 1
 * Description: This file contains the web service for the forum application, 
 *              which provides the functionality to greet the user on the 
 *              home page, change a user's password, update a user's profile 
 *              information, post a comment on a topic, and post a reply on a 
 *              comment.
 */
package forumservice;

import javax.jws.*;
import java.sql.*;
import beans.DatabaseConnectionBean;
import org.apache.logging.log4j.*;

/**
 * Web service for the forum application
 *
 * @author Lara Aras
 */
@WebService(serviceName = "ForumService")
public class ForumService {

    private static final Logger logger = LogManager.getLogger(ForumService.class);

    /**
     * Version: Project 1
     * <p>
     * Date: 15/11/2021
     * <p>
     * Web service operation to greet user
     *
     * @param name
     * @return String
     * @author Lara Aras
     */
    @WebMethod(operationName = "sayHello")
    public String sayHello(@WebParam(name = "name") String name) {
        return "Hello " + name;
    }

    /**
     * Version: Project 1
     * <p>
     * Date: 15/11/2021
     * <p>
     * Web service operation to change a user's password
     *
     * @param newPassword
     * @param userID
     * @return boolean
     * @author Lara Aras
     */
    @WebMethod(operationName = "changePassword")
    public boolean changePassword(@WebParam(name = "newPassword") String newPassword,
            @WebParam(name = "userID") int userID) {
        DatabaseConnectionBean connect = new DatabaseConnectionBean();

        try {
            PreparedStatement passwordStatement = connect
                    .getConnection().prepareStatement(
                            "UPDATE ForumUser "
                            + "SET password = ? "
                            + "WHERE userID = ?");

            passwordStatement.setString(1, newPassword);
            passwordStatement.setInt(2, userID);

            passwordStatement.executeUpdate();
        } catch (SQLException sqle) {
            return false;
        }

        return true;
    }

    /**
     * Version: Project 1
     * <p>
     * Date: 16/11/2021
     * <p>
     * Web service operation to update user's profile
     *
     * @param firstName
     * @param lastName
     * @param displayName
     * @param email
     * @param userID
     * @return boolean
     * @author Lara Aras
     */
    @WebMethod(operationName = "updateProfile")
    public boolean updateProfile(@WebParam(name = "firstName") String firstName,
            @WebParam(name = "lastName") String lastName,
            @WebParam(name = "displayName") String displayName,
            @WebParam(name = "email") String email,
            @WebParam(name = "userID") int userID) {
        DatabaseConnectionBean connect = new DatabaseConnectionBean();

        try {
            PreparedStatement passwordStatement = connect
                    .getConnection().prepareStatement(
                            "UPDATE ForumUser "
                            + "SET firstName = ?, lastName = ?, "
                            + "displayName = ?, email = ? "
                            + "WHERE userID = ?");

            passwordStatement.setString(1, firstName);
            passwordStatement.setString(2, lastName);
            passwordStatement.setString(3, displayName);
            passwordStatement.setString(4, email);
            passwordStatement.setInt(5, userID);

            passwordStatement.executeUpdate();
        } catch (SQLException sqle) {
            return false;
        }

        return true;
    }

    /**
     * Version: Project 1
     * <p>
     * Date: 18/11/2021
     * <p>
     * Web service operation to post a comment on a topic
     *
     * @param topicID
     * @param text
     * @param userID
     * @return boolean
     * @author Lara Aras
     */
    @WebMethod(operationName = "postComment")
    public boolean postComment(@WebParam(name = "topicID") int topicID,
            @WebParam(name = "text") String text,
            @WebParam(name = "userID") int userID) {
        DatabaseConnectionBean connect = new DatabaseConnectionBean();

        try {
            PreparedStatement commentStatement = connect
                    .getConnection().prepareStatement(
                            "INSERT INTO Comment(topicID, userID, text) "
                            + "VALUES(?, ?, ?)");

            commentStatement.setInt(1, topicID);
            commentStatement.setInt(2, userID);
            commentStatement.setString(3, text);

            commentStatement.executeUpdate();
        } catch (SQLException sqle) {
            return false;
        }

        // Write to log
        String logBody = "New comment on topic with id "
                + String.valueOf(topicID) + ": " + text;
        logger.info(logBody);

        return true;
    }

    /**
     * Version: Project 1
     * <p>
     * Date: 18/11/2021
     * <p>
     * Web service operation to post a reply to a comment
     *
     * @param commentID
     * @param text
     * @param userID
     * @return boolean
     * @author Lara Aras
     */
    @WebMethod(operationName = "postReply")
    public boolean postReply(@WebParam(name = "commentID") int commentID,
            @WebParam(name = "text") String text,
            @WebParam(name = "userID") int userID) {
        DatabaseConnectionBean connect = new DatabaseConnectionBean();

        try {
            PreparedStatement replyStatement = connect
                    .getConnection().prepareStatement(
                            "INSERT INTO Reply(commentID, userID, text) "
                            + "VALUES(?, ?, ?)");

            replyStatement.setInt(1, commentID);
            replyStatement.setInt(2, userID);
            replyStatement.setString(3, text);

            replyStatement.executeUpdate();
        } catch (SQLException sqle) {
            return false;
        }

        // Write to log
        String logBody = "New reply on comment with id "
                + String.valueOf(commentID) + ": " + text;
        logger.info(logBody);

        return true;
    }
}
