package com.ninggc.template.springbootfastdemo.project.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ninggc.template.springbootfastdemo.project.domain.BdSmsRecord;
import com.ninggc.template.springbootfastdemo.project.domain.SMSRecordExample;

import java.util.List;

public interface SmsRecordMapper extends BaseMapper<BdSmsRecord> {
    int deleteByPrimaryKey(Integer id);

    int insert(BdSmsRecord record);

    int insertSelective(BdSmsRecord record);

    List<BdSmsRecord> selectByExampleWithBLOBs(SMSRecordExample example);

    List<BdSmsRecord> selectByExample(SMSRecordExample example);

    BdSmsRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BdSmsRecord record);

    int updateByPrimaryKeyWithBLOBs(BdSmsRecord record);

    int updateByPrimaryKey(BdSmsRecord record);
}