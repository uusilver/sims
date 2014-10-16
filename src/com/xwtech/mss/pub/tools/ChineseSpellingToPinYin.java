package com.xwtech.mss.pub.tools;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xwtech.framework.pub.web.BaseFrameworkApplication;

/**
 * 中文读取转换成拼音。
 * @author weizhen
 * create on 2008.12.2
 */
public class ChineseSpellingToPinYin {
	private static final Log log = LogFactory.getLog(ChineseSpellingToPinYin.class);
	
	/**
	 * @功能描述：把result字符串（汉字）转换为拼音。
	 * @param result
	 * @return
	 */
	public static String SpellingChinese(String result) {
		String temp = "";

		if (result.length() > 0) {
			char ch = result.charAt(0);
			if (ch >= 'a' && ch <= 'z')
				return "" + (char) (ch - 'a' + 'A');
			
			if (ch >= 'A' && ch <= 'Z')
				return "" + ch;
			
			if (ch >= '0' && ch <= '9')
				return "" + ch;
			
			temp = ChineseSpellingToPinYin.concatPinyinStringArray(PinyinHelper
					.toHanyuPinyinStringArray(ch));
			
			// 多音字取第一个拼音
			temp = temp.split("\r")[0];
			
			temp = temp.substring(0, 1).toUpperCase();// 取首字母，转换为大写
		}

		return temp;
	}

	/**
	 * @功能描述：得到汉字的所有的拼音，包括多音。
	 * @param pinyinArray
	 * @return
	 */
	public static String concatPinyinStringArray(String[] pinyinArray) {
		StringBuffer pinyinStrBuf = new StringBuffer();
		if ((null != pinyinArray) && (pinyinArray.length > 0)) {
			for (int i = 0; i < pinyinArray.length; i++) {
				pinyinStrBuf.append(pinyinArray[i]);
				pinyinStrBuf.append(System.getProperty("line.separator"));
			}
		}
		String outputString = pinyinStrBuf.toString();
		return outputString;
	}

	public static void main(String[] args) {
		System.out.println(ChineseSpellingToPinYin.SpellingChinese("苜"));
	}
	
	/**
	 * @功能描述：转换页面下拉框活动类型的显示文本
	 * @param text
	 * @param keyWords
	 * @return
	 */
	public String convertActivityType(String text, String[] keyWords) {
		if (text == null && text.length() == 0) {
			return text;
		}

		// 判断活动类型名称的首字符是否是特殊字符，首字符非中文，则取第2个字符
		int flg = 0;
		int len = keyWords.length;
		for (int i = 0; i < len; i++) {
			String str = keyWords[i];
			if (text.startsWith(str)) {
				flg = str.length();
				break;
			}
		}

		if (text.length() > flg) {
			text = ChineseSpellingToPinYin.SpellingChinese(text.substring(flg, flg + 1)) + " - " + text;
		}
		
		return text;
	}
	
	/**
	 * @功能描述：转换页面下拉框营业厅名称的显示文本
	 * @param text
	 * @param keyWords
	 * @return
	 */
	public String convertOrgName(String text, String[] keyWords) {
		if (text == null && text.length() == 0) {
			return text;
		}

		// 判断活动类型名称的首字符是否是特殊字符，首字符非中文，则取第2个字符
		int flg = 0;
		int len = keyWords.length;
		for (int i = 0; i < len; i++) {
			String str = keyWords[i];
			if (text.startsWith(str)) {
				flg = str.length();
				break;
			}
		}

		if (text.length() > flg) {
			text = ChineseSpellingToPinYin.SpellingChinese(text.substring(flg, flg + 1)) + " - " + text;
		}

		return text;
	}
	
	/**
	 * @功能描述：获取名字首字母（大写）
	 * @param text
	 * @param keyWords
	 * @return
	 */
	public String getFirstletterByName(String text) {
		if (text == null && text.length() == 0) {
			return text;
		}

		// 判断名称的首字符是否是特殊字符，首字符非中文，则取第2个字符
		int flg = 0;
		int len = text.length();
		for (int i = 0; i < len; i++) {
			String str = text.substring(i,i+1);
			char ch = str.charAt(0);
			if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') 
					|| (ch >= '0' && ch <= '9') || (ch >= '\u4e00' && ch <= '\u9fa5') || (ch >='\uF900' && ch <='\uFA2D')){
				flg = i;
				break;
			}

		}

		if (text.length() > flg) {
			text = ChineseSpellingToPinYin.SpellingChinese(text.substring(flg, flg + 1));
		}

		return text;
	}
	
	/**
	 * @功能描述：得到配置文件参数值
	 * @param path
	 * @return
	 */
	public String getPropValue(String path) {
		Properties objectProps = new Properties();
		final String dir = BaseFrameworkApplication.FrameworkWebAppRootPath;
		
        //加载对象映射
        try {
			objectProps.load(new FileInputStream(dir + "/WEB-INF/keyWord.properties"));
		} catch (final FileNotFoundException e) {
			log.error("没有找到配置文件：" + e);
		} catch (final IOException e) {
			log.error(e);
		}

        return objectProps.getProperty(path);
	}
	
	
}
