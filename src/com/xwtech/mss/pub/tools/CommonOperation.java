package com.xwtech.mss.pub.tools;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.regexp.RE;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.huawei.api.SMEntry;
import com.xwtech.framework.login.pub.AbstractLoginToken;
import com.xwtech.framework.pub.utils.SessionUtils;
import com.xwtech.framework.pub.utils.StringUtils;
import com.xwtech.framework.pub.web.BaseFrameworkApplication;
import com.xwtech.framework.pub.web.FrameworkApplication;
import com.xwtech.framework.pub.web.SessionNameConstants;
import com.xwtech.mss.pub.UserBaseInfo;
import com.xwtech.mss.pub.constants.MssConstants;
import com.xwtech.mss.pub.po.Role;

/**
 * 整合常用方法
 * @author gdp
 */

public class CommonOperation {
	
	protected static final Logger logger = Logger.getLogger(CommonOperation.class);
	
	private static String dbName = "10.33.13.131";
	private static String name = "Customsms";
	private static String pass = "sqlmsde@infoxeie2000";


	/**
	 * 获得已登陆用户的信息
	 * @return
	 */
	public UserBaseInfo getLoginUserInfo(HttpServletRequest request){
		AbstractLoginToken loginToken = (AbstractLoginToken) SessionUtils.getObjectAttribute(request, SessionNameConstants.LOGIN_TOKEN);
		UserBaseInfo userBaseInfo = new UserBaseInfo();
		if (loginToken != null) {
			try {
				userBaseInfo = (UserBaseInfo) loginToken.getBaseInfo();
			}catch (ClassCastException cce) {
			  cce.printStackTrace();
			}
		}
		return userBaseInfo;
	}
	
	/**
	 * 获取已登录用户的角色
	 * @param request
	 * @return
	 */
	public Role[] getLoginUserRole(HttpServletRequest request){
		AbstractLoginToken loginToken = (AbstractLoginToken) SessionUtils.getObjectAttribute(request, SessionNameConstants.LOGIN_TOKEN);
		UserBaseInfo userBaseInfo = new UserBaseInfo();
		Role[] roles = null;
		if (loginToken != null) {
			try {
				userBaseInfo = (UserBaseInfo) loginToken.getBaseInfo();
				roles = loginToken.getRoles();
			}catch (ClassCastException cce) {
			  cce.printStackTrace();
			}
		}
		return roles;
	}

    /**
     * 上传附件
     * @param multipartRequest 包装过的request
     * @param fileColumnName 文件字段名称
     * @param uploadDir 文件上传路径
     * @return String[] 第一个参数是上传的文件原来的文件名，包括文件扩展名，第二个参数是文件上传到服务器后的物理路径
     * @author gu_daping
     */
	public String[] upLoadFile(MultipartHttpServletRequest multipartRequest,String fileColumnName, String uploadDir){
		//文件名
    	String fileName = "";
    	//文件上传后的保存路径
		String fileSaveFath = "";
		//上传的文件
		CommonsMultipartFile uploadFile = null;		
		String sep = System.getProperty("file.separator");		
	    //取得项目所在的绝对路径
	    String realpath = BaseFrameworkApplication.FrameworkWebAppRootPath;	  
	    //附件路径前缀
	    String prefixPath = FrameworkApplication.frameworkProperties.getPrefixPath();
	    //访问附件文件路径前缀
//	    String annexPathPrefix = FrameworkApplication.frameworkProperties.getAnnexPathPrefix(); 
	    //文件路径
		boolean mkdirFlag = false;		
		//上传文件的扩展名
		String filePostfix = "";
		
		try{
			uploadFile = (CommonsMultipartFile)multipartRequest.getFile(fileColumnName);
			//若取得的文件名为空，说明用户未上传，跳过上传文件步骤
			if(uploadFile!=null && !uploadFile.getOriginalFilename().equals("")){
				fileName = uploadFile.getOriginalFilename();
				filePostfix = uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().indexOf("."));				
				File dirPath = new File(realpath + prefixPath + uploadDir);
				String fileSaveName = String.valueOf(System.currentTimeMillis()) + createRandomNC(8);
				File saveFile = new File(realpath + prefixPath + uploadDir + sep + fileSaveName + filePostfix);
				//若目标文件夹不存在，生成文件夹
				if(!dirPath.exists()){
				    dirPath.mkdirs();
				}
				//上传文件到指定目录
				uploadFile.transferTo(saveFile);
				fileSaveFath = uploadDir+(uploadDir.substring((uploadDir.length()-1)).equals("/")?"":"/")+fileSaveName+filePostfix;
			}
		}catch(java.io.IOException ioe){
			//上传附件失败时，如果建立了目标文件夹，应该删除
			if(mkdirFlag){
				File dirPath = new File(realpath + uploadDir);
				dirPath.delete();
			}
			logger.error("上传附件失败", ioe);
		}
		String[] returnFileNames = new String[2];
		returnFileNames[0] = fileName;
		returnFileNames[1] = fileSaveFath;
		return returnFileNames;
	  }

	/*
	 * 得到总页码
	 * 
	 */
	
	public int getTotalPage(List dataList)
	{
		int totalPage = 1;
		int recordTotalNum = 0;
		if(dataList!=null && dataList.size()>0)
		{
			recordTotalNum = dataList.size();
			if (dataList.size() % MssConstants.COUNT_FOR_EVERY_PAGE == 0)
			{
				totalPage = dataList.size() / MssConstants.COUNT_FOR_EVERY_PAGE;
			} 
			else 
			{
				totalPage = dataList.size() / MssConstants.COUNT_FOR_EVERY_PAGE + 1;
			}
		}
		return totalPage;
	}
	
	
	
    /**
     * 根据指定的页码进行翻页操作
     * @param currentPage
     * @param dataList
     * @return
     */
    public HashMap getNextPageItems(String currentPage,List dataList){    	
    	List returnList = new ArrayList();
    	HashMap map = new HashMap();
    	int recordTotalNum = 0;
    	int totalPage = 1;
        if(currentPage==null || currentPage.equals("")){
        	currentPage = "1";
        }
        
        //当前页码
    	int currPage = Integer.parseInt(currentPage);
    	
		if(dataList!=null && dataList.size()>0){
			//取得总的记录数
			recordTotalNum = dataList.size();
			//取得总页数
			if(dataList.size() % MssConstants.COUNT_FOR_EVERY_PAGE == 0){
				totalPage = dataList.size() / MssConstants.COUNT_FOR_EVERY_PAGE;
			}else {
				totalPage = dataList.size() / MssConstants.COUNT_FOR_EVERY_PAGE + 1;
			}
			//当前页数大于总数时，自动跳转到最末页
			if(currPage>totalPage){
				currPage = totalPage;
			}
			//取得当前页的记录数，默认为每页的记录数
			int currPageListCount = MssConstants.COUNT_FOR_EVERY_PAGE;
			//当前页为最后一页时，取得最后一页的记录数
			if(currPage==totalPage){
				currPageListCount = dataList.size() - (currPage - 1) * MssConstants.COUNT_FOR_EVERY_PAGE;
			}
	    	//记录开始编号
			int entryId = (currPage-1)* MssConstants.COUNT_FOR_EVERY_PAGE;	
			//取得合适的记录
			for (int i = 0; i < currPageListCount; i++) {
				returnList.add(dataList.get(entryId));
				entryId=entryId+1;
			}
		}
		
		map.put("recordTotalNum", new Integer(recordTotalNum));
		map.put("totalPage", new Integer(totalPage));
		map.put("currPage", new Integer(currPage));		
		map.put("returnList", returnList);
		return map;
    }
    
    /**
     * 取得系统时间
     * @param type:	date--"20070318", 
     * 				dateFormated--"2007-03-18",
     * 				dateTime--"20070318182521", 
     *              dateTimeMin--"200703181825"  
     * 				dateTimeFormated--"2007-03-18 18:25:21"
     */
    public static String getSystemTime(String type){
    	Date dateTime = new Date();
    	String returnTime = "";
    	String year = String.valueOf(dateTime.getYear()+1900);
    	String month = String.valueOf(dateTime.getMonth()+1);
    	String date = String.valueOf(dateTime.getDate());
    	String hour = String.valueOf(dateTime.getHours());
    	String minute = String.valueOf(dateTime.getMinutes());
    	String second = String.valueOf(dateTime.getSeconds());
    	if(type==null || type.equals("date")){
    		returnTime = year + "" + (month.length()==1?("0"+month):month) + "" + (date.length()==1?("0"+date):month);
    	}
    	if(type!=null && type.equals("dateTime")){
    		returnTime = year + (month.length()==1?("0"+month):month) + (date.length()==1?("0"+date):date) + (hour.length()==1?("0"+hour):hour) 
    		+ (minute.length()==1?("0"+minute):minute) + (second.length()==1?("0"+second):second);
    	}
    	if(type!=null && type.equals("dateTimeMin")){
    		returnTime = year + (month.length()==1?("0"+month):month) + (date.length()==1?("0"+date):date) + (hour.length()==1?("0"+hour):hour) 
    		+ (minute.length()==1?("0"+minute):minute) ;
    	}
    	if(type!=null && type.equals("dateFormated")){
    		returnTime = year + "-" + (month.length()==1?("0"+month):month) + "-" + (date.length()==1?("0"+date):month);
    	}
    	if(type!=null && type.equals("dateTimeFormated")){
    		returnTime = year + "-" + (month.length()==1?("0"+month):month) + "-" + (date.length()==1?("0"+date):date)
    			+ " " + (hour.length()==1?("0"+hour):hour) + ":" + (minute.length()==1?("0"+minute):minute) + ":" + (second.length()==1?("0"+second):second);
    	}
    	return returnTime;
    }
    
    /**
     * 产生由数字组成的指定长度的随机数
     * @param length
     * @return
     */
    public String createRandomNo(int length){
    	Random random = new Random();
    	String sRand="";
    	for (int i=0;i<length;i++){
    	    String rand=String.valueOf(random.nextInt(10));
    	    sRand+=rand;
    	} 
    	return sRand;
    }

    /**
     * 产生由数字和字母组成的指定长度的的无重复的随机数
     * @param length
     * @return
     */
    public String createRandomNC(int length){
        String returnStr = "";
        Random r = new Random();

        int tempPos;
        char tempChar;
        char send[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        int len = send.length;
        for(int i=0;i<length;i++){
        	tempPos = Math.abs(r.nextInt())% len;
	        tempChar = send[tempPos];
	        returnStr += tempChar;
	        send[tempPos] = send[len-1];
	        send[len-1] = tempChar;
	        len--;
        }
//        System.out.println("Genarated Str ---------------------- " + returnStr);
        return returnStr;
    }
    
   /**
    * 转换表中时间格式
    * 
    * @param inputDateStr
    * @param type 1----输入:yyyy-mm-dd-HH:mm 输出:yyyymmdd
    *             2----输入:yyyy-mm-dd-HH:mm 输出:yyyymmddHHmm
    *             3----输入:yyyy-mm-dd HH:mm 输出:yyyyMMdd
    * 
    * @return
    */
    public String getDBTimeForMaterialStat(String inputDateStr,int type){
    	String format = "";
    	if(inputDateStr==null){
    		format = getSystemTime("date"); 
    	}
    	if(1==type){
    		format=(StringUtils.replace(StringUtils.replace(inputDateStr, "-", ""),":","")).substring(0,8);
    	}else if(2==type){
    		format=StringUtils.replace(StringUtils.replace(inputDateStr, "-", ""),":","");
    	}else if(3==type){
    		if(inputDateStr.length()<=10){
    			format=inputDateStr.replaceAll("-","");
    		}else{
    			format=inputDateStr.substring(0,10).replaceAll("-","");
    		}
    	}else{
    		format = getSystemTime("date");
    	}
    	return format;
    }
    
    /**
	 * 转换dms_material_flow_info表中的操作类型 
	 * type: 1 物料申请;
	 *       2 物料出库;
	 *       3 物料入库;
	 *       4 物料退库;
	 *       5 物料退库申请;
	 * @param type
	 * @return
	 */
	public static String transformToTypeName(long type){
		String operName = "";
		if(1==type){
			operName = "物料申请";
		}else if(2==type){
			operName = "物料出库";
		}else if(6==type){
			operName = "物料入库";
		}else if(3==type){
			operName = "物料退库";
		}else if(7==type){
			operName = "物料退库申请";
		}else if(4==type){
			operName = "统一下发";
		}else if(5==type){
			operName = "顾客退库";
		}else if(8==type){
			operName = "顾客出库";
		}else{
			operName = "未知的操作类型";
		}
		return operName;
	}
	
	/**
	 * 得到当前时间前一天的日期
	 * @param pattern
	 * @return
	 */
	public static String getYestodayDate(String pattern){
		if(StringUtils.isEmpty(pattern)){
			pattern = "yyyyMMdd";
		}
		long current = System.currentTimeMillis();
		long yestaday = current-86400000;
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date(yestaday));
	}

	/**
	 * 得到当前时间前两天的日期
	 * @param pattern
	 * @return
	 */
	public static String getLastDate(String pattern){
		if(StringUtils.isEmpty(pattern)){
			pattern = "yyyyMMdd";
		}
		long current = System.currentTimeMillis();
		long yestaday = current-(86400000*2);
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date(yestaday));
	}

	/**
	 * 得到当前时间前N天的日期
	 * @param pattern
	 * @return
	 */
	public static String getBeforeDate(String pattern, long n){
		if(StringUtils.isEmpty(pattern)){
			pattern = "yyyyMMdd";
		}
		long current = System.currentTimeMillis();
		long yestaday = current-(86400000*n);
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date(yestaday));
	}

	/**
	 * 得到当前时间后N天的日期
	 * @param pattern
	 * @return
	 */
	public static String getAfterDate(String pattern, long n){
		if(StringUtils.isEmpty(pattern)){
			pattern = "yyyyMMdd";
		}
		long current = System.currentTimeMillis();
		long yestaday = current+(86400000*n);
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date(yestaday));
	}
	
	/**
	 * 发送指定的短信内容到指定的用户
	 * @param destAddr
	 * @param content
	 * @throws Exception
	 */
	public void send(String destAddr, String content) throws Exception {
		try {
			logger.info("发送短信: "+content+"到 -> "+destAddr);
			SMEntry.init(dbName,name,pass);
			Date atTime = new Date();
			String sourceAddr = "07139";
			int needStateReport = 0;
			String serviceID = "123456";
			String feeType = "01";
			String feeCode = "0";
			int count = SMEntry.submitShortMessage(atTime, sourceAddr,
					destAddr, content, needStateReport, serviceID, feeType,
					feeCode);
			logger.info("返回码为： " + count);
		} catch (Exception e) {
			logger.info("返回码为： " + e);
		}
	}
	
	/**
	 * 根据记录序号排序
	 * @param resultList 欲排序的结果列表
	 * @param className 结果列表中的基础对象的名称，例如：com.xwtech.pms.pub.tools.ClassName
	 * @param methodName 基础对象中的方法名，例如：根据序号排序，方法名为 getId
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @author yangy
	 */
    public List sortListByRecordId (List resultList,String className,String methodName) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
        List list = new ArrayList();
        //通过反射机制加载类
        Class commClass = Class.forName(className);
        Object resultContent = null;
        Object resultContentNext = null;
        long recordId;
        long recordIdNext;
        int count = resultList.size();
        int id = 0;
        Method[] method = commClass.getMethods();
        Method targetMethod = null;
        //根据指定的方法名获取方法
        for(int j = 0 ;j < method.length; j++){
        	if(method[j].getName().equals(methodName)){
        		targetMethod = method[j];
        	}
        }
        for (int i = 0; i < count; i++) {
        	resultContent = resultList.get(0);
            id = 0;
            for (int j = 1; j < resultList.size(); j++) {
            	resultContentNext = resultList.get(j);
            	recordId = ((Long)targetMethod.invoke(resultContent, null)).longValue();
            	recordIdNext = ((Long)targetMethod.invoke(resultContentNext, null)).longValue();
                if (recordId > recordIdNext){
                	resultContent = resultContentNext;
                    id = j;
                }
            }
            list.add(i,resultContent);
            resultList.remove(id);
        }
        return list;
    }
    
    /**
     * 对字符串中的特殊符号<>&进行转换
     * @param str 转换前的字符
     * @return 转换后的字符
     */
    public String formatSpecialCharacters(String str){
    	//str = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;");
		StringBuffer sb = new StringBuffer("");
		if(str!=null && str.length()>0){
			for(int i = 0 ; i < str.length() ; i++) {
				char ch = str.charAt(i);
				switch(ch){
					case '>':sb.append("&gt;");break;
					case '<':sb.append("&lt;");break;
					case '\"':sb.append("&quot;");break;
					case '&':sb.append("&amp;");break;
					default:sb.append(ch);
			 	}
			}
		}
		return sb.toString();
    }

    /**
     * 将取出的值转换为字符串，如果值为null，转换为“”，否则返回原值
     * @param str
     * @return
     */
    public String convertNullToNullstring(Object str){
		return str==null?"":String.valueOf(str);
	}

    /**
     * 将字符串中的英文引号转换为中文引号
     * @return
     */
    public String convertEquoteToCquote(String str){
    	String[] oldChars = {"'", "\""};
    	String[] newChars = {"’", "”"};
    	if(oldChars!=null && oldChars.length>0 && str!=null && !str.equals("")){
    		for(int i=0;i<oldChars.length;i++){
    			str = str.replaceAll(oldChars[i], newChars[i]);
    		}
    	}
    	return str;
	}

    /**
     * 判断一个字符串是否可以转换为整型
     * @param string
     * @return
     */
    public boolean isNumber(String string)
    {
        boolean flag = true;
    	if(string!=null && string.length()>0){
        	for(int i=0;i<string.length();i++){
        		if(string.charAt(i)<'0' || string.charAt(i)>'9'){
        			flag = false;
        			break;
        		}
        	}
        }
        return flag;
    }

    /**
     * 判断一个字符串是否可以转换为浮点型
     * @param string
     * @return
     */
    public boolean isCouble(String string)
    {
        boolean flag = true;
        if(string.indexOf('.')!=string.lastIndexOf('.')){
			return false;
        }
    	if(string!=null && string.length()>0){
        	for(int i=0;i<string.length();i++){
        		if(string.charAt(i)!='.'&&(string.charAt(i)<'0' || string.charAt(i)>'9')){
        			flag = false;
        			break;
        		}
        	}
        }
        return flag;
    }

    /**
     * 将阿拉伯数字转换成中文大写数字
     * @param currencyDigits
     * @return
     */
    public String toCNnumber(String currencyDigits){
    	    double MAXIMUM_NUMBER = 99999999999.99; 
    	    //常量
    	    final String CN_ZERO = "零"; 
    	    final String CN_ONE = "壹"; 
    	    final String CN_TWO = "贰"; 
    	    final String CN_THREE = "叁"; 
    	    final String CN_FOUR = "肆"; 
    	    final String CN_FIVE = "伍"; 
    	    final String CN_SIX = "陆"; 
    	    final String CN_SEVEN = "柒"; 
    	    final String CN_EIGHT = "捌"; 
    	    final String CN_NINE = "玖"; 
    	    final String CN_TEN = "拾"; 
    	    final String CN_HUNDRED = "佰"; 
    	    final String CN_THOUSAND = "仟"; 
    	    final String CN_TEN_THOUSAND = "万"; 
    	    final String CN_HUNDRED_MILLION = "亿"; 
    	    final String CN_DOLLAR = "圆"; 
    	    final String CN_TEN_CENT = "角"; 
    	    final String CN_CENT = "分"; 
    	    final String CN_INTEGER = "整"; 
    	    
    	    //check illegal format of digit number
    	    RE reg_check1 = new RE("[^,.\\d]");
    	    
    	    //match the llegal format of digit munber
    	    RE reg_check2 = new RE("^((\\d{1,3}(,\\d{3})*(.((\\d{3},)*\\d{1,3}))?)|(\\d+(.\\d+)?))$");
    	    
    		//变量
    	    String integral = "";	//圆，整数部分 
    	    String decimal = "";    //分角，小数部分
    	    String outputCharacters = "";    //转换后的结果
    	    String[] parts = new String[2];
    	    String[] digits, radices, bigRadices, decimals; 
    	    int  p;
    	    String d;
    	    int quotient, modulus; 
    	     
    		//首先校验输入值是否正确
    	    currencyDigits = currencyDigits.toString(); 
    	    if (currencyDigits.equals("")) { 
    	        logger.info("Empty input!"); 
    	        return ""; 
    	    } 
    	    if (reg_check1.match(currencyDigits)) { 
    	    	logger.info("Invalid characters in the input string!"); 
    	        return ""; 
    	    } 
    	    if (!reg_check2.match(currencyDigits)) { 
    	    	logger.info("Illegal format of digit number!"); 
    	        return ""; 
    	    } 
    	     
    		//Normalize the format of input digits: 
    	    currencyDigits = currencyDigits.replaceAll(",/g", "");    // Remove comma delimiters. 
    	    currencyDigits = currencyDigits.replaceAll("^0+", "");    // Trim zeros at the beginning. 
    	    // Assert the number is not greater than the maximum number. 
    	    if ((new Double(currencyDigits)).doubleValue() > MAXIMUM_NUMBER) { 
    	    	logger.info("Too large a number to convert!"); 
    	        return ""; 
    	    } 
    	     
    		// Process the coversion from currency digits to characters: 
    	    // Separate integral and decimal parts before processing coversion: 
    	    parts = currencyDigits.split("\\.");
    	    if (parts.length > 1) { 
    	        integral = parts[0]; 
    	        decimal = parts[1]; 
    	        // Cut down redundant decimal digits that are after the second. 
    	        decimal = decimal.substring(0, 2); 
    	    } 
    	    else { 
    	        integral = parts[0]; 
    	        decimal = ""; 
    	    } 
    	    // Prepare the characters corresponding to the digits: 
    	    digits = new String[]{CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE}; 
    	    radices = new String[]{"", CN_TEN, CN_HUNDRED, CN_THOUSAND}; 
    	    bigRadices = new String[]{"", CN_TEN_THOUSAND, CN_HUNDRED_MILLION}; 
    	    decimals = new String[]{CN_TEN_CENT, CN_CENT}; 
    	    // Start processing: 
    	    outputCharacters = ""; 
    	    // Process integral part if it is larger than 0: 
    	    if ((new Integer(integral)).intValue() > 0) { 
    	        int zeroCount = 0; 
    	        for (int i = 0; i < integral.length(); i++) { 
    	            p = integral.length() - i - 1;
    	            d = integral.substring(i, i+1);
    	            
    	            //locate the higher scalar of number in the digit
    	            quotient = p / 4;
    	            
    	            //locate the lower scalar of number in the digit
    	            modulus = p % 4; 
    	            if (d.equals("0")) {
    	                zeroCount++; 
    	            }
    	            else { 
    	                if (zeroCount > 0) 
    	                { 
    	                    outputCharacters += digits[0]; 
    	                } 
    	                zeroCount = 0; 
    	                outputCharacters += digits[(new Integer(d)).intValue()] + radices[modulus]; 
    	            } 
    	            if (modulus == 0 && zeroCount < 4) { 
    	                outputCharacters += bigRadices[quotient]; 
    	            } 
    	        } 
    	        outputCharacters += CN_DOLLAR; 
    	    } 
    	    // Process decimal part if there is: 
    	    if (!decimal.equals("")) { 
    	        for (int i = 0; i < decimal.length(); i++) { 
    	            d = decimal.substring(i, i+1); 
    	            if (!d.equals("0")) { 
    	                outputCharacters += digits[(new Integer(d)).intValue()] + decimals[i]; 
    	            } 
    	        } 
    	    } 
    	    // Confirm and return the final output string: 
    	    if (outputCharacters.equals("")) { 
    	        outputCharacters = CN_ZERO + CN_DOLLAR; 
    	    } 
    	    if (decimal.equals("")) { 
    	        outputCharacters += CN_INTEGER; 
    	    } 
    	    return outputCharacters; 
    }
    
    /*
     * 用正则表达式判断手机号码
     * add by jzy 09-01-08
     */
    public boolean checkPhoneNumber(String num) 
    {
    	Pattern pattern = Pattern.compile("^(13[0-9]|15[0|3|6|8|9])\\d{8}$"); //      (13|15)\\d{9}

    	Matcher matcher = pattern.matcher(num);

    	return matcher.matches();
    }
    
	//15:59:59 == > 155959
	public static String timeToNum(String str)
	{
		String hour = str.substring(0, 2);
		String minute = str.substring(3, 5);
		String second = str.substring(6, 8);
		return hour+minute+second;
	}
	
	//返回日期所在月份的最后一天
	public static int getLastDayIfMonth(int year, int month, int day)
	{
		Calendar cDate = new GregorianCalendar();
		cDate.set(year, month, day); //设置年月日，日随便设一个就可以   
		return cDate.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	//06/Feb/2009 ==> 20090206
	public static String engMonthToNum(String str)
	{
		String date = str.substring(0, 2);
		String temp = str.substring(3, 6);
		String year = str.substring(7, 11);

		if (temp.equals("Jan"))
		{
			return year + "01" + date;
		}
		else if (temp.equals("Feb"))
		{
			return year + "02" + date;
		}
		else if (temp.equals("Mar"))
		{
			return year + "03" + date;
		}
		else if (temp.equals("Apr"))
		{
			return year + "04" + date;
		}
		else if (temp.equals("May"))
		{
			return year + "05" + date;
		}
		else if (temp.equals("Jun"))
		{
			return year + "06" + date;
		}
		else if (temp.equals("Jul"))
		{
			return year + "07" + date;
		}
		else if (temp.equals("Aug"))
		{
			return year + "08" + date;
		}
		else if (temp.equals("Sep"))
		{
			return year + "09" + date;
		}
		else if (temp.equals("Oct"))
		{
			return year + "10" + date;
		}
		else if (temp.equals("Nov"))
		{
			return year + "11" + date;
		}
		else if (temp.equals("Dec"))
		{
			return year + "12" + date;
		}

		return "00000000";
	}
	
    /**
     * 生成兴趣分类编码，编码起始值为1
     * @param likeCode
     * @return
     */
    public static int getLikeCode (int likeCode){
    	if(likeCode <= 0 ){
    		likeCode = 1;
    	}else{
    		likeCode = likeCode<<1;
    	}
    	return likeCode;
    }
    
    
    
    
    /**
     * 将前台传入的兴趣分类数组中包含的用户选择的兴趣分类
     * 转化成一个int值
     * @param interests 前台传入的用户选择的部落数组
     * @return
     */
    public static int toUserInterest(String[] interests)
    {
        int userInterest = 0;
        
        if(null == interests)
        {
            return userInterest;
        }
        
        for(int i = 0;i < interests.length; i++)
        {
        	userInterest = userInterest | Integer.parseInt(interests[i]);
        }
        return userInterest;
    }
    
    /**
     * 解析用户兴趣分类，即通过按位与运算匹配出结果编码所包含的兴趣分类
     * @param userInterests 用户所选兴趣分类结果编码
     * @return
     */
    public static List parseUserInterest(int userInterests){
    	//从缓存中获取基础的兴趣分类信息
    	List userInterestList = FrameworkApplication.getCacheObjects("userInterests");
    	ArrayList interests = null;
    	if(userInterestList!=null && userInterestList.size()>0){
    		interests = new ArrayList();
    		for(int i = 0 ; i < userInterestList.size(); i++){
    			
//    			MssLikeInfo mssLikeInfo = (MssLikeInfo)userInterestList.get(i);
    			
    			//获得兴趣分类编码
//    			int interest = mssLikeInfo.getLikeCode().intValue();
    			
    			//判断结果编码中是否包含
//    			if((userInterests & interest) == interest){
//    				interests.add(mssLikeInfo);
//    			}
    		}
    	}
    	return interests;
    }
    
    public static void main (String[] args) 
//    throws IllegalArgumentException, ClassNotFoundException, IllegalAccessException,InvocationTargetException, NoSuchMethodException
    {
//    	CommonOperation c = new CommonOperation();
//    	List list = new ArrayList();
//    	Test t = null;
//    	for(int i = 0;i<10; i++ ){
//    		t = new Test();
//    		t.setId(new Integer(c.createRandomNo(1)).intValue());
//    		t.setName(String.valueOf(i));
//    		list.add(t);
//    	}
//    	for(int j = 0 ; j<10;j++){
//    		System.out.println("id : " + ((Test)list.get(j)).getId()+" ; name : "+ ((Test)list.get(j)).getName());
//    	}
//			list = c.sortListByRecordId(list,"com.xwtech.pms.pub.tools.Test","getId");
//			System.out.println("--------------------------");
//    	for(int k = 0;k<10;k++){
//    		System.out.println("id : " + ((Test)list.get(k)).getId()+" ; name : "+ ((Test)list.get(k)).getName());
//        }
    	int a = 0;
    	int b = 0;
    	for(int i = 0 ; i<31 ; i++){
    		a = getLikeCode(a);
//    		b = b|a;
        	System.out.println(i+" a: "+a);
    	}
//    	int d = (int)(Math.pow(2, 80)+Math.pow(2, 8));
//    	int c = (int)(Math.pow(2, 31));
//    	System.out.println(" d: "+d);
//    	System.out.println(" c: "+c);
//    	System.out.println(" a: "+ (d & (int)Math.pow(2, 4)));
    }
	
}

//class Test {
//	
//	private int id;
//	
//	private String name;
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//	
//}
