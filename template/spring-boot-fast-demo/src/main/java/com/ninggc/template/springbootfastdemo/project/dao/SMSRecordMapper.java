package com.ninggc.template.springbootfastdemo.project.dao;

import com.ninggc.template.springbootfastdemo.project.domain.SMSRecord;
import com.ninggc.template.springbootfastdemo.project.domain.SMSRecordExample;
import java.util.List;

public interface SMSRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SMSRecord record);

    int insertSelective(SMSRecord record);

    List<SMSRecord> selectByExampleWithBLOBs(SMSRecordExample example);

    List<SMSRecord> selectByExample(SMSRecordExample example);

    SMSRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SMSRecord record);

    int updateByPrimaryKeyWithBLOBs(SMSRecord record);

    int updateByPrimaryKey(SMSRecord record);
}