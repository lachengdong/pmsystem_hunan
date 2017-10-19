package com.sinog2c.model.dbmsnew;

import java.util.List;

public class ShuJuTempTable {
	private DbmsDatasTableNew thetable;
	private List<DbmsDatasChemeDetailNew> lielist;
	public DbmsDatasTableNew getThetable() {
		return thetable;
	}
	public void setThetable(DbmsDatasTableNew thetable) {
		this.thetable = thetable;
	}
	public List<DbmsDatasChemeDetailNew> getLielist() {
		return lielist;
	}
	public void setLielist(List<DbmsDatasChemeDetailNew> lielist) {
		this.lielist = lielist;
	}
}
