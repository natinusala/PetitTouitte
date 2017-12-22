package resources;

import java.util.Set;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class User
{
	@Persistent
	@PrimaryKey
	String alias; //"@something"

	@Persistent
	String name; //"Firstname Lastname"

	@Persistent
	@ApiResourceProperty(ignored=AnnotationBoolean.TRUE)
	Set<String> followers;

	@Persistent
	@ApiResourceProperty(ignored=AnnotationBoolean.TRUE)
	Set<String> following;
	
	@Persistent
	long followersCount;
	
	@Persistent
	long followingCount;

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

	public Set<String> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<String> followers) {
		this.followers = followers;
	}

	public Set<String> getFollowing() {
		return following;
	}

	public void setFollowing(Set<String> following) {
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

