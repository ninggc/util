package com.ninggc.util.hibernatebase.query;

import org.hibernate.Criteria;
import org.hibernate.criterion.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>  Created by LXL on 2017-08-01  </p>
 */
public class QueryCondition {

    /**
     * 比较查询组
     * =    等于
     * <>   不等于
     * >    大于
     * >=   大于等于
     * <    小于
     * <=   小于等于
     */
    private Map<String, Object> eq; // =  等于
    private Map<String, Object> ne; // <> 不等于
    private Map<String, Object> gt; // >  大于
    private Map<String, Object> ge; // >= 大于等于
    private Map<String, Object> lt; // <  小于
    private Map<String, Object> le; // <= 小于等于

    /**
     * 模糊查询组
     * like    模糊查询
     * iLike   模糊查询(忽略大小写)
     */
    private Map<String, Object[]> like;  // like  模糊查询
    private Map<String, Object[]> iLike; // iLike 模糊查询(忽略大小写),i代表ignore

    /**
     * 匹配查询组
     * in      在所给参数值中的
     * notIn   不在所给参数值的
     * between 介于某两个值之间
     */
    private Map<String, Object[]> in;      // in      在所给参数值中的
    private Map<String, Object[]> notIn;   // notIn   不在所给参数值的
    private Map<String, Object[]> between; // between 介于某两个值之间

    /**
     * 判空查询组
     * <p>
     * isNull  是空的
     * notNull 不是空的
     */
    private List<String> isNull;  // isNull  是空的
    private List<String> notNull; // notNull 不是空的

    /**
     * 排序方式
     * 指定哪个字段按照哪种方式排序
     */
    private Map<String, String> order;

    /**
     * 投影、聚合、分组方式
     * 此类查询是针对某一列进行的,查询结果会在 HibernateBaseDaoImpl 的对应方法的 List 数组中
     * group       按指定列分组统计
     * max         获取指定列的最大值
     * min         获取指定列的最小值
     * avg         获取指定列的平均值
     * sum         获取指定列的累加和
     * count       获取查询到的记录个数
     * countUnique 获取去重后的记录个数
     */
    private List<String> group;       // 按指定列分组统计
    private List<String> max;         // 获取指定列的最大值
    private List<String> min;         // 获取指定列的最小值
    private List<String> avg;         // 获取指定列的平均值
    private List<String> sum;         // 获取指定列的累加和
    private List<String> count;       // 获取查询到的记录个数
    private List<String> countUnique; // 获取去重后的记录个数

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加等于的查询条件
     * <br> 例：void addEq("delete_flag",DeleteEnum.NOT.value())
     * <br> 注：查询 delete_flag = 0 的记录(即未被删除的记录)
     * @param columnName 指定的数据库字段名
     * @param param      查询的参数
     */
    public void addEq(String columnName, Object param) {
        if (eq == null) {
            Map<String, Object> eqCondition = new HashMap<String, Object>();
            eqCondition.put(columnName, param);
            eq = eqCondition;
        } else {
            eq.put(columnName, param);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加不等于的查询条件
     * <br> 例：void addNe("role","leader")
     * <br> 注：查询 role <> leader 的记录(即不是领导角色的记录)
     * @param columnName 指定的数据库字段名
     * @param param      查询的参数
     */
    public void addNe(String columnName, Object param) {
        if (ne == null) {
            Map<String, Object> neCondition = new HashMap<String, Object>();
            neCondition.put(columnName, param);
            ne = neCondition;
        } else {
            ne.put(columnName, param);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加大于的查询条件
     * <br> 例：void addGt("level",20)
     * <br> 注：查询 level > 20 的记录(即等级大于20的记录)
     * @param columnName 指定的数据库字段名
     * @param param      查询的参数
     */
    public void addGt(String columnName, Object param) {
        if (gt == null) {
            Map<String, Object> gtCondition = new HashMap<String, Object>();
            gtCondition.put(columnName, param);
            gt = gtCondition;
        } else {
            gt.put(columnName, param);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加大于等于的查询条件
     * <br> 例：void addGe("level",20)
     * <br> 注：查询 level >= 20 的记录(即等级大于等于20的记录)
     * @param columnName 指定的数据库字段名
     * @param param      查询的参数
     */
    public void addGe(String columnName, Object param) {
        if (ge == null) {
            Map<String, Object> geCondition = new HashMap<String, Object>();
            geCondition.put(columnName, param);
            ge = geCondition;
        } else {
            ge.put(columnName, param);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加小于的查询条件
     * <br> 例：void addLt("data_grade",5)
     * <br> 注：查询 data_grade < 5 的记录(即数据等级小于5的记录)
     * @param columnName 指定的数据库字段名
     * @param param      查询的参数
     */
    public void addLt(String columnName, Object param) {
        if (lt == null) {
            Map<String, Object> ltCondition = new HashMap<String, Object>();
            ltCondition.put(columnName, param);
            lt = ltCondition;
        } else {
            lt.put(columnName, param);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加小于等于的查询条件
     * <br> 例：void addLe("data_grade",5)
     * <br> 注：查询 data_grade <= 5 的记录(即数据等级小于等于5的记录)
     * @param columnName 指定的数据库字段名
     * @param param      查询的参数
     */
    public void addLe(String columnName, Object param) {
        if (le == null) {
            Map<String, Object> leCondition = new HashMap<String, Object>();
            leCondition.put(columnName, param);
            le = leCondition;
        } else {
            le.put(columnName, param);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加模糊查询条件
     * <br> 例：void addLike("user_name","张",MatchMode.ANYWHERE)
     * <br> 注：查询 user_name 字段中姓 [张] 的记录
     * @param columnName 指定的数据库字段名
     * @param param      查询的参数
     * @param matchMode  匹配模式
     */
    public void addLike(String columnName, String param, MatchMode matchMode) {
        Object[] likeParam = new Object[]{param, matchMode};
        if (like == null) {
            Map<String, Object[]> likeCondition = new HashMap<String, Object[]>();
            likeCondition.put(columnName, likeParam);
            like = likeCondition;
        } else {
            like.put(columnName, likeParam);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加模糊查询条件(忽略大小写)
     * <br> 例：void addILike("user_name","abc",MatchMode.ANYWHERE)
     * <br> 注：查询 user_name 字段中含有 [abc] 的记录,由于忽略大小写,可能查出如 efaBC,Abcde,ABC 的记录
     * @param columnName 指定的数据库字段名
     * @param param      查询的参数
     * @param matchMode  匹配模式
     */
    public void addILike(String columnName, String param, MatchMode matchMode) {
        Object[] iLikeParam = new Object[]{param, matchMode};
        if (iLike == null) {
            Map<String, Object[]> iLikeCondition = new HashMap<String, Object[]>();
            iLikeCondition.put(columnName, iLikeParam);
            iLike = iLikeCondition;
        } else {
            iLike.put(columnName, iLikeParam);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加 in 的匹配查询条件,查询在参数 param 中的记录
     * <br> 例：void addIn("user_name",new Object[]{"mike","lily","jake"})
     * <br> 注：查询 user_name 字段属于 "mike","lily","jake" 这三个人的记录
     * @param columnName 指定的数据库字段名
     * @param param      查询的参数
     */
    public void addIn(String columnName, Object[] param) {
        if (in == null) {
            Map<String, Object[]> inCondition = new HashMap<String, Object[]>();
            inCondition.put(columnName, param);
            in = inCondition;
        } else {
            in.put(columnName, param);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加 notIn 的匹配查询条件,查询不在参数 param 中的记录
     * <br> 例：void addNotIn("user_name",new Object[]{"mike","lily","jake"})
     * <br> 注：查询 user_name 字段不属于 "mike","lily","jake" 这三个人的记录
     * @param columnName 指定的数据库字段名
     * @param param      查询的参数
     */
    public void addNotIn(String columnName, Object[] param) {
        if (notIn == null) {
            Map<String, Object[]> notInCondition = new HashMap<String, Object[]>();
            notInCondition.put(columnName, param);
            notIn = notInCondition;
        } else {
            notIn.put(columnName, param);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加 between 的匹配查询条件,查询在 min 和 max 区间范围的记录
     * <br> 例：void addBetween("user_age",15,25)
     * <br> 注：查询 use_age 介于 15 至 25 之间的记录(即用户年龄介于15至25岁之间的)
     * @param columnName 指定的数据库字段名
     * @param min        最小值
     * @param max        最大值
     */
    public void addBetween(String columnName, Object min, Object max) {
        if (between == null) {
            Map<String, Object[]> betweenCondition = new HashMap<String, Object[]>();
            Object[] param = new Object[]{min, max};
            betweenCondition.put(columnName, param);
            between = betweenCondition;
        } else {
            Object[] param = new Object[]{min, max};
            between.put(columnName, param);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加字段为空的查询条件,即查询指定字段是空的记录
     * <br> 例：void addIsNull("user_address")
     * <br> 注：查询 user_address 字段为空的记录(即尚未填写地址的用户)
     * @param columnName 指定的数据库字段名
     */
    public void addIsNull(String columnName) {
        if (isNull == null) {
            List<String> isNullCondition = new ArrayList<String>();
            isNullCondition.add(columnName);
            isNull = isNullCondition;
        } else {
            isNull.add(columnName);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加字段不为空的查询条件,即查询指定字段不是空的记录
     * <br> 例：void addNotNull("user_phone")
     * <br> 注：查询 user_phone 字段不为空的记录(即已经填写手机号的用户)
     * @param columnName 指定的数据库字段名
     */
    public void addNotNull(String columnName) {
        if (notNull == null) {
            List<String> notNullCondition = new ArrayList<String>();
            notNullCondition.add(columnName);
            notNull = notNullCondition;
        } else {
            notNull.add(columnName);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加指定字段的排序方式
     * <br> 例：void addOrder("student_score",QueryOrderOptions.ASC)
     * <br> 注：将 user_score 按照升序排列(即将学生成绩从低到高排序)
     * @param columnName        指定的数据库字段名
     * @param queryOrderOptions 枚举类型的排序方式
     */
    public void addOrder(String columnName, QueryOrderOptions queryOrderOptions) {
        if (order == null) {
            Map<String, String> orderCondition = new HashMap<String, String>();
            orderCondition.put(columnName, queryOrderOptions.order());
            order = orderCondition;
        } else {
            order.put(columnName, queryOrderOptions.order());
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加指定字段的分组依据
     * <br> 例：void addGroup("student_class")
     * <br> 注：按照 student_class 进行分组(即将学生按照班级进行分组)
     * @param columnName 指定的数据库字段名
     */
    public void addGroup(String columnName) {
        if (group == null) {
            List<String> groupCondition = new ArrayList<String>();
            groupCondition.add(columnName);
            group = groupCondition;
        } else {
            group.add(columnName);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加获取指定字段的最大值
     * <br> 例：void addMax("student_score")
     * <br> 注：获取 student_score 列的最大值(即获取学生的最高分)
     * @param columnName 指定的数据库字段名
     */
    public void addMax(String columnName) {
        if (max == null) {
            List<String> maxCondition = new ArrayList<String>();
            maxCondition.add(columnName);
            max = maxCondition;
        } else {
            max.add(columnName);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加获取指定字段的最小值
     * <br> 例：void addMin("student_score")
     * <br> 注：获取 student_score 列的最小值(即获取学生的最低分)
     * @param columnName 指定的数据库字段名
     */
    public void addMin(String columnName) {
        if (min == null) {
            List<String> minCondition = new ArrayList<String>();
            minCondition.add(columnName);
            min = minCondition;
        } else {
            min.add(columnName);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加获取指定字段的平均值
     * <br> 例：void addAvg("student_score")
     * <br> 注：获取 student_score 列的平均值(即获取学生的平均分)
     * @param columnName 指定的数据库字段名
     */
    public void addAvg(String columnName) {
        if (avg == null) {
            List<String> avgCondition = new ArrayList<String>();
            avgCondition.add(columnName);
            avg = avgCondition;
        } else {
            avg.add(columnName);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加获取指定字段的累加和
     * <br> 例：void addSum("student_donate")
     * <br> 注：获取 student_donate 列的累加和(即获取全部学生的捐款总额)
     * @param columnName 指定的数据库字段名
     */
    public void addSum(String columnName) {
        if (sum == null) {
            List<String> sumCondition = new ArrayList<String>();
            sumCondition.add(columnName);
            sum = sumCondition;
        } else {
            sum.add(columnName);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加获取指定字段的记录条数,全部记录,即使重复的也算在内
     * <br> 例：void addCount("student_name")
     * <br> 注：获取 student_name 列的记录条数(即获取学生总人数,全部人数,即使重名的也算在内)
     * @param columnName 指定的数据库字段名
     */
    public void addCount(String columnName) {
        if (count == null) {
            List<String> countCondition = new ArrayList<String>();
            countCondition.add(columnName);
            count = countCondition;
        } else {
            count.add(columnName);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 添加获取指定字段的记录条数,去重后的记录,重复的不算在内
     * <br> 例：void addCount("student_name")
     * <br> 注：获取 student_name 列的记录条数(即获取学生总人数,不同名的人数)
     * @param columnName 指定的数据库字段名
     */
    public void setCountUnique(String columnName) {
        if (countUnique == null) {
            List<String> countUniqueCondition = new ArrayList<String>();
            countUniqueCondition.add(columnName);
            countUnique = countUniqueCondition;
        } else {
            countUnique.add(columnName);
        }
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 构造 AND 查询条件,将传入的查询条件赋予内容
     * @param criteria 初始化的查询条件
     * @return 赋予内容的查询条件
     */
    public Criteria andCriteria(Criteria criteria) {
        // 获取查询条件
        List<Criterion> criterionList = getCriterions();
        // 构造 AND 查询条件,默认添加即为 AND 查询
        if (null != criterionList && criterionList.size() > 0) {
            for (Criterion criterion : criterionList) {
                criteria.add(criterion);
            }
        }
        // 添加排序方式后返回
        return getCriteriaOrder(criteria);
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 构造 OR 查询条件,将传入的查询条件赋予内容
     * @param criteria 初始化的查询条件
     * @return 赋予内容的查询条件
     */
    public Criteria orCriteria(Criteria criteria) {
        // 获取查询条件
        List<Criterion> criterionList = getCriterions();
        // 或查询条件组装
        if (null != criterionList && criterionList.size() > 0) {
            if (criterionList.size() < 2) {
                criteria.add(Restrictions.or(criterionList.get(0)));
            } else {
                LogicalExpression logicalExpression = Restrictions.or(criterionList.get(0), criterionList.get(1));
                for (int i = 2; i < criterionList.size(); i++) {
                    logicalExpression = Restrictions.or(logicalExpression, criterionList.get(i));
                }
                criteria.add(logicalExpression);
            }
        }
        // 添加排序方式后返回
        return getCriteriaOrder(criteria);
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 构造投影、聚合、分组条件
     * @return Hibernate 支持的投影运算条件列表
     */
    public ProjectionList projectionList() {
        // 初始化投影运算条件
        ProjectionList projectionList = Projections.projectionList();
        // 设置按指定列分组统计条件
        if (null != group && group.size() > 0) {
            for (String columnName : group) {
                projectionList.add(Projections.groupProperty(columnName));
            }
        }
        // 设置求指定列的最大值的条件
        if (null != max && max.size() > 0) {
            for (String columnName : max) {
                projectionList.add(Projections.max(columnName));
            }
        }
        // 设置求指定列的最小值的条件
        if (null != min && min.size() > 0) {
            for (String columnName : min) {
                projectionList.add(Projections.min(columnName));
            }
        }
        // 设置求指定列的平均值的条件
        if (null != avg && avg.size() > 0) {
            for (String columnName : avg) {
                projectionList.add(Projections.avg(columnName));
            }
        }
        // 设置求指定列的累加和的条件
        if (null != sum && sum.size() > 0) {
            for (String columnName : sum) {
                projectionList.add(Projections.sum(columnName));
            }
        }
        // 设置求查询到的记录个数的条件
        if (null != count && count.size() > 0) {
            for (String columnName : count) {
                projectionList.add(Projections.count(columnName));
            }
        }
        // 设置求查询到的并且去重的记录个数的条件
        if (null != countUnique && countUnique.size() > 0) {
            for (String columnName : countUnique) {
                projectionList.add(Projections.countDistinct(columnName));
            }
        }
        return projectionList;
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 将查询条件转换成 Criterion 类型,此类型是 Hibernate 对一个查询条件的封装格式
     * @return 全部 Criterion 格式条件的数组
     */
    private List<Criterion> getCriterions() {
        // 设置返回数组
        List<Criterion> criterionList = new ArrayList<Criterion>();

        // 设置等于条件
        if (null != eq) {
            for (Map.Entry<String, Object> query : eq.entrySet()) {
                String columnName = query.getKey();
                Object param = query.getValue();
                Criterion criterion = Restrictions.eq(columnName, param);
                criterionList.add(criterion);
            }
        }
        // 设置不等于条件
        if (null != ne) {
            for (Map.Entry<String, Object> query : ne.entrySet()) {
                String columnName = query.getKey();
                Object param = query.getValue();
                Criterion criterion = Restrictions.ne(columnName, param);
                criterionList.add(criterion);
            }
        }
        // 设置大于条件
        if (null != gt) {
            for (Map.Entry<String, Object> query : gt.entrySet()) {
                String columnName = query.getKey();
                Object param = query.getValue();
                Criterion criterion = Restrictions.gt(columnName, param);
                criterionList.add(criterion);
            }
        }
        // 设置大于等于条件
        if (null != ge) {
            for (Map.Entry<String, Object> query : ge.entrySet()) {
                String columnName = query.getKey();
                Object param = query.getValue();
                Criterion criterion = Restrictions.ge(columnName, param);
                criterionList.add(criterion);
            }
        }
        // 设置小于条件
        if (null != lt) {
            for (Map.Entry<String, Object> query : lt.entrySet()) {
                String columnName = query.getKey();
                Object param = query.getValue();
                Criterion criterion = Restrictions.lt(columnName, param);
                criterionList.add(criterion);
            }
        }
        // 设置小于等于条件
        if (null != le) {
            for (Map.Entry<String, Object> query : le.entrySet()) {
                String columnName = query.getKey();
                Object param = query.getValue();
                Criterion criterion = Restrictions.le(columnName, param);
                criterionList.add(criterion);
            }
        }
        // 设置模糊查询条件
        if (null != like) {
            for (Map.Entry<String, Object[]> query : like.entrySet()) {
                String columnName = query.getKey();
                Object[] param = query.getValue();
                Criterion criterion = Restrictions.like(columnName,  param[0]+"", (MatchMode) param[1]);
                criterionList.add(criterion);
            }
        }
        // 设置忽略大小写的模糊查询条件
        if (null != iLike) {
            for (Map.Entry<String, Object[]> query : iLike.entrySet()) {
                String columnName = query.getKey();
                Object[] param = query.getValue();
                Criterion criterion = Restrictions.ilike(columnName, param[0] + "", (MatchMode) param[1]);
                criterionList.add(criterion);
            }
        }
        // 设置在所给参数值中的查询条件
        if (null != in) {
            for (Map.Entry<String, Object[]> entry : in.entrySet()) {
                String columnName = entry.getKey();
                Object[] params = entry.getValue();
                Criterion criterion = Restrictions.in(columnName, params);
                criterionList.add(criterion);
            }
        }

        // 设置不在所给参数值的的查询条件,即对 in 查询条件取反
        if (null != notIn) {
            for (Map.Entry<String, Object[]> entry : notIn.entrySet()) {
                String columnName = entry.getKey();
                Object[] params = entry.getValue();
                Criterion criterion = Restrictions.not(Restrictions.in(columnName, params));
                criterionList.add(criterion);
            }
        }
        // 设置介于某两个值之间的查询条件
        if (null != between) {
            for (Map.Entry<String, Object[]> entry : between.entrySet()) {
                String columnName = entry.getKey();
                Object[] params = entry.getValue();
                Criterion criterion = Restrictions.between(columnName, params[0], params[1]);
                criterionList.add(criterion);
            }
        }
        // 设置字段为空的查询条件
        if (null != isNull && isNull.size() > 0) {
            for (String columnName : isNull) {
                Criterion criterion = Restrictions.isNull(columnName);
                criterionList.add(criterion);
            }
        }
        // 设置字段不为空的查询条件
        if (null != notNull && notNull.size() > 0) {
            for (String columnName : notNull) {
                Criterion criterion = Restrictions.isNotNull(columnName);
                criterionList.add(criterion);
            }
        }
        return criterionList;
    }

    /**
     * Created by LXL on 2017-08-01
     * <br> 设置字段的排序条件
     * @param criteria 初始化的查询条件
     * @return 赋予内容的查询条件
     */
    private Criteria getCriteriaOrder(Criteria criteria) {
        if (null != order) {
            for (Map.Entry<String, String> entry : order.entrySet()) {
                String columnName = entry.getKey();
                String sort = entry.getValue();
                if ("desc".equals(sort)) {
                    criteria.addOrder(Order.desc(columnName));
                } else {
                    criteria.addOrder(Order.asc(columnName));

                }
            }
        }
        return criteria;
    }
}
