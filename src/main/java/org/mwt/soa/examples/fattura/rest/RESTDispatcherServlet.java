package org.mwt.soa.examples.fattura.rest;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Giuseppe
 */
public class RESTDispatcherServlet extends HttpServlet {

    Pattern COLLECTION_URL = Pattern.compile("/collezioni", Pattern.CASE_INSENSITIVE);   


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String pathInfo = request.getPathInfo();

        if (pathInfo != null) {
            Matcher collection_url_m = COLLECTION_URL.matcher(pathInfo);

            if (collection_url_m.matches()) {
                //la semantica standard vuole che una get
                //su una collezione ritorni la lista delle URI per accedere
                //ai suoi elementi
                //i parametri della query string possono essere usati come filtri
                getServletContext().getNamedDispatcher("CollectionResourceServlet").forward(request, response);
            }
            else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "REST operation not recognized");
            }
        } 
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "REST resource not found");
        }

    }
}
