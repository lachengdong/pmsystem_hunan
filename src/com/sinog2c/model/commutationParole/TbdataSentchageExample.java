package com.sinog2c.model.commutationParole;

import java.util.ArrayList;
import java.util.List;

public class TbdataSentchageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbdataSentchageExample() {
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

        public Criteria andCourtsanctionIsNull() {
            addCriterion("COURTSANCTION is null");
            return (Criteria) this;
        }

        public Criteria andCourtsanctionIsNotNull() {
            addCriterion("COURTSANCTION is not null");
            return (Criteria) this;
        }

        public Criteria andCourtsanctionEqualTo(String value) {
            addCriterion("COURTSANCTION =", value, "courtsanction");
            return (Criteria) this;
        }

        public Criteria andCourtsanctionNotEqualTo(String value) {
            addCriterion("COURTSANCTION <>", value, "courtsanction");
            return (Criteria) this;
        }

        public Criteria andCourtsanctionGreaterThan(String value) {
            addCriterion("COURTSANCTION >", value, "courtsanction");
            return (Criteria) this;
        }

        public Criteria andCourtsanctionGreaterThanOrEqualTo(String value) {
            addCriterion("COURTSANCTION >=", value, "courtsanction");
            return (Criteria) this;
        }

        public Criteria andCourtsanctionLessThan(String value) {
            addCriterion("COURTSANCTION <", value, "courtsanction");
            return (Criteria) this;
        }

        public Criteria andCourtsanctionLessThanOrEqualTo(String value) {
            addCriterion("COURTSANCTION <=", value, "courtsanction");
            return (Criteria) this;
        }

        public Criteria andCourtsanctionLike(String value) {
            addCriterion("COURTSANCTION like", value, "courtsanction");
            return (Criteria) this;
        }

        public Criteria andCourtsanctionNotLike(String value) {
            addCriterion("COURTSANCTION not like", value, "courtsanction");
            return (Criteria) this;
        }

        public Criteria andCourtsanctionIn(List<String> values) {
            addCriterion("COURTSANCTION in", values, "courtsanction");
            return (Criteria) this;
        }

        public Criteria andCourtsanctionNotIn(List<String> values) {
            addCriterion("COURTSANCTION not in", values, "courtsanction");
            return (Criteria) this;
        }

        public Criteria andCourtsanctionBetween(String value1, String value2) {
            addCriterion("COURTSANCTION between", value1, value2, "courtsanction");
            return (Criteria) this;
        }

        public Criteria andCourtsanctionNotBetween(String value1, String value2) {
            addCriterion("COURTSANCTION not between", value1, value2, "courtsanction");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNull() {
            addCriterion("CATEGORY is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            addCriterion("CATEGORY is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(String value) {
            addCriterion("CATEGORY =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(String value) {
            addCriterion("CATEGORY <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(String value) {
            addCriterion("CATEGORY >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("CATEGORY >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(String value) {
            addCriterion("CATEGORY <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(String value) {
            addCriterion("CATEGORY <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLike(String value) {
            addCriterion("CATEGORY like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotLike(String value) {
            addCriterion("CATEGORY not like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<String> values) {
            addCriterion("CATEGORY in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<String> values) {
            addCriterion("CATEGORY not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(String value1, String value2) {
            addCriterion("CATEGORY between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(String value1, String value2) {
            addCriterion("CATEGORY not between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCourtnameIsNull() {
            addCriterion("COURTNAME is null");
            return (Criteria) this;
        }

        public Criteria andCourtnameIsNotNull() {
            addCriterion("COURTNAME is not null");
            return (Criteria) this;
        }

        public Criteria andCourtnameEqualTo(String value) {
            addCriterion("COURTNAME =", value, "courtname");
            return (Criteria) this;
        }

        public Criteria andCourtnameNotEqualTo(String value) {
            addCriterion("COURTNAME <>", value, "courtname");
            return (Criteria) this;
        }

        public Criteria andCourtnameGreaterThan(String value) {
            addCriterion("COURTNAME >", value, "courtname");
            return (Criteria) this;
        }

        public Criteria andCourtnameGreaterThanOrEqualTo(String value) {
            addCriterion("COURTNAME >=", value, "courtname");
            return (Criteria) this;
        }

        public Criteria andCourtnameLessThan(String value) {
            addCriterion("COURTNAME <", value, "courtname");
            return (Criteria) this;
        }

        public Criteria andCourtnameLessThanOrEqualTo(String value) {
            addCriterion("COURTNAME <=", value, "courtname");
            return (Criteria) this;
        }

        public Criteria andCourtnameLike(String value) {
            addCriterion("COURTNAME like", value, "courtname");
            return (Criteria) this;
        }

        public Criteria andCourtnameNotLike(String value) {
            addCriterion("COURTNAME not like", value, "courtname");
            return (Criteria) this;
        }

        public Criteria andCourtnameIn(List<String> values) {
            addCriterion("COURTNAME in", values, "courtname");
            return (Criteria) this;
        }

        public Criteria andCourtnameNotIn(List<String> values) {
            addCriterion("COURTNAME not in", values, "courtname");
            return (Criteria) this;
        }

        public Criteria andCourtnameBetween(String value1, String value2) {
            addCriterion("COURTNAME between", value1, value2, "courtname");
            return (Criteria) this;
        }

        public Criteria andCourtnameNotBetween(String value1, String value2) {
            addCriterion("COURTNAME not between", value1, value2, "courtname");
            return (Criteria) this;
        }

        public Criteria andCourttitleIsNull() {
            addCriterion("COURTTITLE is null");
            return (Criteria) this;
        }

        public Criteria andCourttitleIsNotNull() {
            addCriterion("COURTTITLE is not null");
            return (Criteria) this;
        }

        public Criteria andCourttitleEqualTo(String value) {
            addCriterion("COURTTITLE =", value, "courttitle");
            return (Criteria) this;
        }

        public Criteria andCourttitleNotEqualTo(String value) {
            addCriterion("COURTTITLE <>", value, "courttitle");
            return (Criteria) this;
        }

        public Criteria andCourttitleGreaterThan(String value) {
            addCriterion("COURTTITLE >", value, "courttitle");
            return (Criteria) this;
        }

        public Criteria andCourttitleGreaterThanOrEqualTo(String value) {
            addCriterion("COURTTITLE >=", value, "courttitle");
            return (Criteria) this;
        }

        public Criteria andCourttitleLessThan(String value) {
            addCriterion("COURTTITLE <", value, "courttitle");
            return (Criteria) this;
        }

        public Criteria andCourttitleLessThanOrEqualTo(String value) {
            addCriterion("COURTTITLE <=", value, "courttitle");
            return (Criteria) this;
        }

        public Criteria andCourttitleLike(String value) {
            addCriterion("COURTTITLE like", value, "courttitle");
            return (Criteria) this;
        }

        public Criteria andCourttitleNotLike(String value) {
            addCriterion("COURTTITLE not like", value, "courttitle");
            return (Criteria) this;
        }

        public Criteria andCourttitleIn(List<String> values) {
            addCriterion("COURTTITLE in", values, "courttitle");
            return (Criteria) this;
        }

        public Criteria andCourttitleNotIn(List<String> values) {
            addCriterion("COURTTITLE not in", values, "courttitle");
            return (Criteria) this;
        }

        public Criteria andCourttitleBetween(String value1, String value2) {
            addCriterion("COURTTITLE between", value1, value2, "courttitle");
            return (Criteria) this;
        }

        public Criteria andCourttitleNotBetween(String value1, String value2) {
            addCriterion("COURTTITLE not between", value1, value2, "courttitle");
            return (Criteria) this;
        }

        public Criteria andCourtyearIsNull() {
            addCriterion("COURTYEAR is null");
            return (Criteria) this;
        }

        public Criteria andCourtyearIsNotNull() {
            addCriterion("COURTYEAR is not null");
            return (Criteria) this;
        }

        public Criteria andCourtyearEqualTo(String value) {
            addCriterion("COURTYEAR =", value, "courtyear");
            return (Criteria) this;
        }

        public Criteria andCourtyearNotEqualTo(String value) {
            addCriterion("COURTYEAR <>", value, "courtyear");
            return (Criteria) this;
        }

        public Criteria andCourtyearGreaterThan(String value) {
            addCriterion("COURTYEAR >", value, "courtyear");
            return (Criteria) this;
        }

        public Criteria andCourtyearGreaterThanOrEqualTo(String value) {
            addCriterion("COURTYEAR >=", value, "courtyear");
            return (Criteria) this;
        }

        public Criteria andCourtyearLessThan(String value) {
            addCriterion("COURTYEAR <", value, "courtyear");
            return (Criteria) this;
        }

        public Criteria andCourtyearLessThanOrEqualTo(String value) {
            addCriterion("COURTYEAR <=", value, "courtyear");
            return (Criteria) this;
        }

        public Criteria andCourtyearLike(String value) {
            addCriterion("COURTYEAR like", value, "courtyear");
            return (Criteria) this;
        }

        public Criteria andCourtyearNotLike(String value) {
            addCriterion("COURTYEAR not like", value, "courtyear");
            return (Criteria) this;
        }

        public Criteria andCourtyearIn(List<String> values) {
            addCriterion("COURTYEAR in", values, "courtyear");
            return (Criteria) this;
        }

        public Criteria andCourtyearNotIn(List<String> values) {
            addCriterion("COURTYEAR not in", values, "courtyear");
            return (Criteria) this;
        }

        public Criteria andCourtyearBetween(String value1, String value2) {
            addCriterion("COURTYEAR between", value1, value2, "courtyear");
            return (Criteria) this;
        }

        public Criteria andCourtyearNotBetween(String value1, String value2) {
            addCriterion("COURTYEAR not between", value1, value2, "courtyear");
            return (Criteria) this;
        }

        public Criteria andCourtshortIsNull() {
            addCriterion("COURTSHORT is null");
            return (Criteria) this;
        }

        public Criteria andCourtshortIsNotNull() {
            addCriterion("COURTSHORT is not null");
            return (Criteria) this;
        }

        public Criteria andCourtshortEqualTo(String value) {
            addCriterion("COURTSHORT =", value, "courtshort");
            return (Criteria) this;
        }

        public Criteria andCourtshortNotEqualTo(String value) {
            addCriterion("COURTSHORT <>", value, "courtshort");
            return (Criteria) this;
        }

        public Criteria andCourtshortGreaterThan(String value) {
            addCriterion("COURTSHORT >", value, "courtshort");
            return (Criteria) this;
        }

        public Criteria andCourtshortGreaterThanOrEqualTo(String value) {
            addCriterion("COURTSHORT >=", value, "courtshort");
            return (Criteria) this;
        }

        public Criteria andCourtshortLessThan(String value) {
            addCriterion("COURTSHORT <", value, "courtshort");
            return (Criteria) this;
        }

        public Criteria andCourtshortLessThanOrEqualTo(String value) {
            addCriterion("COURTSHORT <=", value, "courtshort");
            return (Criteria) this;
        }

        public Criteria andCourtshortLike(String value) {
            addCriterion("COURTSHORT like", value, "courtshort");
            return (Criteria) this;
        }

        public Criteria andCourtshortNotLike(String value) {
            addCriterion("COURTSHORT not like", value, "courtshort");
            return (Criteria) this;
        }

        public Criteria andCourtshortIn(List<String> values) {
            addCriterion("COURTSHORT in", values, "courtshort");
            return (Criteria) this;
        }

        public Criteria andCourtshortNotIn(List<String> values) {
            addCriterion("COURTSHORT not in", values, "courtshort");
            return (Criteria) this;
        }

        public Criteria andCourtshortBetween(String value1, String value2) {
            addCriterion("COURTSHORT between", value1, value2, "courtshort");
            return (Criteria) this;
        }

        public Criteria andCourtshortNotBetween(String value1, String value2) {
            addCriterion("COURTSHORT not between", value1, value2, "courtshort");
            return (Criteria) this;
        }

        public Criteria andCourtsnIsNull() {
            addCriterion("COURTSN is null");
            return (Criteria) this;
        }

        public Criteria andCourtsnIsNotNull() {
            addCriterion("COURTSN is not null");
            return (Criteria) this;
        }

        public Criteria andCourtsnEqualTo(String value) {
            addCriterion("COURTSN =", value, "courtsn");
            return (Criteria) this;
        }

        public Criteria andCourtsnNotEqualTo(String value) {
            addCriterion("COURTSN <>", value, "courtsn");
            return (Criteria) this;
        }

        public Criteria andCourtsnGreaterThan(String value) {
            addCriterion("COURTSN >", value, "courtsn");
            return (Criteria) this;
        }

        public Criteria andCourtsnGreaterThanOrEqualTo(String value) {
            addCriterion("COURTSN >=", value, "courtsn");
            return (Criteria) this;
        }

        public Criteria andCourtsnLessThan(String value) {
            addCriterion("COURTSN <", value, "courtsn");
            return (Criteria) this;
        }

        public Criteria andCourtsnLessThanOrEqualTo(String value) {
            addCriterion("COURTSN <=", value, "courtsn");
            return (Criteria) this;
        }

        public Criteria andCourtsnLike(String value) {
            addCriterion("COURTSN like", value, "courtsn");
            return (Criteria) this;
        }

        public Criteria andCourtsnNotLike(String value) {
            addCriterion("COURTSN not like", value, "courtsn");
            return (Criteria) this;
        }

        public Criteria andCourtsnIn(List<String> values) {
            addCriterion("COURTSN in", values, "courtsn");
            return (Criteria) this;
        }

        public Criteria andCourtsnNotIn(List<String> values) {
            addCriterion("COURTSN not in", values, "courtsn");
            return (Criteria) this;
        }

        public Criteria andCourtsnBetween(String value1, String value2) {
            addCriterion("COURTSN between", value1, value2, "courtsn");
            return (Criteria) this;
        }

        public Criteria andCourtsnNotBetween(String value1, String value2) {
            addCriterion("COURTSN not between", value1, value2, "courtsn");
            return (Criteria) this;
        }

        public Criteria andCourtchangeIsNull() {
            addCriterion("COURTCHANGE is null");
            return (Criteria) this;
        }

        public Criteria andCourtchangeIsNotNull() {
            addCriterion("COURTCHANGE is not null");
            return (Criteria) this;
        }

        public Criteria andCourtchangeEqualTo(String value) {
            addCriterion("COURTCHANGE =", value, "courtchange");
            return (Criteria) this;
        }

        public Criteria andCourtchangeNotEqualTo(String value) {
            addCriterion("COURTCHANGE <>", value, "courtchange");
            return (Criteria) this;
        }

        public Criteria andCourtchangeGreaterThan(String value) {
            addCriterion("COURTCHANGE >", value, "courtchange");
            return (Criteria) this;
        }

        public Criteria andCourtchangeGreaterThanOrEqualTo(String value) {
            addCriterion("COURTCHANGE >=", value, "courtchange");
            return (Criteria) this;
        }

        public Criteria andCourtchangeLessThan(String value) {
            addCriterion("COURTCHANGE <", value, "courtchange");
            return (Criteria) this;
        }

        public Criteria andCourtchangeLessThanOrEqualTo(String value) {
            addCriterion("COURTCHANGE <=", value, "courtchange");
            return (Criteria) this;
        }

        public Criteria andCourtchangeLike(String value) {
            addCriterion("COURTCHANGE like", value, "courtchange");
            return (Criteria) this;
        }

        public Criteria andCourtchangeNotLike(String value) {
            addCriterion("COURTCHANGE not like", value, "courtchange");
            return (Criteria) this;
        }

        public Criteria andCourtchangeIn(List<String> values) {
            addCriterion("COURTCHANGE in", values, "courtchange");
            return (Criteria) this;
        }

        public Criteria andCourtchangeNotIn(List<String> values) {
            addCriterion("COURTCHANGE not in", values, "courtchange");
            return (Criteria) this;
        }

        public Criteria andCourtchangeBetween(String value1, String value2) {
            addCriterion("COURTCHANGE between", value1, value2, "courtchange");
            return (Criteria) this;
        }

        public Criteria andCourtchangeNotBetween(String value1, String value2) {
            addCriterion("COURTCHANGE not between", value1, value2, "courtchange");
            return (Criteria) this;
        }

        public Criteria andSentenceIsNull() {
            addCriterion("SENTENCE is null");
            return (Criteria) this;
        }

        public Criteria andSentenceIsNotNull() {
            addCriterion("SENTENCE is not null");
            return (Criteria) this;
        }

        public Criteria andSentenceEqualTo(String value) {
            addCriterion("SENTENCE =", value, "sentence");
            return (Criteria) this;
        }

        public Criteria andSentenceNotEqualTo(String value) {
            addCriterion("SENTENCE <>", value, "sentence");
            return (Criteria) this;
        }

        public Criteria andSentenceGreaterThan(String value) {
            addCriterion("SENTENCE >", value, "sentence");
            return (Criteria) this;
        }

        public Criteria andSentenceGreaterThanOrEqualTo(String value) {
            addCriterion("SENTENCE >=", value, "sentence");
            return (Criteria) this;
        }

        public Criteria andSentenceLessThan(String value) {
            addCriterion("SENTENCE <", value, "sentence");
            return (Criteria) this;
        }

        public Criteria andSentenceLessThanOrEqualTo(String value) {
            addCriterion("SENTENCE <=", value, "sentence");
            return (Criteria) this;
        }

        public Criteria andSentenceLike(String value) {
            addCriterion("SENTENCE like", value, "sentence");
            return (Criteria) this;
        }

        public Criteria andSentenceNotLike(String value) {
            addCriterion("SENTENCE not like", value, "sentence");
            return (Criteria) this;
        }

        public Criteria andSentenceIn(List<String> values) {
            addCriterion("SENTENCE in", values, "sentence");
            return (Criteria) this;
        }

        public Criteria andSentenceNotIn(List<String> values) {
            addCriterion("SENTENCE not in", values, "sentence");
            return (Criteria) this;
        }

        public Criteria andSentenceBetween(String value1, String value2) {
            addCriterion("SENTENCE between", value1, value2, "sentence");
            return (Criteria) this;
        }

        public Criteria andSentenceNotBetween(String value1, String value2) {
            addCriterion("SENTENCE not between", value1, value2, "sentence");
            return (Criteria) this;
        }

        public Criteria andCourtchangefromIsNull() {
            addCriterion("COURTCHANGEFROM is null");
            return (Criteria) this;
        }

        public Criteria andCourtchangefromIsNotNull() {
            addCriterion("COURTCHANGEFROM is not null");
            return (Criteria) this;
        }

        public Criteria andCourtchangefromEqualTo(String value) {
            addCriterion("COURTCHANGEFROM =", value, "courtchangefrom");
            return (Criteria) this;
        }

        public Criteria andCourtchangefromNotEqualTo(String value) {
            addCriterion("COURTCHANGEFROM <>", value, "courtchangefrom");
            return (Criteria) this;
        }

        public Criteria andCourtchangefromGreaterThan(String value) {
            addCriterion("COURTCHANGEFROM >", value, "courtchangefrom");
            return (Criteria) this;
        }

        public Criteria andCourtchangefromGreaterThanOrEqualTo(String value) {
            addCriterion("COURTCHANGEFROM >=", value, "courtchangefrom");
            return (Criteria) this;
        }

        public Criteria andCourtchangefromLessThan(String value) {
            addCriterion("COURTCHANGEFROM <", value, "courtchangefrom");
            return (Criteria) this;
        }

        public Criteria andCourtchangefromLessThanOrEqualTo(String value) {
            addCriterion("COURTCHANGEFROM <=", value, "courtchangefrom");
            return (Criteria) this;
        }

        public Criteria andCourtchangefromLike(String value) {
            addCriterion("COURTCHANGEFROM like", value, "courtchangefrom");
            return (Criteria) this;
        }

        public Criteria andCourtchangefromNotLike(String value) {
            addCriterion("COURTCHANGEFROM not like", value, "courtchangefrom");
            return (Criteria) this;
        }

        public Criteria andCourtchangefromIn(List<String> values) {
            addCriterion("COURTCHANGEFROM in", values, "courtchangefrom");
            return (Criteria) this;
        }

        public Criteria andCourtchangefromNotIn(List<String> values) {
            addCriterion("COURTCHANGEFROM not in", values, "courtchangefrom");
            return (Criteria) this;
        }

        public Criteria andCourtchangefromBetween(String value1, String value2) {
            addCriterion("COURTCHANGEFROM between", value1, value2, "courtchangefrom");
            return (Criteria) this;
        }

        public Criteria andCourtchangefromNotBetween(String value1, String value2) {
            addCriterion("COURTCHANGEFROM not between", value1, value2, "courtchangefrom");
            return (Criteria) this;
        }

        public Criteria andCourtchangetoIsNull() {
            addCriterion("COURTCHANGETO is null");
            return (Criteria) this;
        }

        public Criteria andCourtchangetoIsNotNull() {
            addCriterion("COURTCHANGETO is not null");
            return (Criteria) this;
        }

        public Criteria andCourtchangetoEqualTo(String value) {
            addCriterion("COURTCHANGETO =", value, "courtchangeto");
            return (Criteria) this;
        }

        public Criteria andCourtchangetoNotEqualTo(String value) {
            addCriterion("COURTCHANGETO <>", value, "courtchangeto");
            return (Criteria) this;
        }

        public Criteria andCourtchangetoGreaterThan(String value) {
            addCriterion("COURTCHANGETO >", value, "courtchangeto");
            return (Criteria) this;
        }

        public Criteria andCourtchangetoGreaterThanOrEqualTo(String value) {
            addCriterion("COURTCHANGETO >=", value, "courtchangeto");
            return (Criteria) this;
        }

        public Criteria andCourtchangetoLessThan(String value) {
            addCriterion("COURTCHANGETO <", value, "courtchangeto");
            return (Criteria) this;
        }

        public Criteria andCourtchangetoLessThanOrEqualTo(String value) {
            addCriterion("COURTCHANGETO <=", value, "courtchangeto");
            return (Criteria) this;
        }

        public Criteria andCourtchangetoLike(String value) {
            addCriterion("COURTCHANGETO like", value, "courtchangeto");
            return (Criteria) this;
        }

        public Criteria andCourtchangetoNotLike(String value) {
            addCriterion("COURTCHANGETO not like", value, "courtchangeto");
            return (Criteria) this;
        }

        public Criteria andCourtchangetoIn(List<String> values) {
            addCriterion("COURTCHANGETO in", values, "courtchangeto");
            return (Criteria) this;
        }

        public Criteria andCourtchangetoNotIn(List<String> values) {
            addCriterion("COURTCHANGETO not in", values, "courtchangeto");
            return (Criteria) this;
        }

        public Criteria andCourtchangetoBetween(String value1, String value2) {
            addCriterion("COURTCHANGETO between", value1, value2, "courtchangeto");
            return (Criteria) this;
        }

        public Criteria andCourtchangetoNotBetween(String value1, String value2) {
            addCriterion("COURTCHANGETO not between", value1, value2, "courtchangeto");
            return (Criteria) this;
        }

        public Criteria andCourtareaIsNull() {
            addCriterion("COURTAREA is null");
            return (Criteria) this;
        }

        public Criteria andCourtareaIsNotNull() {
            addCriterion("COURTAREA is not null");
            return (Criteria) this;
        }

        public Criteria andCourtareaEqualTo(String value) {
            addCriterion("COURTAREA =", value, "courtarea");
            return (Criteria) this;
        }

        public Criteria andCourtareaNotEqualTo(String value) {
            addCriterion("COURTAREA <>", value, "courtarea");
            return (Criteria) this;
        }

        public Criteria andCourtareaGreaterThan(String value) {
            addCriterion("COURTAREA >", value, "courtarea");
            return (Criteria) this;
        }

        public Criteria andCourtareaGreaterThanOrEqualTo(String value) {
            addCriterion("COURTAREA >=", value, "courtarea");
            return (Criteria) this;
        }

        public Criteria andCourtareaLessThan(String value) {
            addCriterion("COURTAREA <", value, "courtarea");
            return (Criteria) this;
        }

        public Criteria andCourtareaLessThanOrEqualTo(String value) {
            addCriterion("COURTAREA <=", value, "courtarea");
            return (Criteria) this;
        }

        public Criteria andCourtareaLike(String value) {
            addCriterion("COURTAREA like", value, "courtarea");
            return (Criteria) this;
        }

        public Criteria andCourtareaNotLike(String value) {
            addCriterion("COURTAREA not like", value, "courtarea");
            return (Criteria) this;
        }

        public Criteria andCourtareaIn(List<String> values) {
            addCriterion("COURTAREA in", values, "courtarea");
            return (Criteria) this;
        }

        public Criteria andCourtareaNotIn(List<String> values) {
            addCriterion("COURTAREA not in", values, "courtarea");
            return (Criteria) this;
        }

        public Criteria andCourtareaBetween(String value1, String value2) {
            addCriterion("COURTAREA between", value1, value2, "courtarea");
            return (Criteria) this;
        }

        public Criteria andCourtareaNotBetween(String value1, String value2) {
            addCriterion("COURTAREA not between", value1, value2, "courtarea");
            return (Criteria) this;
        }

        public Criteria andLosepowerIsNull() {
            addCriterion("LOSEPOWER is null");
            return (Criteria) this;
        }

        public Criteria andLosepowerIsNotNull() {
            addCriterion("LOSEPOWER is not null");
            return (Criteria) this;
        }

        public Criteria andLosepowerEqualTo(String value) {
            addCriterion("LOSEPOWER =", value, "losepower");
            return (Criteria) this;
        }

        public Criteria andLosepowerNotEqualTo(String value) {
            addCriterion("LOSEPOWER <>", value, "losepower");
            return (Criteria) this;
        }

        public Criteria andLosepowerGreaterThan(String value) {
            addCriterion("LOSEPOWER >", value, "losepower");
            return (Criteria) this;
        }

        public Criteria andLosepowerGreaterThanOrEqualTo(String value) {
            addCriterion("LOSEPOWER >=", value, "losepower");
            return (Criteria) this;
        }

        public Criteria andLosepowerLessThan(String value) {
            addCriterion("LOSEPOWER <", value, "losepower");
            return (Criteria) this;
        }

        public Criteria andLosepowerLessThanOrEqualTo(String value) {
            addCriterion("LOSEPOWER <=", value, "losepower");
            return (Criteria) this;
        }

        public Criteria andLosepowerLike(String value) {
            addCriterion("LOSEPOWER like", value, "losepower");
            return (Criteria) this;
        }

        public Criteria andLosepowerNotLike(String value) {
            addCriterion("LOSEPOWER not like", value, "losepower");
            return (Criteria) this;
        }

        public Criteria andLosepowerIn(List<String> values) {
            addCriterion("LOSEPOWER in", values, "losepower");
            return (Criteria) this;
        }

        public Criteria andLosepowerNotIn(List<String> values) {
            addCriterion("LOSEPOWER not in", values, "losepower");
            return (Criteria) this;
        }

        public Criteria andLosepowerBetween(String value1, String value2) {
            addCriterion("LOSEPOWER between", value1, value2, "losepower");
            return (Criteria) this;
        }

        public Criteria andLosepowerNotBetween(String value1, String value2) {
            addCriterion("LOSEPOWER not between", value1, value2, "losepower");
            return (Criteria) this;
        }

        public Criteria andFineIsNull() {
            addCriterion("FINE is null");
            return (Criteria) this;
        }

        public Criteria andFineIsNotNull() {
            addCriterion("FINE is not null");
            return (Criteria) this;
        }

        public Criteria andFineEqualTo(String value) {
            addCriterion("FINE =", value, "fine");
            return (Criteria) this;
        }

        public Criteria andFineNotEqualTo(String value) {
            addCriterion("FINE <>", value, "fine");
            return (Criteria) this;
        }

        public Criteria andFineGreaterThan(String value) {
            addCriterion("FINE >", value, "fine");
            return (Criteria) this;
        }

        public Criteria andFineGreaterThanOrEqualTo(String value) {
            addCriterion("FINE >=", value, "fine");
            return (Criteria) this;
        }

        public Criteria andFineLessThan(String value) {
            addCriterion("FINE <", value, "fine");
            return (Criteria) this;
        }

        public Criteria andFineLessThanOrEqualTo(String value) {
            addCriterion("FINE <=", value, "fine");
            return (Criteria) this;
        }

        public Criteria andFineLike(String value) {
            addCriterion("FINE like", value, "fine");
            return (Criteria) this;
        }

        public Criteria andFineNotLike(String value) {
            addCriterion("FINE not like", value, "fine");
            return (Criteria) this;
        }

        public Criteria andFineIn(List<String> values) {
            addCriterion("FINE in", values, "fine");
            return (Criteria) this;
        }

        public Criteria andFineNotIn(List<String> values) {
            addCriterion("FINE not in", values, "fine");
            return (Criteria) this;
        }

        public Criteria andFineBetween(String value1, String value2) {
            addCriterion("FINE between", value1, value2, "fine");
            return (Criteria) this;
        }

        public Criteria andFineNotBetween(String value1, String value2) {
            addCriterion("FINE not between", value1, value2, "fine");
            return (Criteria) this;
        }

        public Criteria andSumfineIsNull() {
            addCriterion("SUMFINE is null");
            return (Criteria) this;
        }

        public Criteria andSumfineIsNotNull() {
            addCriterion("SUMFINE is not null");
            return (Criteria) this;
        }

        public Criteria andSumfineEqualTo(String value) {
            addCriterion("SUMFINE =", value, "sumfine");
            return (Criteria) this;
        }

        public Criteria andSumfineNotEqualTo(String value) {
            addCriterion("SUMFINE <>", value, "sumfine");
            return (Criteria) this;
        }

        public Criteria andSumfineGreaterThan(String value) {
            addCriterion("SUMFINE >", value, "sumfine");
            return (Criteria) this;
        }

        public Criteria andSumfineGreaterThanOrEqualTo(String value) {
            addCriterion("SUMFINE >=", value, "sumfine");
            return (Criteria) this;
        }

        public Criteria andSumfineLessThan(String value) {
            addCriterion("SUMFINE <", value, "sumfine");
            return (Criteria) this;
        }

        public Criteria andSumfineLessThanOrEqualTo(String value) {
            addCriterion("SUMFINE <=", value, "sumfine");
            return (Criteria) this;
        }

        public Criteria andSumfineLike(String value) {
            addCriterion("SUMFINE like", value, "sumfine");
            return (Criteria) this;
        }

        public Criteria andSumfineNotLike(String value) {
            addCriterion("SUMFINE not like", value, "sumfine");
            return (Criteria) this;
        }

        public Criteria andSumfineIn(List<String> values) {
            addCriterion("SUMFINE in", values, "sumfine");
            return (Criteria) this;
        }

        public Criteria andSumfineNotIn(List<String> values) {
            addCriterion("SUMFINE not in", values, "sumfine");
            return (Criteria) this;
        }

        public Criteria andSumfineBetween(String value1, String value2) {
            addCriterion("SUMFINE between", value1, value2, "sumfine");
            return (Criteria) this;
        }

        public Criteria andSumfineNotBetween(String value1, String value2) {
            addCriterion("SUMFINE not between", value1, value2, "sumfine");
            return (Criteria) this;
        }

        public Criteria andOfineIsNull() {
            addCriterion("OFINE is null");
            return (Criteria) this;
        }

        public Criteria andOfineIsNotNull() {
            addCriterion("OFINE is not null");
            return (Criteria) this;
        }

        public Criteria andOfineEqualTo(String value) {
            addCriterion("OFINE =", value, "ofine");
            return (Criteria) this;
        }

        public Criteria andOfineNotEqualTo(String value) {
            addCriterion("OFINE <>", value, "ofine");
            return (Criteria) this;
        }

        public Criteria andOfineGreaterThan(String value) {
            addCriterion("OFINE >", value, "ofine");
            return (Criteria) this;
        }

        public Criteria andOfineGreaterThanOrEqualTo(String value) {
            addCriterion("OFINE >=", value, "ofine");
            return (Criteria) this;
        }

        public Criteria andOfineLessThan(String value) {
            addCriterion("OFINE <", value, "ofine");
            return (Criteria) this;
        }

        public Criteria andOfineLessThanOrEqualTo(String value) {
            addCriterion("OFINE <=", value, "ofine");
            return (Criteria) this;
        }

        public Criteria andOfineLike(String value) {
            addCriterion("OFINE like", value, "ofine");
            return (Criteria) this;
        }

        public Criteria andOfineNotLike(String value) {
            addCriterion("OFINE not like", value, "ofine");
            return (Criteria) this;
        }

        public Criteria andOfineIn(List<String> values) {
            addCriterion("OFINE in", values, "ofine");
            return (Criteria) this;
        }

        public Criteria andOfineNotIn(List<String> values) {
            addCriterion("OFINE not in", values, "ofine");
            return (Criteria) this;
        }

        public Criteria andOfineBetween(String value1, String value2) {
            addCriterion("OFINE between", value1, value2, "ofine");
            return (Criteria) this;
        }

        public Criteria andOfineNotBetween(String value1, String value2) {
            addCriterion("OFINE not between", value1, value2, "ofine");
            return (Criteria) this;
        }

        public Criteria andIsdeportationIsNull() {
            addCriterion("ISDEPORTATION is null");
            return (Criteria) this;
        }

        public Criteria andIsdeportationIsNotNull() {
            addCriterion("ISDEPORTATION is not null");
            return (Criteria) this;
        }

        public Criteria andIsdeportationEqualTo(Short value) {
            addCriterion("ISDEPORTATION =", value, "isdeportation");
            return (Criteria) this;
        }

        public Criteria andIsdeportationNotEqualTo(Short value) {
            addCriterion("ISDEPORTATION <>", value, "isdeportation");
            return (Criteria) this;
        }

        public Criteria andIsdeportationGreaterThan(Short value) {
            addCriterion("ISDEPORTATION >", value, "isdeportation");
            return (Criteria) this;
        }

        public Criteria andIsdeportationGreaterThanOrEqualTo(Short value) {
            addCriterion("ISDEPORTATION >=", value, "isdeportation");
            return (Criteria) this;
        }

        public Criteria andIsdeportationLessThan(Short value) {
            addCriterion("ISDEPORTATION <", value, "isdeportation");
            return (Criteria) this;
        }

        public Criteria andIsdeportationLessThanOrEqualTo(Short value) {
            addCriterion("ISDEPORTATION <=", value, "isdeportation");
            return (Criteria) this;
        }

        public Criteria andIsdeportationIn(List<Short> values) {
            addCriterion("ISDEPORTATION in", values, "isdeportation");
            return (Criteria) this;
        }

        public Criteria andIsdeportationNotIn(List<Short> values) {
            addCriterion("ISDEPORTATION not in", values, "isdeportation");
            return (Criteria) this;
        }

        public Criteria andIsdeportationBetween(Short value1, Short value2) {
            addCriterion("ISDEPORTATION between", value1, value2, "isdeportation");
            return (Criteria) this;
        }

        public Criteria andIsdeportationNotBetween(Short value1, Short value2) {
            addCriterion("ISDEPORTATION not between", value1, value2, "isdeportation");
            return (Criteria) this;
        }

        public Criteria andAttachinfoIsNull() {
            addCriterion("ATTACHINFO is null");
            return (Criteria) this;
        }

        public Criteria andAttachinfoIsNotNull() {
            addCriterion("ATTACHINFO is not null");
            return (Criteria) this;
        }

        public Criteria andAttachinfoEqualTo(String value) {
            addCriterion("ATTACHINFO =", value, "attachinfo");
            return (Criteria) this;
        }

        public Criteria andAttachinfoNotEqualTo(String value) {
            addCriterion("ATTACHINFO <>", value, "attachinfo");
            return (Criteria) this;
        }

        public Criteria andAttachinfoGreaterThan(String value) {
            addCriterion("ATTACHINFO >", value, "attachinfo");
            return (Criteria) this;
        }

        public Criteria andAttachinfoGreaterThanOrEqualTo(String value) {
            addCriterion("ATTACHINFO >=", value, "attachinfo");
            return (Criteria) this;
        }

        public Criteria andAttachinfoLessThan(String value) {
            addCriterion("ATTACHINFO <", value, "attachinfo");
            return (Criteria) this;
        }

        public Criteria andAttachinfoLessThanOrEqualTo(String value) {
            addCriterion("ATTACHINFO <=", value, "attachinfo");
            return (Criteria) this;
        }

        public Criteria andAttachinfoLike(String value) {
            addCriterion("ATTACHINFO like", value, "attachinfo");
            return (Criteria) this;
        }

        public Criteria andAttachinfoNotLike(String value) {
            addCriterion("ATTACHINFO not like", value, "attachinfo");
            return (Criteria) this;
        }

        public Criteria andAttachinfoIn(List<String> values) {
            addCriterion("ATTACHINFO in", values, "attachinfo");
            return (Criteria) this;
        }

        public Criteria andAttachinfoNotIn(List<String> values) {
            addCriterion("ATTACHINFO not in", values, "attachinfo");
            return (Criteria) this;
        }

        public Criteria andAttachinfoBetween(String value1, String value2) {
            addCriterion("ATTACHINFO between", value1, value2, "attachinfo");
            return (Criteria) this;
        }

        public Criteria andAttachinfoNotBetween(String value1, String value2) {
            addCriterion("ATTACHINFO not between", value1, value2, "attachinfo");
            return (Criteria) this;
        }

        public Criteria andCivilfineIsNull() {
            addCriterion("CIVILFINE is null");
            return (Criteria) this;
        }

        public Criteria andCivilfineIsNotNull() {
            addCriterion("CIVILFINE is not null");
            return (Criteria) this;
        }

        public Criteria andCivilfineEqualTo(String value) {
            addCriterion("CIVILFINE =", value, "civilfine");
            return (Criteria) this;
        }

        public Criteria andCivilfineNotEqualTo(String value) {
            addCriterion("CIVILFINE <>", value, "civilfine");
            return (Criteria) this;
        }

        public Criteria andCivilfineGreaterThan(String value) {
            addCriterion("CIVILFINE >", value, "civilfine");
            return (Criteria) this;
        }

        public Criteria andCivilfineGreaterThanOrEqualTo(String value) {
            addCriterion("CIVILFINE >=", value, "civilfine");
            return (Criteria) this;
        }

        public Criteria andCivilfineLessThan(String value) {
            addCriterion("CIVILFINE <", value, "civilfine");
            return (Criteria) this;
        }

        public Criteria andCivilfineLessThanOrEqualTo(String value) {
            addCriterion("CIVILFINE <=", value, "civilfine");
            return (Criteria) this;
        }

        public Criteria andCivilfineLike(String value) {
            addCriterion("CIVILFINE like", value, "civilfine");
            return (Criteria) this;
        }

        public Criteria andCivilfineNotLike(String value) {
            addCriterion("CIVILFINE not like", value, "civilfine");
            return (Criteria) this;
        }

        public Criteria andCivilfineIn(List<String> values) {
            addCriterion("CIVILFINE in", values, "civilfine");
            return (Criteria) this;
        }

        public Criteria andCivilfineNotIn(List<String> values) {
            addCriterion("CIVILFINE not in", values, "civilfine");
            return (Criteria) this;
        }

        public Criteria andCivilfineBetween(String value1, String value2) {
            addCriterion("CIVILFINE between", value1, value2, "civilfine");
            return (Criteria) this;
        }

        public Criteria andCivilfineNotBetween(String value1, String value2) {
            addCriterion("CIVILFINE not between", value1, value2, "civilfine");
            return (Criteria) this;
        }

        public Criteria andExpropriationinfoIsNull() {
            addCriterion("EXPROPRIATIONINFO is null");
            return (Criteria) this;
        }

        public Criteria andExpropriationinfoIsNotNull() {
            addCriterion("EXPROPRIATIONINFO is not null");
            return (Criteria) this;
        }

        public Criteria andExpropriationinfoEqualTo(String value) {
            addCriterion("EXPROPRIATIONINFO =", value, "expropriationinfo");
            return (Criteria) this;
        }

        public Criteria andExpropriationinfoNotEqualTo(String value) {
            addCriterion("EXPROPRIATIONINFO <>", value, "expropriationinfo");
            return (Criteria) this;
        }

        public Criteria andExpropriationinfoGreaterThan(String value) {
            addCriterion("EXPROPRIATIONINFO >", value, "expropriationinfo");
            return (Criteria) this;
        }

        public Criteria andExpropriationinfoGreaterThanOrEqualTo(String value) {
            addCriterion("EXPROPRIATIONINFO >=", value, "expropriationinfo");
            return (Criteria) this;
        }

        public Criteria andExpropriationinfoLessThan(String value) {
            addCriterion("EXPROPRIATIONINFO <", value, "expropriationinfo");
            return (Criteria) this;
        }

        public Criteria andExpropriationinfoLessThanOrEqualTo(String value) {
            addCriterion("EXPROPRIATIONINFO <=", value, "expropriationinfo");
            return (Criteria) this;
        }

        public Criteria andExpropriationinfoLike(String value) {
            addCriterion("EXPROPRIATIONINFO like", value, "expropriationinfo");
            return (Criteria) this;
        }

        public Criteria andExpropriationinfoNotLike(String value) {
            addCriterion("EXPROPRIATIONINFO not like", value, "expropriationinfo");
            return (Criteria) this;
        }

        public Criteria andExpropriationinfoIn(List<String> values) {
            addCriterion("EXPROPRIATIONINFO in", values, "expropriationinfo");
            return (Criteria) this;
        }

        public Criteria andExpropriationinfoNotIn(List<String> values) {
            addCriterion("EXPROPRIATIONINFO not in", values, "expropriationinfo");
            return (Criteria) this;
        }

        public Criteria andExpropriationinfoBetween(String value1, String value2) {
            addCriterion("EXPROPRIATIONINFO between", value1, value2, "expropriationinfo");
            return (Criteria) this;
        }

        public Criteria andExpropriationinfoNotBetween(String value1, String value2) {
            addCriterion("EXPROPRIATIONINFO not between", value1, value2, "expropriationinfo");
            return (Criteria) this;
        }

        public Criteria andSumcivilfineIsNull() {
            addCriterion("SUMCIVILFINE is null");
            return (Criteria) this;
        }

        public Criteria andSumcivilfineIsNotNull() {
            addCriterion("SUMCIVILFINE is not null");
            return (Criteria) this;
        }

        public Criteria andSumcivilfineEqualTo(String value) {
            addCriterion("SUMCIVILFINE =", value, "sumcivilfine");
            return (Criteria) this;
        }

        public Criteria andSumcivilfineNotEqualTo(String value) {
            addCriterion("SUMCIVILFINE <>", value, "sumcivilfine");
            return (Criteria) this;
        }

        public Criteria andSumcivilfineGreaterThan(String value) {
            addCriterion("SUMCIVILFINE >", value, "sumcivilfine");
            return (Criteria) this;
        }

        public Criteria andSumcivilfineGreaterThanOrEqualTo(String value) {
            addCriterion("SUMCIVILFINE >=", value, "sumcivilfine");
            return (Criteria) this;
        }

        public Criteria andSumcivilfineLessThan(String value) {
            addCriterion("SUMCIVILFINE <", value, "sumcivilfine");
            return (Criteria) this;
        }

        public Criteria andSumcivilfineLessThanOrEqualTo(String value) {
            addCriterion("SUMCIVILFINE <=", value, "sumcivilfine");
            return (Criteria) this;
        }

        public Criteria andSumcivilfineLike(String value) {
            addCriterion("SUMCIVILFINE like", value, "sumcivilfine");
            return (Criteria) this;
        }

        public Criteria andSumcivilfineNotLike(String value) {
            addCriterion("SUMCIVILFINE not like", value, "sumcivilfine");
            return (Criteria) this;
        }

        public Criteria andSumcivilfineIn(List<String> values) {
            addCriterion("SUMCIVILFINE in", values, "sumcivilfine");
            return (Criteria) this;
        }

        public Criteria andSumcivilfineNotIn(List<String> values) {
            addCriterion("SUMCIVILFINE not in", values, "sumcivilfine");
            return (Criteria) this;
        }

        public Criteria andSumcivilfineBetween(String value1, String value2) {
            addCriterion("SUMCIVILFINE between", value1, value2, "sumcivilfine");
            return (Criteria) this;
        }

        public Criteria andSumcivilfineNotBetween(String value1, String value2) {
            addCriterion("SUMCIVILFINE not between", value1, value2, "sumcivilfine");
            return (Criteria) this;
        }

        public Criteria andCrimclassesIsNull() {
            addCriterion("CRIMCLASSES is null");
            return (Criteria) this;
        }

        public Criteria andCrimclassesIsNotNull() {
            addCriterion("CRIMCLASSES is not null");
            return (Criteria) this;
        }

        public Criteria andCrimclassesEqualTo(String value) {
            addCriterion("CRIMCLASSES =", value, "crimclasses");
            return (Criteria) this;
        }

        public Criteria andCrimclassesNotEqualTo(String value) {
            addCriterion("CRIMCLASSES <>", value, "crimclasses");
            return (Criteria) this;
        }

        public Criteria andCrimclassesGreaterThan(String value) {
            addCriterion("CRIMCLASSES >", value, "crimclasses");
            return (Criteria) this;
        }

        public Criteria andCrimclassesGreaterThanOrEqualTo(String value) {
            addCriterion("CRIMCLASSES >=", value, "crimclasses");
            return (Criteria) this;
        }

        public Criteria andCrimclassesLessThan(String value) {
            addCriterion("CRIMCLASSES <", value, "crimclasses");
            return (Criteria) this;
        }

        public Criteria andCrimclassesLessThanOrEqualTo(String value) {
            addCriterion("CRIMCLASSES <=", value, "crimclasses");
            return (Criteria) this;
        }

        public Criteria andCrimclassesLike(String value) {
            addCriterion("CRIMCLASSES like", value, "crimclasses");
            return (Criteria) this;
        }

        public Criteria andCrimclassesNotLike(String value) {
            addCriterion("CRIMCLASSES not like", value, "crimclasses");
            return (Criteria) this;
        }

        public Criteria andCrimclassesIn(List<String> values) {
            addCriterion("CRIMCLASSES in", values, "crimclasses");
            return (Criteria) this;
        }

        public Criteria andCrimclassesNotIn(List<String> values) {
            addCriterion("CRIMCLASSES not in", values, "crimclasses");
            return (Criteria) this;
        }

        public Criteria andCrimclassesBetween(String value1, String value2) {
            addCriterion("CRIMCLASSES between", value1, value2, "crimclasses");
            return (Criteria) this;
        }

        public Criteria andCrimclassesNotBetween(String value1, String value2) {
            addCriterion("CRIMCLASSES not between", value1, value2, "crimclasses");
            return (Criteria) this;
        }

        public Criteria andIslimitIsNull() {
            addCriterion("ISLIMIT is null");
            return (Criteria) this;
        }

        public Criteria andIslimitIsNotNull() {
            addCriterion("ISLIMIT is not null");
            return (Criteria) this;
        }

        public Criteria andIslimitEqualTo(Short value) {
            addCriterion("ISLIMIT =", value, "islimit");
            return (Criteria) this;
        }

        public Criteria andIslimitNotEqualTo(Short value) {
            addCriterion("ISLIMIT <>", value, "islimit");
            return (Criteria) this;
        }

        public Criteria andIslimitGreaterThan(Short value) {
            addCriterion("ISLIMIT >", value, "islimit");
            return (Criteria) this;
        }

        public Criteria andIslimitGreaterThanOrEqualTo(Short value) {
            addCriterion("ISLIMIT >=", value, "islimit");
            return (Criteria) this;
        }

        public Criteria andIslimitLessThan(Short value) {
            addCriterion("ISLIMIT <", value, "islimit");
            return (Criteria) this;
        }

        public Criteria andIslimitLessThanOrEqualTo(Short value) {
            addCriterion("ISLIMIT <=", value, "islimit");
            return (Criteria) this;
        }

        public Criteria andIslimitIn(List<Short> values) {
            addCriterion("ISLIMIT in", values, "islimit");
            return (Criteria) this;
        }

        public Criteria andIslimitNotIn(List<Short> values) {
            addCriterion("ISLIMIT not in", values, "islimit");
            return (Criteria) this;
        }

        public Criteria andIslimitBetween(Short value1, Short value2) {
            addCriterion("ISLIMIT between", value1, value2, "islimit");
            return (Criteria) this;
        }

        public Criteria andIslimitNotBetween(Short value1, Short value2) {
            addCriterion("ISLIMIT not between", value1, value2, "islimit");
            return (Criteria) this;
        }

        public Criteria andIsparoleIsNull() {
            addCriterion("ISPAROLE is null");
            return (Criteria) this;
        }

        public Criteria andIsparoleIsNotNull() {
            addCriterion("ISPAROLE is not null");
            return (Criteria) this;
        }

        public Criteria andIsparoleEqualTo(Short value) {
            addCriterion("ISPAROLE =", value, "isparole");
            return (Criteria) this;
        }

        public Criteria andIsparoleNotEqualTo(Short value) {
            addCriterion("ISPAROLE <>", value, "isparole");
            return (Criteria) this;
        }

        public Criteria andIsparoleGreaterThan(Short value) {
            addCriterion("ISPAROLE >", value, "isparole");
            return (Criteria) this;
        }

        public Criteria andIsparoleGreaterThanOrEqualTo(Short value) {
            addCriterion("ISPAROLE >=", value, "isparole");
            return (Criteria) this;
        }

        public Criteria andIsparoleLessThan(Short value) {
            addCriterion("ISPAROLE <", value, "isparole");
            return (Criteria) this;
        }

        public Criteria andIsparoleLessThanOrEqualTo(Short value) {
            addCriterion("ISPAROLE <=", value, "isparole");
            return (Criteria) this;
        }

        public Criteria andIsparoleIn(List<Short> values) {
            addCriterion("ISPAROLE in", values, "isparole");
            return (Criteria) this;
        }

        public Criteria andIsparoleNotIn(List<Short> values) {
            addCriterion("ISPAROLE not in", values, "isparole");
            return (Criteria) this;
        }

        public Criteria andIsparoleBetween(Short value1, Short value2) {
            addCriterion("ISPAROLE between", value1, value2, "isparole");
            return (Criteria) this;
        }

        public Criteria andIsparoleNotBetween(Short value1, Short value2) {
            addCriterion("ISPAROLE not between", value1, value2, "isparole");
            return (Criteria) this;
        }

        public Criteria andOrgidIsNull() {
            addCriterion("ORGID is null");
            return (Criteria) this;
        }

        public Criteria andOrgidIsNotNull() {
            addCriterion("ORGID is not null");
            return (Criteria) this;
        }

        public Criteria andOrgidEqualTo(String value) {
            addCriterion("ORGID =", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidNotEqualTo(String value) {
            addCriterion("ORGID <>", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidGreaterThan(String value) {
            addCriterion("ORGID >", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidGreaterThanOrEqualTo(String value) {
            addCriterion("ORGID >=", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidLessThan(String value) {
            addCriterion("ORGID <", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidLessThanOrEqualTo(String value) {
            addCriterion("ORGID <=", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidLike(String value) {
            addCriterion("ORGID like", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidNotLike(String value) {
            addCriterion("ORGID not like", value, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidIn(List<String> values) {
            addCriterion("ORGID in", values, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidNotIn(List<String> values) {
            addCriterion("ORGID not in", values, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidBetween(String value1, String value2) {
            addCriterion("ORGID between", value1, value2, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgidNotBetween(String value1, String value2) {
            addCriterion("ORGID not between", value1, value2, "orgid");
            return (Criteria) this;
        }

        public Criteria andOrgnameIsNull() {
            addCriterion("ORGNAME is null");
            return (Criteria) this;
        }

        public Criteria andOrgnameIsNotNull() {
            addCriterion("ORGNAME is not null");
            return (Criteria) this;
        }

        public Criteria andOrgnameEqualTo(String value) {
            addCriterion("ORGNAME =", value, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameNotEqualTo(String value) {
            addCriterion("ORGNAME <>", value, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameGreaterThan(String value) {
            addCriterion("ORGNAME >", value, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameGreaterThanOrEqualTo(String value) {
            addCriterion("ORGNAME >=", value, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameLessThan(String value) {
            addCriterion("ORGNAME <", value, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameLessThanOrEqualTo(String value) {
            addCriterion("ORGNAME <=", value, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameLike(String value) {
            addCriterion("ORGNAME like", value, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameNotLike(String value) {
            addCriterion("ORGNAME not like", value, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameIn(List<String> values) {
            addCriterion("ORGNAME in", values, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameNotIn(List<String> values) {
            addCriterion("ORGNAME not in", values, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameBetween(String value1, String value2) {
            addCriterion("ORGNAME between", value1, value2, "orgname");
            return (Criteria) this;
        }

        public Criteria andOrgnameNotBetween(String value1, String value2) {
            addCriterion("ORGNAME not between", value1, value2, "orgname");
            return (Criteria) this;
        }

        public Criteria andAwardendIsNull() {
            addCriterion("AWARDEND is null");
            return (Criteria) this;
        }

        public Criteria andAwardendIsNotNull() {
            addCriterion("AWARDEND is not null");
            return (Criteria) this;
        }

        public Criteria andAwardendEqualTo(String value) {
            addCriterion("AWARDEND =", value, "awardend");
            return (Criteria) this;
        }

        public Criteria andAwardendNotEqualTo(String value) {
            addCriterion("AWARDEND <>", value, "awardend");
            return (Criteria) this;
        }

        public Criteria andAwardendGreaterThan(String value) {
            addCriterion("AWARDEND >", value, "awardend");
            return (Criteria) this;
        }

        public Criteria andAwardendGreaterThanOrEqualTo(String value) {
            addCriterion("AWARDEND >=", value, "awardend");
            return (Criteria) this;
        }

        public Criteria andAwardendLessThan(String value) {
            addCriterion("AWARDEND <", value, "awardend");
            return (Criteria) this;
        }

        public Criteria andAwardendLessThanOrEqualTo(String value) {
            addCriterion("AWARDEND <=", value, "awardend");
            return (Criteria) this;
        }

        public Criteria andAwardendLike(String value) {
            addCriterion("AWARDEND like", value, "awardend");
            return (Criteria) this;
        }

        public Criteria andAwardendNotLike(String value) {
            addCriterion("AWARDEND not like", value, "awardend");
            return (Criteria) this;
        }

        public Criteria andAwardendIn(List<String> values) {
            addCriterion("AWARDEND in", values, "awardend");
            return (Criteria) this;
        }

        public Criteria andAwardendNotIn(List<String> values) {
            addCriterion("AWARDEND not in", values, "awardend");
            return (Criteria) this;
        }

        public Criteria andAwardendBetween(String value1, String value2) {
            addCriterion("AWARDEND between", value1, value2, "awardend");
            return (Criteria) this;
        }

        public Criteria andAwardendNotBetween(String value1, String value2) {
            addCriterion("AWARDEND not between", value1, value2, "awardend");
            return (Criteria) this;
        }

        public Criteria andReporttimeIsNull() {
            addCriterion("REPORTTIME is null");
            return (Criteria) this;
        }

        public Criteria andReporttimeIsNotNull() {
            addCriterion("REPORTTIME is not null");
            return (Criteria) this;
        }

        public Criteria andReporttimeEqualTo(String value) {
            addCriterion("REPORTTIME =", value, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeNotEqualTo(String value) {
            addCriterion("REPORTTIME <>", value, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeGreaterThan(String value) {
            addCriterion("REPORTTIME >", value, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeGreaterThanOrEqualTo(String value) {
            addCriterion("REPORTTIME >=", value, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeLessThan(String value) {
            addCriterion("REPORTTIME <", value, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeLessThanOrEqualTo(String value) {
            addCriterion("REPORTTIME <=", value, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeLike(String value) {
            addCriterion("REPORTTIME like", value, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeNotLike(String value) {
            addCriterion("REPORTTIME not like", value, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeIn(List<String> values) {
            addCriterion("REPORTTIME in", values, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeNotIn(List<String> values) {
            addCriterion("REPORTTIME not in", values, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeBetween(String value1, String value2) {
            addCriterion("REPORTTIME between", value1, value2, "reporttime");
            return (Criteria) this;
        }

        public Criteria andReporttimeNotBetween(String value1, String value2) {
            addCriterion("REPORTTIME not between", value1, value2, "reporttime");
            return (Criteria) this;
        }

        public Criteria andExectimeIsNull() {
            addCriterion("EXECTIME is null");
            return (Criteria) this;
        }

        public Criteria andExectimeIsNotNull() {
            addCriterion("EXECTIME is not null");
            return (Criteria) this;
        }

        public Criteria andExectimeEqualTo(String value) {
            addCriterion("EXECTIME =", value, "exectime");
            return (Criteria) this;
        }

        public Criteria andExectimeNotEqualTo(String value) {
            addCriterion("EXECTIME <>", value, "exectime");
            return (Criteria) this;
        }

        public Criteria andExectimeGreaterThan(String value) {
            addCriterion("EXECTIME >", value, "exectime");
            return (Criteria) this;
        }

        public Criteria andExectimeGreaterThanOrEqualTo(String value) {
            addCriterion("EXECTIME >=", value, "exectime");
            return (Criteria) this;
        }

        public Criteria andExectimeLessThan(String value) {
            addCriterion("EXECTIME <", value, "exectime");
            return (Criteria) this;
        }

        public Criteria andExectimeLessThanOrEqualTo(String value) {
            addCriterion("EXECTIME <=", value, "exectime");
            return (Criteria) this;
        }

        public Criteria andExectimeLike(String value) {
            addCriterion("EXECTIME like", value, "exectime");
            return (Criteria) this;
        }

        public Criteria andExectimeNotLike(String value) {
            addCriterion("EXECTIME not like", value, "exectime");
            return (Criteria) this;
        }

        public Criteria andExectimeIn(List<String> values) {
            addCriterion("EXECTIME in", values, "exectime");
            return (Criteria) this;
        }

        public Criteria andExectimeNotIn(List<String> values) {
            addCriterion("EXECTIME not in", values, "exectime");
            return (Criteria) this;
        }

        public Criteria andExectimeBetween(String value1, String value2) {
            addCriterion("EXECTIME between", value1, value2, "exectime");
            return (Criteria) this;
        }

        public Criteria andExectimeNotBetween(String value1, String value2) {
            addCriterion("EXECTIME not between", value1, value2, "exectime");
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