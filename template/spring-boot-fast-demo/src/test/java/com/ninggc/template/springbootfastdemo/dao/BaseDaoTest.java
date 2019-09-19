package com.ninggc.template.springbootfastdemo.dao;

import com.mongodb.MongoException;
import com.ninggc.template.springbootfastdemo.AbstractBaseTest;
import com.ninggc.template.springbootfastdemo.entity.BaseEntity;
import com.ninggc.util.morphia.dao.base.impl.MorphiaQueryCondition;
import com.ninggc.util.morphia.util.DeleteEnum;
import com.ninggc.util.morphia.util.MatchMode;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class BaseDaoTest extends AbstractBaseTest {

    @Autowired
    private DaoFactory.MorphiaBaseDAO baseDAO;

    @Override
    public void before() {
        super.before();
    }

    @Override
    public void after() {
        System.out.println("end");
    }

    @Test
    public void save() {
        BaseEntity po = new BaseEntity();
        BaseEntity save = baseDAO.save(po);
    }

    @Test
    public void update() {
        BaseEntity po = new BaseEntity();
        po.setId(new ObjectId());
        BaseEntity update = baseDAO.update(po);
    }

    @Test
    public void get() {
        BaseEntity po = new BaseEntity();
        BaseEntity save = baseDAO.save(po);
        BaseEntity qsAnswerPO = baseDAO.get(save.getId());
    }

    @Test
    public void findAll() {
        List<BaseEntity> all = baseDAO.findAll();
    }

    @Test
    public void findAll1() {
        List<BaseEntity> all = baseDAO.findAll(10);
    }

    @Test
    public void findAll2() {
        List<BaseEntity> all = baseDAO.findAll(10, 3);
    }

    @Test
    public void findByAND() {
        MorphiaQueryCondition queryCondition = new MorphiaQueryCondition();
        String key = "6ed98792-95be-428a-a507-1728f723b786";
        String content = "2945SDXZQ";
//        String content = "1";
        queryCondition.addLikeKeyContent(key, content, MatchMode.EXACT);
//        queryCondition.addEq("question_id", 5);
        List<BaseEntity> byAND = baseDAO.findByAND(queryCondition);
    }

    @Test
    public void findByAND1() {
        MorphiaQueryCondition morphiaQueryCondition = new MorphiaQueryCondition();
        morphiaQueryCondition.addEq("patientId", Long.parseLong("4161"));
        morphiaQueryCondition.addEq("taskId", 150);
        morphiaQueryCondition.addEq("questionId", 5);
        morphiaQueryCondition.addEq("deleteFlag", DeleteEnum.NOT);
//        morphiaQueryCondition.addOrder("createDate", QueryOrderOptions.DESC);
        List l = baseDAO.findByAND(morphiaQueryCondition);

    }

    @Test
    public void findByAND2() {
        MorphiaQueryCondition queryCondition = new MorphiaQueryCondition();
//        queryCondition.addEq("deleteFlag", DeleteEnum.NOT);
        List<BaseEntity> byAND = baseDAO.findByAND(1, 5, queryCondition);
    }

    @Test
    public void findByANDWithStatistic() {
    }

    @Test
    public void findByOR() {
        MorphiaQueryCondition queryCondition = new MorphiaQueryCondition();
        String key = "c5928a80-a83d-41c0-8701-aa636bcc654a";
        String content = "310101199908080012";
        queryCondition.addLikeKeyContent(key, content, MatchMode.EXACT);
        queryCondition.addEq("question_id", 5);
        List<BaseEntity> byOR = baseDAO.findByOR(queryCondition);
    }

    @Test
    public void findByOR1() {
    }

    @Test
    public void findByOR2() {
        MorphiaQueryCondition queryCondition = new MorphiaQueryCondition();
        queryCondition.addEq("deleteFlag", DeleteEnum.NOT.value());
        List<BaseEntity> byAND = baseDAO.findByOR(1, queryCondition);
    }

    @Test
    public void findByORWithStatistic() {
    }

    @Test
    public void saveList() {
        ArrayList<BaseEntity> pos = new ArrayList<>();
        BaseEntity po = new BaseEntity();
        po.setId(new ObjectId());
        pos.add(po);

        try {
            baseDAO.saveList(pos);
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
}