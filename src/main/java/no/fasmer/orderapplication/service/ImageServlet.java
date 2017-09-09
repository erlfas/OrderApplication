package no.fasmer.orderapplication.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import no.fasmer.orderapplication.dao.PartDao;

@WebServlet(name = "ImageServlet", urlPatterns = {"/images/*"})
public class ImageServlet extends HttpServlet {

    @Inject
    private PartDao partDao;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final String partNumber = request.getParameter("partNumber");
        final String revision = request.getParameter("revision");

        final byte[] image = partDao.getImage(partNumber, Integer.parseInt(revision));

        response.setHeader("Content-Type", "image/jpg");
        response.setHeader("Content-Disposition", "inline; filename=\"" + "drawing" + "\"");

        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            input = new BufferedInputStream(new ByteArrayInputStream(image));
            output = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[8192];
            for (int length = 0; (length = input.read(buffer)) > 0;) {
                output.write(buffer, 0, length);
            }
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException ignore) {
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ignore) {
                }
            }
        }

    }

}
