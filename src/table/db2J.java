package table;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Date;


import conndb.doConn;

public class db2J {
	private doConn dn=new doConn();
	
	public void createDB2Class() {
		Writer w;
		for (String s : dn.getTables()) {
			String fileName=s;
			File file=new File("src/pojo/"+s+".java");
			try {
				w=new FileWriter(file);
				w.write("package pojo;\r\n");
				w.write("public class "+s+"{\r\n");
				for (fontTable f : dn.table(s)) {
					String type=propertyType2S(f.getType());
					String fName=f.getFileName();
					w.write("	private "+type+" "+fName+";\r\n");
					w.write("	public "+type+" get"+upFirstChar(fName)+"() {\r\n");
					w.write("		return "+fName+";\r\n");
					w.write("	}\r\n");
					w.write("	public void set"+fName.substring(0, 1).toUpperCase()+"("+type+" "+fName+") {\r\n");
					w.write("		this."+fName+" = "+fName+";\r\n");
					w.write("	}\r\n");
				}
				w.write("}");
				w.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	private Class<?> propertyType(String type) {
		if(type.indexOf("char")>-1) {
			return String.class;
		}else if(type.indexOf("blob")>-1) {
			return String.class;
		}else if(type.indexOf("text")>-1) {
			return Integer.class;
		}else if(type.indexOf("int")>-1) {
			return Integer.class;
		}else if(type.startsWith("date")) {
			return Date.class;
		}else if(type.startsWith("time")) {
			return Date.class;
		}else if(type.startsWith("year")) {
			return Date.class;
		}else if(type.startsWith("boolean")) {
			return Boolean.class;
		}else if(type.startsWith("float")) {
			return float.class;
		}else if(type.startsWith("double")) {
			return double.class;
		}else if(type.startsWith("decimal")) {
			return double.class;
		}
		
		throw new RuntimeException("no such type:"+type);
	}

	private String propertyType2S(String type) {
		if(type.indexOf("char")>-1) {
			return "String";
		}else if(type.indexOf("blob")>-1) {
			return "String";
		}else if(type.indexOf("text")>-1) {
			return "Integer";
		}else if(type.indexOf("int")>-1) {
			return "Integer";
		}else if(type.startsWith("date")) {
			return "Date";
		}else if(type.startsWith("time")) {
			return "Date";
		}else if(type.startsWith("year")) {
			return "Date";
		}else if(type.startsWith("boolean")) {
			return "Boolean";
		}else if(type.startsWith("float")) {
			return "float";
		}else if(type.startsWith("double")) {
			return "double";
		}else if(type.startsWith("decimal")) {
			return "double";
		}
		
		throw new RuntimeException("no such type:"+type);
	}
	
	private String upFirstChar(String text) {
		char[] cs=text.toCharArray();
		cs[0]-=32;
		return String.valueOf(cs);
	}
}
