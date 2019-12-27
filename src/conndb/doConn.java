package conndb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yaml.snakeyaml.Yaml;

import table.fontTable;

public class doConn {
	private Connection conn;
	private ResultSet rs;
	private PreparedStatement pstmt;
	private connDb cnDb = new connDb();

	public doConn() {
		_doConn();
	}

	@SuppressWarnings("unchecked")
	private connDb init() {
		Yaml yaml = new Yaml();
		Map<String, String> map = new HashMap<String, String>();
		File file = new File(System.getProperty("user.dir")+"\\datasource.yaml");
		
		if (file != null) {
			try {
				map = (Map<String, String>) yaml.load(new FileInputStream(file));
				// System.out.println(map);
				for (String s : map.keySet()) {
					switch (s) {
					case "driver":
						cnDb.setDrive(map.get(s));
						break;
					case "url":
						cnDb.setUrl(map.get(s));
						break;
					case "username":
						cnDb.setUsername(map.get(s));
						break;
					case "password":
						cnDb.setPassword(map.get(s));
						break;

					default:
						System.err.println("no such data or bad file!");
						break;
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else System.err.println("Can not get file!");

		// System.out.println(cnDb.getUsername());
		return cnDb;
	}

	private Connection _doConn() {
		connDb cndb = new connDb();
		cndb = init();
		try {
			Class.forName(cndb.getDrive());
			conn = DriverManager.getConnection(cndb.getUrl(), cndb.getUsername(), cndb.getPassword());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public List<String> getTables() {
		String getUrl = cnDb.getUrl();
		String pattern = "[a-zA-Z0-9_]*db$";
		String database = null;
		List<String> list = new ArrayList<String>();

		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(getUrl);

		if (m.find()) {
			database = m.group();
			// System.out.println(database);
		} else {
			System.err.println("no such database!");
		}

		String sql = "select table_name from information_schema.tables where table_schema=? and table_type='base table'";
		Object[] obj = { database };
		try {
			pstmt = _doConn().prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					pstmt.setObject(i + 1, obj[i]);
				}
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("table_name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<fontTable> table(String tablename) {
		List<fontTable> fList = new ArrayList<fontTable>();

		String sql = "show full columns from " + tablename;
		try {
			pstmt = _doConn().prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				fontTable ft = new fontTable();
				ft.setFileName(rs.getString("field"));
				ft.setType(rs.getString("type"));
				fList.add(ft);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fList;
	}

	/*
	 * public List<List<fontTable>> tablesList() { List<List<fontTable>> list=new
	 * ArrayList<>(); for (String s:getTables()) { list.add(table(s)); } return
	 * list; }
	 */
}
