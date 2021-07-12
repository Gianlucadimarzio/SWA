/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Didattica
 */
public class RestUtils {

    public static boolean matchContentType(HttpServletRequest request, String mediaType) {
        return (request.getContentType() != null
                && request.getContentType().split("\\s*;\\s*")[0].equals(mediaType));
    }

    public static boolean matchAccept(HttpServletRequest request, String mediaType) {
        String[] accepts = request.getHeader("Accept").split("\\s*,\\s*");
        for (String accept : accepts) {
            if (accept.equals(mediaType)) {
                return true;
            }
        }
        return false;
    }

    public static String getAbsoluteContextUrl(HttpServletRequest request) {
        return request.getScheme() + "://"
                + request.getServerName()
                + ":" + request.getServerPort()
                + request.getContextPath();
    }

    public static Gson getGson() {
        //creaimo un serializzatore/deserializzatore json registrando uno speciale handler per le date (vedi dopo)
        GsonBuilder b = new GsonBuilder();
        b.registerTypeAdapter(Calendar.class, new CalendarSerializer());
        b.registerTypeAdapter(GregorianCalendar.class, new CalendarSerializer());
        return b.create();

    }

    public static Object findBeanAttribute(Object bean, String attribute) {
        try {
            BeanInfo info = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] pds = info.getPropertyDescriptors();
            for (PropertyDescriptor pd : pds) {
                if (pd.getName().equals(attribute) && pd.getReadMethod() != null) {
                    return pd.getReadMethod().invoke(bean);
                }
            }
            return null;
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            return null;
        }
    }
}

//questo serializzatore è necessario per fare in modo che Gson serializzi le date (Calendar)
//sotto forma di millisecondi dalla epoch, come fa jackson (usato in jersey)
class CalendarSerializer implements JsonSerializer<Calendar>, JsonDeserializer<Calendar> {

    @Override
    public JsonElement serialize(Calendar src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getTimeInMillis());
    }

    @Override
    public Calendar deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(json.getAsJsonPrimitive().getAsLong());
        return cal;
    }
}
