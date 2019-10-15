package com.ninggc.util.hibernatebase;

import com.ninggc.util.hibernatebase.page.Page;
import com.ninggc.util.hibernatebase.page.PageEnum;
import com.ninggc.util.hibernatebase.query.QueryCondition;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>  Created by LXL on 2017/9/25  </p>
 */
public class HibernateBaseImpl<T> implements HibernateBase<T> {

    private SessionFactory sessionFactory;

    // 导入的 T 的类的类型,通过构造类装载
    private Class<T> clazz;

    @SuppressWarnings("unchecked")
    public HibernateBaseImpl() {
        this.clazz = (Class<T>) ((ParameterizedType) super.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 获得Dao对于的实体类
     *
     * @return
     */
    public Class<T> getEntityClass() {
        return clazz;
    }

    @Resource // 通过 Spring 自动装配的 Session 工厂
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        Session session = sessionFactory.getCurrentSession();
        if(null==session || false==session.isOpen()){
            session = sessionFactory.openSession();
        }
        return session;
    }

    /**
     * Created by LXL on 2017-07-26
     * <br> 将某个主键为空的实体存入数据库,由于实体中主键的生成策略一般为整型自增,且主键唯一
     * <br> 因此当实体的主键存在时不建议使用此方法,而应该使用 update
     * @param po 需要持久化的实体类
     * @return 已经持久化的实体类, 且主键已自动分配
     */
    public T save(T po) {
        sessionFactory.getCurrentSession().save(po);
        return po;
    }

    /**
     * Created by LXL on 2017-07-26
     * <br> 对某个已经知道逐渐的实体进行修改时,通过此方法完成待修改实体的持久化操作
     * @param po 需要持久化的实体类
     * @return 已经持久化的实体类
     */
    public T update(T po) {
        sessionFactory.getCurrentSession().update(po);
        return po;
    }

    /**
     * Created by LXL on 2017-07-26
     * <br> 根据实体类的主键 id 获取实体类
     * @param id 实体类的主键 id
     * @return 对应 id 实体类
     */
    public T get(Serializable id) {
        return sessionFactory.getCurrentSession().get(clazz, id);
    }

    /**
     * Created by LXL on 2017-08-02
     * <br> 查询全部的记录
     * @return 全部记录
     */
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(clazz).list();
    }

    /**
     * Created by LXL on 2017-08-02
     * <br> 查询全部的记录,以分页方式返回,具体显示哪一页通过 pageNumber 传入,采用默认的页大小(每页10条记录)
     * @param pageNumber 需要查询第几页
     * @return 分页结果对象
     */
    public Page<T> findAll(int pageNumber) {
        return findAll(pageNumber, PageEnum.SIZE.value());
    }

    /**
     * Created by LXL on 2017-08-02
     * <br> 查询全部的记录,以分页方式返回,具体显示哪一页通过 pageNumber 传入,采用指定的页大小(每页 pageSize 条记录)
     * @param pageNumber 需要查询第几页
     * @param pageSize   每页包含多少条记录
     * @return 分页结果对象
     */
    @SuppressWarnings("unchecked")
    public Page<T> findAll(int pageNumber, int pageSize) {
        // 初始化返回页对象
        Page<T> page = new Page<T>();
        // 设置当前显示的页码
        page.setPageNumber(pageNumber);
        // 设置每页大小
        page.setPageSize(pageSize);
        // 创建一次查询
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
        // 设为查询记录总数的模式
        criteria.setProjection(Projections.rowCount());
        // 获取查询的记录总数结果
        int totalCount = ((Long) criteria.uniqueResult()).intValue();
        // 设置总条数
        page.setTotal(totalCount);
        // 设置总页数
        page.setTotalPage((int) Math.ceil((double) totalCount / page.getPageSize()));
        // 重置 Projection
        criteria.setProjection(null);
        // 设置查询结果为实体对象,而不是封装为Object数组,因为前边已经有一句:criteria.uniqueResult(),此句的查询返回类型为 Object
        criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        // 由于是分页查询,需要设置结果范围
        criteria.setFirstResult((pageNumber - 1) * page.getPageSize()).setMaxResults(page.getPageSize());
        page.setResult(criteria.list());
        return page;
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 使用查询对象 QueryCondition 进行 AND 查询
     * @param queryCondition 查询对象
     * @return 符合条件的查询结果
     */
    @SuppressWarnings("unchecked")
    public List<T> findByAND(QueryCondition queryCondition) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
        if (null != queryCondition) {
            criteria = queryCondition.andCriteria(criteria);
        }
        return criteria.list();
    }

    /**
     * Created by LXL on 2017-08-02
     * <br> 使用查询对象 QueryCondition 进行 AND 查询,具体显示哪一页通过 pageNumber 传入,采用默认的页大小(每页10条记录)
     * @param pageNumber     需要查询第几页
     * @param queryCondition 查询对象
     * @return 分页结果对象
     */
    public Page<T> findByAND(int pageNumber, QueryCondition queryCondition) {
        return findByAND(pageNumber, PageEnum.SIZE.value(), queryCondition);
    }

    /**
     * Created by LXL on 2017-08-02
     * <br> 使用查询对象 QueryCondition 进行 AND 查询,具体显示哪一页通过 pageNumber 传入,采用指定的页大小(每页 pageSize 条记录)
     * @param pageNumber     需要查询第几页
     * @param pageSize       每页包含多少条记录
     * @param queryCondition 查询对象
     * @return 分页结果对象
     */
    @SuppressWarnings("unchecked")
    public Page<T> findByAND(int pageNumber, int pageSize, QueryCondition queryCondition) {
        // 初始化返回页对象
        Page<T> page = new Page<T>();
        // 设置当前显示的页码
        page.setPageNumber(pageNumber);
        // 设置每页大小
        page.setPageSize(pageSize);
        // 创建一次查询
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
        // 设置查询条件
        if (null != queryCondition) {
            criteria = queryCondition.andCriteria(criteria);
        }
        // 设为查询记录总数的模式
        criteria.setProjection(Projections.rowCount());
        // 获取查询的记录总数结果
        int totalCount = ((Long) criteria.uniqueResult()).intValue();
        // 设置总条数
        page.setTotal(totalCount);
        // 设置总页数
        page.setTotalPage((int) Math.ceil((double) totalCount / page.getPageSize()));
        // 重置 Projection
        criteria.setProjection(null);
        // 设置查询结果为实体对象,而不是封装为Object数组,因为前边已经有一句:criteria.uniqueResult(),此句的查询返回类型为 Object
        criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        // 由于是分页查询,需要设置结果范围
        criteria.setFirstResult((pageNumber - 1) * page.getPageSize()).setMaxResults(page.getPageSize());
        page.setResult(criteria.list());
        return page;
    }

    /**
     * Created by LXL on 2017-08-02
     * <br> 使用查询对象 QueryCondition 进行 AND 的统计学方式查询
     * @param queryCondition 查询对象
     * @return 分页结果对象
     */
    @SuppressWarnings("unchecked")
    public Object findByANDWithStatistic(QueryCondition queryCondition) {
        // 创建一次查询
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
        criteria = queryCondition.andCriteria(criteria);
        criteria.setProjection(null);
        criteria.setProjection(queryCondition.projectionList());
        return criteria.uniqueResult();
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 使用查询对象 QueryCondition 进行 OR 查询
     * @param queryCondition 查询对象
     * @return 对应的查询结果
     */
    @SuppressWarnings("unchecked")
    public List<T> findByOR(QueryCondition queryCondition) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
        if (null != queryCondition) {
            criteria = queryCondition.orCriteria(criteria);
        }
        return criteria.list();
    }

    /**
     * Created by LXL on 2017-08-02
     * <br> 使用查询对象 QueryCondition 进行 OR 查询,具体显示哪一页通过 pageNumber 传入,采用默认的页大小(每页10条记录)
     * @param pageNumber     需要查询第几页
     * @param queryCondition 查询对象
     * @return 分页结果对象
     */
    public Page<T> findByOR(int pageNumber, QueryCondition queryCondition) {
        return findByOR(pageNumber, PageEnum.SIZE.value(), queryCondition);
    }

    /**
     * Created by LXL on 2017-08-02
     * <br> 使用查询对象 QueryCondition 进行 OR 查询,具体显示哪一页通过 pageNumber 传入,采用指定的页大小(每页 pageSize 条记录)
     * @param pageNumber     需要查询第几页
     * @param pageSize       每页包含多少条记录
     * @param queryCondition 查询对象
     * @return 分页结果对象
     */
    @SuppressWarnings("unchecked")
    public Page<T> findByOR(int pageNumber, int pageSize, QueryCondition queryCondition) {
        // 初始化返回页对象
        Page<T> page = new Page<T>();
        // 设置当前显示的页码
        page.setPageNumber(pageNumber);
        // 设置每页大小
        page.setPageSize(pageSize);
        // 创建一次查询
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
        // 设置查询条件
        if (null != queryCondition) {
            criteria = queryCondition.orCriteria(criteria);
        }
        // 设为查询记录总数的模式
        criteria.setProjection(Projections.rowCount());
        // 获取查询的记录总数结果
        int totalCount = ((Long) criteria.uniqueResult()).intValue();
        // 设置总条数
        page.setTotal(totalCount);
        // 设置总页数
        page.setTotalPage((int) Math.ceil((double) totalCount / page.getPageSize()));
        // 重置 Projection
        criteria.setProjection(null);
        // 设置查询结果为实体对象,而不是封装为Object数组,因为前边已经有一句:criteria.uniqueResult(),此句的查询返回类型为 Object
        criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        // 由于是分页查询,需要设置结果范围
        criteria.setFirstResult((pageNumber - 1) * page.getPageSize()).setMaxResults(page.getPageSize());
        page.setResult(criteria.list());
        return page;
    }

    /**
     * Created by LXL on 2017-08-02
     * <br> 使用查询对象 QueryCondition 进行 OR 的统计学方式查询
     * @param queryCondition 查询对象
     * @return 分页结果对象
     */
    @SuppressWarnings("unchecked")
    public Object findByORWithStatistic(QueryCondition queryCondition) {
        // 创建一次查询
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
        criteria = queryCondition.orCriteria(criteria);
        criteria.setProjection(null);
        criteria.setProjection(queryCondition.projectionList());
        return criteria.uniqueResult();
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 使用 HQL 查询方式进行查询,查询结果是列表
     * @param hql    HQL 查询语句,包含占位符,占位符由第二个参数 values 替换
     * @param values 占位符对应的变量值,即查询条件
     * @return 查询结果, 可能为空
     */
    @SuppressWarnings("unchecked")
    public Object findByHQL(String hql, Object... values) {
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.list();
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 使用 SQL 查询方式进行查询,查询结果是列表
     * @param sql SQL 查询语句
     * @return 查询结果, 可能为空
     */
    public Object findBySQL(String sql) {
        return sessionFactory.getCurrentSession().createNativeQuery(sql).list();
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 使用 SQL 查询方式进行查询,查询结果是列表
     * @param sql SQL 查询语句
     * @param values 占位符对应的变量值,即查询条件
     * @return 查询结果, 可能为空
     */
    public Object findBySQL(String sql, Object... values) {
        Query query = sessionFactory.getCurrentSession().createNativeQuery(sql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.list();
    }

    @SuppressWarnings("all")
    public List<Map<String, Object>> queryBySQL(String sql) {
        List<Map<String, Object>> result= new ArrayList<Map<String,Object>>();

        List<?> list=getSession().createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        Iterator<?> iterator = list.iterator();
        while (iterator.hasNext()) {
            try {
                Map<String,Object> map= (Map<String,Object> ) iterator.next();
                result.add(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void saveList(List<T> pos) {
        Session session = sessionFactory.getCurrentSession(); // 获取Session
        try {
            if (pos != null && pos.size() > 0) {
                T t = null;
                for (int i = 0; i < pos.size(); i++) {
                    t = (T) pos.get(i);
                    session.save(t);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback(); // 出错将回滚事物
        }
    }

    public void updateList(List<T> pos) {
        Session session = sessionFactory.getCurrentSession(); // 获取Session
        try {
            if (pos != null && pos.size() > 0) {
                T t = null;
                for (int i = 0; i < pos.size(); i++) {
                    t = (T) pos.get(i);
                    session.update(t);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback(); // 出错将回滚事物
        }
    }

    public void excuteCall(final String callSql) {
        Session session = sessionFactory.getCurrentSession();
        session.doWork(new Work() {
            public void execute(Connection conn) throws SQLException {
                ResultSet rs = null;
                CallableStatement call = conn.prepareCall(callSql);
                rs = call.executeQuery();
                call.close();
                rs.close();
            }
        });
    }


}
