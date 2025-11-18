package servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import utils.Config;

@WebServlet("/images")
public class ImageServer extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fileName = request.getParameter("id");

        if (fileName == null) {
            response.sendError(400, "Archivo no especificado");
            return;
        }
        String basePath = Config.get("uploads.path").replace("\"", "");
        System.out.println("BASE PATH: " + basePath);
        File file = new File(basePath + fileName);

        if (!file.exists()) {
            response.sendError(404, "Imagen no encontrada");
            return;
        }

        FileInputStream fis = new FileInputStream(file);

        String mimeType = getServletContext().getMimeType(fileName);
        response.setContentType(mimeType != null ? mimeType : "image/jpeg");

        ServletOutputStream out = response.getOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }

        out.flush();
        fis.close();
    }
}
