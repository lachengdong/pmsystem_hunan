package com.sinog2c.model.criminalexam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class TbyzCheckForWorkExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbyzCheckForWorkExample() {
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

        public Criteria andCrimidIsNull() {
            addCriterion("CRIMID is null");
            return (Criteria) this;
        }

        public Criteria andCrimidIsNotNull() {
            addCriterion("CRIMID is not null");
            return (Criteria) this;
        }

        public Criteria andCrimidEqualTo(String value) {
            addCriterion("CRIMID =", value, "crimid");
            return (Criteria) this;
        }

        public Criteria andCrimidNotEqualTo(String value) {
            addCriterion("CRIMID <>", value, "crimid");
            return (Criteria) this;
        }

        public Criteria andCrimidGreaterThan(String value) {
            addCriterion("CRIMID >", value, "crimid");
            return (Criteria) this;
        }

        public Criteria andCrimidGreaterThanOrEqualTo(String value) {
            addCriterion("CRIMID >=", value, "crimid");
            return (Criteria) this;
        }

        public Criteria andCrimidLessThan(String value) {
            addCriterion("CRIMID <", value, "crimid");
            return (Criteria) this;
        }

        public Criteria andCrimidLessThanOrEqualTo(String value) {
            addCriterion("CRIMID <=", value, "crimid");
            return (Criteria) this;
        }

        public Criteria andCrimidLike(String value) {
            addCriterion("CRIMID like", value, "crimid");
            return (Criteria) this;
        }

        public Criteria andCrimidNotLike(String value) {
            addCriterion("CRIMID not like", value, "crimid");
            return (Criteria) this;
        }

        public Criteria andCrimidIn(List<String> values) {
            addCriterion("CRIMID in", values, "crimid");
            return (Criteria) this;
        }

        public Criteria andCrimidNotIn(List<String> values) {
            addCriterion("CRIMID not in", values, "crimid");
            return (Criteria) this;
        }

        public Criteria andCrimidBetween(String value1, String value2) {
            addCriterion("CRIMID between", value1, value2, "crimid");
            return (Criteria) this;
        }

        public Criteria andCrimidNotBetween(String value1, String value2) {
            addCriterion("CRIMID not between", value1, value2, "crimid");
            return (Criteria) this;
        }

        public Criteria andYeardateIsNull() {
            addCriterion("YEARDATE is null");
            return (Criteria) this;
        }

        public Criteria andYeardateIsNotNull() {
            addCriterion("YEARDATE is not null");
            return (Criteria) this;
        }

        public Criteria andYeardateEqualTo(String value) {
            addCriterion("YEARDATE =", value, "yeardate");
            return (Criteria) this;
        }

        public Criteria andYeardateNotEqualTo(String value) {
            addCriterion("YEARDATE <>", value, "yeardate");
            return (Criteria) this;
        }

        public Criteria andYeardateGreaterThan(String value) {
            addCriterion("YEARDATE >", value, "yeardate");
            return (Criteria) this;
        }

        public Criteria andYeardateGreaterThanOrEqualTo(String value) {
            addCriterion("YEARDATE >=", value, "yeardate");
            return (Criteria) this;
        }

        public Criteria andYeardateLessThan(String value) {
            addCriterion("YEARDATE <", value, "yeardate");
            return (Criteria) this;
        }

        public Criteria andYeardateLessThanOrEqualTo(String value) {
            addCriterion("YEARDATE <=", value, "yeardate");
            return (Criteria) this;
        }

        public Criteria andYeardateLike(String value) {
            addCriterion("YEARDATE like", value, "yeardate");
            return (Criteria) this;
        }

        public Criteria andYeardateNotLike(String value) {
            addCriterion("YEARDATE not like", value, "yeardate");
            return (Criteria) this;
        }

        public Criteria andYeardateIn(List<String> values) {
            addCriterion("YEARDATE in", values, "yeardate");
            return (Criteria) this;
        }

        public Criteria andYeardateNotIn(List<String> values) {
            addCriterion("YEARDATE not in", values, "yeardate");
            return (Criteria) this;
        }

        public Criteria andYeardateBetween(String value1, String value2) {
            addCriterion("YEARDATE between", value1, value2, "yeardate");
            return (Criteria) this;
        }

        public Criteria andYeardateNotBetween(String value1, String value2) {
            addCriterion("YEARDATE not between", value1, value2, "yeardate");
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

        public Criteria andDay01IsNull() {
            addCriterion("DAY01 is null");
            return (Criteria) this;
        }

        public Criteria andDay01IsNotNull() {
            addCriterion("DAY01 is not null");
            return (Criteria) this;
        }

        public Criteria andDay01EqualTo(String value) {
            addCriterion("DAY01 =", value, "day01");
            return (Criteria) this;
        }

        public Criteria andDay01NotEqualTo(String value) {
            addCriterion("DAY01 <>", value, "day01");
            return (Criteria) this;
        }

        public Criteria andDay01GreaterThan(String value) {
            addCriterion("DAY01 >", value, "day01");
            return (Criteria) this;
        }

        public Criteria andDay01GreaterThanOrEqualTo(String value) {
            addCriterion("DAY01 >=", value, "day01");
            return (Criteria) this;
        }

        public Criteria andDay01LessThan(String value) {
            addCriterion("DAY01 <", value, "day01");
            return (Criteria) this;
        }

        public Criteria andDay01LessThanOrEqualTo(String value) {
            addCriterion("DAY01 <=", value, "day01");
            return (Criteria) this;
        }

        public Criteria andDay01Like(String value) {
            addCriterion("DAY01 like", value, "day01");
            return (Criteria) this;
        }

        public Criteria andDay01NotLike(String value) {
            addCriterion("DAY01 not like", value, "day01");
            return (Criteria) this;
        }

        public Criteria andDay01In(List<String> values) {
            addCriterion("DAY01 in", values, "day01");
            return (Criteria) this;
        }

        public Criteria andDay01NotIn(List<String> values) {
            addCriterion("DAY01 not in", values, "day01");
            return (Criteria) this;
        }

        public Criteria andDay01Between(String value1, String value2) {
            addCriterion("DAY01 between", value1, value2, "day01");
            return (Criteria) this;
        }

        public Criteria andDay01NotBetween(String value1, String value2) {
            addCriterion("DAY01 not between", value1, value2, "day01");
            return (Criteria) this;
        }

        public Criteria andDay02IsNull() {
            addCriterion("DAY02 is null");
            return (Criteria) this;
        }

        public Criteria andDay02IsNotNull() {
            addCriterion("DAY02 is not null");
            return (Criteria) this;
        }

        public Criteria andDay02EqualTo(String value) {
            addCriterion("DAY02 =", value, "day02");
            return (Criteria) this;
        }

        public Criteria andDay02NotEqualTo(String value) {
            addCriterion("DAY02 <>", value, "day02");
            return (Criteria) this;
        }

        public Criteria andDay02GreaterThan(String value) {
            addCriterion("DAY02 >", value, "day02");
            return (Criteria) this;
        }

        public Criteria andDay02GreaterThanOrEqualTo(String value) {
            addCriterion("DAY02 >=", value, "day02");
            return (Criteria) this;
        }

        public Criteria andDay02LessThan(String value) {
            addCriterion("DAY02 <", value, "day02");
            return (Criteria) this;
        }

        public Criteria andDay02LessThanOrEqualTo(String value) {
            addCriterion("DAY02 <=", value, "day02");
            return (Criteria) this;
        }

        public Criteria andDay02Like(String value) {
            addCriterion("DAY02 like", value, "day02");
            return (Criteria) this;
        }

        public Criteria andDay02NotLike(String value) {
            addCriterion("DAY02 not like", value, "day02");
            return (Criteria) this;
        }

        public Criteria andDay02In(List<String> values) {
            addCriterion("DAY02 in", values, "day02");
            return (Criteria) this;
        }

        public Criteria andDay02NotIn(List<String> values) {
            addCriterion("DAY02 not in", values, "day02");
            return (Criteria) this;
        }

        public Criteria andDay02Between(String value1, String value2) {
            addCriterion("DAY02 between", value1, value2, "day02");
            return (Criteria) this;
        }

        public Criteria andDay02NotBetween(String value1, String value2) {
            addCriterion("DAY02 not between", value1, value2, "day02");
            return (Criteria) this;
        }

        public Criteria andDay03IsNull() {
            addCriterion("DAY03 is null");
            return (Criteria) this;
        }

        public Criteria andDay03IsNotNull() {
            addCriterion("DAY03 is not null");
            return (Criteria) this;
        }

        public Criteria andDay03EqualTo(String value) {
            addCriterion("DAY03 =", value, "day03");
            return (Criteria) this;
        }

        public Criteria andDay03NotEqualTo(String value) {
            addCriterion("DAY03 <>", value, "day03");
            return (Criteria) this;
        }

        public Criteria andDay03GreaterThan(String value) {
            addCriterion("DAY03 >", value, "day03");
            return (Criteria) this;
        }

        public Criteria andDay03GreaterThanOrEqualTo(String value) {
            addCriterion("DAY03 >=", value, "day03");
            return (Criteria) this;
        }

        public Criteria andDay03LessThan(String value) {
            addCriterion("DAY03 <", value, "day03");
            return (Criteria) this;
        }

        public Criteria andDay03LessThanOrEqualTo(String value) {
            addCriterion("DAY03 <=", value, "day03");
            return (Criteria) this;
        }

        public Criteria andDay03Like(String value) {
            addCriterion("DAY03 like", value, "day03");
            return (Criteria) this;
        }

        public Criteria andDay03NotLike(String value) {
            addCriterion("DAY03 not like", value, "day03");
            return (Criteria) this;
        }

        public Criteria andDay03In(List<String> values) {
            addCriterion("DAY03 in", values, "day03");
            return (Criteria) this;
        }

        public Criteria andDay03NotIn(List<String> values) {
            addCriterion("DAY03 not in", values, "day03");
            return (Criteria) this;
        }

        public Criteria andDay03Between(String value1, String value2) {
            addCriterion("DAY03 between", value1, value2, "day03");
            return (Criteria) this;
        }

        public Criteria andDay03NotBetween(String value1, String value2) {
            addCriterion("DAY03 not between", value1, value2, "day03");
            return (Criteria) this;
        }

        public Criteria andDay04IsNull() {
            addCriterion("DAY04 is null");
            return (Criteria) this;
        }

        public Criteria andDay04IsNotNull() {
            addCriterion("DAY04 is not null");
            return (Criteria) this;
        }

        public Criteria andDay04EqualTo(String value) {
            addCriterion("DAY04 =", value, "day04");
            return (Criteria) this;
        }

        public Criteria andDay04NotEqualTo(String value) {
            addCriterion("DAY04 <>", value, "day04");
            return (Criteria) this;
        }

        public Criteria andDay04GreaterThan(String value) {
            addCriterion("DAY04 >", value, "day04");
            return (Criteria) this;
        }

        public Criteria andDay04GreaterThanOrEqualTo(String value) {
            addCriterion("DAY04 >=", value, "day04");
            return (Criteria) this;
        }

        public Criteria andDay04LessThan(String value) {
            addCriterion("DAY04 <", value, "day04");
            return (Criteria) this;
        }

        public Criteria andDay04LessThanOrEqualTo(String value) {
            addCriterion("DAY04 <=", value, "day04");
            return (Criteria) this;
        }

        public Criteria andDay04Like(String value) {
            addCriterion("DAY04 like", value, "day04");
            return (Criteria) this;
        }

        public Criteria andDay04NotLike(String value) {
            addCriterion("DAY04 not like", value, "day04");
            return (Criteria) this;
        }

        public Criteria andDay04In(List<String> values) {
            addCriterion("DAY04 in", values, "day04");
            return (Criteria) this;
        }

        public Criteria andDay04NotIn(List<String> values) {
            addCriterion("DAY04 not in", values, "day04");
            return (Criteria) this;
        }

        public Criteria andDay04Between(String value1, String value2) {
            addCriterion("DAY04 between", value1, value2, "day04");
            return (Criteria) this;
        }

        public Criteria andDay04NotBetween(String value1, String value2) {
            addCriterion("DAY04 not between", value1, value2, "day04");
            return (Criteria) this;
        }

        public Criteria andDay05IsNull() {
            addCriterion("DAY05 is null");
            return (Criteria) this;
        }

        public Criteria andDay05IsNotNull() {
            addCriterion("DAY05 is not null");
            return (Criteria) this;
        }

        public Criteria andDay05EqualTo(String value) {
            addCriterion("DAY05 =", value, "day05");
            return (Criteria) this;
        }

        public Criteria andDay05NotEqualTo(String value) {
            addCriterion("DAY05 <>", value, "day05");
            return (Criteria) this;
        }

        public Criteria andDay05GreaterThan(String value) {
            addCriterion("DAY05 >", value, "day05");
            return (Criteria) this;
        }

        public Criteria andDay05GreaterThanOrEqualTo(String value) {
            addCriterion("DAY05 >=", value, "day05");
            return (Criteria) this;
        }

        public Criteria andDay05LessThan(String value) {
            addCriterion("DAY05 <", value, "day05");
            return (Criteria) this;
        }

        public Criteria andDay05LessThanOrEqualTo(String value) {
            addCriterion("DAY05 <=", value, "day05");
            return (Criteria) this;
        }

        public Criteria andDay05Like(String value) {
            addCriterion("DAY05 like", value, "day05");
            return (Criteria) this;
        }

        public Criteria andDay05NotLike(String value) {
            addCriterion("DAY05 not like", value, "day05");
            return (Criteria) this;
        }

        public Criteria andDay05In(List<String> values) {
            addCriterion("DAY05 in", values, "day05");
            return (Criteria) this;
        }

        public Criteria andDay05NotIn(List<String> values) {
            addCriterion("DAY05 not in", values, "day05");
            return (Criteria) this;
        }

        public Criteria andDay05Between(String value1, String value2) {
            addCriterion("DAY05 between", value1, value2, "day05");
            return (Criteria) this;
        }

        public Criteria andDay05NotBetween(String value1, String value2) {
            addCriterion("DAY05 not between", value1, value2, "day05");
            return (Criteria) this;
        }

        public Criteria andDay06IsNull() {
            addCriterion("DAY06 is null");
            return (Criteria) this;
        }

        public Criteria andDay06IsNotNull() {
            addCriterion("DAY06 is not null");
            return (Criteria) this;
        }

        public Criteria andDay06EqualTo(String value) {
            addCriterion("DAY06 =", value, "day06");
            return (Criteria) this;
        }

        public Criteria andDay06NotEqualTo(String value) {
            addCriterion("DAY06 <>", value, "day06");
            return (Criteria) this;
        }

        public Criteria andDay06GreaterThan(String value) {
            addCriterion("DAY06 >", value, "day06");
            return (Criteria) this;
        }

        public Criteria andDay06GreaterThanOrEqualTo(String value) {
            addCriterion("DAY06 >=", value, "day06");
            return (Criteria) this;
        }

        public Criteria andDay06LessThan(String value) {
            addCriterion("DAY06 <", value, "day06");
            return (Criteria) this;
        }

        public Criteria andDay06LessThanOrEqualTo(String value) {
            addCriterion("DAY06 <=", value, "day06");
            return (Criteria) this;
        }

        public Criteria andDay06Like(String value) {
            addCriterion("DAY06 like", value, "day06");
            return (Criteria) this;
        }

        public Criteria andDay06NotLike(String value) {
            addCriterion("DAY06 not like", value, "day06");
            return (Criteria) this;
        }

        public Criteria andDay06In(List<String> values) {
            addCriterion("DAY06 in", values, "day06");
            return (Criteria) this;
        }

        public Criteria andDay06NotIn(List<String> values) {
            addCriterion("DAY06 not in", values, "day06");
            return (Criteria) this;
        }

        public Criteria andDay06Between(String value1, String value2) {
            addCriterion("DAY06 between", value1, value2, "day06");
            return (Criteria) this;
        }

        public Criteria andDay06NotBetween(String value1, String value2) {
            addCriterion("DAY06 not between", value1, value2, "day06");
            return (Criteria) this;
        }

        public Criteria andDay07IsNull() {
            addCriterion("DAY07 is null");
            return (Criteria) this;
        }

        public Criteria andDay07IsNotNull() {
            addCriterion("DAY07 is not null");
            return (Criteria) this;
        }

        public Criteria andDay07EqualTo(String value) {
            addCriterion("DAY07 =", value, "day07");
            return (Criteria) this;
        }

        public Criteria andDay07NotEqualTo(String value) {
            addCriterion("DAY07 <>", value, "day07");
            return (Criteria) this;
        }

        public Criteria andDay07GreaterThan(String value) {
            addCriterion("DAY07 >", value, "day07");
            return (Criteria) this;
        }

        public Criteria andDay07GreaterThanOrEqualTo(String value) {
            addCriterion("DAY07 >=", value, "day07");
            return (Criteria) this;
        }

        public Criteria andDay07LessThan(String value) {
            addCriterion("DAY07 <", value, "day07");
            return (Criteria) this;
        }

        public Criteria andDay07LessThanOrEqualTo(String value) {
            addCriterion("DAY07 <=", value, "day07");
            return (Criteria) this;
        }

        public Criteria andDay07Like(String value) {
            addCriterion("DAY07 like", value, "day07");
            return (Criteria) this;
        }

        public Criteria andDay07NotLike(String value) {
            addCriterion("DAY07 not like", value, "day07");
            return (Criteria) this;
        }

        public Criteria andDay07In(List<String> values) {
            addCriterion("DAY07 in", values, "day07");
            return (Criteria) this;
        }

        public Criteria andDay07NotIn(List<String> values) {
            addCriterion("DAY07 not in", values, "day07");
            return (Criteria) this;
        }

        public Criteria andDay07Between(String value1, String value2) {
            addCriterion("DAY07 between", value1, value2, "day07");
            return (Criteria) this;
        }

        public Criteria andDay07NotBetween(String value1, String value2) {
            addCriterion("DAY07 not between", value1, value2, "day07");
            return (Criteria) this;
        }

        public Criteria andDay08IsNull() {
            addCriterion("DAY08 is null");
            return (Criteria) this;
        }

        public Criteria andDay08IsNotNull() {
            addCriterion("DAY08 is not null");
            return (Criteria) this;
        }

        public Criteria andDay08EqualTo(String value) {
            addCriterion("DAY08 =", value, "day08");
            return (Criteria) this;
        }

        public Criteria andDay08NotEqualTo(String value) {
            addCriterion("DAY08 <>", value, "day08");
            return (Criteria) this;
        }

        public Criteria andDay08GreaterThan(String value) {
            addCriterion("DAY08 >", value, "day08");
            return (Criteria) this;
        }

        public Criteria andDay08GreaterThanOrEqualTo(String value) {
            addCriterion("DAY08 >=", value, "day08");
            return (Criteria) this;
        }

        public Criteria andDay08LessThan(String value) {
            addCriterion("DAY08 <", value, "day08");
            return (Criteria) this;
        }

        public Criteria andDay08LessThanOrEqualTo(String value) {
            addCriterion("DAY08 <=", value, "day08");
            return (Criteria) this;
        }

        public Criteria andDay08Like(String value) {
            addCriterion("DAY08 like", value, "day08");
            return (Criteria) this;
        }

        public Criteria andDay08NotLike(String value) {
            addCriterion("DAY08 not like", value, "day08");
            return (Criteria) this;
        }

        public Criteria andDay08In(List<String> values) {
            addCriterion("DAY08 in", values, "day08");
            return (Criteria) this;
        }

        public Criteria andDay08NotIn(List<String> values) {
            addCriterion("DAY08 not in", values, "day08");
            return (Criteria) this;
        }

        public Criteria andDay08Between(String value1, String value2) {
            addCriterion("DAY08 between", value1, value2, "day08");
            return (Criteria) this;
        }

        public Criteria andDay08NotBetween(String value1, String value2) {
            addCriterion("DAY08 not between", value1, value2, "day08");
            return (Criteria) this;
        }

        public Criteria andDay09IsNull() {
            addCriterion("DAY09 is null");
            return (Criteria) this;
        }

        public Criteria andDay09IsNotNull() {
            addCriterion("DAY09 is not null");
            return (Criteria) this;
        }

        public Criteria andDay09EqualTo(String value) {
            addCriterion("DAY09 =", value, "day09");
            return (Criteria) this;
        }

        public Criteria andDay09NotEqualTo(String value) {
            addCriterion("DAY09 <>", value, "day09");
            return (Criteria) this;
        }

        public Criteria andDay09GreaterThan(String value) {
            addCriterion("DAY09 >", value, "day09");
            return (Criteria) this;
        }

        public Criteria andDay09GreaterThanOrEqualTo(String value) {
            addCriterion("DAY09 >=", value, "day09");
            return (Criteria) this;
        }

        public Criteria andDay09LessThan(String value) {
            addCriterion("DAY09 <", value, "day09");
            return (Criteria) this;
        }

        public Criteria andDay09LessThanOrEqualTo(String value) {
            addCriterion("DAY09 <=", value, "day09");
            return (Criteria) this;
        }

        public Criteria andDay09Like(String value) {
            addCriterion("DAY09 like", value, "day09");
            return (Criteria) this;
        }

        public Criteria andDay09NotLike(String value) {
            addCriterion("DAY09 not like", value, "day09");
            return (Criteria) this;
        }

        public Criteria andDay09In(List<String> values) {
            addCriterion("DAY09 in", values, "day09");
            return (Criteria) this;
        }

        public Criteria andDay09NotIn(List<String> values) {
            addCriterion("DAY09 not in", values, "day09");
            return (Criteria) this;
        }

        public Criteria andDay09Between(String value1, String value2) {
            addCriterion("DAY09 between", value1, value2, "day09");
            return (Criteria) this;
        }

        public Criteria andDay09NotBetween(String value1, String value2) {
            addCriterion("DAY09 not between", value1, value2, "day09");
            return (Criteria) this;
        }

        public Criteria andDay10IsNull() {
            addCriterion("DAY10 is null");
            return (Criteria) this;
        }

        public Criteria andDay10IsNotNull() {
            addCriterion("DAY10 is not null");
            return (Criteria) this;
        }

        public Criteria andDay10EqualTo(String value) {
            addCriterion("DAY10 =", value, "day10");
            return (Criteria) this;
        }

        public Criteria andDay10NotEqualTo(String value) {
            addCriterion("DAY10 <>", value, "day10");
            return (Criteria) this;
        }

        public Criteria andDay10GreaterThan(String value) {
            addCriterion("DAY10 >", value, "day10");
            return (Criteria) this;
        }

        public Criteria andDay10GreaterThanOrEqualTo(String value) {
            addCriterion("DAY10 >=", value, "day10");
            return (Criteria) this;
        }

        public Criteria andDay10LessThan(String value) {
            addCriterion("DAY10 <", value, "day10");
            return (Criteria) this;
        }

        public Criteria andDay10LessThanOrEqualTo(String value) {
            addCriterion("DAY10 <=", value, "day10");
            return (Criteria) this;
        }

        public Criteria andDay10Like(String value) {
            addCriterion("DAY10 like", value, "day10");
            return (Criteria) this;
        }

        public Criteria andDay10NotLike(String value) {
            addCriterion("DAY10 not like", value, "day10");
            return (Criteria) this;
        }

        public Criteria andDay10In(List<String> values) {
            addCriterion("DAY10 in", values, "day10");
            return (Criteria) this;
        }

        public Criteria andDay10NotIn(List<String> values) {
            addCriterion("DAY10 not in", values, "day10");
            return (Criteria) this;
        }

        public Criteria andDay10Between(String value1, String value2) {
            addCriterion("DAY10 between", value1, value2, "day10");
            return (Criteria) this;
        }

        public Criteria andDay10NotBetween(String value1, String value2) {
            addCriterion("DAY10 not between", value1, value2, "day10");
            return (Criteria) this;
        }

        public Criteria andDay11IsNull() {
            addCriterion("DAY11 is null");
            return (Criteria) this;
        }

        public Criteria andDay11IsNotNull() {
            addCriterion("DAY11 is not null");
            return (Criteria) this;
        }

        public Criteria andDay11EqualTo(String value) {
            addCriterion("DAY11 =", value, "day11");
            return (Criteria) this;
        }

        public Criteria andDay11NotEqualTo(String value) {
            addCriterion("DAY11 <>", value, "day11");
            return (Criteria) this;
        }

        public Criteria andDay11GreaterThan(String value) {
            addCriterion("DAY11 >", value, "day11");
            return (Criteria) this;
        }

        public Criteria andDay11GreaterThanOrEqualTo(String value) {
            addCriterion("DAY11 >=", value, "day11");
            return (Criteria) this;
        }

        public Criteria andDay11LessThan(String value) {
            addCriterion("DAY11 <", value, "day11");
            return (Criteria) this;
        }

        public Criteria andDay11LessThanOrEqualTo(String value) {
            addCriterion("DAY11 <=", value, "day11");
            return (Criteria) this;
        }

        public Criteria andDay11Like(String value) {
            addCriterion("DAY11 like", value, "day11");
            return (Criteria) this;
        }

        public Criteria andDay11NotLike(String value) {
            addCriterion("DAY11 not like", value, "day11");
            return (Criteria) this;
        }

        public Criteria andDay11In(List<String> values) {
            addCriterion("DAY11 in", values, "day11");
            return (Criteria) this;
        }

        public Criteria andDay11NotIn(List<String> values) {
            addCriterion("DAY11 not in", values, "day11");
            return (Criteria) this;
        }

        public Criteria andDay11Between(String value1, String value2) {
            addCriterion("DAY11 between", value1, value2, "day11");
            return (Criteria) this;
        }

        public Criteria andDay11NotBetween(String value1, String value2) {
            addCriterion("DAY11 not between", value1, value2, "day11");
            return (Criteria) this;
        }

        public Criteria andDay12IsNull() {
            addCriterion("DAY12 is null");
            return (Criteria) this;
        }

        public Criteria andDay12IsNotNull() {
            addCriterion("DAY12 is not null");
            return (Criteria) this;
        }

        public Criteria andDay12EqualTo(String value) {
            addCriterion("DAY12 =", value, "day12");
            return (Criteria) this;
        }

        public Criteria andDay12NotEqualTo(String value) {
            addCriterion("DAY12 <>", value, "day12");
            return (Criteria) this;
        }

        public Criteria andDay12GreaterThan(String value) {
            addCriterion("DAY12 >", value, "day12");
            return (Criteria) this;
        }

        public Criteria andDay12GreaterThanOrEqualTo(String value) {
            addCriterion("DAY12 >=", value, "day12");
            return (Criteria) this;
        }

        public Criteria andDay12LessThan(String value) {
            addCriterion("DAY12 <", value, "day12");
            return (Criteria) this;
        }

        public Criteria andDay12LessThanOrEqualTo(String value) {
            addCriterion("DAY12 <=", value, "day12");
            return (Criteria) this;
        }

        public Criteria andDay12Like(String value) {
            addCriterion("DAY12 like", value, "day12");
            return (Criteria) this;
        }

        public Criteria andDay12NotLike(String value) {
            addCriterion("DAY12 not like", value, "day12");
            return (Criteria) this;
        }

        public Criteria andDay12In(List<String> values) {
            addCriterion("DAY12 in", values, "day12");
            return (Criteria) this;
        }

        public Criteria andDay12NotIn(List<String> values) {
            addCriterion("DAY12 not in", values, "day12");
            return (Criteria) this;
        }

        public Criteria andDay12Between(String value1, String value2) {
            addCriterion("DAY12 between", value1, value2, "day12");
            return (Criteria) this;
        }

        public Criteria andDay12NotBetween(String value1, String value2) {
            addCriterion("DAY12 not between", value1, value2, "day12");
            return (Criteria) this;
        }

        public Criteria andDay13IsNull() {
            addCriterion("DAY13 is null");
            return (Criteria) this;
        }

        public Criteria andDay13IsNotNull() {
            addCriterion("DAY13 is not null");
            return (Criteria) this;
        }

        public Criteria andDay13EqualTo(String value) {
            addCriterion("DAY13 =", value, "day13");
            return (Criteria) this;
        }

        public Criteria andDay13NotEqualTo(String value) {
            addCriterion("DAY13 <>", value, "day13");
            return (Criteria) this;
        }

        public Criteria andDay13GreaterThan(String value) {
            addCriterion("DAY13 >", value, "day13");
            return (Criteria) this;
        }

        public Criteria andDay13GreaterThanOrEqualTo(String value) {
            addCriterion("DAY13 >=", value, "day13");
            return (Criteria) this;
        }

        public Criteria andDay13LessThan(String value) {
            addCriterion("DAY13 <", value, "day13");
            return (Criteria) this;
        }

        public Criteria andDay13LessThanOrEqualTo(String value) {
            addCriterion("DAY13 <=", value, "day13");
            return (Criteria) this;
        }

        public Criteria andDay13Like(String value) {
            addCriterion("DAY13 like", value, "day13");
            return (Criteria) this;
        }

        public Criteria andDay13NotLike(String value) {
            addCriterion("DAY13 not like", value, "day13");
            return (Criteria) this;
        }

        public Criteria andDay13In(List<String> values) {
            addCriterion("DAY13 in", values, "day13");
            return (Criteria) this;
        }

        public Criteria andDay13NotIn(List<String> values) {
            addCriterion("DAY13 not in", values, "day13");
            return (Criteria) this;
        }

        public Criteria andDay13Between(String value1, String value2) {
            addCriterion("DAY13 between", value1, value2, "day13");
            return (Criteria) this;
        }

        public Criteria andDay13NotBetween(String value1, String value2) {
            addCriterion("DAY13 not between", value1, value2, "day13");
            return (Criteria) this;
        }

        public Criteria andDay14IsNull() {
            addCriterion("DAY14 is null");
            return (Criteria) this;
        }

        public Criteria andDay14IsNotNull() {
            addCriterion("DAY14 is not null");
            return (Criteria) this;
        }

        public Criteria andDay14EqualTo(String value) {
            addCriterion("DAY14 =", value, "day14");
            return (Criteria) this;
        }

        public Criteria andDay14NotEqualTo(String value) {
            addCriterion("DAY14 <>", value, "day14");
            return (Criteria) this;
        }

        public Criteria andDay14GreaterThan(String value) {
            addCriterion("DAY14 >", value, "day14");
            return (Criteria) this;
        }

        public Criteria andDay14GreaterThanOrEqualTo(String value) {
            addCriterion("DAY14 >=", value, "day14");
            return (Criteria) this;
        }

        public Criteria andDay14LessThan(String value) {
            addCriterion("DAY14 <", value, "day14");
            return (Criteria) this;
        }

        public Criteria andDay14LessThanOrEqualTo(String value) {
            addCriterion("DAY14 <=", value, "day14");
            return (Criteria) this;
        }

        public Criteria andDay14Like(String value) {
            addCriterion("DAY14 like", value, "day14");
            return (Criteria) this;
        }

        public Criteria andDay14NotLike(String value) {
            addCriterion("DAY14 not like", value, "day14");
            return (Criteria) this;
        }

        public Criteria andDay14In(List<String> values) {
            addCriterion("DAY14 in", values, "day14");
            return (Criteria) this;
        }

        public Criteria andDay14NotIn(List<String> values) {
            addCriterion("DAY14 not in", values, "day14");
            return (Criteria) this;
        }

        public Criteria andDay14Between(String value1, String value2) {
            addCriterion("DAY14 between", value1, value2, "day14");
            return (Criteria) this;
        }

        public Criteria andDay14NotBetween(String value1, String value2) {
            addCriterion("DAY14 not between", value1, value2, "day14");
            return (Criteria) this;
        }

        public Criteria andDay15IsNull() {
            addCriterion("DAY15 is null");
            return (Criteria) this;
        }

        public Criteria andDay15IsNotNull() {
            addCriterion("DAY15 is not null");
            return (Criteria) this;
        }

        public Criteria andDay15EqualTo(String value) {
            addCriterion("DAY15 =", value, "day15");
            return (Criteria) this;
        }

        public Criteria andDay15NotEqualTo(String value) {
            addCriterion("DAY15 <>", value, "day15");
            return (Criteria) this;
        }

        public Criteria andDay15GreaterThan(String value) {
            addCriterion("DAY15 >", value, "day15");
            return (Criteria) this;
        }

        public Criteria andDay15GreaterThanOrEqualTo(String value) {
            addCriterion("DAY15 >=", value, "day15");
            return (Criteria) this;
        }

        public Criteria andDay15LessThan(String value) {
            addCriterion("DAY15 <", value, "day15");
            return (Criteria) this;
        }

        public Criteria andDay15LessThanOrEqualTo(String value) {
            addCriterion("DAY15 <=", value, "day15");
            return (Criteria) this;
        }

        public Criteria andDay15Like(String value) {
            addCriterion("DAY15 like", value, "day15");
            return (Criteria) this;
        }

        public Criteria andDay15NotLike(String value) {
            addCriterion("DAY15 not like", value, "day15");
            return (Criteria) this;
        }

        public Criteria andDay15In(List<String> values) {
            addCriterion("DAY15 in", values, "day15");
            return (Criteria) this;
        }

        public Criteria andDay15NotIn(List<String> values) {
            addCriterion("DAY15 not in", values, "day15");
            return (Criteria) this;
        }

        public Criteria andDay15Between(String value1, String value2) {
            addCriterion("DAY15 between", value1, value2, "day15");
            return (Criteria) this;
        }

        public Criteria andDay15NotBetween(String value1, String value2) {
            addCriterion("DAY15 not between", value1, value2, "day15");
            return (Criteria) this;
        }

        public Criteria andDay16IsNull() {
            addCriterion("DAY16 is null");
            return (Criteria) this;
        }

        public Criteria andDay16IsNotNull() {
            addCriterion("DAY16 is not null");
            return (Criteria) this;
        }

        public Criteria andDay16EqualTo(String value) {
            addCriterion("DAY16 =", value, "day16");
            return (Criteria) this;
        }

        public Criteria andDay16NotEqualTo(String value) {
            addCriterion("DAY16 <>", value, "day16");
            return (Criteria) this;
        }

        public Criteria andDay16GreaterThan(String value) {
            addCriterion("DAY16 >", value, "day16");
            return (Criteria) this;
        }

        public Criteria andDay16GreaterThanOrEqualTo(String value) {
            addCriterion("DAY16 >=", value, "day16");
            return (Criteria) this;
        }

        public Criteria andDay16LessThan(String value) {
            addCriterion("DAY16 <", value, "day16");
            return (Criteria) this;
        }

        public Criteria andDay16LessThanOrEqualTo(String value) {
            addCriterion("DAY16 <=", value, "day16");
            return (Criteria) this;
        }

        public Criteria andDay16Like(String value) {
            addCriterion("DAY16 like", value, "day16");
            return (Criteria) this;
        }

        public Criteria andDay16NotLike(String value) {
            addCriterion("DAY16 not like", value, "day16");
            return (Criteria) this;
        }

        public Criteria andDay16In(List<String> values) {
            addCriterion("DAY16 in", values, "day16");
            return (Criteria) this;
        }

        public Criteria andDay16NotIn(List<String> values) {
            addCriterion("DAY16 not in", values, "day16");
            return (Criteria) this;
        }

        public Criteria andDay16Between(String value1, String value2) {
            addCriterion("DAY16 between", value1, value2, "day16");
            return (Criteria) this;
        }

        public Criteria andDay16NotBetween(String value1, String value2) {
            addCriterion("DAY16 not between", value1, value2, "day16");
            return (Criteria) this;
        }

        public Criteria andDay17IsNull() {
            addCriterion("DAY17 is null");
            return (Criteria) this;
        }

        public Criteria andDay17IsNotNull() {
            addCriterion("DAY17 is not null");
            return (Criteria) this;
        }

        public Criteria andDay17EqualTo(String value) {
            addCriterion("DAY17 =", value, "day17");
            return (Criteria) this;
        }

        public Criteria andDay17NotEqualTo(String value) {
            addCriterion("DAY17 <>", value, "day17");
            return (Criteria) this;
        }

        public Criteria andDay17GreaterThan(String value) {
            addCriterion("DAY17 >", value, "day17");
            return (Criteria) this;
        }

        public Criteria andDay17GreaterThanOrEqualTo(String value) {
            addCriterion("DAY17 >=", value, "day17");
            return (Criteria) this;
        }

        public Criteria andDay17LessThan(String value) {
            addCriterion("DAY17 <", value, "day17");
            return (Criteria) this;
        }

        public Criteria andDay17LessThanOrEqualTo(String value) {
            addCriterion("DAY17 <=", value, "day17");
            return (Criteria) this;
        }

        public Criteria andDay17Like(String value) {
            addCriterion("DAY17 like", value, "day17");
            return (Criteria) this;
        }

        public Criteria andDay17NotLike(String value) {
            addCriterion("DAY17 not like", value, "day17");
            return (Criteria) this;
        }

        public Criteria andDay17In(List<String> values) {
            addCriterion("DAY17 in", values, "day17");
            return (Criteria) this;
        }

        public Criteria andDay17NotIn(List<String> values) {
            addCriterion("DAY17 not in", values, "day17");
            return (Criteria) this;
        }

        public Criteria andDay17Between(String value1, String value2) {
            addCriterion("DAY17 between", value1, value2, "day17");
            return (Criteria) this;
        }

        public Criteria andDay17NotBetween(String value1, String value2) {
            addCriterion("DAY17 not between", value1, value2, "day17");
            return (Criteria) this;
        }

        public Criteria andDay18IsNull() {
            addCriterion("DAY18 is null");
            return (Criteria) this;
        }

        public Criteria andDay18IsNotNull() {
            addCriterion("DAY18 is not null");
            return (Criteria) this;
        }

        public Criteria andDay18EqualTo(String value) {
            addCriterion("DAY18 =", value, "day18");
            return (Criteria) this;
        }

        public Criteria andDay18NotEqualTo(String value) {
            addCriterion("DAY18 <>", value, "day18");
            return (Criteria) this;
        }

        public Criteria andDay18GreaterThan(String value) {
            addCriterion("DAY18 >", value, "day18");
            return (Criteria) this;
        }

        public Criteria andDay18GreaterThanOrEqualTo(String value) {
            addCriterion("DAY18 >=", value, "day18");
            return (Criteria) this;
        }

        public Criteria andDay18LessThan(String value) {
            addCriterion("DAY18 <", value, "day18");
            return (Criteria) this;
        }

        public Criteria andDay18LessThanOrEqualTo(String value) {
            addCriterion("DAY18 <=", value, "day18");
            return (Criteria) this;
        }

        public Criteria andDay18Like(String value) {
            addCriterion("DAY18 like", value, "day18");
            return (Criteria) this;
        }

        public Criteria andDay18NotLike(String value) {
            addCriterion("DAY18 not like", value, "day18");
            return (Criteria) this;
        }

        public Criteria andDay18In(List<String> values) {
            addCriterion("DAY18 in", values, "day18");
            return (Criteria) this;
        }

        public Criteria andDay18NotIn(List<String> values) {
            addCriterion("DAY18 not in", values, "day18");
            return (Criteria) this;
        }

        public Criteria andDay18Between(String value1, String value2) {
            addCriterion("DAY18 between", value1, value2, "day18");
            return (Criteria) this;
        }

        public Criteria andDay18NotBetween(String value1, String value2) {
            addCriterion("DAY18 not between", value1, value2, "day18");
            return (Criteria) this;
        }

        public Criteria andDay19IsNull() {
            addCriterion("DAY19 is null");
            return (Criteria) this;
        }

        public Criteria andDay19IsNotNull() {
            addCriterion("DAY19 is not null");
            return (Criteria) this;
        }

        public Criteria andDay19EqualTo(String value) {
            addCriterion("DAY19 =", value, "day19");
            return (Criteria) this;
        }

        public Criteria andDay19NotEqualTo(String value) {
            addCriterion("DAY19 <>", value, "day19");
            return (Criteria) this;
        }

        public Criteria andDay19GreaterThan(String value) {
            addCriterion("DAY19 >", value, "day19");
            return (Criteria) this;
        }

        public Criteria andDay19GreaterThanOrEqualTo(String value) {
            addCriterion("DAY19 >=", value, "day19");
            return (Criteria) this;
        }

        public Criteria andDay19LessThan(String value) {
            addCriterion("DAY19 <", value, "day19");
            return (Criteria) this;
        }

        public Criteria andDay19LessThanOrEqualTo(String value) {
            addCriterion("DAY19 <=", value, "day19");
            return (Criteria) this;
        }

        public Criteria andDay19Like(String value) {
            addCriterion("DAY19 like", value, "day19");
            return (Criteria) this;
        }

        public Criteria andDay19NotLike(String value) {
            addCriterion("DAY19 not like", value, "day19");
            return (Criteria) this;
        }

        public Criteria andDay19In(List<String> values) {
            addCriterion("DAY19 in", values, "day19");
            return (Criteria) this;
        }

        public Criteria andDay19NotIn(List<String> values) {
            addCriterion("DAY19 not in", values, "day19");
            return (Criteria) this;
        }

        public Criteria andDay19Between(String value1, String value2) {
            addCriterion("DAY19 between", value1, value2, "day19");
            return (Criteria) this;
        }

        public Criteria andDay19NotBetween(String value1, String value2) {
            addCriterion("DAY19 not between", value1, value2, "day19");
            return (Criteria) this;
        }

        public Criteria andDay20IsNull() {
            addCriterion("DAY20 is null");
            return (Criteria) this;
        }

        public Criteria andDay20IsNotNull() {
            addCriterion("DAY20 is not null");
            return (Criteria) this;
        }

        public Criteria andDay20EqualTo(String value) {
            addCriterion("DAY20 =", value, "day20");
            return (Criteria) this;
        }

        public Criteria andDay20NotEqualTo(String value) {
            addCriterion("DAY20 <>", value, "day20");
            return (Criteria) this;
        }

        public Criteria andDay20GreaterThan(String value) {
            addCriterion("DAY20 >", value, "day20");
            return (Criteria) this;
        }

        public Criteria andDay20GreaterThanOrEqualTo(String value) {
            addCriterion("DAY20 >=", value, "day20");
            return (Criteria) this;
        }

        public Criteria andDay20LessThan(String value) {
            addCriterion("DAY20 <", value, "day20");
            return (Criteria) this;
        }

        public Criteria andDay20LessThanOrEqualTo(String value) {
            addCriterion("DAY20 <=", value, "day20");
            return (Criteria) this;
        }

        public Criteria andDay20Like(String value) {
            addCriterion("DAY20 like", value, "day20");
            return (Criteria) this;
        }

        public Criteria andDay20NotLike(String value) {
            addCriterion("DAY20 not like", value, "day20");
            return (Criteria) this;
        }

        public Criteria andDay20In(List<String> values) {
            addCriterion("DAY20 in", values, "day20");
            return (Criteria) this;
        }

        public Criteria andDay20NotIn(List<String> values) {
            addCriterion("DAY20 not in", values, "day20");
            return (Criteria) this;
        }

        public Criteria andDay20Between(String value1, String value2) {
            addCriterion("DAY20 between", value1, value2, "day20");
            return (Criteria) this;
        }

        public Criteria andDay20NotBetween(String value1, String value2) {
            addCriterion("DAY20 not between", value1, value2, "day20");
            return (Criteria) this;
        }

        public Criteria andDay21IsNull() {
            addCriterion("DAY21 is null");
            return (Criteria) this;
        }

        public Criteria andDay21IsNotNull() {
            addCriterion("DAY21 is not null");
            return (Criteria) this;
        }

        public Criteria andDay21EqualTo(String value) {
            addCriterion("DAY21 =", value, "day21");
            return (Criteria) this;
        }

        public Criteria andDay21NotEqualTo(String value) {
            addCriterion("DAY21 <>", value, "day21");
            return (Criteria) this;
        }

        public Criteria andDay21GreaterThan(String value) {
            addCriterion("DAY21 >", value, "day21");
            return (Criteria) this;
        }

        public Criteria andDay21GreaterThanOrEqualTo(String value) {
            addCriterion("DAY21 >=", value, "day21");
            return (Criteria) this;
        }

        public Criteria andDay21LessThan(String value) {
            addCriterion("DAY21 <", value, "day21");
            return (Criteria) this;
        }

        public Criteria andDay21LessThanOrEqualTo(String value) {
            addCriterion("DAY21 <=", value, "day21");
            return (Criteria) this;
        }

        public Criteria andDay21Like(String value) {
            addCriterion("DAY21 like", value, "day21");
            return (Criteria) this;
        }

        public Criteria andDay21NotLike(String value) {
            addCriterion("DAY21 not like", value, "day21");
            return (Criteria) this;
        }

        public Criteria andDay21In(List<String> values) {
            addCriterion("DAY21 in", values, "day21");
            return (Criteria) this;
        }

        public Criteria andDay21NotIn(List<String> values) {
            addCriterion("DAY21 not in", values, "day21");
            return (Criteria) this;
        }

        public Criteria andDay21Between(String value1, String value2) {
            addCriterion("DAY21 between", value1, value2, "day21");
            return (Criteria) this;
        }

        public Criteria andDay21NotBetween(String value1, String value2) {
            addCriterion("DAY21 not between", value1, value2, "day21");
            return (Criteria) this;
        }

        public Criteria andDay22IsNull() {
            addCriterion("DAY22 is null");
            return (Criteria) this;
        }

        public Criteria andDay22IsNotNull() {
            addCriterion("DAY22 is not null");
            return (Criteria) this;
        }

        public Criteria andDay22EqualTo(String value) {
            addCriterion("DAY22 =", value, "day22");
            return (Criteria) this;
        }

        public Criteria andDay22NotEqualTo(String value) {
            addCriterion("DAY22 <>", value, "day22");
            return (Criteria) this;
        }

        public Criteria andDay22GreaterThan(String value) {
            addCriterion("DAY22 >", value, "day22");
            return (Criteria) this;
        }

        public Criteria andDay22GreaterThanOrEqualTo(String value) {
            addCriterion("DAY22 >=", value, "day22");
            return (Criteria) this;
        }

        public Criteria andDay22LessThan(String value) {
            addCriterion("DAY22 <", value, "day22");
            return (Criteria) this;
        }

        public Criteria andDay22LessThanOrEqualTo(String value) {
            addCriterion("DAY22 <=", value, "day22");
            return (Criteria) this;
        }

        public Criteria andDay22Like(String value) {
            addCriterion("DAY22 like", value, "day22");
            return (Criteria) this;
        }

        public Criteria andDay22NotLike(String value) {
            addCriterion("DAY22 not like", value, "day22");
            return (Criteria) this;
        }

        public Criteria andDay22In(List<String> values) {
            addCriterion("DAY22 in", values, "day22");
            return (Criteria) this;
        }

        public Criteria andDay22NotIn(List<String> values) {
            addCriterion("DAY22 not in", values, "day22");
            return (Criteria) this;
        }

        public Criteria andDay22Between(String value1, String value2) {
            addCriterion("DAY22 between", value1, value2, "day22");
            return (Criteria) this;
        }

        public Criteria andDay22NotBetween(String value1, String value2) {
            addCriterion("DAY22 not between", value1, value2, "day22");
            return (Criteria) this;
        }

        public Criteria andDay23IsNull() {
            addCriterion("DAY23 is null");
            return (Criteria) this;
        }

        public Criteria andDay23IsNotNull() {
            addCriterion("DAY23 is not null");
            return (Criteria) this;
        }

        public Criteria andDay23EqualTo(String value) {
            addCriterion("DAY23 =", value, "day23");
            return (Criteria) this;
        }

        public Criteria andDay23NotEqualTo(String value) {
            addCriterion("DAY23 <>", value, "day23");
            return (Criteria) this;
        }

        public Criteria andDay23GreaterThan(String value) {
            addCriterion("DAY23 >", value, "day23");
            return (Criteria) this;
        }

        public Criteria andDay23GreaterThanOrEqualTo(String value) {
            addCriterion("DAY23 >=", value, "day23");
            return (Criteria) this;
        }

        public Criteria andDay23LessThan(String value) {
            addCriterion("DAY23 <", value, "day23");
            return (Criteria) this;
        }

        public Criteria andDay23LessThanOrEqualTo(String value) {
            addCriterion("DAY23 <=", value, "day23");
            return (Criteria) this;
        }

        public Criteria andDay23Like(String value) {
            addCriterion("DAY23 like", value, "day23");
            return (Criteria) this;
        }

        public Criteria andDay23NotLike(String value) {
            addCriterion("DAY23 not like", value, "day23");
            return (Criteria) this;
        }

        public Criteria andDay23In(List<String> values) {
            addCriterion("DAY23 in", values, "day23");
            return (Criteria) this;
        }

        public Criteria andDay23NotIn(List<String> values) {
            addCriterion("DAY23 not in", values, "day23");
            return (Criteria) this;
        }

        public Criteria andDay23Between(String value1, String value2) {
            addCriterion("DAY23 between", value1, value2, "day23");
            return (Criteria) this;
        }

        public Criteria andDay23NotBetween(String value1, String value2) {
            addCriterion("DAY23 not between", value1, value2, "day23");
            return (Criteria) this;
        }

        public Criteria andDay24IsNull() {
            addCriterion("DAY24 is null");
            return (Criteria) this;
        }

        public Criteria andDay24IsNotNull() {
            addCriterion("DAY24 is not null");
            return (Criteria) this;
        }

        public Criteria andDay24EqualTo(String value) {
            addCriterion("DAY24 =", value, "day24");
            return (Criteria) this;
        }

        public Criteria andDay24NotEqualTo(String value) {
            addCriterion("DAY24 <>", value, "day24");
            return (Criteria) this;
        }

        public Criteria andDay24GreaterThan(String value) {
            addCriterion("DAY24 >", value, "day24");
            return (Criteria) this;
        }

        public Criteria andDay24GreaterThanOrEqualTo(String value) {
            addCriterion("DAY24 >=", value, "day24");
            return (Criteria) this;
        }

        public Criteria andDay24LessThan(String value) {
            addCriterion("DAY24 <", value, "day24");
            return (Criteria) this;
        }

        public Criteria andDay24LessThanOrEqualTo(String value) {
            addCriterion("DAY24 <=", value, "day24");
            return (Criteria) this;
        }

        public Criteria andDay24Like(String value) {
            addCriterion("DAY24 like", value, "day24");
            return (Criteria) this;
        }

        public Criteria andDay24NotLike(String value) {
            addCriterion("DAY24 not like", value, "day24");
            return (Criteria) this;
        }

        public Criteria andDay24In(List<String> values) {
            addCriterion("DAY24 in", values, "day24");
            return (Criteria) this;
        }

        public Criteria andDay24NotIn(List<String> values) {
            addCriterion("DAY24 not in", values, "day24");
            return (Criteria) this;
        }

        public Criteria andDay24Between(String value1, String value2) {
            addCriterion("DAY24 between", value1, value2, "day24");
            return (Criteria) this;
        }

        public Criteria andDay24NotBetween(String value1, String value2) {
            addCriterion("DAY24 not between", value1, value2, "day24");
            return (Criteria) this;
        }

        public Criteria andDay25IsNull() {
            addCriterion("DAY25 is null");
            return (Criteria) this;
        }

        public Criteria andDay25IsNotNull() {
            addCriterion("DAY25 is not null");
            return (Criteria) this;
        }

        public Criteria andDay25EqualTo(String value) {
            addCriterion("DAY25 =", value, "day25");
            return (Criteria) this;
        }

        public Criteria andDay25NotEqualTo(String value) {
            addCriterion("DAY25 <>", value, "day25");
            return (Criteria) this;
        }

        public Criteria andDay25GreaterThan(String value) {
            addCriterion("DAY25 >", value, "day25");
            return (Criteria) this;
        }

        public Criteria andDay25GreaterThanOrEqualTo(String value) {
            addCriterion("DAY25 >=", value, "day25");
            return (Criteria) this;
        }

        public Criteria andDay25LessThan(String value) {
            addCriterion("DAY25 <", value, "day25");
            return (Criteria) this;
        }

        public Criteria andDay25LessThanOrEqualTo(String value) {
            addCriterion("DAY25 <=", value, "day25");
            return (Criteria) this;
        }

        public Criteria andDay25Like(String value) {
            addCriterion("DAY25 like", value, "day25");
            return (Criteria) this;
        }

        public Criteria andDay25NotLike(String value) {
            addCriterion("DAY25 not like", value, "day25");
            return (Criteria) this;
        }

        public Criteria andDay25In(List<String> values) {
            addCriterion("DAY25 in", values, "day25");
            return (Criteria) this;
        }

        public Criteria andDay25NotIn(List<String> values) {
            addCriterion("DAY25 not in", values, "day25");
            return (Criteria) this;
        }

        public Criteria andDay25Between(String value1, String value2) {
            addCriterion("DAY25 between", value1, value2, "day25");
            return (Criteria) this;
        }

        public Criteria andDay25NotBetween(String value1, String value2) {
            addCriterion("DAY25 not between", value1, value2, "day25");
            return (Criteria) this;
        }

        public Criteria andDay26IsNull() {
            addCriterion("DAY26 is null");
            return (Criteria) this;
        }

        public Criteria andDay26IsNotNull() {
            addCriterion("DAY26 is not null");
            return (Criteria) this;
        }

        public Criteria andDay26EqualTo(String value) {
            addCriterion("DAY26 =", value, "day26");
            return (Criteria) this;
        }

        public Criteria andDay26NotEqualTo(String value) {
            addCriterion("DAY26 <>", value, "day26");
            return (Criteria) this;
        }

        public Criteria andDay26GreaterThan(String value) {
            addCriterion("DAY26 >", value, "day26");
            return (Criteria) this;
        }

        public Criteria andDay26GreaterThanOrEqualTo(String value) {
            addCriterion("DAY26 >=", value, "day26");
            return (Criteria) this;
        }

        public Criteria andDay26LessThan(String value) {
            addCriterion("DAY26 <", value, "day26");
            return (Criteria) this;
        }

        public Criteria andDay26LessThanOrEqualTo(String value) {
            addCriterion("DAY26 <=", value, "day26");
            return (Criteria) this;
        }

        public Criteria andDay26Like(String value) {
            addCriterion("DAY26 like", value, "day26");
            return (Criteria) this;
        }

        public Criteria andDay26NotLike(String value) {
            addCriterion("DAY26 not like", value, "day26");
            return (Criteria) this;
        }

        public Criteria andDay26In(List<String> values) {
            addCriterion("DAY26 in", values, "day26");
            return (Criteria) this;
        }

        public Criteria andDay26NotIn(List<String> values) {
            addCriterion("DAY26 not in", values, "day26");
            return (Criteria) this;
        }

        public Criteria andDay26Between(String value1, String value2) {
            addCriterion("DAY26 between", value1, value2, "day26");
            return (Criteria) this;
        }

        public Criteria andDay26NotBetween(String value1, String value2) {
            addCriterion("DAY26 not between", value1, value2, "day26");
            return (Criteria) this;
        }

        public Criteria andDay27IsNull() {
            addCriterion("DAY27 is null");
            return (Criteria) this;
        }

        public Criteria andDay27IsNotNull() {
            addCriterion("DAY27 is not null");
            return (Criteria) this;
        }

        public Criteria andDay27EqualTo(String value) {
            addCriterion("DAY27 =", value, "day27");
            return (Criteria) this;
        }

        public Criteria andDay27NotEqualTo(String value) {
            addCriterion("DAY27 <>", value, "day27");
            return (Criteria) this;
        }

        public Criteria andDay27GreaterThan(String value) {
            addCriterion("DAY27 >", value, "day27");
            return (Criteria) this;
        }

        public Criteria andDay27GreaterThanOrEqualTo(String value) {
            addCriterion("DAY27 >=", value, "day27");
            return (Criteria) this;
        }

        public Criteria andDay27LessThan(String value) {
            addCriterion("DAY27 <", value, "day27");
            return (Criteria) this;
        }

        public Criteria andDay27LessThanOrEqualTo(String value) {
            addCriterion("DAY27 <=", value, "day27");
            return (Criteria) this;
        }

        public Criteria andDay27Like(String value) {
            addCriterion("DAY27 like", value, "day27");
            return (Criteria) this;
        }

        public Criteria andDay27NotLike(String value) {
            addCriterion("DAY27 not like", value, "day27");
            return (Criteria) this;
        }

        public Criteria andDay27In(List<String> values) {
            addCriterion("DAY27 in", values, "day27");
            return (Criteria) this;
        }

        public Criteria andDay27NotIn(List<String> values) {
            addCriterion("DAY27 not in", values, "day27");
            return (Criteria) this;
        }

        public Criteria andDay27Between(String value1, String value2) {
            addCriterion("DAY27 between", value1, value2, "day27");
            return (Criteria) this;
        }

        public Criteria andDay27NotBetween(String value1, String value2) {
            addCriterion("DAY27 not between", value1, value2, "day27");
            return (Criteria) this;
        }

        public Criteria andDay28IsNull() {
            addCriterion("DAY28 is null");
            return (Criteria) this;
        }

        public Criteria andDay28IsNotNull() {
            addCriterion("DAY28 is not null");
            return (Criteria) this;
        }

        public Criteria andDay28EqualTo(String value) {
            addCriterion("DAY28 =", value, "day28");
            return (Criteria) this;
        }

        public Criteria andDay28NotEqualTo(String value) {
            addCriterion("DAY28 <>", value, "day28");
            return (Criteria) this;
        }

        public Criteria andDay28GreaterThan(String value) {
            addCriterion("DAY28 >", value, "day28");
            return (Criteria) this;
        }

        public Criteria andDay28GreaterThanOrEqualTo(String value) {
            addCriterion("DAY28 >=", value, "day28");
            return (Criteria) this;
        }

        public Criteria andDay28LessThan(String value) {
            addCriterion("DAY28 <", value, "day28");
            return (Criteria) this;
        }

        public Criteria andDay28LessThanOrEqualTo(String value) {
            addCriterion("DAY28 <=", value, "day28");
            return (Criteria) this;
        }

        public Criteria andDay28Like(String value) {
            addCriterion("DAY28 like", value, "day28");
            return (Criteria) this;
        }

        public Criteria andDay28NotLike(String value) {
            addCriterion("DAY28 not like", value, "day28");
            return (Criteria) this;
        }

        public Criteria andDay28In(List<String> values) {
            addCriterion("DAY28 in", values, "day28");
            return (Criteria) this;
        }

        public Criteria andDay28NotIn(List<String> values) {
            addCriterion("DAY28 not in", values, "day28");
            return (Criteria) this;
        }

        public Criteria andDay28Between(String value1, String value2) {
            addCriterion("DAY28 between", value1, value2, "day28");
            return (Criteria) this;
        }

        public Criteria andDay28NotBetween(String value1, String value2) {
            addCriterion("DAY28 not between", value1, value2, "day28");
            return (Criteria) this;
        }

        public Criteria andDay29IsNull() {
            addCriterion("DAY29 is null");
            return (Criteria) this;
        }

        public Criteria andDay29IsNotNull() {
            addCriterion("DAY29 is not null");
            return (Criteria) this;
        }

        public Criteria andDay29EqualTo(String value) {
            addCriterion("DAY29 =", value, "day29");
            return (Criteria) this;
        }

        public Criteria andDay29NotEqualTo(String value) {
            addCriterion("DAY29 <>", value, "day29");
            return (Criteria) this;
        }

        public Criteria andDay29GreaterThan(String value) {
            addCriterion("DAY29 >", value, "day29");
            return (Criteria) this;
        }

        public Criteria andDay29GreaterThanOrEqualTo(String value) {
            addCriterion("DAY29 >=", value, "day29");
            return (Criteria) this;
        }

        public Criteria andDay29LessThan(String value) {
            addCriterion("DAY29 <", value, "day29");
            return (Criteria) this;
        }

        public Criteria andDay29LessThanOrEqualTo(String value) {
            addCriterion("DAY29 <=", value, "day29");
            return (Criteria) this;
        }

        public Criteria andDay29Like(String value) {
            addCriterion("DAY29 like", value, "day29");
            return (Criteria) this;
        }

        public Criteria andDay29NotLike(String value) {
            addCriterion("DAY29 not like", value, "day29");
            return (Criteria) this;
        }

        public Criteria andDay29In(List<String> values) {
            addCriterion("DAY29 in", values, "day29");
            return (Criteria) this;
        }

        public Criteria andDay29NotIn(List<String> values) {
            addCriterion("DAY29 not in", values, "day29");
            return (Criteria) this;
        }

        public Criteria andDay29Between(String value1, String value2) {
            addCriterion("DAY29 between", value1, value2, "day29");
            return (Criteria) this;
        }

        public Criteria andDay29NotBetween(String value1, String value2) {
            addCriterion("DAY29 not between", value1, value2, "day29");
            return (Criteria) this;
        }

        public Criteria andDay30IsNull() {
            addCriterion("DAY30 is null");
            return (Criteria) this;
        }

        public Criteria andDay30IsNotNull() {
            addCriterion("DAY30 is not null");
            return (Criteria) this;
        }

        public Criteria andDay30EqualTo(String value) {
            addCriterion("DAY30 =", value, "day30");
            return (Criteria) this;
        }

        public Criteria andDay30NotEqualTo(String value) {
            addCriterion("DAY30 <>", value, "day30");
            return (Criteria) this;
        }

        public Criteria andDay30GreaterThan(String value) {
            addCriterion("DAY30 >", value, "day30");
            return (Criteria) this;
        }

        public Criteria andDay30GreaterThanOrEqualTo(String value) {
            addCriterion("DAY30 >=", value, "day30");
            return (Criteria) this;
        }

        public Criteria andDay30LessThan(String value) {
            addCriterion("DAY30 <", value, "day30");
            return (Criteria) this;
        }

        public Criteria andDay30LessThanOrEqualTo(String value) {
            addCriterion("DAY30 <=", value, "day30");
            return (Criteria) this;
        }

        public Criteria andDay30Like(String value) {
            addCriterion("DAY30 like", value, "day30");
            return (Criteria) this;
        }

        public Criteria andDay30NotLike(String value) {
            addCriterion("DAY30 not like", value, "day30");
            return (Criteria) this;
        }

        public Criteria andDay30In(List<String> values) {
            addCriterion("DAY30 in", values, "day30");
            return (Criteria) this;
        }

        public Criteria andDay30NotIn(List<String> values) {
            addCriterion("DAY30 not in", values, "day30");
            return (Criteria) this;
        }

        public Criteria andDay30Between(String value1, String value2) {
            addCriterion("DAY30 between", value1, value2, "day30");
            return (Criteria) this;
        }

        public Criteria andDay30NotBetween(String value1, String value2) {
            addCriterion("DAY30 not between", value1, value2, "day30");
            return (Criteria) this;
        }

        public Criteria andDay31IsNull() {
            addCriterion("DAY31 is null");
            return (Criteria) this;
        }

        public Criteria andDay31IsNotNull() {
            addCriterion("DAY31 is not null");
            return (Criteria) this;
        }

        public Criteria andDay31EqualTo(String value) {
            addCriterion("DAY31 =", value, "day31");
            return (Criteria) this;
        }

        public Criteria andDay31NotEqualTo(String value) {
            addCriterion("DAY31 <>", value, "day31");
            return (Criteria) this;
        }

        public Criteria andDay31GreaterThan(String value) {
            addCriterion("DAY31 >", value, "day31");
            return (Criteria) this;
        }

        public Criteria andDay31GreaterThanOrEqualTo(String value) {
            addCriterion("DAY31 >=", value, "day31");
            return (Criteria) this;
        }

        public Criteria andDay31LessThan(String value) {
            addCriterion("DAY31 <", value, "day31");
            return (Criteria) this;
        }

        public Criteria andDay31LessThanOrEqualTo(String value) {
            addCriterion("DAY31 <=", value, "day31");
            return (Criteria) this;
        }

        public Criteria andDay31Like(String value) {
            addCriterion("DAY31 like", value, "day31");
            return (Criteria) this;
        }

        public Criteria andDay31NotLike(String value) {
            addCriterion("DAY31 not like", value, "day31");
            return (Criteria) this;
        }

        public Criteria andDay31In(List<String> values) {
            addCriterion("DAY31 in", values, "day31");
            return (Criteria) this;
        }

        public Criteria andDay31NotIn(List<String> values) {
            addCriterion("DAY31 not in", values, "day31");
            return (Criteria) this;
        }

        public Criteria andDay31Between(String value1, String value2) {
            addCriterion("DAY31 between", value1, value2, "day31");
            return (Criteria) this;
        }

        public Criteria andDay31NotBetween(String value1, String value2) {
            addCriterion("DAY31 not between", value1, value2, "day31");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andZhjfscoreIsNull() {
            addCriterion("ZHJFSCORE is null");
            return (Criteria) this;
        }

        public Criteria andZhjfscoreIsNotNull() {
            addCriterion("ZHJFSCORE is not null");
            return (Criteria) this;
        }

        public Criteria andZhjfscoreEqualTo(Double value) {
            addCriterion("ZHJFSCORE =", value, "zhjfscore");
            return (Criteria) this;
        }

        public Criteria andZhjfscoreNotEqualTo(Double value) {
            addCriterion("ZHJFSCORE <>", value, "zhjfscore");
            return (Criteria) this;
        }

        public Criteria andZhjfscoreGreaterThan(Double value) {
            addCriterion("ZHJFSCORE >", value, "zhjfscore");
            return (Criteria) this;
        }

        public Criteria andZhjfscoreGreaterThanOrEqualTo(Double value) {
            addCriterion("ZHJFSCORE >=", value, "zhjfscore");
            return (Criteria) this;
        }

        public Criteria andZhjfscoreLessThan(Double value) {
            addCriterion("ZHJFSCORE <", value, "zhjfscore");
            return (Criteria) this;
        }

        public Criteria andZhjfscoreLessThanOrEqualTo(Double value) {
            addCriterion("ZHJFSCORE <=", value, "zhjfscore");
            return (Criteria) this;
        }

        public Criteria andZhjfscoreIn(List<Double> values) {
            addCriterion("ZHJFSCORE in", values, "zhjfscore");
            return (Criteria) this;
        }

        public Criteria andZhjfscoreNotIn(List<Double> values) {
            addCriterion("ZHJFSCORE not in", values, "zhjfscore");
            return (Criteria) this;
        }

        public Criteria andZhjfscoreBetween(Double value1, Double value2) {
            addCriterion("ZHJFSCORE between", value1, value2, "zhjfscore");
            return (Criteria) this;
        }

        public Criteria andZhjfscoreNotBetween(Double value1, Double value2) {
            addCriterion("ZHJFSCORE not between", value1, value2, "zhjfscore");
            return (Criteria) this;
        }

        public Criteria andLdgzscoreIsNull() {
            addCriterion("LDGZSCORE is null");
            return (Criteria) this;
        }

        public Criteria andLdgzscoreIsNotNull() {
            addCriterion("LDGZSCORE is not null");
            return (Criteria) this;
        }

        public Criteria andLdgzscoreEqualTo(Double value) {
            addCriterion("LDGZSCORE =", value, "ldgzscore");
            return (Criteria) this;
        }

        public Criteria andLdgzscoreNotEqualTo(Double value) {
            addCriterion("LDGZSCORE <>", value, "ldgzscore");
            return (Criteria) this;
        }

        public Criteria andLdgzscoreGreaterThan(Double value) {
            addCriterion("LDGZSCORE >", value, "ldgzscore");
            return (Criteria) this;
        }

        public Criteria andLdgzscoreGreaterThanOrEqualTo(Double value) {
            addCriterion("LDGZSCORE >=", value, "ldgzscore");
            return (Criteria) this;
        }

        public Criteria andLdgzscoreLessThan(Double value) {
            addCriterion("LDGZSCORE <", value, "ldgzscore");
            return (Criteria) this;
        }

        public Criteria andLdgzscoreLessThanOrEqualTo(Double value) {
            addCriterion("LDGZSCORE <=", value, "ldgzscore");
            return (Criteria) this;
        }

        public Criteria andLdgzscoreIn(List<Double> values) {
            addCriterion("LDGZSCORE in", values, "ldgzscore");
            return (Criteria) this;
        }

        public Criteria andLdgzscoreNotIn(List<Double> values) {
            addCriterion("LDGZSCORE not in", values, "ldgzscore");
            return (Criteria) this;
        }

        public Criteria andLdgzscoreBetween(Double value1, Double value2) {
            addCriterion("LDGZSCORE between", value1, value2, "ldgzscore");
            return (Criteria) this;
        }

        public Criteria andLdgzscoreNotBetween(Double value1, Double value2) {
            addCriterion("LDGZSCORE not between", value1, value2, "ldgzscore");
            return (Criteria) this;
        }

        public Criteria andXzjfscoreIsNull() {
            addCriterion("XZJFSCORE is null");
            return (Criteria) this;
        }

        public Criteria andXzjfscoreIsNotNull() {
            addCriterion("XZJFSCORE is not null");
            return (Criteria) this;
        }

        public Criteria andXzjfscoreEqualTo(Double value) {
            addCriterion("XZJFSCORE =", value, "xzjfscore");
            return (Criteria) this;
        }

        public Criteria andXzjfscoreNotEqualTo(Double value) {
            addCriterion("XZJFSCORE <>", value, "xzjfscore");
            return (Criteria) this;
        }

        public Criteria andXzjfscoreGreaterThan(Double value) {
            addCriterion("XZJFSCORE >", value, "xzjfscore");
            return (Criteria) this;
        }

        public Criteria andXzjfscoreGreaterThanOrEqualTo(Double value) {
            addCriterion("XZJFSCORE >=", value, "xzjfscore");
            return (Criteria) this;
        }

        public Criteria andXzjfscoreLessThan(Double value) {
            addCriterion("XZJFSCORE <", value, "xzjfscore");
            return (Criteria) this;
        }

        public Criteria andXzjfscoreLessThanOrEqualTo(Double value) {
            addCriterion("XZJFSCORE <=", value, "xzjfscore");
            return (Criteria) this;
        }

        public Criteria andXzjfscoreIn(List<Double> values) {
            addCriterion("XZJFSCORE in", values, "xzjfscore");
            return (Criteria) this;
        }

        public Criteria andXzjfscoreNotIn(List<Double> values) {
            addCriterion("XZJFSCORE not in", values, "xzjfscore");
            return (Criteria) this;
        }

        public Criteria andXzjfscoreBetween(Double value1, Double value2) {
            addCriterion("XZJFSCORE between", value1, value2, "xzjfscore");
            return (Criteria) this;
        }

        public Criteria andXzjfscoreNotBetween(Double value1, Double value2) {
            addCriterion("XZJFSCORE not between", value1, value2, "xzjfscore");
            return (Criteria) this;
        }

        public Criteria andXzkfscoreIsNull() {
            addCriterion("XZKFSCORE is null");
            return (Criteria) this;
        }

        public Criteria andXzkfscoreIsNotNull() {
            addCriterion("XZKFSCORE is not null");
            return (Criteria) this;
        }

        public Criteria andXzkfscoreEqualTo(Double value) {
            addCriterion("XZKFSCORE =", value, "xzkfscore");
            return (Criteria) this;
        }

        public Criteria andXzkfscoreNotEqualTo(Double value) {
            addCriterion("XZKFSCORE <>", value, "xzkfscore");
            return (Criteria) this;
        }

        public Criteria andXzkfscoreGreaterThan(Double value) {
            addCriterion("XZKFSCORE >", value, "xzkfscore");
            return (Criteria) this;
        }

        public Criteria andXzkfscoreGreaterThanOrEqualTo(Double value) {
            addCriterion("XZKFSCORE >=", value, "xzkfscore");
            return (Criteria) this;
        }

        public Criteria andXzkfscoreLessThan(Double value) {
            addCriterion("XZKFSCORE <", value, "xzkfscore");
            return (Criteria) this;
        }

        public Criteria andXzkfscoreLessThanOrEqualTo(Double value) {
            addCriterion("XZKFSCORE <=", value, "xzkfscore");
            return (Criteria) this;
        }

        public Criteria andXzkfscoreIn(List<Double> values) {
            addCriterion("XZKFSCORE in", values, "xzkfscore");
            return (Criteria) this;
        }

        public Criteria andXzkfscoreNotIn(List<Double> values) {
            addCriterion("XZKFSCORE not in", values, "xzkfscore");
            return (Criteria) this;
        }

        public Criteria andXzkfscoreBetween(Double value1, Double value2) {
            addCriterion("XZKFSCORE between", value1, value2, "xzkfscore");
            return (Criteria) this;
        }

        public Criteria andXzkfscoreNotBetween(Double value1, Double value2) {
            addCriterion("XZKFSCORE not between", value1, value2, "xzkfscore");
            return (Criteria) this;
        }

        public Criteria andKhscoreIsNull() {
            addCriterion("KHSCORE is null");
            return (Criteria) this;
        }

        public Criteria andKhscoreIsNotNull() {
            addCriterion("KHSCORE is not null");
            return (Criteria) this;
        }

        public Criteria andKhscoreEqualTo(Double value) {
            addCriterion("KHSCORE =", value, "khscore");
            return (Criteria) this;
        }

        public Criteria andKhscoreNotEqualTo(Double value) {
            addCriterion("KHSCORE <>", value, "khscore");
            return (Criteria) this;
        }

        public Criteria andKhscoreGreaterThan(Double value) {
            addCriterion("KHSCORE >", value, "khscore");
            return (Criteria) this;
        }

        public Criteria andKhscoreGreaterThanOrEqualTo(Double value) {
            addCriterion("KHSCORE >=", value, "khscore");
            return (Criteria) this;
        }

        public Criteria andKhscoreLessThan(Double value) {
            addCriterion("KHSCORE <", value, "khscore");
            return (Criteria) this;
        }

        public Criteria andKhscoreLessThanOrEqualTo(Double value) {
            addCriterion("KHSCORE <=", value, "khscore");
            return (Criteria) this;
        }

        public Criteria andKhscoreIn(List<Double> values) {
            addCriterion("KHSCORE in", values, "khscore");
            return (Criteria) this;
        }

        public Criteria andKhscoreNotIn(List<Double> values) {
            addCriterion("KHSCORE not in", values, "khscore");
            return (Criteria) this;
        }

        public Criteria andKhscoreBetween(Double value1, Double value2) {
            addCriterion("KHSCORE between", value1, value2, "khscore");
            return (Criteria) this;
        }

        public Criteria andKhscoreNotBetween(Double value1, Double value2) {
            addCriterion("KHSCORE not between", value1, value2, "khscore");
            return (Criteria) this;
        }

        public Criteria andCcjfscoreIsNull() {
            addCriterion("CCJFSCORE is null");
            return (Criteria) this;
        }

        public Criteria andCcjfscoreIsNotNull() {
            addCriterion("CCJFSCORE is not null");
            return (Criteria) this;
        }

        public Criteria andCcjfscoreEqualTo(Double value) {
            addCriterion("CCJFSCORE =", value, "ccjfscore");
            return (Criteria) this;
        }

        public Criteria andCcjfscoreNotEqualTo(Double value) {
            addCriterion("CCJFSCORE <>", value, "ccjfscore");
            return (Criteria) this;
        }

        public Criteria andCcjfscoreGreaterThan(Double value) {
            addCriterion("CCJFSCORE >", value, "ccjfscore");
            return (Criteria) this;
        }

        public Criteria andCcjfscoreGreaterThanOrEqualTo(Double value) {
            addCriterion("CCJFSCORE >=", value, "ccjfscore");
            return (Criteria) this;
        }

        public Criteria andCcjfscoreLessThan(Double value) {
            addCriterion("CCJFSCORE <", value, "ccjfscore");
            return (Criteria) this;
        }

        public Criteria andCcjfscoreLessThanOrEqualTo(Double value) {
            addCriterion("CCJFSCORE <=", value, "ccjfscore");
            return (Criteria) this;
        }

        public Criteria andCcjfscoreIn(List<Double> values) {
            addCriterion("CCJFSCORE in", values, "ccjfscore");
            return (Criteria) this;
        }

        public Criteria andCcjfscoreNotIn(List<Double> values) {
            addCriterion("CCJFSCORE not in", values, "ccjfscore");
            return (Criteria) this;
        }

        public Criteria andCcjfscoreBetween(Double value1, Double value2) {
            addCriterion("CCJFSCORE between", value1, value2, "ccjfscore");
            return (Criteria) this;
        }

        public Criteria andCcjfscoreNotBetween(Double value1, Double value2) {
            addCriterion("CCJFSCORE not between", value1, value2, "ccjfscore");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("STATE is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("STATE is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(String value) {
            addCriterion("STATE =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(String value) {
            addCriterion("STATE <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(String value) {
            addCriterion("STATE >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("STATE >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(String value) {
            addCriterion("STATE <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("STATE <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(String value) {
            addCriterion("STATE like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(String value) {
            addCriterion("STATE not like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<String> values) {
            addCriterion("STATE in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<String> values) {
            addCriterion("STATE not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(String value1, String value2) {
            addCriterion("STATE between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("STATE not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andWorkdaynumIsNull() {
            addCriterion("WORKDAYNUM is null");
            return (Criteria) this;
        }

        public Criteria andWorkdaynumIsNotNull() {
            addCriterion("WORKDAYNUM is not null");
            return (Criteria) this;
        }

        public Criteria andWorkdaynumEqualTo(BigDecimal value) {
            addCriterion("WORKDAYNUM =", value, "workdaynum");
            return (Criteria) this;
        }

        public Criteria andWorkdaynumNotEqualTo(BigDecimal value) {
            addCriterion("WORKDAYNUM <>", value, "workdaynum");
            return (Criteria) this;
        }

        public Criteria andWorkdaynumGreaterThan(BigDecimal value) {
            addCriterion("WORKDAYNUM >", value, "workdaynum");
            return (Criteria) this;
        }

        public Criteria andWorkdaynumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("WORKDAYNUM >=", value, "workdaynum");
            return (Criteria) this;
        }

        public Criteria andWorkdaynumLessThan(BigDecimal value) {
            addCriterion("WORKDAYNUM <", value, "workdaynum");
            return (Criteria) this;
        }

        public Criteria andWorkdaynumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("WORKDAYNUM <=", value, "workdaynum");
            return (Criteria) this;
        }

        public Criteria andWorkdaynumIn(List<BigDecimal> values) {
            addCriterion("WORKDAYNUM in", values, "workdaynum");
            return (Criteria) this;
        }

        public Criteria andWorkdaynumNotIn(List<BigDecimal> values) {
            addCriterion("WORKDAYNUM not in", values, "workdaynum");
            return (Criteria) this;
        }

        public Criteria andWorkdaynumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("WORKDAYNUM between", value1, value2, "workdaynum");
            return (Criteria) this;
        }

        public Criteria andWorkdaynumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("WORKDAYNUM not between", value1, value2, "workdaynum");
            return (Criteria) this;
        }

        public Criteria andLastscoreIsNull() {
            addCriterion("LASTSCORE is null");
            return (Criteria) this;
        }

        public Criteria andLastscoreIsNotNull() {
            addCriterion("LASTSCORE is not null");
            return (Criteria) this;
        }

        public Criteria andLastscoreEqualTo(BigDecimal value) {
            addCriterion("LASTSCORE =", value, "lastscore");
            return (Criteria) this;
        }

        public Criteria andLastscoreNotEqualTo(BigDecimal value) {
            addCriterion("LASTSCORE <>", value, "lastscore");
            return (Criteria) this;
        }

        public Criteria andLastscoreGreaterThan(BigDecimal value) {
            addCriterion("LASTSCORE >", value, "lastscore");
            return (Criteria) this;
        }

        public Criteria andLastscoreGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("LASTSCORE >=", value, "lastscore");
            return (Criteria) this;
        }

        public Criteria andLastscoreLessThan(BigDecimal value) {
            addCriterion("LASTSCORE <", value, "lastscore");
            return (Criteria) this;
        }

        public Criteria andLastscoreLessThanOrEqualTo(BigDecimal value) {
            addCriterion("LASTSCORE <=", value, "lastscore");
            return (Criteria) this;
        }

        public Criteria andLastscoreIn(List<BigDecimal> values) {
            addCriterion("LASTSCORE in", values, "lastscore");
            return (Criteria) this;
        }

        public Criteria andLastscoreNotIn(List<BigDecimal> values) {
            addCriterion("LASTSCORE not in", values, "lastscore");
            return (Criteria) this;
        }

        public Criteria andLastscoreBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("LASTSCORE between", value1, value2, "lastscore");
            return (Criteria) this;
        }

        public Criteria andLastscoreNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("LASTSCORE not between", value1, value2, "lastscore");
            return (Criteria) this;
        }

        public Criteria andText1IsNull() {
            addCriterion("TEXT1 is null");
            return (Criteria) this;
        }

        public Criteria andText1IsNotNull() {
            addCriterion("TEXT1 is not null");
            return (Criteria) this;
        }

        public Criteria andText1EqualTo(String value) {
            addCriterion("TEXT1 =", value, "text1");
            return (Criteria) this;
        }

        public Criteria andText1NotEqualTo(String value) {
            addCriterion("TEXT1 <>", value, "text1");
            return (Criteria) this;
        }

        public Criteria andText1GreaterThan(String value) {
            addCriterion("TEXT1 >", value, "text1");
            return (Criteria) this;
        }

        public Criteria andText1GreaterThanOrEqualTo(String value) {
            addCriterion("TEXT1 >=", value, "text1");
            return (Criteria) this;
        }

        public Criteria andText1LessThan(String value) {
            addCriterion("TEXT1 <", value, "text1");
            return (Criteria) this;
        }

        public Criteria andText1LessThanOrEqualTo(String value) {
            addCriterion("TEXT1 <=", value, "text1");
            return (Criteria) this;
        }

        public Criteria andText1Like(String value) {
            addCriterion("TEXT1 like", value, "text1");
            return (Criteria) this;
        }

        public Criteria andText1NotLike(String value) {
            addCriterion("TEXT1 not like", value, "text1");
            return (Criteria) this;
        }

        public Criteria andText1In(List<String> values) {
            addCriterion("TEXT1 in", values, "text1");
            return (Criteria) this;
        }

        public Criteria andText1NotIn(List<String> values) {
            addCriterion("TEXT1 not in", values, "text1");
            return (Criteria) this;
        }

        public Criteria andText1Between(String value1, String value2) {
            addCriterion("TEXT1 between", value1, value2, "text1");
            return (Criteria) this;
        }

        public Criteria andText1NotBetween(String value1, String value2) {
            addCriterion("TEXT1 not between", value1, value2, "text1");
            return (Criteria) this;
        }

        public Criteria andText2IsNull() {
            addCriterion("TEXT2 is null");
            return (Criteria) this;
        }

        public Criteria andText2IsNotNull() {
            addCriterion("TEXT2 is not null");
            return (Criteria) this;
        }

        public Criteria andText2EqualTo(String value) {
            addCriterion("TEXT2 =", value, "text2");
            return (Criteria) this;
        }

        public Criteria andText2NotEqualTo(String value) {
            addCriterion("TEXT2 <>", value, "text2");
            return (Criteria) this;
        }

        public Criteria andText2GreaterThan(String value) {
            addCriterion("TEXT2 >", value, "text2");
            return (Criteria) this;
        }

        public Criteria andText2GreaterThanOrEqualTo(String value) {
            addCriterion("TEXT2 >=", value, "text2");
            return (Criteria) this;
        }

        public Criteria andText2LessThan(String value) {
            addCriterion("TEXT2 <", value, "text2");
            return (Criteria) this;
        }

        public Criteria andText2LessThanOrEqualTo(String value) {
            addCriterion("TEXT2 <=", value, "text2");
            return (Criteria) this;
        }

        public Criteria andText2Like(String value) {
            addCriterion("TEXT2 like", value, "text2");
            return (Criteria) this;
        }

        public Criteria andText2NotLike(String value) {
            addCriterion("TEXT2 not like", value, "text2");
            return (Criteria) this;
        }

        public Criteria andText2In(List<String> values) {
            addCriterion("TEXT2 in", values, "text2");
            return (Criteria) this;
        }

        public Criteria andText2NotIn(List<String> values) {
            addCriterion("TEXT2 not in", values, "text2");
            return (Criteria) this;
        }

        public Criteria andText2Between(String value1, String value2) {
            addCriterion("TEXT2 between", value1, value2, "text2");
            return (Criteria) this;
        }

        public Criteria andText2NotBetween(String value1, String value2) {
            addCriterion("TEXT2 not between", value1, value2, "text2");
            return (Criteria) this;
        }

        public Criteria andText3IsNull() {
            addCriterion("TEXT3 is null");
            return (Criteria) this;
        }

        public Criteria andText3IsNotNull() {
            addCriterion("TEXT3 is not null");
            return (Criteria) this;
        }

        public Criteria andText3EqualTo(String value) {
            addCriterion("TEXT3 =", value, "text3");
            return (Criteria) this;
        }

        public Criteria andText3NotEqualTo(String value) {
            addCriterion("TEXT3 <>", value, "text3");
            return (Criteria) this;
        }

        public Criteria andText3GreaterThan(String value) {
            addCriterion("TEXT3 >", value, "text3");
            return (Criteria) this;
        }

        public Criteria andText3GreaterThanOrEqualTo(String value) {
            addCriterion("TEXT3 >=", value, "text3");
            return (Criteria) this;
        }

        public Criteria andText3LessThan(String value) {
            addCriterion("TEXT3 <", value, "text3");
            return (Criteria) this;
        }

        public Criteria andText3LessThanOrEqualTo(String value) {
            addCriterion("TEXT3 <=", value, "text3");
            return (Criteria) this;
        }

        public Criteria andText3Like(String value) {
            addCriterion("TEXT3 like", value, "text3");
            return (Criteria) this;
        }

        public Criteria andText3NotLike(String value) {
            addCriterion("TEXT3 not like", value, "text3");
            return (Criteria) this;
        }

        public Criteria andText3In(List<String> values) {
            addCriterion("TEXT3 in", values, "text3");
            return (Criteria) this;
        }

        public Criteria andText3NotIn(List<String> values) {
            addCriterion("TEXT3 not in", values, "text3");
            return (Criteria) this;
        }

        public Criteria andText3Between(String value1, String value2) {
            addCriterion("TEXT3 between", value1, value2, "text3");
            return (Criteria) this;
        }

        public Criteria andText3NotBetween(String value1, String value2) {
            addCriterion("TEXT3 not between", value1, value2, "text3");
            return (Criteria) this;
        }

        public Criteria andText4IsNull() {
            addCriterion("TEXT4 is null");
            return (Criteria) this;
        }

        public Criteria andText4IsNotNull() {
            addCriterion("TEXT4 is not null");
            return (Criteria) this;
        }

        public Criteria andText4EqualTo(String value) {
            addCriterion("TEXT4 =", value, "text4");
            return (Criteria) this;
        }

        public Criteria andText4NotEqualTo(String value) {
            addCriterion("TEXT4 <>", value, "text4");
            return (Criteria) this;
        }

        public Criteria andText4GreaterThan(String value) {
            addCriterion("TEXT4 >", value, "text4");
            return (Criteria) this;
        }

        public Criteria andText4GreaterThanOrEqualTo(String value) {
            addCriterion("TEXT4 >=", value, "text4");
            return (Criteria) this;
        }

        public Criteria andText4LessThan(String value) {
            addCriterion("TEXT4 <", value, "text4");
            return (Criteria) this;
        }

        public Criteria andText4LessThanOrEqualTo(String value) {
            addCriterion("TEXT4 <=", value, "text4");
            return (Criteria) this;
        }

        public Criteria andText4Like(String value) {
            addCriterion("TEXT4 like", value, "text4");
            return (Criteria) this;
        }

        public Criteria andText4NotLike(String value) {
            addCriterion("TEXT4 not like", value, "text4");
            return (Criteria) this;
        }

        public Criteria andText4In(List<String> values) {
            addCriterion("TEXT4 in", values, "text4");
            return (Criteria) this;
        }

        public Criteria andText4NotIn(List<String> values) {
            addCriterion("TEXT4 not in", values, "text4");
            return (Criteria) this;
        }

        public Criteria andText4Between(String value1, String value2) {
            addCriterion("TEXT4 between", value1, value2, "text4");
            return (Criteria) this;
        }

        public Criteria andText4NotBetween(String value1, String value2) {
            addCriterion("TEXT4 not between", value1, value2, "text4");
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