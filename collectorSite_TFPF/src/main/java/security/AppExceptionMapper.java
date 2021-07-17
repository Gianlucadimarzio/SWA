package security;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import rest.RESTCustomException;


@Provider
public class AppExceptionMapper implements ExceptionMapper<RESTCustomException> {

    @Override
    public Response toResponse(RESTCustomException exception) {
        return Response.serverError().entity(exception.getMessage()).type(MediaType.APPLICATION_JSON).build();
    }

}
