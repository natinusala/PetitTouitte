package resources;

import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Transient;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class User
{
	@Persistent(valueStrategy=IdGeneratorStrategy.IDENTITY)
	@PrimaryKey
	Long id;

	@Persistent
	String alias; //"@something"

	@Persistent
	String name; //"Firstname Lastname"

	@Persistent
	@ApiResourceProperty(ignored=AnnotationBoolean.TRUE)
	Set<Long> followers; //list of user ids

	@Persistent
	@ApiResourceProperty(ignored=AnnotationBoolean.TRUE)
	Set<Long> following; //list of user ids
	
	@Persistent
	long followersCount;
	
	@Persistent
	long followingCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Long> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<Long> followers) {
		this.followers = followers;
	}

	public Set<Long> getFollowing() {
		return following;
	}

	public void setFollowing(Set<Long> following) {
		this.following = following;
	}

	public long getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(long followersCount) {
		this.followersCount = followersCount;
	}

	public long getFollowingCount() {
		return followingCount;
	}

	public void setFollowingCount(long followingCount) {
		this.followingCount = followingCount;
	}


	
}

