package com.biz.mysql.dao;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.biz.mysql.db.*;
import com.biz.mysql.vo.*;

public interface AddrDao {
	
	@Select(AddrSQL.ADDR_SELECT_ALL)
	public List<AddrVO> selectAll();
	
	@Select(AddrSQL.ADDR_FIND_BY_NAME)
	public List<AddrVO> findByName(String name);
	
	@Insert(AddrSQL.ADDR_INSERT)
	public int insert(AddrVO vo);
	
	@Update(AddrSQL.ADDR_UPDATE)
	public int update(AddrVO vo);
	
	
	public int delete(long id);

}
