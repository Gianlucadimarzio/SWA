package org.mwt.soa.examples.fattura.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mwt.soa.examples.fattura.logic.Collezione;
import org.mwt.soa.examples.fattura.logic.CollezioneAPIDummy;
import org.mwt.soa.examples.fattura.logic.CollezioneRepoAPI;
import org.mwt.soa.examples.fattura.logic.CollezioneRepoError;

/**
 *
 * @author Giuseppe
 */
public class CollectionResourceServlet extends HttpServlet {

    private static final CollezioneRepoAPI api = new CollezioneAPIDummy();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       

        response.setContentType("application/json; charset=UTF-8");
        Gson g = RestUtils.getGson();

        Writer out = response.getWriter();

        if (RestUtils.matchAccept(request, "application/json")) {
            try {
                List<String> result_uris = new ArrayList();
                List<Collezione> results = api.getCollezione();
                for(Collezione result : results){
                    result_uris.add(RestUtils.getAbsoluteContextUrl(request) + "/rest/collezioni/" + result.getTitolo());
                }
                out.write(g.toJson(result_uris));
            } 
            catch (CollezioneRepoError ex) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error getting data entry: " + ex.getMessage());
            }
        } 
        else {
            response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }
    }

}

