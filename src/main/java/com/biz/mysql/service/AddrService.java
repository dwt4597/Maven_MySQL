package com.biz.mysql.service;

import java.util.*;

import javax.sql.*;

import org.apache.ibatis.mapping.*;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.*;
import org.apache.ibatis.transaction.jdbc.*;

import com.biz.mysql.dao.*;
import com.biz.mysql.db.*;
import com.biz.mysql.vo.*;

public class AddrService {
	SqlSessionFactory sqlSession;
	Scanner scan;
	
	private String otherDriver = "org.gjt.mm.mysql.Driver";
	private String mySqlDriver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/mydb?useSSL=false";
	private String user = "root";
	private String password = "!aa1234";
	
	public AddrService() {
		
		scan = new Scanner(System.in);
		
		// import java.util.Properties
		Properties props = new Properties();  //임시 vo역할을 한다
		
		//DRIVER라고하는 이름으로, mySqlDriver에 담긴 문자열을 vo로 만들고,
		//그걸 props에 추가하라
		props.put("DRIVER",otherDriver); 
		props.put("URL",url);
		props.put("USER",user);
		props.put("PASSWORD",password);
		
		//props에 DB연결 profile을 담아서 
		//DataSource에게 전달을 할 예정
		AddrDataFactory dataFactory = new AddrDataFactory();
		dataFactory.setProperties(props);
		
		// import javax.sql.DataSource
		// DB연결만 관리할 객체
		DataSource dataSource = dataFactory.getDataSource();
	
		//데이터를 변환(DB -> Java, Java -> DB)할 도구 설정
		//데이터를 자신의 방법으로 모아서 보내고, 받는 일을 수행한다.
		TransactionFactory transactionFactory 
				= new JdbcTransactionFactory();
		
		//import org.apache.ibatis.*
		Environment env = new Environment("addrEnv", 
				transactionFactory, dataSource);
		
		// import org.apache.ibatis.*
		// Dao 클래스와 MyBatis를 연결하는 일을 수행한다.
		Configuration config = new Configuration(env);
		config.addMapper(AddrDao.class);
		
		this.sqlSession 
			= new SqlSessionFactoryBuilder().build(config);
		
		
		
	}
	
	public void addrView() {
		//SqlSessionFactory가 만든 SqlSession 상품을 사용하겠다.
		SqlSession session = this.sqlSession.openSession();
		
		AddrDao addrDao = session.getMapper(AddrDao.class);
		
		List<AddrVO> addrList = addrDao.selectAll();
		
		for(AddrVO vo : addrList) {
			System.out.println(vo);
		}
	}
	
	public void findByName() {
		System.out.println("===========================");
		System.out.print("이름 >>");
		String strName = scan.nextLine();
		
		SqlSession session = this.sqlSession.openSession();
		AddrDao dao = session.getMapper(AddrDao.class);
		List<AddrVO> addrList = dao.findByName(strName);
		
		for(AddrVO vo : addrList) {
			System.out.println(vo);
		}
		
	} //findName End
	public int insert(AddrVO vo) {
		
		SqlSession session = this.sqlSession.openSession();
		AddrDao dao = session.getMapper(AddrDao.class);
		
		int ret = dao.insert(vo);
		// insert, update, delete를 실행 후에는
		// 반드시 commit, close 실행하라.
		session.commit();
		session.close();
		
		System.out.println(ret);
		return ret;
		
	}
}
