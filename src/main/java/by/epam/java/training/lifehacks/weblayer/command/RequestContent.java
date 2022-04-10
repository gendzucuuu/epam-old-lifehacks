package by.epam.java.training.lifehacks.weblayer.command;

import lombok.Getter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;

public class RequestContent {
    private HttpServletRequest request;
    @Getter
    private HashMap<String, Object> requestAttributes;
    @Getter
    private HashMap<String, String> requestParameters;
    @Getter
    private HashMap<String, Object> sessionAttributes;
    @Getter
    private HashMap<String, String> headers;

    public RequestContent(HttpServletRequest request){
        this.request = request;
        extractValues(request);
    }


    private void extractValues(HttpServletRequest request) {
        requestParameters = new HashMap<>();
        if (request.getParameterNames()!=null) {
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String name = paramNames.nextElement();
                requestParameters.put(name, request.getParameter(name));
            }
        }
        Enumeration<String> attrNames = request.getAttributeNames();
        requestAttributes = new HashMap<>();
        while (attrNames.hasMoreElements()){
            String name = attrNames.nextElement();
            requestAttributes.put(name, request.getAttribute(name));
        }
        sessionAttributes = new HashMap<>();
        Enumeration<String> sessionAttrNames = request.getSession().getAttributeNames();
        while (sessionAttrNames.hasMoreElements()){
            String name = sessionAttrNames.nextElement();
            sessionAttributes.put(name, request.getSession().getAttribute(name));
        }
        Enumeration<String> headersNames = request.getHeaderNames();
        headers = new HashMap<>();
        while (headersNames.hasMoreElements()){
            String name = headersNames.nextElement();
            headers.put(name, request.getHeader(name));
        }
    }


    public HttpServletRequest getRequest() {
        return request;
    }
    public void insertAttribute(String attrName, Object attr) {
        request.setAttribute(attrName, attr);
    }

    public void insertSessionAttribute (String name, Object attr){
        if (request.getSession(false) != null) {
            request.getSession(false).setAttribute(name, attr);
        }
    }

    public Object getRequestAttribute (String name){
        return requestAttributes.get(name);
    }

    public String getRequestParameter(String name){
        return requestParameters.get(name);
    }

    public Object getSessionAttribute(String name){
        return sessionAttributes.get(name);
    }

    public Part getPart(String name) throws IOException, ServletException {
        return request.getPart(name);
    }

    public String getHeader(String name){
        return headers.get(name);
    }

    public HttpSession getSession () {
        return request.getSession(false);
    }

    public String[] getRequestParameters (String name) {
        return request.getParameterValues(name);
    }
}
