/*
This servlet is meant for handling File Uploads and was made by following this
tutorial:
http://docs.oracle.com/javaee/6/tutorial/doc/glraq.html
-Delvison
*/

package com.unihub.app;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

@WebServlet(name = "FileUploadServlet", urlPatterns = { "/upload" })
@MultipartConfig /* tells servlet to expect requests made up of 
                                 multipart/form-data MIME type*/
public class FileUploadServlet extends HttpServlet {

  private final static Logger LOGGER = 
            Logger.getLogger(FileUploadServlet.class.getCanonicalName());

  /* This method Retrieves destination and file part from request. Then create a 
     FileOutputStream to copy the file into desired directory. */          
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");

    // Create path components to save the file
    String path = request.getSession().getServletContext().getRealPath("/listings");
    String itemId = request.getParameter("id");
    path = path+"/"+itemId;
    final Part filePart = request.getPart("file");
    final String fileName = getFileName(filePart);

    File chkDir = new File(path);

    if (!chkDir.exists()){
        chkDir.mkdir();
    }

    OutputStream out = null;
    InputStream filecontent = null;
    final PrintWriter writer = response.getWriter();

    try {
        out = new FileOutputStream(new File(path + File.separator
                + fileName));
        filecontent = filePart.getInputStream();

        int read = 0;
        final byte[] bytes = new byte[1024];

        while ((read = filecontent.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        writer.println("DEBUG MSG:::\n New file " + fileName + " created at " + path+"\n");
        writer.println("\n\n\nFile was successfully uploaded. Just have to decide how to manage the files once they are on the server.");

        LOGGER.log(Level.INFO, "File{0}being uploaded to {1}", 
                new Object[]{fileName, path});
    } catch (FileNotFoundException fne) {
        writer.println("You either did not specify a file to upload or are "
                + "trying to upload a file to a protected or nonexistent "
                + "location.");
        writer.println("<br/> ERROR: " + fne.getMessage());

        LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}", 
                new Object[]{fne.getMessage()});
    } finally {
        if (out != null) {
            out.close();
        }
        if (filecontent != null) {
            filecontent.close();
        }
        if (writer != null) {
            writer.close();
        }
    }
  }

  private String getFileName(final Part part) {
    final String partHeader = part.getHeader("content-disposition");
    LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
    for (String content : part.getHeader("content-disposition").split(";")) {
        if (content.trim().startsWith("filename")) {
            return content.substring(
                    content.indexOf('=') + 1).trim().replace("\"", "");
        }
    }
    return null;
  }
}