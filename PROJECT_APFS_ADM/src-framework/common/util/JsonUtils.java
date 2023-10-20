package common.util;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class JsonUtils {

    /**
     * Json 문자열을 List<Map<String,Object>> 객체로 변환
     * @param jsonString
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static List<Map<String,Object>> jsonToList(String jsonString) throws JsonParseException, JsonMappingException, IOException {

        List<Map<String, Object>> list = null;
        if(!CommUtils.isEmpty(jsonString)) {
            ObjectMapper mapper = new ObjectMapper();
            TypeFactory typeFactory = mapper.getTypeFactory();
            list = mapper.readValue(jsonString, typeFactory.constructCollectionType(List.class,  Map.class));
        }
        return list;
    }

    /**
     * json 문자열을 Map 객체로 변환
     * 이때 입력된 순서를 유지하기 위해 LinkedHashMap을 이용
     * @param jsonString
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static Map<String, Object> jsonToMap(String jsonString)  throws JsonParseException, JsonMappingException, IOException {
        Map<String, Object> map = null;
        if(!CommUtils.isEmpty(jsonString)) {
            ObjectMapper mapper = new ObjectMapper();
            map = new LinkedHashMap<String, Object>();
            // convert JSON string to map
            map = mapper.readValue(jsonString, new TypeReference<Map<String,Object>>(){});

        }
        return map;
    }

    /**
     * map 객체를 문자열로 변환
     * @param map
     * @return
     * @throws JsonProcessingException
     */
    public static String mapToJson(Map<String, Object> map) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json         = "";

        // convert map to JSON string
//        json = mapper.writeValueAsString(map);
        json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);

        return json;
    }
    
    public static String getMethodName(String snakeString) {
        boolean isUpper = true;
        StringBuilder res = new StringBuilder("set");
        for (int i=0; i<snakeString.length(); i++) {
            char c = snakeString.charAt(i);
            if (isUpper) {
                res.append(("" + c).toUpperCase());
                isUpper = false;
            } else if (c == '_') {
                isUpper = true;
            } else {
                res.append(c);
            }
        }
        return res.toString();
    }

    @SuppressWarnings("unchecked")
	public static void jsonToObject(JSONObject jobj, Object obj) {
        Class<?> clazz = obj.getClass();

        HashMap<String, Method> methods = new HashMap<>();
        for(Method m : clazz.getMethods()) {
            methods.put(m.getName(), m);
        }
        
        jobj.forEach( (key, value) -> {
            try {
                String mname = getMethodName(key.toString());
                
                if (value == null || value instanceof Boolean ||
                        value instanceof Number || value instanceof String) {
                    methods.get(mname).invoke(obj, new Object[]{value});
                } else if (value instanceof JSONObject) {
                    Class<?> vclazz = value.getClass();
                    Constructor<?> cons = vclazz.getConstructor(new Class[]{});
                    Object nobj = cons.newInstance(new Object[]{});
                    methods.get(mname).invoke(obj, new Object[]{nobj});
                } else if (value instanceof JSONArray) {
                    // TODO: build object array
                } else {
                    // I don't know what to do.
                }
            } catch(Exception ignored) {}
        });
    }
    
}
