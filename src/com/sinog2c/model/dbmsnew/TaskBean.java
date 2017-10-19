package com.sinog2c.model.dbmsnew;

import java.io.Serializable;

/**
 * 任务进度Bean.
 */
public class TaskBean implements Serializable {

	private static final long serialVersionUID = 3288110250281569501L;

	/**
	 * 状态, 0 为未开始
	 */
	public static final int STATUS_INIT = 0;
	/**
	 * 状态, 1 为已开始
	 */
	public static final int STATUS_START = 1;
	/**
	 * 状态, 2为正在执行
	 */
	public static final int STATUS_DOING = 2;
	/**
	 * 状态, 3 为执行成功,
	 */
	public static final int STATUS_SUCCESS = 3;
	/**
	 * 状态, 4为执行失败.
	 */
	public static final int STATUS_FAILURE = 4;
	/**
	 * 5. 正在分发数据 
	 */
	public static final int STATUS_CALLING = 5;

	private volatile int status = STATUS_INIT; // 状态

	private volatile int counter;
	private volatile int sum;
	private double percent;
	private int delta;

	public TaskBean() {
	}

	public double getPercent() {
		if (this.sum != 0) {
			this.percent = this.counter * 100 / this.sum;
			if (this.percent >= 100.0) {
				this.percent = 100;
			}
		}
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getDelta() {
		return delta;
	}

	public void setDelta(int delta) {
		this.delta = delta;
	}

}
