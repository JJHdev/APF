package common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebUtil {
	private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);
	/**
     * Description : 동인근원정책(Same-Origin Policy) 정책을 해결하기 위한 방법으로써, 
     * 사용자의 요청/응답을 특정 API로 연결해주는 역할을 수행
     *
     * @param reqUrl(String)                - 연결 URL
     * @param encode(String)                - Connection 인코딩(ex : UTF-8)
     * @param request(HttpServletRequest)    - 사용자 요청 객체
     * @param response(HttpServletResponse)    - 사용자 응답 객체
     */
    public static void getProxy(String reqUrl, String encode, 
        HttpServletRequest request, HttpServletResponse response) {
 
        getProxy(reqUrl, encode, null, request, response, null);
    }
 
    public static void getProxy(String reqUrl, String encode, Map<String,String> addHeader, 
        HttpServletRequest request, HttpServletResponse response, Map<String, Object> additionalParam) {
 
        HttpURLConnection httpURLConnection = null;
        InputStream is = null; 
 
        try {
            
            Enumeration<String> e = request.getParameterNames();
            
            StringBuffer param = new StringBuffer();
            
            while(e.hasMoreElements()){
                String paramKey = e.nextElement();
                
                String[] values = request.getParameterValues(paramKey);
                
                for(String value : values) {
                    param.append(paramKey + "=" + value + "&");
                }
            }
            
            String query = param.toString();
            query = query.substring(0, query.lastIndexOf("&"));
            
            if(request.getQueryString() != null && !"".equals(request.getQueryString())){
                reqUrl = reqUrl + "?" + query; 
            }
            
            System.out.println(reqUrl);
            
            URL targetURL = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection)targetURL.openConnection();
            Enumeration<String> headerKey = request.getHeaderNames();
            
            while(headerKey.hasMoreElements()) {
                String key = headerKey.nextElement();
                String value = request.getHeader(key);
                System.out.println(key + " : " + value);
                //httpURLConnection.addRequestProperty(key, value);
            }
            
            //httpURLConnection.setRequestMethod(request.getMethod().toUpperCase());

            if(httpURLConnection.getResponseCode() == 200) {
                IOUtils.copy(httpURLConnection.getInputStream(), response.getOutputStream());
            }else{
                IOUtils.copy(httpURLConnection.getErrorStream(), response.getOutputStream());
            }
            
        } catch (NullPointerException e) {
        	if(logger.isErrorEnabled()) {
            	logger.error("Proxy Error : ("+reqUrl+")", e);
            }
        } catch (Exception e) {
            if(logger.isErrorEnabled()) {
            	logger.error("Proxy Error : ("+reqUrl+")", e);
            }
        } finally {
            try { if(is != null) { is.close(); } } catch(IOException e) {
                if(logger.isErrorEnabled()) {
                	logger.error("exception", e);
                }
            } 
            if(httpURLConnection != null) { httpURLConnection.disconnect(); }
        }
    }

}