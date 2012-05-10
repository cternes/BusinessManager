package org.businessmanager.web.controller.state;

import java.util.List;

public abstract class AbstractModel<T> {

	private List<T> entityList;
	private T selectedEntity;
	
	public void setEntityList(List<T> entityList) {
		this.entityList = entityList;
	}
	
	public List<T> getEntityList() {
		return entityList;
	}

	public void setSelectedEntity(T selectedEntity) {
		this.selectedEntity = selectedEntity;
	}

	public T getSelectedEntity() {
		return selectedEntity;
	}
}
