package com.hiair.app.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JacksonTest {
	//Test Object 
	// getter/setter 를 만들지 않기 위해 public 으로 선언
	// lombok 을 사용하면 간편
	static class JsonVO {
		  public String   val_str;
		  public int 	  val_int; 
		  public List<?>  val_list; 
		  public Map<?,?> val_map; 
		  public JsonVO   val_obj;
		  public List<JsonVO> val_obj_list;
	}
	
	static class UserVO {
		  public int  id;
		  public String	name; 
		  public int age; 
		  public TestInfo testInfo;
		  
		  public UserVO (int id, String name, int age){
			  this.id = id;
			  this.name = name;
			  this.age = age;
		  }
	}

	
	static class TestInfo {
		  public String	aaa; 
		  public String bbb;
		  
		  public TestInfo (String aaa, String bbb){
			  this.aaa = aaa;
			  this.bbb = bbb;
		  }
	}
	
	private UserVO user1;
	private UserVO user2;
	private List<UserVO> userList;
	private ObjectMapper mapper;
	
	@Before
	public void setUp() {
		//fixture 생성
		user1 = new UserVO(1,"박정하",27);
		user1.testInfo = new TestInfo("111", "222");
		user2 = new UserVO(2,"박유진",29);
		user2.testInfo = new TestInfo("333", "444");
		
		userList = new ArrayList<UserVO>();
		userList.add(user1);
		userList.add(user2);
		
		mapper = new ObjectMapper(); 
	}
	
	@Test
	public void prettyPrinter() throws JsonProcessingException {
		
		JsonVO jsonVO = new JsonVO();
		jsonVO.val_str = "hi my name is jeongha Park";
		jsonVO.val_int= 27;
		jsonVO.val_list= new ArrayList<String>(
				Arrays.asList("hi", "hello", "안녕"));
		
		// 포맷팅하여 스트링으로 변환
		String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonVO);
		  
		System.out.println(jsonString);
		
		// 포맷팅하여 파일로 저장
		//mapper.writerWithDefaultPrettyPrinter().writeValue(new File("output.json"), myResultObject);
	}
	
	
	@Test 
	public void test() throws JsonProcessingException {
		
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userList));
		
	}
	
	
	@Test
	public void jsonStringToJavaObject() throws IOException {
		//JSON String => Map 
		String jsString = "{\"name\":\"mkgil\",\"age\":25}"; 
		Map<String, Object> map = new HashMap<String, Object>(); 
		map = mapper.readValue(jsString, new TypeReference<Map<String, String>>(){}); 
		
		System.out.println("##### map : " + map); 
		System.out.println("##### map.get('name') : " + map.get("name"));
		
		IsInstanceOf.instanceOf(Map.class);
		
		List<String> list = new ArrayList<String>(Arrays.asList("hi", "hello", "안녕"));
		String jsString2 = mapper.writeValueAsString(list); 
		
		System.out.println("##### jsString2 : " + jsString2);
		
		List<String> new_list = new ArrayList<String>(); 
//		new_list = mapper.readValue(jsString2, new TypeReference<List<String>>(){}); 
		new_list = Arrays.asList(mapper.readValue(jsString2, String[].class)); 
		
		System.out.println("##### list : " + new_list); 
		System.out.println("##### list.get(0) : " + new_list.get(0));
		
		IsInstanceOf.instanceOf(List.class);
	}
	
	@Test
	public void javaObjectToJsonString() throws IOException {
		//Map => JSON String
		String json2 = ""; 
		Map<String, Object> map2 = new HashMap<String, Object>(); 
		map2.put("name", "mkgil"); 
		map2.put("age", 25); 
		json2 = mapper/*.writerWithDefaultPrettyPrinter()*/.writeValueAsString(map2); 
		System.out.println(json2);
		
		assertThat(json2, is("{\"name\":\"mkgil\",\"age\":25}"));
	}
	
	 @Test
	    public void readJsonFile() throws IOException {
	       /**
	        JacksonTestMap.json 파일 내용
	        {
	            "name":"박정하",
	            "role":"파트장"
	        }
	       */
	        InputStream is = JacksonTest.class.getClassLoader()
	                .getResourceAsStream("jacksonTestFile/JacksonTestMap.json");
	        Map map = mapper.readValue(is, Map.class);
	        assertTrue("박정하".equals(map.get("name")));
	        assertTrue("파트장".equals(map.get("role")));
	        
	    }

	    /**
	     * 이 테스트는 JsonNode를 사용하는 방법을 보여줍니다.
	     * @throws IOException
	     */
	    @Test
	    public void testComplexCodeUsingJsonNode() throws IOException {
	        /**
	         test2.json 파일 내용
	         {
	             "name":"JDM",
	             "age":30,
	             "bool":true,
	             "numbers":[1,2,3]
	         }
	         */
	        InputStream is = JacksonTest.class.getClassLoader()
	                .getResourceAsStream("jacksonTestFile/test2.json");
	        JsonNode test = mapper.readValue(is, JsonNode.class);

	        /**
	         * .get()를 이용해서 원하는 필드의 값을 가져옵니다.
	         * .get()은 값을 찾을 수 없는 경우 null을 반환합니다.
	         */
	        assertTrue(test.get("name").asText().equals("JDM"));
	        assertTrue(test.get("age").asInt() == 30);
	        assertTrue(test.get("bool").asBoolean() == true);
	        assertTrue(test.get("numbers").isArray());

	        /**
	         * .path()를 이용할 수도 있습니다.
	         * .get()와의 차이점은 값을 찾을 수 없는 경우 return 값으로
	         * MissingNode 객체를 반환합니다.
	         */
	        assertTrue(test.path("name").asText().equals("JDM"));
	    }

	    /**
	     * JsonNode 객체는 불변(immutable)하는 객체기 때문에 값을 변경하려면
	     * 가변 객체인 ObjectNode로 캐스팅해서 바꿔야 합니다.
	     * @throws IOException
	     */
	    @Test
	    public void testChangeValueUsingObejctNode() throws IOException {
	        String src = "{\"item\":\"i'm Item!\"}";
	        JsonNode obj = mapper.readValue(src, JsonNode.class);
	        assertTrue(obj.get("item").asText().equals("i'm Item!"));
	        ((ObjectNode) obj).put("item", "change Value!"); // .set()을 사용해도 됩니다.
	        assertTrue(obj.get("item").asText().equals("change Value!"));
	    }
	    

	    /**
	     * ================================================
	     * 클래스의 NULL 값 변수 제외 
	     * ================================================
	     */
	    @JsonInclude(JsonInclude.Include.NON_NULL) 
	    static class Test1 {
			  public int  id;
			  public String	name; 
			  public TestInfo testInfo;
			  
			public Test1(int id, String name, TestInfo testInfo) {
				super();
				this.id = id;
				this.name = name;
				this.testInfo = testInfo;
			}
		}
	    @Test
	    public void IgnoreNullFieldsTest() throws JsonProcessingException {
	    	Test1 t = new Test1(1, null, null);
	    	String result = mapper.writeValueAsString(t); 
	    	System.out.println(result);
	    }
	    
	}
