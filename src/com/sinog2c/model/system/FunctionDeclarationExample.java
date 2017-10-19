package com.sinog2c.model.system;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FunctionDeclarationExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FunctionDeclarationExample() {
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

        public Criteria andMenuidIsNull() {
            addCriterion("MENUID is null");
            return (Criteria) this;
        }

        public Criteria andMenuidIsNotNull() {
            addCriterion("MENUID is not null");
            return (Criteria) this;
        }

        public Criteria andMenuidEqualTo(String value) {
            addCriterion("MENUID =", value, "menuid");
            return (Criteria) this;
        }

        public Criteria andMenuidNotEqualTo(String value) {
            addCriterion("MENUID <>", value, "menuid");
            return (Criteria) this;
        }

        public Criteria andMenuidGreaterThan(String value) {
            addCriterion("MENUID >", value, "menuid");
            return (Criteria) this;
        }

        public Criteria andMenuidGreaterThanOrEqualTo(String value) {
            addCriterion("MENUID >=", value, "menuid");
            return (Criteria) this;
        }

        public Criteria andMenuidLessThan(String value) {
            addCriterion("MENUID <", value, "menuid");
            return (Criteria) this;
        }

        public Criteria andMenuidLessThanOrEqualTo(String value) {
            addCriterion("MENUID <=", value, "menuid");
            return (Criteria) this;
        }

        public Criteria andMenuidLike(String value) {
            addCriterion("MENUID like", value, "menuid");
            return (Criteria) this;
        }

        public Criteria andMenuidNotLike(String value) {
            addCriterion("MENUID not like", value, "menuid");
            return (Criteria) this;
        }

        public Criteria andMenuidIn(List<String> values) {
            addCriterion("MENUID in", values, "menuid");
            return (Criteria) this;
        }

        public Criteria andMenuidNotIn(List<String> values) {
            addCriterion("MENUID not in", values, "menuid");
            return (Criteria) this;
        }

        public Criteria andMenuidBetween(String value1, String value2) {
            addCriterion("MENUID between", value1, value2, "menuid");
            return (Criteria) this;
        }

        public Criteria andMenuidNotBetween(String value1, String value2) {
            addCriterion("MENUID not between", value1, value2, "menuid");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("TITLE is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("TITLE is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("TITLE =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("TITLE <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("TITLE >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("TITLE >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("TITLE <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("TITLE <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("TITLE like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("TITLE not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("TITLE in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("TITLE not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("TITLE between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("TITLE not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andSnIsNull() {
            addCriterion("SN is null");
            return (Criteria) this;
        }

        public Criteria andSnIsNotNull() {
            addCriterion("SN is not null");
            return (Criteria) this;
        }

        public Criteria andSnEqualTo(BigDecimal value) {
            addCriterion("SN =", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnNotEqualTo(BigDecimal value) {
            addCriterion("SN <>", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnGreaterThan(BigDecimal value) {
            addCriterion("SN >", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SN >=", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnLessThan(BigDecimal value) {
            addCriterion("SN <", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SN <=", value, "sn");
            return (Criteria) this;
        }

        public Criteria andSnIn(List<BigDecimal> values) {
            addCriterion("SN in", values, "sn");
            return (Criteria) this;
        }

        public Criteria andSnNotIn(List<BigDecimal> values) {
            addCriterion("SN not in", values, "sn");
            return (Criteria) this;
        }

        public Criteria andSnBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SN between", value1, value2, "sn");
            return (Criteria) this;
        }

        public Criteria andSnNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SN not between", value1, value2, "sn");
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