package com.sinog2c.model.commutationParole;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PrisonerPerformanceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PrisonerPerformanceExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andCriidIsNull() {
            addCriterion("CRIID is null");
            return (Criteria) this;
        }

        public Criteria andCriidIsNotNull() {
            addCriterion("CRIID is not null");
            return (Criteria) this;
        }

        public Criteria andCriidEqualTo(String value) {
            addCriterion("CRIID =", value, "criid");
            return (Criteria) this;
        }

        public Criteria andCriidNotEqualTo(String value) {
            addCriterion("CRIID <>", value, "criid");
            return (Criteria) this;
        }

        public Criteria andCriidGreaterThan(String value) {
            addCriterion("CRIID >", value, "criid");
            return (Criteria) this;
        }

        public Criteria andCriidGreaterThanOrEqualTo(String value) {
            addCriterion("CRIID >=", value, "criid");
            return (Criteria) this;
        }

        public Criteria andCriidLessThan(String value) {
            addCriterion("CRIID <", value, "criid");
            return (Criteria) this;
        }

        public Criteria andCriidLessThanOrEqualTo(String value) {
            addCriterion("CRIID <=", value, "criid");
            return (Criteria) this;
        }

        public Criteria andCriidLike(String value) {
            addCriterion("CRIID like", value, "criid");
            return (Criteria) this;
        }

        public Criteria andCriidNotLike(String value) {
            addCriterion("CRIID not like", value, "criid");
            return (Criteria) this;
        }

        public Criteria andCriidIn(List<String> values) {
            addCriterion("CRIID in", values, "criid");
            return (Criteria) this;
        }

        public Criteria andCriidNotIn(List<String> values) {
            addCriterion("CRIID not in", values, "criid");
            return (Criteria) this;
        }

        public Criteria andCriidBetween(String value1, String value2) {
            addCriterion("CRIID between", value1, value2, "criid");
            return (Criteria) this;
        }

        public Criteria andCriidNotBetween(String value1, String value2) {
            addCriterion("CRIID not between", value1, value2, "criid");
            return (Criteria) this;
        }

        public Criteria andOptimeIsNull() {
            addCriterion("OPTIME is null");
            return (Criteria) this;
        }

        public Criteria andOptimeIsNotNull() {
            addCriterion("OPTIME is not null");
            return (Criteria) this;
        }

        public Criteria andOptimeEqualTo(Date value) {
            addCriterionForJDBCDate("OPTIME =", value, "optime");
            return (Criteria) this;
        }

        public Criteria andOptimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("OPTIME <>", value, "optime");
            return (Criteria) this;
        }

        public Criteria andOptimeGreaterThan(Date value) {
            addCriterionForJDBCDate("OPTIME >", value, "optime");
            return (Criteria) this;
        }

        public Criteria andOptimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("OPTIME >=", value, "optime");
            return (Criteria) this;
        }

        public Criteria andOptimeLessThan(Date value) {
            addCriterionForJDBCDate("OPTIME <", value, "optime");
            return (Criteria) this;
        }

        public Criteria andOptimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("OPTIME <=", value, "optime");
            return (Criteria) this;
        }

        public Criteria andOptimeIn(List<Date> values) {
            addCriterionForJDBCDate("OPTIME in", values, "optime");
            return (Criteria) this;
        }

        public Criteria andOptimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("OPTIME not in", values, "optime");
            return (Criteria) this;
        }

        public Criteria andOptimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("OPTIME between", value1, value2, "optime");
            return (Criteria) this;
        }

        public Criteria andOptimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("OPTIME not between", value1, value2, "optime");
            return (Criteria) this;
        }

        public Criteria andDepartidIsNull() {
            addCriterion("DEPARTID is null");
            return (Criteria) this;
        }

        public Criteria andDepartidIsNotNull() {
            addCriterion("DEPARTID is not null");
            return (Criteria) this;
        }

        public Criteria andDepartidEqualTo(String value) {
            addCriterion("DEPARTID =", value, "departid");
            return (Criteria) this;
        }

        public Criteria andDepartidNotEqualTo(String value) {
            addCriterion("DEPARTID <>", value, "departid");
            return (Criteria) this;
        }

        public Criteria andDepartidGreaterThan(String value) {
            addCriterion("DEPARTID >", value, "departid");
            return (Criteria) this;
        }

        public Criteria andDepartidGreaterThanOrEqualTo(String value) {
            addCriterion("DEPARTID >=", value, "departid");
            return (Criteria) this;
        }

        public Criteria andDepartidLessThan(String value) {
            addCriterion("DEPARTID <", value, "departid");
            return (Criteria) this;
        }

        public Criteria andDepartidLessThanOrEqualTo(String value) {
            addCriterion("DEPARTID <=", value, "departid");
            return (Criteria) this;
        }

        public Criteria andDepartidLike(String value) {
            addCriterion("DEPARTID like", value, "departid");
            return (Criteria) this;
        }

        public Criteria andDepartidNotLike(String value) {
            addCriterion("DEPARTID not like", value, "departid");
            return (Criteria) this;
        }

        public Criteria andDepartidIn(List<String> values) {
            addCriterion("DEPARTID in", values, "departid");
            return (Criteria) this;
        }

        public Criteria andDepartidNotIn(List<String> values) {
            addCriterion("DEPARTID not in", values, "departid");
            return (Criteria) this;
        }

        public Criteria andDepartidBetween(String value1, String value2) {
            addCriterion("DEPARTID between", value1, value2, "departid");
            return (Criteria) this;
        }

        public Criteria andDepartidNotBetween(String value1, String value2) {
            addCriterion("DEPARTID not between", value1, value2, "departid");
            return (Criteria) this;
        }

        public Criteria andInt1IsNull() {
            addCriterion("INT1 is null");
            return (Criteria) this;
        }

        public Criteria andInt1IsNotNull() {
            addCriterion("INT1 is not null");
            return (Criteria) this;
        }

        public Criteria andInt1EqualTo(Integer value) {
            addCriterion("INT1 =", value, "int1");
            return (Criteria) this;
        }

        public Criteria andInt1NotEqualTo(Integer value) {
            addCriterion("INT1 <>", value, "int1");
            return (Criteria) this;
        }

        public Criteria andInt1GreaterThan(Integer value) {
            addCriterion("INT1 >", value, "int1");
            return (Criteria) this;
        }

        public Criteria andInt1GreaterThanOrEqualTo(Integer value) {
            addCriterion("INT1 >=", value, "int1");
            return (Criteria) this;
        }

        public Criteria andInt1LessThan(Integer value) {
            addCriterion("INT1 <", value, "int1");
            return (Criteria) this;
        }

        public Criteria andInt1LessThanOrEqualTo(Integer value) {
            addCriterion("INT1 <=", value, "int1");
            return (Criteria) this;
        }

        public Criteria andInt1In(List<Integer> values) {
            addCriterion("INT1 in", values, "int1");
            return (Criteria) this;
        }

        public Criteria andInt1NotIn(List<Integer> values) {
            addCriterion("INT1 not in", values, "int1");
            return (Criteria) this;
        }

        public Criteria andInt1Between(Integer value1, Integer value2) {
            addCriterion("INT1 between", value1, value2, "int1");
            return (Criteria) this;
        }

        public Criteria andInt1NotBetween(Integer value1, Integer value2) {
            addCriterion("INT1 not between", value1, value2, "int1");
            return (Criteria) this;
        }

        public Criteria andInt2IsNull() {
            addCriterion("INT2 is null");
            return (Criteria) this;
        }

        public Criteria andInt2IsNotNull() {
            addCriterion("INT2 is not null");
            return (Criteria) this;
        }

        public Criteria andInt2EqualTo(Integer value) {
            addCriterion("INT2 =", value, "int2");
            return (Criteria) this;
        }

        public Criteria andInt2NotEqualTo(Integer value) {
            addCriterion("INT2 <>", value, "int2");
            return (Criteria) this;
        }

        public Criteria andInt2GreaterThan(Integer value) {
            addCriterion("INT2 >", value, "int2");
            return (Criteria) this;
        }

        public Criteria andInt2GreaterThanOrEqualTo(Integer value) {
            addCriterion("INT2 >=", value, "int2");
            return (Criteria) this;
        }

        public Criteria andInt2LessThan(Integer value) {
            addCriterion("INT2 <", value, "int2");
            return (Criteria) this;
        }

        public Criteria andInt2LessThanOrEqualTo(Integer value) {
            addCriterion("INT2 <=", value, "int2");
            return (Criteria) this;
        }

        public Criteria andInt2In(List<Integer> values) {
            addCriterion("INT2 in", values, "int2");
            return (Criteria) this;
        }

        public Criteria andInt2NotIn(List<Integer> values) {
            addCriterion("INT2 not in", values, "int2");
            return (Criteria) this;
        }

        public Criteria andInt2Between(Integer value1, Integer value2) {
            addCriterion("INT2 between", value1, value2, "int2");
            return (Criteria) this;
        }

        public Criteria andInt2NotBetween(Integer value1, Integer value2) {
            addCriterion("INT2 not between", value1, value2, "int2");
            return (Criteria) this;
        }

        public Criteria andInt3IsNull() {
            addCriterion("INT3 is null");
            return (Criteria) this;
        }

        public Criteria andInt3IsNotNull() {
            addCriterion("INT3 is not null");
            return (Criteria) this;
        }

        public Criteria andInt3EqualTo(Integer value) {
            addCriterion("INT3 =", value, "int3");
            return (Criteria) this;
        }

        public Criteria andInt3NotEqualTo(Integer value) {
            addCriterion("INT3 <>", value, "int3");
            return (Criteria) this;
        }

        public Criteria andInt3GreaterThan(Integer value) {
            addCriterion("INT3 >", value, "int3");
            return (Criteria) this;
        }

        public Criteria andInt3GreaterThanOrEqualTo(Integer value) {
            addCriterion("INT3 >=", value, "int3");
            return (Criteria) this;
        }

        public Criteria andInt3LessThan(Integer value) {
            addCriterion("INT3 <", value, "int3");
            return (Criteria) this;
        }

        public Criteria andInt3LessThanOrEqualTo(Integer value) {
            addCriterion("INT3 <=", value, "int3");
            return (Criteria) this;
        }

        public Criteria andInt3In(List<Integer> values) {
            addCriterion("INT3 in", values, "int3");
            return (Criteria) this;
        }

        public Criteria andInt3NotIn(List<Integer> values) {
            addCriterion("INT3 not in", values, "int3");
            return (Criteria) this;
        }

        public Criteria andInt3Between(Integer value1, Integer value2) {
            addCriterion("INT3 between", value1, value2, "int3");
            return (Criteria) this;
        }

        public Criteria andInt3NotBetween(Integer value1, Integer value2) {
            addCriterion("INT3 not between", value1, value2, "int3");
            return (Criteria) this;
        }

        public Criteria andInt4IsNull() {
            addCriterion("INT4 is null");
            return (Criteria) this;
        }

        public Criteria andInt4IsNotNull() {
            addCriterion("INT4 is not null");
            return (Criteria) this;
        }

        public Criteria andInt4EqualTo(Integer value) {
            addCriterion("INT4 =", value, "int4");
            return (Criteria) this;
        }

        public Criteria andInt4NotEqualTo(Integer value) {
            addCriterion("INT4 <>", value, "int4");
            return (Criteria) this;
        }

        public Criteria andInt4GreaterThan(Integer value) {
            addCriterion("INT4 >", value, "int4");
            return (Criteria) this;
        }

        public Criteria andInt4GreaterThanOrEqualTo(Integer value) {
            addCriterion("INT4 >=", value, "int4");
            return (Criteria) this;
        }

        public Criteria andInt4LessThan(Integer value) {
            addCriterion("INT4 <", value, "int4");
            return (Criteria) this;
        }

        public Criteria andInt4LessThanOrEqualTo(Integer value) {
            addCriterion("INT4 <=", value, "int4");
            return (Criteria) this;
        }

        public Criteria andInt4In(List<Integer> values) {
            addCriterion("INT4 in", values, "int4");
            return (Criteria) this;
        }

        public Criteria andInt4NotIn(List<Integer> values) {
            addCriterion("INT4 not in", values, "int4");
            return (Criteria) this;
        }

        public Criteria andInt4Between(Integer value1, Integer value2) {
            addCriterion("INT4 between", value1, value2, "int4");
            return (Criteria) this;
        }

        public Criteria andInt4NotBetween(Integer value1, Integer value2) {
            addCriterion("INT4 not between", value1, value2, "int4");
            return (Criteria) this;
        }

        public Criteria andInt5IsNull() {
            addCriterion("INT5 is null");
            return (Criteria) this;
        }

        public Criteria andInt5IsNotNull() {
            addCriterion("INT5 is not null");
            return (Criteria) this;
        }

        public Criteria andInt5EqualTo(Integer value) {
            addCriterion("INT5 =", value, "int5");
            return (Criteria) this;
        }

        public Criteria andInt5NotEqualTo(Integer value) {
            addCriterion("INT5 <>", value, "int5");
            return (Criteria) this;
        }

        public Criteria andInt5GreaterThan(Integer value) {
            addCriterion("INT5 >", value, "int5");
            return (Criteria) this;
        }

        public Criteria andInt5GreaterThanOrEqualTo(Integer value) {
            addCriterion("INT5 >=", value, "int5");
            return (Criteria) this;
        }

        public Criteria andInt5LessThan(Integer value) {
            addCriterion("INT5 <", value, "int5");
            return (Criteria) this;
        }

        public Criteria andInt5LessThanOrEqualTo(Integer value) {
            addCriterion("INT5 <=", value, "int5");
            return (Criteria) this;
        }

        public Criteria andInt5In(List<Integer> values) {
            addCriterion("INT5 in", values, "int5");
            return (Criteria) this;
        }

        public Criteria andInt5NotIn(List<Integer> values) {
            addCriterion("INT5 not in", values, "int5");
            return (Criteria) this;
        }

        public Criteria andInt5Between(Integer value1, Integer value2) {
            addCriterion("INT5 between", value1, value2, "int5");
            return (Criteria) this;
        }

        public Criteria andInt5NotBetween(Integer value1, Integer value2) {
            addCriterion("INT5 not between", value1, value2, "int5");
            return (Criteria) this;
        }

        public Criteria andInt6IsNull() {
            addCriterion("INT6 is null");
            return (Criteria) this;
        }

        public Criteria andInt6IsNotNull() {
            addCriterion("INT6 is not null");
            return (Criteria) this;
        }

        public Criteria andInt6EqualTo(Integer value) {
            addCriterion("INT6 =", value, "int6");
            return (Criteria) this;
        }

        public Criteria andInt6NotEqualTo(Integer value) {
            addCriterion("INT6 <>", value, "int6");
            return (Criteria) this;
        }

        public Criteria andInt6GreaterThan(Integer value) {
            addCriterion("INT6 >", value, "int6");
            return (Criteria) this;
        }

        public Criteria andInt6GreaterThanOrEqualTo(Integer value) {
            addCriterion("INT6 >=", value, "int6");
            return (Criteria) this;
        }

        public Criteria andInt6LessThan(Integer value) {
            addCriterion("INT6 <", value, "int6");
            return (Criteria) this;
        }

        public Criteria andInt6LessThanOrEqualTo(Integer value) {
            addCriterion("INT6 <=", value, "int6");
            return (Criteria) this;
        }

        public Criteria andInt6In(List<Integer> values) {
            addCriterion("INT6 in", values, "int6");
            return (Criteria) this;
        }

        public Criteria andInt6NotIn(List<Integer> values) {
            addCriterion("INT6 not in", values, "int6");
            return (Criteria) this;
        }

        public Criteria andInt6Between(Integer value1, Integer value2) {
            addCriterion("INT6 between", value1, value2, "int6");
            return (Criteria) this;
        }

        public Criteria andInt6NotBetween(Integer value1, Integer value2) {
            addCriterion("INT6 not between", value1, value2, "int6");
            return (Criteria) this;
        }

        public Criteria andInt7IsNull() {
            addCriterion("INT7 is null");
            return (Criteria) this;
        }

        public Criteria andInt7IsNotNull() {
            addCriterion("INT7 is not null");
            return (Criteria) this;
        }

        public Criteria andInt7EqualTo(Integer value) {
            addCriterion("INT7 =", value, "int7");
            return (Criteria) this;
        }

        public Criteria andInt7NotEqualTo(Integer value) {
            addCriterion("INT7 <>", value, "int7");
            return (Criteria) this;
        }

        public Criteria andInt7GreaterThan(Integer value) {
            addCriterion("INT7 >", value, "int7");
            return (Criteria) this;
        }

        public Criteria andInt7GreaterThanOrEqualTo(Integer value) {
            addCriterion("INT7 >=", value, "int7");
            return (Criteria) this;
        }

        public Criteria andInt7LessThan(Integer value) {
            addCriterion("INT7 <", value, "int7");
            return (Criteria) this;
        }

        public Criteria andInt7LessThanOrEqualTo(Integer value) {
            addCriterion("INT7 <=", value, "int7");
            return (Criteria) this;
        }

        public Criteria andInt7In(List<Integer> values) {
            addCriterion("INT7 in", values, "int7");
            return (Criteria) this;
        }

        public Criteria andInt7NotIn(List<Integer> values) {
            addCriterion("INT7 not in", values, "int7");
            return (Criteria) this;
        }

        public Criteria andInt7Between(Integer value1, Integer value2) {
            addCriterion("INT7 between", value1, value2, "int7");
            return (Criteria) this;
        }

        public Criteria andInt7NotBetween(Integer value1, Integer value2) {
            addCriterion("INT7 not between", value1, value2, "int7");
            return (Criteria) this;
        }

        public Criteria andInt8IsNull() {
            addCriterion("INT8 is null");
            return (Criteria) this;
        }

        public Criteria andInt8IsNotNull() {
            addCriterion("INT8 is not null");
            return (Criteria) this;
        }

        public Criteria andInt8EqualTo(Integer value) {
            addCriterion("INT8 =", value, "int8");
            return (Criteria) this;
        }

        public Criteria andInt8NotEqualTo(Integer value) {
            addCriterion("INT8 <>", value, "int8");
            return (Criteria) this;
        }

        public Criteria andInt8GreaterThan(Integer value) {
            addCriterion("INT8 >", value, "int8");
            return (Criteria) this;
        }

        public Criteria andInt8GreaterThanOrEqualTo(Integer value) {
            addCriterion("INT8 >=", value, "int8");
            return (Criteria) this;
        }

        public Criteria andInt8LessThan(Integer value) {
            addCriterion("INT8 <", value, "int8");
            return (Criteria) this;
        }

        public Criteria andInt8LessThanOrEqualTo(Integer value) {
            addCriterion("INT8 <=", value, "int8");
            return (Criteria) this;
        }

        public Criteria andInt8In(List<Integer> values) {
            addCriterion("INT8 in", values, "int8");
            return (Criteria) this;
        }

        public Criteria andInt8NotIn(List<Integer> values) {
            addCriterion("INT8 not in", values, "int8");
            return (Criteria) this;
        }

        public Criteria andInt8Between(Integer value1, Integer value2) {
            addCriterion("INT8 between", value1, value2, "int8");
            return (Criteria) this;
        }

        public Criteria andInt8NotBetween(Integer value1, Integer value2) {
            addCriterion("INT8 not between", value1, value2, "int8");
            return (Criteria) this;
        }

        public Criteria andInt9IsNull() {
            addCriterion("INT9 is null");
            return (Criteria) this;
        }

        public Criteria andInt9IsNotNull() {
            addCriterion("INT9 is not null");
            return (Criteria) this;
        }

        public Criteria andInt9EqualTo(Integer value) {
            addCriterion("INT9 =", value, "int9");
            return (Criteria) this;
        }

        public Criteria andInt9NotEqualTo(Integer value) {
            addCriterion("INT9 <>", value, "int9");
            return (Criteria) this;
        }

        public Criteria andInt9GreaterThan(Integer value) {
            addCriterion("INT9 >", value, "int9");
            return (Criteria) this;
        }

        public Criteria andInt9GreaterThanOrEqualTo(Integer value) {
            addCriterion("INT9 >=", value, "int9");
            return (Criteria) this;
        }

        public Criteria andInt9LessThan(Integer value) {
            addCriterion("INT9 <", value, "int9");
            return (Criteria) this;
        }

        public Criteria andInt9LessThanOrEqualTo(Integer value) {
            addCriterion("INT9 <=", value, "int9");
            return (Criteria) this;
        }

        public Criteria andInt9In(List<Integer> values) {
            addCriterion("INT9 in", values, "int9");
            return (Criteria) this;
        }

        public Criteria andInt9NotIn(List<Integer> values) {
            addCriterion("INT9 not in", values, "int9");
            return (Criteria) this;
        }

        public Criteria andInt9Between(Integer value1, Integer value2) {
            addCriterion("INT9 between", value1, value2, "int9");
            return (Criteria) this;
        }

        public Criteria andInt9NotBetween(Integer value1, Integer value2) {
            addCriterion("INT9 not between", value1, value2, "int9");
            return (Criteria) this;
        }

        public Criteria andInt10IsNull() {
            addCriterion("INT10 is null");
            return (Criteria) this;
        }

        public Criteria andInt10IsNotNull() {
            addCriterion("INT10 is not null");
            return (Criteria) this;
        }

        public Criteria andInt10EqualTo(Integer value) {
            addCriterion("INT10 =", value, "int10");
            return (Criteria) this;
        }

        public Criteria andInt10NotEqualTo(Integer value) {
            addCriterion("INT10 <>", value, "int10");
            return (Criteria) this;
        }

        public Criteria andInt10GreaterThan(Integer value) {
            addCriterion("INT10 >", value, "int10");
            return (Criteria) this;
        }

        public Criteria andInt10GreaterThanOrEqualTo(Integer value) {
            addCriterion("INT10 >=", value, "int10");
            return (Criteria) this;
        }

        public Criteria andInt10LessThan(Integer value) {
            addCriterion("INT10 <", value, "int10");
            return (Criteria) this;
        }

        public Criteria andInt10LessThanOrEqualTo(Integer value) {
            addCriterion("INT10 <=", value, "int10");
            return (Criteria) this;
        }

        public Criteria andInt10In(List<Integer> values) {
            addCriterion("INT10 in", values, "int10");
            return (Criteria) this;
        }

        public Criteria andInt10NotIn(List<Integer> values) {
            addCriterion("INT10 not in", values, "int10");
            return (Criteria) this;
        }

        public Criteria andInt10Between(Integer value1, Integer value2) {
            addCriterion("INT10 between", value1, value2, "int10");
            return (Criteria) this;
        }

        public Criteria andInt10NotBetween(Integer value1, Integer value2) {
            addCriterion("INT10 not between", value1, value2, "int10");
            return (Criteria) this;
        }

        public Criteria andIsmergeIsNull() {
            addCriterion("ISMERGE is null");
            return (Criteria) this;
        }

        public Criteria andIsmergeIsNotNull() {
            addCriterion("ISMERGE is not null");
            return (Criteria) this;
        }

        public Criteria andIsmergeEqualTo(Short value) {
            addCriterion("ISMERGE =", value, "ismerge");
            return (Criteria) this;
        }

        public Criteria andIsmergeNotEqualTo(Short value) {
            addCriterion("ISMERGE <>", value, "ismerge");
            return (Criteria) this;
        }

        public Criteria andIsmergeGreaterThan(Short value) {
            addCriterion("ISMERGE >", value, "ismerge");
            return (Criteria) this;
        }

        public Criteria andIsmergeGreaterThanOrEqualTo(Short value) {
            addCriterion("ISMERGE >=", value, "ismerge");
            return (Criteria) this;
        }

        public Criteria andIsmergeLessThan(Short value) {
            addCriterion("ISMERGE <", value, "ismerge");
            return (Criteria) this;
        }

        public Criteria andIsmergeLessThanOrEqualTo(Short value) {
            addCriterion("ISMERGE <=", value, "ismerge");
            return (Criteria) this;
        }

        public Criteria andIsmergeIn(List<Short> values) {
            addCriterion("ISMERGE in", values, "ismerge");
            return (Criteria) this;
        }

        public Criteria andIsmergeNotIn(List<Short> values) {
            addCriterion("ISMERGE not in", values, "ismerge");
            return (Criteria) this;
        }

        public Criteria andIsmergeBetween(Short value1, Short value2) {
            addCriterion("ISMERGE between", value1, value2, "ismerge");
            return (Criteria) this;
        }

        public Criteria andIsmergeNotBetween(Short value1, Short value2) {
            addCriterion("ISMERGE not between", value1, value2, "ismerge");
            return (Criteria) this;
        }

        public Criteria andOpidIsNull() {
            addCriterion("OPID is null");
            return (Criteria) this;
        }

        public Criteria andOpidIsNotNull() {
            addCriterion("OPID is not null");
            return (Criteria) this;
        }

        public Criteria andOpidEqualTo(String value) {
            addCriterion("OPID =", value, "opid");
            return (Criteria) this;
        }

        public Criteria andOpidNotEqualTo(String value) {
            addCriterion("OPID <>", value, "opid");
            return (Criteria) this;
        }

        public Criteria andOpidGreaterThan(String value) {
            addCriterion("OPID >", value, "opid");
            return (Criteria) this;
        }

        public Criteria andOpidGreaterThanOrEqualTo(String value) {
            addCriterion("OPID >=", value, "opid");
            return (Criteria) this;
        }

        public Criteria andOpidLessThan(String value) {
            addCriterion("OPID <", value, "opid");
            return (Criteria) this;
        }

        public Criteria andOpidLessThanOrEqualTo(String value) {
            addCriterion("OPID <=", value, "opid");
            return (Criteria) this;
        }

        public Criteria andOpidLike(String value) {
            addCriterion("OPID like", value, "opid");
            return (Criteria) this;
        }

        public Criteria andOpidNotLike(String value) {
            addCriterion("OPID not like", value, "opid");
            return (Criteria) this;
        }

        public Criteria andOpidIn(List<String> values) {
            addCriterion("OPID in", values, "opid");
            return (Criteria) this;
        }

        public Criteria andOpidNotIn(List<String> values) {
            addCriterion("OPID not in", values, "opid");
            return (Criteria) this;
        }

        public Criteria andOpidBetween(String value1, String value2) {
            addCriterion("OPID between", value1, value2, "opid");
            return (Criteria) this;
        }

        public Criteria andOpidNotBetween(String value1, String value2) {
            addCriterion("OPID not between", value1, value2, "opid");
            return (Criteria) this;
        }

        public Criteria andGettimeIsNull() {
            addCriterion("GETTIME is null");
            return (Criteria) this;
        }

        public Criteria andGettimeIsNotNull() {
            addCriterion("GETTIME is not null");
            return (Criteria) this;
        }

        public Criteria andGettimeEqualTo(Date value) {
            addCriterionForJDBCDate("GETTIME =", value, "gettime");
            return (Criteria) this;
        }

        public Criteria andGettimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("GETTIME <>", value, "gettime");
            return (Criteria) this;
        }

        public Criteria andGettimeGreaterThan(Date value) {
            addCriterionForJDBCDate("GETTIME >", value, "gettime");
            return (Criteria) this;
        }

        public Criteria andGettimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("GETTIME >=", value, "gettime");
            return (Criteria) this;
        }

        public Criteria andGettimeLessThan(Date value) {
            addCriterionForJDBCDate("GETTIME <", value, "gettime");
            return (Criteria) this;
        }

        public Criteria andGettimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("GETTIME <=", value, "gettime");
            return (Criteria) this;
        }

        public Criteria andGettimeIn(List<Date> values) {
            addCriterionForJDBCDate("GETTIME in", values, "gettime");
            return (Criteria) this;
        }

        public Criteria andGettimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("GETTIME not in", values, "gettime");
            return (Criteria) this;
        }

        public Criteria andGettimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("GETTIME between", value1, value2, "gettime");
            return (Criteria) this;
        }

        public Criteria andGettimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("GETTIME not between", value1, value2, "gettime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}