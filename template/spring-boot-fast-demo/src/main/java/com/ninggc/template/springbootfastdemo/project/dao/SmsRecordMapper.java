package com.ninggc.template.springbootfastdemo.project.dao;

import com.ninggc.template.springbootfastdemo.project.domain.BdSmsRecord;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;

@CacheNamespace
public interface SmsRecordMapper {
    @Select("select * from bd_sms_record where id = ${id}")
    BdSmsRecord selectById(Integer id);
    // int deleteByPrimaryKey(Integer id);
    //
    // int insert(BdSmsRecord record);
    //
    // int insertSelective(BdSmsRecord record);
    //
    // List<BdSmsRecord> selectByExampleWithBLOBs(SMSRecordExample example);
    //
    // List<BdSmsRecord> selectByExample(SMSRecordExample example);
    //
    // BdSmsRecord selectByPrimaryKey(Integer id);
    //
    // int updateByPrimaryKeySelective(BdSmsRecord record);
    //
    // int updateByPrimaryKeyWithBLOBs(BdSmsRecord record);
    //
    // int updateByPrimaryKey(BdSmsRecord record);
}