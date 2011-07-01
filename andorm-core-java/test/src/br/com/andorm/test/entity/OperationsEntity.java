package br.com.andorm.test.entity;

import br.com.andorm.AfterDelete;
import br.com.andorm.AfterSave;
import br.com.andorm.AfterUpdate;
import br.com.andorm.BeforeDelete;
import br.com.andorm.BeforeSave;
import br.com.andorm.BeforeUpdate;
import br.com.andorm.Entity;
import br.com.andorm.PrimaryKey;
import br.com.andorm.Transient;

@Entity
public class OperationsEntity {

	@PrimaryKey
	private Integer id;

	@Transient
	private boolean beforeSaveExecuted;
	@Transient
	private boolean afterSaveExecuted;
	@Transient
	private boolean beforeUpdateExecuted;
	@Transient
	private boolean afterUpdateExecuted;
	@Transient
	private boolean beforeDeleteExecuted;
	@Transient
	private boolean afterDeleteExecuted;

	@BeforeSave
	public void beforeSave() {
		beforeSaveExecuted = true;
	}

	@AfterSave
	public void afterSave() {
		afterSaveExecuted = true;
	}

	@BeforeUpdate
	public void beforeUpdate() {
		beforeUpdateExecuted = true;
	}

	@AfterUpdate
	public void afterUpdate() {
		afterUpdateExecuted = true;
	}

	@BeforeDelete
	public void beforeDelete() {
		beforeDeleteExecuted = true;
	}

	@AfterDelete
	public void afterDelete() {
		afterDeleteExecuted = true;
	}

	public boolean isBeforeSaveExecuted() {
		return beforeSaveExecuted;
	}

	public void setBeforeSaveExecuted(boolean beforeSaveExecuted) {
		this.beforeSaveExecuted = beforeSaveExecuted;
	}

	public boolean isAfterSaveExecuted() {
		return afterSaveExecuted;
	}

	public void setAfterSaveExecuted(boolean afterSaveExecuted) {
		this.afterSaveExecuted = afterSaveExecuted;
	}

	public boolean isBeforeUpdateExecuted() {
		return beforeUpdateExecuted;
	}

	public void setBeforeUpdateExecuted(boolean beforeUpdateExecuted) {
		this.beforeUpdateExecuted = beforeUpdateExecuted;
	}

	public boolean isAfterUpdateExecuted() {
		return afterUpdateExecuted;
	}

	public void setAfterUpdateExecuted(boolean afterUpdateExecuted) {
		this.afterUpdateExecuted = afterUpdateExecuted;
	}

	public boolean isBeforeDeleteExecuted() {
		return beforeDeleteExecuted;
	}

	public void setBeforeDeleteExecuted(boolean beforeDeleteExecuted) {
		this.beforeDeleteExecuted = beforeDeleteExecuted;
	}

	public boolean isAfterDeleteExecuted() {
		return afterDeleteExecuted;
	}

	public void setAfterDeleteExecuted(boolean afterDeleteExecuted) {
		this.afterDeleteExecuted = afterDeleteExecuted;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}