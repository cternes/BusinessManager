/*******************************************************************************
 * Copyright 2012 Christian Ternes and Thorsten Volland
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.businessmanager.domain.security;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.businessmanager.domain.AbstractEntity;
import org.businessmanager.web.jsf.helper.ResourceBundleProducer;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * @author Christian Ternes
 *
 */
@Entity(name="groups")
public final class Group extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=50)
	@NotEmpty
	private String name;
	
	@Column
	private String messagesKey;
	
	@Column
	private String description;
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name="group_members")
	private List<User> members = new ArrayList<User>();
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="group_permissions")
	private List<Permission> permissions = new ArrayList<Permission>();
	
	private boolean isDefaultGroup = false;
	
	protected Group() {
	}
	
	public Group(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getMembers() {
		return members;
	}
	
	public int getMembersSize() {
		return members.size();
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> list) {
		this.permissions = list;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + "]";
	}

	public String getMessagesKey() {
		return messagesKey;
	}

	public void setMessagesKey(String messagesKey) {
		this.messagesKey = messagesKey;
	}
	
	public String getDisplayName() {
		if(messagesKey != null && !messagesKey.isEmpty()) {
			return ResourceBundleProducer.getString(messagesKey);
		}
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getIsDefaultGroup() {
		return isDefaultGroup;
	}

	public void setDefaultGroup(boolean isDefaultGroup) {
		this.isDefaultGroup = isDefaultGroup;
	}
	
}
