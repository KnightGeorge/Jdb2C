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
					w.write("private "+propertyType2S(f.getType())+" "+f.getFileName()+";\r\n");
				}
				w.write("}");
				w.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public Class<?> propertyType(String type) {
		if(type.startsWith("varchar")) {
			return String.class;
		}else if(type.startsWith("int")) {
			return Integer.class;
		}else if(type.startsWith("date")) {
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
		
		throw new RuntimeException("no such type!");
	}

	public String propertyType2S(String type) {
		if(type.startsWith("varchar")) {
			return "String";
		}else if(type.startsWith("int")) {
			return "Integer";
		}else if(type.startsWith("date")) {
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
		
		throw new RuntimeException("no such type!");
	}
}
