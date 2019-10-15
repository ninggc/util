package com.ninggc.util.hibernatebase;

import com.ninggc.util.hibernatebase.page.Page;
import com.ninggc.util.hibernatebase.query.QueryCondition;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by LXL on 2017/9/20.
 * Hibernate BaseDao 实现类
 * @param <T> 实体类
 */
public interface HibernateBase<T> {

    /**
     * Created by LXL on 2017-07-26
     * <br> 将某个主键为空的实体存入数据库,由于实体中主键的生成策略一般为整型自增,且主键唯一
     * <br> 因此当实体的主键存在时不建议使用此方法,而应该使用 update
     * @param po 需要持久化的实体类
     * @return 已经持久化的实体类, 且主键已自动分配
     */
    T save(T po);

    /**
     * Created by LXL on 2017-07-26
     * <br> 对某个已经知道主键的实体进行修改时,通过此方法完成待修改实体的持久化操作
     * @param po 需要持久化的实体类
     * @return 已经持久化的实体类
     */
    T update(T po);

    /**
     * Created by LXL on 2017-07-26
     * <br> 根据实体类的主键 id 获取实体类
     * @param id 实体类的主键 id
     * @return 对应 id 实体类
     */
    T get(Serializable id);

    /**
     * Created by LXL on 2017-08-02
     * <br> 查询全部的记录
     * @return 全部记录
     */
    List<T> findAll();

    /**
     * Created by LXL on 2017-08-02
     * <br> 查询全部的记录,以分页方式返回,具体显示哪一页通过 pageNumber 传入,采用默认的页大小(每页10条记录)
     * @param pageNumber 需要查询第几页
     * @return 分页结果对象
     */
    Page<T> findAll(int pageNumber);

    /**
     * Created by LXL on 2017-08-02
     * <br> 查询全部的记录,以分页方式返回,具体显示哪一页通过 pageNumber 传入,采用指定的页大小(每页 pageSize 条记录)
     * @param pageNumber 需要查询第几页
     * @param pageSize   每页包含多少条记录
     * @return 分页结果对象
     */
    Page<T> findAll(int pageNumber, int pageSize);

    /**
     * Created by LXL on 2017-08-01
     * <br> 使用查询对象 QueryCondition 进行 AND 查询
     * @param queryCondition 查询对象
     * @return 符合条件的查询结果
     */
    List<T> findByAND(QueryCondition queryCondition);

    /**
     * Created by LXL on 2017-08-02
     * <br> 使用查询对象 QueryCondition 进行 AND 查询,具体显示哪一页通过 pageNumber 传入,采用默认的页大小(每页10条记录)
     * @param pageNumber     需要查询第几页
     * @param queryCondition 查询对象
     * @return 分页结果对象
     */
    Page<T> findByAND(int pageNumber, QueryCondition queryCondition);

    /**
     * Created by LXL on 2017-08-02
     * <br> 使用查询对象 QueryCondition 进行 AND 查询,具体显示哪一页通过 pageNumber 传入,采用指定的页大小(每页 pageSize 条记录)
     * @param pageNumber     需要查询第几页
     * @param pageSize       每页包含多少条记录
     * @param queryCondition 查询对象
     * @return 分页结果对象
     */
    Page<T> findByAND(int pageNumber, int pageSize, QueryCondition queryCondition);

    /**
     * Created by LXL on 2017-08-02
     * <br> 使用查询对象 QueryCondition 进行 AND 的统计学方式查询
     * @param queryCondition 查询对象
     * @return 分页结果对象
     */
    Object findByANDWithStatistic(QueryCondition queryCondition);

    /**
     * Created by LXL on 2017-08-01
     * <br> 使用查询对象 QueryCondition 进行 OR 查询
     * @param queryCondition 查询对象
     * @return 对应的查询结果
     */
    List<T> findByOR(QueryCondition queryCondition);

    /**
     * Created by LXL on 2017-08-02
     * <br> 使用查询对象 QueryCondition 进行 OR 查询,具体显示哪一页通过 pageNumber 传入,采用默认的页大小(每页10条记录)
     * @param pageNumber     需要查询第几页
     * @param queryCondition 查询对象
     * @return 分页结果对象
     */
    Page<T> findByOR(int pageNumber, QueryCondition queryCondition);

    /**
     * Created by LXL on 2017-08-02
     * <br> 使用查询对象 QueryCondition 进行 OR 查询,具体显示哪一页通过 pageNumber 传入,采用指定的页大小(每页 pageSize 条记录)
     * @param pageNumber     需要查询第几页
     * @param pageSize       每页包含多少条记录
     * @param queryCondition 查询对象
     * @return 分页结果对象
     */
    Page<T> findByOR(int pageNumber, int pageSize, QueryCondition queryCondition);

    /**
     * Created by LXL on 2017-08-02
     * <br> 使用查询对象 QueryCondition 进行 OR 的统计学方式查询
     * @param queryCondition 查询对象
     * @return 分页结果对象
     */
    Object findByORWithStatistic(QueryCondition queryCondition);

    /**
     * Created by LXL on 2017-08-01
     * <br> 使用 HQL 查询方式进行查询,查询结果是列表
     * @param hql    HQL 查询语句,包含占位符,占位符由第二个参数 values 替换
     * @param values 占位符对应的变量值,即查询条件
     * @return 查询结果, 可能为空
     */
    Object findByHQL(String hql, Object... values);

    /**
     * Created by LXL on 2017-08-01
     * <br> 使用 SQL 查询方式进行查询,查询结果是列表
     * @param sql SQL 查询语句
     * @return 查询结果, 可能为空
     */
    Object findBySQL(String sql);

    /**
     * Created by LXL on 2017-08-01
     * <br> 使用 SQL 查询方式进行查询,查询结果是列表
     * @param sql SQL 查询语句
     * @param values 占位符对应的变量值,即查询条件
     * @return 查询结果, 可能为空
     */
    Object findBySQL(String sql, Object... values);

    /**
     * <p>
     * Description: [按照标准sql查询]
     * </p>
     *
     * @author ZYY
     * @version $Revision$ 2017-05-27
     * @author (latest modification by $Author$)
     * @return List<Map<String,Object>>
     */
    List<Map<String,Object>> queryBySQL(String  sql);

    /**
     * 批量插入
     * @param pos
     */
    public void saveList(List<T> pos);

    /**
     * 批量更新
     * @param pos
     */
    public void updateList(List<T> pos);

    /**
     * jdbc执行sql
     * @param callSql
     */
    public void excuteCall(String callSql);


}
