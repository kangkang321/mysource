package com.neusoft.hp.mybatis.plugin.xmlgenerate.util;

import java.io.File;
import java.util.Locale;

public class StringUtil {

    private StringUtil(){

    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static boolean isNotEmpty(String value) {
        return value != null && value.trim().length() > 0;
    }

    public static boolean isNotBlank(String value) {
        return value != null && value.trim().length() > 0;
    }

    public static String packagePathToFilePath(String packagepath) {
        String result = null;
        if (packagepath != null) {
            result = packagepath.replace(".", File.separator);
        }
        return result;
    }
    
    public static String srcPackagePathToFilePath(String packagepath) {
        String result = null;
        if (packagepath != null) {
        	int i = packagepath.indexOf("src");
        	if(i != -1){
        		result = packagepath.substring(0, i) + packagepath.substring(i, packagepath.length()).replace(".", File.separator);
        	}else{
        		result = packagepath;
        	}
            //result = packagepath.replace(".", File.separator);
        }
        return result;
    }

    public static String toUnicodeString(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                sb.append("\\u" + Integer.toHexString(c));
            }
        }
        return sb.toString();
    }
    
    /**
     * 
     * @param inputString
     * @param flag 传入标识，首字母大写传入：UPPER，首字母小写传入LOWER，其余返回原值
     * @return
     */
    public static String toUpperOrLowerInitials(String inputString,String flag){
    	if(inputString != null && !"".equals(inputString))
    	{
    		StringBuilder sb = new StringBuilder(inputString);
    		if("LOWER".equals(flag)){
    			sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
    			return sb.toString();
    		}else if("UPPER".equals(flag))
    		{
    			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
    			return sb.toString();
    		}else
    		{
    			return inputString;
    		}
    		
    	}else
    	{
    		return null;
    	}
    	
    }

    public static String getCamelCaseString(String inputString, boolean firstCharacterUppercase) {
        StringBuilder sb = new StringBuilder();

        boolean nextUpperCase = false;
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);

            switch (c) {
                case '_':
                case '-':
                case '@':
                case '$':
                case '#':
                case ' ':
                case '/':
                case '&':
                    if (sb.length() > 0) {
                        nextUpperCase = true;
                    }
                    break;

                default:
                    if (nextUpperCase) {
                        sb.append(Character.toUpperCase(c));
                        nextUpperCase = false;
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                    break;
            }
        }

        if (firstCharacterUppercase) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }

        return sb.toString();
    }

    public static String getValidPropertyName(String inputString) {
        String answer;

        if (inputString == null) {
            answer = null;
        } else if (inputString.length() < 2) {
            answer = inputString.toLowerCase(Locale.US);
        } else {
            if (Character.isUpperCase(inputString.charAt(0)) && !Character.isUpperCase(inputString.charAt(1))) {
                answer = inputString.substring(0, 1).toLowerCase(Locale.US) + inputString.substring(1);
            } else {
                answer = inputString;
            }
        }

        return answer;
    }
}
